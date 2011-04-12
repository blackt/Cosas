import java.util.*;



public class Main {
	
	public static final String punto=".";
	public static final String union="+";
	public static final String asterisco="*";
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		Scanner LeerRegExp= new Scanner(System.in);
		LinkedList<String []> automatas=new LinkedList<String[]>();
		//automatas= reglaAsterisco(reglaUnion(reglaBase("c"),reglaAsterisco(reglaPunto(reglaBase("b"), reglaBase("c")))));
		automatas= Main.evaluador((LeerRegExp.nextLine()));

		for(String [] e: automatas)
			System.out.println(Arrays.toString(e));
		Grafico.grafica(automatas);

	}

	public static LinkedList<String []> reglaPunto(LinkedList<String []> primero,LinkedList<String []> segundo){
		LinkedList<String []> resultado = new LinkedList<String[]>();
		int inicio =primero.size()+Integer.parseInt(primero.getFirst()[0]);
		for(String [] estado:primero)
			if(estado[3].equals("Y")){
				estado[2]=String.valueOf(inicio);
				estado[3]="N";
				resultado.add(estado);
			}
			else
			resultado.add(estado);
			
		
		for(String[] estado :segundo){
			estado[0]=String.valueOf(Integer.parseInt(estado[0])+inicio);
			estado[2]= estado[2].equals("")?"":String.valueOf(Integer.parseInt(estado[2])+inicio);
			resultado.add(estado);
			//++inicio;
		}
		
				return resultado;	
				
	}
	
	
	
	public static LinkedList<String []> reglaAsterisco(LinkedList<String[]> asteriscado){
		
		
		LinkedList<String []> resultado = new LinkedList<String[]>();
		int inicio =1;
		int fin= Integer.parseInt(asteriscado.getLast()[0])+2;
		
		
		String [] Fa={""," ",String.valueOf(fin),"N"};

		for(String [] estado:asteriscado)
			{
				estado[0]=String.valueOf(Integer.parseInt(estado[0])+1);
				//estado[1]=" ";
				estado[2]= estado[2].equals("")?"":String.valueOf(Integer.parseInt(estado[2])+1);
				
				if(estado[3].equals("Y")){
					
					estado[3]="N";
					estado[2]="1";
					resultado.add(estado);
					Fa[0]= estado[0];
					resultado.add(Fa.clone());
					}
				else
					resultado.add(estado);
				++inicio;
			}
		
		String [] I={"0"," ","1","N"};
		String [] I2={"0"," ",String.valueOf(fin),"N"};
		String [] F={String.valueOf(fin)," ","","Y"};
				
		resultado.addFirst(I2);
		resultado.addFirst(I);
		resultado.add(F);
		return resultado;
		
		
	}
	public static LinkedList<String []> reglaBase(String letra){
		LinkedList<String []> automata= new LinkedList<String[]>();
		String [] I={"0",letra,"1","N"};
		String [] F={"1"," ","","Y"};
		automata.add(I);
		automata.add(F);
		return automata;
	}
	public static LinkedList<String []> reglaUnion(LinkedList<String []> primero,LinkedList<String []> segundo){
		
		LinkedList<String []> resultado = new LinkedList<String[]>();
		int fin= Integer.parseInt( primero.getLast()[0])+Integer.parseInt(segundo.getLast()[0])+3;
		
		String [] I ={"0"," ","1","N"};
		resultado.add(I);
		
		int inicio=1;
		for(String [] estado: primero){
			estado[0]=String.valueOf(inicio);
			estado[2]= estado[2].equals("")?"":String.valueOf(Integer.parseInt(estado[2])+1);
			if(estado[3].equals("Y")){
				estado[2]=String.valueOf(fin);
				estado[3]="N";				
				resultado.add(estado);
			}
			else
				resultado.add(estado);
			++inicio;	
		}
		
		String [] I2 ={"0"," ",String.valueOf(inicio),"N"};
		resultado.add(I2);
		int diferencia=inicio;
		for(String [] estado: segundo){
			estado[0]=String.valueOf(Integer.parseInt(estado[0])+diferencia);
			estado[2]= estado[2].equals("")?"":String.valueOf(Integer.parseInt(estado[2])+diferencia);
			if(estado[3].equals("Y")){
				estado[2]=String.valueOf(fin);
				estado[3]="N";				
				resultado.add(estado);
			}
			else
			resultado.add(estado);
				++inicio;	
		}	
		
		String [] Fin={String.valueOf(fin)," ","","Y"};
		resultado.add(Fin);
		
		
		return resultado;
	}
	public static void reglaNada(){}
	public static String[] reglaVacia(){
		
		String [] a= {"0"," ","","N"};
		return a;
	}
	
	public static String infijoPosfijo(String b){
		String posfiga="";
		Stack<String> pilaParentesis=new Stack<String>();
		Stack<String> posfija=new Stack<String>();
		String temporal="";
		
		if(!completo(b))
			System.out.println("faltan parentesis");
		
		
		for(String exp:espaciar(b).split("[ ]+"))
			if(exp.equals(".")) 
				
				pilaParentesis.push(".");
		
			else if(exp.equals("+"))
				
				pilaParentesis.push("+");
		
			else if(exp.equals("*"))
				
				pilaParentesis.push("*");
		
		
			else if(exp.equals("("))
				
				pilaParentesis.push("(");
		
			else if(exp.equals(")")){
				while (!(temporal=pilaParentesis.pop()).equals("("))
					posfija.push(temporal);
				if(!pilaParentesis.empty())
					if(pilaParentesis.peek().equals("*"))
						posfija.push(pilaParentesis.pop());
				
			}
			else
				posfija.push(exp);
				
		while(!pilaParentesis.empty())
			posfija.push(pilaParentesis.pop());
		
		for(int i=0;i<posfija.size();i++)
			posfiga+=posfija.get(i)+" ";
	
			return posfiga;
		}
	
	
	public static boolean  completo(String cade){
		Stack<String> par=new Stack<String>();
		for(String  p:cade.split("")){
			if(p.equals("("))
				par.push("(");				
			else if(p.equals(")")){				
				if(par.empty())
					return false;				
				par.pop();
			}			
		}		
		return par.empty();
	}
	
	
	
	public static String espaciar(String expresion){
		String espaciado="";
		for(int i=0;i<expresion.length();i++){
			if(expresion.substring(i, i+1).equals("."))
				espaciado+=" . ";
		  else if(expresion.substring(i, i+1).equals("+"))
				espaciado+=" + ";
			else if(expresion.substring(i, i+1).equals("*"))
				espaciado+=" * ";
			else if(expresion.substring(i, i+1).equals(")"))
				espaciado+=" ) ";
		else if(expresion.substring(i, i+1).equals("("))
			espaciado+=" ( "; 
			
			else
				espaciado+=expresion.substring(i, i+1);
		}
		return espaciado;
	}
	
	
	
	
	public static LinkedList<String []> polaca(String a) {
		String[] partes=a.split("[ ]");
		Stack<LinkedList<String []>> pila=new Stack<LinkedList<String []>>();
		LinkedList<String []> al=new LinkedList<String[]>();
		LinkedList<String[]> b= new LinkedList<String []>();
		try{
		for(String exp: partes){
			
			if(exp.equals(".")){
				b=pila.pop();
				al=pila.pop();
				pila.push(reglaPunto(al,b));
				
			}
				
			else if(exp.equals("+"))
				pila.push(reglaUnion(pila.pop(),pila.pop() ));
			else if(exp.equals("*")){
				pila.push(reglaAsterisco(pila.pop()));		
				
			}
				
		
			else
				pila.push(reglaBase(exp));
			
		}
		}
		catch (EmptyStackException e) {
			System.out.println("expresion incosistente");
			return null;
		}
	  if(pila.empty())
		  return null;

		return pila.pop();
		
	}
	
	
	public static LinkedList<String []> evaluador(String exp){
		return polaca(infijoPosfijo(exp));
		
		
	}

}


