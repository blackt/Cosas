import java.util.LinkedList;
public class analizador {

    public static final String [] tipos={"varchar2","number","int","date"};
    	public static String [] separadores={"+","-","*","/","=","(",")",","," ","\n","\t",";","<",">"};
	public LinkedList<String> tokens;
	public static void main(String[] args) {
		analizador kk=new analizador();
		//kk.tokens=tokenizador( "create or replace procedure nom ( p_name in VARCHAR2 , nom in out number ) begin select * from tavh ; delete from tab where alum = ghj ; end ;");
		//System.out.println(kk.procedimiento());

        	kk.tokens=tokenizador("create trigger pri before insert or update of cantidad on perrito for each row  begin update perrito set cantidad='1' where id='1';en;");//"CREATE trigger nom BEFORE insert OR UPDATE OF salario ON empleados FOR EACH ROW WHEN (:new.salario > '5000') ; BEGIN UPDATE empleados SET salario='5000' where empleado_id=:new.empleado_id ; END;" );
	//	kk.tokens= tokenizador("select cam,dia,fdiaso,afos from tab,tab2 where camp=camp1;");
        //kk.tokens=tokenizador("create or replace procedure nom() Begin insert into perrito values (332,'col','sgf',2); end;");
        if(kk.triggers())
        		System.out.println("true");
        	else{
        		System.out.println("false");
        		//for(int i=0;i<10 && !kk.esVacio();i++)
        			System.out.print(" "+kk.siguiente());
        		}
            System.out.println();
	}
	//delete from tabla where condicion (and|or) condicion ... 

