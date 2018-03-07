package test.driven.development;

import static persistence.Persistence.END_OF_THE_PROGRAMA;

import java.util.List;

import br.edu.uniopet.Chronometer;
import br.edu.uniopet.ConnectionFactory;
import br.edu.uniopet.Reader;
import model.ReadPersonDataFromFile;
import vo.person.Person;

/**
 * Program to test the read the person data from the file.
 * 
 * @author Baracho
 * 
 * @since 2017 nov, 21
 * 
 * @version 1.0
 *
 */
public class MainTestReadPersonDataFromFile {

	/**
	 * @param args
	 *            the args to set
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Variables declaration

		final int CONST = 500;

		ReadPersonDataFromFile readPersonDataFromFile = null;

		List<Person> persons = null;

		int counter = 0;

		Chronometer chronometer = null;

		// Data input

		chronometer = new Chronometer("Test Read Person Data From File");

		chronometer.start();

		readPersonDataFromFile = new ReadPersonDataFromFile();

		persons = readPersonDataFromFile.getPersons();

		// Information output

		if (persons != null && persons.size() > 0) {

			System.out.printf("\n %-8s %-20s %-40s %-20s %-1s", "Nº", "FIRST NAME", "MIDDLE NAME", "LAST NAME",
					"GENDER");

			counter = 1;

			for (Person person : persons) {

				if (counter != 0 && counter % CONST == 0) {

					Reader.readCharacterNull("Digite uma tecla...");

					System.out.printf("\n %-8s %-20s %-40s %-20s %-1s", "Nº", "FIRST NAME", "MIDDLE NAME", "LAST NAME",
							"GENDER");

				}

				System.out.printf("\n %-8d %-20s %-40s %-20s %-1s", counter++, person.getFirstName(),
						person.getMiddleName(), person.getLastName(), person.getGender());
			}

			System.out.println();
		}

		chronometer.stop();

		ConnectionFactory.closeConnection();

		System.err.printf("\n %s", END_OF_THE_PROGRAMA);
	}
}
