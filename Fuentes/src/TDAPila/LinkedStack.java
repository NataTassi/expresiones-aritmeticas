package TDAPila;

/**
 * Class LinkedStack
 * Implementa Stack
 *
 * @param <E> Tipo de los elementos de la pila.
 */
public class LinkedStack<E> implements Stack<E>{
    protected Nodo<E> head;
    protected int cant;

    /**
     * Instancia un objeto de tipo LinkedStack. El nuevo objeto es una pila vacía.
     */
    public LinkedStack(){
        head=null;
        cant=0;
    }

    public void push(E item){
        Nodo<E> n = new Nodo<E>(item,head);
        head=n;
        cant++;
    }

    public boolean isEmpty(){
        return cant==0;
    }

    public int size(){
        return cant;
    }

    public E pop() throws EmptyStackException{
        if(cant==0)throw new EmptyStackException("Pila vacía");
        E res=head.getElemento();
        cant--;
        head=head.getSiguiente();
        return res;
    }

    public E top() throws EmptyStackException{
        if(cant==0)throw new EmptyStackException("Pila vacía");
        return head.getElemento();
    }

}
