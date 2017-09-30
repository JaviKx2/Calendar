package es.giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import es.giiis.pi.dao.CalendarDAO;
import es.giiis.pi.dao.EventDAO;
import es.giiis.pi.dao.JDBCCalendarDAOImpl;
import es.giiis.pi.dao.JDBCEventDAOImpl;
import es.giiis.pi.model.Event;
import es.giiis.pi.model.User;
import es.giiis.pi.model.Calendario;
/**
 * Servlet implementation class WeekViewServlet
 */
@WebServlet("/views/WeekViewServlet")
public class WeekViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Calendar WeekView;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeekViewServlet() {
        super();
        WeekView = GregorianCalendar.getInstance();
    }
    
    public void goTo(String goTo){
    	if(goTo.equals("today")){
    		WeekView = new GregorianCalendar();
		}else if(goTo.equals("backward"))
			WeekView.add(Calendar.DAY_OF_WEEK_IN_MONTH, -1);
		else if(goTo.equals("forward"))
				WeekView.add(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
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
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String startDay = "";
		String endDay = "";
		WeekView.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		startDay = formatter.format(WeekView.getTime());
		WeekView.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		endDay = formatter.format(WeekView.getTime());
		
		int[] dias = new int[7];
		for (int i = 1; i <= dias.length; i++) {
			if(i==7){
				WeekView.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			}
			else WeekView.set(Calendar.DAY_OF_WEEK, i+1);
			dias[i-1] = WeekView.get(Calendar.DATE);
		}
		
		
		User user = (User) session.getAttribute("user");		
		List<Calendario> calendars = calendarDao.getAllByUser(user.getId());	
		if(!calendars.isEmpty()){
			List<Event> events = eventDao.getAllByWeek(calendars.get(0).getId(),startDay, endDay);
			
			for(int i=0; i<events.size(); i++){
				events.get(i).setHourByTime();
				events.get(i).setDayByDate();
			}
			request.setAttribute("events", events);
		}
		request.setAttribute("startDay", startDay);
		request.setAttribute("endDay", endDay);
		
		request.setAttribute("dias", dias);
		request.setAttribute("servlet", "WeekViewServlet");
		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/week_view.jsp");
		view.forward(request, response);
	}
}
