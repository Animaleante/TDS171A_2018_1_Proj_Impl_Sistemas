package persistence.person;

import java.util.List;

import br.edu.uniopet.CPF;
import vo.person.PersonConstraints;
import vo.person.Person;

/**
 * Interface to define the operations against the database to Person entity.
 * 
 * @author Baracho
 * 
 * @since January 08, 2017
 * 
 * @version 3.0
 * 
 */

interface IPersonDAO {

	public Person salve(Person person);

	public List<Person> list();

	public Person update(Person person);

	public Boolean delete(Person person);

	public PersonConstraints search();

	public Person search(PersonConstraints personConstraints);

	public List<Person> search(String name);

	public PersonConstraints personConstraints();

	public Boolean validateCPF(CPF cpf);

	public List<Person> listNames();
}
