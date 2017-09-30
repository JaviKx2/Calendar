package es.giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.giiis.pi.dao.CalendarDAO;
import es.giiis.pi.dao.JDBCCalendarDAOImpl;
import es.giiis.pi.model.Calendario;
import es.giiis.pi.model.User;

/**
 * Servlet implementation class DeleteCalendarServlet
 */
@WebServlet("/views/DeleteCalendarServlet")
public class DeleteCalendarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCalendarServlet() {
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
			
		User user = (User) session.getAttribute("user");	
		
		List<Calendario> calendars = calendarDao.getAllByUser(user.getId());
		if(!calendars.isEmpty()){
			calendarDao.delete(calendars.get(0).getId());
		}
		response.sendRedirect(request.getParameter("servlet"));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
