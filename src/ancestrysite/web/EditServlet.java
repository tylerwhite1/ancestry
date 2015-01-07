package ancestrysite.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

public class EditServlet extends HttpServlet {

	private RequestDispatcher edit;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = config.getServletContext();
		edit = context.getRequestDispatcher("/WEB-INF/jsp/edit.jsp");

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// super.doGet(req, resp);
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		User userLogin = new UserDAO().find(user.getEmail());
		session.setAttribute("user", userLogin);
		//String idOfFather = (String) session.getAttribute("fatherId");
		//session.setAttribute("fatherId", idOfFather);
		//String idOfMother = (String) session.getAttribute("motherId");
		//session.setAttribute("motherId", idOfMother);
		edit.forward(req, resp);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String cancelButton = req.getParameter("cancel-button");
		if (cancelButton != null) {
			resp.sendRedirect("profile");
			return;
		}
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		User userLogin = new UserDAO().find(user.getEmail());
		Map<String, String> errors = validate(req, userLogin);

		if (!errors.isEmpty()) {
			// session.setAttribute("user", userLogin);
			edit.forward(req, resp);
			return;
		}
		if (userLogin.getFatherId().equals(0)) {
			generateFatherNotification(session, userLogin.getUserId());
		}
		if (userLogin.getMotherId().equals(0)) {
			generateMotherNotification(session, userLogin.getUserId());
		}
		session.setAttribute("user", userLogin);

		new UserDAO().update(userLogin);
		//String idOfFather = req.getParameter("idOfFather");
		resp.sendRedirect("home");

	}

	public static Map<String, String> validate(HttpServletRequest req, User user) {
		HashMap<String, String> errors = new HashMap<String, String>();
		req.setAttribute("errors", errors);

		String password = req.getParameter("password");
		if (password == null || password.length() == 0) {
			errors.put("password", "Password Required.");
		}
		user.setPassword(password);

		String firstName = req.getParameter("firstName");
		if (firstName == null || firstName.length() == 0) {
			errors.put("firstName", "First Name required.");
		}
		user.setFirstName(firstName);

		String lastName = req.getParameter("lastName");
		if (lastName == null || lastName.length() == 0) {
			errors.put("lastName", "Last Name required.");
		}
		user.setLastName(lastName);

		String email = req.getParameter("email");
		ArrayList<User> existingEmails = new UserDAO().checkForSameEmail(email);

		if (existingEmails.size() > 0 && !user.getEmail().equals(email)) {
			errors.put("email", "The email you provided already exist");
		}
		user.setEmail(email);

		if (email == null || email.length() == 0) {
			errors.put("email", "Email required.");
		}
		user.setEmail(email);

		String birthDate = req.getParameter("dateOfBirth");
		if (birthDate == null || birthDate.length() == 0) {
			errors.put("dateOfBirth", "Date of birth required.");
		}
		user.setBirthDate(birthDate);

		String gender = req.getParameter("gender");
		if (gender == null || gender.length() == 0) {
			errors.put("gender", "Gender required.");
		}
		user.setGender(gender);

		String activation = req.getParameter("activation");
		if (activation == null || activation.length() == 0) {
			errors.put("activation", "Activation required.");
		}

		user.setPassword(password);
		user.setActivation(activation);

		String idOfFather = req.getParameter("idOfFather");
		HttpSession session = req.getSession();
		//String fatherId = (String) session.getAttribute("fatherId");
		///if(fatherId == null){
			//if (idOfFather == null || idOfFather.length() == 0) {
			//	errors.put("idOfFather", "Father's ID is required.");
			//} else {
			//	user.setFatherId(Long.valueOf(idOfFather));
			//}
		//}
		//else{
			//user.setFatherId(Long.valueOf(fatherId));
		//	}
		
		//String idOfMother = req.getParameter("idOfMother");
		//String motherId = (String) session.getAttribute("motherId");
		//if(motherId == null){
		//if (idOfMother == null || idOfMother.length() == 0) {
		//	errors.put("idOfMother", "Mother's ID is required.");
		//} else {
		//	user.setMotherId(Long.valueOf(idOfMother));
	//		}
	//	}
		//else{
		//	user.setMotherId(Long.valueOf(motherId));
		//	}
		
		return errors;
	}

	public void generateFatherNotification(HttpSession session, Long userId) {

		String fatherFirst = (String) session.getAttribute("fatherFirst");
		String fatherLast = (String) session.getAttribute("fatherLast");
		Random random = new Random();
		int notificationValue = random.nextInt(2000) + 1000;
		Long notificationId = Long.valueOf(notificationValue);
		ParentNotification notification = new ParentNotification();
		notification.setUserId(userId);
		notification.setNotificationId(notificationId);
		notification.setParentFirstName(fatherFirst);
		notification.setParentLastName(fatherLast);
		new ParentNotificationDAO().createParentNotification(notification);
	}

	public void generateMotherNotification(HttpSession session, Long userId) {

		String motherFirst = (String) session.getAttribute("motherFirst");
		String motherLast = (String) session.getAttribute("motherLast");
		Random random = new Random();
		int notificationValue = random.nextInt(2000) + 1000;
		Long notificationId = Long.valueOf(notificationValue);
		ParentNotification notification = new ParentNotification();
		notification.setUserId(userId);
		notification.setNotificationId(notificationId);
		notification.setParentFirstName(motherFirst);
		notification.setParentLastName(motherLast);
		new ParentNotificationDAO().createParentNotification(notification);
	}

}
