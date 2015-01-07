<%@ page import="java.util.Random" %>
<jsp:useBean id="errors" scope="request" type="java.util.Map" class="java.util.HashMap" />
<%@ include file="top.inc" %>

<%@ include file="middle.inc" %>
<h2>Create a Group</h2>
<% 
   String message = (String) request.getAttribute("message");
   if (message != null) {
      out.println("<p>" + message + "</p>");
   }
%>
<%
    		Random random = new Random();
    		int groupValue = random.nextInt(2000) + 1000;
%>

<form method="post" action="">
 	  <input type="hidden" name="groupId" value="<%=groupValue %>" />
 	  <input type="hidden" name="createById" value="${user.userId}" />
 	  <label>Name of Group&nbsp;&nbsp;&nbsp;</label><input type="text" name="name" size="32" />
 	     <%
      if (errors.containsKey("name")) {
          out.println("<span class=\"error\">" + errors.get("name") + "</span>");
      }
   %>
      </br><label>Groups Picture&nbsp;&nbsp;&nbsp;&nbsp;</label><input type="file" name="groupFile" size="25" />
          <%
       if (errors.containsKey("groupFile")) {
           out.println("<span class=\"error\">" + errors.get("groupFile") + "</span>");
       }
    %>
      </br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="submit" name="submit-button" value="Submit" />
    <input type="submit" name="cancel-button" value="Cancel" />
</form>
<%@ include file="bottom.inc" %>
