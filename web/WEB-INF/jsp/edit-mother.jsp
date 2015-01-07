<jsp:useBean id="errors" scope="request" type="java.util.Map" class="java.util.HashMap" />
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import= "ancestrysite.data.User" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map.Entry" %>
<%@ include file="top.inc" %>

<%@ include file="middle.inc" %>
<h2>Search for your mother</h2>
<% 
   String message = (String) request.getAttribute("message");
   if (message != null) {
      out.println("<p>" + message + "</p>");
   }
%>
<form method="post" action="">
 	  			<input type="text" name="mother-name" placeholder="Member Search.........." size="50" />
      			<input type="submit" name="submit-query" value="Search"/>
      	
	  		</form>
</br>
<table>
 <form method="post">
<%

 java.util.Hashtable<User, String> usersList = (java.util.Hashtable)request.getAttribute("motherList");
    if(usersList!=null){
		
		Set inviteSet = usersList.entrySet();
 		Iterator iterInvite = inviteSet.iterator();
    	while (iterInvite.hasNext()) {

	  		Entry<User, String> elem = (Entry)iterInvite.next();

 %>
 
<ul>
 <a href="view-user?value=<%=elem.getKey().getUserId()%>"><img src=<%=elem.getValue()%>  height="100" width="100" hspace="10" Vspace="5"></a>  <%=elem.getKey().getFirstName()%>   <%=elem.getKey().getLastName() %>
<input type="radio" name="motherOf" value=<%=elem.getKey().getUserId() %>/> 
</ul>

 <%
 }}
 %>
<input type="submit" name="submit-search" value="Enter Mother Info" />
 </form>
</table>

<form method="post">
 	  			</br><label>If your parent is not a member of the site yet, add their name and we will send </br>you a notification of
 	  			when they do.</label></br>
 	  			<input type="text" name="mother-first" placeholder="Mother First Name...." size="25" />
 	  			<%
      if (errors.containsKey("mother-first")) {
          out.println("<span class=\"error\">" + errors.get("mother-first") + "</span>");
      }
   %>
 	  			<input type="text" name="mother-last" placeholder="Mother Last Name...." size="25" />
 	  			<%
      if (errors.containsKey("mother-last")) {
          out.println("<span class=\"error\">" + errors.get("mother-last") + "</span>");
      }
   %>
      			<input type="submit" name="submit-parent" value="Add Mother"/>
      	
</form>
</br></br>
<form method="post">
<input type="submit" name="cancel-button" value="Cancel" />
</form>
<%@ include file="bottom.inc" %>