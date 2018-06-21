package TDALista;

/**
 * Class EmptyListException.
 * Extiende a la interfaz Exception.
 */
public class EmptyListException extends Exception{
	/**
	 * Instancia a un objeto de clase EmptyListException.
	 * @param msg El mensaje que describe el evento que disparó la excepción. 
	 */
	public EmptyListException(String msg){
		super(msg);
	}
}
