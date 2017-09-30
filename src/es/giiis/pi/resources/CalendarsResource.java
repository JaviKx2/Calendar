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

import es.giiis.pi.dao.CalendarDAO;
import es.giiis.pi.dao.JDBCCalendarDAOImpl;
import es.giiis.pi.model.Calendario;
import es.giiis.pi.resources.exceptions.*;

@Path("/calendars")
public class CalendarsResource {

	  @Context
	  ServletContext sc;
	  @Context
	  UriInfo uriInfo;
  
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<Calendario> getCalendariosJSON() {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CalendarDAO calendarDao = new JDBCCalendarDAOImpl();
		calendarDao.setConnection(conn);
		
		List<Calendario> calendars = calendarDao.getAll();		
	    return calendars; 
	  }
	  
	  @GET
	  @Path("/{calendarid: [0-9]+}")	  
	  @Produces(MediaType.APPLICATION_JSON)
	  public Calendario getCalendarioJSON(@PathParam("calendarid") long calendarid) {
		  
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CalendarDAO calendarDao = new JDBCCalendarDAOImpl();
		calendarDao.setConnection(conn);
		
		Calendario calendar = calendarDao.get(calendarid);
		if (calendar == null) {
		    throw new CustomNotFoundException("Calendario, " + calendarid + ", is not found");
		  }
		
	    return calendar; 
	  }

	  @POST	  	  
	  @Consumes("application/x-www-form-urlencoded")
	  public Response post(
			  MultivaluedMap<String, String> formParams) {		 
		  Response res;
		  Connection conn = (Connection) sc.getAttribute("dbConn");
		  CalendarDAO calendarDao = new JDBCCalendarDAOImpl();
		  calendarDao.setConnection(conn);
	  
		  SimpleDateFormat formatter;
		  Time time;
		  Calendario calendar = new Calendario();
		/*		  
		  calendar.setName(formParams.getFirst("name"));
		  
		  try {
			  formatter = new SimpleDateFormat("yyyy-MM-dd");
			  calendar.setStartDate(formatter.parse(formParams.getFirst("startdate")));
			  formatter = new SimpleDateFormat("HH:mm");
			  time = new Time(formatter.parse(formParams.getFirst("starttime")).getTime());
			  calendar.setStartTime(time);
			  time = new Time(formatter.parse(formParams.getFirst("endtime")).getTime());
			  calendar.setEndTime(time);
			  formatter = new SimpleDateFormat("yyyy-MM-dd");
			  calendar.setEndDate(formatter.parse(formParams.getFirst("enddate")));
		  } catch (ParseException e) {
			  e.printStackTrace();
		  }*/
		  
		  

		  Map<String, String> messages = new HashMap<String, String>();
		  if (!calendar.validate(messages))
				  throw new CustomBadRequestException("Errors in parameters");
		  //save calendar in DB
		  long id = calendarDao.add(calendar);

		  res = Response //return 201 and Location: /calendars/newid
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
	  @Path("/{calendarid: [0-9]+}")	  
	  public Response deleteCalendario(@PathParam("calendarid") long calendarid) {
		  
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CalendarDAO calendarDao = new JDBCCalendarDAOImpl();
		calendarDao.setConnection(conn);
		
		calendarDao.delete(calendarid);
//		if (calendar == null) {
//		    throw new CustomNotFoundException(uriInfo.getAbsolutePath().toString());//"Calendario, " + calendarid + ", is not found");
//		  }
		
	    return Response.noContent().build(); //204 no content 
	  }
	  
} 
