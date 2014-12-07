
// TODO: Auto-generated Javadoc
/**
 * The Class DownloaderException.
 *
 * @author Kamil
 */
public class DownloaderException extends Exception {
		final ExceptionReason reason;

	/**
	 * Instantiates a new downloader exception.
	 */
	public DownloaderException(ExceptionReason reason) {
		this.reason = reason;
	}

	/**
	 * Instantiates a new downloader exception.
	 *
	 * @param arg0 the arg0
	 */
	public DownloaderException(String message, ExceptionReason reason ) {
		super(message);
		this.reason = reason;
		// TODO Auto-generated constructor stub
	}
	
	public DownloaderException(String message, Throwable ex, ExceptionReason reason ) { 
		super(message, ex );
		this.reason = reason;
	}
}
