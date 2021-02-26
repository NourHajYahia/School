package main.core.exceptions;

public class DAOException extends CouponsSystemException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
