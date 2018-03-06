package model;

import java.util.List;
import java.util.Random;

import br.edu.uniopet.CPF;
import br.edu.uniopet.ElectoralTitle;
import br.edu.uniopet.Email;
import br.edu.uniopet.PersonDate;
import br.edu.uniopet.RG;
import persistence.name.and.gender.NameAndGenderPersistence;
import persistence.permission.PermissionPersistence;
import persistence.person.PersonPersistence;
import persistence.user.UserPersistence;
import vo.permission.PermitionConstraints;
import vo.permission.Permission;
import vo.person.Person;
import vo.user.User;

/**
 * Class to generate the person data.
 * 
 * @author Baracho
 * 
 * @since August 22th, 2015
 * 
 * @version 2.0
 * 
 */
public class GeneratePersonData {

	/**
	 * Instance variables
	 */

	private Person person;

	private List<Person> persons;

	private PersonPersistence persistencePerson;

	private GeneratePersonAddress generatePersonAddress;

	private GeneratePersonPhone generatePersonPhone;

	private PermissionPersistence persistencePermission;

	private Permission permission;

	private UserPersistence persistenceUser;

	/**
	 * Default constructor
	 */

	public GeneratePersonData() {

		this.readPersonDateFromDatabase();

		this.persistencePerson = new PersonPersistence();

		this.generatePersonAddress = new GeneratePersonAddress();

		this.generatePersonPhone = new GeneratePersonPhone();

		this.persistencePermission = new PermissionPersistence();

		PermitionConstraints constraintsPermition = new PermitionConstraints();

		constraintsPermition.setId_tb_Permition(new Integer(3));

		this.permission = this.persistencePermission.search(constraintsPermition);

		this.persistenceUser = new UserPersistence();
	}

	/**
	 * Access methods
	 */

	/**
	 * @return the person
	 */
	public Person getPerson() {

		do {

			this.generatePersonData();

		} while (this.person == null);

		return this.person;
	}

	/**
	 * Class operations
	 */

	private void readPersonDateFromDatabase() {

		// Variables declaration

		NameAndGenderPersistence persistenceNameAndGender = null;

		// Data processing

		persistenceNameAndGender = new NameAndGenderPersistence();

		this.persons = persistenceNameAndGender.list();
	}

