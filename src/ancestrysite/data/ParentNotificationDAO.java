package ancestrysite.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ParentNotificationDAO extends DataAccessObject {

	private static ParentNotificationDAO instance = new ParentNotificationDAO();

	public static ParentNotificationDAO getInstance() {
		return instance;
	}

	private ParentNotification read(ResultSet rs) throws SQLException {
		
		Long notificationId = new Long(rs.getLong("notificationId"));
		Long userId = new Long(rs.getLong("userId"));
		String parentFirstName = rs.getString("parentFirstName");
		String parentLastName = rs.getString("parentLastName");
		
		ParentNotification notification = new ParentNotification();
		notification.setNotificationId(notificationId);
		notification.setUserId(userId);
		notification.setParentFirstName(parentFirstName);
		notification.setParentLastName(parentLastName);
		return notification;
	}

	public void createParentNotification(ParentNotification notification) {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "insert into parentnotification"
					+ " values(?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setLong(1, notification.getNotificationId());
			statement.setLong(2, notification.getUserId());
			statement.setString(3, notification.getParentFirstName());
			statement.setString(4, notification.getParentLastName());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(statement, connection);
		}
	}
	
	public ArrayList<ParentNotification> getAllMembersParentNotification(Long userId) {

		ArrayList<ParentNotification> notification = new ArrayList<ParentNotification>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;

		try {
			connection = getConnection();
			String sql = "select * from parentnotification where userId=?";
			statement = connection.prepareStatement(sql);
			statement.setLong(1, userId.longValue());
			rs = statement.executeQuery();
			while (rs.next()) {
				notification.add(read(rs));
			}
			return notification;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(rs, statement, connection);
		}
	}
	
	public void deleteNotification(Long notificationId)
	   {
		 PreparedStatement statement = null;
	      
	      Connection connection = null;
	      try
	      {
	        	 connection = getConnection();
	        	 String sql = "DELETE FROM parentnotification WHERE parentnotification.notificationId="+notificationId.toString();
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

	public ParentNotification findNotificationByInfo(Long userId,String firstName, String lastName)
	   {
		ResultSet rs = null;
	      PreparedStatement statement = null;
	      Connection connection = null;
	      try
	      {
	        	 connection = getConnection();
	        	 String sql = "select * from parentnotification WHERE parentnotification.userId=? AND parentnotification.parentFirstName=? AND parentnotification.parentLastName=?";
	        	 statement = connection.prepareStatement(sql);
		         statement.setLong(1, userId.longValue());
		         statement.setString(2, firstName);
		         statement.setString(3, lastName);
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
