package TDAMapeo;
import TDALista.*;

/**
 * Class OpenHashMap
 * Implementa Map
 * @param <K> Tipo de la clave de las entradas del mapeo.
 * @param <V> Tipo de los valores de las entradas del mapeo.
 */
public class OpenHashMap<K,V> implements Map<K,V>{
	protected static final double loadFactor=0.9;
	protected PositionList<Entrada<K,V>>[] Buckets;
	protected int cant;
	
	/**
	 * Instancia un objeto de tipo OpenHashMap.
	 */
	public OpenHashMap() {
		this(11);
	}
	/**
	 * Instancia un objeto de tipo OpenHashMap.
	 * @param n Cantidad de entradas minimas a introducirse.
	 */
	public OpenHashMap(int n) {
		Buckets = (PositionList<Entrada<K,V>>[]) new SimplyLinkedList[nxtPrimo(n)];
		for(int i=0;i<Buckets.length;i++)
			Buckets[i]=new SimplyLinkedList<Entrada<K,V>>();
		cant=0;
	}
	
	private int nxtPrimo(int n) {
		for(int i =2;i*i<=n;i++) {
			if(n%i==0)return nxtPrimo(n+1);
		}
		return n;
	}
	
	public int size() {
		return cant;
	}

	public boolean isEmpty() {
		return cant==0;
	}
	
	private int h(K key) {
		return Math.abs(key.hashCode());
	}

	public V get(K key) throws InvalidKeyException {
        if(key==null)throw new InvalidKeyException("clave nula");
		PositionList<Entrada<K,V>> bucket = Buckets[h(key)%Buckets.length]; 
		for(Entrada<K,V> a:bucket) {
			if(a.getKey().equals(key))return a.getValue();
		}
		return null;
	}

	public V put(K key, V value) throws InvalidKeyException {
        if(key==null)throw new InvalidKeyException("clave nula");
		if(cant/(double)Buckets.length>loadFactor)aumentar();
		PositionList<Entrada<K,V>> bucket = Buckets[h(key)%Buckets.length];
		for(Entrada<K,V> a:bucket) {
			if(a.getKey().equals(key)) {
				V res= a.getValue();
				a.setValue(value);
				return res;
			}
		}
		bucket.addLast(new Entrada<K,V>(key,value));
		cant++;
		return null;
	}
	
	private void aumentar() {
		PositionList<Entrada<K,V>>[]aux= (PositionList<Entrada<K,V>>[])new SimplyLinkedList[nxtPrimo(2*Buckets.length)];
		for(int i=0;i<aux.length;i++)aux[i]=new SimplyLinkedList<Entrada<K,V>>();
		for(int i=0;i<Buckets.length;i++) {
			for(Entrada<K,V>a:Buckets[i]) {
				aux[h(a.getKey())%aux.length].addLast(a);
			}
		}
		Buckets = aux;
	}

	public V remove(K key) throws InvalidKeyException {
        if(key==null)throw new InvalidKeyException("clave nula");
		PositionList<Entrada<K,V>> bucket = Buckets[h(key)%Buckets.length]; 
        if(!bucket.isEmpty()){
            try{
            Position<Entrada<K,V>> p=bucket.first();
            while(p!=null) {
                if(p.element().getKey().equals(key)){
                	cant--;
                    return bucket.remove(p).getValue();
                }
                p=(p!=bucket.last())? bucket.next(p):null;
            }
            }catch(EmptyListException | BoundaryViolationException | InvalidPositionException e){
                System.out.println(e.getMessage()+"\n"+e.getStackTrace());
            }
        }
        return null;
	}

	public Iterable<K> keys() {
        PositionList<K> res= new SimplyLinkedList<K>();
        for(int i=0;i<Buckets.length;i++){
            for(Entrada<K,V> a:Buckets[i]){
                res.addLast(a.getKey());
            }
        }
		return res;
	}

	public Iterable<V> values() {
        PositionList<V> res= new SimplyLinkedList<V>();
        for(int i=0;i<Buckets.length;i++){
            for(Entrada<K,V> a:Buckets[i]){
                res.addLast(a.getValue());
            }
        }
		return res;
	}

	public Iterable<Entry<K, V>> entries() {
        PositionList<Entry<K,V>> res= new SimplyLinkedList<Entry<K,V>>();
        for(int i=0;i<Buckets.length;i++){
            for(Entrada<K,V> a:Buckets[i]){
                res.addLast(a);
            }
        }
		return res;
	}
}
