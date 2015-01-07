<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import= "ancestrysite.data.User" %>
<%@ page import= "ancestrysite.data.FamilyGroup" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map.Entry" %>

<%@ include file="top.inc" %>

<%@ include file="middle.inc" %>
<h2>Parent Notification</h2>
<% 
	
  String requestValue = (String) session.getAttribute("notifyNum");
   if (requestValue.equals("0")) {
      out.println("You have no parent notifications at this time");
  }
%>
</br>
<form method="post">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="submit" name="cancel-button" value="Cancel" />
</form>
</br>
<table>
<h4>Father's Notification</h4>
  <%
 java.util.Hashtable<User, String> users = (java.util.Hashtable)request.getAttribute("parentResult");
	
 Set set = users. entrySet();
     Iterator iter = set.iterator();  


      while (iter.hasNext()) {

     	Entry<User, String> elem = (Entry)iter.next();
 		if(elem.getKey().getGender().equals("Male")){
 %>
<ul>  
 <a href="view-user?value=<%=elem.getKey().getUserId()%>"><img src=<%=elem.getValue()%>  height="100" width="100" hspace="10" Vspace="5"></a>  <%=elem.getKey().getFirstName()%>   <%=elem.getKey().getLastName() %>
<a href="parent-notify?parentvalue=<%=elem.getKey().getUserId()%>">Is this your father</a> 
</ul>
 <%
}}
 %>
</table>
</br>
<table>
<h4>Mother's Notification</h4>
  <%
 java.util.Hashtable<User, String> motherUser = (java.util.Hashtable)request.getAttribute("parentResult");
	
 Set motherset = motherUser. entrySet();
     Iterator motheriter = motherset.iterator();  


      while (motheriter.hasNext()) {

     	Entry<User, String> elem = (Entry)motheriter.next();
 		if(elem.getKey().getGender().equals("Female")){
 %>
<ul>  
 <a href="view-user?value=<%=elem.getKey().getUserId()%>"><img src=<%=elem.getValue()%>  height="100" width="100" hspace="10" Vspace="5"></a>  <%=elem.getKey().getFirstName()%>   <%=elem.getKey().getLastName() %>
<a href="parent-notify?parentvalue=<%=elem.getKey().getUserId()%>">Is this your mother</a> 
</ul>
 <%
}}
 %>
</table>
<%@ include file="bottom.inc" %>