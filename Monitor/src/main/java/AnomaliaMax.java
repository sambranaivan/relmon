import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AnomaliaMax extends Anomalia {
	
	//Constructor
	public AnomaliaMax(double p_data){
		super(p_data);
	}
	
	
	//check MAX anomaly
	public ResultadoAnomalia check(double p_data, String p_nombreVariable){ 
		String msg;
		double diff;
		ResultadoAnomalia r = new ResultadoAnomalia();
		
		//get current date
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String strDate = sdf.format(cal.getTime());
		
		if(super.refValue < p_data){
			diff = p_data - super.refValue;
			msg = "<log date="+ strDate +"><type>MAX</type><variable>"+p_nombreVariable+"</variable><ref_value>"+super.refValue +"</ref_value><actual_value>"+p_data+"</actual_value><diff>"+diff+"<diff></log>";
			r.add(true, msg);
		}
		this.setFecha(strDate);
		this.setResultadoAnomalia(r);
		return r;
	}
	

	
}

