<head>

  <meta charset="utf-8">

  <title>jQuery UI Datepicker - Default functionality</title>

  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">

  <script src="//code.jquery.com/jquery-1.10.2.js"></script>

  <script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>

  <link rel="stylesheet" href="/resources/demos/style.css">

  <script>

  $(function() {

    $( "#datepicker" ).datepicker();

  });

  </script>

</head>


<%@ page import="java.util.Random" %>

<!-- edit-news-item.jsp -->

<jsp:useBean id="errors" scope="request" type="java.util.Map" class="java.util.HashMap" />
<%@ include file="top.inc" %>

<%@ include file="middle.inc" %>
<h2>Register</h2>
<% 
   String message = (String) request.getAttribute("message");
   if (message != null) {
      out.println("<p>" + message + "</p>");
   }
%>

<%
    		Random r = new Random();
    		int value = r.nextInt(1000 - 100) + 100;
%>
<%
	String father = (String) session.getAttribute("fatherId");
%>
<%
	String mother = (String) session.getAttribute("motherId");
%>

<form method="post" action="">
 	  <input type="hidden" name="userId" value="<%=value %>" />
 	<input type="hidden" name="idOfFather" size="40" placeholder="<%= father%>" value="<%= father%>" readonly/><a href="father-search">Add Father</a>   
       <%
      if (errors.containsKey("idOfFather")) {
          out.println("<span class=\"error\">" + errors.get("idOfFather") + "</span>");
      }
   %>
       </br></br><input type="hidden" name="idOfMother" size="40" placeholder="<%= mother%>" value="<%= mother%>" readonly/><a href="mother-search">Add Mother</a>
         <%
      if (errors.containsKey("idOfMother")) {
          out.println("<span class=\"error\">" + errors.get("idOfMother") + "</span>");
      }
   %>
 	  
 	  </br></br><label>First Name&nbsp;&nbsp;&nbsp;</label><input type="text" name="firstName" size="32" />
 	     <%
      if (errors.containsKey("firstName")) {
          out.println("<span class=\"error\">" + errors.get("firstName") + "</span>");
      }
   %>
      <label>Last Name</label><input type="text" name="lastName" size="32" />
          <%
       if (errors.containsKey("lastName")) {
           out.println("<span class=\"error\">" + errors.get("lastName") + "</span>");
       }
    %>
      </br></br><label>Date of birth</label><input type="text" id="datepicker" name="dateOfBirth"/>
          <%
       if (errors.containsKey("dateOfBirth")) {
           out.println("<span class=\"error\">" + errors.get("dateOfBirth") + "</span>");
       }
     %>
      </br></br><label>Email&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input type="text" name="email" size="40" />
           <%
       if (errors.containsKey("email")) {
           out.println("<span class=\"error\">" + errors.get("email") + "</span>");
       }
    %>
       
      </br></br><label>Gender:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input type="radio" name="gender" value="Male">Male<input type="radio" name="gender" value="Female">Female
          <%
       if (errors.containsKey("gender")) {
           out.println("<span class=\"error\">" + errors.get("gender") + "</span>");
       }
    %>
      </br></br><label>Activation:&nbsp;</label><input type="radio" name="activation" value="unlock" checked>Unlock<input type="radio" name="activation" value="lock" disabled>Lock</br></br>
 	  <label>Password&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input type="text" name="password" size="20" />
 	     <%
      if (errors.containsKey("password")) {
          out.println("<span class=\"error\">" + errors.get("password") + "</span>");
      }
   %>
      <label>Retype Password</label><input type="text" name="retypePassword" size="20" />
         <%
      if (errors.containsKey("retypePassword")) {
          out.println("<span class=\"error\">" + errors.get("retypePassword") + "</span>");
      }
   %>
      </br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="submit" name="submit-button" value="Submit" />
    <input type="submit" name="cancel-button" value="Cancel" />
</form>

<%@ include file="bottom.inc" %>