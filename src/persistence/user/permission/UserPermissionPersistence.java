package persistence.user.permission;

import static persistence.Persistence.CONNECTION_NOT_SUCCESS;

import java.sql.Connection;
import java.util.Set;

import br.edu.uniopet.ConnectionFactory;
import vo.permission.Permission;
import vo.user.User;
import vo.user.permition.UserPermissionConstraints;

/**
 * Interface to define the operations against to the database to the
 * UserPermission entity.
 * 
 * @author Baracho
 * 
 * @since January 31, 2017
 * 
 * @version 1.0
 *
 */
public class UserPermissionPersistence implements IUserPermissionDAO {

	/**
	 * Instance variables
	 */

	private Connection connection;

	private UserPermissionDAO userPermissionDAO;

	/**
	 * Default constructor
	 */
	public UserPermissionPersistence() {
		// TODO Auto-generated constructor stub

		this.connection = ConnectionFactory.getInstance();

		if (this.connection != null) {

			this.userPermissionDAO = new UserPermissionDAO();

			this.userPermissionDAO.setConnection(connection);

		} else {

			System.err.println(String.format("\n %s", CONNECTION_NOT_SUCCESS));
		}
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
		return this.userPermissionDAO.save(user);
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
		return this.save(userPermissionConstraints);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.permission.IUserPermissionDAO#delete(vo.user.User)
	 */
	@Override
	public Boolean delete(User user) {
		// TODO Auto-generated method stub
		return this.userPermissionDAO.delete(user);
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
		return this.userPermissionDAO.delete(userPermissionConstraints);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.permission.IUserPermissionDAO#search(vo.user.User)
	 */
	@Override
	public Set<Permission> search(User user) {
		// TODO Auto-generated method stub
		return this.userPermissionDAO.search(user);
	}
}
