package es.giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
import es.giiis.pi.model.Calendario;
import es.giiis.pi.model.Event;
import es.giiis.pi.model.User;

import java.sql.Time;

/**
 * Servlet implementation class UpdateEventServlet
 */
@WebServlet("/views/UpdateEventServlet")
public class UpdateEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateEventServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		EventDAO eventDao = new JDBCEventDAOImpl();
		eventDao.setConnection(conn);
		
		Event event = eventDao.get(Long.parseLong(request.getParameter("id")));
		HttpSession session = request.getSession();
		
		session.setAttribute("event", event);
		request.setAttribute("servlet", request.getParameter("servlet"));
		request.getRequestDispatcher("/WEB-INF/update_event.jsp").forward(request, response);
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
		Event event = eventDao.get(Long.parseLong(request.getParameter("id")));
		Calendario calendar = calendarDao.get(event.getCalendar());
			
		if(calendar.getOwner() == user.getId()){
		
			SimpleDateFormat formatter;
			Time time;
			event.setName(request.getParameter("name"));
			
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
			
			eventDao.save(event);
		}
		
		response.sendRedirect(request.getParameter("servlet"));
	}

}
