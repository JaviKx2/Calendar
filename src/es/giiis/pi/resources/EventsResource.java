package es.giiis.pi.resources;

import java.sql.Connection;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import es.giiis.pi.dao.JDBCEventDAOImpl;
import es.giiis.pi.dao.EventDAO;
import es.giiis.pi.model.Event;
import es.giiis.pi.resources.exceptions.*;

@Path("/events")
public class EventsResource {

	  @Context
	  ServletContext sc;
	  @Context
	  UriInfo uriInfo;
  
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<Event> getEventsJSON() {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		EventDAO eventDao = new JDBCEventDAOImpl();
		eventDao.setConnection(conn);
		
		List<Event> events = eventDao.getAll();		
	    return events; 
	  }
	  
	  @GET
	  @Path("/{eventid: [0-9]+}")	  
	  @Produces(MediaType.APPLICATION_JSON)
	  public Event getEventJSON(@PathParam("eventid") long eventid) {
		  
		Connection conn = (Connection) sc.getAttribute("dbConn");
		EventDAO eventDao = new JDBCEventDAOImpl();
		eventDao.setConnection(conn);
		
		Event event = eventDao.get(eventid);
		if (event == null) {
		    throw new CustomNotFoundException("Event, " + eventid + ", is not found");
		  }
		
	    return event; 
	  }

	  @POST	  	  
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response post(Event e) {		 
		  Response res;
		  Connection conn = (Connection) sc.getAttribute("dbConn");
		  EventDAO eventDao = new JDBCEventDAOImpl();
		  eventDao.setConnection(conn);
	  
		  
		  long id = eventDao.add(e);
		  
		  System.out.println("DFDF"+e.getStartDate());
		  System.out.println(e.getStartTime());
		  System.out.println(e.getEndTime());
		  System.out.println(e.getEndDate());

		  res = Response //return 201 and Location: /events/newid
				  .created(
						  uriInfo
						  .getAbsolutePathBuilder()
						  .path(Long.toString(id))
						  .build())
						  .contentLocation(
								  uriInfo
								  .getAbsolutePathBuilder()
								  .path(Long.toString(id))
								  .build())
				  .build();

		  return res; 
	  }
	  
	  
	  @DELETE
	  @Path("/{eventid: [0-9]+}")	  
	  public Response deleteEvent(@PathParam("eventid") long eventid) {
		  
		Connection conn = (Connection) sc.getAttribute("dbConn");
		EventDAO eventDao = new JDBCEventDAOImpl();
		eventDao.setConnection(conn);
		
		eventDao.delete(eventid);
//		if (event == null) {
//		    throw new CustomNotFoundException(uriInfo.getAbsolutePath().toString());//"Event, " + eventid + ", is not found");
//		  }
		
	    return Response.noContent().build(); //204 no content 
	  }
	  
} 
