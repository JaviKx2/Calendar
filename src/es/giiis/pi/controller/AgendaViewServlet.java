package es.giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.giiis.pi.dao.CalendarDAO;
import es.giiis.pi.dao.EventDAO;
import es.giiis.pi.dao.JDBCCalendarDAOImpl;
import es.giiis.pi.dao.JDBCEventDAOImpl;
import es.giiis.pi.model.Event;
import es.giiis.pi.model.Calendario;
import es.giiis.pi.model.User;

/**
 * Servlet implementation class AgendaViewServlet
 */
@WebServlet("/views/AgendaViewServlet")
public class AgendaViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AgendaViewServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		CalendarDAO calendarDao = new JDBCCalendarDAOImpl();
		calendarDao.setConnection(conn);
		EventDAO eventDao = new JDBCEventDAOImpl();
		eventDao.setConnection(conn);
		
		User user = (User) session.getAttribute("user");		
		List<Calendario> calendars = calendarDao.getAllByUser(user.getId());	
		if(!calendars.isEmpty()){
			List<Event> events = eventDao.getAllByCalendar(calendars.get(0).getId());
			request.setAttribute("events", events);
		}
		request.setAttribute("servlet", "AgendaViewServlet");

		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/agenda_view.jsp");
		view.forward(request, response);
	}

}
