package TDAArbolBinario;

/**
 * Class BTPosition.
 * Extiende Position.
 * Representa la ubicación de un elemento en un BinaryTree.
 * @param <E> Tipo de los elemento que almacena el BinaryTree.
 */
public interface BTPosition<E> extends Position<E>{
	/**
	 * Establece el hijo izquierdo de la posición.
	 * @param l BTPosition a establecer como hijo izquierdo.
	 */
	public void setLeft(BTPosition<E> l);
	/**
	 * Establece el hijo derecho de la posición.
	 * @param r BTPosition a establecer como hijo derecho.
	 */
    public void setRight(BTPosition<E> r);
    /**
	 * Establece el padre de la posición.
	 * @param p BTPosition a establecer como padre.
	 */
    public void setParent(BTPosition<E> p);
    /**
     * Retorna el hijo izquierdo de la posición.
     * @return el hijo izquierdo de la posición.
     */
    public BTPosition<E> getLeft();
    /**
     * Retorna el hijo derecho de la posición.
     * @return el hijo derecho de la posición.
     */
    public BTPosition<E> getRight();
    /**
     * Retorna el padre de la posición.
     * @return el padre de la posición.
     */
    public BTPosition<E> getParent();
}
