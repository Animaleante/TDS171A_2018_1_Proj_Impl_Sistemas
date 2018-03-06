package persistence.cep.database;

import static persistence.Persistence.CONNECTION_NOT_SUCCESS;

import java.sql.Connection;
import java.util.List;

import br.edu.uniopet.ConnectionFactory;
import vo.cep.FederationUnityConstraints;
import vo.cep.FederationUnity;

/**
 * Class to implement a interface between the system and data access layer to
 * the Federation Unity Entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
public class FederationUnityPersistence implements IFederationUnityDAO {

	/**
	 * Instance variables
	 */

	private Connection connection;

	private FederationUnityDAO federationUnityDAO;

	/**
	 * Default constructor
	 */
	public FederationUnityPersistence() {
		// TODO Auto-generated constructor stub

		this.connection = ConnectionFactory.getInstance();

		if (this.connection != null) {

			this.federationUnityDAO = new FederationUnityDAO();

			this.federationUnityDAO.setConnection(this.connection);

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
	 * persistence.cep.database.IFederationUnityDAO#save(vo.cep.FederationUnity)
	 */
	public FederationUnity save(FederationUnity federationUnity) {
		return this.federationUnityDAO.save(federationUnity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IFederationUnityDAO#list()
	 */
	@Override
	public List<FederationUnity> list() {
		// TODO Auto-generated method stub
		return this.federationUnityDAO.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * persistence.cep.database.IFederationUnityDAO#update(vo.cep.FederationUnity)
	 */
	public FederationUnity update(FederationUnity federationUnity) {
		return this.federationUnityDAO.update(federationUnity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * persistence.cep.database.IFederationUnityDAO#delete(vo.cep.FederationUnity)
	 */
	public Boolean delete(FederationUnity federationUnity) {
		return this.federationUnityDAO.delete(federationUnity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IFederationUnityDAO#search()
	 */
	public FederationUnityConstraints search() {
		return this.federationUnityDAO.search();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IFederationUnityDAO#search(vo.cep.
	 * FederationUnityConstraints)
	 */
	@Override
	public FederationUnity search(FederationUnityConstraints FederationUnityConstraints) {
		// TODO Auto-generated method stub
		return federationUnityDAO.search(FederationUnityConstraints);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IFederationUnityDAO#federationUnityCode()
	 */
	public Integer federationUnityCode() {
		return this.federationUnityDAO.federationUnityCode();
	}
}
