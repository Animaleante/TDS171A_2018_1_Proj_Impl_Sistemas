package persistence.cep.database;

import java.util.List;

import vo.cep.City;
import vo.cep.CityConstraints;
import vo.cep.FederationUnity;

/**
 * Interface to define the operations against to the database to the City
 * entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
interface ICityDAO {

	public City save(City city);

	public List<City> list();
	
	public City update(City city);
	
	public Boolean delete(City city);
	
	public CityConstraints search();

	public City search(CityConstraints cityConstraints);

	public List<City> search(FederationUnity federationUnity);
	
	public Integer cityCode();
}
