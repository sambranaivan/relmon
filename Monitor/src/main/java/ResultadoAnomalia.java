
public class ResultadoAnomalia {

	private boolean flag;
	private String mensaje;
	
	public ResultadoAnomalia(){
		this.flag = false;
	}
	
	//setters  and gettes
	public String getMensaje(){
		return this.mensaje;
	}
	
	public boolean getFlag(){
		return this.flag;
	}
	
	public void add(boolean p_flag, String p_mensaje){
		flag = p_flag;
		mensaje = p_mensaje;
	}
	
}
