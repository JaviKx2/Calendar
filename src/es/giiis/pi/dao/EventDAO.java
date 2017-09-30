package es.giiis.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.giiis.pi.model.Event;



public interface EventDAO {
	
	public List<Event> getAll();
	public List<Event> getAllByCalendar(long calendarid);
	public List<Event> getAllByMonth(long calendarId, int month, int year);
	public List<Event> getAllByDay(long calendarId, String startDay);
	public List<Event> getAllByWeek(long calendarId, String startDay, String endDay);
	public Event get(long id);	
	public long add(Event event);
	public void save(Event event);
	public void delete(long id);
	
	public void setConnection(Connection conn);
	
}
