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

public class FatherSearchServlet extends HttpServlet {

	private RequestDispatcher father_seacrch;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = config.getServletContext();
		father_seacrch = context
				.getRequestDispatcher("/WEB-INF/jsp/father-search.jsp");

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Hashtable<User, String> resultsList = (Hashtable<User, String>) req
				.getAttribute("fatherList");
		// String fatherId = req.getParameter("fatherId");
		// req.setAttribute("fatherId", fatherId);
		req.setAttribute("fatherList", resultsList);
		father_seacrch.forward(req, resp);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String cancelButton = req.getParameter("cancel-button");
		String fatherName = req.getParameter("father-name");
		String submitSearch = req.getParameter("submit-search");
		String submitQuery = req.getParameter("submit-query");
		String submitParent = req.getParameter("submit-parent");

		if (cancelButton != null) {
			resp.sendRedirect("register");
			return;
		}
		if (submitSearch != null) {
			String fatherId = req.getParameter("fatherOf");
			if (fatherId != null) {
				HttpSession session = req.getSession();
				fatherId = fatherId.substring(0, fatherId.length() - 1);
				session.setAttribute("fatherId", fatherId);
				resp.sendRedirect("register");
			} else {
				req.setAttribute("message", "You didn't select a radio button");
				father_seacrch.forward(req, resp);
				return;
			}

		}
		if (submitQuery != null) {
			if (fatherName.length() > 0 && fatherName != null) {

				findMembersByName(fatherName, req, resp);
			}
			else {
				req.setAttribute("message",
						"The name you entered was not found, please try again");
				father_seacrch.forward(req, resp);
				return;
			}
		} 
		
		if (submitParent != null) {
			//String fatherInfo = req.getParameter("father-info");
			//if (fatherInfo != null) {
				HttpSession session = req.getSession();
				// fatherInfo=fatherInfo.substring(0,fatherInfo.length()-1);
				String defaultId = "0";
				Map<String, String> errors = validate(req);
				if (!errors.isEmpty()) {
					father_seacrch.forward(req, resp);
					return;
				}
				String fatherFirst = req.getParameter("father-first");
				String fatherLast = req.getParameter("father-last");
				session.setAttribute("fatherFirst", fatherFirst);
				session.setAttribute("fatherLast", fatherLast);
				session.setAttribute("fatherId", defaultId);
				resp.sendRedirect("register");
		} 
	}

	public void findMembersByName(String result, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {

		ArrayList<User> users = new UserDAO().userList();

		java.util.Hashtable<User, String> resultsList = new java.util.Hashtable<User, String>();

		URL main = FatherSearchServlet.class
				.getResource("FatherSearchServlet.class");
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
		father_seacrch.forward(req, resp);
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
