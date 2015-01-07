package ancestrysite.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FamilyGroupDAO extends DataAccessObject {

	private static FamilyGroupDAO instance = new FamilyGroupDAO();

	public static FamilyGroupDAO getInstance() {
		return instance;
	}

	
	private FamilyGroup read(ResultSet rs) throws SQLException {
		Long id = new Long(rs.getLong("groupId"));
		Long creatorId = new Long(rs.getLong("createById"));
		String name= rs.getString("groupName");

		FamilyGroup family = new FamilyGroup();
		family.setCreateById(creatorId);
		family.setGroupName(name);
		family.setGroupId(id);
		
		return family;
	}
	
	public void createFamilyGroup(FamilyGroup group) {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "insert into familygroup"
					+ " values(?, ?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setLong(1, group.getGroupId());
			statement.setString(2, group.getGroupName());
			statement.setLong(3, group.getCreateById());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(statement, connection);
		}
	}
	
	public ArrayList<FamilyGroup> groupList() {

		ArrayList<FamilyGroup> familygroup = new ArrayList<FamilyGroup>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;

		try {
			connection = getConnection();
			String sql = "select * from familygroup";
			statement = connection.prepareStatement(sql);
			rs = statement.executeQuery();
			while (rs.next()) {
				familygroup.add(read(rs));
			}
			return familygroup;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(rs, statement, connection);
		}
	}
	
	public FamilyGroup findGroup(String id) {
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "select * from familygroup where groupId=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			rs = statement.executeQuery();
			if (!rs.next()) {
				return null;
			}
			return read(rs);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(rs, statement, connection);
		}
	}
	
	 public void update(FamilyGroup group)
	   {
	      PreparedStatement statement = null;
	      
	      Connection connection = null;
	      try
	      {
	         connection = getConnection();
	         String sql = "update familygroup set " + "groupName=? where groupId=?";
	         statement = connection.prepareStatement(sql);
	         statement.setString(1, group.getGroupName());
	         statement.setLong(2, group.getGroupId());
	         statement.executeUpdate();
	      } catch (SQLException e)
	      {
	         throw new RuntimeException(e);
	      } finally
	      {
	         close(statement, connection);
	      }
	   }
	
	public void deleteGroup(Long Id)
	   {
		 PreparedStatement statement = null;
	      
	      Connection connection = null;
	      try
	      {
	        	 connection = getConnection();
	        	 String sql = "DELETE FROM familygroup WHERE familygroup.groupId="+Id.toString();
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
}
