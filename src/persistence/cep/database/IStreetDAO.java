package persistence.cep.database;

import java.util.List;

import vo.cep.City;
import vo.cep.StreetConstraints;
import vo.cep.District;
import vo.cep.Street;

/**
 * Interface to define the operations against to the database to the Street
 * entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
interface IStreetDAO {

	public Street save(Street street);

	public List<Street> list();
	
	public Street update(Street street, StreetConstraints streetConstraints);
	
	public Boolean delete(Street street);

	public StreetConstraints search();

	public Street search(StreetConstraints streetConstraints);

	public Street search(Integer streetcode);

	public Street searchCEP(Integer cep);

	public List<Street> search(District district);

	public List<StreetConstraints> search(City city);

	public List<StreetConstraints> searchStreetConstraints();

	public Integer streetCode();
}
