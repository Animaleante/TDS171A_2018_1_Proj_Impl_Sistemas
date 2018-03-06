package infrastructure;

import static persistence.Persistence.END_OF_THE_PROGRAMA;
import static persistence.Persistence.SAVE_NOT_SUCCESS;

import java.util.HashSet;
import java.util.Set;

import br.edu.uniopet.CPF;
import br.edu.uniopet.ConnectionFactory;
import br.edu.uniopet.ElectoralTitle;
import br.edu.uniopet.Email;
import br.edu.uniopet.PersonDate;
import br.edu.uniopet.RG;
import br.edu.uniopet.Reader;
import persistence.address.AddressPersistence;
import persistence.address.type.AddressTypePersistence;
import persistence.cep.database.StreetPersistence;
import persistence.permission.PermissionPersistence;
import persistence.person.PersonPersistence;
import persistence.phone.PhonePersistence;
import persistence.phone.type.PhoneTypePersistence;
import vo.address.Address;
import vo.address.type.AddressType;
import vo.address.type.AddressTypeConstraints;
import vo.cep.StreetConstraints;
import vo.cep.Street;
import vo.permission.PermitionConstraints;
import vo.permission.Permission;
import vo.person.Person;
import vo.phone.Phone;
import vo.phone.type.PhoneTypeConstraints;
import vo.phone.type.PhoneType;
import vo.user.User;

/**
 * Program to create the infrastructure of the system.
 * 
 * @author Baracho
 * 
 * @since 2017 dez, 12
 * 
 * @version 1.0
 *
 */
public class MainCreateInfrastructureOfTheSystem {

	/**
	 * @param args
	 *            the args to set
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Data processing

		createPermission();

		createAddressType();

		createPhoneType();

		createAdministratorSystem();

		createManagerSystem();

		// Information output

		ConnectionFactory.closeConnection();

		System.err.println(String.format("\n %s", END_OF_THE_PROGRAMA));

	}

	public static void createPermission() {

		// Variables declaration

		Permission permission = null;

		PermissionPersistence persistencePermission = null;

		// Data input

		persistencePermission = new PermissionPersistence();

		permission = new Permission();

		permission.setPermission("ROLE_ADMINISTRATOR");

		permission = persistencePermission.save(permission);

		if (permission != null) {

			System.out.println(permission.toString());

		} else {

			System.out.println(String.format("\n %s %s", "Create Permission", SAVE_NOT_SUCCESS));
		}

		permission = new Permission();

		permission.setPermission("ROLE_MANAGER");

		permission = persistencePermission.save(permission);

		if (permission != null) {

			System.out.println(permission.toString());

		} else {

			System.out.println(String.format("\n %s %s", "Create Permission", SAVE_NOT_SUCCESS));
		}

		permission = new Permission();

		permission.setPermission("ROLE_USER");

		permission = persistencePermission.save(permission);

		if (permission != null) {

			System.out.println(permission.toString());

		} else {

			System.out.println(String.format("\n %s %s", "Create Permission", SAVE_NOT_SUCCESS));
		}
	}

	public static void createAddressType() {

		// Variables declaration

		AddressType addressType = null;

		AddressTypePersistence persistenceAddressType = null;

		// Data input

		persistenceAddressType = new AddressTypePersistence();

		addressType = new AddressType();

		addressType.setType("Residential");

		addressType = persistenceAddressType.salve(addressType);

		if (addressType != null) {

			System.out.println(addressType.toString());

		} else {

			System.out.println(String.format("\n %s %s", "Create Address Type", SAVE_NOT_SUCCESS));
		}

		addressType = new AddressType();

		addressType.setType("Commercial");

		addressType = persistenceAddressType.salve(addressType);

		if (addressType != null) {

			System.out.println(addressType.toString());

		} else {

			System.out.println(String.format("\n %s %s", "Create Address Type", SAVE_NOT_SUCCESS));
		}
	}

	public static void createPhoneType() {

		// Variables declaration

		PhoneType phoneType = null;

		PhoneTypePersistence persistencePhoneType = null;

		// Data input

		persistencePhoneType = new PhoneTypePersistence();

		phoneType = new PhoneType();

		phoneType.setType("Residential");

		phoneType = persistencePhoneType.salve(phoneType);

		if (phoneType != null) {

			System.out.println(phoneType.toString());

		} else {

			System.out.println(String.format("\n %s %s", "Create Phone Type", SAVE_NOT_SUCCESS));
		}

		phoneType = new PhoneType();

		phoneType.setType("Commercial");

		phoneType = persistencePhoneType.salve(phoneType);

		if (phoneType != null) {

			System.out.println(phoneType.toString());

		} else {

			System.out.println(String.format("\n %s %s", "Create Phone Type", SAVE_NOT_SUCCESS));
		}

		phoneType = new PhoneType();

		phoneType.setType("Smartphone");

		phoneType = persistencePhoneType.salve(phoneType);

		if (phoneType != null) {

			System.out.println(phoneType.toString());

		} else {

			System.out.println(String.format("\n %s %s", "Create Phone Type", SAVE_NOT_SUCCESS));
		}

		phoneType = new PhoneType();

		phoneType.setType("Contact");

		phoneType = persistencePhoneType.salve(phoneType);

		if (phoneType != null) {

			System.out.println(phoneType.toString());

		} else {

			System.out.println(String.format("\n %s %s", "Create Phone Type", SAVE_NOT_SUCCESS));
		}
	}

	public static void createAdministratorSystem() {

		// Data input

		/* ************** PERSON *************************** */

