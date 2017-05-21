import com.fazecast.jSerialComm.SerialPort;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.services.sheets.v4.Sheets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

public class Quickstart {
	/** Relee*/
	
	static SerialPort chosenPort;
	static SerialPort chosenPort2;
	static SerialPort chosenPort3;
	static int cantidad_puertos = 4;
	static int cantidad_sensores = 13;

	static int x = 0;
	
    /** Application name. */
    private static final String APPLICATION_NAME =
        "Google Sheets API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/sheets.googleapis.com-java-quickstart
     */
    private static final List<String> SCOPES =
        Arrays.asList(SheetsScopes.SPREADSHEETS);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
            Quickstart.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Sheets API client service.
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void main(String[] args) throws IOException {
////////////////////////////////////////////////////////
///////////////////////////////////////////////////////
////////////////////////////////////////////////////////
////////////////////////////////////////////////////////
////////////////////////////////////////////////////////
////////codigo del monitor////////////////////////
////////////////////////////////////////////////////////
////////////////////////////////////////////////////////
    	
    	// creo una ventana
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
    			
    			//crear un menu de seleccion de puertos y el boton para empezar
    			final JComboBox<String> portList = new JComboBox<String>();
    			final JComboBox<String> portList2 = new JComboBox<String>();
    			final JComboBox<String> portList3 = new JComboBox<String>();
    			final JComboBox<String> portList4 = new JComboBox<String>();
    			final JButton connectButton = new JButton("Conectar");
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
    			
    			
    			final XYSeries[][] seriesArray = new XYSeries[cantidad_puertos][cantidad_sensores];
    			final XYSeriesCollection[] datasetArray = new XYSeriesCollection[cantidad_sensores];
    			final JFreeChart[] chartArray = new JFreeChart[cantidad_sensores];
    			
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
    	
    	
    	
    	
    	
////////////////////////////////////////////////////////
///////////////////////////////////////////////////////
////////////////////////////////////////////////////////
////////////////////////////////////////////////////////
////////////////////////////////////////////////////////
////////codigo del monitor////////////////////////
////////////////////////////////////////////////////////
////////////////////////////////////////////////////////
        // Build a new authorized API client service.
        Sheets service = getSheetsService();

        // Prints the names and majors of students in a sample spreadsheet:
        // https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
        // https://docs.google.com/spreadsheets/d/1Tc0H58Fl2HN5RF9_6CtdjuGQRnW-Mix2P_Gv3kNY85c/edit#gid=0
        String spreadsheetId = "1Tc0H58Fl2HN5RF9_6CtdjuGQRnW-Mix2P_Gv3kNY85c";
        String range = "Class Data!A2:E";
 
        ValueRange response = service.spreadsheets().values()
            .get(spreadsheetId, range)
            .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.size() == 0) {
            System.out.println("No data found.");
        } else {
          System.out.println("LEctura");
          
          for (List row : values) {
            // Print columns A and E, which correspond to indices 0 and 4.
            System.out.printf("%s, %s\n", row.get(0), row.get(1));
          }
        }
        
        
        ///write
        String rango = response.getRange();
        ///crear una lista de objetos a escribir
        List<List<Object>> valores = new ArrayList<>();
        
        ///creo el objeto
        List<Object> dato1 = new ArrayList<>();
        dato1.add("objC");
        dato1.add("OBJD");
        
        valores.add(dato1);
        
        //crear el valuerange y modificar la configuracion
        ValueRange rangodevalores = new ValueRange();
        System.out.println(response.getRange());
        rangodevalores.setMajorDimension("ROWS");
        rangodevalores.setRange(rango);
        rangodevalores.setValues(valores);
        
        ///ejecutar la peticion
        service.spreadsheets()
        .values()
        .append(spreadsheetId, rango, rangodevalores)
        .setValueInputOption("RAW")
        .execute();
        
        
        
        
    }


}