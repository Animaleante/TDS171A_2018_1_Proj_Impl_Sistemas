package persistence.cep.database;

import static persistence.Persistence.CONNECTION_NOT_SUCCESS;

import java.sql.Connection;
import java.util.List;

import br.edu.uniopet.ConnectionFactory;
import vo.cep.City;
import vo.cep.DistrictConstraints;
import vo.cep.District;

/**
 * Class to implement a interface between the system and data access layer to
 * the District Entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
public class DistrictPersistence implements IDistrictDAO {

	/**
	 * Instance variables
	 */

	private DistrictDAO disctrictDAO;

	private Connection connection;

	/**
	 * Default constructor
	 */
	public DistrictPersistence() {
		// TODO Auto-generated constructor stub

		this.connection = ConnectionFactory.getInstance();

		if (this.connection != null) {

			this.disctrictDAO = new DistrictDAO();

			this.disctrictDAO.setConnection(connection);

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
	 * @see persistence.cep.database.IDistrictDAO#save(vo.cep.District)
	 */
	public District save(District district) {
		return this.disctrictDAO.save(district);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IDistrictDAO#list()
	 */
	public List<District> list() {
		return this.disctrictDAO.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IDistrictDAO#update(vo.cep.District)
	 */
	public District update(District district) {
		return this.disctrictDAO.update(district);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IDistrictDAO#delete(vo.cep.District)
	 */
	public Boolean delete(District district) {
		return this.disctrictDAO.delete(district);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IDistrictDAO#search()
	 */
	public DistrictConstraints search() {
		return this.disctrictDAO.search();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IDistrictDAO#search(vo.cep.DistrictConstraints)
	 */
	@Override
	public District search(DistrictConstraints districtConstraints) {
		// TODO Auto-generated method stub
		return this.disctrictDAO.search(districtConstraints);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IDistrictDAO#search(vo.cep.City)
	 */
	@Override
	public List<District> search(City city) {
		// TODO Auto-generated method stub
		return this.disctrictDAO.search(city);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IDistrictDAO#districtCode()
	 */
	public Integer districtCode() {
		return this.disctrictDAO.districtCode();
	}
}
