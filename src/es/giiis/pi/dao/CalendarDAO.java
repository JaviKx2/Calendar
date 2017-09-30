package es.giiis.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.giiis.pi.model.Calendario;


public interface CalendarDAO {
	
	public List<Calendario> getAll();
	public List<Calendario>  getAllByUser(long userid);
	public Calendario get(long id);	
	public long add(Calendario Calendar);
	public void save(Calendario Calendar);
	public void delete(long id);
	
	public void setConnection(Connection conn);
}
