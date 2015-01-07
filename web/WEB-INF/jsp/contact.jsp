<jsp:useBean id="errors" scope="request" type="java.util.Map" class="java.util.HashMap" />
<%@ include file="top.inc" %>

<%@ include file="middle.inc" %>


<h2>Contact Admin</h2>
<% 
   String message = (String) request.getAttribute("message");
   if (message != null) {
      out.println("<p>" + message + "</p>");
   }
%>
<form method="post" action="">

<label>Enter your email</label><input type="text" name="email" size="40" placeholder="Email"/></br></br>

<label>Message</label></br>
<textarea name="message"cols="40" rows="16"></textarea></br></br>  

<input type="submit" name="submit-button" value="Submit" />
<input type="submit" name="cancel-button" value="Cancel" />



</form>

<%@ include file="bottom.inc" %>