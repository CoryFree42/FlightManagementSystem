package com.skillstorm.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.skillstorm.beans.Flight;

public class FlightDAO {
	
	private final static String url = "jdbc:mysql://localhost:3306/flights";
	private final static String username = "root";
	private final static String password = "root";
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver didn't load properly.");
			e.printStackTrace();
		}
	}
	
	//CRUD: Create, Retrieve, Update, Delete
	public Flight create(Flight flight) {
		//try-with resources block
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			String sql = "insert into Flight(arrivalLocation, departureLocation, airline, gateNumber) values (?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //generated keys
			stmt.setString(1, flight.getArrivalLocation());
			stmt.setString(2, flight.getDepartureLocation());
			stmt.setString(3, flight.getAirline());
			stmt.setString(4,  flight.getGateNumber());
			stmt.executeUpdate(); //key is generated here
			
			ResultSet keys = stmt.getGeneratedKeys();
			keys.next(); //returns 1 row
			int id = keys.getInt(1); //generated primary key
			flight.setId(id); //update id in java
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return flight;
	}
	
	public void update(Flight flight) {
		try(Connection conn = DriverManager.getConnection(url, username, password)) {
			String sql = "update flight set arrivalLocation = ?, departureLocation = ?, airline = ?, gateNumber = ? where id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, flight.getArrivalLocation());
			stmt.setString(2, flight.getDepartureLocation());
			stmt.setString(3, flight.getAirline());
			stmt.setString(4, flight.getGateNumber());
			stmt.setInt(5, flight.getId());
			stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		try(Connection conn = DriverManager.getConnection(url, username, password)) {
			String sql = "delete from flight where id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Flight findById(int id) {
		try(Connection conn = DriverManager.getConnection(url, username, password)) {
			String sql = "select id, arrivalLocation, departureLocation, airline, gateNumber from flight where id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			return new Flight(id, rs.getString("arrivalLocation"), rs.getString("departureLocation"), rs.getString("airline"), rs.getString("gateNumber"));
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Set<Flight> findAll() {
		Set<Flight> results = new TreeSet<>();
		try(Connection conn = DriverManager.getConnection(url, username, password)) {
			String sql = "select id, arrivalLocation, departureLocation, airline, gateNumber from flight order by id asc";
			PreparedStatement stmt = conn.prepareStatement(sql);
			//returns rows from the query, starts at row 0 (full of null)
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				int id =rs.getInt("id");
				String arrival = rs.getString("arrivalLocation");
				String departure = rs.getString("departureLocation");
				String airline = rs.getString("airline");
				String gate = rs.getString("gateNumber");
				Flight flight = new Flight(id, arrival, departure, airline, gate);
				results.add(flight);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return results;
	}
}