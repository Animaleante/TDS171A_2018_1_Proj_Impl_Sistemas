package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import vo.person.Person;

/**
 * Class to read from file the names and gender person.
 * 
 * @author Baracho
 * 
 * @since August 22th, 2015
 * 
 * @version 2.0
 * 
 */
public class ReadPersonDataFromFile {

	/**
	 * Instance variables
	 */

	private final String FILE_NAME = "resources\\NamesAndGendersList\\name_and_genders_list.txt";

	private List<Person> persons;

	/**
	 * Default constructor
	 */
	public ReadPersonDataFromFile() {

		this.readDataFromFile();
	}

	/**
	 * Access methods
	 */

	/**
	 * @return the persons
	 */
	public List<Person> getPersons() {
		return persons;
	}

	/**
	 * Class operations
	 */

	private void readDataFromFile() {

		// Variable statement

		String linha = null;

		String[] dados = null;

		Person person = null;

		String firstName = null;

		String middleName = null;

		String lastName = null;

		String gender = null;

		Scanner scanner = null;

		int size = 0;

		// Data processing

		scanner = openFile();

		if (scanner != null) {

			this.persons = new ArrayList<Person>();

			while (scanner.hasNext()) {

				linha = scanner.nextLine();

				dados = linha.split(";");

				size = dados.length;

				person = new Person();

				firstName = dados[0].trim();

				person.setFirstName(firstName);

				middleName = new String();

				for (int i = 1; i < (size - 2); i++) {

					middleName += dados[i].trim() + " ";
				}

				if (middleName != null && middleName.length() > 0) {

					middleName = middleName.trim();

					person.setMiddleName(middleName);
				}

				lastName = dados[size - 2].trim();

				person.setLastName(lastName);

				gender = dados[size - 1].trim();

				if (gender.equals("1")) {

					person.setGender(new Character('m'));

				} else {

					if (gender.equals("2")) {

						person.setGender(new Character('f'));
					}
				}

				this.persons.add(person);
			}

			closeFile(scanner);
		}
	}

	private Scanner openFile() {

		// Variables declaration

		Scanner scanner = null;

		// Data Processing

		try {

			scanner = new Scanner(new File(FILE_NAME));

			System.err.println("\n File opened with successful!");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

			System.err.println("Error to open file.");

			e.printStackTrace();
		}

		// Information output

		return scanner;
	}

	private void closeFile(Scanner scanner) {

		if (scanner != null) {

			scanner.close();

			System.err.println("\n File closed with successful!");

		} else {

			System.err.println("\n File is not opened.!");
		}
	}
}
