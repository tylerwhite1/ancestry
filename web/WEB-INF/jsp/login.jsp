<jsp:useBean id="path" scope="request" class="java.lang.String" />
<%@ include file="top.inc" %>

<%@ include file="middle.inc" %>
<h2>Login</h2>
<% 
   String message = (String) request.getAttribute("message");
   if (message != null) {
      out.println("<p>" + message + "</p>");
   }
%>

<form method="post" action="">

 	  <label>Email        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input type="text" name="email" size="32" /></br>
      <label>Password</label><input type="password" name="password" size="32" /></br>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"/>
</form>
<a href="register">Don't have an account yet?</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="retrievePassword">Forgot Password?</a>
<%@ include file="bottom.inc" %>