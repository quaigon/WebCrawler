import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQueue implements DownloadQueue {

	private Connection dbConnection;

	public DBQueue(Connection dbConnection) {
		this.dbConnection = dbConnection;
	}

	@Override
	public void addPage(URL pageURL) {
		Statement stmt = null;
		try {
			stmt = dbConnection.createStatement();
			stmt.executeUpdate("insert into downloadqueue (URL) " + "values( '"
					+ pageURL.toString() + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public boolean isEmpty() {
		boolean result = false;
		int elementsCount = -1;

		PreparedStatement stmt = null;
		try {
			stmt = dbConnection
					.prepareStatement("select count(*) from downloadqueue");
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				elementsCount = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		result = elementsCount <= 0 ? true : false;
		return result;
	}

	@Override
	public URL getNextPage() {
		URL result = null;
		PreparedStatement stmt = null;

		try {
			stmt = dbConnection
					.prepareStatement("select id,url from downloadqueue");
			ResultSet rs = stmt.executeQuery();
			rs.next();
			System.out.println(rs.getString(2));
			try {
				result = new URL(rs.getString(2));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stmt = dbConnection
					.prepareStatement("delete from downloadqueue where id=select min(id) from downloadqueue");
			stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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

}
