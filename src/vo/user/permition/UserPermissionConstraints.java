package vo.user.permition;

/**
 * Class to represent the constraints of the UserPermission entity.
 * 
 * @author Baracho
 * 
 * @since 2017 fev, 04
 * 
 * @version 1.0
 *
 */
public class UserPermissionConstraints {

	/**
	 * Instance variables
	 */

	private Integer id_tb_permission;

	private Integer id_tb_user;

	/**
	 * Default constructor
	 */
	public UserPermissionConstraints() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Access methods
	 */

	/**
	 * @return the id_tb_permission
	 */
	public Integer getId_tb_permission() {
		return id_tb_permission;
	}

	/**
	 * @param id_tb_permission
	 *            the id_tb_permission to set
	 */
	public void setId_tb_permission(Integer id_tb_permission) {
		this.id_tb_permission = id_tb_permission;
	}

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
