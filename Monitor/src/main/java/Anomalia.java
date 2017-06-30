
public  abstract class Anomalia {
	//attributes
	double refValue;
	public ResultadoAnomalia resultado;
	private String date;
	
	//constructor
	public Anomalia(double p_data) {
		refValue = p_data;	
	}
	
	//setters
	public void setResultadoAnomalia(ResultadoAnomalia p_resultadoAnomalia){
		resultado = p_resultadoAnomalia;
	}
	public void setFecha(String p_date){
		this.date = p_date;
	}
	
	public String getFecha(){
		return this.date;
	}
	
	public ResultadoAnomalia getResultado(){
		return this.resultado;
	}
	
	//abstract methods 
	//abstract public boolean check(double dataRef);
	abstract public ResultadoAnomalia check(double p_data, String p_nombreVariable);
	
	//add an ResultadoAnomalia
	public void add(ResultadoAnomalia p_resultado){
		this.resultado = p_resultado;
	}
	
	
}
