package com.skillstorm.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.beans.Flight;
import com.skillstorm.data.FlightDAO;

@WebServlet(urlPatterns = "/api/flight")
public class FlightServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	FlightDAO dao = new FlightDAO();
	
	//GET request to /api/flight
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//resp.addHeader("Access-Control-Allow-Origin", "*");
		if(req.getParameter("id") != null) {
			String param = req.getParameter("id");
			int id = Integer.parseInt(param);
			Flight flight = dao.findById(id); //JDBC
			String json = new ObjectMapper().writeValueAsString(flight); //convert java object to json 
			//System.out.println(json);
			resp.getWriter().print(json); //write the data to the response
		}else {
			Set<Flight> flights = dao.findAll();
			String json = new ObjectMapper().writeValueAsString(flights);
			resp.getWriter().print(json);
		}
	}
	
	//HttpServlet has methods to handle each type of HTTP request method: GET, POST, PUT, DELETE
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//resp.addHeader("Access-Control-Allow-Origin", "*");
		InputStream requestBody = req.getInputStream();
		//convert the request body into a Flight.class object
		Flight flight = new ObjectMapper().readValue(requestBody, Flight.class);
		System.out.println(flight);
		Flight updated = dao.create(flight);
		//return back updated flight
		resp.getWriter().print(new ObjectMapper().writeValueAsString(updated));
		resp.setStatus(201);
		resp.setContentType("application/json");
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//resp.addHeader("Access-Control-Allow-Origin", "*");
		InputStream requestBody = req.getInputStream();
		
		Flight updatedFlight = new ObjectMapper().readValue(requestBody, Flight.class);
		//Flight originalFlight = dao.findById(updatedFlight.getId());
		dao.update(updatedFlight);
		resp.getWriter().print(new ObjectMapper().writeValueAsString(updatedFlight));
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//resp.addHeader("Access-Control-Allow-Origin", "*");
		if(req.getParameter("id") != null) {
			String param = req.getParameter("id");
			int id = Integer.parseInt(param);
			dao.delete(id);
		}
	}
}
