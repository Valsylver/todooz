package fr.todooz.derby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DerbyTest {
	
	private Connection connection;
	
	@Before
	public void init() throws ClassNotFoundException, SQLException {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		connection = DriverManager.getConnection("jdbc:derby:target/testdb;create=true");
		clean();
	}

	@After
	public void clean() throws SQLException {
		try {
			connection.createStatement().execute("drop table test");
		} 
		catch (SQLException e) {
			if (!e.getMessage().equals("'DROP TABLE' cannot be performed on 'TEST' because it does not exist.")) {
				throw e;
			}
		}
	}

	@Test
	public void createDb() throws ClassNotFoundException, SQLException {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		DriverManager.getConnection("jdbc:derby:target/testdb;create=true");
	}
	
	@Test
	public void createTable() throws ClassNotFoundException, SQLException {
		connection.createStatement().execute("create table test (firstname varchar(30), lastname varchar(30))");
	}
	
	@Test
	public void insert() throws ClassNotFoundException, SQLException {
		createTable();
		PreparedStatement stmt = connection
				.prepareStatement("insert into test (firstname, lastname) values (?, ?)");
		stmt.setString(1, "Groucho");
		stmt.setString(2, "Marx");
		Assert.assertEquals(1, stmt.executeUpdate());
		stmt.close();
	}
	
	@Test
	public void select() throws ClassNotFoundException, SQLException {
	  insert();
	  PreparedStatement stmt = connection.prepareStatement("select firstname from test where lastname = ?");
	  stmt.setString(1, "Marx");
	  ResultSet rs = stmt.executeQuery();
	  rs.next();
	  Assert.assertEquals("Groucho", rs.getString("firstname"));
	  stmt.close();
	}
	
	@Test
	public void delete() throws ClassNotFoundException, SQLException {
	  insert();
	  PreparedStatement stmt = connection.prepareStatement("delete from test where lastname = ?");
	  stmt.setString(1, "Marx");
	  Assert.assertEquals(1, stmt.executeUpdate());
	  stmt.close();
	}


}
