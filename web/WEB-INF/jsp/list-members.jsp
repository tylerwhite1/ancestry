<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import= "ancestrysite.data.User" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map.Entry" %>
<%@ include file="top.inc" %>

<%@ include file="middle.inc" %>
<h2>Users that are part of the group</h2>
<table>
  <%
 java.util.Hashtable<User, String> allmembers = (java.util.Hashtable)request.getAttribute("allmembers");
	
 Set listSet = allmembers. entrySet();
     Iterator iterList = listSet.iterator();  
   

      while (iterList.hasNext()) {

     	Entry<User, String> elem = (Entry)iterList.next();
     	 if(elem.getKey().getActivation().equals("unlock")){
 %>

<ul>  
 <a href="view-user?value=<%=elem.getKey().getUserId()%>"><img src=<%=elem.getValue()%>  height="100" width="100" hspace="10" Vspace="5"></a>  <%=elem.getKey().getFirstName()%>   <%=elem.getKey().getLastName() %>
</ul>
 <%
 }}
 %>
</table>

<table>
  <%
  java.util.Hashtable<User, String> lockUsers = (java.util.Hashtable)request.getAttribute("allmembers");
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