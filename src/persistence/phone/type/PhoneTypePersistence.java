package persistence.phone.type;

import static persistence.Persistence.CONNECTION_NOT_SUCCESS;

import java.sql.Connection;
import java.util.Set;

import br.edu.uniopet.ConnectionFactory;
import vo.phone.type.PhoneTypeConstraints;
import vo.phone.type.PhoneType;

/**
 * Class to implement a interface between the system and data access layer to
 * the PhoneType Entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
public class PhoneTypePersistence implements IPhoneTypeDAO {

	/**
	 * Instance variables
	 */

	private Connection connection;

	private PhoneTypeDAO phoneTypeDAO;

	/**
	 * Default constructor
	 */
	public PhoneTypePersistence() {
		// TODO Auto-generated constructor stub

		this.connection = ConnectionFactory.getInstance();

		if (this.connection != null) {

			this.phoneTypeDAO = new PhoneTypeDAO();

			this.phoneTypeDAO.setConnection(connection);

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
	 * @see persistence.phone.type.ITipoTelefoneDAO#salve(vo.phone.PhoneType)
	 */
	@Override
	public PhoneType salve(PhoneType phoneType) {
		// TODO Auto-generated method stub
		return this.phoneTypeDAO.salve(phoneType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.type.ITipoTelefoneDAO#list()
	 */
	@Override
	public Set<PhoneType> list() {
		// TODO Auto-generated method stub
		return this.phoneTypeDAO.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.type.ITipoTelefoneDAO#update(vo.phone.PhoneType)
	 */
	@Override
	public PhoneType update(PhoneType phoneType) {
		// TODO Auto-generated method stub
		return this.phoneTypeDAO.update(phoneType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.type.ITipoTelefoneDAO#delete(vo.phone.PhoneType)
	 */
	@Override
	public Boolean delete(PhoneType phoneType) {
		// TODO Auto-generated method stub
		return this.phoneTypeDAO.delete(phoneType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.type.ITipoTelefoneDAO#search()
	 */
	@Override
	public PhoneTypeConstraints search() {
		// TODO Auto-generated method stub
		return this.phoneTypeDAO.search();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * persistence.phone.type.ITipoTelefoneDAO#search(vo.phone.ConstraintsPhoneType)
	 */
	@Override
	public PhoneType search(PhoneTypeConstraints constraintsPhoneType) {
		// TODO Auto-generated method stub
		return this.phoneTypeDAO.search(constraintsPhoneType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.type.IPhoneTypeDAO#constraintsPhoneType()
	 */
	@Override
	public PhoneTypeConstraints constraintsPhoneType() {
		// TODO Auto-generated method stub
		return this.phoneTypeDAO.constraintsPhoneType();
	}
}
