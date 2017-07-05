import java.util.ArrayList;


public class Variable {

	//attributes
	private String nombreVariable;
	private double valor;
	private ArrayList<Anomalia> anomalias = new ArrayList<Anomalia>(); 

	//setter and getters
	public String getNombreVariable(){
		return this.nombreVariable;
	}
	
	public double getValor(){
		return this.valor;
	}
	
	public ArrayList<Anomalia> getAnomalias(){
		return this.anomalias;
	}
	
	public void setValor(double p_valor){
		this.valor = p_valor;
	}
	
	public void setAnomalias(ArrayList<Anomalia> p_anomalias){
		this.anomalias = p_anomalias;
	}
	
	///NULL POINT EXCEPTION
	public void setAnomaliaMin(AnomaliaMin p_anomMin){
		this.getAnomalias().add(p_anomMin);
	}
	
	public void setAnomaliaMax(AnomaliaMax p_anomMax){
		this.getAnomalias().add(p_anomMax);
	}
	
	//constructor
	public Variable(double p_valor, String p_nombreVariable){
		valor = p_valor;
		nombreVariable = p_nombreVariable;
	}
	
	public Variable(String p_nombreVariable) {
		this.nombreVariable = p_nombreVariable;
	}

	//check general
	public void check(){
		Anomalia unAnomalia;
		ResultadoAnomalia unResultado;
		for (Anomalia a : this.getAnomalias()) {
	    	  a.check(this.valor, this.getNombreVariable());
		}
	}
	
	
}

