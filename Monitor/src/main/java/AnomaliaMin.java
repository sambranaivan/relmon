
public class AnomaliaMin extends Anomalia {

	//Constructor
	public AnomaliaMin(double p_data){
		super(p_data);
	}
	
	
	//check MIN anomaly
	public ResultadoAnomalia check(double p_data){
		ResultadoAnomalia r = new ResultadoAnomalia();
		if(super.refValue > p_data){
			r.add(true, "MINIMO");
		}
		return r;
	}
	

	
}
