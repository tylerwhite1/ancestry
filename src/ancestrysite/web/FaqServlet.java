package ancestrysite.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FaqServlet extends HttpServlet {

	private RequestDispatcher faq;

	
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = config.getServletContext();
		faq = context.getRequestDispatcher("/WEB-INF/jsp/faq.jsp");

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//super.doGet(req, resp);
		req.setAttribute("message", "bye");
		faq.forward(req, resp);

	}

}
