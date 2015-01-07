package ancestrysite.web;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

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
import ancestrysite.data.ImageUpload;
import ancestrysite.data.User;
import ancestrysite.data.UserDAO;

public class EditGroupServlet extends HttpServlet {

	private RequestDispatcher editGroup;
	private Hashtable<User, String> usersOfGroup;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = config.getServletContext();
		editGroup = context.getRequestDispatcher("/WEB-INF/jsp/edit-group.jsp");

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// super.doGet(req, resp);
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		User userLogin = new UserDAO().find(user.getEmail());
		String groupId = req.getParameter("group");
		FamilyGroup group = new FamilyGroupDAO().findGroup(groupId);
		ArrayList<GroupMembers> members = new GroupMembersDAO().getGroupsMembership(groupId);
		//ArrayList<User> usersOfGroup = new ArrayList<User>();
		usersOfGroup = new Hashtable<User, String>();
		URL main = ProfileServlet.class.getResource("EditGroupServlet.class");
	    File path = new File(main.getPath());
	    String result = path.getPath().split("WEB")[0];
	    result = result+"image\\";
		File dir = new File(result);

		
		ArrayList<User> users = new UserDAO().userList();
		for(GroupMembers current: members){		
			for(User curUser:users){
				if(current.getUsersId().equals(curUser.getUserId()) && !userLogin.getUserId().equals(curUser.getUserId())){
					
					for(File imgFile : dir.listFiles()) {
						
						String userImage = Long.toString(curUser.getUserId())+".png";
						if(userImage.equals(imgFile.getName())){
							usersOfGroup.put(curUser,"image/"+imgFile.getName());
						}
					}
				}
			}
		}
		req.setAttribute("groupMembers", usersOfGroup);
		req.setAttribute("groupEdit", group);
		req.setAttribute("group", group);
		session.setAttribute("user", userLogin);
		editGroup.forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String cancelButton = req.getParameter("cancel-button");
		if (cancelButton != null) {
			resp.sendRedirect("profile");
			return;
		}
		String submitButton = req.getParameter("submit-button");
		String groupId = req.getParameter("group");
		
		if(submitButton != null){
			FamilyGroup group = new FamilyGroupDAO().findGroup(groupId);
			Map<String, String> errors = validate(req, group);
			
			if (!errors.isEmpty()) {
				req.setAttribute("groupEdit", group);
				req.setAttribute("group", group);
				req.setAttribute("groupMembers", usersOfGroup);
				editGroup.forward(req, resp);
				return;
			}
			req.setAttribute("group", group);
			new FamilyGroupDAO().update(group);
			resp.sendRedirect("profile");
		}
		
		String submitRemove = req.getParameter("submit-remove");
		if(submitRemove != null){
			String[] idToRemove = req.getParameterValues("member");
			if(idToRemove !=null){
			    for (int i = 0; i < idToRemove.length; i++) {
			    	
			    	idToRemove[i] = idToRemove[i].substring(0, idToRemove[i].length() - 1);
			    	Long value = new Long(idToRemove[i]);
			    	new GroupMembersDAO().deleteGroupMembership(Long.valueOf(groupId),value);
			    }
			}
			resp.sendRedirect("profile");
		}
		
	}

	public static Map<String, String> validate(HttpServletRequest req,
			FamilyGroup group) throws IOException {
		HashMap<String, String> errors = new HashMap<String, String>();
		req.setAttribute("errors", errors);

		String name = req.getParameter("name");
		if (name == null || name.length() == 0) {
			errors.put("name", "Name Required.");
		}
		group.setGroupName(name);
		
		String picture = req.getParameter("picture");
		
		if (picture != null && picture.length() >0) {
			ImageUpload image = new ImageUpload();
			URL main = ProfileServlet.class
					.getResource("EditGroupServlet.class");
			File path = new File(main.getPath());
			String result = path.getPath().split("WEB")[0];
			image.saveGroupImage(result, group, picture);
		}
		return errors;
	}
}
