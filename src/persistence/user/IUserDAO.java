package persistence.user;

import java.util.List;

import vo.user.UserConstraints;
import vo.user.User;

/**
 * Interface to define the operations against to the database to the User
 * entity.
 * 
 * @author Baracho
 * 
 * @since January 09, 2017
 * 
 * @version 1.0
 *
 */
interface IUserDAO {

	public User save(User user);

	public List<User> list();

	public User update(User user);

	public Boolean delete(User user);

	public UserConstraints search();

	public User search(UserConstraints userConstraints);

	public UserConstraints userConstraints();

	public Boolean validateLogin(String login);

	public Boolean validateLoginToUpdate(User user, String login);

	public Boolean authentication(User user);
}
