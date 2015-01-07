package ancestrysite.web;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import ancestrysite.data.DataAccessObject;

public class Init implements ServletContextListener {
	    
	    public void contextDestroyed(ServletContextEvent sce) {
	    }
	    
	    private void contextInitialized2(ServletContext servletContext)
	    throws Exception {
	       InitialContext enc = new InitialContext();
	       Context compContext = (Context) enc.lookup("java:comp/env");
	       DataSource dataSource = (DataSource) compContext.lookup("datasourceForAncestryDB");

	       DataAccessObject.setDataSource(dataSource);
	    }

	    public void contextInitialized(ServletContextEvent sce) {
	        ServletContext servletContext = sce.getServletContext();
	        try {
	           contextInitialized2(servletContext);
	        }
	        catch (Exception e)
	        {
	           throw new RuntimeException(e);
	        }
	    }

}
