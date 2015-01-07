<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import= "ancestrysite.data.User" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map.Entry" %>

<%@ include file="top.inc" %>
<%@ include file="middle.inc" %>
 <div id="nav">
     
     <table>
  <%
 java.util.Hashtable<User, String> users = (java.util.Hashtable)request.getAttribute("randomUsers");
	
 Set set = users. entrySet();
     Iterator iter = set.iterator();  

      while (iter.hasNext()) {

     	Entry<User, String> elem = (Entry)iter.next();
 %>

<ul>  
 <a href="view-user?value=<%=elem.getKey().getUserId()%>"><img src=<%=elem.getValue()%>  height="100" width="100" hspace="10" Vspace="5"></a>  <%=elem.getKey().getFirstName()%>   <%=elem.getKey().getLastName() %>
</ul>
 <%
 }
 %>
</table>
     
     
  </div>
  <div id="content">
    <!-- Content Start -->
    <h3>Welcome to our ancestry site</h3>
   Have fun learning about you family tree!</br></br></br>
   <iframe width="650" height="425"
 src="http://www.youtube.com/embed/iYJcYLEN2tg">
</iframe> 
    <!-- Content End -->
  </div>    
 <div id="description">
   What's a family tree mean to you? Listen to what these children think a family tree is.
  </div>
  <div id="footer">
   <a href="contact">CONTACT</a><a href="terms">TERMS</a><a href="privacy">PRIVACY</a>
  </div>
  
