import static persistence.Persistence.END_OF_THE_PROGRAMA;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;

import br.edu.uniopet.CPF;
import br.edu.uniopet.ElectoralTitle;
import br.edu.uniopet.Email;
import br.edu.uniopet.RG;
import br.edu.uniopet.Reader;
import persistence.person.PersonPersistence;
import vo.address.Address;
import vo.person.Person;
import vo.person.PersonConstraints;
import vo.phone.Phone;
import vo.user.User;

/**
 * Program to implement enrollment of persons.
 * 
 * @author Baracho
 * 
 * @since October 14, 2017
 * 
 * @version 1.0
 *
 */
public class MainEnrollmentOfPersons {

	final static String MAIN_MENU = "REGISTRATION OF PERSON" + "\n 1. Enroll" + "\n 2. Consult" + "\n 3. Update"
			+ "\n 4. Delete" + "\n 5. Search" + "\n 6. Quit the program" + "\n Enter your option: ";

	final static String PERSON_MENU = "\n 1. First Name" + "\n 2. Middle Name" + "\n 3. Last Name"
			+ "\n 4. Date of Birth" + "\n 5. Gender" + "\n 6. RG" + "\n 7. CPF" + "\n 8. Electoral Title"
			+ "\n 9. E-mail: " + "\n 10. User" + "\n 11. Address " + "\n 12. Phone" + "\n 14. Return to the main menu"
			+ "\n Enter your option: ";

	final static int CONST = 500;

	static PersonPersistence personPersistence;

	static DecimalFormat decimalFormat;

	/**
	 * 
	 */
	/**
	 * @param args
	 *            the args to set
	 */
	public static void main(String[] args) {
		// TODO Auto-generated constructor stub

		decimalFormat = new DecimalFormat();

		personPersistence = new PersonPersistence();

		menu();

		System.out.println(String.format("\n %s", END_OF_THE_PROGRAMA));
	}

	/**
	 * Class operations
	 */
	private static void menu() {
		// TODO Auto-generated constructor stub

		// Variables declaration

		int option = 0;

		Character answer = null;

		Boolean flag = null;

		// Data processing

		do {

			flag = new Boolean(true);

			option = Reader.readInt(MAIN_MENU);

			switch (option) {

			case 1: // Enroll

				enroll();

				break;

			case 2: // Consult

				consult();

				break;

			case 3: // Update

				update();

				break;

			case 4: // Delete

				delete();

				break;

			case 5: // Search

				search();

				break;

			case 6: // Quit the program

				answer = Reader.readCharacter("Quit the program (Y/N)? ");

				answer = new Character(Character.toUpperCase(answer.charValue()));

				if (answer.equals('Y')) {

					flag = new Boolean(false);
				}

				break;
			}

		} while (flag.booleanValue());
	}

	private static void enroll() {

		// Variables declaration

		// Data processing

		inputData("ENROLL" + PERSON_MENU, null);
	}

	private static void consult() {

		// Variables declaration

		List<Person> persons = null;

		int counter = 0;

		// Data input

		persons = personPersistence.list();

		// Information output

		if (persons != null && persons.size() > 0) {

			System.out.println(String.format("\n %-8s %-15s %-20s %-45s %-20s %-16s", "N�", "PERSON CODE", "FIRST NAME",
					"MIDDLE NAME", "LAST NAME", "CPF"));

			for (Person person : persons) {

				if (counter != 0 && counter % CONST == 0) {

					Reader.readCharacterNull("Enter a key...");

					System.out.println(String.format("\n %-8s %-15s %-20s %-45s %-20s %-16s", "N�", "PERSON CODE",
							"FIRST NAME", "MIDDLE NAME", "LAST NAME", "CPF"));
				}

				System.out.print(
						String.format("\n %-8s %-15s %-20s %-45s %-20s %-16s", new DecimalFormat().format(++counter),
								new DecimalFormat().format(person.getPersonCode()), person.getFirstName(),
								person.getMiddleName(), person.getLastName(), person.getCpf().getValueString()));
			}

		} else {

			System.out.println("\n At this moment there are not persons enrolled.");
		}
	}

	private static void update() {

		// Variables declaration

		Person person;

		Integer personCode;

		PersonConstraints personConstraints = null;

		// Data input

		personCode = Reader.readIntNull("Enter the Person Code: ");

		personConstraints = new PersonConstraints();

		personConstraints.setId_tb_Person(personCode);

		person = personPersistence.search(personConstraints);

		if (person != null) {

			inputData("UPDATE" + PERSON_MENU, person);
		}
	}

	private static void delete() {

		// Variables declaration

		Person person = null;

		PersonConstraints personConstraints = null;

		PersonPersistence personPersistence = null;

		Integer personCode;

		Character answer = null;

		// Data input

		personCode = Reader.readIntNull("Enter the Person Code: ");

		personConstraints = new PersonConstraints();

		personConstraints.setId_tb_Person(personCode);

		personPersistence = new PersonPersistence();

		person = personPersistence.search(personConstraints);

		if (person != null) {

			inputData("UPDATE" + PERSON_MENU, person);

			System.out.println(person.toString());

			answer = Reader.readCharacter("\n Confirm deletion (Y/N)? ");

			answer = new Character(Character.toUpperCase(answer.charValue()));

			if (answer.equals('Y')) {

				personPersistence.delete(person);
			}
		}
	}

