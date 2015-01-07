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

public class EditFatherServlet extends HttpServlet {

	private RequestDispatcher edit_father;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = config.getServletContext();
		edit_father = context
				.getRequestDispatcher("/WEB-INF/jsp/edit-father.jsp");

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Hashtable<User, String> resultsList = (Hashtable<User, String>) req
				.getAttribute("fatherList");
		req.setAttribute("fatherList", resultsList);
		edit_father.forward(req, resp);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String cancelButton = req.getParameter("cancel-button");
		String fatherName = req.getParameter("father-name");
		String submitSearch = req.getParameter("submit-search");
		String submitQuery = req.getParameter("submit-query");
		String submitParent = req.getParameter("submit-parent");
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		User userLogin = new UserDAO().find(user.getEmail());
		
		
		if (cancelButton != null) {
			resp.sendRedirect("edit");
			return;
		}
		if (submitSearch != null) {
			String fatherId = req.getParameter("fatherOf");
			if (fatherId != null) {
				//HttpSession session = req.getSession();
				fatherId = fatherId.substring(0, fatherId.length() - 1);
				userLogin.setFatherId(Long.valueOf(fatherId));
				new UserDAO().update(userLogin);
				session.setAttribute("user", userLogin);
				resp.sendRedirect("edit");
			} else {
				req.setAttribute("message", "You didn't select a radio button");
				edit_father.forward(req, resp);
				return;
			}

		}
		if (submitQuery != null) {
			if (fatherName.length() > 0 && fatherName != null) {

				findMembersByName(fatherName, req, resp);
			} else {
				req.setAttribute("message",
						"The name you entered was not found, please try again");
				edit_father.forward(req, resp);
				return;
			}
		}

		if (submitParent != null) {
			// String fatherInfo = req.getParameter("father-info");
			// if (fatherInfo != null) {
			//HttpSession session = req.getSession();
			// fatherInfo=fatherInfo.substring(0,fatherInfo.length()-1);
			String defaultId = "0";
			Map<String, String> errors = validate(req);
			if (!errors.isEmpty()) {
				edit_father.forward(req, resp);
				return;
			}
			
			userLogin.setFatherId(Long.valueOf(defaultId));
			new UserDAO().update(userLogin);
			session.setAttribute("user", userLogin);
			
			String fatherFirst = req.getParameter("father-first");
			String fatherLast = req.getParameter("father-last");
			session.setAttribute("fatherFirst", fatherFirst);
			session.setAttribute("fatherLast", fatherLast);
			//session.setAttribute("fatherId", defaultId);
			resp.sendRedirect("edit");
		}
	}

	public void findMembersByName(String result, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {

		ArrayList<User> users = new UserDAO().userList();

		java.util.Hashtable<User, String> resultsList = new java.util.Hashtable<User, String>();

		URL main = EditFatherServlet.class
				.getResource("EditFatherServlet.class");
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
		req.setAttribute("fatherList", resultsList);
		// req.setAttribute("resultSearch", result);
		edit_father.forward(req, resp);
		return;
	}

	public static Map<String, String> validate(HttpServletRequest req) {
		HashMap<String, String> errors = new HashMap<String, String>();
		req.setAttribute("errors", errors);

		String fatherFirst = req.getParameter("father-first");
		if (fatherFirst == null || fatherFirst.length() == 0) {
			errors.put("father-first", "Retype Father's First Name");
		}
		String fatherLast = req.getParameter("father-last");
		if (fatherLast == null || fatherLast.length() == 0) {
			errors.put("father-last", "Retype Father's Last Name");
		}
		return errors;
	}

}
