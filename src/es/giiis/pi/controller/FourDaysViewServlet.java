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
 * Servlet implementation class FourDaysViewServlet
 */
@WebServlet("/views/FourDaysViewServlet")
public class FourDaysViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Calendar FourDaysView;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FourDaysViewServlet() {
        super();
        FourDaysView = new GregorianCalendar();
    }

    public void goTo(String goTo){
    	if(goTo.equals("today")){
    		FourDaysView = new GregorianCalendar();
		}else if(goTo.equals("backward"))
			FourDaysView.add(Calendar.DAY_OF_WEEK, -7);
		else if(goTo.equals("forward"))
				FourDaysView.add(Calendar.DAY_OF_WEEK, 1);
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
		else FourDaysView = new GregorianCalendar();
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String startDay = "";
		String endDay = "";
		
		int[] dias = new int[4];
		String[] wdays = new String[4];
		for (int i = 0; i < dias.length; i++) {
			if(i == 0) startDay = formatter.format(FourDaysView.getTime());
			wdays[i] = FourDaysView.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("ES"));
			dias[i] = FourDaysView.get(Calendar.DATE);
			if(i == 3) endDay = formatter.format(FourDaysView.getTime());
			if(i != 3) FourDaysView.add(Calendar.DAY_OF_WEEK, 1);
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
		request.setAttribute("wdays", wdays);
		request.setAttribute("dias", dias);
		request.setAttribute("servlet", "FourDaysViewServlet");
		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/4days_view.jsp");
		view.forward(request, response);
	}
}
