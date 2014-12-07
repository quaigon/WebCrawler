import java.net.URL;
import java.util.*;

public class Queue implements DownloadQueue {

	private List<URL> pageQueue = new LinkedList<URL>();

	public void addPage(URL pageURL) {
		pageQueue.add(pageURL);
	}

	public boolean isEmpty() {
		if (pageQueue.isEmpty()) {
			return true;
		}
		return false;
	}

	public URL getNextPage() {
		return pageQueue.remove(0);
	}

	public int getNumberOfElements() {
		return pageQueue.size();
	}

}
