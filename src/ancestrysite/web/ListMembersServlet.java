package ancestrysite.web;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ancestrysite.data.GroupMembers;
import ancestrysite.data.GroupMembersDAO;
import ancestrysite.data.User;
import ancestrysite.data.UserDAO;

public class ListMembersServlet extends HttpServlet{

    private RequestDispatcher viewMembers;

    public void init(ServletConfig config) throws ServletException {
    	
    	ServletContext context = config.getServletContext();
    	
        viewMembers = context.getRequestDispatcher("/WEB-INF/jsp/list-members.jsp");
       
     }
      
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
    	    throws ServletException, IOException {

    	String groupId = req.getParameter("list");
        ArrayList<GroupMembers> members = new GroupMembersDAO().getGroupsMembership(groupId);
        java.util.Hashtable<User, String> allmembers = new java.util.Hashtable<User, String>();
        ArrayList<User> users =new UserDAO().userList();
		URL main = ProfileServlet.class.getResource("ProfileServlet.class");
	    File path = new File(main.getPath());
	    String result = path.getPath().split("WEB")[0];
	    result = result+"image\\";
		File dir = new File(result);
		
		for(GroupMembers current: members){		
			for(User curUser:users){
				if(current.getUsersId().equals(curUser.getUserId())){
					
					for(File imgFile : dir.listFiles()) {
						
						String userImage = Long.toString(curUser.getUserId())+".png";
						if(userImage.equals(imgFile.getName())){
							allmembers.put(curUser,"image/"+imgFile.getName());
						}
					}
				}
			}
		}
        req.setAttribute("list",groupId);
        req.setAttribute("allmembers",allmembers);
    	viewMembers.forward(req, resp);
    }
}
