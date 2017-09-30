package es.giiis.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.giiis.pi.model.Calendario;


public class JDBCCalendarDAOImpl implements CalendarDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCCalendarDAOImpl.class.getName());

	@Override
	public List<Calendario> getAll() {

		logger.info("starting");
		if (conn == null) return null;
						
		ArrayList<Calendario> calendars = new ArrayList<Calendario>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Calendar");

			while ( rs.next() ) {
				Calendario calendar = new Calendario();
				calendar.setId(rs.getLong("id"));
				calendar.setName(rs.getString("name"));
				calendar.setOwner(rs.getLong("owner"));
				calendars.add(calendar);
				logger.info("fetching Calendar: "+calendar.getId()+" "+calendar.getName()+" "+calendar.getOwner());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return calendars;
	}

	@Override
	public List<Calendario> getAllByUser(long userid) {
		logger.info("starting");
		if (conn == null) return null;
						
		ArrayList<Calendario> calendars = new ArrayList<Calendario>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Calendar WHERE owner="+userid);

			while ( rs.next() ) {
				Calendario calendar = new Calendario();
				calendar.setId(rs.getLong("id"));
				calendar.setName(rs.getString("name"));
				calendar.setOwner(rs.getLong("owner"));
				calendars.add(calendar);
				logger.info("fetching Calendar: "+calendar.getId()+" "+calendar.getName()+" "+calendar.getOwner());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return calendars;
	}
	
	@Override
	public Calendario get(long id) {
		if (conn == null) return null;
		
		Calendario calendar = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Calendar WHERE id ="+id);			 
			if (!rs.next()) return null; 
			calendar  = new Calendario();	 
			calendar.setId(rs.getLong("id"));
			calendar.setName(rs.getString("name"));
			calendar.setOwner(rs.getLong("owner"));
			logger.info("fetching Calendar: "+calendar.getId()+" "+calendar.getName()+" "+calendar.getOwner());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendar;
	}
	
	

	@Override
	public long add(Calendario calendar) {
		long id = -1;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Calendar (name,owner) VALUES('"+calendar.getName()+"','"+calendar.getOwner()+"')");
				
				ResultSet genKeys = stmt.getGeneratedKeys();
				
				if (genKeys.next())
				    id = genKeys.getInt(1);
				
				
				
				logger.info("creating Calendar: "+calendar.getName()+" - owner "+calendar.getOwner());
			
			
			
			
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return id;
	}

	@Override
	public void save(Calendario calendar) {
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE Calendar SET name='"+calendar.getName()+"', owner='"+calendar.getOwner()+"' WHERE id = "+calendar.getId());
				logger.info("updating Calendar: "+calendar.getId()+" "+calendar.getName()+" "+calendar.getOwner());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
				stmt.executeUpdate("DELETE FROM Calendar WHERE id ="+id);
				logger.info("updating Calendar: "+id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}

	
}
