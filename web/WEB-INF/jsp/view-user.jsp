<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script>jQuery(document).ready(function() {
    jQuery('.tabs .tab-links a').on('click', function(e)  {
        var currentAttrValue = jQuery(this).attr('href');
 
        // Show/Hide Tabs
        jQuery('.tabs ' + currentAttrValue).siblings().slideUp(400);
jQuery('.tabs ' + currentAttrValue).delay(400).slideDown(400);


 
        // Change/remove current tab to active
        jQuery(this).parent('li').addClass('active').siblings().removeClass('active');
 
        e.preventDefault();
    });
});</script>

<%@ page import= "ancestrysite.data.User" %>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.ArrayList" %>
<%@ page import= "ancestrysite.data.FamilyGroup" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map.Entry" %>

<%@ include file="top.inc" %>

<%@ include file="middle.inc" %>
<%
    		 User user2 = (User) request.getAttribute("user");
%>


<div id="profile-nav">
    <div id="profile-pic">
    	<img src="image/<%=user2.getUserId()%>.png"  height="250" width="240" hspace="30" Vspace="15">
	</div>
	<div id="profile-family" style="overflow-y: scroll;">
  <table>
  <%
 java.util.Hashtable<User, String> friendsThatsfamily = (java.util.Hashtable)request.getAttribute("familyMembers");

 Set friendsThatsfamilyset = friendsThatsfamily. entrySet();
     Iterator friendsThatsfamilyiter = friendsThatsfamilyset.iterator();  
    
      while (friendsThatsfamilyiter.hasNext()) {

     	Entry<User, String> elem = (Entry)friendsThatsfamilyiter.next();
 
 %>
<ul style="list-style-type: none; display: inline-block;">
 <a href="view-user?value=<%=elem.getKey().getUserId()%>"><img src=<%=elem.getValue()%>  height="70" width="70" hspace="0" Vspace="0"></a>  
  </br><%=elem.getKey().getFirstName()%>   <%=elem.getKey().getLastName() %>
</ul>
 <%
}
 %>
</table>
	</div>
    
</div>
<div class="tabs">
    <ul class="tab-links">
        <li class="active"><a href="#tab1"><%=user2.getFirstName()%>  <%=user2.getLastName()%></a></li>
        <li><a href="#tab2">Family Members</a></li>
        <li><a href="#tab3">Immediate Family</a></li>
        <li><a href="#tab4">Father's Side</a></li>
        <li><a href="#tab5">Mother's Side</a></li>
        <li><a href="#tab6">Group</a></li>
    </ul>
 
    <div class="tab-content">
        <div id="tab1" class="tab active">
            <p>Name: <%=user2.getFirstName()%>  <%=user2.getLastName()%></p>
            <p>Email: <%=user2.getEmail()%></p>
            <p>Gender: <%=user2.getGender()%></p>
            <p>Date of birth: <%=user2.getBirthDate()%></p>
        </div>
 
        <div id="tab2" class="tab">
            <p>Family Members</p>
            <table>
  <%
 java.util.Hashtable<User, String> family = (java.util.Hashtable)request.getAttribute("familyMembers");

 Set familyset = family. entrySet();
     Iterator familyiter = familyset.iterator();  
    
      while (familyiter.hasNext()) {

     	Entry<User, String> elem = (Entry)familyiter.next();
 
 %>
<ul style="list-style-type: none; display: inline-block;">
 <a href="view-user?value=<%=elem.getKey().getUserId()%>"><img src=<%=elem.getValue()%>  height="100" width="100" hspace="10" Vspace="5"></a>  
 </br><%=elem.getKey().getFirstName()%>   <%=elem.getKey().getLastName() %>
</ul>
 <%
}
 %>
</table>
</div>
 
        <div id="tab3" class="tab">
            <table>
            
             <%
 java.util.Hashtable<User, String> immediateTree = (java.util.Hashtable)request.getAttribute("immediateTree");

 Set immediateset = immediateTree. entrySet();
     Iterator immediateiter = immediateset.iterator();  
    
      while (immediateiter.hasNext()) {

     	Entry<User, String> elem = (Entry)immediateiter.next();
 
 %>
<ul>
 <a href="view-user?value=<%=elem.getKey().getUserId()%>"><%=elem.getValue()%></a>  
</ul>
 <%
}
 %>
</table>
</div>
        
        <div id="tab4" class="tab">
            <table>
             <%

 java.util.Hashtable<User, String> fatherTree = (java.util.Hashtable)request.getAttribute("fatherTree");
 
 Set fatherTreeset = fatherTree. entrySet();
     Iterator fatherTreeiter = fatherTreeset.iterator();  
    
      while (fatherTreeiter.hasNext()) {

     	Entry<User, String> elem = (Entry)fatherTreeiter.next();
 
 %>
<ul>
 <a href="view-user?value=<%=elem.getKey().getUserId()%>"><%=elem.getValue() %></a>  
</ul>
 <%
}
 %>
</table>
</div>
 
 		<div id="tab5" class="tab">
           <table>
            
             <%
 java.util.Hashtable<User, String> motherTree = (java.util.Hashtable)request.getAttribute("motherTree");

 Set motherTreeset = motherTree. entrySet();
     Iterator motherTreeiter = motherTreeset.iterator();  
    
      while (motherTreeiter.hasNext()) {

     	Entry<User,String> elem = (Entry)motherTreeiter.next();
 
 %>
<ul>
 <a href="view-user?value=<%=elem.getKey().getUserId()%>"><%=elem.getValue() %></a>  
</ul>
 <%
}
 %>
</table>
</div>
 
        <div id="tab6" class="tab">
          <p>Groups that <%=user2.getFirstName()%>  <%=user2.getLastName()%> is a member of:</p>
<table>          
<%
 ArrayList memberApartOf = (ArrayList) request.getAttribute("memberOf");
 
if(memberApartOf!=null){
 Iterator iterate = memberApartOf.iterator();
while (iterate.hasNext()) {

      FamilyGroup groupWithFamily = (FamilyGroup)iterate.next();
%>
<a href="list-members?list=<%=groupWithFamily.getGroupId()%>"><img src=image/<%=groupWithFamily.getGroupId()%>.png  height="100" width="100" hspace="10" Vspace="5"> 
<p><%=groupWithFamily.getGroupName() %></p>
<%
} }
 %>
 </table>
 
  <table>          
<%
 ArrayList notApartOf = (ArrayList) request.getAttribute("memberOf");
 if(notApartOf.size()==0){
 out.println("<p>" + "I'm not a apart of any groups yet" + "</p>");
 
 }
 %>
  </table>       
          
         </div>
    </div>
</div>



<%@ include file="bottom.inc" %>


