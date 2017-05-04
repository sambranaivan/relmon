import java.awt.BorderLayout;
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
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.fazecast.jSerialComm.SerialPort;

public class SensorGraph {
	
	static SerialPort chosenPort;
	static int x = 0;

	public static void main(String[] args) {
		// creo una ventana
		System.out.println("Begin");
		JFrame window = new JFrame();
		window.setTitle("Monitor de Rele");
		window.setSize(600,400);
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//crear un menu de seleccion de puertos y el boton para empezar
		JComboBox<String> portList = new JComboBox<String>();
		JButton connectButton = new JButton("Conectar");
		JPanel topPanel = new JPanel();
		topPanel.add(portList);
		topPanel.add(connectButton);
		window.add(topPanel,BorderLayout.NORTH);
		
		
		//cargar los puertos disponibles en el combo
		SerialPort[] portNames = SerialPort.getCommPorts();
		for (int i = 0; i < portNames.length; i++) {
			portList.addItem(portNames[i].getSystemPortName());
		}
		
		// crear una linea de graficos
		XYSeries series = new XYSeries("Light Sensor Readings");
		XYSeriesCollection dataset = new XYSeriesCollection(series);
		JFreeChart chart = ChartFactory.createXYLineChart("Light Sensor Readings", "Tiempo en Seconds", "ADC VAlue", dataset);
		window.add(new ChartPanel(chart), BorderLayout.CENTER);
		
		
		//configura boton conectar
		//ootro hilo paralelo que escuche el puerto
		connectButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(connectButton.getText().equals("Conectar"))
				{
					System.out.println("Click on Conectar");
					//listo para conectar al pueto
					chosenPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
					chosenPort.setBaudRate(115200);
					chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
					if (chosenPort.openPort()) 
					{
						connectButton.setText("Desconectar");
						portList.setEnabled(false);
					}
					
					//crear un nuevo hilo que escuche el puerto y lo lleve al grafico
					Thread hilo = new Thread(){
						@Override public void run(){
							System.out.println("into the thread");
						Scanner scanner = new Scanner(chosenPort.getInputStream());
				
						while (scanner.hasNextLine()) {
							try {
								String line = scanner.nextLine();
								System.out.println(line);
								int number = Integer.parseInt(line);
								series.add(x++, number);
								window.repaint();
							} catch (Exception e2) {
								// TODO: handle exception
							}
						}
						scanner.close();
						}
					};
					hilo.start();
					
				}
				else
				{
					//desconetar del puerto
					chosenPort.closePort();
					portList.setEnabled(true);
					connectButton.setText("Conectar");
					series.clear();
					x = 0;
				}
			}
		});
		
		//mostrar panatalla
		window.setVisible(true);
	}

}
