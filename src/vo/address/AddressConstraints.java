package vo.address;

/**
 * Class to represent the constraints of the Address entity.
 * 
 * @author Baracho
 * 
 * @since 2017 jan, 12
 * 
 * @version 1.0
 *
 */
public class AddressConstraints {

	/**
	 * Instance variables
	 */

	private Integer id_tb_address;

	private Integer id_tb_user;

	private Integer id_tb_person;

	/**
	 * Default constructor
	 */
	public AddressConstraints() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Access methods
	 */

	/**
	 * @return the id_tb_Address
	 */
	public Integer getId_tb_address() {
		return id_tb_address;
	}

	/**
	 * @param id_tb_Address
	 *            the id_tb_Address to set
	 */
	public void setId_tb_address(Integer id_tb_Address) {
		this.id_tb_address = id_tb_Address;
	}

	/**
	 * @return the id_tb_User
	 */
	public Integer getId_tb_User() {
		return id_tb_user;
	}

	/**
	 * @param id_tb_User
	 *            the id_tb_User to set
	 */
	public void setId_tb_user(Integer id_tb_User) {
		this.id_tb_user = id_tb_User;
	}

	/**
	 * @return the id_tb_Person
	 */
	public Integer getId_tb_person() {
		return id_tb_person;
	}

	/**
	 * @param id_tb_Person
	 *            the id_tb_Person to set
	 */
	public void setId_tb_person(Integer id_tb_Person) {
		this.id_tb_person = id_tb_Person;
	}
}
