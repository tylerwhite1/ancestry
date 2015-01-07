package ancestrysite.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ancestrysite.data.User;
import ancestrysite.data.UserDAO;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

public class RetrievePassword extends HttpServlet {

	private RequestDispatcher retrievePassword;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = config.getServletContext();
		retrievePassword = context
				.getRequestDispatcher("/WEB-INF/jsp/retrievePassword.jsp");

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		retrievePassword.forward(req, resp);
	}

	protected void forwardBack(String loggerMessage, String name,
			String message, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setAttribute(name, message);
		retrievePassword.forward(req, resp);
		return;
	}

	   protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
	    	    throws ServletException, IOException {

		   String email = req.getParameter("email");
		 
		   if(email.length()>0){
			   
			   User user = new UserDAO().find(email);
			   checkValidationForStudent(req, resp, user);
		   }
		   else{

		       req.setAttribute("message", "The email you entered wasn't in our database, please try again");
		       retrievePassword.forward(req, resp);
		       return;
	       } 
	   }
	   
	   public void checkValidationForStudent(HttpServletRequest req,
				HttpServletResponse resp, User user) throws ServletException,
				IOException {

			if (user == null) {
				req.setAttribute("message",
						"The email doesn't exist in our system , please try again");
				retrievePassword.forward(req, resp);
				return;
			} else {
				sendPasswordThroughEmail(user);
				forwardBack("password sent", "message",
							"The password for the account has been sent please check your email", req, resp);
			}
	
	   }
	   
	   public void sendPasswordThroughEmail(User user){
		   
		   try{

		        Properties props = new Properties();
		        props.put("mail.smtp.host", "smtp.mail.yahoo.com"); // for gmail use smtp.gmail.com
		        props.put("mail.smtp.auth", "true");
		        props.put("mail.debug", "true"); 
		        props.put("mail.smtp.starttls.enable", "true");
		        props.put("mail.smtp.port", "465");
		        props.put("mail.smtp.socketFactory.port", "465");
		        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		        props.put("mail.smtp.socketFactory.fallback", "false");

		        Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {

		            protected PasswordAuthentication getPasswordAuthentication() {
		                return new PasswordAuthentication("tyler15white@yahoo.com", "titanisfunny1");
		            }
		        });

		        mailSession.setDebug(true); // Enable the debug mode

		        Message msg = new MimeMessage( mailSession );

		        //--[ Set the FROM, TO, DATE and SUBJECT fields
		        msg.setFrom( new InternetAddress( "tyler15white@yahoo.com" ) );
		        msg.setRecipients( Message.RecipientType.TO,InternetAddress.parse(user.getEmail()) );
		        msg.setSentDate( new Date());
		        msg.setSubject( "Password Recovery" );

		        //--[ Create the body of the mail
		        msg.setText( "Hello, we've been nofitifed that you have forgotten your password at our ancestry site."
		        		+ " The password for your account is: "+user.getPassword() );

		        //--[ Ask the Transport class to send our mail message
		        Transport.send( msg );

		    }catch(Exception E){

		    }

	   }
}
