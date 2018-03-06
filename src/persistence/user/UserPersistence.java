package persistence.user;

import static persistence.Persistence.CONNECTION_NOT_SUCCESS;

import java.sql.Connection;
import java.util.List;

import br.edu.uniopet.ConnectionFactory;
import vo.user.UserConstraints;
import vo.user.User;

/**
 * Class to implement the interface between the system and the data access layer
 * to the User entity.
 * 
 * @author Baracho
 * 
 * @since January 09, 2017
 * 
 * @version 1.0
 *
 */
public class UserPersistence implements IUserDAO {

	/**
	 * Instance variables
	 */

	private Connection connection;

	private UserDAO userDAO;

	/**
	 * Default constructor
	 */

	public UserPersistence() {
		// TODO Auto-generated constructor stub

		this.connection = ConnectionFactory.getInstance();

		if (this.connection != null) {

			this.userDAO = new UserDAO();

			this.userDAO.setConnection(this.connection);

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
	 * @see persistence.user.IUserDAO#save(vo.user.User)
	 */
	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return this.userDAO.save(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.IUserDAO#list()
	 */
	@Override
	public List<User> list() {
		// TODO Auto-generated method stub
		return this.userDAO.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.IUserDAO#update(vo.user.User)
	 */
	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
		return this.userDAO.update(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.IUserDAO#delete(vo.user.User)
	 */
	@Override
	public Boolean delete(User user) {
		// TODO Auto-generated method stub
		return this.userDAO.delete(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.IUserDAO#search()
	 */
	@Override
	public UserConstraints search() {
		// TODO Auto-generated method stub
		return this.userDAO.search();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.IUserDAO#search(vo.user.UserConstraints)
	 */
	@Override
	public User search(UserConstraints userConstraints) {
		// TODO Auto-generated method stub
		return this.userDAO.search(userConstraints);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.IUserDAO#userCode()
	 */
	public UserConstraints userConstraints() {
		return this.userDAO.userConstraints();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.IUserDAO#validateLogin(java.lang.String)
	 */
	@Override
	public Boolean validateLogin(String login) {
		// TODO Auto-generated method stub
		return this.userDAO.validateLogin(login);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.IUserDAO#validateLoginToUpdate(vo.user.User,
	 * java.lang.String)
	 */
	public Boolean validateLoginToUpdate(User user, String login) {
		return this.userDAO.validateLoginToUpdate(user, login);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.user.IUserDAO#authentication(vo.user.User)
	 */
	@Override
	public Boolean authentication(User user) {
		// TODO Auto-generated method stub
		return this.userDAO.authentication(user);
	}
}
