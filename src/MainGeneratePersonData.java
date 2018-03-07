import static persistence.Persistence.END_OF_THE_PROGRAMA;

import java.text.DecimalFormat;

import br.edu.uniopet.Chronometer;
import model.GeneratePersonData;
import persistence.person.PersonPersistence;
import vo.person.Person;

/**
 * Program to generate the person data.
 * 
 * @author Baracho
 * 
 * @since 2017 nov, 21
 * 
 * @version 1.0
 *
 */
public class MainGeneratePersonData {

	/**
	 * @param args
	 *            the args to set
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Variables declaration

		final int MAX = 65000;

		GeneratePersonData generatePersonData = null;

		PersonPersistence persistencePersonData = null;

		Person person = null;

		long counter = 0L;

		DecimalFormat decimalFormat = new DecimalFormat();

		// Data processing

		Chronometer chronometer = new Chronometer("Generate Person Data");

		chronometer.start();

		generatePersonData = new GeneratePersonData();

		persistencePersonData = new PersonPersistence();

		for (long i = 0; i < MAX; i++) {

			person = generatePersonData.getPerson();

			if (person != null) {

				person = persistencePersonData.salve(person);

				System.err.println(decimalFormat.format(++counter));
			}
		}

		// Information output

		chronometer.stop();

		System.err.println(String.format("\n %s", END_OF_THE_PROGRAMA));
	}
}