	public void generatePersonData() {

		// Variable statement

		PersonDate personDate = null;

		RG rg = null;

		CPF cpf = new CPF();

		ElectoralTitle tituloEleitoral = null;

		Email email = null;

		Random random = new Random();

		int index = 0;

		int value = 0;

		boolean flag = false;

		// Data processing

		this.person = null;

		if (this.persons != null && this.persons.size() > 0) {

			index = random.nextInt(persons.size());

			if (index >= 0 && index < this.persons.size()) {

				this.person = new Person();

				/** ***************** FIRST NAME ****************** */

				this.person.setFirstName(this.persons.get(index).getFirstName());

				/** ***************** GENDER ****************** */

				value = generateIndex();

				value %= this.persons.size();

				if (value >= 0 && value < this.persons.size()) {

					this.person.setGender(this.persons.get(index).getGender());
				}

				/** ***************** MIDDLE NAME ****************** */

				value = generateIndex();

				value %= 2 * this.persons.size();

				if (value >= 0 && value < this.persons.size()) {

					this.person.setMiddleName(persons.get(value).getMiddleName());
				}

				/** ***************** LAST NAME ****************** */

				value = generateIndex();

				value %= 2 * this.persons.size();

				if (value >= 0 && value < this.persons.size()) {

					this.person.setLastName(persons.get(value).getLastName());
				}

				/** ***************** DATE OF BIRTH ****************** */

				value = generateIndex();

				value %= this.persons.size();

				if (value >= 0 && value < this.persons.size()) {

					personDate = new PersonDate();

					personDate.generateDate();

					this.person.setDateOfBirth(personDate);
				}

				/** ***************** RG ****************** */

				value = generateIndex();

				value %= 2 * this.persons.size();

				if (value >= 0 && value < this.persons.size()) {

					rg = new RG();

					rg.generateRG();

					this.person.setRg(rg);
				}

				/** ***************** CPF ****************** */

				value = generateIndex();

				value %= 2 * this.persons.size();

				if (value >= 0 && value < this.persons.size()) {

					cpf = new CPF();

					do {

						flag = false;

						cpf.generateCPF();

						flag = this.persistencePerson.validateCPF(cpf).booleanValue();

					} while (flag);

					this.person.setCpf(cpf);
				}

				/** ***************** ELECTORAL TITLE ****************** */

				value = generateIndex();

				value %= 2 * this.persons.size();

				if (value >= 0 && value < this.persons.size()) {

					tituloEleitoral = new ElectoralTitle();

					tituloEleitoral.generateElectoralTitle();

					this.person.setElectoralTitle(tituloEleitoral);
				}

				/** ***************** E-MAIL ****************** */

				value = generateIndex();

				value %= this.persons.size();

				if (value >= 0 && value < this.persons.size()) {

					email = new Email();

					email.setFirstPart(person.getFirstName());

					email.setSecondPart(person.getLastName());

					email.generateEmail();

					this.person.setEmail(email);
				}

				/** ***************** STATUS ****************** */

				value = generateIndex();

				value %= this.persons.size();

				if (value >= 0 && value < this.persons.size()) {

					Boolean status = null;

					random = new Random();

					int i = random.nextInt(3);

					if (i == 1) {

						status = new Boolean(true);
					}

					if (i == 2) {

						status = new Boolean(false);
					}

					this.person.setStatus(status);
				}

				/** ***************** USER ****************** */

				User user = new User();

				String firstName = null;

				String lastName = null;

				String login = null;

				int beginIndex = 0;

				flag = false;

				firstName = person.getFirstName().toLowerCase();

				firstName = this.convertToASCII(firstName);

				if (this.person.getLastName() != null && !this.person.getLastName().equals("")) {

					lastName = this.person.getLastName().toLowerCase();

					if (lastName.contains(" ")) {

						beginIndex = lastName.indexOf(" ");

						beginIndex++;

						lastName = lastName.substring(beginIndex);
					}

					lastName = this.convertToASCII(lastName);
				}

				login = this.generateLogin(firstName, lastName);

				user.setLogin(login);

				if (lastName != null && !lastName.equals("")) {

					user.setPassword(lastName);

				} else {

					user.setPassword(firstName);
				}

				user.setPermission(this.permission);

				this.person.setUser(user);

				/** ***************** ADDRESS ****************** */

				this.generatePersonAddress.setPerson(this.person);

				this.generatePersonAddress.generatePersonAddress();

				this.person = this.generatePersonAddress.getPerson();

				/** ***************** PHONE ****************** */

				this.generatePersonPhone.setPerson(this.person);

				this.generatePersonPhone.generatePersonPhone();

				this.person = this.generatePersonPhone.getPerson();
			}
		}
	}

	private int generateIndex() {

		// Variable statement

		int index = 0;

		Random random = new Random();

		// Data processing

		index = Math.abs(random.nextInt());

		// Information output

		return index;
	}

	public String generateLogin(String firstName, String lastName) {

		// Variables declaration

		String login = null;

		int number = 0;

		int length = 0;

		boolean flag = false;

		Random random = new Random();

		// Data processing

		if (lastName != null && !lastName.equals("")) {

			login = lastName;

			flag = this.persistenceUser.validateLogin(login).booleanValue();

			if (flag) {

				login += firstName.toUpperCase();
			}

		} else {

			login = firstName;

			flag = this.persistenceUser.validateLogin(login).booleanValue();

			if (flag) {

				login += firstName.toUpperCase();
			}
		}

		flag = this.persistenceUser.validateLogin(login);

		if (flag) {

			do {

				length = login.length();

				number = random.nextInt(1000);

				if (number % 2 == 0) {

					number %= length;

				} else {

					number %= 10;
				}

				login += number;

				flag = persistenceUser.validateLogin(login);

				if (flag) {

					number = Math.abs(this.person.hashCode());

					number /= 100;

					login += number;

					flag = persistenceUser.validateLogin(login);
				}

			} while (flag);
		}

		// Information output

		return login;
	}

	public String convertToASCII(String value) {

		if (value != null && !value.equals("")) {

			value = value.replaceAll("[ãâàáä]", "a");

			value = value.replaceAll("[êèéë]", "e");

			value = value.replaceAll("[îìíï]", "i");

			value = value.replaceAll("[õôòóö]", "o");

			value = value.replaceAll("[ûúùü]", "u");

			value = value.replaceAll("[ÃÂÀÁÄ]", "A");

			value = value.replaceAll("[ÊÈÉË]", "E");

			value = value.replaceAll("[ÎÌÍÏ]", "I");

			value = value.replaceAll("[ÕÔÒÓÖ]", "O");

			value = value.replaceAll("[ÛÙÚÜ]", "U");

			value = value.replace('ç', 'c');

			value = value.replace('Ç', 'C');

			value = value.replace('ñ', 'n');

			value = value.replace('Ñ', 'N');
		}

		// Information output

		return value;
	}
}
