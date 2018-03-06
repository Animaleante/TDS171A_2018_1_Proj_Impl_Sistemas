package persistence.cep.database;

import java.util.List;

import vo.cep.FederationUnityConstraints;
import vo.cep.FederationUnity;

/**
 * Interface to define the operations against to the database to the Federation
 * Unity entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
interface IFederationUnityDAO {

	public FederationUnity save(FederationUnity federationUnity);

	public List<FederationUnity> list();

	public FederationUnity update(FederationUnity federationUnity);

	public Boolean delete(FederationUnity federationUnity);

	public FederationUnityConstraints search();

	public FederationUnity search(FederationUnityConstraints federationUnityConstraints);

	public Integer federationUnityCode();
}
