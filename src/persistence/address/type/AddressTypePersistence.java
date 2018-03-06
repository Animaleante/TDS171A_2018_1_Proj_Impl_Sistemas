package persistence.address.type;

import static persistence.Persistence.CONNECTION_NOT_SUCCESS;

import java.sql.Connection;
import java.util.Set;

import br.edu.uniopet.ConnectionFactory;
import vo.address.type.AddressType;
import vo.address.type.AddressTypeConstraints;

/**
 * Class to implement a interface between the system and data access layer to
 * the AddressType Entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
public class AddressTypePersistence implements IAddressTypeDAO {

	/**
	 * Instance variables
	 */

	private AddressTypeDAO addressTypeDAO;

	private Connection connection;

	/**
	 * Default constructor
	 */

	public AddressTypePersistence() {
		// TODO Auto-generated constructor stub

		this.connection = ConnectionFactory.getInstance();

		if (this.connection != null) {

			this.addressTypeDAO = new AddressTypeDAO();

			this.addressTypeDAO.setConnection(this.connection);

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
	 * @see
	 * persistence.address.type.IAddressTypeDAO#salve(vo.address.type.AddressType)
	 */
	@Override
	public AddressType salve(AddressType addressType) {
		// TODO Auto-generated method stub
		return this.addressTypeDAO.salve(addressType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.type.IAddressTypeDAO#list()
	 */
	@Override
	public Set<AddressType> list() {
		// TODO Auto-generated method stub
		return this.addressTypeDAO.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * persistence.address.type.IAddressTypeDAO#update(vo.address.type.AddressType)
	 */
	@Override
	public AddressType update(AddressType addressType) {
		// TODO Auto-generated method stub
		return this.addressTypeDAO.update(addressType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * persistence.address.type.IAddressTypeDAO#delete(vo.address.type.AddressType)
	 */
	@Override
	public Boolean delete(AddressType addressType) {
		// TODO Auto-generated method stub
		return this.addressTypeDAO.delete(addressType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.type.IAddressTypeDAO#search()
	 */
	@Override
	public AddressTypeConstraints search() {
		// TODO Auto-generated method stub
		return this.addressTypeDAO.search();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.type.IAddressTypeDAO#search(vo.address.type.
	 * AddressTypeConstraints)
	 */
	@Override
	public AddressType search(AddressTypeConstraints addressTypeConstraints) {
		// TODO Auto-generated method stub
		return this.addressTypeDAO.search(addressTypeConstraints);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.type.IAddressTypeDAO#constraintsAddressType()
	 */
	@Override
	public AddressTypeConstraints constraintsAddressType() {
		// TODO Auto-generated method stub
		return this.addressTypeDAO.constraintsAddressType();
	}
}
