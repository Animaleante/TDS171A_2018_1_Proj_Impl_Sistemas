package persistence.cep.database;

import java.util.List;

import vo.cep.City;
import vo.cep.DistrictConstraints;
import vo.cep.District;

/**
 * Interface to define the operations against to the database to the District
 * entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
interface IDistrictDAO {

	public District save(District district);

	public District update(District district);

	public Boolean delete(District district);

	public List<District> list();

	public DistrictConstraints search();

	public District search(DistrictConstraints districtConstraints);

	public List<District> search(City city);

	public Integer districtCode();
}
