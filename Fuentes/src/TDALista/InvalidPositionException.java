package TDALista;

/**
 * Class InvalidPositionException.
 * Extiende a la interfaz Exception.
 */
public class InvalidPositionException extends Exception {
	/**
	 * Instancia a un objeto de clase InvalidPositionException.
	 * @param msg El mensaje que describe el evento que disparó la excepción. 
	 */
	public InvalidPositionException(String msg) {
		super(msg);
	}
}
