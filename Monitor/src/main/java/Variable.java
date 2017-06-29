import java.util.ArrayList;


public class Variable {

	//attributes
	private String nombreVariable;
	private double valor;
	private ArrayList<Anomalia> anomalias;

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
	
	public void setAnomalias(ArrayList<Anomalia> p_anomalias){
		this.anomalias = p_anomalias;
	}
	
	//constructor
	public Variable(double p_valor, String p_nombreVariable){
		valor = p_valor;
		nombreVariable = p_nombreVariable;
	}
	
	//check general
	public void check(ArrayList<Anomalia> p_tiposAnomalias){
		
		Anomalia unAnomalia;
		ResultadoAnomalia unResultado;
		for (Anomalia a : p_tiposAnomalias) {
	    	  unResultado = a.check(this.valor, this.getNombreVariable());
	    	  
	    	  //Solo en caso de haber una anomalia, se registra una con su resultado
	    	  if(unResultado != null){
	    		  unAnomalia = a;
	    		  unAnomalia.setResultadoAnomalia(unResultado);
	    		  this.getAnomalias().add(unAnomalia);
	    	  }
		}
	}
	
	
}

