
public class WebCrawlerConfig {
	
	public final String errorLogFilePrefix;
	private final int period;
	
	public WebCrawlerConfig(String errorLogFilePrefix, int period) {
		this.errorLogFilePrefix = errorLogFilePrefix;
		this.period = period;
	}

	public int getPeriod () {
		return period;
	}
}
