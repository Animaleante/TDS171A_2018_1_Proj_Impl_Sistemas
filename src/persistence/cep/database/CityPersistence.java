package persistence.cep.database;

import static persistence.Persistence.CONNECTION_NOT_SUCCESS;

import java.sql.Connection;
import java.util.List;

import br.edu.uniopet.ConnectionFactory;
import vo.cep.City;
import vo.cep.CityConstraints;
import vo.cep.FederationUnity;

/**
 * Class to implement a interface between the system and data access layer to
 * the City Entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
public class CityPersistence implements ICityDAO {

	/**
	 * Instance variables
	 */

	private Connection connection;

	private CityDAO cityDAO;

	/**
	 * Default constructor
	 */
	public CityPersistence() {
		// TODO Auto-generated constructor stub

		this.connection = ConnectionFactory.getInstance();

		if (this.connection != null) {

			this.cityDAO = new CityDAO();

			this.cityDAO.setConnection(this.connection);

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
	 * @see persistence.cep.database.ICityDAO#save(vo.cep.City)
	 */
	public City save(City city) {
		return this.cityDAO.save(city);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.ICityDAO#list()
	 */
	public List<City> list() {
		return this.cityDAO.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.ICityDAO#update(vo.cep.City)
	 */
	public City update(City city) {
		return this.cityDAO.update(city);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.ICityDAO#delete(vo.cep.City)
	 */
	public Boolean delete(City city) {
		return this.cityDAO.delete(city);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.ICityDAO#search()
	 */
	public CityConstraints search() {
		return this.cityDAO.search();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.ICityDAO#search(vo.cep.CityConstraints)
	 */
	@Override
	public City search(CityConstraints cityConstraints) {
		// TODO Auto-generated method stub
		return this.cityDAO.search(cityConstraints);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.ICityDAO#search(vo.cep.FederationUnity)
	 */
	@Override
	public List<City> search(FederationUnity federationUnity) {
		// TODO Auto-generated method stub
		return this.cityDAO.search(federationUnity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.ICityDAO#cityCode()
	 */
	public Integer cityCode() {
		return this.cityDAO.cityCode();
	}
}
