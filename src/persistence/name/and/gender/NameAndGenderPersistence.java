package persistence.name.and.gender;

import static persistence.Persistence.CONNECTION_NOT_SUCCESS;

import java.sql.Connection;
import java.util.List;

import br.edu.uniopet.ConnectionFactory;
import vo.person.Person;

/**
 * Class to implement the interface between the system and the data access layer
 * to the NameAndGender entity.
 * 
 * @author Baracho
 * 
 * @since November 29, 2017
 * 
 * @version 1.0
 *
 */
public class NameAndGenderPersistence implements INameAndGender {

	/**
	 * Instance variables
	 */

	private Connection connection;

	private NameAndGendersDAO nameAndGendersDAO;

	/**
	 * Default constructor
	 */
	public NameAndGenderPersistence() {
		// TODO Auto-generated constructor stub

		this.connection = ConnectionFactory.getInstance();

		if (this.connection != null) {

			this.nameAndGendersDAO = new NameAndGendersDAO();

			this.nameAndGendersDAO.setConnection(this.connection);

		} else {

			System.err.println(String.format("\n %s", CONNECTION_NOT_SUCCESS));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.name.and.gender.INameAndGender#salve(vo.person.Person)
	 */
	@Override
	public Person salve(Person person) {
		// TODO Auto-generated method stub
		return this.nameAndGendersDAO.salve(person);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.name.and.gender.INameAndGender#list()
	 */
	@Override
	public List<Person> list() {
		// TODO Auto-generated method stub
		return this.nameAndGendersDAO.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.name.and.gender.INameAndGender#update(vo.person.Person)
	 */
	@Override
	public Person update(Person person) {
		// TODO Auto-generated method stub
		return this.nameAndGendersDAO.update(person);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.name.and.gender.INameAndGender#delete(vo.person.Person)
	 */
	@Override
	public Boolean delete(Person person) {
		// TODO Auto-generated method stub
		return this.nameAndGendersDAO.delete(person);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.name.and.gender.INameAndGender#search()
	 */
	@Override
	public Integer search() {
		// TODO Auto-generated method stub
		return this.nameAndGendersDAO.search();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.name.and.gender.INameAndGender#search(java.lang.Integer)
	 */
	@Override
	public Person search(Integer personCode) {
		// TODO Auto-generated method stub
		return this.nameAndGendersDAO.search(personCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.name.and.gender.INameAndGender#search(java.lang.String)
	 */
	@Override
	public List<Person> search(String name) {
		// TODO Auto-generated method stub
		return this.nameAndGendersDAO.search(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.name.and.gender.INameAndGender#personCode()
	 */
	@Override
	public Integer personCode() {
		// TODO Auto-generated method stub
		return this.nameAndGendersDAO.personCode();
	}

}
