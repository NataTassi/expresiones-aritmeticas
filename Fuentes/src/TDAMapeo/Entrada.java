package TDAMapeo;

/**
 * Class Entrada
 * Implementa entry.
 * Representa una entrada de un OpenHashMap.
 *
 * @param <K> Tipo de la clave de la Entrada.
 * @param <V> Tipo del valor de la Entrada.
 */
public class Entrada<K,V> implements Entry<K,V>{
    K key;
    V value;

    /**
     * Instancia un objeto de tipo Entrada y establece su clave y su valor.
     * @param k clave de la Entrada.
     * @param v valor de la Entrada.
     */
    public Entrada(K k,V v){
        key=k;
        value=v;
    }
    /**
     * Instancia un objeto de tipo Entrada.
     */
    public Entrada(){
        this(null,null);
    }
    public K getKey(){
        return key;
    }
    public V getValue(){
        return value;
    }
    /**
     * Establece una nueva clave para la entrada.
     * @param k nueva clave.
     */
    public void setKey(K k){
        key=k;
    }
    /**
     * Establece un nuevo valor para la entrada.
     * @param v nuevo valor.
     */
    public void setValue(V v){
        value=v;
    }
}
