package com.skillstorm.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.skillstorm.beans.Flight;
import com.skillstorm.data.FlightDAO;

import static org.junit.Assert.*;

public class testDAO {
	private final static String url = "jdbc:mysql://localhost:3306/flights";
	private final static String username = "root";
	private final static String password = "root";

	FlightDAO dao = new FlightDAO();
	
	@Test 
	public void testCreate() { 
		try { 
			String sql = "select count(*) from flight";
			Connection conn = DriverManager.getConnection(url, username, password); 
			Statement stmt = conn.createStatement(); 
			ResultSet rs1 = stmt.executeQuery(sql);
			rs1.next();
			int rowsBefore = rs1.getInt(1);
			
			dao.create(new Flight("Las Vegas", "Topeka", "Delta", "C13"));
			System.out.println("Row Inserted"); 
			
			ResultSet rs2 = stmt.executeQuery(sql);
			rs2.next();
			int rowsAfter = rs2.getInt(1);
			
			assertEquals(rowsAfter, ++rowsBefore);
			conn.close(); 
		} catch (Exception e) {
			fail(); 
		}
	}
	
	@Test 
	public void testUpdate() { 
		try { 
			String sql = "select count(*) from flight";
			Connection conn = DriverManager.getConnection(url, username, password); 
			Statement stmt = conn.createStatement(); 
			ResultSet rs1 = stmt.executeQuery(sql);
			rs1.next();
			int rowsBefore = rs1.getInt(1);
			
			dao.update(new Flight(1, "Las Vegas", "Topeka", "Delta", "C13"));
			System.out.println("Row Updated"); 
			
			ResultSet rs2 = stmt.executeQuery(sql);
			rs2.next();
			int rowsAfter = rs2.getInt(1);
			
			assertEquals(rowsAfter, rowsBefore);
			conn.close(); 
		} catch (Exception e) {
			fail(); 
		}
	}
	
	@Test 
	public void testDelete() { 
		try { 
			String sql = "select count(*) from flight";
			Connection conn = DriverManager.getConnection(url, username, password); 
			Statement stmt = conn.createStatement(); 
			ResultSet rs1 = stmt.executeQuery(sql);
			rs1.next();
			int rowsBefore = rs1.getInt(1);
			
			dao.delete(1);
			System.out.println("Row Deleted"); 
			
			ResultSet rs2 = stmt.executeQuery(sql);
			rs2.next();
			int rowsAfter = rs2.getInt(1);
			
			assertEquals(rowsAfter, --rowsBefore);
			conn.close(); 
		} catch (Exception e) {
			fail(); 
		}
	}
	
	@Before
	public void beforeTest() { //create table ddl //insert test rows dml
		try { 
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("CREATE TABLE `flight` (`id` INT NOT NULL AUTO_INCREMENT,`arrivalLocation` VARCHAR(45) NOT NULL, `departureLocation` VARCHAR(45) NOT NULL, `airline` VARCHAR(45) NOT NULL, `gateNumber` VARCHAR(45) NOT NULL, PRIMARY KEY (`id`));");
			stmt.executeUpdate("insert into flight (arrivalLocation, departureLocation, airline, gateNumber) values ('Guernica', 'El Cacao', 'Spirit', 'C12'), ('Veshnyaki', 'Muonio', 'Frontier Airlines', 'C15'), ('Sanlifan', 'Salgueiro', 'Frontier Airlines', 'A10');");
			System.out.println("Table Created");
			conn.close();
		} catch (Exception e) {
			fail(); 
		} 
	}

	@After
	public void afterTest() { 
		try { 
			Connection conn = DriverManager.getConnection(url, username, password); 
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("drop table flight");
			System.out.println("Table Dropped");
			conn.close(); 
		} catch (Exception e) {
			fail();
		}
	}
}
