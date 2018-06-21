package TDAMapeo;

/**
 * Interface Entry.
 * Representa un par clave-valor. 
 * 
 * @param <K> Tipo de la clave.
 * @param <V> Tipo del valor.
 */
public interface Entry<K,V>{
	/**
	 * Retorna la clave de la Entry.
	 * @return la clave de la Entry.
	 */
    public K getKey();
    /**
     * Retorna el valor de la Entry.
     * @return el valor de la Entry.
     */
    public V getValue();
}