		Person person = null;

		PersonDate personDate = new PersonDate();

		RG rg = null;

		CPF cpf = null;

		ElectoralTitle electoralTitle = null;

		Email email = null;

		person = new Person();

		person.setFirstName("maria");

		person.setMiddleName("garcia");

		person.setLastName("da silva");

		personDate = new PersonDate();

		try {

			personDate.setValue(Reader.converterStringToDate("31/12/1935"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (personDate != null) {

			person.setDateOfBirth(personDate);
		}

		person.setGender(new Character('f'));

		rg = new RG();

		rg.generateRG();

		person.setRg(rg);

		cpf = new CPF();

		cpf.generateCPF();

		person.setCpf(cpf);

		electoralTitle = new ElectoralTitle();

		electoralTitle.generateElectoralTitle();

		person.setElectoralTitle(electoralTitle);

		email = new Email();

		email.setFirstPart(person.getFirstName());

		email.setSecondPart(person.getLastName());

		email.generateEmail();

		person.setEmail(email);

		person.setStatus(new Boolean(true));

		/* ************** USER *************************** */

		User user = null;

		Permission permission = null;

		PermitionConstraints constraintsPermition = null;

		Set<Permission> permissions = null;

		PermissionPersistence persistencePermission = null;

		user = new User();

		user.setLogin("maria");

		user.setPassword("silva");

		persistencePermission = new PermissionPersistence();

		permissions = new HashSet<Permission>();

		constraintsPermition = new PermitionConstraints();

		constraintsPermition.setId_tb_Permition(new Integer(1));

		permission = persistencePermission.search(constraintsPermition);

		permissions.add(permission);

		constraintsPermition = new PermitionConstraints();

		constraintsPermition.setId_tb_Permition(new Integer(2));

		permission = persistencePermission.search(constraintsPermition);

		permissions.add(permission);

		constraintsPermition = new PermitionConstraints();

		constraintsPermition.setId_tb_Permition(new Integer(3));

		permission = persistencePermission.search(constraintsPermition);

		permissions.add(permission);

		user.setPermissions(permissions);

		person.setUser(user);

		/* ************** PERSON PERSISTENCE *************************** */

		PersonPersistence persistencePerson = null;

		persistencePerson = new PersonPersistence();

		person = persistencePerson.salve(person);

		/* ************** ADDRESS *************************** */

		Address address = null;

		AddressType addressType = null;

		AddressTypeConstraints constraintsAddressType = null;

		AddressTypePersistence persistenceAddressType = null;

		Street street = null;

		StreetConstraints constraintsStreet = null;

		StreetPersistence persistenceStreet = null;

		Set<Address> addresses = null;

		addresses = new HashSet<Address>();

		persistenceStreet = new StreetPersistence();

		if (person != null) {

			// Residential Address

			address = new Address();

			address.setPerson(person);

			constraintsStreet = new StreetConstraints();

			constraintsStreet.setCD_LOGRADOURO(new Integer(291974));

			constraintsStreet.setNO_LOGRADOURO_CEP(new Integer(80620080));

			street = persistenceStreet.search(constraintsStreet);

			address.setStreet(street);

			constraintsAddressType = new AddressTypeConstraints();

			constraintsAddressType.setId_tb_address_type(new Integer(1));

			persistenceAddressType = new AddressTypePersistence();

			addressType = persistenceAddressType.search(constraintsAddressType);

			address.setAddressType(addressType);

			address.setAddressNumber(new Integer(100));

			address.setComplement("Apto. 505");

			addresses.add(address);

			// Commercial Address

			address = new Address();

			address.setPerson(person);

			constraintsStreet = new StreetConstraints();

			constraintsStreet.setCD_LOGRADOURO(new Integer(291581));

			constraintsStreet.setNO_LOGRADOURO_CEP(new Integer(80230030));

			persistenceStreet = new StreetPersistence();

			street = persistenceStreet.search(constraintsStreet);

			address.setStreet(street);

			constraintsAddressType = new AddressTypeConstraints();

			constraintsAddressType.setId_tb_address_type(new Integer(2));

			persistenceAddressType = new AddressTypePersistence();

			addressType = persistenceAddressType.search(constraintsAddressType);

			address.setAddressType(addressType);

			address.setAddressNumber(new Integer(892));

			address.setComplement("Térreo");

			addresses.add(address);

			person.setAddresses(addresses);

			/* ************** ADDRESS PERSISTENCE *************************** */

			AddressPersistence persistenceAddress = new AddressPersistence();

			addresses = persistenceAddress.salve(person);

			if (addresses != null && addresses.size() > 0) {

				person.setAddresses(addresses);

			} else {

				person.setAddresses(null);
			}
		}

		/* ************** PHONE *************************** */

		Phone phone = null;

		PhoneType phoneType = null;

		PhoneTypeConstraints constraintsPhoneType = null;

		Set<Phone> phones = new HashSet<Phone>();

		PhoneTypePersistence persistencePhoneType = new PhoneTypePersistence();

		if (person != null) {

			// Residential phone

			phone = new Phone();

			phone.setPerson(person);

			phone.setPrefixPhone(new Integer(41));

			phone.setNumberPhone(new Integer(30415161));

			constraintsPhoneType = new PhoneTypeConstraints();

			constraintsPhoneType.setId_tb_Phone_Type(new Integer(1));

			phoneType = persistencePhoneType.search(constraintsPhoneType);

			phone.setPhoneType(phoneType);

			phones.add(phone);

			// Commercial phone

			phone = new Phone();

			phone.setPerson(person);

			phone.setPrefixPhone(new Integer(41));

			phone.setNumberPhone(new Integer(30300708));

			constraintsPhoneType = new PhoneTypeConstraints();

			constraintsPhoneType.setId_tb_Phone_Type(new Integer(2));

			phoneType = persistencePhoneType.search(constraintsPhoneType);

			phone.setPhoneType(phoneType);

			phones.add(phone);

			// Smart phone

			phone = new Phone();

			phone.setPerson(person);

			phone.setPrefixPhone(new Integer(41));

			phone.setNumberPhone(new Integer(999807060));

			constraintsPhoneType = new PhoneTypeConstraints();

			constraintsPhoneType.setId_tb_Phone_Type(new Integer(3));

			phoneType = persistencePhoneType.search(constraintsPhoneType);

			phone.setPhoneType(phoneType);

			phones.add(phone);

			// Add phones

			person.setPhones(phones);

			/* ************** PHONE PERSISTENCE *************************** */

			PhonePersistence persistencePhone = new PhonePersistence();

			phones = persistencePhone.save(person);

			if (phones != null && phones.size() > 0) {

				person.setPhones(phones);

			} else {

				person.setPhones(null);
			}
		}

		// Information output

		if (person != null) {

			System.out.println(String.format("\n Administrator System %s", person.toString()));

		} else {

			System.out.println(String.format("\n %s %s", "Create Administrador System", SAVE_NOT_SUCCESS));
		}
	}

	public static void createManagerSystem() {

		// Data input

		/* ************** PERSON *************************** */

		Person person = null;

		PersonDate personDate = new PersonDate();

		RG rg = null;

		CPF cpf = null;

		ElectoralTitle electoralTitle = null;

		Email email = null;

		person = new Person();

		person.setFirstName("josé");

		person.setMiddleName("antônio");

		person.setLastName("ferreira");

		personDate = new PersonDate();

		try {

			personDate.setValue(Reader.converterStringToDate("5/5/1985"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (personDate != null) {

			person.setDateOfBirth(personDate);
		}

		person.setGender(new Character('m'));

		rg = new RG();

		rg.generateRG();

		person.setRg(rg);

		cpf = new CPF();

		cpf.generateCPF();

		person.setCpf(cpf);

		electoralTitle = new ElectoralTitle();

		electoralTitle.generateElectoralTitle();

		person.setElectoralTitle(electoralTitle);

		email = new Email();

		email.setFirstPart(person.getFirstName());

		email.setSecondPart(person.getLastName());

		email.generateEmail();

		person.setEmail(email);

		person.setStatus(new Boolean(true));

		/* ************** USER *************************** */

		User user = null;

		Permission permission = null;

		PermitionConstraints constraintsPermition = null;

		Set<Permission> permissions = null;

		PermissionPersistence persistencePermission = null;

		user = new User();

		user.setLogin("jose");

		user.setPassword("ferreira");

		persistencePermission = new PermissionPersistence();

		permissions = new HashSet<Permission>();

		constraintsPermition = new PermitionConstraints();

		constraintsPermition.setId_tb_Permition(new Integer(2));

		permission = persistencePermission.search(constraintsPermition);

		permissions.add(permission);

		constraintsPermition = new PermitionConstraints();

		constraintsPermition.setId_tb_Permition(new Integer(3));

		permission = persistencePermission.search(constraintsPermition);

		permissions.add(permission);

		user.setPermissions(permissions);

		person.setUser(user);

		/* ************** PERSON PERSISTENCE *************************** */

		PersonPersistence persistencePerson = null;

		persistencePerson = new PersonPersistence();

		person = persistencePerson.salve(person);

		/* ************** ADDRESS *************************** */

		Address address = null;

		AddressType addressType = null;

		AddressTypeConstraints constraintsAddressType = null;

		AddressTypePersistence persistenceAddressType = null;

		Street street = null;

		StreetConstraints constraintsStreet = null;

		StreetPersistence persistenceStreet = null;

		Set<Address> addresses = null;

		addresses = new HashSet<Address>();

		persistenceStreet = new StreetPersistence();

		if (person != null) {

			// Residential Address

			address = new Address();

			address.setPerson(person);

			constraintsStreet = new StreetConstraints();

			constraintsStreet.setCD_LOGRADOURO(new Integer(290791));

			constraintsStreet.setNO_LOGRADOURO_CEP(new Integer(80520600));

			street = persistenceStreet.search(constraintsStreet);

			address.setStreet(street);

			constraintsAddressType = new AddressTypeConstraints();

			constraintsAddressType.setId_tb_address_type(new Integer(1));

			persistenceAddressType = new AddressTypePersistence();

			addressType = persistenceAddressType.search(constraintsAddressType);

			address.setAddressType(addressType);

			address.setAddressNumber(new Integer(3789));

			address.setComplement("Sobrado 3");

			addresses.add(address);

			// Commercial Address

			address = new Address();

			address.setPerson(person);

			constraintsStreet = new StreetConstraints();

			constraintsStreet.setCD_LOGRADOURO(new Integer(290549));

			constraintsStreet.setNO_LOGRADOURO_CEP(new Integer(80050010));

			persistenceStreet = new StreetPersistence();

			street = persistenceStreet.search(constraintsStreet);

			address.setStreet(street);

			constraintsAddressType = new AddressTypeConstraints();

			constraintsAddressType.setId_tb_address_type(new Integer(2));

			persistenceAddressType = new AddressTypePersistence();

			addressType = persistenceAddressType.search(constraintsAddressType);

			address.setAddressType(addressType);

			address.setAddressNumber(new Integer(892));

			address.setComplement("10ª andar - sala 1003");

			addresses.add(address);

			person.setAddresses(addresses);

			/* ************** ADDRESS PERSISTENCE *************************** */

			AddressPersistence persistenceAddress = new AddressPersistence();

			addresses = persistenceAddress.salve(person);

			if (addresses != null && addresses.size() > 0) {

				person.setAddresses(addresses);

			} else {

				person.setAddresses(null);
			}

		}

		/* ************** PHONE *************************** */

		Phone phone = null;

		PhoneType phoneType = null;

		PhoneTypeConstraints constraintsPhoneType = null;

		Set<Phone> phones = new HashSet<Phone>();

		PhoneTypePersistence persistencePhoneType = new PhoneTypePersistence();

		if (person != null) {

			// Residential phone

			phone = new Phone();

			phone.setPerson(person);

			phone.setPrefixPhone(new Integer(41));

			phone.setNumberPhone(new Integer(30605040));

			constraintsPhoneType = new PhoneTypeConstraints();

			constraintsPhoneType.setId_tb_Phone_Type(new Integer(1));

			phoneType = persistencePhoneType.search(constraintsPhoneType);

			phone.setPhoneType(phoneType);

			phones.add(phone);

			// Commercial phone

			phone = new Phone();

			phone.setPerson(person);

			phone.setPrefixPhone(new Integer(41));

			phone.setNumberPhone(new Integer(31387532));

			constraintsPhoneType = new PhoneTypeConstraints();

			constraintsPhoneType.setId_tb_Phone_Type(new Integer(2));

			phoneType = persistencePhoneType.search(constraintsPhoneType);

			phone.setPhoneType(phoneType);

			phones.add(phone);

			// Smart phone

			phone = new Phone();

			phone.setPerson(person);

			phone.setPrefixPhone(new Integer(41));

			phone.setNumberPhone(new Integer(988801245));

			constraintsPhoneType = new PhoneTypeConstraints();

			constraintsPhoneType.setId_tb_Phone_Type(new Integer(3));

			phoneType = persistencePhoneType.search(constraintsPhoneType);

			phone.setPhoneType(phoneType);

			phones.add(phone);

			// Add phones

			person.setPhones(phones);

			/* ************** PHONE PERSISTENCE *************************** */

			PhonePersistence persistencePhone = new PhonePersistence();

			phones = persistencePhone.save(person);

			if (phones != null && phones.size() > 0) {

				person.setPhones(phones);

			} else {

				person.setPhones(null);
			}
		}

		// Information output

		if (person != null) {

			System.out.println(String.format("\n Manager System %s", person.toString()));

		} else {

			System.out.println(String.format("\n %s %s", "Create Manager System", SAVE_NOT_SUCCESS));
		}
	}
}
