import java.io.File;
import java.util.*;

import javax.swing.JOptionPane;

public class Analizador {
	Map<String,Nodo > Tabla =new HashMap<String,Nodo >();
	String inicio="";


	public Analizador(String n){
	Entrada(n);
	

	}
	public void Entrada(String leer){
		Scanner lector;
		
			lector = new Scanner(leer);
		
		String texto="";
		String inicio=lector.nextLine().trim();
		this.inicio=inicio;
		String [] aceptacion= lector.nextLine().split("[,]");
		while(lector.hasNext())
			texto+=lector.nextLine()+"\n";
		parser(texto,aceptacion);
			//System.out.println(lector.nextLine());
		
	}
	
	public void Prueba(String validar){	
		
		Nodo actual=Tabla.get(inicio);	     
	    String cadena=validar.trim();
	      	    
	       for(int i=0;i<cadena.length();i++){
	           actual=actual.Nodorespuesta(cadena.charAt(i));
	            if(actual==null){
	            	JOptionPane.showMessageDialog(null,"cadena no aceptada "+cadena);
	              break;
	              }
	       if(i+1==cadena.length())
	           if(actual.getAceptacion())
	               JOptionPane.showMessageDialog(null,"cadena aceptada "+cadena);
	           else
	        	   JOptionPane.showMessageDialog(null,"cadena no aceptada "+cadena);	
	       }  		
	}	
	
	public  void parser(String texto,String [] aceptacion){
		Map<String,Nodo > Tabla =new HashMap<String,Nodo >();
		GraphViz gv = new GraphViz();		
		for(String clave:texto.split("\\n") )
			Tabla.put(tomalinea(clave, 0), new Nodo());		

		for(String linea:texto.split("\\n") )			
			Tabla.get(tomalinea(linea, 0)).addNodo(new Tupla<Nodo>(tomalinea(linea, 1),Tabla.get(tomalinea(linea, 2))));
		
		for(String a:aceptacion)
			Tabla.get(a).setAceptacion(true);
		
		
		 
	      gv.addln(gv.start_graph());
	     
	     Iterator<String> it=Tabla.keySet().iterator();
	     String estado="";
	     while(it.hasNext())
	    	 if(Tabla.get(estado=it.next()).getAceptacion())	    	 
	    		 gv.addln(estado+"[color=\"yellow\"];");
	      
	     
	      
		for(String clave:texto.split("\\n") ){	
			 gv.addln(tomalinea(clave, 0)+" -> "+tomalinea(clave, 2)+"[label=\""+tomalinea(clave, 1)+"\"];" );				
		}
		gv.addln(gv.end_graph());
		 File out = new File("out.gif");
	      gv.writeGraphToFile(gv.getGraph(gv.getDotSource()), out);
		
		this.Tabla= Tabla;	
	}
	
	
	public static String abreviatura(String abr){
		String estados="";
		for(int i=abr.charAt(1);i<abr.charAt(3);i++)
			estados+= (char)i;
		return estados;	
		
	}
	public static String tomalinea(String linea,int i){	
		
		//String[] cadena=linea.split("[,]"); //descomentar para solo usar q0,a,3
		String[] cadena=new String[3];
		String[] c=linea.split("[(),=]");
		cadena[0]=c[1].trim();
		cadena[1]=c[2].replaceAll("[{}]", "").trim();
		cadena[2]=c[4].trim();
		return cadena[i];		
	}
}

class Nodo {
	
	private LinkedList<Tupla<Nodo>> Nodos;
	 private boolean aceptacion=false;
	 
	 public Nodo(){
		 Nodos=new LinkedList<Tupla<Nodo>>(); 
	 }
	
	 public void addNodo(Tupla<Nodo> t){
		 Nodos.add(t); 
	 }
	 public LinkedList<Tupla<Nodo>> getNodos(){
		 return Nodos;
	 }
	 
	 public boolean getAceptacion(){
	        return aceptacion;
	    }
	 public void setAceptacion(boolean estado){
	          aceptacion=estado;
	    }	 
		
	public Nodo Nodorespuesta(char estado){
			
			for(Tupla<Nodo> t:Nodos)
				if(concuerda(estado,t.Estado))
					return t.nodo;
			return null;			
		}		
		
	private boolean concuerda(char estado,String e){

		        for(int j=0;j<e.length();j++)
		            if(estado==e.charAt(j))
		                return true;

		        return false;
		    }
	
}
class Tupla<T>{
	String Estado;
	T nodo;
	public Tupla(String E,T nodo){
		Estado=E;
		this.nodo=nodo;		
	}	
}
