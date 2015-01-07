<%@ page import="ancestrysite.data.User" %>
<html> 
<head> 
  <title>Ancestry Tree</title>
  <link href="ancestry.css" rel="stylesheet" media="screen">
</head>

<body>
<div id="wrapper">
	  <id="header"><a href="home"><img src="image/bnr.jpg" /><a/>
	  
	<div id="ui">
  		<div id="search">
  			<form method="post" action="result">
 	  			<input type="text" name="search" placeholder="Search.........." size="60" />
      			<input type="submit"/>
      	
	  		</form>
	  			</div>

	<% 
	
  User user = (User) session.getAttribute("user");
  String requestNumber = (String) session.getAttribute("requestNum");
  String notifyNumber = (String) session.getAttribute("notifyNum");
   if (user ==null) {
      out.println("<a href="+"login"+">"+"Login"+"</a>"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+"<a href="+"register"+">"+"Register"+"</a>");
    }
    else if(user.getActivation().equals("lock")){
    	out.println("Welcome,      "+user.getFirstName()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+"<a href="+"edit"+">"+"Edit"+"</a>"+"&nbsp;&nbsp;&nbsp;"+"&nbsp;&nbsp;&nbsp;"+"<a href="+"logout"+">"+"Logout"+"</a>");
    }
    else{
   	  out.println("Welcome,      "+"<a href="+"profile"+">"+user.getFirstName()+"</a>"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+"<a href="+"edit"+">"+"Edit"+"</a>"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+"<a href="+"notification"+">"+"Notification: "+notifyNumber+"</a>"+"&nbsp;&nbsp;&nbsp;"+"<a href="+"invitation"+">"+"Invitation: "+requestNumber+"</a>"+"&nbsp;&nbsp;&nbsp;"+"<a href="+"logout"+">"+"Logout"+"</a>");
	}
	%>
	 </div>  		
	  		