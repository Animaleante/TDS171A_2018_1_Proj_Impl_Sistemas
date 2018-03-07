/**
 * 
 */
package controller.person;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import persistence.person.PersonPersistence;
import vo.person.Person;

/**
 * Backing Class Class to support the JSF pages to the Person entity.
 * @author opet
 * 
 * @since março 06, 2018
 * 
 * @version 1.0
 *
 */
@Named("personBean")
@RequestScoped
public class PersonBean implements Serializable {

	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 4651395576987524210L;
	
	/**
	 * Instance Variables
	 */
	
	private Person person;
	
	/**
	 * Default constructor
	 */
	public PersonBean() {
		this.setPerson(new Person());
	}
	
	/**
	 * Access Methods
	 */

	/**
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}
	
	/**
	 * Class Operations
	 */
	
	public List<Person> getPersons() {
		List<Person> persons = null;
		PersonPersistence personPersistence = new PersonPersistence();
		persons = personPersistence.listNames();
		return persons;
	}
	
	public String homepage() {
		this.person = new Person();
		return "/pages/homepage";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((person == null) ? 0 : person.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PersonBean)) {
			return false;
		}
		PersonBean other = (PersonBean) obj;
		if (person == null) {
			if (other.person != null) {
				return false;
			}
		} else if (!person.equals(other.person)) {
			return false;
		}
		return true;
	}
}
