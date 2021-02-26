package main.core.exceptions;

public class FacadeException extends CouponsSystemException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FacadeException() {
    }

    public FacadeException(String message) {
        super(message);
    }

    public FacadeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FacadeException(Throwable cause) {
        super(cause);
    }
}
