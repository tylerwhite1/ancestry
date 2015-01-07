package ancestrysite.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO extends DataAccessObject {

	private static UserDAO instance = new UserDAO();

	public static UserDAO getInstance() {
		return instance;
	}

	private User read(ResultSet rs) throws SQLException {
		Long id = new Long(rs.getLong("userId"));
		String password = rs.getString("password");
		String firstName = rs.getString("firstName");
		String lastName = rs.getString("lastName");
		String email = rs.getString("email");
		String gender = rs.getString("gender");
		String birthDate = rs.getString("birthDate");
		String activation = rs.getString("activation");
		Long fatherId = new Long(rs.getLong("fatherId"));
		Long motherId = new Long(rs.getLong("motherId"));

		User user = new User();
		user.setUserId(id);
		user.setFirstName(firstName);
		user.setPassword(password);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setGender(gender);
		user.setBirthDate(birthDate);
		user.setFatherId(fatherId);
		user.setMotherId(motherId);
		user.setActivation(activation);
		return user;
	}

	public User find(String email) {
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "select * from user where email=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, email);
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

	public ArrayList<User> checkForSameEmail(String email) {

		ArrayList<User> users = new ArrayList<User>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;

		try {
			connection = getConnection();
			String sql = "select * from user where email=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			rs = statement.executeQuery();
			while (rs.next()) {
				users.add(read(rs));
			}
			return users;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(rs, statement, connection);
		}
	}
	
	public void createStudent(User user) {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			String sql = "insert into user"
					+ " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setLong(1, user.getUserId());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
			statement.setString(5, user.getEmail());
			statement.setString(6, user.getGender());
			statement.setString(7, user.getBirthDate());
			statement.setString(8, user.getActivation());
			statement.setLong(9, user.getFatherId());
			statement.setLong(10, user.getMotherId());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(statement, connection);
		}
	}

	public ArrayList<User> userList() {

		ArrayList<User> users = new ArrayList<User>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;

		try {
			connection = getConnection();
			String sql = "select * from user";
			statement = connection.prepareStatement(sql);
			rs = statement.executeQuery();
			while (rs.next()) {
				users.add(read(rs));
			}
			return users;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(rs, statement, connection);
		}
	}
	
	public User findUserById(Long userId)
	   {
	      ResultSet rs = null;
	      PreparedStatement statement = null;
	      Connection connection = null;
	      try
	      {
	         connection = getConnection();
	         String sql = "select * from user where userId=?";
	         statement = connection.prepareStatement(sql);
	         statement.setLong(1, userId.longValue());
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
	
	 public void update(User user)
	   {
	      PreparedStatement statement = null;
	      
	      Connection connection = null;
	      try
	      {
	         connection = getConnection();
	         String sql = "update user set " + "password=?, firstName=?, lastName=?, email=?, gender=?, birthDate=?, activation=?, fatherId=?, motherId=? where userId=?";
	         statement = connection.prepareStatement(sql);
	         statement.setString(1, user.getPassword());
	         statement.setString(2, user.getFirstName());
	         statement.setString(3, user.getLastName());
	         statement.setString(4, user.getEmail());
	         statement.setString(5, user.getGender());
	         statement.setString(6, user.getBirthDate());
	         statement.setString(7, user.getActivation());
	         statement.setLong(8, user.getFatherId());
	         statement.setLong(9, user.getMotherId());
	         statement.setLong(10, user.getUserId());
	         statement.executeUpdate();
	      } catch (SQLException e)
	      {
	         throw new RuntimeException(e);
	      } finally
	      {
	         close(statement, connection);
	      }
	   }
	 
	 public ArrayList<User> getRandomUsers() {

			ArrayList<User> users = new ArrayList<User>();
			ResultSet rs = null;
			PreparedStatement statement = null;
			Connection connection = null;

			try {
				connection = getConnection();
				String sql = "SELECT * FROM user where activation = 'unlock' ORDER BY RAND() LIMIT 5";
				statement = connection.prepareStatement(sql);
				rs = statement.executeQuery();
				while (rs.next()) {
					users.add(read(rs));
				}
				return users;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				close(rs, statement, connection);
			}
		}
}
