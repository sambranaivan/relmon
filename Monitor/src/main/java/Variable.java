import java.util.ArrayList;


public class Variable {

	//attributes
	double valor;
	public ArrayList<Anomalia> anomalias;


	//constructor
	public Variable( double unValor){
		valor = unValor;
	}
	
	//add anomalias and resultadosAnomalias;
	public void addAnomaly(double minData, double maxData){
		Anomalia unAnomalia = new AnomaliaMin(minData);
		anomalias.add(unAnomalia);
		unAnomalia = new AnomaliaMax(maxData);
		anomalias.add(unAnomalia);
	} 
	
}


