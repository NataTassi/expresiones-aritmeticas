package gui;
import TDAArbolBinario.*;
import TDAMapeo.*;
import TDAPila.*;
import TDALista.SimplyLinkedList;
import TDALista.EmptyListException;
import TDALista.PositionList;

/**
 * Class Logica. Provee métodos para recibir una expresión aritmética y realizar operaciones y consultas sobre la misma.
 */
public class Logica {
	private BinaryTree<String>  exprBT;
	private Map<String,Double> varsMap;
	private static final String invExp = "Expresión aritmética inválida.";
	
	/**
	 * Instancia un objeto de clase Logica.
	 */
	public Logica() {
		exprBT = new ArbolBinario<String>();
		varsMap = new OpenHashMap<String,Double>();
	}
	
	/**
	 * Devuelve el valor de la clave solicitada.
	 * @param n Nombre de la clave.
	 * @return Valor asociado a la clave o null si la clave no existe
	 */
	public String getValue(String n) {
		String r = null;
		try {
			Double val = varsMap.get(n);
			if(val != null) r = val.toString();
		} catch(InvalidKeyException e) {
			System.out.println(e.getMessage()+"\n"+e.getStackTrace());
		}
		return r;
	}
	
	/**
	 * @param n Nombre de la variable
	 * @return true si el nombre de la variable es válido, es decir, no contien paréntesis, espacios o caracteres que representen operaciones.
	 * @throws InvalidInputException Si el nombre de la variable es inválido.
	 */
	public boolean isNameValid(String n)throws InvalidInputException{
		if(n.length()==0) throw new InvalidInputException("La cadena vacía no es un nombre válido.");
		for(int i=0;i<n.length();i++) {
			char c=n.charAt(i);
			if(c==' '||c=='('||c==')'||isOperator(c)) throw new InvalidInputException("\"" + n + "\" no es un nombre de variable válido.");
		}
		return true;
	}
	
	private boolean isOperator(char o) {
		return o=='+'||o=='-'||o=='*'||o=='/'||o=='^';
	}
	private boolean isOperator(String s) {
		return s.length()==1 && isOperator(s.charAt(0));
	}
	
	/**
	 * Define una variable con nombre n y le asigna el valor d.
	 * Si ya existe una variable con nombre n, se le asignará a la misma el valor d sobrescribiendo el anterior.
	 * @param n Nombre de la variable a definir. No puede contener paréntesis, espacios o caracteres que representen operaciones.
	 * @param d Número real que se va a asignar;
	 * @throws InvalidInputException Si el nombre dado no es una cadena válida.
	 */
	public void assign(String n, double d) throws InvalidInputException{
		if(isNameValid(n)) try {
			varsMap.put(n, d);
		}catch(InvalidKeyException e) {
			System.out.println(e.getMessage()+"\n"+e.getStackTrace());
		}
	}
	
	/**
	 * Recibe y guarda una expresión matemática en notación infija, totalmente parentizada y sin precedencia de operadores. Las variables utilizadas deben estar definidas.
	 * Si ya se había ingresado una expresión aritmética previamente, esta va a ser reemplazada por la nueva expresión. 
	 * @param exp Expresion aritmetica en notacion infija. No se consideran los espacios en blanco. 
	 * @throws InvalidInputException si la sintaxis de la expresion ingresada no es valida.
	 */
	public void setExpr(String exp)throws InvalidInputException{
		Stack<BinaryTree<String>> s = new LinkedStack<BinaryTree<String>>();
		try{
			for(int i=0;i<exp.length();i++) {
				char c=exp.charAt(i);
				if(c!='('&&c!=' ') {
					if(c==')') {
						BinaryTree<String> t2 = s.pop(),
								op=s.pop(),
								t1=s.pop();
						op.Attach(op.root(), t1, t2);
						s.push(op);
					}
					else{
						String rotulo="";
						if(isOperator(c))rotulo+=c;
						else {
							int j;
							for(j=i;j<exp.length()&&!isOperator(exp.charAt(j))&&exp.charAt(j)!=' '&&exp.charAt(j)!=')';j++) {
								c=exp.charAt(j);
								if(c=='(')throw new InvalidInputException(invExp);
								rotulo+=c;
							}
							if(varsMap.get(rotulo)==null)
								throw new InvalidInputException(invExp + " Variable \""+rotulo+"\" inválida o sin definir.");
							
							i=j-1;
						}
						BinaryTree<String>t=new ArbolBinario<String>();
						t.createRoot(rotulo);
						s.push(t);
					}
				}
			}
			if(s.size()!=1)throw new InvalidInputException(invExp);
			exprBT = s.pop();
		}catch( EmptyTreeException | InvalidKeyException | InvalidPositionException |InvalidOperationException e) {
			System.out.println(e.getMessage()+"\n"+e.getStackTrace());
		}catch(EmptyStackException e) {
			throw new InvalidInputException(invExp);
		}
	}
	
