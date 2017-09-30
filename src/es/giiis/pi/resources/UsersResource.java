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

import es.giiis.pi.dao.JDBCUserDAOImpl;
import es.giiis.pi.dao.UserDAO;
import es.giiis.pi.model.User;
import es.giiis.pi.resources.exceptions.*;

@Path("/users")
public class UsersResource {

	  @Context
	  ServletContext sc;
	  @Context
	  UriInfo uriInfo;
	  
	  @GET
	  @Path("/{userid: [0-9]+}")	  
	  @Produces(MediaType.APPLICATION_JSON)
	  public User getUserJSON(@PathParam("userid") long userid) {
		  
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		
		User user = userDao.get(userid);
		if (user == null) {
		    throw new CustomNotFoundException("User, " + userid + ", is not found");
		  }
		
	    return user; 
	  }

	  @POST	  	  
	  @Consumes("application/x-www-form-urlencoded")
	  public Response post(
			  MultivaluedMap<String, String> formParams) {		 
		  Response res;
		  Connection conn = (Connection) sc.getAttribute("dbConn");
		  UserDAO userDao = new JDBCUserDAOImpl();
		  userDao.setConnection(conn);
	  
		  SimpleDateFormat formatter;
		  Time time;
		  User user = new User();
				  
		 /* user.setName(formParams.getFirst("name"));
		  
		  try {
			  formatter = new SimpleDateFormat("yyyy-MM-dd");
			  user.setStartDate(formatter.parse(formParams.getFirst("startdate")));
			  formatter = new SimpleDateFormat("HH:mm");
			  time = new Time(formatter.parse(formParams.getFirst("starttime")).getTime());
			  user.setStartTime(time);
			  time = new Time(formatter.parse(formParams.getFirst("endtime")).getTime());
			  user.setEndTime(time);
			  formatter = new SimpleDateFormat("yyyy-MM-dd");
			  user.setEndDate(formatter.parse(formParams.getFirst("enddate")));
		  } catch (ParseException e) {
			  e.printStackTrace();
		  }*/
		  
		  

		  Map<String, String> messages = new HashMap<String, String>();
		  if (!user.validate(messages))
				  throw new CustomBadRequestException("Errors in parameters");
		  //save user in DB
		  long id = userDao.add(user);

		  res = Response //return 201 and Location: /users/newid
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
	  @Path("/{userid: [0-9]+}")	  
	  public Response deleteUser(@PathParam("userid") long userid) {
		  
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		
		userDao.delete(userid);
//		if (user == null) {
//		    throw new CustomNotFoundException(uriInfo.getAbsolutePath().toString());//"User, " + userid + ", is not found");
//		  }
		
	    return Response.noContent().build(); //204 no content 
	  }
	  
} 
