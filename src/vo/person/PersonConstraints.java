package vo.person;

/**
 * Class to represent the constraints of the Person entity.
 * 
 * @author Baracho
 * 
 * @since 2017 jan, 12
 * 
 * @version 1.0
 *
 */
public class PersonConstraints {

	/**
	 * Instance variables
	 */

	private Integer id_tb_person;

	private Integer id_tb_user;

	/**
	 * Default Constructor
	 */

	public PersonConstraints() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Access methods
	 */

	/**
	 * @return the id_tb_person
	 */
	public Integer getId_tb_person() {
		return id_tb_person;
	}

	/**
	 * @param id_tb_person
	 *            the id_tb_person to set
	 */
	public void setId_tb_Person(Integer id_tb_person) {
		this.id_tb_person = id_tb_person;
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
	public void setId_tb_User(Integer id_tb_user) {
		this.id_tb_user = id_tb_user;
	}
}
