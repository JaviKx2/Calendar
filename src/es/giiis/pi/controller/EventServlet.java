package es.giiis.pi.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import es.giiis.pi.dao.CalendarDAO;
import es.giiis.pi.dao.EventDAO;
import es.giiis.pi.dao.JDBCCalendarDAOImpl;
import es.giiis.pi.dao.JDBCEventDAOImpl;
import es.giiis.pi.model.Calendario;
import es.giiis.pi.model.Event;
import es.giiis.pi.model.User;

import java.sql.Connection;
import java.sql.Time;

/**
 * Servlet implementation class EventServlet
 */
@WebServlet("/views/EventServlet")
public class EventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		request.setAttribute("servlet", request.getParameter("servlet"));
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/create_event.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		HttpSession session = request.getSession();
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		CalendarDAO calendarDao = new JDBCCalendarDAOImpl();
		calendarDao.setConnection(conn);
		EventDAO eventDao = new JDBCEventDAOImpl();
		eventDao.setConnection(conn);
		
		User user = (User) session.getAttribute("user");		
		List<Calendario> calendars = calendarDao.getAllByUser(user.getId());	
		if(!calendars.isEmpty()){	
			SimpleDateFormat formatter;
			Time time;
			Event event = new Event();
			event.setName(request.getParameter("name"));
			event.setCalendar(calendars.get(0).getId());
			
			try {
				formatter = new SimpleDateFormat("yyyy-MM-dd");
				event.setStartDate(formatter.parse(request.getParameter("init-date")));
				formatter = new SimpleDateFormat("HH:mm");
				time = new Time(formatter.parse(request.getParameter("init-time")).getTime());
				event.setStartTime(time);
				time = new Time(formatter.parse(request.getParameter("end-time")).getTime());
				event.setEndTime(time);
				formatter = new SimpleDateFormat("yyyy-MM-dd");
				event.setEndDate(formatter.parse(request.getParameter("end-date")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
	
			eventDao.add(event);
		}

		response.sendRedirect(request.getParameter("servlet"));
	}
}
