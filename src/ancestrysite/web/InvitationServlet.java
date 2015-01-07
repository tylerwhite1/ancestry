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


public class InvitationServlet extends HttpServlet {

	private RequestDispatcher invitation;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = config.getServletContext();
		invitation = context.getRequestDispatcher("/WEB-INF/jsp/invitation.jsp");
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		User userLogin = new UserDAO().find(user.getEmail());
		ArrayList<Invitation> request =new InvitationDAO().getAllMembersInvitation(userLogin.getUserId());
		Hashtable<User,FamilyGroup> group = new Hashtable<User,FamilyGroup>();
		for(Invitation current:request){
			
			//GroupMembers member =new GroupMembersDAO().findMembership(current.getGroupId(), userLogin.getUserId());
			FamilyGroup familyGroup = new FamilyGroupDAO().findGroup(String.valueOf(current.getGroupId()));
			User groupCreator = new UserDAO().findUserById(familyGroup.getCreateById());
			group.put(groupCreator,familyGroup);
		}
		req.setAttribute("c", String.valueOf(group.size()));
		req.setAttribute("requestedGroups", group);
		session.setAttribute("request", request);
		session.setAttribute("requestNum", String.valueOf(request.size()));
		session.setAttribute("user", userLogin);
		invitation.forward(req, resp);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		User userLogin = new UserDAO().find(user.getEmail());
		ArrayList<Invitation> request =new InvitationDAO().getAllMembersInvitation(userLogin.getUserId());
		
		  String cancelButton = req.getParameter("cancel-button");
	      if (cancelButton != null)
	      {
	         resp.sendRedirect("profile");
	         return;
	      }
	      resp.sendRedirect("profile");
	}


}
