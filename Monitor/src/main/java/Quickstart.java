import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import com.google.api.services.drive.Drive.Changes.GetStartPageToken;

public class Quickstart {
	
	static SerialPort chosenPort;
	static SerialPort chosenPort2;
	static SerialPort chosenPort3;
	static int cantidad_puertos = 3;
	static int cantidad_sensores = 13;
	final static GoogleSheets Gsheets = new GoogleSheets();
	final static DriveApi DriveApi = new DriveApi();
	static int x = 0;

	public static void main(String[] args) throws IOException {
	
		Gsheets.init();
		DriveApi.init();
		
		/**/
		/**/
		/**/
		// CONFIGURAR PANTALLA
				System.out.println("Begin");
				final JFrame window = new JFrame();
				window.setTitle("Monitor de Rele");
				Dimension UserScreen = Toolkit.getDefaultToolkit().getScreenSize();
				int ScreenWidth = (int) UserScreen.getWidth();
				int ScreenHeight = (int) UserScreen.getHeight();
				window.setSize(ScreenWidth, ScreenHeight);
				window.setLayout(new BorderLayout());
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
				/**/
				/**/
				/**/
		//crear un menu de seleccion de puertos y el boton para empezar
		final JComboBox<String> portList = new JComboBox<String>();
		final JComboBox<String> portList2 = new JComboBox<String>();
		final JComboBox<String> portList3 = new JComboBox<String>();
		final JButton connectButton = new JButton("Conectar");
		JPanel topPanel = new JPanel();
		JPanel centerPanel = new JPanel(new GridLayout(3, 5));
		topPanel.add(portList);
		topPanel.add(portList2);
		topPanel.add(portList3);		
		topPanel.add(connectButton);
		
		JButton testButton = new JButton("TEST");
		topPanel.add(testButton);
		
		
		/**/
		/**/
		/*CARGA LOS PUERTOS/**/
		/**/
		SerialPort[] portNames = SerialPort.getCommPorts();
		for (int i = 0; i < portNames.length; i++) {
			portList.addItem(portNames[i].getSystemPortName());
			portList2.addItem(portNames[i].getSystemPortName());
			portList3.addItem(portNames[i].getSystemPortName());
		}
		
		/**/
		/**/
		/* CREO LA SERIES DE VALORES*/
		/**/		
		final XYSeries[][] seriesArray = new XYSeries[cantidad_puertos][cantidad_sensores];
		XYSeriesCollection[] datasetArray = new XYSeriesCollection[cantidad_sensores];
		final JFreeChart[] chartArray = new JFreeChart[cantidad_sensores];
		
		//VINCULO LAS SERIE DE DATOS CON LOS DATASET PARA GRAFICAR
		for (int i = 0; i < datasetArray.length; i++) {//13
			
			//creo un data ser por cada sensor
			datasetArray[i] = new XYSeriesCollection();
			
			for (int j = 0; j < seriesArray.length; j++) {//3
				//creo una serie por cada puerto			
				seriesArray[j][i] = new XYSeries("Ciaa_"+(j+1));//creo la serie con el nombre CIAA_n
				seriesArray[j][i].setMaximumItemCount(10);//limito a 10 la informacion ah graficar
				
				datasetArray[i].addSeries(seriesArray[j][i]);//Vinculo la serie a cada dataset
			}
			//Creo un grafico de lineas por cada sensor
			chartArray[i] = ChartFactory.createXYLineChart("Sensor N°"+(i+1), null, null, datasetArray[i]);
			//agrego al panel cada grafico
			centerPanel.add(new ChartPanel(chartArray[i]));
		}
		//agrego el panel al panel la botonera
		window.add(topPanel,BorderLayout.NORTH);
		//agrego al panel los graficos
		window.add(centerPanel, BorderLayout.CENTER);
		
		//configura boton conectar
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

					chosenPort2 = SerialPort.getCommPort(portList2.getSelectedItem().toString());
					chosenPort2.setBaudRate(115200);
					chosenPort2.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

					chosenPort3 = SerialPort.getCommPort(portList3.getSelectedItem().toString());
					chosenPort3.setBaudRate(115200);
					chosenPort3.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

					
					if (chosenPort.openPort() && chosenPort2.openPort() && chosenPort3.openPort())
					{
						connectButton.setText("Desconectar");
						//APAGO LOS COMBOBOX
						portList.setEnabled(false);
						portList2.setEnabled(false);
						portList3.setEnabled(false);
					}
					
					//crear un nuevo hilo que escuche el puerto y lo lleve al grafico
					Thread hilo = new Thread(){
						@Override public void run(){
							System.out.println("Dentro del Hilo");
						Scanner scanner = new Scanner(chosenPort.getInputStream());
						Scanner scanner2 = new Scanner(chosenPort2.getInputStream());
						Scanner scanner3 = new Scanner(chosenPort3.getInputStream());
						
							while (scanner.hasNextLine() && scanner2.hasNextLine() && scanner3.hasNextLine() ) {
							try {
								String line = scanner.nextLine();
								String line2 = scanner2.nextLine();
								String line3 = scanner3.nextLine();
								//System.out.println("CIAA_1 "+line);
								//System.out.println("CIAA_2 "+line2);
								//System.out.println("CIAA_3 "+line3);
								
								//divido el string
								//Convierto la cadena de datos en un Arraylist
								ArrayList<String> aList= new ArrayList<String>(Arrays.asList(line.split(",")));
								ArrayList<String> aList2= new ArrayList<String>(Arrays.asList(line2.split(",")));
								ArrayList<String> aList3= new ArrayList<String>(Arrays.asList(line3.split(",")));
							
								
								
								for (int j = 0; j < chartArray.length; j++) 
								{
									
									//CONVIERTO LOS VALORES EN NUMEROS Y LOS CARGO A LA SERIE DE DATOS
									double number = Double.parseDouble(aList.get(j));
									double number2 = Double.parseDouble(aList2.get(j));
									double number3 = Double.parseDouble(aList3.get(j));
									seriesArray[0][j].add(x, number);
									seriesArray[1][j].add(x,number2);//simul
									seriesArray[2][j].add(x,number3);//simul
									
								}
								
								///ENVIO LOS DATOS A GSHETTS
								updateFile();
								Gsheets.insert(aList,"CIAA_1!A1:N");
								Gsheets.insert(aList2,"CIAA_2!A1:N");
								Gsheets.insert(aList3,"CIAA_3!A1:N");
								
								///Grabar en EXcel
								//excel.insert(datos,path,1)
								
								//INCREMENTO CONTADOR
								x++;
								
								//LIMPIO LA PANTALLA
								window.repaint();
							} catch (Exception e2) {
								// TODO: handle exception
							}
						}
							//CIERRO LOS SCANNER DE PUERTOS
							scanner.close();
							scanner2.close();
							scanner3.close();
						}
					};
					
					hilo.start();
					
				}
				else
				{
					//desconetar del puerto
					chosenPort.closePort();
					chosenPort2.closePort();
					chosenPort3.closePort();
					
					//HABILITO LOS COMBOBOX
					portList.setEnabled(true);
					portList2.setEnabled(true);
					portList3.setEnabled(true);
					connectButton.setText("Conectar");
					//LIMPIO LOS GRAFICOS
					for (int i = 0; i < seriesArray.length; i++) {
						for (int j = 0; j < seriesArray[i].length; j++) {
							seriesArray[i][j].clear();
						}
					}
					//REINICIO EL CONTADOR DE GRAFICO
					x = 0;
				}
			}
		});
		
		//MOSTRAR PANTALLA
		window.setVisible(true);
	}

	public static String getTitulo()
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		// Get the date today using Calendar object.
		Date today = Calendar.getInstance().getTime();        
		// Using DateFormat format method we can create a string 
		// representation of a date with the defined format.
		String reportDate = df.format(today);
		return reportDate+"_Registro Rele";
	}
	
	
	public static void updateFile() throws IOException, GeneralSecurityException
	{
		try {
			//DriveApi.createFolder("Registro de Reles");
			
			String actualFile = DriveApi.fileExist(getTitulo()); 
			if( actualFile != null)
			{
				System.out.println("El Archivo<<"+getTitulo()+">>Ya existe");
			}
			else
			{
				actualFile = Gsheets.getSheet(getTitulo());
				DriveApi.moveToFolder(actualFile);
			}
			
			Gsheets.setActualFile(actualFile);
			
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (GeneralSecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
}
