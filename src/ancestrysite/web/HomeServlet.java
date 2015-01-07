package ancestrysite.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ancestrysite.data.User;
import ancestrysite.data.UserDAO;

public class HomeServlet extends HttpServlet {

	private RequestDispatcher homeJsp;

	
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = config.getServletContext();
		homeJsp = context.getRequestDispatcher("/WEB-INF/jsp/home.jsp");

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		

		ArrayList<User> users =new UserDAO().getRandomUsers();
		java.util.Hashtable<User, String> results = new java.util.Hashtable<User, String>();

		URL main = ProfileServlet.class.getResource("ProfileServlet.class");
	    File path = new File(main.getPath());
	    String result = path.getPath().split("WEB")[0];
	    result = result+"image\\";
		File dir = new File(result);
		for(User current : users){
				for(File imgFile : dir.listFiles()) {
					
					String userImage = Long.toString(current.getUserId())+".png";
					if(userImage.equals(imgFile.getName())){
						results.put(current,"image/"+imgFile.getName());
					}
			}
		}
		req.setAttribute("randomUsers", results);
		
		homeJsp.forward(req, resp);
	}

}
