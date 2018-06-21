package TDALista;

import java.util.Iterator;

/**
 * Class SimplyLinkedList
 * Implementa PositionList 
 *
 * @param <E> Tipo de los elementos almacenados.
 */
public class SimplyLinkedList<E> implements PositionList<E>{
	protected Nodo<E> cabeza,cola;
	protected int cant;
	
	/**
	 * Instancia un objeto de tipo SimplyLinkedList. Una lista vacía.
	 */
	public SimplyLinkedList() { 
		cabeza=null;
        cola=null;
		cant=0;
	}
	
	public int size() {
		return cant;
	}
	
	public boolean isEmpty() {
        return cabeza==null;
	}
	
	public Position<E>first()throws EmptyListException{
		if(cant==0)throw new EmptyListException("Lista vacía al consultar first");
		return cabeza;
	}
	public Position<E>last()throws EmptyListException{
		if(cant==0)throw new EmptyListException("Lista vacía al consultar first");
        return cola;
	}
	
	public Position<E> next(Position<E> p)throws InvalidPositionException,BoundaryViolationException{
		if(cant==0)throw new InvalidPositionException("Lista vacía al consultar next");
		Nodo<E> n = checkPosition(p);
		if(n.getSiguiente()==null)throw new BoundaryViolationException("No hay siguiente a p");						
		return n.getSiguiente();
	}

	private Nodo<E> checkPosition(Position<E> p)throws InvalidPositionException {
		Nodo<E> res;
		try{
			if(p==null)throw new InvalidPositionException("Posición inválida, p nulo");
			res= (Nodo<E>)p;
		}catch(ClassCastException e) {
			throw new InvalidPositionException("Posición inválida, p no es nodo");
		}
		return res;
	}
	
	public Position<E> prev(Position<E> p)throws InvalidPositionException, BoundaryViolationException{
		Nodo<E> nodo = checkPosition(p);
        Nodo<E> aux;
		try{
            if(nodo==first())throw new BoundaryViolationException("No hay previo a p");
            aux=cabeza;
		}catch(EmptyListException e){
            throw new InvalidPositionException("Lista vacía al consultar prev");
        }
		while(aux.getSiguiente()!=null&&aux.getSiguiente()!=nodo) {
			aux=aux.getSiguiente();
		}
		return aux;
	}
	
	public void addFirst(E e) {
		cabeza= new Nodo<E>(cabeza,e);
        if(cant==0)cola=cabeza;
		cant++;
	}
	public void addLast(E e) {
		if(cant==0) {
			addFirst(e);
		}
		else {
            Nodo<E> n = new Nodo<E>(null,e);
			cola.setSiguiente(n);
            cola=n;
			cant++;
		}
	}
	
	public void addAfter(Position<E> p,E e) throws InvalidPositionException{//O(1)
		if(cant==0)throw new InvalidPositionException("Lista vacía al consultar next");
        Nodo<E> n= checkPosition(p);
        if(n==cola)addLast(e);
        else{
            n.setSiguiente(new Nodo<E>(n.getSiguiente(),e));
            cant++;
        }
	}
	public void addBefore(Position<E> p,E e)throws InvalidPositionException{
		Nodo<E> n = checkPosition(p);
		try {
			if(n==first())addFirst(e);	
			else{
				Nodo<E> prev = checkPosition(prev(p));
				prev.setSiguiente(new Nodo<E>(n,e));
			}
			cant++;
		}catch(BoundaryViolationException | EmptyListException exc) {
			throw new InvalidPositionException("Lista vacía o p inválido en addBefore");
		}
	}
	
	public E remove(Position<E> p)throws InvalidPositionException{
		if(cant==0)throw new InvalidPositionException("Lista vacía al remover");
		Nodo<E> n=checkPosition(p);
		if(cant==1) {
			cabeza=null;
			cola=null;
			cant--;
			return n.element();
		}
		try {
			if(n==cabeza)cabeza=n.getSiguiente();
            else {
                Nodo<E> prev = checkPosition(prev(p));
                prev.setSiguiente(n.getSiguiente());
                if(n==cola)cola=prev;
            }
        }catch(BoundaryViolationException | InvalidPositionException e) {
            throw new InvalidPositionException("Pos inválida al remover");
        }
        cant--;
        return n.element();
    }

    public E set(Position<E> p,E elem)throws InvalidPositionException{
        if(cant==0)throw new InvalidPositionException("Lista vacía durante set(p,e)");
        Nodo<E> n = checkPosition(p);
        E aux = n.element();
        n.setElemento(elem);
        return aux;
    }

    public Iterator<E> iterator(){
        return new ElementIterator<E>(this);
    }
    
    public Iterable<Position<E>> positions(){
        PositionList<Position<E>> res = new SimplyLinkedList<Position<E>>();
        try {
            if(!isEmpty()) {
                Nodo<E> n= checkPosition(cabeza);
                while(n!=null) {
                    res.addLast(n);
                    n= n.getSiguiente();
                }
            }
        }catch(InvalidPositionException e) {
            System.out.println(e.getMessage()+"\n"+e.getStackTrace());
        }
        return res;
    }

}
