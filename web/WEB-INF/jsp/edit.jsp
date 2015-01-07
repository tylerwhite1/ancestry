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


<jsp:useBean id="errors" scope="request" type="java.util.Map" class="java.util.HashMap" />
<%@ include file="top.inc" %>

<%@ include file="middle.inc" %>
<h2>Edit Account</h2>
<% 
   String message = (String) request.getAttribute("message");
   if (message != null) {
      out.println("<p>" + message + "</p>");
   }
%>

<form method="post" action="">

	<input type="hidden" name="idOfFather" size="40" value="${user.fatherId}" readonly/><a href="edit-father">Edit Father</a>   
       <%
      if (errors.containsKey("idOfFather")) {
          out.println("<span class=\"error\">" + errors.get("idOfFather") + "</span>");
      }
   %>
       </br></br><input type="hidden" name="idOfMother" size="40" value="${user.motherId}" readonly/><a href="edit-mother">Edit Mother</a>
         <%
      if (errors.containsKey("idOfMother")) {
          out.println("<span class=\"error\">" + errors.get("idOfMother") + "</span>");
      }
   %>

 	   </br></br><label>First Name&nbsp;&nbsp;&nbsp;</label><input type="text" name="firstName"  value="${user.firstName}" size="32" />
 	     <%
      if (errors.containsKey("firstName")) {
          out.println("<span class=\"error\">" + errors.get("firstName") + "</span>");
      }
   %>
      <label>Last Name</label><input type="text" name="lastName" value="${user.lastName}" size="32" />
          <%
       if (errors.containsKey("lastName")) {
           out.println("<span class=\"error\">" + errors.get("lastName") + "</span>");
       }
    %>
      </br></br><label>Date of birth</label><input type="text" id="datepicker" name="dateOfBirth" value="${user.birthDate}" />
          <%
       if (errors.containsKey("dateOfBirth")) {
           out.println("<span class=\"error\">" + errors.get("dateOfBirth") + "</span>");
       }
     %>
      </br></br><label>Email&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input type="text" name="email" value="${user.email}" size="40" />
           <%
       if (errors.containsKey("email")) {
           out.println("<span class=\"error\">" + errors.get("email") + "</span>");
       }
    %>
      <%
      User userGender = (User) session.getAttribute("user");
      String checkMale = "";
      String checkFemale = "";
      if(userGender.getGender().equals("Male")){
      	checkMale="checked";
      }
      else{
      	checkFemale="checked";
      } 
    %>
      </br></br><label>Gender:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input type="radio" name="gender" value="Male" <%=checkMale %>>Male<input type="radio" name="gender" value="Female"<%=checkFemale %>>Female
          <%
       if (errors.containsKey("gender")) {
           out.println("<span class=\"error\">" + errors.get("gender") + "</span>");
       }
    %>
    
    <%
      User userActivation = (User) session.getAttribute("user");
      String checkLock = "";
      String checkUnlock = "";
      if(userActivation.getActivation().equals("unlock")){
      	checkUnlock="checked";
      }
      else{
      	checkLock="checked";
      } 
    %>
    
      </br></br><label>Activation:&nbsp;</label><input type="radio" name="activation" value="unlock" <%=checkUnlock %>>Unlock<input type="radio" name="activation" value="lock" <%=checkLock %>>Lock</br></br>
 	  <label>Password&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input type="text" name="password" value="${user.password}" size="20" />
 	     <%
      if (errors.containsKey("password")) {
          out.println("<span class=\"error\">" + errors.get("password") + "</span>");
      }
   %>
      </br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="submit" name="submit-button" value="Submit" />
    <input type="submit" name="cancel-button" value="Cancel" />
</form>

<%@ include file="bottom.inc" %>