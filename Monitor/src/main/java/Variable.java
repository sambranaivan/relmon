import java.util.ArrayList;


public class Variable {

	//attributes
	private String nombreVariable;
	private ArrayList<Double> valores;
	private ArrayList<Anomalia> anomalias = new ArrayList<Anomalia>(); 

	//setter and getters
	public String getNombreVariable(){
		return this.nombreVariable;
	}
	
	public double getValor(){
		//retorna el ultimo valor almacenado, el mas actual
		return this.valores.get(this.getValores().size()-1);
	}
	
	public ArrayList<Double> getValores(){
		return this.valores;
	}
	
	public ArrayList<Anomalia> getAnomalias(){
		return this.anomalias;
	}
	
	public void setValor(double p_valor){
		this.valores.add(p_valor);
		
		//solo guarda hasta 100 valores, arquitectura FIFO
		if(this.getValores().size() > 100){
			this.getValores().remove(0);
		}
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
	public Variable(String p_nombreVariable) {
		this.nombreVariable = p_nombreVariable;
		this.valores = new ArrayList<Double>();
	}

	//check general
	public void check(){
		for (Anomalia a : this.getAnomalias()) {
	    	  a.check(this.getValor(), this.getNombreVariable());
		}
	}
	
	
}

