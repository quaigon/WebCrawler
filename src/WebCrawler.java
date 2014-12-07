import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class WebCrawler.
 */
public class WebCrawler {

	private static final String DB_URL = "jdbc:h2:tcp://localhost/~/crawlerbase";
	private static final String DB_USER = "sa";
	private static final String DB_PASSWD = "";

	/**
	 * Creates the url.
	 * 
	 * @param path
	 *            the path
	 * @return url
	 */
	public static String createURL(String path) {
		String result = "";
		Pattern pattern = Pattern.compile("<a href=\"h?t?t?p?s?:?//(.+)\"");
		Matcher matcher = pattern.matcher(path);
		if (matcher.find()) {
			result = matcher.group(1);

		}

		return "http://" + result;
	}

	/**
	 * Find url.
	 * 
	 * @param pageContent
	 *            the page content
	 */
	public static void findUrl(Connection dbConnection, String pageContent,
			DownloadQueue queue) {
		String url = "<[Aa] +[Hh][Rr][Ee][Ff] *= *\"([^\"]+)\"";
		Pattern pattern = Pattern.compile(url);

		Matcher matcher = pattern.matcher(pageContent);

		while (matcher.find()) {
			URL page;
			try {
				if (matcher.group(0).contains("//")) {
					// System.out.println(createURL(matcher.group(0)));
					page = new URL(createURL(matcher.group(0)));
					// queue.addPage(page);
					queue.addPage(page);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		PageDownloader pageDownloader = new PageDownloader();
		WebCrawlerConfig config = new WebCrawlerConfig("error_log_", 1);
		SaveToFile FileSaver = new SaveToFile(config.errorLogFilePrefix);
		long date = System.currentTimeMillis();
		long pagesVisted = 0;
		String strona;
		DBQueue dbqueue = null;
		DBVPages dbvpages = null; 
		try {
			dbqueue = new DBQueue(DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWD));
			dbvpages = new DBVPages(DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWD));
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//
		// try {
		// strona = pageDownloader.downloadPage("http://stackoverflow.com");
		// findUrl(conn,strona);
		//
		// } catch (DownloaderException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWD);
			try {
				strona = pageDownloader
						.downloadPage("http://stackoverflow.com");
				findUrl(conn, strona, dbqueue);
				dbqueue.getNextPage();
				dbqueue.getNextPage();
			} catch (DownloaderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (!dbqueue.isEmpty()) {
			URL page = dbqueue.getNextPage();
			if (config.getPeriod() > 0
					&& System.currentTimeMillis() - date > config.getPeriod() * 1000) {
				System.out.println(pagesVisted / config.getPeriod());
				date = System.currentTimeMillis();
				pagesVisted = 0;
			}

			if (!dbvpages.pageAlreadyVisited(page)) {
				pagesVisted++;
				dbvpages.addVisitedPage(page);
				try {
					System.out.println(page.toString());
					pageDownloader.downloadPage(page.toString());
					// System.out
					// .println(pageDownloader.downloadPage(page.toString()));
				} catch (DownloaderException e) {
					FileSaver.save(page, e.reason.toString());
					e.printStackTrace();
				}
			}
		}

	}

}
