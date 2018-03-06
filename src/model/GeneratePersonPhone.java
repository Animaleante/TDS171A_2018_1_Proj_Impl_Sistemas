package model;

import java.util.Random;
import java.util.Set;

import persistence.phone.type.PhoneTypePersistence;
import vo.person.Person;
import vo.phone.Phone;
import vo.phone.type.PhoneType;

/**
 * Class to generate the person phone.
 * 
 * @author Baracho
 * 
 * @since 2017 dez, 12
 * 
 * @version 1.0
 *
 */
public class GeneratePersonPhone {

	/**
	 * Instance variables
	 */

	private final int MAX = 2054;

	private Person person;

	private PhoneTypePersistence persistencePhoneType;

	private Set<PhoneType> phoneTypes;

	/**
	 * Default constructor
	 */

	public GeneratePersonPhone() {
		// TODO Auto-generated constructor stub

		this.persistencePhoneType = new PhoneTypePersistence();

		this.phoneTypes = this.persistencePhoneType.list();
	}

	/**
	 * Access methods
	 */

	/**
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * @param person
	 *            the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	public void generatePersonPhone() {
		// TODO Auto-generated method stub

		// Variables declaration

		Phone phone = null;

		int value = 0;

		int number = 0;

		int order = 0;

		Random random = new Random();

		// Data processing

		/** ***************** RESIDENTIAL PHONE ****************** */

		value = random.nextInt();

		value %= MAX;

		if (value >= 0 && value < MAX) {

			phone = new Phone();

			phone.setPerson(this.person);

			for (PhoneType phoneType : this.phoneTypes) {

				if (phoneType.getType().equals("Residential")) {

					phone.setPhoneType(phoneType);

					break;
				}
			}

			number = random.nextInt(100);

			phone.setPrefixPhone(new Integer(number));

			order = 7;

			number = 3 * (int) Math.pow(10, order--);

			while (order >= 0) {

				number += random.nextInt(10) * (int) Math.pow(10, order--);
			}

			phone.setNumberPhone(new Integer(number));

			this.person.setPhone(phone);
		}

		/** ***************** COMMERCIAL PHONE ****************** */

		value = random.nextInt();

		value %= MAX;

		if (value >= 0 && value < MAX) {

			phone = new Phone();

			phone.setPerson(this.person);

			for (PhoneType phoneType : this.phoneTypes) {

				if (phoneType.getType().equals("Commercial")) {

					phone.setPhoneType(phoneType);

					break;
				}
			}

			number = random.nextInt(100);

			phone.setPrefixPhone(new Integer(number));

			order = 7;

			number = 3 * (int) Math.pow(10, order--);

			while (order >= 0) {

				number += random.nextInt(10) * (int) Math.pow(10, order--);
			}

			phone.setNumberPhone(new Integer(number));

			this.person.setPhone(phone);
		}

		/** ***************** SMARTPHONE PHONE ****************** */

		value = random.nextInt();

		value %= MAX;

		if (value >= 0 && value < MAX) {

			phone = new Phone();

			phone.setPerson(this.person);

			for (PhoneType phoneType : this.phoneTypes) {

				if (phoneType.getType().equals("Smartphone")) {

					phone.setPhoneType(phoneType);

					break;
				}
			}

			number = random.nextInt(100);

			phone.setPrefixPhone(new Integer(number));

			order = 8;

			number = 9 * (int) Math.pow(10, order--);

			while (order >= 0) {

				number += random.nextInt(10) * (int) Math.pow(10, order--);
			}

			phone.setNumberPhone(new Integer(number));

			this.person.setPhone(phone);
		}

		/** ***************** CONTACT PHONE ****************** */

		value = random.nextInt();

		value %= 2 * MAX;

		if (value >= 0 && value < MAX) {

			phone = new Phone();

			phone.setPerson(this.person);

			for (PhoneType phoneType : this.phoneTypes) {

				if (phoneType.getType().equals("Contact")) {

					phone.setPhoneType(phoneType);

					break;
				}
			}

			number = random.nextInt(100);

			phone.setPrefixPhone(new Integer(number));

			order = 7;

			number = 3 * (int) Math.pow(10, order--);

			while (order >= 0) {

				number += random.nextInt(10) * (int) Math.pow(10, order--);
			}

			phone.setNumberPhone(new Integer(number));

			this.person.setPhone(phone);
		}
	}
}
