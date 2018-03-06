package persistence.permission;

import static persistence.Persistence.CONNECTION_NOT_SUCCESS;

import java.sql.Connection;
import java.util.Set;

import br.edu.uniopet.ConnectionFactory;
import vo.permission.PermitionConstraints;
import vo.permission.Permission;

/**
 * Interface between the system and the data access layer to the Permission
 * entity.
 * 
 * @author Baracho
 * 
 * @since January 31, 2017
 * 
 * @version 1.0
 *
 */
public class PermissionPersistence implements IPermissionDAO {

	/**
	 * Instance variables
	 */

	private Connection connection;

	private PermissionDAO permissionDAO;

	/**
	 * Default constructor
	 */
	public PermissionPersistence() {
		// TODO Auto-generated constructor stub

		this.connection = ConnectionFactory.getInstance();

		if (this.connection != null) {

			this.permissionDAO = new PermissionDAO();
			this.permissionDAO.setConnection(this.connection);

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
	 * @see persistence.permission.IPermissionDAO#save(vo.permission.Permission)
	 */
	@Override
	public Permission save(Permission permission) {
		// TODO Auto-generated method stub
		return this.permissionDAO.save(permission);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.permission.IPermissionDAO#list()
	 */
	@Override
	public Set<Permission> list() {
		// TODO Auto-generated method stub
		return this.permissionDAO.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.permission.IPermissionDAO#update(vo.permission.Permission)
	 */
	@Override
	public Permission update(Permission permission) {
		// TODO Auto-generated method stub
		return this.permissionDAO.update(permission);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.permission.IPermissionDAO#delete(vo.permission.Permission)
	 */
	@Override
	public Boolean delete(Permission permission) {
		// TODO Auto-generated method stub
		return this.delete(permission);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.permission.IPermissionDAO#search()
	 */
	@Override
	public PermitionConstraints search() {
		// TODO Auto-generated method stub
		return this.permissionDAO.search();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.permission.IPermissionDAO#search(vo.permission.
	 * PermitionConstraints)
	 */
	@Override
	public Permission search(PermitionConstraints permitionConstraints) {
		// TODO Auto-generated method stub
		return this.permissionDAO.search(permitionConstraints);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.permission.IPermissionDAO#permissionConstraints()
	 */
	public PermitionConstraints permissionConstraints() {
		return this.permissionDAO.permissionConstraints();
	}
}
