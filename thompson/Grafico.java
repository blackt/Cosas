import java.io.File;
import java.util.*;
public class Grafico {

	public static void grafica(LinkedList<String []> lista ){
		
		
		GraphViz gv = new GraphViz();	
	      gv.addln(gv.start_graph());
	     
	
	     
	         	 for(String [] estado : lista)
	         		 if(estado[3].equals("Y"))
	         			 gv.addln(estado[0]+"[color=\"yellow\"];");
	      
	     
	      
		for(String[] clave:lista) {	
			 gv.addln(clave[0]+" -> "+clave[2]+"[label=\""+((clave[1].equals(" ") && !clave[3].equals("Y"))?"E":clave[1])+"\"];" );				
		}
		gv.addln(gv.end_graph());
		 File out = new File("out.gif");
	      gv.writeGraphToFile(gv.getGraph(gv.getDotSource()), out);
		
		
	}
	
}
