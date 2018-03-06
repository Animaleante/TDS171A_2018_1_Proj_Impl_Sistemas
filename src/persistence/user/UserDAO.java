package persistence.user;

import static persistence.Persistence.DELETE_DATA;
import static persistence.Persistence.DELETE_ERROR;
import static persistence.Persistence.DELETE_NOT_SUCCESS;
import static persistence.Persistence.DELETE_SUCCESS;
import static persistence.Persistence.FLAG;
import static persistence.Persistence.LIST_DATA;
import static persistence.Persistence.QUERY_ERROR;
import static persistence.Persistence.QUERY_NOT_SUCCESS;
import static persistence.Persistence.QUERY_SUCCESS;
import static persistence.Persistence.RECOVERY_NOT_SUCCESS;
import static persistence.Persistence.RECOVERY_SUCCESS;
import static persistence.Persistence.SAVE_DATA;
import static persistence.Persistence.SAVE_ERROR;
import static persistence.Persistence.SAVE_NOT_SUCCESS;
import static persistence.Persistence.SAVE_SUCCESS;
import static persistence.Persistence.UPDATE_DATA;
import static persistence.Persistence.UPDATE_ERROR;
import static persistence.Persistence.UPDATE_NOT_SUCCESS;
import static persistence.Persistence.UPDATE_SUCCESS;
import static persistence.Persistence.V_TABLE_USER;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import persistence.user.permission.UserPermissionPersistence;
import vo.permission.Permission;
import vo.user.UserConstraints;
import vo.user.User;

/**
 * Class to implement the operations against to the database to User entity.
 * 
 * @author Baracho
 * 
 * @since January 09, 2017
 * 
 * @version 1.0
 *
 */
class UserDAO implements IUserDAO {

	/**
	 * Instance variables
	 */

	private Connection connection;

	private UserPermissionPersistence userPermissionPersistence;

	/**
	 * Default constructor
	 */
	public UserDAO() {
		// TODO Auto-generated constructor stub

		this.userPermissionPersistence = new UserPermissionPersistence();
	}

	/**
	 * Access methods
	 */

	/**
	 * @param connection
	 *            the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Class operations
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.IUserDAO#save(vo.user.User)
	 */
	@Override
	public User save(User user) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		UserConstraints userConstraints = null;

		Integer counter = null;

		int i = 1;

		// Data processing

		if (user != null) {

			userConstraints = this.userConstraints();

			if (userConstraints != null) {

				user.setUserCode(userConstraints.getId_tb_user());

				try {

					query = "INSERT INTO ";

					query += V_TABLE_USER;

					query += " U (";

					query += "U.ID_TB_USER, ";

					query += "U.LOGIN, ";

					query += "U.PASSWORD";

					query += " ) VALUES (?, ?, ?)";

					preparedStatement = this.connection.prepareStatement(query);

					preparedStatement.setInt(i++, user.getUserCode());

					preparedStatement.setString(i++, user.getLogin());

					preparedStatement.setString(i++, user.getPassword());

					counter = new Integer(preparedStatement.executeUpdate());

					if (counter != null && counter.equals(1)) {

						msg = SAVE_SUCCESS;

						if (user.getPermissions() != null && user.getPermissions().size() > 0) {

							this.userPermissionPersistence.save(user);
						}

						userConstraints = this.search();

						user = search(userConstraints);

						if (user != null) {

							msg += RECOVERY_SUCCESS;

						} else {

							msg += RECOVERY_NOT_SUCCESS;
						}

					} else {

						user = null;

						msg = SAVE_NOT_SUCCESS;
					}

				} catch (SQLException e) {

					user = null;

					msg = SAVE_ERROR + e.getMessage();

					e.printStackTrace();

				} finally {

					if (preparedStatement != null) {

						try {

							preparedStatement.close();

						} catch (SQLException e2) {
							// TODO: handle exception

							e2.printStackTrace();
						}
					}

					if (FLAG.equals(true) && msg != null) {

						System.err.println(
								String.format("\n %s%s: %s", this.getClass().getSimpleName(), ".save(User user)", msg));

						if (user != null) {

							System.err.println(String.format(String.format("\n %s %s", SAVE_DATA, user.toString())));
						}
					}
				}
			}
		}

