package vo.person.phone;

import vo.person.Person;
import vo.phone.Phone;

/**
 * Class to represent the PersonPhone entity.
 * 
 * @author Baracho
 * 
 * @since 2017 dez, 08
 * 
 * @version 1.0
 *
 */
public class PersonPhone {

	/**
	 * Instance variables
	 */

	private Person person;

	private Phone phone;

	/**
	 * Default constructor
	 */
	public PersonPhone() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param person
	 *            the person to set
	 * @param phone
	 *            the phone to set Parameterized constructor
	 */
	public PersonPhone(Person person, Phone phone) {

		this.setPerson(person);

		this.setPhone(phone);
	}

	/**
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * @param person
	 *            the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	/**
	 * @return the phone
	 */
	public Phone getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(Phone phone) {
		this.phone = phone;
	}
}