	/**
	 * Devuelve la expresión matemática almacenada, en notación prefija.
	 * @return Expresión matemática en notación prefija.
	 */
	public String getPrefix() {
		String res="";
		try {
			if(!exprBT.isEmpty())
				res=getPrefix(exprBT.root());
		}catch(EmptyTreeException e) {
			System.out.println(e.getMessage()+"\n"+e.getStackTrace());
		}
		return res;
	}
	private String getPrefix(Position<String>p) {
		String res = p.element();
		try {
			if(exprBT.isInternal(p))
				res+= " "+getPrefix(exprBT.left(p))+" "+getPrefix(exprBT.right(p));
		}catch(InvalidPositionException | BoundaryViolationException e) {
			System.out.println(e.getMessage()+"\n"+e.getStackTrace());
		}
		return res;
	}
	
	/**
	 * Devuelve la expresión matemática almacenada, en notación infija.
	 * @return Expresión matemática en notación infija.
	 */
	public String getInfix() {
		String res="";
		try {
			if(!exprBT.isEmpty())
				res=getInfix(exprBT.root());
		}catch(EmptyTreeException e) {
			System.out.println(e.getMessage()+"\n"+e.getStackTrace());
		}
		return res;	
	}
	private String getInfix(Position<String>p) {
		String res=p.element();
		try {
			if(exprBT.isInternal(p))
				res= "("+getInfix(exprBT.left(p))+" "+ res + " "+ getInfix(exprBT.right(p))+ ")";
		}catch(InvalidPositionException | BoundaryViolationException e) {
			System.out.println(e.getMessage()+"\n"+e.getStackTrace());
		}
		return res;
	}
	
	/**
	 * Devuelve la expresión matemática almacenada, en notación posfija.
	 * @return Expresión matemática en notación posfija.
	 */
	public String getPostfix() {
		String res="";
		try {
			if(!exprBT.isEmpty())
				res=getPostfix(exprBT.root());
		}catch(EmptyTreeException e) {
			System.out.println(e.getMessage()+"\n"+e.getStackTrace());
		}
		return res;
	}
	private String getPostfix(Position<String>p) {
		String res = p.element();
		try {
			if(exprBT.isInternal(p))
				res= getPostfix(exprBT.left(p))+" "+getPostfix(exprBT.right(p))+" "+p.element();
		}catch(InvalidPositionException | BoundaryViolationException e) {
			System.out.println(e.getMessage()+"\n"+e.getStackTrace());
		}
		return res;
	}

	/**
	 * Calcula el valor de la expresión aritmética ingresada de forma recursiva.
	 * @return El número real que equivale a la expresión almacenada.
	 * @throws InvalidOperationException Si la expresión aritmética no es válida.
	 */
    public double solveRecursive()throws InvalidOperationException{
        try{
            if(!exprBT.isEmpty()) return solveRecursive(exprBT.root());
        }catch(EmptyTreeException e){
			System.out.println(e.getMessage()+"\n"+e.getStackTrace());
        }
        return 0;
    }
    private double solveRecursive(Position<String>p)throws InvalidOperationException{
        try{
            if(exprBT.isInternal(p)){
                double l=solveRecursive(exprBT.left(p));
                double r=solveRecursive(exprBT.right(p));
                return calc(l,r,p.element());
            }
        }catch(InvalidPositionException | BoundaryViolationException e) {
			throw new InvalidOperationException(invExp);
		}
        try{
            Double res= varsMap.get(p.element());
            if(res==null)throw new InvalidOperationException(invExp);
            return res;
        }catch(InvalidKeyException e){
			System.out.println(e.getMessage()+"\n"+e.getStackTrace());
            return 0;
        }
    }

    private double calc(double n1,double n2,String op){
        if(op.equals("+"))return n1+n2;
        if(op.equals("-"))return n1-n2;
        if(op.equals("*"))return n1*n2;
        if(op.equals("/"))return n1/n2;
        if(op.equals("^"))return Math.pow(n1,n2);
        return 0;
    }

    /**
	 * Calcula el valor de la expresión aritmética ingresada de forma recursiva.
	 * @return El número real que equivale a la expresión almacenada.
     * @throws InvalidOperationException Si la expresión aritmética no es válida.
	 */
    public double solveWithStack() throws InvalidOperationException{
        PositionList<String> l = new SimplyLinkedList<String>();
        Stack<Double> s = new LinkedStack<Double>();
        double res=0;
        if(!exprBT.isEmpty()){
        	try {
        		Postfix(exprBT.root(),l);
        		while(!l.isEmpty()) {
        			String atom=l.first().element();
        			l.remove(l.first());
        			if(isOperator(atom)){
        				Double n2 = s.pop();
        				Double n1 = s.pop();
        				s.push(calc(n1,n2,atom));
        			}
        			else {
        				Double val = varsMap.get(atom);
        				s.push(val);
        			}
        		}
        		res=s.pop();
        	}catch(EmptyTreeException | InvalidKeyException | EmptyStackException | TDALista.InvalidPositionException | EmptyListException e) {
    			throw new InvalidOperationException(invExp);
        	}
        }
        return res; 	
    }
	private void Postfix(Position<String>p,PositionList<String>l) {
		try {
			if(exprBT.isInternal(p)){
                Postfix(exprBT.left(p),l);
                Postfix(exprBT.right(p),l);
			}
		}catch(InvalidPositionException | BoundaryViolationException e) {
			System.out.println(e.getMessage()+"\n"+e.getStackTrace());
		}
        l.addLast(p.element());
	}
	
