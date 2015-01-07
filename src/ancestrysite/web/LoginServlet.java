package ancestrysite.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ancestrysite.data.Invitation;
import ancestrysite.data.InvitationDAO;
import ancestrysite.data.ParentNotification;
import ancestrysite.data.ParentNotificationDAO;
import ancestrysite.data.User;
import ancestrysite.data.UserDAO;

public class LoginServlet extends HttpServlet {

	private RequestDispatcher login;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = config.getServletContext();
		login = context.getRequestDispatcher("/WEB-INF/jsp/login.jsp");

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		login.forward(req, resp);

	}

	protected void forwardBack(String loggerMessage, String name,
			String message, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setAttribute(name, message);
		login.forward(req, resp);
		return;
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String email = req.getParameter("email");
		String password = req.getParameter("password");

		if (email.length() > 0 && password.length() > 0) {

			User user = new UserDAO().find(email);
			checkValidationForStudent(req, resp, user);
		} else {
			req.setAttribute("message",
					"The email or password you entered was incorrect, please try again");
			login.forward(req, resp);
			return;
		}
	}

	public void checkValidationForStudent(HttpServletRequest req,
			HttpServletResponse resp, User user) throws ServletException,
			IOException {

		if (user == null) {
			req.setAttribute("message",
					"The email or password you entered was incorrect, please try again");
			login.forward(req, resp);
			return;
		} else {
			String password = req.getParameter("password");

			if (!user.getPassword().equals(password)) {
				forwardBack("authentication failed: bad password", "message",
						"Authentication failed, wrong password.", req, resp);
			} else {
				user.setIfLogIn(true);
				HttpSession session = req.getSession();
				
				session.setAttribute("user", user);
				inviteRequest(user, session);
				parentNotification(user, session);
				String url = "home";
				resp.sendRedirect(url);
			}
		}
	}
	
	public void inviteRequest(User user, HttpSession session){
		
		ArrayList<Invitation> request =new InvitationDAO().getAllMembersInvitation(user.getUserId());
		session.setAttribute("request", request);
		session.setAttribute("requestNum", String.valueOf(request.size()));
	}

	public void parentNotification(User user, HttpSession session){
		
		ArrayList<ParentNotification> notify = new ParentNotificationDAO().getAllMembersParentNotification(user.getUserId());
		session.setAttribute("parents", notify);
		session.setAttribute("notifyNum", String.valueOf(getParentsByName(notify).size()));
		
	}
	
 public ArrayList<User> getParentsByName(ArrayList<ParentNotification> notify){
		
		ArrayList<User> users= new UserDAO().userList();
		ArrayList<User> parent = new ArrayList<User>();
		for(ParentNotification curParent: notify){
			
			for(User curUser : users){
				
				if(curParent.getParentFirstName().equals(curUser.getFirstName()) && curParent.getParentLastName().equals(curUser.getLastName())){
					parent.add(curUser);
				}
			}
		}
		return parent;
	}
}
