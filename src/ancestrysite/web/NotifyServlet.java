package ancestrysite.web;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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

import ancestrysite.data.ParentNotification;
import ancestrysite.data.ParentNotificationDAO;
import ancestrysite.data.User;
import ancestrysite.data.UserDAO;

public class NotifyServlet extends HttpServlet {

	private RequestDispatcher notify;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = config.getServletContext();
		notify = context.getRequestDispatcher("/WEB-INF/jsp/notification.jsp");
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		User userLogin = new UserDAO().find(user.getEmail());
		ArrayList<ParentNotification> notifyParents = new ParentNotificationDAO().getAllMembersParentNotification(user.getUserId());
		//ArrayList<User> user = (ArrayList<User>) session.getAttribute("parents");
		Hashtable<User, String> results = setUpParentNotify(getParentsByName(notifyParents));
		session.setAttribute("parents", getParentsByName(notifyParents));
		session.setAttribute("notifyNum", String.valueOf( getParentsByName(notifyParents).size()));
		req.setAttribute("parentResult", results);
		req.setAttribute("user", userLogin);
		notify.forward(req, resp);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String cancelButton = req.getParameter("cancel-button");
		if (cancelButton != null)
	      {
	         resp.sendRedirect("profile");
	         return;
	     }
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
	
	public Hashtable<User, String> setUpParentNotify(ArrayList<User> user){
		
		Hashtable<User, String> results = new Hashtable<User, String>();
		URL main = ProfileServlet.class.getResource("ProfileServlet.class");
	    File path = new File(main.getPath());
	    String result = path.getPath().split("WEB")[0];
	    result = result+"image\\";
		File dir = new File(result);
		
		for(User current : user){
	
				for(File imgFile : dir.listFiles()) {
					
					String userImage = Long.toString(current.getUserId())+".png";
					if(userImage.equals(imgFile.getName())){
						results.put(current,"image/"+imgFile.getName());
					}
				}
		}
		return results;	
	}

}
