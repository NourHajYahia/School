package app.core.exceptions;

public class LoginManagerException extends CouponSystemExceprion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public LoginManagerException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public LoginManagerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LoginManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public LoginManagerException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public LoginManagerException(Throwable cause) {
		super(cause);
	}
	
	

}