    public boolean procedimiento(){
    if(!siguiente().equalsIgnoreCase("create"))
        return false;
    if(mirar().equalsIgnoreCase("or")){
        siguiente();
        if(!siguiente().equalsIgnoreCase("replace"))
            return false;
    }
    if(!siguiente().equalsIgnoreCase("procedure"))
        return false;
    if(!variable(siguiente()))
        return false;
    if(!variables())
        return false;
    if(!BloBegin())
        return false;

    return true;
    }
    public  boolean variable(){
        if(!variable(siguiente()))
            return false;
        if(mirar().toLowerCase().equals("in")){
            if(!siguiente().equalsIgnoreCase("in"))
                return false;
            if(mirar().equalsIgnoreCase("out"))
                siguiente(); 
        }else if(!siguiente().equalsIgnoreCase("out"))
                return false;
        if(!tipo(siguiente()))
            return false;
        return true;
        }
    public boolean variables(){
          if(!siguiente().equals("("))
              return false;
          if(!variable())
              return false;
          if(mirar().equals(","))
              do{
                 if(!siguiente().equals(","))
                     return false;
                 if(!variable())
                    return false;
          }while(!mirar().equals(")"));
          if(!siguiente().equals(")"))
              return false;
        return true;
    }
	public boolean delete(){
		if(!siguiente().equalsIgnoreCase("delete"))
			return false;
		if(!siguiente().equalsIgnoreCase("from"))
			return false;
		if(!variable(siguiente()))
			return false;
		if(mirar().equals(";"))
			return true;
		return Where();	
		}
	public boolean Where(){

		if(!siguiente().equalsIgnoreCase("where"))
			return false;		
		boolean f=true;
		String k="";
		do{
			if(!(campotabla(mirar()) || varh(mirar())))
                return false;
            siguiente();
			k=siguiente();
            
			if(!(k.equals("<")||k.equals(">")||k.equals("=")))
                return false;

				if(!(campotabla(mirar()) || varh(mirar())|| numero(mirar())|| mirar().matches("\\w+")|| mirar().matches("'\\w+'")))
                    return false;
            siguiente();
			k=siguiente();
			if(k.equals(";")) break;
			f=(k.equals("and") || k.equals("or"))&& f;
			
		}while(!k.equals(";"));
       
		return f;	
	}
	public boolean select(){
		if(!siguiente().equalsIgnoreCase("select"))
			return false;
	
		if(!mirar().equals("*")){
			if(!campotabla(siguiente()))
				return false;
            while(!mirar().equalsIgnoreCase("from")){
                if(!siguiente().equals(","))
                	return false;
                
                if(!(campotabla(siguiente())))
                	return false;                
            }
			}
		else
			siguiente();
        if(!siguiente().equalsIgnoreCase("from"))
            return false;
        
			if(!variable(siguiente()))
				return false;
			
            //System.out.println(mirar());
            while(!mirar().equalsIgnoreCase("where")&&(!mirar().equals(";"))){
                if(!siguiente().equals(","))
                	return false;
                if(!variable(siguiente()))
                	return false;
            }
     
		if(mirar().equals(";")){
			siguiente();
			return true;			
		}
		return Where();
	}
    public boolean update(){
       if(!siguiente().equalsIgnoreCase("update"))
            return false;        
        if(!variable(siguiente()))
            return false;       
        if(!siguiente().equalsIgnoreCase("set"))
            return false;        
     
     if(!variable(siguiente()))
    	 return false;   
        
        if(!siguiente().equals("="))
            return false;
        if(!(variable(mirar()) ||numero(mirar())|| mirar().matches("'\\w+'")))
        	return false;
       siguiente();
      if(mirar().equals(","))
        do{
            if(!siguiente().equals(","))
            	return false;
            if(!variable(siguiente()))
           	 	return false;                  
            if(!siguiente().equals("="))
                   return false;
            if(!variable(mirar())|| !mirar().matches("'\\w+'"))
               	return false;
            siguiente();
            if(mirar().equals(";"))break;
        }while(!mirar().equalsIgnoreCase("where"));
          //System.out.println(mirar());
        
        if(mirar().equals(";")){
            siguiente();
            return true;
        }

        
        return Where();
  
    }
    public boolean Insert(){
        if(!siguiente().equalsIgnoreCase("insert"))
           return false;
        if(!siguiente().equalsIgnoreCase("into"))
            return false;
        if(!variable(siguiente()))
            return false;
        if(!siguiente().equalsIgnoreCase("values"))
            return false;
        if(!siguiente().equalsIgnoreCase("("))
            return false;
        if(! variable(siguiente()))
            return false;
        if(mirar().equals(",")){
            do{

        if(!siguiente().equalsIgnoreCase(","))
            return false;

        if(!variable(siguiente()))
            return false;
        if(mirar().equals(";"))break;

            }while(!mirar().equals(")"));
        }

        if(!siguiente().equalsIgnoreCase(")"))
            return false;

        if(!siguiente().equalsIgnoreCase(";"))
            return false;
        return true;

    }
    public static boolean tipo(String h){
        for(String g: tipos)
            if(g.equalsIgnoreCase(h))
                return true ;
        return false;

    }
	public static boolean variable(String cad){
		return cad.matches("[A-Za-z](\\w+)?");			
	}
	public static boolean campotabla(String c){
		if(variable(c))
			return true;		
		return c.matches("[A-Za-z](\\w+)?.[A-Za-z](\\w+)?");		
		}
	public static boolean numero(String d){
		return d.matches("\\d+");		
	}
	public String siguiente(){
	
        System.out.println(mirar());
		return tokens.remove();		
	}
	public String mirar(){

		return tokens.peek();		
	}
	public boolean esVacio(){
        
		return tokens.isEmpty();
	}
    public boolean BloBegin(){
    if(!siguiente().equalsIgnoreCase("begin"))
        return false;
    String g="";
    do{
    if(mirar().equalsIgnoreCase("select")){
        if(!select())
            return false;
            }
    else if(mirar().equalsIgnoreCase("update")){
            if(!update())
                return false;
            }
    else if(mirar().equalsIgnoreCase("insert")){
            if(!Insert())
                return false;
        }
    else if(mirar().equalsIgnoreCase("delete")){
            if(!delete())
                return false;
            }
    else
        return false;

    }while(!mirar().equalsIgnoreCase("end"));
    siguiente();
    return true;
    }
	public static boolean varh(String c){
		return c.matches("(:new|:old).[A-Za-z](\\w+)?");

	}
    public static LinkedList<String> tokenizador(String Cadena){
		LinkedList<String> Tokens=new LinkedList<String>();
		String palabra="";
		boolean ban=true;
		for(int i=0;i<Cadena.length();i++){
			for(int j=0;j<separadores.length;j++){
				if(Cadena.substring(i,i+1).equals(separadores[j]) ){
					if(palabra.trim().length()!=0)
						Tokens.add(palabra);
					if(Cadena.substring(i,i+1).trim().length()!=0)
						Tokens.add(Cadena.substring(i,i+1));
					palabra="";
					ban=false;
					break;
				}
			}
			if(ban)
				palabra+=Cadena.substring(i,i+1);
			else
				ban=true;
		}
		if(palabra.trim().length()!=0)
			Tokens.add(palabra);
		return Tokens;
	}
	public boolean triggers(){

		String k="";
		if(!siguiente().equalsIgnoreCase("create"))
			return false;
		if(!siguiente().equalsIgnoreCase("trigger"))
			return false;
		if(!variable(siguiente()))
			return false;
		if(!siguiente().toLowerCase().matches("before|after"))
			return false;
		if(!siguiente().toLowerCase().matches("insert|update|delete"))
			return false;
		if(mirar().equalsIgnoreCase("or")){
			do{
				if(!siguiente().equalsIgnoreCase("or"))
					return false;
				if(!siguiente().toLowerCase().matches("insert|update|delete"))
					return false;
			}while(!mirar().equalsIgnoreCase("of"));
		}
		if(!siguiente().equalsIgnoreCase("of"))
			return false;
		if(!variable(siguiente()))
			return false;
		if(!mirar().equalsIgnoreCase("on")){
			do{
				if(!siguiente().equalsIgnoreCase(","))
					return false;
				if(!variable(siguiente()))
					return false;
			}while(!mirar().equalsIgnoreCase("on"));
		}
		if(!siguiente().equalsIgnoreCase("on"))
			return false;
		if(!variable(siguiente()))
			return false;
		if(mirar().equalsIgnoreCase("for")){
			if(!siguiente().equalsIgnoreCase("for"))
				return false;
			if(!siguiente().equalsIgnoreCase("each"))
				return false;
			if(!siguiente().equalsIgnoreCase("row"))
				return false;
		}	
		if(mirar().equalsIgnoreCase("when")){
			siguiente();
			if(!siguiente().equals("("))
				return false;
			do{
					if(!varh(siguiente()))
							return false;
					if(!siguiente().matches("<|>|="))
						return false;
					k=siguiente();
					if(!(numero(k)|| k.matches("\\w+") || varh(k)||k.matches("'\\w+'") ))
						return false;
					k=siguiente();
					if(k.equals(")")) break;
					if(!(k.equals("and") || k.equals("or")))
						return false;
			}while(!mirar().equalsIgnoreCase("BEGIN"));
		}
        if(!BloBegin())
            return false;
		return true;
	}

}
