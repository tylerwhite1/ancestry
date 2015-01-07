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

import ancestrysite.data.GroupMembers;
import ancestrysite.data.GroupMembersDAO;
import ancestrysite.data.InvitationDAO;
import ancestrysite.data.ParentNotification;
import ancestrysite.data.ParentNotificationDAO;
import ancestrysite.data.User;
import ancestrysite.data.UserDAO;

public class ParentRequestServlet extends HttpServlet {

	private RequestDispatcher parent_request;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = config.getServletContext();
		parent_request = context.getRequestDispatcher("/WEB-INF/jsp/parent-notify.jsp");
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String requestValue = req.getParameter("parentvalue");
		req.setAttribute("parentvalue", requestValue);
		parent_request.forward(req, resp);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String cancelButton = req.getParameter("cancel-button");
		String requestValue = req.getParameter("parentvalue");
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		User userLogin = new UserDAO().find(user.getEmail());
		
	      if (cancelButton != null)
	      {
	         resp.sendRedirect("notification");
	         return;
	      }
	      
	      String yesButton = req.getParameter("accept-button");
	      if (yesButton != null)
	      {
	    	  User parentUser = new UserDAO().findUserById(Long.valueOf(requestValue));
	    	  if(parentUser.getGender().equals("Male")){
	    		 userLogin.setFatherId(parentUser.getUserId());
	    		 session.setAttribute("user", userLogin);
	   		     new UserDAO().update(userLogin);
	    	 }
	    	  else if(parentUser.getGender().equals("Female")){
	    		 userLogin.setMotherId(parentUser.getUserId());
	    		 session.setAttribute("user", userLogin);
	   		    new UserDAO().update(userLogin);
	    	  }
	    	  ParentNotification notify = new ParentNotificationDAO().findNotificationByInfo(userLogin.getUserId(), parentUser.getFirstName(), parentUser.getLastName());
	    	  new ParentNotificationDAO().deleteNotification(notify.getNotificationId());
	    	  resp.sendRedirect("notification");
	      }
	      String nobutton = req.getParameter("decline-button");
	      if (nobutton != null)
	      {
	    	  User parentUser = new UserDAO().findUserById(Long.valueOf(requestValue));
	    	  ParentNotification notify = new ParentNotificationDAO().findNotificationByInfo(userLogin.getUserId(), parentUser.getFirstName(), parentUser.getLastName());
	    	  new ParentNotificationDAO().deleteNotification(notify.getNotificationId());
	    	  resp.sendRedirect("notification");
	      }
	}
}
