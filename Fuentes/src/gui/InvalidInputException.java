package gui;

/**
 * Class InvalidInputException.
 * Extiende a la interfaz Exception
 *
 */
public class InvalidInputException extends Exception{
	/**
	 * Instancia a un objeto de clase InvalidInputException.
	 * @param msg El mensaje que describe el evento que disparó la excepción. 
	 */
	public InvalidInputException(String msg) {
		super(msg);
	}
}
