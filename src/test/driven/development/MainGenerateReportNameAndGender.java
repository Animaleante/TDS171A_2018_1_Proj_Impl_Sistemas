package test.driven.development;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.List;

import model.ReadPersonDataFromFile;
import vo.person.Person;

/**
 * Program to generate the report of the names and gender.
 * 
 * @author Baracho
 * 
 * @since 2017 dez, 30
 * 
 * @version 1.0
 *
 */
public class MainGenerateReportNameAndGender {

	private static Formatter formatter;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Variables declaration

		List<Person> persons = null;

		ReadPersonDataFromFile readPersonDataFromFile = null;

		// Data processing

		readPersonDataFromFile = new ReadPersonDataFromFile();

		persons = readPersonDataFromFile.getPersons();

		generateFile(persons);

		// Information output

		System.err.println("\n END OF THE PROGRAM.");
	}

	public static void generateFile(List<Person> persons) {

		int counter = 0;

		openFile();

		if (formatter != null) {

			if (persons != null && persons.size() > 0) {

				counter = 1;

				formatter.format("\n %-8s %-20s %-45s %-20s %-1s", "Nº", "FIRST NAME", "MIDDLE NAME", "LAST NAME",
						"GENDER");

				for (Person person : persons) {

					formatter.format("\n %-8s %-20s %-45s %-20s %-1s", new DecimalFormat().format(counter++),
							person.getFirstName(), person.getMiddleName(), person.getLastName(),
							person.getGender().equals('M') ? "Male" : "Female");
				}

			} else {

				System.out.println("\n At the moment does not exist registered person");
			}
		}

		closeFile();
	}

	private static void openFile() {

		final String FILE_NAME = "resources\\NamesAndGendersList\\report_name_and_gender.txt";

		try {

			formatter = new Formatter(new File(FILE_NAME));

			System.err.println("\n Output File opened with successful!");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

			System.err.println("Error to open file.");

			e.printStackTrace();
		}
	}

	private static void closeFile() {

		if (formatter != null) {

			formatter.close();

			System.err.println("\n Output File closed with successful!");

		} else {

			System.err.println("\n File is not opened.!");
		}
	}
}
