import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.JFreeChartEntity;
import org.jfree.chart.resources.JFreeChartResources;
import org.jfree.data.xy.XYDatasetTableModel;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.statistics.*;

//client id 571325534848-5gv2trfk3tm7dd1bs8980uok5pd9rhgp.apps.googleusercontent.com
//client secrete aYtej_QeEYei42ygruIvxjpS



import com.fazecast.jSerialComm.SerialPort;

public class SensorGraph {
	
	static SerialPort chosenPort;
	static SerialPort chosenPort2;
	static SerialPort chosenPort3;
	static int cantidad_puertos = 4;
	static int cantidad_sensores = 13;

	static int x = 0;

	public static void main(String[] args) {
		// creo una ventana
		System.out.println("Begin");
		JFrame window = new JFrame();
		window.setTitle("Monitor de Rele");
		 Dimension UserScreen = Toolkit.getDefaultToolkit().getScreenSize();
		    int ScreenWidth = (int) UserScreen.getWidth();
		    int ScreenHeight = (int) UserScreen.getHeight();
		    window.setSize(ScreenWidth, ScreenHeight);
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		
		//crear un menu de seleccion de puertos y el boton para empezar
		JComboBox<String> portList = new JComboBox<String>();
		JComboBox<String> portList2 = new JComboBox<String>();
		JComboBox<String> portList3 = new JComboBox<String>();
		JComboBox<String> portList4 = new JComboBox<String>();
		JButton connectButton = new JButton("Conectar");
		JPanel topPanel = new JPanel();
		JPanel centerPanel = new JPanel(new GridLayout(3, 5));
		topPanel.add(portList);
		topPanel.add(portList2);
		topPanel.add(portList3);
		topPanel.add(portList4);
		topPanel.add(connectButton);
		
		
		//cargar los puertos disponibles en el combo
		SerialPort[] portNames = SerialPort.getCommPorts();
		for (int i = 0; i < portNames.length; i++) {
		//cargo los dos combo
			portList.addItem(portNames[i].getSystemPortName());
			portList2.addItem(portNames[i].getSystemPortName());
			portList3.addItem(portNames[i].getSystemPortName());
			portList4.addItem(portNames[i].getSystemPortName());
		}
		
		
		XYSeries[][] seriesArray = new XYSeries[cantidad_puertos][cantidad_sensores];
		XYSeriesCollection[] datasetArray = new XYSeriesCollection[cantidad_sensores];
		JFreeChart[] chartArray = new JFreeChart[cantidad_sensores];
		
		//blind series to dataset
		for (int i = 0; i < datasetArray.length; i++) {//13
			
			System.out.println("Creo Dataset: "+i);
			datasetArray[i] = new XYSeriesCollection();
			
			for (int j = 0; j < seriesArray.length; j++) {//3
								
				seriesArray[j][i] = new XYSeries("Ciaa_"+(j+1));
				seriesArray[j][i].setMaximumItemCount(10);
				
				datasetArray[i].addSeries(seriesArray[j][i]);
			}
			chartArray[i] = ChartFactory.createXYLineChart("Sensor N°"+(i+1), null, null, datasetArray[i]);
			centerPanel.add(new ChartPanel(chartArray[i]));
		}
		window.add(topPanel,BorderLayout.NORTH);
		window.add(centerPanel, BorderLayout.CENTER);
		//configura boton conectar
		//ootro hilo paralelo que escuche el puerto
		connectButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(connectButton.getText().equals("Conectar"))
				{
					System.out.println("Click on Conectar");
					//listo para conectar al pueto
					chosenPort = SerialPort.getCommPort("COM2");
					chosenPort.setBaudRate(115200);

					chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
					
					if (chosenPort.openPort())
					{
						connectButton.setText("Desconectar");
						portList.setEnabled(false);
						portList2.setEnabled(false);
						portList3.setEnabled(false);
						portList4.setEnabled(false);
					}
					
					//crear un nuevo hilo que escuche el puerto y lo lleve al grafico
					Thread hilo = new Thread(){
						@Override public void run(){
							System.out.println("into the thread");
						Scanner scanner = new Scanner(chosenPort.getInputStream());
						//Scanner scanner2 = new Scanner(chosenPort2.getInputStream());
						//Scanner scanner3 = new Scanner(chosenPort3.getInputStream());
				
						//while (scanner.hasNextLine() && scanner2.hasNextLine() && scanner3.hasNextLine()) {
							while (scanner.hasNextLine()) {
							try {
								String line = scanner.nextLine();
								String[] parts = line.split(",");
							
								
								
								for (int j = 0; j < chartArray.length; j++) 
								{
									int number = Integer.parseInt(parts[j]);
									seriesArray[0][j].add(x, number);
									seriesArray[1][j].add(x,number+5);
									seriesArray[2][j].add(x,number+10);
									seriesArray[3][j].add(x,number+15);
								}
								
								x++;
								
								window.repaint();
							} catch (Exception e2) {
								// TODO: handle exception
							}
						}
						scanner.close();
						//scanner2.close();
						//scanner3.close();
						}
					};
					
					hilo.start();
					
				}
				else
				{
					//desconetar del puerto
					chosenPort.closePort();
					//chosenPort2.closePort();
					//chosenPort3.closePort();
					portList.setEnabled(true);
					portList2.setEnabled(true);
					portList3.setEnabled(true);
					portList4.setEnabled(true);
					connectButton.setText("Conectar");
					//series clear
					for (int i = 0; i < seriesArray.length; i++) {
						for (int j = 0; j < seriesArray[i].length; j++) {
							seriesArray[i][j].clear();
						}
					}
					x = 0;
				}
			}
		});
		
		//mostrar panatalla
		window.setVisible(true);
	}

}
