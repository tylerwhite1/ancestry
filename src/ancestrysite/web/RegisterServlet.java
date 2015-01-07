package ancestrysite.web;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ancestrysite.data.ImageUpload;
import ancestrysite.data.ParentNotification;
import ancestrysite.data.ParentNotificationDAO;
import ancestrysite.data.User;
import ancestrysite.data.UserDAO;

public class RegisterServlet extends HttpServlet {

	private RequestDispatcher register;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = config.getServletContext();
		register = context.getRequestDispatcher("/WEB-INF/jsp/register.jsp");

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		String idOfFather =  (String) session.getAttribute("fatherId");
		session.setAttribute("fatherId", idOfFather);
		String idOfMother =   (String) session.getAttribute("motherId");
		session.setAttribute("motherId", idOfMother);
		register.forward(req, resp);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String cancelButton = req.getParameter("cancel-button");
		if (cancelButton != null) {
			resp.sendRedirect("home");
			return;
		}
		String id = req.getParameter("userId");
		Long iduser = new Long(id);
		String password = req.getParameter("password");
		String firstName = req.getParameter("firstName");
		if (!firstName.equals("")) {

			firstName = firstName.toLowerCase();
			firstName = firstName.substring(0, 1).toUpperCase()
					+ firstName.substring(1);
		}
		String lastName = req.getParameter("lastName");
		if (!lastName.equals("")) {
			lastName = lastName.toLowerCase();
			lastName = lastName.substring(0, 1).toUpperCase()
					+ lastName.substring(1);
		}
		String birthDate = req.getParameter("dateOfBirth");
		String email = req.getParameter("email");
		String gender = req.getParameter("gender");
		String activation = req.getParameter("activation");
		HttpSession session = req.getSession();
		String idOfFather = (String) session.getAttribute("fatherId");
		String idOfMother = (String) session.getAttribute("motherId");
		
		User user = new User();
		user.setUserId(iduser);
		user.setBirthDate(birthDate);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPassword(password);
		user.setActivation(activation);
		user.setGender(gender);
		if(idOfFather !=null && idOfMother !=null){
			user.setFatherId(Long.valueOf(idOfFather));
			user.setMotherId(Long.valueOf(idOfMother));
		}
		Map<String, String> errors = validate(req, user);
		if (!errors.isEmpty()) {
			req.setAttribute("user", user);
			register.forward(req, resp);
			return;
		}

		new UserDAO().createStudent(user);
		ImageUpload image = new ImageUpload();
		URL main = ProfileServlet.class.getResource("ProfileServlet.class");
		File path = new File(main.getPath());
		String savePath = path.getPath().split("WEB")[0];
		String picLocation = savePath + "image\\noAvatar.png";
		image.saveImage(savePath, user, picLocation);
		if(user.getFatherId().equals(0)){
			generateFatherNotification(session, user.getUserId());
		}
		if(user.getMotherId().equals(0)){
		generateMotherNotification(session, user.getUserId());
		}
		resp.sendRedirect("login");

	}

	public static Map<String, String> validate(HttpServletRequest req, User user) {
		HashMap<String, String> errors = new HashMap<String, String>();
		req.setAttribute("errors", errors);

		String password = req.getParameter("password");
		String retypePassword = req.getParameter("retypePassword");
		if (!password.equals(retypePassword)) {
			errors.put("password", "Both passwords need to be the same.");
		}
		if (retypePassword == null || retypePassword.length() == 0) {
			errors.put("retypePassword", "Retype Password.");
		}
		String firstName = req.getParameter("firstName");
		if (firstName == null || firstName.length() == 0) {
			errors.put("firstName", "First Name required.");
		}
		//firstName = firstName.toLowerCase();
		//firstName = firstName.substring(0, 1).toUpperCase()
				//+ firstName.substring(1);
		user.setFirstName(firstName);

		String lastName = req.getParameter("lastName");
		if (lastName == null || lastName.length() == 0) {
			errors.put("lastName", "Last Name required.");
		}
		//lastName = lastName.toLowerCase();
		//lastName = lastName.substring(0, 1).toUpperCase()
				//+ firstName.substring(1);
		user.setLastName(lastName);

		String email = req.getParameter("email");
		if (email == null || email.length() == 0) {
			errors.put("email", "Email required.");
		}
		user.setEmail(email);

		ArrayList<User> existingEmails = new UserDAO().checkForSameEmail(email);

		if (existingEmails.size() > 1) {
			errors.put("email", "The email you provided already exist");
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
		user.setActivation(activation);

		String idOfFather = req.getParameter("idOfFather");
		if (idOfFather == null || idOfFather.length() == 0) {
			errors.put("idOfFather", "Father's ID is required.");
		}else{
		user.setFatherId(Long.valueOf(idOfFather));}
		
		String idOfMother = req.getParameter("idOfMother");
		if (idOfMother == null || idOfMother.length() == 0) {
			errors.put("idOfMother", "Mother's ID is required.");
		}else{
		user.setMotherId(Long.valueOf(idOfMother));}

		return errors;
	}
	
	public void generateFatherNotification(HttpSession session, Long userId){
		
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
	
	public void generateMotherNotification(HttpSession session, Long userId){
		
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
