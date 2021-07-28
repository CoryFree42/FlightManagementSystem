package com.skillstorm.data;

import java.util.Set;

import com.skillstorm.beans.Flight;

public class exapleDAO {

	public static void main(String[] args) {
		FlightDAO dao = new FlightDAO();
		//Flight updated = dao.create(new Flight("New York", "Washington", "United", "C13"));
		Flight updated = new Flight(503, "New York", "Montana", "United", "C13");
		dao.update(updated);
		//System.out.println(updated.getId());
		//dao.delete(6);
	}
}
