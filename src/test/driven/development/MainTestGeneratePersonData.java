package test.driven.development;

import static persistence.Persistence.END_OF_THE_PROGRAMA;

import java.util.ArrayList;
import java.util.List;

import br.edu.uniopet.Chronometer;
import br.edu.uniopet.ConnectionFactory;
import model.GeneratePersonData;
import vo.person.Person;

/**
 * Program to test the generate of the list of persons.
 * 
 * @author Baracho
 * 
 * @since 2017 nov, 21
 * 
 * @version 1.0
 *
 */
public class MainTestGeneratePersonData {

	/**
	 * @param args
	 *            the args to set
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Variables declaration

		final int MAX = 10;

		List<Person> persons = null;

		Person person = null;

		GeneratePersonData generatePersonData = null;

		int counter = 0;

		// Data processing

		Chronometer chronometer = new Chronometer("Test Generate Person Data");

		chronometer.start();

		generatePersonData = new GeneratePersonData();

		persons = new ArrayList<Person>();

		System.err.println("\n Gerando os dados...");

		for (int i = 0; i < MAX; i++) {

			person = generatePersonData.getPerson();

			if (person != null) {

				persons.add(person);
			}
		}

		// Information output

		counter = 1;

		if (persons != null && persons.size() > 0) {

			for (Person people : persons) {

				System.out.println(String.format("\n Person #%d %s", counter++, people.toString()));
			}
		}

		chronometer.stop();

		ConnectionFactory.closeConnection();

		System.err.println(String.format("\n %s", END_OF_THE_PROGRAMA));
	}
}
