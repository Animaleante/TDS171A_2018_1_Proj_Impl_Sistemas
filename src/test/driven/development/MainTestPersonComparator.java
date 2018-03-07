/**
 * 
 */
package test.driven.development;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import br.edu.uniopet.Reader;
import persistence.person.PersonPersistence;
import vo.person.Person;
import vo.person.PersonComparator;

/**
 * Program to test the PersonComparator class.
 * 
 * @author Baracho
 * 
 * @since janeiro 23, 2018
 * 
 * @version 1.0
 * 
 */
public class MainTestPersonComparator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Variables declarations

		final int CONST = 500;

		List<Person> persons = null;

		PersonPersistence persistencePerson = null;

		int counter = 0;

		// Data input

		persistencePerson = new PersonPersistence();

		persons = persistencePerson.listNames();

		// Data processing

		if (persons != null && persons.size() > 0) {

			Collections.sort(persons, new PersonComparator());
		}

		// Information output

		if (persons != null && persons.size() > 0) {

			counter = 1;

			System.out.println(String.format("\n %-8s %-15s %-20s %-45s %-20s", "Nº", "PERSON CODE", "FIRST NAME",
					"MIDDLE NAME", "LAST NAME"));

			for (Person person : persons) {

				if (counter != 0 && counter % CONST == 0) {

					Reader.readCharacterNull("Enter a key...");
				}

				System.out.print(String.format("\n %-8s %-15s %-20s %-45s %-20s", new DecimalFormat().format(++counter),
						new DecimalFormat().format(person.getPersonCode()), person.getFirstName(),
						person.getMiddleName(), person.getLastName()));
			}

		} else {

			System.out.println("\n At the moment does not exist registered person");
		}
	}
}
