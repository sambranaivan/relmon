import java.util.ArrayList;
import java.util.Arrays;

public class GestionVariable {

	//attributes
	private ArrayList<Variable> variables;


	//Constructor
	public GestionVariable(){
		this.variables = new ArrayList<Variable>(); 
		Variable varConAbierto1 = new Variable("Conector Abierto 1",100);
		
		///ME TIRA NUUL POINTER EXCEPCION EN variable.
		varConAbierto1.setAnomaliaMin(new AnomaliaMin(30));
		varConAbierto1.setAnomaliaMax(new AnomaliaMax(100));
		this.variables.add(varConAbierto1);
		
		Variable varConCerrado1 = new Variable("Conector Cerrado 1", 100);
		varConCerrado1.setAnomaliaMin(new AnomaliaMin(400));
		varConCerrado1.setAnomaliaMax(new AnomaliaMax(600));
		this.variables.add(varConCerrado1);
		
		Variable varConAbierto2 = new Variable("Conector Abierto 2", 100);
		varConAbierto2.setAnomaliaMin(new AnomaliaMin(30));
		varConAbierto2.setAnomaliaMax(new AnomaliaMax(100));
		this.variables.add(varConAbierto2);
		
		Variable varConCerrado2 = new Variable("Conector Cerrado 2", 100);
		varConCerrado2.setAnomaliaMin(new AnomaliaMin(400));
		varConCerrado2.setAnomaliaMax(new AnomaliaMax(600));
		this.variables.add(varConCerrado2);
		
		Variable varConAbierto3 = new Variable("Conector Abierto 3", 100);
		varConAbierto3.setAnomaliaMin(new AnomaliaMin(30));
		varConAbierto3.setAnomaliaMax(new AnomaliaMax(100));
		this.variables.add(varConAbierto3);
		
		Variable varConCerrado3 = new Variable("Conector Cerrado 3", 100);
		varConCerrado3.setAnomaliaMin(new AnomaliaMin(400));
		varConCerrado3.setAnomaliaMax(new AnomaliaMax(600));
		this.variables.add(varConCerrado3);
		
		Variable varConAbierto4 = new Variable("Conector Abierto 4", 100);
		varConAbierto4.setAnomaliaMin(new AnomaliaMin(30));
		varConAbierto4.setAnomaliaMax(new AnomaliaMax(100));
		this.variables.add(varConAbierto4);
		
		Variable varConCerrado4 = new Variable("Conector Cerrado 4", 100);
		varConCerrado4.setAnomaliaMin(new AnomaliaMin(400));
		varConCerrado4.setAnomaliaMax(new AnomaliaMax(600));
		this.variables.add(varConCerrado4);
		
		Variable varConAbierto5 = new Variable("Conector Abierto 5", 100);
		varConAbierto5.setAnomaliaMin(new AnomaliaMin(30));
		varConAbierto5.setAnomaliaMax(new AnomaliaMax(100));
		this.variables.add(varConAbierto5);
		
		Variable varConCerrado5 = new Variable("Conector Cerrado 5", 100);
		varConCerrado5.setAnomaliaMin(new AnomaliaMin(400));
		varConCerrado5.setAnomaliaMax(new AnomaliaMax(600));
		this.variables.add(varConCerrado5);
		
		Variable varConAbierto6 = new Variable("Conector Abierto 6", 100);
		varConAbierto6.setAnomaliaMin(new AnomaliaMin(30));
		varConAbierto6.setAnomaliaMax(new AnomaliaMax(100));
		this.variables.add(varConAbierto6);
		
		Variable varConCerrado6 = new Variable("Conector Cerrado 6", 100);
		varConCerrado6.setAnomaliaMin(new AnomaliaMin(400));
		varConCerrado6.setAnomaliaMax(new AnomaliaMax(600));
		this.variables.add(varConCerrado6);
		
		//Variable varTemperatura = new Variable("Temperatura", 100);
		//varTemperatura.setAnomaliaMin(new AnomaliaMin(200));
		//varTemperatura.setAnomaliaMax(new AnomaliaMax(250));
		//this.variables.add(varTemperatura);
		
		System.out.println("...new Gestion Variable");
	}
	
	//setters y getters
	public ArrayList<Variable> getVariables(){
		return this.variables;
	}
	
	 
	//carga ArrayList anomalias de una Variable, con resultados posee anomalias
	public void checkVariables(String p_dato){
		System.out.println("--checkVariables: "+p_dato);
		//divide los 13 valores que vienen en String separados por comas
		 ArrayList<String> data = new ArrayList<String>(Arrays.asList(p_dato.split(",")));
		
		 //Check Resultados de anomalias
		 //** cambio (i < 13) por (i < variables.size())..
		 for(int i = 0; i < variables.size(); i++){
			 variables.get(i).setValor(Double.parseDouble(data.get(i)));
			 variables.get(i).check();
		 }
		
	}
	
	//guarda anomalia en un ExcelFile
	public void cargarEnExcel(String p_ruta){
		
		//El constructor necesita lso nombres de cada Varibale para una hoja diferente 
		ArrayList<String> nombreVariables = new ArrayList<String>();
		
		for(Variable var : this.getVariables()){
			//fix
			nombreVariables.add(var.getNombreVariable());
		}
		
		
		///nombreVariables LLega Vacio
		
		ExcelFile unDoc = new ExcelFile(nombreVariables);

		
		//carga cada variable en ExcelFile solamente si el resultado de la anomlia es true
		/// get.(0) no va...hacer con otro For
		for(Variable var : this.getVariables()){
		
				for(Anomalia ano: var.getAnomalias())
				{
					if(ano.getResultado().getFlag())
					{
						unDoc.CargarExcelAnomalia(p_ruta, var);
					}
				}			
				
			
		}
	}
	
}
