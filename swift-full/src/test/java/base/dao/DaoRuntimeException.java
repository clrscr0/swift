package base.dao;

public class DaoRuntimeException extends RuntimeException {
	private static final long serialVersionUID = -6579475271949951127L;

	public DaoRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}
