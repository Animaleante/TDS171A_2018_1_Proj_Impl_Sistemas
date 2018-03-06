package model;

import java.util.List;
import java.util.Random;
import java.util.Set;

import persistence.address.type.AddressTypePersistence;
import persistence.cep.database.StreetPersistence;
import vo.address.Address;
import vo.address.type.AddressType;
import vo.cep.StreetConstraints;
import vo.cep.Street;
import vo.person.Person;

/**
 * Class to generate the person address.
 * 
 * @author Baracho
 * 
 * @since 2017 dez, 12
 * 
 * @version 1.0
 *
 */
public class GeneratePersonAddress {

	/**
	 * Instance variables
	 */

	private Person person;

	private AddressTypePersistence persistenceAddressType;

	private Set<AddressType> addressTypes;

	private StreetPersistence persistenceStreet;

	private List<StreetConstraints> constraintsStreets;

	private int size;

	/**
	 * Default constructor
	 */

	public GeneratePersonAddress() {
		// TODO Auto-generated constructor stub

		this.persistenceAddressType = new AddressTypePersistence();

		this.addressTypes = persistenceAddressType.list();

		this.persistenceStreet = new StreetPersistence();

		this.constraintsStreets = persistenceStreet.searchStreetConstraints();

		this.size = this.constraintsStreets.size();
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

	public void generatePersonAddress() {
		// TODO Auto-generated method stub

		// Variables declaration

		int value = 0;

		StreetConstraints constraintsStreet = null;

		Address address = null;

		String[] complement = null;

		int number = 0;

		Random random = new Random();

		// Data processing

		value = random.nextInt();

		value %= this.size;

		if (value >= 0 && value < this.size) {

			constraintsStreet = this.constraintsStreets.get(value);

			Street street = persistenceStreet.search(constraintsStreet);

			List<StreetConstraints> constraintsStreetsOfCities = persistenceStreet
					.search(street.getDistrict().getCity());

			/** ***************** RESIDENTIAL ADDRESS ****************** */

			value = random.nextInt();

			value %= constraintsStreetsOfCities.size();

			if (value >= 0 && value < constraintsStreetsOfCities.size()) {

				address = new Address();

				constraintsStreet = constraintsStreetsOfCities.get(value);

				street = persistenceStreet.search(constraintsStreet);

				address.setStreet(street);

				address.setPerson(person);

				for (AddressType addressType : this.addressTypes) {

					if (addressType.getType().equals("Residential")) {

						address.setAddressType(addressType);

						break;
					}
				}

				number = random.nextInt(5000) + 1;

				address.setAddressNumber(number);

				complement = new String[3];

				complement[0] = "Casa";

				complement[1] = "Sobrado";

				number = random.nextInt(100) + 1;

				complement[2] = "Apto. " + number;

				number = random.nextInt(3);

				address.setComplement(complement[number]);

				person.setAddress(address);
			}

			/** ***************** COMMERCIAL ADDRESS ****************** */

			value = random.nextInt();

			value %= constraintsStreetsOfCities.size();

			if (value >= 0 && value < constraintsStreetsOfCities.size()) {

				address = new Address();

				constraintsStreet = constraintsStreetsOfCities.get(value);

				street = persistenceStreet.search(constraintsStreet);

				address.setStreet(street);

				address.setPerson(person);

				for (AddressType addressType : this.addressTypes) {

					if (addressType.getType().equals("Commercial")) {

						address.setAddressType(addressType);

						break;
					}
				}

				number = random.nextInt(5000) + 1;

				address.setAddressNumber(number);

				complement = new String[2];

				complement[0] = "Térreo";

				number = random.nextInt(1000) + 1;

				complement[1] = "Conjunto " + number;

				number = random.nextInt(2);

				address.setComplement(complement[number]);

				person.setAddress(address);
			}
		}
	}
}
