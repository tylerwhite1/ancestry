package ancestrysite.web;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ancestrysite.data.GroupMembers;
import ancestrysite.data.GroupMembersDAO;
import ancestrysite.data.Invitation;
import ancestrysite.data.InvitationDAO;
import ancestrysite.data.User;
import ancestrysite.data.UserDAO;

public class InviteGroupServlet extends HttpServlet {

	private RequestDispatcher invite;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = config.getServletContext();
		invite = context.getRequestDispatcher("/WEB-INF/jsp/invite-group.jsp");

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Hashtable<User, String> resultsList = (Hashtable<User, String>) req
				.getAttribute("resultsList");
		String groupId = req.getParameter("invite");
		req.setAttribute("invite", groupId);
		req.setAttribute("resultsList", resultsList);

		invite.forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String inviteSearch = req.getParameter("invite-search");
		String submitInvite = req.getParameter("submit-invite");
		if (submitInvite != null) {

			addMembersToGroup(req, resp);
		} else if (inviteSearch.length() > 0 && inviteSearch != null) {

			findMembersByName(inviteSearch, req, resp);
		} else {
			req.setAttribute("message",
					"The name you entered was not found, please try again");
			invite.forward(req, resp);
			return;
		}
	}

	public void findMembersByName(String result, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {

		ArrayList<User> users = new UserDAO().userList();
		users = userCantBeAddedToInviteListTwice(users, req, resp);
		users = userCantBeAddedToGroupTwice(users, req, resp);
		
		java.util.Hashtable<User, String> resultsList = new java.util.Hashtable<User, String>();

		URL main = InviteGroupServlet.class
				.getResource("InviteGroupServlet.class");
		File path = new File(main.getPath());
		String resultImage = path.getPath().split("WEB")[0];
		resultImage = resultImage + "image\\";
		File dir = new File(resultImage);

		String[] firstAndLast = result.split("\\s+");
		String firstName = firstAndLast[0];
		firstName = firstName.toLowerCase();
		firstName = firstName.substring(0, 1).toUpperCase()
				+ firstName.substring(1);
		for (User current : users) {
			if (current.getFirstName().equals(firstName)) {
				for (File imgFile : dir.listFiles()) {

					String userImage = Long.toString(current.getUserId())
							+ ".png";
					if (userImage.equals(imgFile.getName())) {
						resultsList.put(current, "image/" + imgFile.getName());
					}
				}
			} else if (current.getLastName().equals(firstName)) {
				for (File imgFile : dir.listFiles()) {

					String userImage = Long.toString(current.getUserId())
							+ ".png";
					if (userImage.equals(imgFile.getName())) {
						resultsList.put(current, "image/" + imgFile.getName());
					}
				}
			}
		}

		if (result.contains(" ")) {
			String lastName = firstAndLast[1];
			lastName = lastName.toLowerCase();
			lastName = lastName.substring(0, 1).toUpperCase()
					+ lastName.substring(1);
			for (User current : users) {

				if (current.getLastName().equals(lastName)) {

					for (File imgFile : dir.listFiles()) {

						String userImage = Long.toString(current.getUserId())
								+ ".png";
						if (userImage.equals(imgFile.getName())) {
							resultsList.put(current,
									"image/" + imgFile.getName());
						}
					}
				}
			}
		}
		String groupId = req.getParameter("invite");
		req.setAttribute("invite", groupId);
		req.setAttribute("resultsList", resultsList);
		req.setAttribute("resultSearch", result);
		invite.forward(req, resp);
		return;
	}

	public void addMembersToGroup(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {

		String groupId = req.getParameter("invite");
		String[] memberToInvite = req.getParameterValues("inviteMember");
		if (memberToInvite != null) {
			for (int i = 0; i < memberToInvite.length; i++) {

				memberToInvite[i] = memberToInvite[i].substring(0,
						memberToInvite[i].length() - 1);
				Long value = new Long(memberToInvite[i]);
				Invitation invitation = new Invitation();
				invitation.setUserId(value);
				invitation.setGroupId(Long.valueOf(groupId));
				invitation.setAccept("waiting");
				Random r = new Random();
	    		int inviteValue = r.nextInt(1000 - 100) + 100;
	    		Long inviteId = new Long(inviteValue);
	    		invitation.setInviteId(inviteId);
				new InvitationDAO().createInvitationt(invitation);
			}
		}
		resp.sendRedirect("profile");
	}

	public ArrayList<User> userCantBeAddedToInviteListTwice(ArrayList<User> users, HttpServletRequest req, HttpServletResponse resp){
		
		String groupId = req.getParameter("invite");
		ArrayList<Invitation> group =new InvitationDAO().getGroupsMembership(groupId);
		List<User> list = users;
		for(Invitation current: group){
			for (Iterator<User> iterator = list.iterator(); iterator.hasNext(); ) {
			    User value = iterator.next();

				if(current.getUserId().equals(value.getUserId())){
					iterator.remove();
				}
			}
		}
		users=(ArrayList<User>) list;
		return users;
	}
	
	public ArrayList<User> userCantBeAddedToGroupTwice(ArrayList<User> users, HttpServletRequest req, HttpServletResponse resp){
		
		String groupId = req.getParameter("invite");
		ArrayList<GroupMembers> group =new GroupMembersDAO().getGroupsMembership(groupId);
		List<User> list = users;
		for(GroupMembers current: group){
			for (Iterator<User> iterator = list.iterator(); iterator.hasNext(); ) {
			    User value = iterator.next();

				if(current.getUsersId().equals(value.getUserId())){
					iterator.remove();
				}
			}
		}
		users=(ArrayList<User>) list;
		return users;
	}
}
