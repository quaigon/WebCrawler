import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// TODO: Auto-generated Javadoc
/**
 * The Class PageDownloader.
 */
public class PageDownloader implements WWWPageDownloader {

	public String downloadPage(String pageURL) throws DownloaderException {
		URL url;
		InputStream is = null;
		BufferedReader br;
		String line;
		StringBuilder page = new StringBuilder();

		try {
			url = new URL(pageURL);
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
		    httpcon.addRequestProperty("User-Agent", "Mozilla/4.0");

			is = httpcon.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));

			while ((line = br.readLine()) != null) {
				page.append(line);
				page.append(System.lineSeparator());
			}

			return page.toString();

		} catch (MalformedURLException mue) {			
			throw new DownloaderException("Bledny URL", mue, ExceptionReason.URLException);
		} catch (IOException ioe) {
			throw new DownloaderException(
					"Podczas pobierania strony wyst¹pi³ b³¹d I/O", ioe, ExceptionReason.IOException);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException ioe) {
			}
		}
	}

}
