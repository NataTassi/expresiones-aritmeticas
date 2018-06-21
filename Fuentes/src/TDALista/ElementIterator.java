package TDALista;
import java.util.*;

/**
 * Class ElementIterator.
 * Implementa Iterator.
 * Iterador de SimplyLinkedList.
 *
 * @param <E> Tipo de los elementos de la lista.
 */
public class ElementIterator<E> implements Iterator<E>{
    protected PositionList<E> list;
    protected Position<E> cursor;

    /** 
     * Instancia un objeto de tipo ElementIterator. Iterador de la lista l.
     * @param l Lista a la que el iterador referencia.
     */
    public ElementIterator(PositionList<E> l){
        list =l;
        try{
            if(l.isEmpty())cursor = null;
            else cursor=l.first();
        }catch(EmptyListException e){
            System.out.println(e.getMessage() + "\n" + e.getStackTrace());
        }
    }
    /**
     * Returns true if the iteration has more elements. (In other words, returns true if next() would return an element rather than throwing an exception.)
     * @return true if the iteration has more elements
     */
    public boolean hasNext(){
        return cursor!=null;
    }
    /**
     * Returns the next element in the iteration.
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    public E next() throws NoSuchElementException{
        E res=null;
        try{
            if(cursor==null)throw new NoSuchElementException("no tiene siguiente");
            res = cursor.element();
            cursor = (cursor==list.last())?null:list.next(cursor);
        }catch(NoSuchElementException | InvalidPositionException | EmptyListException | BoundaryViolationException e){
            System.out.println(e.getMessage() + "\n" + e.getStackTrace());
        }
        return res;
    }
}
