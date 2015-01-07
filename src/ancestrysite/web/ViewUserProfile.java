package ancestrysite.web;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ancestrysite.data.FamilyGroup;
import ancestrysite.data.FamilyGroupDAO;
import ancestrysite.data.GroupMembers;
import ancestrysite.data.GroupMembersDAO;
import ancestrysite.data.User;
import ancestrysite.data.UserDAO;

public class ViewUserProfile extends HttpServlet {

	private RequestDispatcher viewProfile;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = config.getServletContext();
		viewProfile = context
				.getRequestDispatcher("/WEB-INF/jsp/view-user.jsp");

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String profileOfAUser = req.getParameter("value");
		User user = new UserDAO().findUserById(Long.valueOf(profileOfAUser));

		ArrayList<GroupMembers> members = new GroupMembersDAO()
				.getAllUsersGroup(String.valueOf(user.getUserId()));
		ArrayList<FamilyGroup> allgroups = new FamilyGroupDAO().groupList();
		ArrayList<FamilyGroup> group = new ArrayList<FamilyGroup>();

		for (GroupMembers current : members) {

			for (FamilyGroup curGroup : allgroups) {
				if (current.getGroupId().equals(curGroup.getGroupId())) {
					group.add(curGroup);
				}
			}
		}
		
		req.setAttribute("memberOf", group);
		req.setAttribute("user", user);
		req.setAttribute("fatherTree",
				findsRelativesConnectionThroughParent(user.getFatherId()));
		req.setAttribute("motherTree",
				findsRelativesConnectionThroughParent(user.getMotherId()));
		req.setAttribute("immediateTree",findImmediateFamily(user.getFatherId(),user.getMotherId(), user));
		req.setAttribute("groupOf", group);
		req.setAttribute("familyMembers",getFamilyMembersThatAreFriends(user.getFatherId(),user.getMotherId(),user));
		viewProfile.forward(req, resp);
	}

	public Hashtable<User, String> getFamilyMembersThatAreFriends(Long fatherId, Long motherId, User user) {

		Hashtable<User, String> relations = new Hashtable<User, String>();
		// ArrayList<User> members = new UserDAO().userList();
		URL main = ProfileServlet.class.getResource("ProfileServlet.class");
		File path = new File(main.getPath());
		String result = path.getPath().split("WEB")[0];
		result = result + "image\\";
		File dir = new File(result);

		for (User key : addToFriendsList(fatherId,motherId,user)) {
			for (File imgFile : dir.listFiles()) {

				String userImage = Long.toString(key.getUserId()) + ".png";
				if (userImage.equals(imgFile.getName())) {
					relations.put(key, "image/" + imgFile.getName());
				}
			}
		}
		return relations;
	}

	public Hashtable<User, String> findsRelativesConnectionThroughParent(
			Long parent) {

		Hashtable<User, Integer> relations = new Hashtable<User, Integer>();
		Hashtable<User, String> relatives = new Hashtable<User, String>();
		ArrayList<User> members = new UserDAO().userList();
		User parentUser = new UserDAO().findUserById(parent);
		if (parentUser != null) {
			for (User current : members) {
				if (parentUser.getUserId().equals(current.getUserId())) {
					//relations.put(current, 1);
					addUnclesAndAuntsToTree(relatives, current, relations, 2);
					addGrandparentsToTree(relatives, parentUser, relations, 0);
					findFirstCousins(relatives, relations, 3, 2);
					break;
				}
			}
		}
		return relatives;
	}

	public ArrayList<User> addToFriendsList(Long fatherId, Long motherId, User userLogin) {

		Hashtable<User, String> fatherSide = findsRelativesConnectionThroughParent(fatherId);
		Hashtable<User,String> motherSide = findsRelativesConnectionThroughParent(motherId);
		Hashtable<User, String> immediate = findImmediateFamily(userLogin.getFatherId(),userLogin.getMotherId(), userLogin);
		ArrayList<User> friendsList = new ArrayList<User>();
		for (User key : fatherSide.keySet()) {
			friendsList.add(key);
		}
		for (User key : motherSide.keySet()) {
			friendsList.add(key);
		}
		for (User key : immediate.keySet()) {
			friendsList.add(key);
		}
		return friendsList;
	}

	public void findFirstCousins(Hashtable<User, String> relatives, Hashtable<User, Integer> relations, int value,
			int num) {

		ArrayList<User> members = new UserDAO().userList();
		//ArrayList<User> cousins = new ArrayList<User>();
		Set set = relations.entrySet();
		Iterator iter = set.iterator();

		while (iter.hasNext()) {

			Entry<User, Integer> elem = (Entry) iter.next();
			if (elem.getValue().equals(num)) {

				for (User current : members) {
					if (current.getFatherId().equals(elem.getKey().getUserId())) {
						relatives.put(current, current.getFirstName()+" "+current.getLastName()+" (Cousin)"+"  - (Father is Uncle: "+elem.getKey().getFirstName()+" "+elem.getKey().getLastName()+")");
					}
					
					if (current.getMotherId().equals(elem.getKey().getUserId())) {
						relatives.put(current, current.getFirstName()+" "+current.getLastName()+" (Cousin)"+"  - (Mother is Auntie: "+elem.getKey().getFirstName()+" "+elem.getKey().getLastName()+")");	
					}
				}
			}
		}
	}
	
	public void findNiecesAndNephrews(Hashtable<User, Integer> niecesAndNephrews , Hashtable<User, String> relations, int value,
			int num) {

		ArrayList<User> members = new UserDAO().userList();
		Set set = niecesAndNephrews.entrySet();
		Iterator iter = set.iterator();

		while (iter.hasNext()) {

			Entry<User, Integer> elem = (Entry) iter.next();
			if (elem.getValue().equals(num)) {

				for (User current : members) {
					if (current.getFatherId().equals(elem.getKey().getUserId()) && current.getGender().equals("Male")) {
						relations.put(current, current.getFirstName()+" "+current.getLastName()+" (Nephew)"+"  -(Father is: "+elem.getKey().getFirstName()+" "+elem.getKey().getLastName()+")");
					}
					if (current.getFatherId().equals(elem.getKey().getUserId()) && current.getGender().equals("Female")) {
						relations.put(current, current.getFirstName()+" "+current.getLastName()+" (Niece)"+"  -(Father is: "+elem.getKey().getFirstName()+" "+elem.getKey().getLastName()+")");
					}
					if (current.getMotherId().equals(elem.getKey().getUserId()) && current.getGender().equals("Male")) {
						relations.put(current, current.getFirstName()+" "+current.getLastName()+" (Nephew)"+"  -(Mother is: "+elem.getKey().getFirstName()+" "+elem.getKey().getLastName()+")");
					}
					if (current.getMotherId().equals(elem.getKey().getUserId()) && current.getGender().equals("Female")) {
						relations.put(current, current.getFirstName()+" "+current.getLastName()+" (Niece)"+"  -(Mother is: "+elem.getKey().getFirstName()+" "+elem.getKey().getLastName()+")");
					}
				}
			}

		}
	}
	
	public void findChild(Hashtable<User, String> relations, Hashtable<User, Integer> kids, User user) {

		ArrayList<User> members = new UserDAO().userList();
		for(User member: members){
			if(member.getFatherId().equals(user.getUserId()) && member.getGender().equals("Male")){
				relations.put(member, member.getFirstName()+" "+member.getLastName()+" (Son)");
				kids.put(member, 9);
			}
			if(member.getFatherId().equals(user.getUserId()) && member.getGender().equals("Female")){
				relations.put(member, member.getFirstName()+" "+member.getLastName()+" (Daughter)");
				kids.put(member, 9);
			}
			if(member.getMotherId().equals(user.getUserId()) && member.getGender().equals("Male")){
				relations.put(member, member.getFirstName()+" "+member.getLastName()+" (Son)");
				kids.put(member, 9);
			}
			if(member.getMotherId().equals(user.getUserId()) && member.getGender().equals("Female")){
				relations.put(member, member.getFirstName()+" "+member.getLastName()+" (Daughter)");
				kids.put(member, 9);
			}
		}
		
	}


	public void addUnclesAndAuntsToTree(Hashtable<User, String> relatives, User parent,
			Hashtable<User, Integer> relations, int value) {

		ArrayList<User> members = new UserDAO().userList();

		for (User current : members) {

			if (current.getFatherId() != 0) {
				if (parent.getFatherId().equals(current.getFatherId())
						&& !current.getUserId().equals(parent.getUserId()) && current.getGender().equals("Female")) {
					relations.put(current, value);
					relatives.put(current, current.getFirstName()+" "+current.getLastName()+" (Auntie)");
				}
				if (parent.getFatherId().equals(current.getFatherId())
						&& !current.getUserId().equals(parent.getUserId()) && current.getGender().equals("Male")) {
					relations.put(current, value);
					relatives.put(current, current.getFirstName()+" "+current.getLastName()+" (Uncle)");
				}
			}
			if (current.getMotherId() != 0) {
				if (parent.getMotherId().equals(current.getMotherId())
						&& !current.getUserId().equals(parent.getUserId()) && current.getGender().equals("Male")) {
					relations.put(current, value);
					relatives.put(current, current.getFirstName()+" "+current.getLastName()+" (Uncle)");
				}
				if (parent.getMotherId().equals(current.getMotherId())
						&& !current.getUserId().equals(parent.getUserId()) && current.getGender().equals("Female")) {
					relations.put(current, value);
					relatives.put(current, current.getFirstName()+" "+current.getLastName()+" (Auntie)");
				}
			}
		}

	}

	public void addGrandparentsToTree(Hashtable<User, String> relatives,User parent,
			Hashtable<User, Integer> relations, int value) {

		ArrayList<User> members = new UserDAO().userList();

		for (User current : members) {

			if (parent.getFatherId().equals(current.getUserId())&& current.getGender().equals("Male")){
				relations.put(current, value);
				relatives.put(current, current.getFirstName()+" "+current.getLastName()+" (Grandfather)");
			}
			if(parent.getMotherId().equals(current.getUserId())&& current.getGender().equals("Female")){
				relations.put(current, value);
				relatives.put(current, current.getFirstName()+" "+current.getLastName()+" (Grandmother)");
			}
		}

	}
	
	public void findGrandChild(Hashtable<User, Integer> grandKid, Hashtable<User, String> immediate, User userLogin){
		
		ArrayList<User> members = new UserDAO().userList();
		
		Set set = grandKid.entrySet();
		Iterator iter = set.iterator();

		while (iter.hasNext()) {

			Entry<User, Integer> elem = (Entry) iter.next();
			if (elem.getValue().equals(9)) {

				for (User current : members) {
			
					if(current.getFatherId().equals(elem.getKey().getUserId()) && current.getGender().equals("Male")){
				
						immediate.put(current, current.getFirstName()+" "+current.getLastName()+" (Grandson) - Father is: "+elem.getKey().getFirstName()+" "+elem.getKey().getLastName());
					}
					if(current.getFatherId().equals(elem.getKey().getUserId()) && current.getGender().equals("Female")){
						immediate.put(current, current.getFirstName()+" "+current.getLastName()+" (Grand daughter) - Father is: "+elem.getKey().getFirstName()+" "+elem.getKey().getLastName());		
					}
					if(current.getMotherId().equals(elem.getKey().getUserId()) && current.getGender().equals("Male")){
				
						immediate.put(current, current.getFirstName()+" "+current.getLastName()+" (Grandson) - Mother is: "+elem.getKey().getFirstName()+" "+elem.getKey().getLastName());
					}		
					if(current.getMotherId().equals(elem.getKey().getUserId()) && current.getGender().equals("Female")){
				
						immediate.put(current, current.getFirstName()+" "+current.getLastName()+" (Grandson) - Mother is: "+elem.getKey().getFirstName()+" "+elem.getKey().getLastName());
					}		
			
				}
			}
		}
	}
	
	
