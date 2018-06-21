package TDAArbolBinario;

/**
 * Class EmptyTreeException.
 * Extiende a la interfaz Exception.
 */
public class EmptyTreeException extends Exception {
	/**
	 * Instancia a un objeto de clase EmptyTreeException.
	 * @param msg El mensaje que describe el evento que disparó la excepción. 
	 */
	public EmptyTreeException(String msg) {
		super(msg);
	}
}
