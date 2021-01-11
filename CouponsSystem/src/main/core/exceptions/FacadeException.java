package main.core.exceptions;

public class FacadeException extends CouponSystemExceprion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public FacadeException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public FacadeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public FacadeException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public FacadeException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public FacadeException(Throwable cause) {
		super(cause);
	}
	
	

}