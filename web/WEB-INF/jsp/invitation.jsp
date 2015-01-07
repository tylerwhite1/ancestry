<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
<script>
$(document).ready(function(){
    $(':checkbox').bind('change', function() {
        var thisClass = $(this).attr('class');
        if ($(this).attr('checked')) {
            $(':checkbox.' + thisClass + ":not(#" + this.id + ")").removeAttr('checked');
        }
        else {
            $(this).attr('checked', 'checked');
        }
    });
});
</script>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import= "ancestrysite.data.User" %>
<%@ page import= "ancestrysite.data.FamilyGroup" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map.Entry" %>

<%@ include file="top.inc" %>

<%@ include file="middle.inc" %>
<h2>Group Request</h2>
<% 
	
  String requestValue = (String) session.getAttribute("requestNum");
   if (requestValue.equals("0")) {
      out.println("You have no group request at this time");
  }
%>
</br>
<table>
 <form method="post">
  <%
 java.util.Hashtable<User, FamilyGroup> table = (java.util.Hashtable)request.getAttribute("requestedGroups");
	int counter =0;
 Set acceptSet = table.entrySet();
     Iterator acceptIter = acceptSet.iterator();  
      while (acceptIter.hasNext()) {

     	Entry<User, FamilyGroup> elem = (Entry)acceptIter.next();
 %>

<ul>
 Invite from <a href="view-user?value=<%=elem.getKey().getUserId()%>"><%=elem.getKey().getFirstName()%>   <%=elem.getKey().getLastName() %></a>
 </br>Group name is: <%=elem.getValue().getGroupName()%>
<a href="group-request?requestvalue=<%=elem.getValue().getGroupId()%>">Accept Or Decline Request</a> 
</ul>

 <%
 }
 %>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" name="cancel-button" value="Cancel" />
 </form>
</table>
<%@ include file="bottom.inc" %>