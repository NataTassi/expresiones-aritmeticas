package TDALista;

/**
 * Class Position
 * Representa la ubicación de un elemento en una estructura de datos con enlaces.
 * @param <E> Tipo de los elementos que almacena la estructura de la posición.
 */
public interface Position<E> {
	/**
	 * Retorna el valor del elemento ubicado en la posición.
	 * @return el valor del elemento ubicado en la posición.
	 */
	public E element();
}
