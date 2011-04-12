
public class DobleLista<T> {
	private Nodo<T> Actual;
	private Nodo<T> Final;
	private Nodo<T> Inicial;
	private int posicion=0;
	
	public void añadirInicio(T Dato){
		
		if(Inicial==null){
			Inicial= new Nodo<T>(Dato);
			Final=Inicial;
			Actual=Inicial;
			posicion=0;
		}			
		else{
		Nodo<T> Inicio=new Nodo<T>(Dato,null,Inicial);
		Inicial.setAnterior(Inicio);
		Inicial=Inicio;
		posicion++;
		}
	}
	
	public void añadirFinal(T Dato){
		
		if(Final==null){
			Final=new Nodo<T>(Dato);
			Inicial=Final;
			Actual=Final;
			posicion=0;
		}
		else{
			Nodo<T> Fin=new Nodo<T>(Dato,Final,null);
			Final.setSiguiente(Fin);
			Final=Fin;
		}	
	}

	public Nodo<T> Actual(){		
		return Actual;
		}
	
	public void Siguiente(){
		if (haySiguiente()){
			Actual=Actual.Siguiente();
			posicion++;
			}
	return;			
	}
	
	public void Anterior(){
		if (hayAnterior()){
			Actual=Actual.Anterior();
			posicion--;
		}
		return;
	}
	
	public boolean haySiguiente(){		
	return Actual.Siguiente()!=null;			
	}
	
	public boolean hayAnterior(){		
		return Actual.Anterior()!=null;
	}
	
	public int posicion(){
		return posicion;
	}
	
	public int longuitud(){
		int l=1;
		for(Nodo<T> Init=Inicial;Init.Siguiente()!=null;Init=Init.Siguiente())
			l++;
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public String[] toArray(){
		String[] resultado =new String[longuitud()];
		int l=0;
		Nodo<T> Init;
		for( Init=Inicial;Init.Siguiente()!=null;Init=Init.Siguiente()){
			resultado[l]= (String) Init.getDatos();
			
			++l;
			}
		resultado[l]= (String) Init.getDatos();
		return resultado;
	} 
	
}





class Nodo <T>{
	private T Dato;
	private Nodo<T> Anterior;
	private Nodo<T> Siguiente;
	public Nodo(T Dato,Nodo<T> Anterior,Nodo<T> Siguiente){
		this.Dato=Dato;
		this.Anterior=Anterior;
		this.Siguiente=Siguiente;		
	}
	public Nodo(T Dato){
		this(Dato,null,null);
	}
	
	public Nodo(T Dato,Nodo<T> Anterior){
		this(Dato,Anterior,null);
	}
	
	
	public Nodo <T> Siguiente(){	
		return Siguiente;
	}
	
	public Nodo<T> Anterior(){
		return Anterior;
	}
	
	public void setSiguiente(Nodo <T> nodo ){	
		Siguiente=nodo;
	}
	
	public void setAnterior( Nodo<T> nodo ){
		Anterior=nodo;
	}
	
	public void setDatos(T datos){
		Dato=datos;		
	}
	
	public T getDatos(){
		return Dato;
	}
	
}
