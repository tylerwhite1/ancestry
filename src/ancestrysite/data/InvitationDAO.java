package ancestrysite.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InvitationDAO extends DataAccessObject {

	private static InvitationDAO instance = new InvitationDAO();

	public static InvitationDAO getInstance() {
		return instance;
	}

	private Invitation read(ResultSet rs) throws SQLException {
		Long id = new Long(rs.getLong("userId"));
		Long groupid = new Long(rs.getLong("groupId"));
		String accept = rs.getString("accept");
		Long inviteid = new Long(rs.getLong("inviteId"));
		
		Invitation invitation = new Invitation();
		invitation.setUserId(id);
		invitation.setGroupId(groupid);
		invitation.setAccept(accept);
		invitation.setInviteId(inviteid);
		return invitation;
	}

	public void createInvitationt(Invitation invitation) {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "insert into invitation"
					+ " values(?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setLong(1, invitation.getUserId());
			statement.setLong(2, invitation.getGroupId());
			statement.setString(3, invitation.getAccept());
			statement.setLong(4, invitation.getInviteId());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(statement, connection);
		}
	}
	
	public ArrayList<Invitation> getAllMembersInvitation(Long userId) {

		ArrayList<Invitation> invitation = new ArrayList<Invitation>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;

		try {
			connection = getConnection();
			String sql = "select * from invitation where userId=?";
			statement = connection.prepareStatement(sql);
			statement.setLong(1, userId.longValue());
			rs = statement.executeQuery();
			while (rs.next()) {
				invitation.add(read(rs));
			}
			return invitation;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(rs, statement, connection);
		}
	}
	
	public void deleteInvitation(Long userId, Long groupId)
	   {
		 PreparedStatement statement = null;
	      
	      Connection connection = null;
	      try
	      {
	        	 connection = getConnection();
	        	 String sql = "DELETE FROM invitation WHERE invitation.userId="+userId.toString()+" AND invitation.groupId="+groupId.toString();
	        	 statement = connection.prepareStatement(sql);
	        	 statement.executeUpdate(sql);
	      }
	      catch (SQLException e)
	      {
	         throw new RuntimeException(e);
	      }
	      finally
	      {
	         close(statement, connection);
	      }
	   }
	
	public ArrayList<Invitation> getGroupsMembership(String groupId) {

		ArrayList<Invitation> members = new ArrayList<Invitation>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;

		try {
			connection = getConnection();
			String sql = "select * from invitation where groupId=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, groupId);
			rs = statement.executeQuery();
			while (rs.next()) {
				members.add(read(rs));
			}
			return members;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(rs, statement, connection);
		}
	}
	
	
}