	private static void search() {

		// Variables declaration

		String name = null;

		List<Person> persons = null;

		int counter = 0;

		// Data processing

		name = Reader.readString("Enter a name: ");

		persons = personPersistence.search(name);

		if (persons != null && persons.size() > 0) {

			counter = 1;

			System.out.println(String.format("\n %-8s %-15s %-20s %-45s %-20s %-16s", "N�", "PERSON CODE", "FIRST NAME",
					"MIDDLE NAME", "LAST NAME", "CPF"));

			for (Person person : persons) {

				if (counter != 0 && counter % CONST == 0) {

					Reader.readCharacterNull("Enter a key...");

					System.out.println(String.format("\n %-8s %-15s %-20s %-45s %-20s %-16s", "N�", "PERSON CODE",
							"FIRST NAME", "MIDDLE NAME", "LAST NAME", "CPF"));
				}

				System.out.print(
						String.format("\n %-8s %-15s %-20s %-45s %-20s %-16s", new DecimalFormat().format(++counter),
								new DecimalFormat().format(person.getPersonCode()), person.getFirstName(),
								person.getMiddleName(), person.getLastName(), person.getCpf().getValueString()));
			}

		} else {

			System.out.printf("\n No occurrences for %s \n", name.toUpperCase());
		}
	}

	private static void inputData(String msg, Person person) {

		// Variables declaration

		Integer option = null;

		Character answer = null;

		Boolean flag = null;

		// Input data

		option = Reader.readInt(msg);

		if (person == null) {

			person = new Person();
		}

		do {

			flag = new Boolean(true);

			System.out.println(person.toString());

			switch (option) {

			case 1: // First Name

				person.setFirstName(Reader.readString("Enter the First Name: "));

				break;

			case 2: // Middle Name

				person.setMiddleName(Reader.readStringNull("Enter the Middle Name: "));

				break;

			case 3: // Last Name

				person.setLastName(Reader.readStringNull("Enter the Last Name: "));

				break;

			case 4: // Date of Birth

				person.getDateOfBirth().setValue(Reader.readDateNull("Enter the Date of Birth: "));

				break;

			case 5: // Gender

				person.setGender(Reader.readCharacterNull("Enter the Gender: "));

				break;

			case 6: // RG

				answer = Reader.readCharacter("Generate the RG (Y/N)? ");

				answer = new Character(Character.toUpperCase(answer.charValue()));

				if (answer.equals('Y')) {

					RG rg = new RG();

					rg.generateRG();

					person.setRg(rg);

				} else {

					person.setRg(null);
				}

				break;

			case 7: // CPF

				answer = Reader.readCharacter("Generate the CPF (Y/N)? ");

				answer = new Character(Character.toUpperCase(answer.charValue()));

				if (answer.equals('Y')) {

					CPF cpf = new CPF();

					cpf.generateCPF();

					person.setCpf(cpf);

				} else {

					person.setCpf(null);
				}

				break;

			case 8: // Electoral Title

				answer = Reader.readCharacter("Generate the Electoral Title (Y/N)? ");

				answer = new Character(Character.toUpperCase(answer.charValue()));

				if (answer.equals('Y')) {

					ElectoralTitle electoralTitle = new ElectoralTitle();

					electoralTitle.generateElectoralTitle();

					person.setElectoralTitle(electoralTitle);

				} else {

					person.setElectoralTitle(null);
				}

				break;

			case 9: // E-mail

				Email email = new Email();

				email.setValue(Reader.readStringNull("E-mail: "));

				person.setEmail(email);

				break;

			case 10: // User

				User user = userMenu();

				person.setUser(user);

				break;

			case 11: // Address

				Set<Address> addresses = addressMenu();

				person.setAddresses(addresses);

			case 12: // Phone

				Set<Phone> phones = phoneMenu();

				person.setPhones(phones);

				break;

			case 13:

				answer = Reader.readCharacter("Save the data (Y/N)? ");

				answer = new Character(Character.toUpperCase(answer.charValue()));

				if (answer.equals('Y')) {

					if (person.getFirstName() != null && !person.getFirstName().equals("")) {

						if (person.getPersonCode() != null) {

							person = personPersistence.salve(person);

						} else {

							person = personPersistence.update(person);
						}
					}
				}

				if (person != null) {

					System.out.println(person.toString());
				}

				flag = new Boolean(false);
			}

		} while (flag.equals(true));
	}

	private static User userMenu() {

		return null;
	}

	private static Set<Address> addressMenu() {
		return null;
	}

	private static Set<Phone> phoneMenu() {
		return null;
	}

}
