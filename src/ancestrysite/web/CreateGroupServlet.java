package ancestrysite.web;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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

public class CreateGroupServlet extends HttpServlet {

	private RequestDispatcher group;

	
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = config.getServletContext();
		group = context.getRequestDispatcher("/WEB-INF/jsp/createGroup.jsp");

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		User userLogin = new UserDAO().find(user.getEmail());
		session.setAttribute("user", userLogin);
		group.forward(req, resp);

	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String cancelButton = req.getParameter("cancel-button");
		if (cancelButton != null) {
			resp.sendRedirect("profile");
			return;
		}
		String id = req.getParameter("groupId");
		Long idOfGroup = new Long(id);
		String idOfCreator = req.getParameter("createById");
		Long creatorsId = new Long(idOfCreator);
		String groupFile = req.getParameter("groupFile");
		String name = req.getParameter("name");
		FamilyGroup familyGroup = new FamilyGroup();
		familyGroup.setGroupId(idOfGroup);
		familyGroup.setCreateById(creatorsId);
		familyGroup.setGroupName(name);
		
		Map<String, String> errors = validate(req, familyGroup);
		if (!errors.isEmpty()) {
			req.setAttribute("group", familyGroup);
			group.forward(req, resp);
			return;
		}
		
		new FamilyGroupDAO().createFamilyGroup(familyGroup);
		GroupMembers members = new GroupMembers();
		members.setGroupId(familyGroup.getGroupId());
		members.setUsersId(familyGroup.getCreateById());
		new GroupMembersDAO().createMembers(members);
		ImageUpload image = new ImageUpload();
        URL main = ProfileServlet.class.getResource("CreateGroupServlet.class");
	    File path = new File(main.getPath());
	    String result = path.getPath().split("WEB")[0];
        image.saveGroupImage(result, familyGroup, groupFile);
		resp.sendRedirect("profile"); 
	}
	
	public static Map<String, String> validate(HttpServletRequest req, FamilyGroup group) {
		HashMap<String, String> errors = new HashMap<String, String>();
		req.setAttribute("errors", errors);

		String name = req.getParameter("name");
		if (name == null || name.length() == 0) {
			errors.put("name", "Name of group required.");
		}
		group.setGroupName(name);

		String groupFile = req.getParameter("groupFile");
		if (groupFile == null || groupFile.length() == 0) {
			errors.put("groupFile", "Picture for group required.");
		}

		return errors;
	}
}
