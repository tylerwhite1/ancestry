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

import ancestrysite.data.User;
import ancestrysite.data.UserDAO;

public class EditMotherServlet extends HttpServlet {

	private RequestDispatcher edit_mother;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = config.getServletContext();
		edit_mother = context
				.getRequestDispatcher("/WEB-INF/jsp/edit-mother.jsp");

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Hashtable<User, String> resultsList = (Hashtable<User, String>) req
				.getAttribute("motherList");
		// String fatherId = req.getParameter("fatherId");
		// req.setAttribute("fatherId", fatherId);
		req.setAttribute("motherList", resultsList);
		edit_mother.forward(req, resp);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String cancelButton = req.getParameter("cancel-button");
		String motherName = req.getParameter("mother-name");
		String submitSearch = req.getParameter("submit-search");
		String submitParent = req.getParameter("submit-parent");
		String submitQuery = req.getParameter("submit-query");
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		User userLogin = new UserDAO().find(user.getEmail());

		if (cancelButton != null) {
			resp.sendRedirect("edit");
			return;
		}
		if (submitSearch != null) {
			String motherId = req.getParameter("motherOf");
			if (motherId != null) {
				///HttpSession session = req.getSession();
				motherId = motherId.substring(0, motherId.length() - 1);
				userLogin.setMotherId(Long.valueOf(motherId));
				new UserDAO().update(userLogin);
				session.setAttribute("user", userLogin);
				resp.sendRedirect("edit");
			} else {
				req.setAttribute("message", "You didn't select a radio button");
				edit_mother.forward(req, resp);
				return;
			}
		}

		if (submitQuery != null) {
			if (motherName.length() > 0 && motherName != null) {

				findMembersByName(motherName, req, resp);
			} else {
				req.setAttribute("message",
						"The name you entered was not found, please try again");
				edit_mother.forward(req, resp);
				return;
			}
		}

		if (submitParent != null) {
			// String motherInfo = req.getParameter("mother-info");
			// if (motherInfo != null) {
			//HttpSession session = req.getSession();
			// motherInfo=motherInfo.substring(0,motherInfo.length()-1);
			String defaultId = "0";
			Map<String, String> errors = validate(req);
			if (!errors.isEmpty()) {
				edit_mother.forward(req, resp);
				return;
			}
			
			userLogin.setMotherId(Long.valueOf(defaultId));
			new UserDAO().update(userLogin);
			session.setAttribute("user", userLogin);
			
			String motherFirst = req.getParameter("mother-first");
			String motherLast = req.getParameter("mother-last");
			session.setAttribute("motherFirst", motherFirst);
			session.setAttribute("motherLast", motherLast);
			//session.setAttribute("motherId", defaultId);
			resp.sendRedirect("edit");
		}

	}

	public void findMembersByName(String result, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {

		ArrayList<User> users = new UserDAO().userList();

		java.util.Hashtable<User, String> resultsList = new java.util.Hashtable<User, String>();

		URL main = EditMotherServlet.class
				.getResource("EditMotherServlet.class");
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
		// String groupId = req.getParameter("invite");
		// req.setAttribute("invite", groupId);
		req.setAttribute("motherList", resultsList);
		// req.setAttribute("resultSearch", result);
		edit_mother.forward(req, resp);
		return;
	}

	public static Map<String, String> validate(HttpServletRequest req) {
		HashMap<String, String> errors = new HashMap<String, String>();
		req.setAttribute("errors", errors);

		String motherFirst = req.getParameter("mother-first");
		if (motherFirst == null || motherFirst.length() == 0) {
			errors.put("mother-first", "Retype Mother's First Name");
		}
		String motherLast = req.getParameter("mother-last");
		if (motherLast == null || motherLast.length() == 0) {
			errors.put("mother-last", "Retype Mother's Last Name");
		}
		return errors;
	}
}
