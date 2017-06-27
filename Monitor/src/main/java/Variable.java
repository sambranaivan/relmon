import java.util.ArrayList;


public class Variable {

	//attributes
	private double valor;
	private ArrayList<Anomalia> anomalias;

	public double getValor(){
		return this.valor;
	}
	
	public ArrayList<Anomalia> getAnomalias(){
		return this.anomalias;
	}
	
	public void setAnomalias(ArrayList<Anomalia> p_anomalias){
		this.anomalias = p_anomalias;
	}
	
	//constructor
	public Variable( double unValor){
		valor = unValor;
	}
	
	//check general
	public void check(ArrayList<Anomalia> p_tiposAnomalias){
		for (Anomalia a : p_tiposAnomalias) {
	    	  a.resultado = a.check(this.valor);
			}
		this.setAnomalias(p_tiposAnomalias);
	}
	
	
}

