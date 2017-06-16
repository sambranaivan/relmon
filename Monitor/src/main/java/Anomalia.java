
public  abstract class Anomalia {
	//attributes
	double refValue;
	public ResultadoAnomalia resultado;
	
	//constructor
	public Anomalia(double p_data) {
		refValue = p_data;	
	}
	
	//abstract methods 
	//abstract public boolean check(double dataRef);
	abstract public ResultadoAnomalia check(double p_data);
	
	//add an ResultadoAnomalia
	public void add(ResultadoAnomalia p_resultado){
		this.resultado = p_resultado;
	}

	
	
	
}
