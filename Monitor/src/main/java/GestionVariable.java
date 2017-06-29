import java.util.ArrayList;

public class GestionVariable {

	//attributes
	private Variable variableUno;
	private ArrayList<Anomalia> tiposAnomaliasConectorAbierto;
	private ArrayList<Anomalia> tiposAnomaliasConectorCerrado;
	private ArrayList<Anomalia> tiposAnomaliasTemperatura;

	//Constructor
	public GestionVariable(){
		
	}
	 
	//carga todos los tipos de anomalias en un arrayList
	public void cargarTipoAnomalias(String p_nombreVariable, double p_minData, double p_maxData){
		
		Anomalia anomaliaMin = new AnomaliaMin(p_minData);
		Anomalia anomaliaMax = new AnomaliaMax(p_maxData);
		
		switch(p_nombreVariable){
			case "ConectorAbierto": tiposAnomaliasConectorAbierto.add(anomaliaMin);
									tiposAnomaliasConectorAbierto.add(anomaliaMax);
			break;
			case "ConectorCerrado": tiposAnomaliasConectorCerrado.add(anomaliaMin);
									tiposAnomaliasConectorCerrado.add(anomaliaMax);
			break;
			case "Temperatura": tiposAnomaliasTemperatura.add(anomaliaMin);
								tiposAnomaliasTemperatura.add(anomaliaMax);
			break;
		}
	}
	
	//carga ArrayList anomalias de una Variable, con resultados posee anomalias
	public void cargarAnomalia(double p_data, String p_nombreVariable){
		
		
		Variable unaVar = new Variable(p_data, p_nombreVariable);
		
		switch(p_nombreVariable){
			case "ConectorAbierto": unaVar.check(this.tiposAnomaliasConectorAbierto);
			break;
			case "ConectorCerrado": unaVar.check(this.tiposAnomaliasConectorCerrado);
			break;
			case "Temperatura": unaVar.check(this.tiposAnomaliasTemperatura);
			break;
		}
		this.variableUno = unaVar; 
		
		//grabar anomalia en un excel
		//this.cargarEnExcel();
		
	}
	
	//guarda anomalia en un ExcelFile
	public void cargarEnExcel(String valor, String p_ruta, String p_nombreVariable){
		
		//esto se debe instanciar una sola vez en el main
		ArrayList<String> nombreVariables = new ArrayList<String>(); 
		nombreVariables.add("ConectorAbierto");
		nombreVariables.add("ConectorCerrado");
		nombreVariables.add("ConectorTemperatura");
		ExcelFile unDoc = new ExcelFile(nombreVariables);

		
		//esto si es codigo propio de este metodo
		//en ExcelFile debe crear la primer etiqueta
		unDoc.CargarExcelAnomalia(p_ruta, this.variableUno);		 
		
	}
	
}
