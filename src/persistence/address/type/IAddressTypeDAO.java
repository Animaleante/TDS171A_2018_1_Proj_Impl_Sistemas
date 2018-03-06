package persistence.address.type;

import java.util.Set;

import vo.address.type.AddressType;
import vo.address.type.AddressTypeConstraints;

/**
 * Interface to define the operations against to the database to the AddressType
 * entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
interface IAddressTypeDAO {

	public AddressType salve(AddressType addressType);

	public Set<AddressType> list();

	public AddressType update(AddressType addressType);

	public Boolean delete(AddressType addressType);

	public AddressTypeConstraints search();

	public AddressType search(AddressTypeConstraints addressTypeCnstraints);

	public AddressTypeConstraints constraintsAddressType();
}
