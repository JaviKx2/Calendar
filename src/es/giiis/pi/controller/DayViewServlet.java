package es.giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

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
import es.giiis.pi.model.Calendario;
import es.giiis.pi.model.Event;
import es.giiis.pi.model.User;



/**
 * Servlet implementation class DayViewServlet
 */
@WebServlet("/views/DayViewServlet")
public class DayViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Calendar DayView;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DayViewServlet() {
        super();
        DayView = new GregorianCalendar();
    }
    
    public void goTo(String goTo){
    	if(goTo.equals("today")){
    		DayView = new GregorianCalendar();
		}else if(goTo.equals("backward"))
			DayView.add(Calendar.DAY_OF_WEEK, -1);
		else if(goTo.equals("forward"))
				DayView.add(Calendar.DAY_OF_WEEK, 1);
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
		
		String goTo = request.getParameter("goTo");
		if(goTo != null) goTo(goTo);
		
		User user = (User) session.getAttribute("user");		
		List<Calendario> calendars = calendarDao.getAllByUser(user.getId());	

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String startDay = "";
		startDay = formatter.format(DayView.getTime());
		if(!calendars.isEmpty()){
			List<Event> events = eventDao.getAllByDay(calendars.get(0).getId(), startDay);
			
			for(int i=0; i<events.size(); i++){
				events.get(i).setHourByTime();
			}
			request.setAttribute("events", events);
		}
		request.setAttribute("wday", DayView.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("ES")));
		request.setAttribute("sday", startDay);
		request.setAttribute("day", DayView.get(Calendar.DATE));
		request.setAttribute("servlet", "DayViewServlet");
		

		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/day_view.jsp");
		view.forward(request, response);
	}

}
