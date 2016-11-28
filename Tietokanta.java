
package bitnamitesti;
/**
 * @author miksa	mika.saari@tut.fi 
 * 
 * @date 28.11.2016
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Tietokanta {
	private Connection con;

	public static void main(String[] args) {
		Tietokanta tk = new Tietokanta();
		try {
			System.out.println("alkoi");
			tk.createConnection("javatesti2", "testaaja2", "salasana");
			// tk.luoTaulu();
			//tk.luoTietoa();
			tk.tulostaTiedot();
			System.out.println("Loppu");
		} catch (Exception e) {
			System.out.println("Virhe tulee");
		}
	}

	/**
	 * Luodaan yhteys tietokantaan
	 * 
	 * @param schema
	 * @param user
	 * @param pass
	 * @throws Exception
	 */
	public void createConnection(String schema, String user, String pass) throws Exception {
		try {
			// this use mysql
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// this is for MariaDB
			// Class.forName("org.mariadb.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.out.println("virhe 1");
			e.printStackTrace();
			throw new Exception("createConnection: JDBC-ajurin rekisterainti epaonnistui.");
		}
		try {
			// String url = "jdbc:mysql://localhost/olioJavatesti";
			// String url = "jdbc:mysql://localhost/" + schema;
			// String url = "jdbc:mysql://192.168.0.121/" + schema;
			String url = "jdbc:mysql://192.168.1.114/" + schema;
			con = DriverManager.getConnection(url, user, pass);
		} catch (SQLException se) {
			System.out.println("virhe 2");
			se.printStackTrace();
			System.out.println("createConnection: Login error");

		}
	}

	void luoTaulu() throws Exception {
		String sql;
		Statement stmt = null;

		try {
			stmt = con.createStatement();

			sql = "CREATE TABLE USERS (" + " NO   INTEGER   NOT NULL AUTO_INCREMENT PRIMARY KEY,"
					+ " NAME VARCHAR(40) NOT NULL," + " PHONENUMBER VARCHAR(40) ," + " YEAR INTEGER)";
			System.out.println(sql);
			stmt.executeUpdate(sql);
		} catch (SQLException se) {
			System.out.println("virhe 3");
			se.printStackTrace();
			throw new Exception("Taulujen luonti epaonnistui.");
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	void luoTietoa() throws Exception {
		String sql;
		Statement stmt = null;

		try {
			stmt = con.createStatement();
			// http://www.w3schools.com/sql/sql_insert.asp
			sql = "INSERT INTO USERS(NAME, PHONENUMBER, YEAR) VALUES ('Masa', '11234567', 2000)";
			System.out.println(sql);
			stmt.executeUpdate(sql);
		} catch (SQLException se) {
			System.out.println("virhe tietojensyotossa");
			se.printStackTrace();
			throw new Exception("Tietojen syotto epaonnistui.");
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	void tulostaTiedot() throws Exception {
		String sql;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			sql = "SELECT * FROM USERS";
			rs = stmt.executeQuery(sql);
			System.out.println("Tulostus alkoi");
			while (rs.next()) {
				int id = rs.getInt("NO");
				String nimi = rs.getString("NAME");
				String numero = rs.getString("PHONENUMBER");
				int vuosi = rs.getInt(3);

				System.out.println(id + " " + nimi + " " + numero + " " + vuosi);

			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("printBooks: Virhe");

		}
	}
}
