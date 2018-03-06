package persistence.cep.database;

import static persistence.Persistence.CONNECTION_NOT_SUCCESS;

import java.sql.Connection;
import java.util.List;

import br.edu.uniopet.ConnectionFactory;
import vo.cep.City;
import vo.cep.StreetConstraints;
import vo.cep.District;
import vo.cep.Street;

/**
 * Class to implement a interface between the system and data access layer to
 * the Street Entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
public class StreetPersistence implements IStreetDAO {

	/**
	 * Instance variables
	 */

	private Connection connection;

	private StreetDAO streetDAO;

	/**
	 * Default constructor
	 */
	public StreetPersistence() {
		// TODO Auto-generated constructor stub

		this.connection = ConnectionFactory.getInstance();

		if (this.connection != null) {

			this.streetDAO = new StreetDAO();

			this.streetDAO.setConnection(this.connection);

		} else {

			System.err.println(String.format("\n %s", CONNECTION_NOT_SUCCESS));
		}
	}

	/**
	 * Class operation
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#save(vo.cep.Street)
	 */
	public Street save(Street street) {
		return this.streetDAO.save(street);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#list()
	 */
	public List<Street> list() {
		return this.streetDAO.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#update(vo.cep.Street,
	 * vo.cep.ConstraintsStreet)
	 */
	public Street update(Street street, StreetConstraints streetConstraints) {
		return this.streetDAO.update(street, streetConstraints);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#delete(vo.cep.Street)
	 */
	public Boolean delete(Street street) {
		return this.streetDAO.delete(street);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#search()
	 */
	public StreetConstraints search() {
		return this.streetDAO.search();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#search(vo.cep.StreetConstraints)
	 */
	@Override
	public Street search(StreetConstraints streetConstraints) {
		// TODO Auto-generated method stub
		return this.streetDAO.search(streetConstraints);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#search(java.lang.Integer)
	 */
	@Override
	public Street search(Integer streetCode) {
		// TODO Auto-generated method stub
		return this.streetDAO.search(streetCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#searchCEP(java.lang.Integer)
	 */
	@Override
	public Street searchCEP(Integer cep) {
		// TODO Auto-generated method stub
		return this.streetDAO.searchCEP(cep);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#search(vo.cep.District)
	 */
	@Override
	public List<Street> search(District district) {
		// TODO Auto-generated method stub
		return this.streetDAO.search(district);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#search(vo.cep.City)
	 */
	public List<StreetConstraints> search(City city) {
		// TODO Auto-generated method stub
		return this.streetDAO.search(city);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#searchConstraintsStreets()
	 */
	@Override
	public List<StreetConstraints> searchStreetConstraints() {
		// TODO Auto-generated method stub
		return this.streetDAO.searchStreetConstraints();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#streetCode()
	 */
	public Integer streetCode() {
		return this.streetDAO.streetCode();
	}
}
