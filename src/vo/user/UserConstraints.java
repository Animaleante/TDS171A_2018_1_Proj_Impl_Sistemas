package vo.user;

/**
 * Class to represent the constraints of the User entity.
 * 
 * @author Baracho
 * 
 * @since 12 de jan de 2017
 * 
 * @version 1.0
 *
 */
public class UserConstraints {

	/**
	 * Instance Variables
	 */

	private Integer id_tb_user;

	/**
	 * Default constructor
	 */
	public UserConstraints() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Access methods
	 */

	/**
	 * @return the id_tb_user
	 */
	public Integer getId_tb_user() {
		return id_tb_user;
	}

	/**
	 * @param id_tb_user
	 *            the id_tb_user to set
	 */
	public void setId_tb_user(Integer id_tb_user) {
		this.id_tb_user = id_tb_user;
	}
}
