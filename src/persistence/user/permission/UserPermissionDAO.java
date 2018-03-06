package persistence.user.permission;

import static persistence.Persistence.DELETE_NOT_SUCCESS;
import static persistence.Persistence.DELETE_SUCCESS;
import static persistence.Persistence.FLAG;
import static persistence.Persistence.SAVE_ERROR;
import static persistence.Persistence.SAVE_NOT_SUCCESS;
import static persistence.Persistence.SAVE_SUCCESS;
import static persistence.Persistence.V_TABLE_USER_PERMISSION;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import persistence.permission.PermissionPersistence;
import vo.permission.PermitionConstraints;
import vo.permission.Permission;
import vo.user.User;
import vo.user.permition.UserPermissionConstraints;

/**
 * Class to implement the operations against to the database to the
 * UserPermition entity.
 * 
 * @author Baracho
 * 
 * @since January 31, 2017
 * 
 * @version 1.0
 *
 */
class UserPermissionDAO implements IUserPermissionDAO {

	/**
	 * Instance variables
	 */

	private Connection connection;

	private PermissionPersistence permissionPersistence;

	/**
	 * Default constructor
	 */

	public UserPermissionDAO() {
		// TODO Auto-generated constructor stub
		this.permissionPersistence = new PermissionPersistence();
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
	 * @see persistence.user.permission.IUserPermissionDAO#save(vo.user.User)
	 */
	@Override
	public Boolean save(User user) {
		// TODO Auto-generated method stub

		// Variables declaration

		Permission permission = null;

		UserPermissionConstraints userPermissionConstraints = null;

		Iterator<Permission> iterator = null;

		Integer counter = null;

		Boolean flag = null;

		// Data processing

		if (user != null) {

			if (user.getPermissions() != null && user.getPermissions().size() > 0) {

				iterator = user.getPermissions().iterator();

				counter = new Integer(0);

				while (iterator.hasNext()) {

					permission = iterator.next();

					userPermissionConstraints = new UserPermissionConstraints();

					userPermissionConstraints.setId_tb_user(user.getUserCode());

					userPermissionConstraints.setId_tb_permission(permission.getPermissionCode());

					flag = this.save(userPermissionConstraints);

					if (flag != null && flag.equals(true)) {

						counter = new Integer(counter.intValue() + 1);
					}
				}
			}
		}

		if (counter != null && counter.equals(user.getPermissions().size())) {

			try {

				this.connection.commit();

				flag = new Boolean(true);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

			try {

				this.connection.rollback();

				flag = new Boolean(false);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				flag = null;
			}
		}

		// Information output

		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.permission.IUserPermissionDAO#save(vo.user.permition.
	 * UserPermissionConstraints)
	 */
	@Override
	public Boolean save(UserPermissionConstraints userPermissionConstraints) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		Boolean flag = null;

		int i = 1;

		// Data processing

		if (userPermissionConstraints != null) {

			try {

				query = "INSERT INTO ";

				query += V_TABLE_USER_PERMISSION;

				query += " USER_PERMISSION (";

				query += "USER_PERMISSION.ID_TB_USER, ";

				query += "USER_PERMISSION.ID_TB_PERMISSION";

				query += ") VALUES (";

				query += "?, ?)";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, userPermissionConstraints.getId_tb_user().intValue());

				preparedStatement.setInt(i++, userPermissionConstraints.getId_tb_permission().intValue());

				counter = new Integer(preparedStatement.executeUpdate());

				if (counter != null && counter.equals(1)) {

					msg = SAVE_SUCCESS;

					flag = new Boolean(true);

				} else {

					msg = SAVE_NOT_SUCCESS;

					flag = new Boolean(false);
				}

			} catch (SQLException e) {
				// TODO: handle exception

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

					System.err.println(String.format("\n %s%s: %s", this.getClass().getSimpleName(),
							".salve(ConstraintsUserPermission constraintsUserPermissions)", msg));
				}
			}
		}