		// Information output

		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.IUserDAO#list()
	 */
	@Override
	public List<User> list() {
		// TODO Auto-generated method stub

		// Variables declaration

		UserConstraints userConstraints = null;

		List<User> users = null;

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		String msg = null;

		User user = null;

		// Data processing

		try {

			query = "SELECT U.ID_TB_USER FROM ";

			query += V_TABLE_USER;

			query += " U ";

			query += "ORDER BY U.ID_TB_USER ASC";

			statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(query);

			if (resultSet != null && resultSet.last()) {

				resultSet.beforeFirst();

				users = new ArrayList<User>();

				while (resultSet.next()) {

					userConstraints = new UserConstraints();

					userConstraints.setId_tb_user(new Integer(resultSet.getInt("ID_TB_USER")));

					user = this.search(userConstraints);

					if (user != null) {

						users.add(user);
					}
				}

				msg = QUERY_SUCCESS;

			} else {

				msg = QUERY_NOT_SUCCESS;
			}

		} catch (Exception e) {
			// TODO: handle exception

			users = null;

			msg = QUERY_ERROR + e.getMessage();

			e.printStackTrace();

		} finally {

			if (statement != null) {

				try {

					statement.close();

				} catch (SQLException e2) {
					// TODO: handle exception

					e2.printStackTrace();
				}
			}

			if (FLAG.equals(true) && msg != null) {

				System.err.println(String.format("\n %s%s: %s", this.getClass().getSimpleName(), ".list()", msg));

				if (users != null && users.size() > 0) {

					System.err.println(String.format("\n %s:", LIST_DATA));

					for (User u : users) {

						System.out.println(u.toString());
					}
				}
			}
		}

		// Information output

		return users;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.IUserDAO#update(vo.user.User)
	 */
	@Override
	public User update(User user) {
		// TODO Auto-generated method stub

		// Variables declaration

		UserConstraints userConstraints = null;

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		int i = 1;

		// Data processing

		try {

			query = "UPDATE ";

			query += V_TABLE_USER;

			query += " U ";

			query += "SET ";

			query += "U.LOGIN = ?, ";

			query += "U.PASSWORD = ? ";

			query += " WHERE U.ID_TB_USER = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(i++, user.getLogin());

			preparedStatement.setString(i++, user.getPassword());

			preparedStatement.setInt(i++, user.getUserCode().intValue());

			counter = new Integer(preparedStatement.executeUpdate());

			if (counter != null && counter.equals(1)) {

				msg = UPDATE_SUCCESS;

				userConstraints = new UserConstraints();

				userConstraints.setId_tb_user(user.getUserCode());

				user = this.search(userConstraints);

				if (user != null) {

					msg += RECOVERY_SUCCESS;

				} else {

					msg += RECOVERY_NOT_SUCCESS;
				}

			} else {

				user = null;

				msg = UPDATE_NOT_SUCCESS;
			}

		} catch (SQLException e) {
			// TODO: handle exception

			user = null;

			msg = UPDATE_ERROR + e.getMessage();

			e.printStackTrace();

		} finally {

			if (preparedStatement != null) {

				try {

					preparedStatement.close();

				} catch (SQLException e2) {
					// TODO: handle exception

					e2.printStackTrace();
				}
			}

			if (FLAG.equals(true) && msg != null) {

				System.err.println(
						String.format("\n %s%s: %s", this.getClass().getSimpleName(), ".update(User user)", msg));

				if (user != null) {

					System.err.println(String.format("\n %s %s", UPDATE_DATA, user.toString()));
				}
			}
		}

		// Information output

		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.IUserDAO#delete(vo.user.User)
	 */
	@Override
	public Boolean delete(User user) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Boolean flag = null;

		Integer counter = null;

		int i = 1;

		// Data processing

		if (user != null) {

			try {

				query = "DELETE FROM ";

				query += V_TABLE_USER;

				query += " U ";

				query += "WHERE ";

				query += "U.ID_TB_USER = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, user.getUserCode().intValue());

				counter = new Integer(preparedStatement.executeUpdate());

				if (counter != null && counter.equals(1)) {

					flag = new Boolean(true);

					msg = DELETE_SUCCESS;

				} else {

					flag = new Boolean(false);

					msg = DELETE_NOT_SUCCESS;
				}

			} catch (SQLException e) {
				// TODO: handle exception

				flag = null;

				msg = DELETE_ERROR + e.getMessage();

				e.printStackTrace();

			} finally {

				if (preparedStatement != null) {

					try {

						preparedStatement.close();

					} catch (SQLException e2) {
						// TODO: handle exception

						e2.printStackTrace();
					}
				}

				if (FLAG.equals(true) && msg != null) {

					System.err.println(
							String.format("\n %s%s: %s", this.getClass().getSimpleName(), ".delete(User user)", msg));

					if (user != null) {

						System.err.println(String.format("\n %s %s", DELETE_DATA, user.toString()));
					}
				}
			}
		}

		// Information output

		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.IUserDAO#search()
	 */
	@Override
	public UserConstraints search() {
		// TODO Auto-generated method stub

		// Variables declaration

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		UserConstraints userConstraints = null;

		// Data processing

		try {

			query = "SELECT U.ID_TB_USER FROM ";

			query += V_TABLE_USER;

			query += " U ";

			query += "WHERE ";

			query += "U.ID_TB_USER = ";

			query += "(SELECT MAX(U.ID_TB_USER) FROM ";

			query += V_TABLE_USER;

			query += " U)";

			statement = this.connection.createStatement();

			resultSet = statement.executeQuery(query);

			userConstraints = new UserConstraints();

			if (resultSet != null && resultSet.next()) {

				userConstraints.setId_tb_user(new Integer(resultSet.getInt("ID_TB_USER")));

			} else {

				userConstraints.setId_tb_user(new Integer(0));
			}

		} catch (SQLException e) {
			// TODO: handle exception

			userConstraints = null;

			e.printStackTrace();

		} finally {

			if (statement != null) {

				try {

					statement.close();

				} catch (SQLException e2) {
					// TODO: handle exception

					e2.printStackTrace();
				}
			}
		}

		// Information output

		return userConstraints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.IUserDAO#search(vo.user.UserConstraints)
	 */
	@Override
	public User search(UserConstraints userConstraints) {
		// TODO Auto-generated method stub

		// Variables declaration

		User user = null;

		Set<Permission> permissions = null;

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		int i = 1;

		// Data processing

		if (userConstraints != null) {

			try {

				query = "SELECT * FROM ";

				query += V_TABLE_USER;

				query += " U ";

				query += "WHERE ";

				query += "U.ID_TB_USER = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, userConstraints.getId_tb_user().intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.next()) {

					user = new User();

					user.setUserCode(new Integer(resultSet.getInt("ID_TB_USER")));

					user.setLogin(resultSet.getString("LOGIN"));

					user.setPassword(resultSet.getString("PASSWORD"));

					permissions = this.userPermissionPersistence.search(user);

					if (permissions != null && permissions.size() > 0) {

						user.setPermissions(permissions);
					}
				}

			} catch (SQLException e) {
				// TODO: handle exception

				user = null;

				e.printStackTrace();

			} finally {

				if (preparedStatement != null) {

					try {

						preparedStatement.close();

					} catch (SQLException e2) {
						// TODO: handle exception

						e2.printStackTrace();
					}
				}

				if (resultSet != null) {

					try {

						resultSet.close();

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		// Information output

		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.IUserDAO#constraintsUser()
	 */
	public UserConstraints userConstraints() {

		// Variable declaration

		UserConstraints userConstraints = null;

		// Data processing

		userConstraints = this.search();

		if (userConstraints != null) {

			userConstraints.setId_tb_user(new Integer(userConstraints.getId_tb_user().intValue() + 1));
		}

		// Information output

		return userConstraints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.IUserDAO#validateLogin(java.lang.String)
	 */
	@Override
	public Boolean validateLogin(String login) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		Boolean flag = null;

		int i = 1;

		// Data processing

		if (login != null && !login.equals("")) {

			try {

				query = "SELECT U.LOGIN FROM ";

				query += V_TABLE_USER;

				query += " U ";

				query += "WHERE ";

				query += "U.LOGIN = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setString(i++, login);

				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {

					flag = new Boolean(true);

				} else {

					flag = new Boolean(false);
				}

			} catch (SQLException e) {
				// TODO: handle exception

				flag = null;

				e.printStackTrace();

			} finally {

				if (preparedStatement != null) {

					try {

						preparedStatement.close();

					} catch (SQLException e2) {
						// TODO: handle exception

						e2.printStackTrace();
					}
				}
			}
		}

		// Information output

		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.IUserDAO#validateLoginToUpdate(vo.user.User,
	 * java.lang.String)
	 */
	@Override
	public Boolean validateLoginToUpdate(User user, String login) {

		// Variables declaration

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		Boolean flag = null;

		int i = 1;

		// Data processing

		if (user != null && (login != null && !login.equals(""))) {

			try {

				query = "SELECT * FROM ";

				query += V_TABLE_USER;

				query += " U ";

				query += "WHERE ";

				query += "U.LOGIN = ? AND U.ID_TB_USER != ?";

				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setString(i++, login);

				preparedStatement.setInt(i++, user.getUserCode().intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.next()) {

					flag = new Boolean(true);

				} else {

					flag = new Boolean(false);
				}

			} catch (SQLException e) {
				// TODO: handle exception

				flag = null;

				e.printStackTrace();

			} finally {

				if (preparedStatement != null) {

					try {

						preparedStatement.close();

					} catch (SQLException e2) {
						// TODO: handle exception

						e2.printStackTrace();
					}
				}
			}
		}

		// Information output

		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.IUserDAO#authentication(vo.user.User)
	 */
	@Override
	public Boolean authentication(User user) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		Boolean flag = null;

		int i = 1;

		// Data processing

		if (user != null) {

			try {

				query = "SELECT * FROM ";

				query += V_TABLE_USER;

				query += " U ";

				query += "WHERE ";

				query += "U.LOGIN = ? AND U.PASSWORD = ?";

				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setString(i++, user.getLogin());

				preparedStatement.setString(i++, user.getPassword());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.next()) {

					flag = new Boolean(true);

				} else {

					flag = new Boolean(false);
				}

			} catch (SQLException e) {
				// TODO: handle exception

				user = null;

				e.printStackTrace();

			} finally {

				if (preparedStatement != null) {

					try {

						preparedStatement.close();

					} catch (SQLException e2) {
						// TODO: handle exception

						e2.printStackTrace();
					}
				}
			}
		}

		// Information output

		return flag;
	}
}
