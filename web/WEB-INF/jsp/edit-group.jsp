<jsp:useBean id="errors" scope="request" type="java.util.Map" class="java.util.HashMap" />
<%@ page import= "ancestrysite.data.FamilyGroup" %>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import= "ancestrysite.data.User" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map.Entry" %>

<%@ include file="top.inc" %>

<%@ include file="middle.inc" %>
<h2>Edit Group</h2>
<% 
   String message = (String) request.getAttribute("message");
   if (message != null) {
      out.println("<p>" + message + "</p>");
   }
%>
<% 
  FamilyGroup group = (FamilyGroup) request.getAttribute("groupEdit");
%>
<form method="post" action="">
 	  <label>Group's Name&nbsp;&nbsp;&nbsp;</label><input type="text" name="name"  value="<%=group.getGroupName()%>" size="32" />
 	     <%
      if (errors.containsKey("name")) {
          out.println("<span class=\"error\">" + errors.get("name") + "</span>");
      }
   %>
      <label>Group's Picture</label><input type="file" name="picture" size="25" />
      
      </br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="submit" name="submit-button" value="Submit" />
    <input type="submit" name="cancel-button" value="Cancel" />
</form>
</br>

<h2>Delete Member's From Group</h2>
   Check box to remove members
<table>
 <form method="post">
<%
 java.util.Hashtable<User, String> usersOfGroup = (java.util.Hashtable)request.getAttribute("groupMembers");

	Set groupSet = usersOfGroup.entrySet();
 	Iterator iterMembers = groupSet.iterator();
    while (iterMembers.hasNext()) {

	  Entry<User, String> elem = (Entry)iterMembers.next();

 %>
 
<ul>
 <a href="view-user?value=<%=elem.getKey().getUserId()%>"><img src=<%=elem.getValue()%>  height="100" width="100" hspace="10" Vspace="5"></a>  <%=elem.getKey().getFirstName()%>   <%=elem.getKey().getLastName() %>
<input type="checkbox" name="member" value=<%=elem.getKey().getUserId() %>/> 
</ul>

 <%
 }
 %>
<input type="submit" name="submit-remove" value="Remove Members" />
 </form>
</table>

<%@ include file="bottom.inc" %>





