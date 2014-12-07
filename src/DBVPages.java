import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBVPages implements VisitedPages {

	private Connection dbConnection;
	
	public DBVPages(Connection dbConnection) {
		this.dbConnection = dbConnection;
	}

	@Override
	public boolean pageAlreadyVisited(URL pageURL) {
		PreparedStatement stmt = null;
		boolean result = false;
        try {
            stmt = dbConnection
                    .prepareStatement("select URL from visitedpages where URL like ?");
            stmt.setString(1, pageURL.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // w kazdym wypadku jesli stmt nie null to go zamknij -
            // zwalnianie zasobow
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
		return result;
	}

	@Override
	public void addVisitedPage(URL pageURL) {
		Statement stmt = null;
		try {
			stmt = dbConnection.createStatement();
		
			stmt.executeUpdate("insert into visitedpages(URL) " + "values( '"
					+ pageURL.toString() + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// w kazdym wypadku jesli stmt nie null to go zamknij -
			// zwalnianie zasobow
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
