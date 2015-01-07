<%@ include file="top.inc" %>

<%@ include file="middle.inc" %>
<h2>Password Recovery</h2>
</br>
<h4>Please enter the email address that you registered with and you're password will be sent
to you</h4>
<% 
   String message = (String) request.getAttribute("message");
   if (message != null) {
      out.println("<p>" + message + "</p>");
   }
%>
<form method="post" action="">

 	  <label>Email</label><input type="text" name="email" size="35" /></br>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="Submit">
</form>
<a href="register">Don't have an account yet?</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="login">login</a>
<%@ include file="bottom.inc" %>