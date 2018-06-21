package TDALista;

/**
 * Class Nodo.
 * Implementa Position.
 * Representa un nodo de una SimplyLinkedList.
 *
 * @param <E> Tipo de los elementos almacenados por la SimplyLinkedList.
 */
public class Nodo<E> implements Position<E>{
	protected E elem;
	protected Nodo<E> next;
	
	/** 
	 * Instancia un objeto de tipo Nodo y establece el elemento del nodo y el nodo siguiente.
	 * @param n Nodo siguiente.
	 * @param e Elemento del nodo.
	 */
	public Nodo(Nodo<E> n,E e) {
		next=n;
		elem=e;
	}
	/** 
	 * Instancia un objeto de tipo Nodo y establece el elemento del nodo.
	 * @param e Elemento del nodo.
	 */
	public Nodo(E e) {
        this(null,e);
    }

	/**
	 * Retorna el siguiente Nodo o null si este no existe.
	 * @return el siguiente Nodo.
	 */
    public Nodo<E> getSiguiente () { 
        return next; 
    } 
    /**
     * Establece el elemento del Nodo.
     * @param elemento Elemento a establecer. 
     */
    public void setElemento( E elemento ) {   
        this.elem = elemento; }
    /**
     * Establece el siguiente Nodo.
     * @param siguiente Nodo a establecer como siguiente.
     */
    public void setSiguiente (Nodo<E> siguiente) {   
        this.next = siguiente;   }
    public E element () { 
        return elem; } 
}
