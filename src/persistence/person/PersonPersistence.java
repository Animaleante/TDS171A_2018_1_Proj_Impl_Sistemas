package persistence.person;

import static persistence.Persistence.CONNECTION_NOT_SUCCESS;

import java.sql.Connection;
import java.util.List;

import br.edu.uniopet.CPF;
import br.edu.uniopet.ConnectionFactory;
import vo.person.PersonConstraints;
import vo.person.Person;

/**
 * Class to implement the interface between the system and the data access layer
 * to the Person entity.
 * 
 * @author Baracho
 * 
 * @since January 08, 2017
 * 
 * @version 2.0
 * 
 */
public class PersonPersistence implements IPersonDAO {

	/**
	 * Instance variables
	 */

	private PersonDAO personDAO;

	private Connection connection;

	/**
	 * Default constructor
	 */
	public PersonPersistence() {
		// TODO Auto-generated constructor stub

		this.connection = ConnectionFactory.getInstance();

		if (this.connection != null) {

			personDAO = new PersonDAO();

			personDAO.setConnection(this.connection);

		} else {

			System.err.println(String.format("\n %s", CONNECTION_NOT_SUCCESS));
		}
	}

	/**
	 * Class operations
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.IPersonDAO#salve(vo.person.Person)
	 */
	@Override
	public Person salve(Person person) {
		// TODO Auto-generated method stub
		return personDAO.salve(person);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.IPersonDAO#list()
	 */
	@Override
	public List<Person> list() {
		// TODO Auto-generated method stub
		return personDAO.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.IPersonDAO#update(vo.person.Person)
	 */
	@Override
	public Person update(Person person) {
		// TODO Auto-generated method stub
		return personDAO.update(person);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.IPersonDAO#delete(vo.person.Person)
	 */
	@Override
	public Boolean delete(Person person) {
		// TODO Auto-generated method stub
		return personDAO.delete(person);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.IPersonDAO#search()
	 */
	@Override
	public PersonConstraints search() {
		// TODO Auto-generated method stub
		return personDAO.search();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.IPersonDAO#search(vo.person.PersonConstraints)
	 */
	@Override
	public Person search(PersonConstraints personConstraints) {
		// TODO Auto-generated method stub
		return personDAO.search(personConstraints);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.IPersonDAO#search(java.lang.String)
	 */
	@Override
	public List<Person> search(String name) {
		// TODO Auto-generated method stub
		return personDAO.search(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.IPersonDAO#constraintsPerson()
	 */
	public PersonConstraints personConstraints() {
		// TODO Auto-generated method stub
		return personDAO.personConstraints();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.IPersonDAO#validateCPF(vo.person.CPF)
	 */
	public Boolean validateCPF(CPF cpf) {
		// TODO Auto-generated method stub
		return this.personDAO.validateCPF(cpf);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.IPersonDAO#listNames()
	 */
	public List<Person> listNames() {
		return this.personDAO.listNames();
	}
}
