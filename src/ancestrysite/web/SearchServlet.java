package ancestrysite.web;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ancestrysite.data.User;
import ancestrysite.data.UserDAO;

public class SearchServlet extends HttpServlet {

	private RequestDispatcher jsp;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub

		ServletContext context = config.getServletContext();
		jsp = context.getRequestDispatcher("/WEB-INF/jsp/search-result.jsp");
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("search", "search");
		jsp.forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String searchResult = req.getParameter("search");
		ArrayList<User> users =new UserDAO().userList();
		
		java.util.Hashtable<User, String> results = new java.util.Hashtable<User, String>();

		URL main = ProfileServlet.class.getResource("ProfileServlet.class");
	    File path = new File(main.getPath());
	    String result = path.getPath().split("WEB")[0];
	    result = result+"image\\";
		File dir = new File(result);

		String[] firstAndLast = searchResult.split("\\s+");
		String firstName = firstAndLast[0];
		firstName = firstName.toLowerCase();
		firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
		for(User current : users){
			if(current.getFirstName().equals(firstName)){
				for(File imgFile : dir.listFiles()) {
					
					 String userImage = Long.toString(current.getUserId())+".png";
					if(userImage.equals(imgFile.getName())){
						results.put(current,"image/"+imgFile.getName());
					}
				}
			}
			else if(current.getLastName().equals(firstName)){
				for(File imgFile : dir.listFiles()) {
					
					 String userImage = Long.toString(current.getUserId())+".png";
					if(userImage.equals(imgFile.getName())){
						results.put(current,"image/"+imgFile.getName());
					}
				}
			}
		}
		
		if(searchResult.contains(" ")){
			String lastName = firstAndLast[1];
			lastName = lastName.toLowerCase();
			lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
			for(User current : users){
				
				if(current.getLastName().equals(lastName)){
					
					for(File imgFile : dir.listFiles()) {
						
						 String userImage = Long.toString(current.getUserId())+".png";
						if(userImage.equals(imgFile.getName())){
							results.put(current,"image/"+imgFile.getName());
						}
					}
					
				}
			}
		}
		req.setAttribute("result", searchResult);
		req.setAttribute("users", results);
		req.setAttribute("size", Long.toString(results.size()));
		jsp.forward(req, resp);
		return;
	}
	
	

}
