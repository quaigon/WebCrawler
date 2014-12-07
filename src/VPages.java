import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class VPages implements VisitedPages {

	private List<URL> pages = new LinkedList<URL>();


	public boolean pageAlreadyVisited(URL pageURL) {
		if (pages.contains(pageURL)) {
			return true;
		}
		return false;
	}

	public void addVisitedPage(URL pageURL) {
		pages.add(pageURL);
	}
	public int getNumberOfVPages () {
		return pages.size();
	}
}
