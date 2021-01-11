package main.core.exceptions;

public class CouponSystemExceprion extends Exception {

	private static final long serialVersionUID = 1L;


	public CouponSystemExceprion() {
		super();
	}


	public CouponSystemExceprion(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CouponSystemExceprion(String message, Throwable cause) {
		super(message, cause);

	}

	public CouponSystemExceprion(String message) {
		super(message);
	}


	public CouponSystemExceprion(Throwable cause) {
		super(cause);
	}
	
	

}
