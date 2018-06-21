package TDAArbolBinario;

/**
 * Class BoundaryViolationException.
 * Extiende a la interfaz Exception.
 */
public class BoundaryViolationException extends Exception {
	/**
	 * Instancia a un objeto de clase BoundaryViolationException.
	 * @param msg El mensaje que describe el evento que disparó la excepción. 
	 */
	public BoundaryViolationException(String msg) {
		super(msg);
	}
}
