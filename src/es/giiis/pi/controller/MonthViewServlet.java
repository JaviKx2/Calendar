package es.giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;
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
 * Servlet implementation class MonthViewServlet
 */
@WebServlet("/views/MonthViewServlet")
public class MonthViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Calendar MonthView; 
	private int firstDay;
	private int lastDay;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MonthViewServlet() {
        super();
        MonthView = new GregorianCalendar();
        
    } 
    public void goTo(String goTo){
    	if(goTo.equals("today")){
    		MonthView = new GregorianCalendar();
		}else if(goTo.equals("backward"))
			MonthView.add(Calendar.MONTH, -1);
		else if(goTo.equals("forward"))
				MonthView.add(Calendar.MONTH, 1);
    }
    
    public int[] days(){
    	int DoFold = MonthView.get(Calendar.DAY_OF_MONTH);
    	int DoWold = MonthView.get(Calendar.DAY_OF_WEEK);
    	int MonthOld = MonthView.get(Calendar.MONTH);
    	int yearOld = MonthView.get(Calendar.YEAR);
    	MonthView.set(Calendar.DAY_OF_MONTH, MonthView.getActualMinimum(Calendar.DAY_OF_MONTH));
    	
    	if(MonthView.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
    		firstDay = 7;
    	}else{
    		firstDay = MonthView.get(Calendar.DAY_OF_WEEK)-1;
    	}
    	lastDay = MonthView.getMaximum(Calendar.DAY_OF_MONTH);
    	MonthView.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    	
    	int[] dias = new int[42];
    	for (int i = 1; i <= dias.length; i++) {
    		if(i >= firstDay && i-(firstDay-1) <= lastDay){
    				dias[i-1] = i - (firstDay-1);   				
    		}
		}
    	
    	MonthView.set(Calendar.MONTH, MonthOld);
    	MonthView.set(Calendar.DAY_OF_WEEK, DoWold);
    	MonthView.set(Calendar.DAY_OF_MONTH, DoFold);
    	MonthView.set(Calendar.YEAR, yearOld);

    	
    	return dias;
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

		int[] dias = days();
				
		User user = (User) session.getAttribute("user");		
		List<Calendario> calendars = calendarDao.getAllByUser(user.getId());	

		if(!calendars.isEmpty()){
			List<Event> eventsByMonth = eventDao.getAllByMonth(calendars.get(0).getId(), 
																MonthView.get(Calendar.MONTH)+1,
																MonthView.get(Calendar.YEAR));
			
			for(int i=0; i<eventsByMonth.size(); i++){
				eventsByMonth.get(i).setDayByDate();
			}
			
			request.setAttribute("events", eventsByMonth);
		}
		request.setAttribute("Month", MonthView.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("ES")));
		request.setAttribute("Year", MonthView.get(Calendar.YEAR));
		request.setAttribute("dias", dias);
		request.setAttribute("servlet", "MonthViewServlet");
		
		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/month_view.jsp");
		view.forward(request, response);
	}

}
