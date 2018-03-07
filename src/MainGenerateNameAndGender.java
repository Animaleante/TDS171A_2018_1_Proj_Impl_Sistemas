import static persistence.Persistence.END_OF_THE_PROGRAMA;

import java.text.DecimalFormat;
import java.util.List;

import br.edu.uniopet.ConnectionFactory;
import br.edu.uniopet.Reader;
import model.ReadPersonDataFromFile;
import persistence.name.and.gender.NameAndGenderPersistence;
import vo.person.Person;

/**
 * Program to persistent the list of names and genders of the persons into the
 * database.
 * 
 * @author Baracho
 * 
 * @since November 27, 2017
 * 
 * @version 1.0
 *
 */
public class MainGenerateNameAndGender {

	final static String MENU = "NAMES AND GENDERS" + "\n 1. Read data from database"
			+ "\n 2. Generate data into the database" + "\n 3. Exit the program" + "\n Enter with your option: ";

	final static int CONST = 500;

	/**
	 * @param args
	 *            the args to set
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Variables declaration

		List<Person> persons = null;

		ReadPersonDataFromFile readPersonDataFromFile = null;

		NameAndGenderPersistence persistenceNameAndGender = null;

		int counter = 0;

		int option = 0;

		boolean flag = false;

		int i = 0;

		// Data processing

		persistenceNameAndGender = new NameAndGenderPersistence();

		do {

			flag = true;

			option = Reader.readInt(MENU);

			switch (option) {

			case 1: // Read data

				persons = persistenceNameAndGender.list();

				if (persons != null && persons.size() > 0) {

					counter = 1;

					System.out.print(String.format("\n %-8s %-20s %-45s %-20s %-1s", "Nº", "FIRST NAME", "MIDDLE NAME",
							"LAST NAME", "GENDER"));

					for (Person person : persons) {

						if (i != 0 && i % CONST == 0) {

							Reader.readCharacterNull("Digite uma tecla...");
						}

						System.out.print(String.format("\n %-8s %-20s %-45s %-20s %-1s",
								new DecimalFormat().format(counter++), person.getFirstName(), person.getMiddleName(),
								person.getLastName(), person.getGender().equals('M') ? "Male" : "Female"));

						i++;
					}

				} else {

					System.out.println("\n At this moment does not exist registered persons.");
				}

				break;

			case 2: // Generate data

				// Data input

				readPersonDataFromFile = new ReadPersonDataFromFile();

				persons = readPersonDataFromFile.getPersons();

				// Information output

				if (persons != null && persons.size() > 0) {

					counter = 1;

					for (Person person : persons) {

						person = persistenceNameAndGender.salve(person);

						if (person != null) {

							System.out.println(String.format("\n Counter: %d %s", counter++, person.toString()));
						}
					}
				}

				break;

			case 3: // Exit the program

				flag = false;

				break;
			}

		} while (flag == true);

		ConnectionFactory.closeConnection();

		System.err.println(String.format("\n %s", END_OF_THE_PROGRAMA));
	}
}
