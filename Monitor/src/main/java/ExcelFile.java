


//Find jar from here "http://poi.apache.org/download.html"
import java.awt.Label;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.*;
//import jdk.internal.org.objectweb.asm.util.Printer;


import  org.apache.poi.hssf.usermodel.HSSFSheet;
	import  org.apache.poi.hssf.usermodel.HSSFWorkbook;
	import  org.apache.poi.hssf.usermodel.HSSFRow;
	import  org.apache.poi.hssf.usermodel.HSSFCell;


public class ExcelFile {
	//attributes
        static HSSFSheet sheetName;
        static ArrayList<String> puertos;

        
            //Constructor: para realizar las etiquetas de inicio
            public ExcelFile (ArrayList<String> Hojas) {
                puertos = Hojas;
            } //Cierre del constructor
        
        

        //Carga una fila del excel con los valores recibidos
        private static HSSFRow cargarRow(HSSFRow p_row, String p_dato){
            //divide the string in parts of an array
            ArrayList<String> data = new ArrayList<String>(Arrays.asList(p_dato.split(",")));
            int n = data.size();
            for (int i = 0; i < n; i++) {
                p_row.createCell(i).setCellValue(data.get(i));
            }

            return p_row;
        }
        
        //crea las etiquetas de cada sheet y encabezado
        private static void CrearEtiquetas(File excelFile, String filename) throws IOException{
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFRow rowhead = null;
            
            for (String item : puertos) {
                sheetName = workbook.createSheet(item);
                rowhead= sheetName.createRow((short)0);
                for(int i = 0; i<= 12; i++)
                {
                	rowhead.createCell(i).setCellValue("SENSOR "+i);
                }
                
                writeSheetFiles(workbook, filename);
                sheetName = null;
            }
            workbook.close();
            
        }
        
        
        
	//C�digo que agrega un dato en el excel
	public static void appendFiles(File excelFile, String filename, String p_dato, String Hoja) throws IOException{
        InputStream excelStream = null;
          
            excelStream = new FileInputStream(excelFile);
            // High level representation of a workbook.
            // Representaci�n del m�s alto nivel de la hoja excel.
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(excelStream);
            // We chose the sheet is passed as parameter. 
            // Elegimos la hoja que se pasa por par�metro.
            //HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(Integer.parseInt(Hoja));

            sheetName = hssfWorkbook.getSheet(Hoja);
            
            // I get the last number of rows occupied on the sheet
            // Obtengo el último n�mero de filas ocupadas en la hoja
            //int rows = hssfSheet.getLastRowNum();        
            int lastRow = sheetName.getLastRowNum() + 1;
            // We create the new sheet we are going to use.
            // Creamos la hoja nueva que vamos a utilizar.
            HSSFRow row = sheetName.createRow((short)lastRow);
            //Appending a row   
            row = cargarRow(row, p_dato);
            writeSheetFiles(hssfWorkbook,  filename);
            
	}
	
        
	
	//C�digo que escribe por primera vez un dato en el excel
	public static void writeSheetFiles(HSSFWorkbook workbook, String filename){
            FileOutputStream fileOut;
            
            File fil = new File(filename);
            fil.setWritable(true, false);
		try {
					
                    fileOut = new FileOutputStream(fil);
                    
	            workbook.write(fileOut);
	            fileOut.close();
	            workbook.close();
	            System.out.println("Write Done on "+filename);

	        } catch ( Exception ex ) {
	            System.out.println(ex);
	        }
        }
	
	    public void CargarExcel(String p_dato, String p_ruta,String Hoja) {
	        try {
                 
	            
	            String filename = p_ruta+".xls" ;
	            File excelFile = new File(filename);
	            
	            
                   
	            if (!(excelFile.exists())){
                        
	            	//En Archivo Excel es nuevo
	            	  System.out.println("El fichero " + excelFile + " es nuevo");
                          
                          CrearEtiquetas(excelFile,filename);
                          
                          System.out.println("Etiquetas creadas");                                           
                                    
	            }
	            
                appendFiles(excelFile, filename, p_dato, Hoja);
                
                	         
	        } catch ( Exception ex ) {
	            System.out.println(ex);
	        }
	    
	}	
	
}
