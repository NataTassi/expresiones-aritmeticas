package TDAArbolBinario;
/**
 * Class BTNode
 * Implementa BTPosition
 * Representa un nodo de ArbolBinario.
 * @param <E> tipo de los elementos que almacena el ArbolBinario
 */
public class BTNode<E> implements BTPosition<E>{
    protected E elem;
    protected BTPosition<E> parent, left, right;

    /**
     * Instancia un objeto de tipo BTNode y establece el rótulo, el padre, el hijo izquierdo y el hijo derecho.
     * @param e rótulo.
     * @param p nodo padre.
     * @param l nodo hijo izquierdo.
     * @param r nodo hijo derecho.
     */
    public BTNode(E e,BTPosition<E> p,BTPosition<E> l,BTPosition<E> r){
        elem=e;
        parent=p;
        left=l;
        right=r;
    }
    /**
     * Instancia un objeto de tipo BTNode.
     */
    public BTNode(){
        this(null,null,null,null);
    }
    /**
     * Instancia un objeto de tipo BTNode y establece el rótulo.
     * @param e rótulo.
     */
    public BTNode(E e){
        this(e,null,null,null);
    }
    public void setElement(E e){
        elem=e;
    }

    public void setLeft(BTPosition<E> l){
        left =l;
    }

    public void setRight(BTPosition<E> r){
        right = r;
    }

    public void setParent(BTPosition<E> p){
        parent=p;
    }

    public E element(){
        return elem;
    }

    public BTPosition<E> getLeft(){
        return left;
    }

    public BTPosition<E> getRight(){
        return right;
    }

    public BTPosition<E> getParent(){
        return parent;
    }
}
