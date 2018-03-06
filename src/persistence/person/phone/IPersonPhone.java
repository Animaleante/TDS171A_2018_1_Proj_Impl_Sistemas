package persistence.person.phone;

import java.util.Set;

import vo.person.Person;
import vo.person.phone.PersonPhoneConstraints;
import vo.phone.PhoneConstraints;
import vo.phone.Phone;

/**
 * Interface to define the operations against to the database to the PersonPhone
 * entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
interface IPersonPhone {

	public Boolean save(Person person);

	public Boolean save(Phone phone);

	public Boolean delete(Person person);

	public Boolean delete(Phone phone);

	public Set<PersonPhoneConstraints> search(Person person);

	public PersonPhoneConstraints search(PhoneConstraints phoneConstraints);
}
