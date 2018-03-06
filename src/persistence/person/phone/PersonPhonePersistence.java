package persistence.person.phone;

import static persistence.Persistence.CONNECTION_NOT_SUCCESS;

import java.sql.Connection;
import java.util.Set;

import br.edu.uniopet.ConnectionFactory;
import vo.person.Person;
import vo.person.phone.PersonPhoneConstraints;
import vo.phone.PhoneConstraints;
import vo.phone.Phone;

/**
 * Class to implement a interface between the system and data access layer to
 * the PersonPhone Entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
public class PersonPhonePersistence implements IPersonPhone {

	/**
	 * Instance variables
	 */

	private PersonPhoneDAO personPhoneDAO;

	private Connection connection;

	/**
	 * Default constructor
	 */
	public PersonPhonePersistence() {
		// TODO Auto-generated constructor stub

		this.connection = ConnectionFactory.getInstance();

		if (this.connection != null) {

			this.personPhoneDAO = new PersonPhoneDAO();

			this.personPhoneDAO.setConnection(this.connection);

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
	 * @see persistence.person.phone.IPersonPhone#save(vo.person.Person)
	 */
	@Override
	public Boolean save(Person person) {
		// TODO Auto-generated method stub
		return this.personPhoneDAO.save(person);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.phone.IPersonPhone#save(vo.phone.Phone)
	 */
	@Override
	public Boolean save(Phone phone) {
		// TODO Auto-generated method stub
		return this.personPhoneDAO.save(phone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.phone.IPersonPhone#delete(vo.person.Person)
	 */
	@Override
	public Boolean delete(Person person) {
		// TODO Auto-generated method stub
		return this.personPhoneDAO.delete(person);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.phone.IPersonPhone#delete(vo.phone.Phone)
	 */
	@Override
	public Boolean delete(Phone phone) {
		// TODO Auto-generated method stub
		return this.personPhoneDAO.delete(phone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.phone.IPersonPhone#search(vo.person.Person)
	 */
	@Override
	public Set<PersonPhoneConstraints> search(Person person) {
		// TODO Auto-generated method stub
		return this.personPhoneDAO.search(person);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.phone.IPersonPhone#search(vo.phone.PhoneConstraints)
	 */
	@Override
	public PersonPhoneConstraints search(PhoneConstraints phoneConstraints) {
		// TODO Auto-generated method stub
		return this.personPhoneDAO.search(phoneConstraints);
	}
}
