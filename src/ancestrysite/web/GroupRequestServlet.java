package ancestrysite.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ancestrysite.data.FamilyGroup;
import ancestrysite.data.FamilyGroupDAO;
import ancestrysite.data.GroupMembers;
import ancestrysite.data.GroupMembersDAO;
import ancestrysite.data.Invitation;
import ancestrysite.data.InvitationDAO;
import ancestrysite.data.User;
import ancestrysite.data.UserDAO;

public class GroupRequestServlet extends HttpServlet {

	private RequestDispatcher group_request;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = config.getServletContext();
		group_request = context.getRequestDispatcher("/WEB-INF/jsp/group-request.jsp");
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		User userLogin = new UserDAO().find(user.getEmail());
		String requestValue = req.getParameter("requestvalue");
		req.setAttribute("requestValue", requestValue);
		group_request.forward(req, resp);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		User userLogin = new UserDAO().find(user.getEmail());
		String requestValue = req.getParameter("requestvalue");
		
		  String cancelButton = req.getParameter("cancel-button");
	      if (cancelButton != null)
	      {
	         resp.sendRedirect("invitation");
	         return;
	      }
	      
	      String accept = req.getParameter("accept-button");
	      if (accept != null)
	      {
	    	 Long value = new Long(requestValue);
	    	 GroupMembers member = new GroupMembers();
			 member.setUsersId(userLogin.getUserId());
			 member.setGroupId(value);
			 new GroupMembersDAO().createMembers(member);
			 new InvitationDAO().deleteInvitation(userLogin.getUserId(), value);
			 resp.sendRedirect("profile");
	      }	
	      
	      String declinebutton = req.getParameter("decline-button");
	      if (declinebutton != null)
	      {
	    	  Long value = new Long(requestValue);
			  new InvitationDAO().deleteInvitation(userLogin.getUserId(), value);
			  resp.sendRedirect("invitation");
	      }
	}

}
