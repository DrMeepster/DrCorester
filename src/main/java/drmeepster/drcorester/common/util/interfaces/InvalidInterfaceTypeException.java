package drmeepster.drcorester.common.util.interfaces;

/**
 * @author DrMeepster
 */
public class InvalidInterfaceTypeException extends RuntimeException{

	private static final long serialVersionUID = -5584271667376838501L;

	/**
	 * @see RuntimeException#RuntimeException()
	 */
	public InvalidInterfaceTypeException(){
		super();
	}

	/**
	 * @see RuntimeException#RuntimeException(String)
	 */
	public InvalidInterfaceTypeException(String message){
		super(message);
	}

	/**
	 * @see RuntimeException#RuntimeException(String, Throwable)
	 */
	public InvalidInterfaceTypeException(String message, Throwable cause){
		super(message, cause);
	}

	/**
	 * @see RuntimeException#RuntimeException(Throwable)
	 */
	public InvalidInterfaceTypeException(Throwable cause){
		super(cause);
	}

	/**
	 * @see RuntimeException#RuntimeException(String, Throwable, boolean, boolean)
	 */
	protected InvalidInterfaceTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