		// Information output

		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.permission.IUserPermissionDAO#delete(vo.user.User)
	 */
	@Override
	public Boolean delete(User user) {
		// TODO Auto-generated method stub

		// Variables declaration

		Iterator<Permission> iterator = null;

		Permission permission = null;

		UserPermissionConstraints userPermissionConstraints = null;

		Integer counter = null;

		Boolean flag = null;

		// Data processing

		if (user != null) {

			if (user.getPermissions() != null && user.getPermissions().size() > 0) {

				iterator = user.getPermissions().iterator();

				counter = new Integer(0);

				while (iterator.hasNext()) {

					permission = iterator.next();

					userPermissionConstraints = new UserPermissionConstraints();

					userPermissionConstraints.setId_tb_user(user.getUserCode());

					userPermissionConstraints.setId_tb_permission(permission.getPermissionCode());

					if (this.delete(userPermissionConstraints).equals(true)) {

						counter = new Integer(counter.intValue() + 1);
					}
				}
			}
		}

		// Information output

		if (counter != null) {

			if (counter.equals(user.getPermissions().size())) {

				if (this.connection != null) {

					try {

						this.connection.commit();

						flag = new Boolean(true);

						System.err.println(String.format("\n %s%s: %s", this.getClass().getSimpleName(),
								".excluir(Usuario usuario)", "usuário permissões excluídas com sucesso."));

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} else {

				if (this.connection != null) {

					try {

						this.connection.rollback();

						flag = new Boolean(false);

						System.err.println(String.format("\n %s%s: %s", this.getClass().getSimpleName(),
								".excluír(Usuario usuario)", "não foi possível excluir os usuário permissões."));

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

						flag = null;
					}
				}
			}

		} else {

			if (this.connection != null) {

				try {

					this.connection.rollback();

					System.err.println(String.format("\n %s%s: %s", this.getClass().getSimpleName(),
							".escluir(Usuario usuario)", "erro ao salvar os usuário permissões."));

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				flag = null;
			}

		}

		// Function output

		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.permission.IUserPermissionDAO#delete(vo.user.permition.
	 * UserPermissionConstraints)
	 */
	@Override
	public Boolean delete(UserPermissionConstraints userPermissionConstraints) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Boolean flag = null;

		Integer count = null;

		int i = 1;

		// Data processing

		if (userPermissionConstraints != null) {

			try {

				query = "DELETE FROM ";

				query += V_TABLE_USER_PERMISSION;

				query += " USER_PERMISSION ";

				query += "WHERE ";

				query += "USER_PERMISSION.ID_TB_USER = ?";

				query += "AND USER_PERMISSION.ID_TB_PERMISSION = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, userPermissionConstraints.getId_tb_user().intValue());

				preparedStatement.setInt(i++, userPermissionConstraints.getId_tb_permission().intValue());

				count = new Integer(preparedStatement.executeUpdate());

				if (count != null && count.equals(1)) {

					flag = new Boolean(true);

					msg = DELETE_SUCCESS;

				} else {

					flag = new Boolean(false);

					msg = DELETE_NOT_SUCCESS;
				}

			} catch (SQLException e) {
				// TODO: handle exception

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

				if (FLAG.equals(true)) {

					if (msg != null) {

						System.err.println(String.format("\n %s%s: %s", this.getClass().getSimpleName(),
								".excluir(UsuarioPermissao usuarioPermissao)", msg));
					}

					if (userPermissionConstraints != null) {

						System.err.println(
								String.format("\n %s %s", "Dados salvos:", userPermissionConstraints.toString()));
					}
				}
			}

		}

		// Function output

		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.permission.IUserPermissionDAO#search(vo.user.User)
	 */
	@Override
	public Set<Permission> search(User user) {
		// TODO Auto-generated method stub

		// Variables declaration

		Permission permission = null;

		PermitionConstraints permitionConstraints = null;

		Set<Permission> permissions = null;

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		int i = 1;

		// Data processing

		if (user != null) {

			try {

				query = "SELECT USER_PERMISSION.ID_TB_PERMISSION FROM ";

				query += V_TABLE_USER_PERMISSION;

				query += " USER_PERMISSION ";

				query += "WHERE ";

				query += "USER_PERMISSION.ID_TB_USER = ?";

				preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				preparedStatement.setInt(i++, user.getUserCode().intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet.last()) {

					resultSet.beforeFirst();

					permissions = new HashSet<Permission>();

					while (resultSet.next()) {

						permitionConstraints = new PermitionConstraints();

						permitionConstraints.setId_tb_Permition(new Integer(resultSet.getInt("ID_TB_PERMISSION")));

						permission = this.permissionPersistence.search(permitionConstraints);

						if (permission != null) {

							permissions.add(permission);
						}
					}
				}

			} catch (SQLException e) {
				// TODO: handle exception

				permissions = null;

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

		// Function output

		return permissions;
	}
}
