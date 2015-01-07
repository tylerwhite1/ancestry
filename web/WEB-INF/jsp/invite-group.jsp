<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import= "ancestrysite.data.User" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map.Entry" %>

<%@ include file="top.inc" %>

<%@ include file="middle.inc" %>
<h2>Invite Members To Group</h2>
<% 
   String message = (String) request.getAttribute("message");
   if (message != null) {
      out.println("<p>" + message + "</p>");
   }
%>
<form method="post" action="">
 	  			<input type="text" name="invite-search" placeholder="Member Search.........." size="50" />
      			<input type="submit"/>
      	
	  		</form>
</br>
<table>
 <form method="post">
<%

 java.util.Hashtable<User, String> usersInvite = (java.util.Hashtable)request.getAttribute("resultsList");
    if(usersInvite!=null){
		
		Set inviteSet = usersInvite.entrySet();
 		Iterator iterInvite = inviteSet.iterator();
    	while (iterInvite.hasNext()) {

	  		Entry<User, String> elem = (Entry)iterInvite.next();

 %>
 
<ul>
 <a href="view-user?value=<%=elem.getKey().getUserId()%>"><img src=<%=elem.getValue()%>  height="100" width="100" hspace="10" Vspace="5"></a>  <%=elem.getKey().getFirstName()%>   <%=elem.getKey().getLastName() %>
<input type="checkbox" name="inviteMember" value=<%=elem.getKey().getUserId() %>/> 
</ul>

 <%
 }}
 %>
<input type="submit" name="submit-invite" value="Invite Members" />
 </form>
</table>
<%@ include file="bottom.inc" %>