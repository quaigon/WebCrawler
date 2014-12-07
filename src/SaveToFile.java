import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import log.FileOutput;
import log.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class SaveToFile.
 */
public class SaveToFile implements DownloaderSaveError {

	/** The logger. */
	private Log logger = Log.getLog();
	
	/** The file prefix. */
	private String filePrefix;
	
	/** The Constant dateFormat. */
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * Instantiates a new save to file.
	 *
	 * @param filePrefix the file prefix
	 */
	public SaveToFile(String filePrefix) {
		this.filePrefix = filePrefix;
	}

	/* (non-Javadoc)
	 * @see DownloaderSaveError#save(java.net.URL, java.lang.String)
	 */
	@Override
	public void save(URL url, String error) {
		logger.setOutput(new FileOutput("C:\\dev\\Java\\WebCrawler\\"
				+ filePrefix
				+ dateFormat.format(Calendar.getInstance().getTime())
				+ ".txt"));
		logger.e(url.toString() + error);
	}
}
