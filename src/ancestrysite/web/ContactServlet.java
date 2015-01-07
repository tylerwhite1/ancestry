package ancestrysite.web;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ancestrysite.data.User;
import ancestrysite.data.UserDAO;

public class ContactServlet extends HttpServlet {

	private RequestDispatcher contact;

	
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = config.getServletContext();
		contact = context.getRequestDispatcher("/WEB-INF/jsp/contact.jsp");

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//super.doGet(req, resp);
		//req.setAttribute("message", "bye");
		contact.forward(req, resp);

	}
	
	protected void forwardBack(String loggerMessage, String name,
			String message, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setAttribute(name, message);
		contact.forward(req, resp);
		return;
	}

	   protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
	    	    throws ServletException, IOException {

		   String email = req.getParameter("email");
		   String message = req.getParameter("message");
		   String cancelButton = req.getParameter("cancel-button");
			if (cancelButton != null) {
				resp.sendRedirect("home");
				return;
			}
		   
		   if((email.length()>0 && email != null) && (message.length()>0 && message != null)){
			   checkValidationForStudent(req, resp, email, message);
		   }
		   else{
		       req.setAttribute("message", "Please provide both an accurate email and a message");
		       contact.forward(req, resp);
		       return;
	       } 
	   }
	   
	   public void checkValidationForStudent(HttpServletRequest req,
				HttpServletResponse resp, String email, String message) throws ServletException,
				IOException {

				sendPasswordThroughEmail(email, message);
				forwardBack("Message sent", "message",
							"The message has been sent to the sites admin. They will email you back at the email you have provided", req, resp);
	
	   }
	   
	   public void sendPasswordThroughEmail(String email, String message){
		   
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
		        msg.setRecipients( Message.RecipientType.TO,InternetAddress.parse(email) );
		        msg.setSentDate( new Date());
		        msg.setSubject( "Urgent" );

		        //--[ Create the body of the mail
		        msg.setText(message);

		        //--[ Ask the Transport class to send our mail message
		        Transport.send( msg );

		    }catch(Exception E){

		    }

	   }
}
