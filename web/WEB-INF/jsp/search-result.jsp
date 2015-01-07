<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import= "ancestrysite.data.User" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map.Entry" %>

<%@ include file="top.inc" %>

<%@ include file="middle.inc" %>
<h2>Search Results</h2>
<% 
  
   String list = (String) request.getAttribute("size");
   String message = (String) request.getAttribute("result");
      out.println("<p>" +"Search:Keyword(s): "+ message  +"</p>");
	  out.println("<p>" +"User(s) Found: "+ list +"</p>");
%>
<table>
  <%
 java.util.Hashtable<User, String> users = (java.util.Hashtable)request.getAttribute("users");
	
 Set set = users. entrySet();
     Iterator iter = set.iterator();  
    
   User ifLogin = (User) session.getAttribute("user");
   if (ifLogin ==null) {
      out.println( "Please " + "<a href="+"login"+">"+"Login"+"</a>"+" or "+"<a href="+"register"+">"+"Register"+"</a>"+" to the site to be able to search for members");
    }
    else{
    	
    

      while (iter.hasNext()) {

     	Entry<User, String> elem = (Entry)iter.next();
     	 if(elem.getKey().getActivation().equals("unlock")){
 
 %>
<ul>  
 <a href="view-user?value=<%=elem.getKey().getUserId()%>"><img src=<%=elem.getValue()%>  height="100" width="100" hspace="10" Vspace="5"></a>  <%=elem.getKey().getFirstName()%>   <%=elem.getKey().getLastName() %>
</ul>
 <%
}}}
 %>
</table>

<table>
  <%
  java.util.Hashtable<User, String> lockUsers = (java.util.Hashtable)request.getAttribute("users");
   Set setLock =lockUsers. entrySet();
     Iterator iterLock = setLock.iterator();  
 	while (iterLock.hasNext()) {

     	Entry<User, String> elem = (Entry)iterLock.next();
     	 if(elem.getKey().getActivation().equals("lock")){
 
 %>
<ul>  
 <img src=<%=elem.getValue()%>  height="100" width="100" hspace="10" Vspace="5"></a>  <%=elem.getKey().getFirstName()%>   <%=elem.getKey().getLastName() %>
</ul>
 <%
}}
 %>
</table>

<%@ include file="bottom.inc" %>