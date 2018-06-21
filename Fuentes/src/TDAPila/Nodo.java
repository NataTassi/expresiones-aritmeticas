package TDAPila;

/**
 * Class Nodo
 * Representa un nodo de LinkedStack
 * @param <E> Tipo del elemento almacenado por el nodo.
 */
public class Nodo<E>{
    protected E elem;
    protected Nodo<E> next;

    /**
     * Instancia un objeto de tipo Nodo. Establece el elemento almacenado y el siguiente nodo.
     * @param v elemento a establecer.
     * @param n Nodo a establecer como siguiente.
     */
    public Nodo(E v,Nodo<E> n){
        elem=v;
        next=n;
    }
    /**
     * Instancia un objeto de tipo Nodo. Establece el elemento almacenado.
     * @param v elemento a establecer. 
     */
    public Nodo(E v){
        this(v,null);
    }
    /**
     * Instancia un objeto de tipo Nodo.
     */
    public Nodo(){
        this(null,null);
    }

    /**
     * Establece el elemento del nodo.
     * @param v elemento a establecer.
     */
    public void setElemento(E v){
        elem=v;
    }
    /** 
     * Establece el siguiente nodo del nodo que recibe el mensaje.
     * @param n Nodo a establecer.
     */
    public void setSiguiente(Nodo<E> n){
        next=n;
    }
    /**
     * Retorna el elemento almacenado en el nodo.
     * @return elemento almacenado en el nodo.
     */
    public E getElemento(){
        return elem;
    }
    /**
     * Retorna el siguiente nodo del nodo que recibe el mensaje.
     * @return el nodo siguiente.
     */
    public Nodo<E> getSiguiente(){
        return next;
    }
}
