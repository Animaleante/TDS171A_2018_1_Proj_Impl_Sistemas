package persistence.permission;

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
import static persistence.Persistence.V_TABLE_PERMISSION;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import vo.permission.PermitionConstraints;
import vo.permission.Permission;

/**
 * Class to implement the operations against to the database to the Permission
 * entity.
 * 
 * @author Baracho
 * 
 * @since January 31, 2017
 * 
 * @version 1.0
 *
 */
class PermissionDAO implements IPermissionDAO {

	/**
	 * Instance variables
	 */

	private Connection connection;

	/**
	 * Default constructor
	 */

	public PermissionDAO() {
		// TODO Auto-generated constructor stub
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
	 * @see persistence.permission.IPermissionDAO#save(vo.permission.Permission)
	 */
	@Override
	public Permission save(Permission permission) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer count = null;

		PermitionConstraints permitionConstraints = null;

		int i = 1;

		// Data processing

		permitionConstraints = this.permissionConstraints();

		permission.setPermissionCode(permitionConstraints.getId_tb_Permition());

		if (permission != null) {

			try {

				query = "INSERT INTO ";

				query += V_TABLE_PERMISSION;

				query += " PERMISSION (";

				query += "PERMISSION.ID_TB_PERMISSION, ";

				query += "PERMISSION.PERMISSION";

				query += ") VALUES (?, ?)";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, permission.getPermissionCode().intValue());

				preparedStatement.setString(i++, permission.getPermission());

				count = new Integer(preparedStatement.executeUpdate());

				if (count != null && count.equals(1)) {

					this.connection.commit();

					msg = SAVE_SUCCESS;

					permitionConstraints = this.search();

					permission = this.search(permitionConstraints);

					if (permission != null) {

						msg = RECOVERY_SUCCESS;

					} else {

						msg = RECOVERY_NOT_SUCCESS;
					}

				} else {

					permission = null;

					msg = SAVE_NOT_SUCCESS;
				}

			} catch (SQLException e) {
				// TODO: handle exception

				permission = null;

				msg = SAVE_ERROR + e.getMessage();

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

					System.err.println(String.format("\n %s%s: %s", this.getClass().getSimpleName(),
							".salve(Permissao permissao)", msg));

					if (permission != null) {

						System.err.println(String.format("\n %s %s", SAVE_DATA, permission.toString()));
					}
				}
			}
		}

		// Information output

		return permission;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.permission.IPermissionDAO#list()
	 */
	@Override
	public Set<Permission> list() {
		// TODO Auto-generated method stub

		// Variables declaration

		PermitionConstraints permitionConstraints = null;

		Permission permission = null;

		Set<Permission> permissions = null;

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		String msg = null;

		// Data processing

		try {

			query = "SELECT PERMISSION.ID_TB_PERMISSION FROM ";

			query += V_TABLE_PERMISSION;

			query += " PERMISSION ";

			query += "ORDER BY PERMISSION.ID_TB_PERMISSION";

			statement = this.connection.prepareStatement(query);

			resultSet = statement.executeQuery(query);

			if (resultSet != null) {

				permissions = new HashSet<Permission>();

				while (resultSet.next()) {

					permitionConstraints = new PermitionConstraints();

					permitionConstraints.setId_tb_Permition(new Integer(resultSet.getInt("ID_TB_PERMISSION")));

					permission = this.search(permitionConstraints);

					if (permission != null) {

						permissions.add(permission);
					}
				}

				msg = QUERY_SUCCESS;

			} else {

				permissions = null;

				msg = QUERY_NOT_SUCCESS;
			}

		} catch (SQLException e) {
			// TODO: handle exception

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

				if (permissions != null && permissions.size() > 0) {

					System.err.println(String.format("\n %s", LIST_DATA));

					for (Permission p : permissions) {

						System.err.println(p.toString());
					}
				}
			}
		}

		// Information output

		return permissions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.permission.IPermissionDAO#update(vo.permission.Permission)
	 */
	@Override
	public Permission update(Permission permission) {
		// TODO Auto-generated method stub

		// Variables declaration

		PermitionConstraints permitionConstraints = null;

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		int i = 1;

		// Data processing

		try {

			query = "UPDATE ";

			query += V_TABLE_PERMISSION;

			query += " PERMISSION ";

			query += "SET ";

			query += "PERMISSION.PERMISSION = ? ";

			query += " WHERE PERMISSION.ID_TB_PERMISSION = ?";

			preparedStatement = this.connection.prepareStatement(query);

			preparedStatement.setString(i++, permission.getPermission());

			preparedStatement.setInt(i++, permission.getPermissionCode());

			counter = new Integer(preparedStatement.executeUpdate());

			if (counter != null && counter.equals(1)) {

				msg = UPDATE_SUCCESS;

				permitionConstraints = new PermitionConstraints();

				permitionConstraints.setId_tb_Permition(permission.getPermissionCode());
				;

				permission = this.search(permitionConstraints);

				if (permission != null) {

					msg += RECOVERY_SUCCESS;

				} else {

					msg += RECOVERY_NOT_SUCCESS;
				}

			} else {

				permission = null;

				msg = UPDATE_NOT_SUCCESS;
			}

		} catch (SQLException e) {
			// TODO: handle exception

			permission = null;

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

				System.err.println(String.format("\n %s%s: %s", this.getClass().getSimpleName(),
						".update(Permition permission)", msg));

				if (permission != null) {

					System.err.println(String.format("\n %s %s", UPDATE_DATA, permission.toString()));
				}
			}
		}

		// Information output

		return permission;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.permission.IPermissionDAO#delete(vo.permission.Permission)
	 */
	@Override
	public Boolean delete(Permission permission) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Boolean flag = null;

		Integer counter = null;

		int i = 1;

		// Data processing

		if (permission != null) {

			try {

				query = "DELETE FROM ";

				query += V_TABLE_PERMISSION;

				query += " PERMISSION ";

				query += "WHERE ";

				query += "PERMISSION.ID_TB_PERMISSION = ?";

				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setInt(i++, permission.getPermissionCode().intValue());

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

					System.err.println(String.format("\n %s%s: %s", this.getClass().getSimpleName(),
							".delete(Permission permission)", msg));

					if (permission != null) {

						System.err.println(String.format("\n %s %s", DELETE_DATA, permission.toString()));
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
	 * @see persistence.permission.IPermissionDAO#search()
	 */
	public PermitionConstraints search() {

		// TODO Auto-generated method stub

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		PermitionConstraints permitionConstraints = null;

		// Data processing

		try {

			query = "SELECT PERMISSION.ID_TB_PERMISSION FROM ";

			query += V_TABLE_PERMISSION;

			query += " PERMISSION ";

			query += "WHERE ";

			query += "PERMISSION.ID_TB_PERMISSION = ";

			query += "(SELECT MAX(PERMISSION.ID_TB_PERMISSION) ";

			query += "FROM ";

			query += V_TABLE_PERMISSION;

			query += " PERMISSION)";

			statement = this.connection.createStatement();

			resultSet = statement.executeQuery(query);

			permitionConstraints = new PermitionConstraints();

			if (resultSet != null && resultSet.next()) {

				permitionConstraints.setId_tb_Permition(new Integer(resultSet.getInt("ID_TB_PERMISSION")));

			} else {

				permitionConstraints.setId_tb_Permition(new Integer(0));
			}

		} catch (SQLException e) {
			// TODO: handle exception

			permitionConstraints = null;

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

		return permitionConstraints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.permission.IPermissionDAO#search(vo.permission.
	 * PermitionConstraints)
	 */
	public Permission search(PermitionConstraints permitionConstraints) {

		// Variables declaration

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		Permission permission = null;

		int i = 1;

		// Data processing

		if (permitionConstraints != null) {

			try {

				query = "SELECT * FROM ";

				query += V_TABLE_PERMISSION;

				query += " PERMISSION ";

				query += "WHERE ";

				query += "PERMISSION.ID_TB_PERMISSION = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, permitionConstraints.getId_tb_Permition().intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.next()) {

					permission = new Permission();

					permission.setPermissionCode(new Integer(resultSet.getInt("ID_TB_PERMISSION")));

					permission.setPermission(resultSet.getString("PERMISSION"));
				}

			} catch (SQLException e) {
				// TODO: handle exception

				permission = null;

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

		return permission;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.permission.IPermissionDAO#permissionConstraints()
	 */
	public PermitionConstraints permissionConstraints() {

		// Variable declaration

		PermitionConstraints permitionConstraints = null;

		// Data processing

		permitionConstraints = this.search();

		if (permitionConstraints != null) {

			permitionConstraints
					.setId_tb_Permition(new Integer(permitionConstraints.getId_tb_Permition().intValue() + 1));
		}

		// Information output

		return permitionConstraints;
	}
}
