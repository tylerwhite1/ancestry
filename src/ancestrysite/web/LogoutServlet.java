package ancestrysite.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ancestrysite.data.User;

public class LogoutServlet extends HttpServlet {

	private RequestDispatcher logout;

	
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = config.getServletContext();
		logout = context.getRequestDispatcher("/WEB-INF/jsp/logout.jsp");

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		user= null;
		req.setAttribute("message", "bye");
		session = req.getSession();
		session.setAttribute("user", user);
		logout.forward(req, resp);

	}

}
