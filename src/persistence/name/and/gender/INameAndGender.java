package persistence.name.and.gender;

import java.util.List;

import vo.person.Person;

/**
 * Interface to define the operations against the database to NameAndGender
 * entity.
 * 
 * @author Baracho
 * 
 * @since November 29, 2017
 * 
 * @version 1.0
 *
 */
interface INameAndGender {

	public Person salve(Person person);

	public List<Person> list();

	public Person update(Person person);

	public Boolean delete(Person person);

	public Integer search();

	public Person search(Integer personCode);

	public List<Person> search(String name);

	public Integer personCode();
}