public Hashtable<User, String> findImmediateFamily(Long fatherId, Long motherId, User userLogin){
		
		Hashtable<User, String> immediate = new Hashtable<User, String>();
		Hashtable<User, Integer> siblings = new Hashtable<User, Integer>();
		ArrayList<User> members = new UserDAO().userList();
		User father = new UserDAO().findUserById(fatherId);
		User mother = new UserDAO().findUserById(motherId);
		
		if (father != null) {
			for (User current : members) {
				if (father.getUserId().equals(current.getUserId())) {
					immediate.put(current , current.getFirstName()+" "+current.getLastName()+"  (Father)");
				}
				
				if (userLogin.getFatherId().equals(current.getFatherId())&& !userLogin.getUserId().equals(current.getUserId()) && current.getGender().equals("Male")) {
					immediate.put(current , current.getFirstName()+" "+current.getLastName()+"  (Brother)");
					siblings.put(current,4);
				}

				if (userLogin.getFatherId().equals(current.getFatherId())&& !userLogin.getUserId().equals(current.getUserId()) && current.getGender().equals("Female")) {
					immediate.put(current , current.getFirstName()+" "+current.getLastName()+"  (Sister)");
					siblings.put(current,4);
				}
			}
		}
		if (mother != null) {
			for (User current : members) {
				if (mother.getUserId().equals(current.getUserId())) {
					immediate.put(current , current.getFirstName()+" "+current.getLastName()+"  (Mother)");
				}
				
				if (userLogin.getMotherId().equals(current.getMotherId()) && !userLogin.getUserId().equals(current.getUserId()) && current.getGender().equals("Male")) {
					immediate.put(current ,  current.getFirstName()+" "+current.getLastName()+"  (Brother)");
					siblings.put(current,4);
				}
				
				if (userLogin.getMotherId().equals(current.getMotherId()) && !userLogin.getUserId().equals(current.getUserId()) && current.getGender().equals("Female")) {
					immediate.put(current ,  current.getFirstName()+" "+current.getLastName()+"  (Sister)");
					siblings.put(current,4);
				}
			}
		}
		findChild(immediate,siblings,userLogin);
		findNiecesAndNephrews(siblings,immediate,5,4);
		findGrandChild(siblings,immediate, userLogin);
		return immediate;
	}
}
