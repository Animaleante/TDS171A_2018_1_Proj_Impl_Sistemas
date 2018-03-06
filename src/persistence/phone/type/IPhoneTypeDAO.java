package persistence.phone.type;

import java.util.Set;

import vo.phone.type.PhoneTypeConstraints;
import vo.phone.type.PhoneType;

/**
 * Interface to define the operations against to the database to the PhoneType
 * entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
interface IPhoneTypeDAO {

	public PhoneType salve(PhoneType phoneType);

	public Set<PhoneType> list();

	public PhoneType update(PhoneType phoneType);

	public Boolean delete(PhoneType phoneType);

	public PhoneTypeConstraints search();

	public PhoneType search(PhoneTypeConstraints constraintsPhoneType);

	public PhoneTypeConstraints constraintsPhoneType();
}
