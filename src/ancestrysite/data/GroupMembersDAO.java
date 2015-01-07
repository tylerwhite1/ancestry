package ancestrysite.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GroupMembersDAO extends DataAccessObject {

	private static GroupMembersDAO instance = new GroupMembersDAO();

	public static GroupMembersDAO getInstance() {
		return instance;
	}

	
	private GroupMembers read(ResultSet rs) throws SQLException {
		Long id = new Long(rs.getLong("userId"));
		Long groupId = new Long(rs.getLong("familyGroupId"));

		GroupMembers group = new GroupMembers();
		group.setGroupId(groupId);
		group.setUsersId(id);
		
		return group;
	}
	
	public void createMembers(GroupMembers group) {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "insert into groupmembers"
					+ " values(?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setLong(1, group.getUsersId());
			statement.setLong(2, group.getGroupId());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(statement, connection);
		}
	}
	
	public ArrayList<GroupMembers> getAllUsersGroup(String userId) {

		ArrayList<GroupMembers> members = new ArrayList<GroupMembers>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;

		try {
			connection = getConnection();
			String sql = "select * from groupmembers where userId=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, userId);
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
	
	public ArrayList<GroupMembers> getGroupsMembership(String groupId) {

		ArrayList<GroupMembers> members = new ArrayList<GroupMembers>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;

		try {
			connection = getConnection();
			String sql = "select * from groupmembers where familyGroupId=?";
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
	
	public void deleteGroupMembership(Long familyGroupId, Long userId)
	   {
		 PreparedStatement statement = null;
	      
	      Connection connection = null;
	      try
	      {
	        	 connection = getConnection();
	        	 String sql = "DELETE FROM groupmembers WHERE groupmembers.userId="+userId.toString()+" AND groupmembers.familyGroupId="+familyGroupId.toString();
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
	
	public void deleteAllMembership(Long familyGroupId)
	   {
		 PreparedStatement statement = null;
	      
	      Connection connection = null;
	      try
	      {
	        	 connection = getConnection();
	        	 String sql = "DELETE FROM groupmembers WHERE groupmembers.familyGroupId="+familyGroupId.toString();
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
	
	public GroupMembers findMembership(Long familyGroupId, Long userId)
	   {
		  ResultSet rs = null;
	      PreparedStatement statement = null;
	      Connection connection = null;
	      try
	      {
	        	 connection = getConnection();
	        	 String sql = "select * from groupmembers WHERE groupmembers.userId=? AND groupmembers.familyGroupId=?";
	        	 statement = connection.prepareStatement(sql);
		         statement.setLong(1, userId.longValue());
		         statement.setLong(2, familyGroupId.longValue());
		         rs = statement.executeQuery();
		         if (!rs.next())
		         {
		            return null;
		         }
		         return read(rs);
		      }
		      catch (SQLException e)
		      {
		         throw new RuntimeException(e);
		      }
		      finally
		      {
		         close(rs, statement, connection);
		      }
	   }
}
