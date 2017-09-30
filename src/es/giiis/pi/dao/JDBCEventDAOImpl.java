package es.giiis.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.giiis.pi.model.Event;



public class JDBCEventDAOImpl implements EventDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCEventDAOImpl.class.getName());

	@Override
	public List<Event> getAll() {

		logger.info("starting");
		if (conn == null) return null;
						
		ArrayList<Event> events = new ArrayList<Event>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Event ORDER BY startdate ASC");

			while ( rs.next() ) {
				Event event = new Event();
				event.setId(rs.getLong("id"));
				event.setName(rs.getString("name"));
				event.setCalendar(rs.getLong("calendar"));
				event.setStartDate(rs.getDate("startdate"));
				event.setStartTime(rs.getTime("starttime"));
				event.setEndDate(rs.getDate("enddate"));
				event.setEndTime(rs.getTime("endtime"));
				events.add(event);
				logger.info("fetching Event: "+event.getId()+" "+event.getName()+" "+event.getCalendar()
						+" "+event.getStartDate()+" "+event.getStartTime());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return events;
	}

	@Override
	public List<Event> getAllByMonth(long calendarId, int month, int year) {

		logger.info("starting");
		if (conn == null) return null;
						
		ArrayList<Event> events = new ArrayList<Event>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Event WHERE calendar="
											+ calendarId
											+" AND MONTH(startdate)="+month+
												" AND YEAR(startdate)="+year);

			while ( rs.next() ) {
				Event event = new Event();
				event.setId(rs.getLong("id"));
				event.setName(rs.getString("name"));
				event.setCalendar(rs.getLong("calendar"));
				event.setStartDate(rs.getDate("startdate"));
				event.setStartTime(rs.getTime("starttime"));
				event.setEndDate(rs.getDate("enddate"));
				event.setEndTime(rs.getTime("endtime"));
				events.add(event);
				logger.info("fetching Event: "+event.getId()+" "+event.getName()+" "+event.getCalendar()
						+" "+event.getStartDate()+" "+event.getStartTime());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return events;
	}

	@Override
	public List<Event> getAllByWeek(long calendarId, String startDay, String endDay) {

		logger.info("starting");
		if (conn == null) return null;
						
		ArrayList<Event> events = new ArrayList<Event>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Event WHERE calendar="
											+ calendarId
											+ " AND startdate BETWEEN '"+startDay+"' AND '"
											+endDay+"'");

			while ( rs.next() ) {
				Event event = new Event();
				event.setId(rs.getLong("id"));
				event.setName(rs.getString("name"));
				event.setCalendar(rs.getLong("calendar"));
				event.setStartDate(rs.getDate("startdate"));
				event.setStartTime(rs.getTime("starttime"));
				event.setEndDate(rs.getDate("enddate"));
				event.setEndTime(rs.getTime("endtime"));
				events.add(event);
				logger.info("fetching Event: "+event.getId()+" "+event.getName()+" "+event.getCalendar()
						+" "+event.getStartDate()+" "+event.getStartTime());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return events;
	}
	@Override
	public List<Event> getAllByDay(long calendarId, String startDay) {

		logger.info("starting");
		if (conn == null) return null;
						
		ArrayList<Event> events = new ArrayList<Event>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Event WHERE calendar="
											+ calendarId
											+" AND startdate='"+startDay+"'");

			while ( rs.next() ) {
				Event event = new Event();
				event.setId(rs.getLong("id"));
				event.setName(rs.getString("name"));
				event.setCalendar(rs.getLong("calendar"));
				event.setStartDate(rs.getDate("startdate"));
				event.setStartTime(rs.getTime("starttime"));
				event.setEndDate(rs.getDate("enddate"));
				event.setEndTime(rs.getTime("endtime"));
				events.add(event);
				logger.info("fetching Event: "+event.getId()+" "+event.getName()+" "+event.getCalendar()
						+" "+event.getStartDate()+" "+event.getStartTime());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return events;
	}
	


	@Override
	public List<Event> getAllByCalendar(long calendarId) {
		logger.info("starting");
		if (conn == null) return null;
						
		ArrayList<Event> events = new ArrayList<Event>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Event WHERE calendar="+calendarId);

			while ( rs.next() ) {
				Event event = new Event();
				event.setId(rs.getLong("id"));
				event.setName(rs.getString("name"));
				event.setCalendar(rs.getLong("calendar"));
				event.setStartDate(rs.getDate("startdate"));
				event.setStartTime(rs.getTime("starttime"));
				event.setEndDate(rs.getDate("enddate"));
				event.setEndTime(rs.getTime("endtime"));
				events.add(event);
				logger.info("fetching Event: "+event.getId()+" "+event.getName()+" "+event.getCalendar()
						+" "+event.getStartDate()+" "+event.getStartTime());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return events;
	}
	
	@Override
	public Event get(long id) {
		if (conn == null) return null;
		
		Event event = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Event WHERE id ="+id);			 
			if (!rs.next()) return null; 
			event = new Event();	 
			event.setId(rs.getLong("id"));
			event.setName(rs.getString("name"));
			event.setCalendar(rs.getLong("calendar"));
			event.setStartDate(rs.getDate("startdate"));
			event.setStartTime(rs.getTime("starttime"));
			event.setEndTime(rs.getTime("endtime"));
			event.setEndDate(rs.getDate("enddate"));
			
			
			logger.info("fetching Event: "+event.getId()+" "+event.getName()+" "+event.getCalendar()
					+" "+event.getStartDate()+" "+event.getStartTime());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return event;
	}
	
	

	@Override
	public long add(Event event) {
		long id = -1;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Event (name,calendar,startdate,starttime,endtime,enddate) VALUES('"+
									event.getName()+"','"+
									event.getCalendar()+"','"+
									new java.sql.Date(event.getStartDate().getTime())+"','"+
									event.getStartTime()+"','"+
									event.getEndTime()+"','"+
									new java.sql.Date(event.getEndDate().getTime())
									+"')");
				
				ResultSet genKeys = stmt.getGeneratedKeys();
				
				if (genKeys.next())
				    id = genKeys.getInt(1);
				
				logger.info("creating Event: "+event.getName()+" - owner "+event.getCalendar());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	@Override
	public void save(Event event) {
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				logger.info("DATE!!! "+new java.sql.Date(event.getStartDate().getTime()));
				stmt.executeUpdate("UPDATE Event SET "
									+ "name='"+event.getName()
									+"', calendar='"+event.getCalendar()
									+"', startdate='"+new java.sql.Date(event.getStartDate().getTime())
									+"', starttime='"+event.getStartTime()
									+"', endtime='"+event.getEndTime()
									+"', enddate='"+new java.sql.Date(event.getEndDate().getTime())
									+"' WHERE id = "+event.getId());
				logger.info("updating Event: "+event.getId()+" "+event.getName()+" "+event.getCalendar());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void delete(long id) {
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM Event WHERE id ="+id);
				logger.info("updating Event: "+id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	
}
