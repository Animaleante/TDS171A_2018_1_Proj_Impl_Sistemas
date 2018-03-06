package persistence.phone;

import java.util.Set;

import vo.person.Person;
import vo.phone.PhoneConstraints;
import vo.phone.Phone;

/**
 * Interface to define the operations against to the database to the Phone
 * entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
interface IPhoneDAO {

	public Set<Phone> save(Person person);

	public Phone save(Phone phone);

	public Set<Phone> update(Person person);

	public Phone update(Phone phone);

	public Boolean delete(Person person);

	public Boolean delete(Phone phone);

	public PhoneConstraints search();

	public Phone search(PhoneConstraints phoneConstraints);

	public Set<Phone> search(Person person);

	public PhoneConstraints phoneConstraints();
}
