package persistence.phone;

import static persistence.Persistence.CONNECTION_NOT_SUCCESS;

import java.sql.Connection;
import java.util.Set;

import br.edu.uniopet.ConnectionFactory;
import vo.person.Person;
import vo.phone.PhoneConstraints;
import vo.phone.Phone;

/**
 * Class to implement a interface between the system and data access layer to
 * the Phone entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
public class PhonePersistence implements IPhoneDAO {

	/**
	 * Instance variables
	 */

	private Connection connection;

	private PhoneDAO phoneDAO;

	/**
	 * Default constructor
	 */
	public PhonePersistence() {
		// TODO Auto-generated constructor stub

		this.connection = ConnectionFactory.getInstance();

		if (this.connection != null) {

			this.phoneDAO = new PhoneDAO();

			this.phoneDAO.setConnection(this.connection);

		} else {

			System.err.println(String.format("\n %s", CONNECTION_NOT_SUCCESS));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.IPhoneDAO#save(vo.person.Person)
	 */
	@Override
	public Set<Phone> save(Person person) {
		// TODO Auto-generated method stub
		return this.phoneDAO.save(person);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.IPhoneDAO#save(vo.phone.Phone)
	 */
	@Override
	public Phone save(Phone phone) {
		// TODO Auto-generated method stub
		return this.phoneDAO.save(phone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.IPhoneDAO#update(vo.person.Person)
	 */
	@Override
	public Set<Phone> update(Person person) {
		// TODO Auto-generated method stub
		return this.phoneDAO.update(person);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.IPhoneDAO#update(vo.phone.Phone)
	 */
	@Override
	public Phone update(Phone phone) {
		// TODO Auto-generated method stub
		return this.phoneDAO.update(phone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.IPhoneDAO#delete(vo.person.Person)
	 */
	@Override
	public Boolean delete(Person person) {
		// TODO Auto-generated method stub
		return this.phoneDAO.delete(person);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.IPhoneDAO#delete(vo.phone.Phone)
	 */
	@Override
	public Boolean delete(Phone phone) {
		// TODO Auto-generated method stub
		return this.phoneDAO.delete(phone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.IPhoneDAO#search()
	 */
	@Override
	public PhoneConstraints search() {
		// TODO Auto-generated method stub
		return this.phoneDAO.search();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.IPhoneDAO#search(vo.phone.PhoneConstraints)
	 */
	@Override
	public Phone search(PhoneConstraints phoneConstraints) {
		// TODO Auto-generated method stub
		return this.phoneDAO.search(phoneConstraints);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.IPhoneDAO#search(vo.person.Person)
	 */
	@Override
	public Set<Phone> search(Person person) {
		// TODO Auto-generated method stub
		return this.phoneDAO.search(person);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.IPhoneDAO#phoneConstraints()
	 */
	@Override
	public PhoneConstraints phoneConstraints() {
		// TODO Auto-generated method stub
		return this.phoneDAO.phoneConstraints();
	}
}
