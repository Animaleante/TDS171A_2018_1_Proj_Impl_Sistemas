package persistence.address;

import java.util.Set;

import vo.address.Address;
import vo.address.AddressConstraints;
import vo.person.Person;

/**
 * Interface to define the operations against to the database to the Address
 * entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
interface IAddressDAO {

	public Set<Address> salve(Person person);

	public Address salve(Address address);

	public Set<Address> update(Person person);

	public Address update(Address address);

	public Boolean delete(Person person);

	public Boolean delete(Address endereco);

	public AddressConstraints search();

	public Address search(AddressConstraints addressConstratints);

	public Set<Address> search(Person person);

	public AddressConstraints addressConstraints();
}
