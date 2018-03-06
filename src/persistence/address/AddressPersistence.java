package persistence.address;

import static persistence.Persistence.CONNECTION_NOT_SUCCESS;

import java.sql.Connection;
import java.util.Set;

import br.edu.uniopet.ConnectionFactory;
import vo.address.Address;
import vo.address.AddressConstraints;
import vo.person.Person;

/**
 * Class to implement a interface between the system and data access layer to
 * the Address Entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
public class AddressPersistence implements IAddressDAO {

	/**
	 * Instance variables
	 */

	private AddressDAO addressDAO;

	private Connection connection;

	/**
	 * Default constructor
	 */

	public AddressPersistence() {
		// TODO Auto-generated constructor stub

		this.connection = ConnectionFactory.getInstance();

		if (this.connection != null) {

			this.addressDAO = new AddressDAO();

			this.addressDAO.setConnection(connection);

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
	 * @see persistence.address.IAddressDAO#salve(vo.person.Person)
	 */
	@Override
	public Set<Address> salve(Person person) {
		// TODO Auto-generated method stub
		return this.addressDAO.salve(person);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.IAddressDAO#salve(vo.address.Address)
	 */
	@Override
	public Address salve(Address address) {
		// TODO Auto-generated method stub
		return this.addressDAO.salve(address);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.IAddressDAO#update(vo.person.Person)
	 */
	@Override
	public Set<Address> update(Person person) {
		// TODO Auto-generated method stub
		return this.addressDAO.update(person);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.IAddressDAO#update(vo.address.Address)
	 */
	@Override
	public Address update(Address address) {
		// TODO Auto-generated method stub
		return this.addressDAO.update(address);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.IAddressDAO#delete(vo.person.Person)
	 */
	@Override
	public Boolean delete(Person person) {
		// TODO Auto-generated method stub
		return this.addressDAO.delete(person);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.IAddressDAO#delete(vo.address.Address)
	 */
	@Override
	public Boolean delete(Address address) {
		// TODO Auto-generated method stub
		return this.addressDAO.delete(address);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.IAddressDAO#search()
	 */
	@Override
	public AddressConstraints search() {
		// TODO Auto-generated method stub
		return this.addressDAO.search();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.IAddressDAO#search(vo.address.AddressConstraints)
	 */
	@Override
	public Address search(AddressConstraints addressConstratints) {
		// TODO Auto-generated method stub
		return this.addressDAO.search(addressConstratints);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.IAddressDAO#search(vo.person.Person)
	 */
	@Override
	public Set<Address> search(Person person) {
		// TODO Auto-generated method stub
		return this.addressDAO.salve(person);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.IAddressDAO#addressConstraints()
	 */
	@Override
	public AddressConstraints addressConstraints() {
		// TODO Auto-generated method stub
		return this.addressDAO.addressConstraints();
	}
}
