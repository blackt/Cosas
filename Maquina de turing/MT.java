import java.util.*;
import java.io.*;

public class MT {
	
	public DobleLista<String> lista=new DobleLista<String>();
	private String Estado;
	private boolean parada=false;
	private String [] alfabeto;
	private String [] finales;
	private String [] Estados;
	private String Funcion;
	
	Map<String,HashMap<String,String>> estados= new HashMap<String, HashMap<String,String>>();
	
	public  void Entrada(String a){
		
		HashMap<String,String> temp=new HashMap<String, String>();
		int posicion=0;
		try {
			Scanner lector=new Scanner(new File(a));
			//datos
			for(String dato:lector.nextLine().split("[ ]+"))
				lista.añadirFinal(dato);
			
			//inicio
				Estado=lector.nextLine();
				finales= lector.nextLine().split("[ ]+");
			// Estados
				Estados=lector.nextLine().split("[ ]+");
			for(String dato:Estados)
				estados.put(dato, null);
				//alfabeto	
				alfabeto= lector.nextLine().split("[ ]+");
			for(int i=0;i<Estados.length;i++){
				if(lector.hasNext())
					for(String dato:lector.nextLine().split(",")){
						temp.put(alfabeto[posicion], dato.trim());
						posicion++;
					}
				posicion=0;
				estados.put(Estados[i], temp);
				temp=null;
				temp=new HashMap<String, String>();
			}	
			
				
		} catch (FileNotFoundException e) {
		
		}
		
	}
	
	public  boolean siguiente(){
		Movimientos( lista.Actual().getDatos() );
	return parada;
	}
	
	public void Movimientos(String Entrada){
		
	//System.out.println(Arrays.toString(Lista())+" "+lista.posicion());
	Funcion=estados.get(Estado).get(Entrada);
	if(Funcion.equals("")){
		System.out.println("Error");
		parada=true;
		return;
		}
	Estado=Funcion.substring(0, 1);
	lista.Actual().setDatos(Funcion.substring(1, 2));
	String Señal=Funcion.substring(2,3);
	//System.out.println(Estado+" "+Entrada+" "+Funcion+" "+Señal);
	if(Señal.equals("i")){
		if(lista.hayAnterior())
			lista.Anterior();
		else{
			lista.añadirInicio("b");
			lista.Anterior();
		}	
	}
	else if(Señal.equals("d")){
		
		if(lista.haySiguiente())
			lista.Siguiente();
		else{
			lista.añadirFinal("b");
			lista.Siguiente();
		}			
	}
	else
		parada=true;		
	}
	
	public String[] Lista(){
		return lista.toArray();		
	}
	
	public int posicion(){
		return lista.posicion();
	}
	
	public String[] Estados(){
		return Estados;
	}
	public String Estado(){
		return Estado;
	}
	
	public boolean aceptacion(){
		for(String n:finales)
			if(n.equals(Estado))
				return parada;
		return false;
		}
	public String Funcion(){
		return Funcion;
		}
		
	
}
