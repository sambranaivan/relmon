
public class AnomaliaMax extends Anomalia {
	
	//Constructor
	public AnomaliaMax(double p_data){
		super(p_data);
	}
	
	
	//check MAX anomaly
	public ResultadoAnomalia check(double p_data){ 
		ResultadoAnomalia r = new ResultadoAnomalia();
		if(super.refValue < p_data){
			r.add(true, "MAXIMO");
		}
		return r;
	}
	

	
}

