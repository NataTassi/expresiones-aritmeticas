package TDAArbolBinario;

/**
 * Class InvalidOperationException.
 * Extiende a la interfaz Exception.
 */
public class InvalidOperationException extends Exception {

	/**
	 * Instancia a un objeto de clase InvalidOperationException.
	 * @param msg El mensaje que describe el evento que disparó la excepción. 
	 */
	public InvalidOperationException(String msg) {
		super(msg);
	}
}
