package com.skillstorm.beans;

import java.util.Objects;

public class Flight implements Comparable<Flight>{
	private int id;
	private String arrivalLocation;
	private String departureLocation;
	private String airline;
	private String gateNumber;
	
	@Override
	public String toString() {
		return "Flight [id=" + id + ", arrivalLocation=" + arrivalLocation + ", departureLocation=" + departureLocation
				+ ", airline=" + airline + ", gateNumber=" + gateNumber
				+ "]";
	}
	
	public Flight() {
		super();
	}

	public Flight(String arrivalLocation, String departureLocation, String airline, String gateNumber) {
		super();
		this.arrivalLocation = arrivalLocation;
		this.departureLocation = departureLocation;
		this.airline = airline;
		this.gateNumber = gateNumber;
	}

	public Flight(int id, String arrivalLocation, String departureLocation, String airline, String gateNumber) {
		super();
		this.id = id;
		this.arrivalLocation = arrivalLocation;
		this.departureLocation = departureLocation;
		this.airline = airline;
		this.gateNumber = gateNumber;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getArrivalLocation() {
		return arrivalLocation;
	}

	public void setArrivalLocation(String arrivalLocation) {
		this.arrivalLocation = arrivalLocation;
	}

	public String getDepartureLocation() {
		return departureLocation;
	}

	public void setDepartureLocation(String departureLocation) {
		this.departureLocation = departureLocation;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getGateNumber() {
		return gateNumber;
	}

	public void setGateNumber(String gateNumber) {
		this.gateNumber = gateNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(airline, arrivalLocation, departureLocation, gateNumber, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		return Objects.equals(airline, other.airline) && Objects.equals(arrivalLocation, other.arrivalLocation)
				&& Objects.equals(departureLocation, other.departureLocation)
				&& Objects.equals(gateNumber, other.gateNumber) && id == other.id;
	}

	@Override
	public int compareTo(Flight other) {
		return this.id - other.id;
	}
}