	/**
	 * Retorna una lista de cadenas que representan los subárboles de altura 1 de la expresión aritmética en notación infija.
	 * @return Una lista de cadenas de los subárboles de altura 1 de la expresión.
	 */
	public PositionList<String> nodesHeight1(){
		PositionList<String>l=new SimplyLinkedList<String>();
		if(exprBT.size()>=3) {
			try {
				nodesHeight1(exprBT.left(exprBT.root()),l);
				nodesHeight1(exprBT.right(exprBT.root()),l);
			}catch(EmptyTreeException | InvalidPositionException | BoundaryViolationException e) {
				System.out.println(e.getMessage()+"\n"+e.getStackTrace());
			}
		}
		return l;
	}
	private void nodesHeight1(Position<String> p,PositionList<String>l){
		try {
			if(exprBT.isInternal(p)){
				Position<String> left = exprBT.left(p);
				Position<String> right = exprBT.right(p);
				if(exprBT.isExternal(left)&&exprBT.isExternal(right)) {
					l.addLast(getInfix(p));
				}
				else {
					nodesHeight1(left,l);
					nodesHeight1(right,l);
				}
			}
		}catch(InvalidPositionException | BoundaryViolationException e) {
			System.out.println(e.getMessage()+"\n"+e.getStackTrace());
		}	
	}
	
	/**
	 * Recibe una lista de nombres válidos de variables y reemplaza las subexpresiones equivalentes a un árbol binario de altura 1 por una nueva variable. 
	 * La i-ésima nueva variable de la expresion en notacion infija recibirá el i-ésimo nombre de la lista. 
	 * @param lista Lista de nombres válidos de variables. No puede contener paréntesis, espacios o caracteres que representen operaciones. No puede existir una variable con ese nombre.
	 * @throws InvalidOperationException Si la longitud de la lista no coincide con la cantidad de subexpresiones a reemplazar.
	 * @throws InvalidInputException Si por lo menos uno de los nombres de la lista no es válido.
	 */
	public void reemplazarTermino(PositionList<String>lista) throws InvalidInputException {
		if(exprBT.size() >= 3) {
			try {
				reemplazarTermino(exprBT.left(exprBT.root()),lista);
				reemplazarTermino(exprBT.right(exprBT.root()),lista);
			}catch(EmptyTreeException | InvalidPositionException | BoundaryViolationException e) {
				System.out.println(e.getMessage()+"\n"+e.getStackTrace());
			}
		}
	}
	private void reemplazarTermino(Position<String> p, PositionList<String>l){
		try {
			if(exprBT.isInternal(p)){
				Position<String> left = exprBT.left(p);
				Position<String> right = exprBT.right(p);
				if(exprBT.isExternal(left) && exprBT.isExternal(right)) {
					String name = l.remove(l.first());
					double val = solveRecursive(p);
					try {
						assign(name,val);
						exprBT.remove(left);
						exprBT.remove(right);
						exprBT.replace(p, name);
					}catch(InvalidInputException e) {
						System.out.println(e.getMessage()+"\n"+e.getStackTrace());
					}
				}
				else {
					reemplazarTermino(left,l);
					reemplazarTermino(right,l);
				}
			}
		}catch(EmptyListException | InvalidPositionException | TDALista.InvalidPositionException | BoundaryViolationException | InvalidOperationException e ) {
			System.out.println(e.getMessage()+"\n"+e.getStackTrace());
		}
	}
	
	/**
	 * Retorna una cadena con la altura del árbol, número de hojas del árbol, número de nodos del árbol y el número de nodos internos del árbol. 
	 * @return Cadena con la informacion consultada concatenada y separada por espacios.
	 */
	public String infoBinaryTree(){
        int hojas = 0, h=0;
        try {
        if(!exprBT.isEmpty()){
            hojas=cantHojas(exprBT.root());
            h=altura(exprBT.root());
        }
        }catch(EmptyTreeException e) {
			System.out.println(e.getMessage()+"\n"+e.getStackTrace());
        }
        return h + " " + hojas + " " + exprBT.size() + " " + (exprBT.size()-hojas);
    }
    private int cantHojas(Position<String> p){
        int res=1;
        try{
            if(exprBT.isInternal(p))
                res= cantHojas(exprBT.left(p))+cantHojas(exprBT.right(p));
        }catch(InvalidPositionException|BoundaryViolationException e){
			System.out.println(e.getMessage()+"\n"+e.getStackTrace());
        }
        return res;
    }
    private int altura(Position<String> p){
        int res=0;
        try{
            if(exprBT.isInternal(p))
                res=1+Math.max(altura(exprBT.left(p)),altura(exprBT.right(p)));
        }catch(InvalidPositionException | BoundaryViolationException e) {
			System.out.println(e.getMessage()+"\n"+e.getStackTrace());
        }
        return res;
    }
}
