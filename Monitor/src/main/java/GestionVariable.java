import java.util.ArrayList;

public class GestionVariable {

	//attributes
	private Variable variableUno;
	private ArrayList<Anomalia> tiposAnomalias;

	//Constructor
	public GestionVariable(double p_minData, double p_maxData){
		this.cargarTipoAnomalias(p_minData, p_maxData);
	}
	 
	//carga todos los tipos de anomalias en un arrayList
	public void cargarTipoAnomalias(double p_minData, double p_maxData){
		Anomalia unAnomalia = new AnomaliaMin(p_minData);
		tiposAnomalias.add(unAnomalia);
		unAnomalia = new AnomaliaMax(p_maxData);
		tiposAnomalias.add(unAnomalia);
	}
	
	//carga ArrayList anomalias de una Variable, con resultados posee anomalias
	public void cargarAnomalia(double p_data){
		
		
		Variable unaVar = new Variable(p_data);
		unaVar.check(this.tiposAnomalias);
		this.variableUno = unaVar; 

	}
	
}
