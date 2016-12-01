
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
			// jos tietokannassa ei ole taulua olemassa niin alla oleva luo
			// uuden taulun
			// tk.luoTaulu();
			// jos tietokannan taulussa ei ole tietoa niin alla oleva luo uuden
			// rivin
			// tk.luoTietoa();
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
	 *            MySQL Tietokannan nimi
	 * @param user
	 *            Kayttajatunnus MySQL-tietokantaan
	 * @param pass
	 *            salasana MySQL-tietokantaan
	 * @throws Exception
	 */
	public void createConnection(String schema, String user, String pass) throws Exception {
		try {
			// this use mysql
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// this is for MariaDB
			// Class.forName("org.mariadb.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.out.println("virhe 1: Et ole asentanut driveria Build Path:iin"
					+ "\n Lataa osoitteesta https://dev.mysql.com/downloads/connector/j/ "
					+ "\n paketti mysql-connector-java-5.1.40.zip ja pura paketista mysql-connector-java-3.1.11-bin.jar "
					+ "tiedosto projektiin mukaan. Lisää kyseinen tiedosto Build Path:iin");

			// e.printStackTrace();
			throw new Exception("createConnection: JDBC-ajurin rekisterainti epaonnistui.");
		}
		try {
			//Satunnaisia osoitteita, jotka kaikki riippuvat ympäristöstä missä toimitaan
			// String url = "jdbc:mysql://localhost/olioJavatesti";
			// String url = "jdbc:mysql://localhost/" + schema;
			// String url = "jdbc:mysql://192.168.0.121/" + schema;
			// String url = "jdbc:mysql://192.168.1.114/" + schema;
			String url = "jdbc:mysql://130.230.119.250/" + schema;
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
