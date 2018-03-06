package vo.person;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import br.edu.uniopet.CPF;
import br.edu.uniopet.ElectoralTitle;
import br.edu.uniopet.Email;
import br.edu.uniopet.PersonDate;
import br.edu.uniopet.RG;
import vo.address.Address;
import vo.phone.Phone;
import vo.user.User;

/**
 * Class to represent the Person entity.
 * 
 * @author Baracho
 * 
 * @since 2017 nov, 28
 * 
 * @version 3.0
 * 
 */
public class Person implements Serializable, Cloneable {

	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 6106859603949400707L;

	/**
	 * Instance variables
	 */

	private Integer personCode;

	private String firstName;

	private String middleName;

	private String lastName;

	private PersonDate dateOfBirth;

	private Character gender;

	private RG rg;

	private CPF cpf;

	private ElectoralTitle electoralTitle;

	private Email email;

	private Boolean status;

	private User user;

	private Set<Address> addresses;

	private Set<Phone> phones;

	/**
	 * Default constructor
	 */
	public Person() {
	}

	/**
	 * Parameterized constructor
	 * 
	 * @param firstName
	 *            the firstName to set
	 * @param middleName
	 *            the middleName to set
	 * @param lastName
	 *            the lastName to set
	 * @param dateOfBirth
	 *            the dateOfBirth to set
	 * @param gender
	 *            the gender to set
	 * @param rg
	 *            the rg to set
	 * @param cpf
	 *            the cpf to set
	 * @param electoralTitle
	 *            the electoralTitle to set
	 * @param email
	 *            the email to set
	 * @param status
	 *            the status to set
	 */
	public Person(String firstName, String middleName, String lastName, PersonDate dateOfBirth, Character gender, RG rg,
			CPF cpf, ElectoralTitle electoralTitle, Email email, Boolean status) {

		setFirstName(firstName);

		setMiddleName(middleName);

		setLastName(lastName);

		setDateOfBirth(dateOfBirth);

		setGender(gender);

		setRg(rg);

		setCpf(cpf);

		setElectoralTitle(electoralTitle);

		setEmail(email);

		setStatus(status);
	}

	/**
	 * Access methods
	 */

	/**
	 * @return the personCode
	 */
	public Integer getPersonCode() {
		return personCode;
	}

	/**
	 * @param personCode
	 *            the personCode to set
	 */
	public void setPersonCode(Integer personCode) {
		this.personCode = personCode;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {

		if (firstName != null && !firstName.equals("")) {

			this.firstName = firstName.toUpperCase();

		} else {

			this.firstName = null;
		}
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName
	 *            the middleName to set
	 */
	public void setMiddleName(String middleName) {

		if (middleName != null && !middleName.equals("")) {

			this.middleName = middleName.toUpperCase();

		} else {

			this.middleName = null;
		}
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {

		if (lastName != null && !lastName.equals("")) {

			this.lastName = lastName.toUpperCase();

		} else {

			this.lastName = null;
		}
	}

	/**
	 * @return the dateOfBirth
	 */
	public PersonDate getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth
	 *            the dateOfBirth to set
	 */
	public void setDateOfBirth(PersonDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the gender
	 */
	public Character getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(Character gender) {

		if (gender != null) {

			gender = new Character(Character.toUpperCase(gender.charValue()));

			if (gender.equals('M') || gender.equals('F')) {

				this.gender = gender;

			} else {

				this.gender = null;
			}

		} else {

			this.gender = null;
		}
	}

	/**
	 * @return the rg
	 */
	public RG getRg() {
		return rg;
	}

	/**
	 * @param rg
	 *            the rg to set
	 */
	public void setRg(RG rg) {
		this.rg = rg;
	}

	/**
	 * @return the cpf
	 */
	public CPF getCpf() {
		return cpf;
	}

	/**
	 * @param cpf
	 *            the cpf to set
	 */
	public void setCpf(CPF cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return the electoralTitle
	 */
	public ElectoralTitle getElectoralTitle() {
		return electoralTitle;
	}

	/**
	 * @param electoralTitle
	 *            the electoralTitle to set
	 */
	public void setElectoralTitle(ElectoralTitle electoralTitle) {
		this.electoralTitle = electoralTitle;
	}

	/**
	 * @return the email
	 */
	public Email getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(Email email) {
		this.email = email;
	}

	/**
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the addresses
	 */
	public Set<Address> getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses
	 *            the addresses to set
	 */
	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(Address address) {

		if (this.addresses == null) {

			this.addresses = new HashSet<Address>();
		}

		this.addresses.add(address);
	}

	/**
	 * @return the phones
	 */
	public Set<Phone> getPhones() {
		return phones;
	}

	/**
	 * @param phones
	 *            the phones to set
	 */
	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}

	public void setPhone(Phone phone) {

		if (this.phones == null) {

			this.phones = new HashSet<Phone>();
		}

		this.phones.add(phone);
	}

	/**
	 * Class operations
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		// Variable statement

		String output = null;

		DecimalFormat decimalFormat = null;

		int i = 0;

		// Data processing

		decimalFormat = new DecimalFormat();

		output = String.format("\n Person Code: %s",
				this.personCode != null ? decimalFormat.format(this.personCode.intValue()) : "");

		output += String.format("\n First Name: %s",
				(this.firstName != null && !this.firstName.equals("")) ? this.firstName : "");

		output += String.format("\n Middle Name: %s",
				(this.middleName != null && !this.middleName.equals("")) ? this.middleName : "");

		output += String.format("\n Last Name: %s",
				(this.lastName != null && !this.lastName.equals("")) ? this.lastName : "");

		output += String.format("\n Date of Birth: %s",
				this.dateOfBirth != null && this.dateOfBirth.getValue() != null
						? new SimpleDateFormat("MMM-dd-yyyy").format(this.dateOfBirth.getValue())
						: "");

		output += String.format("\n Age: %s",
				this.dateOfBirth != null && this.dateOfBirth.getValue() != null
						? this.dateOfBirth.calculateAge().toString() + " years"
						: "");

		output += String.format("\n Gender: %s",
				this.gender != null ? (this.gender.equals('M') ? "Male" : "Female") : "");

		output += String.format("\n RG: %s",
				(this.rg != null && !this.rg.getValue().equals("")) ? this.rg.getValue() : "");

		output += String.format("\n CPF: %s",
				this.cpf != null && (this.cpf.getValueString() != null && !this.cpf.getValueString().equals(""))
						? this.cpf.getValueString()
						: "");

		output += String.format("\n Electoral Title: %s",
				this.electoralTitle != null
						&& (this.electoralTitle.getValue() != null && !this.electoralTitle.getValue().equals(""))
								? this.electoralTitle.getValue()
								: "");

		output += String.format("\n E-mail: %s",
				this.email != null && (this.email.getValue() != null && !this.email.getValue().equals(""))
						? this.email.getValue()
						: "");

		output += String.format("\n Status: %s",
				this.status != null ? (this.status.equals(true) ? "Active" : "Inactive") : "");

		output += String.format("\n User %s", this.user != null ? this.user.toString() : "");

		output += "\n Addresses";

		if (this.addresses != null && this.addresses.size() > 0) {

			i = 1;

			for (Address address : this.addresses) {

				output += String.format("\n #%d %s", i++, address.toString());
			}
		}

		output += "\n Phones";

		if (this.phones != null && this.phones.size() > 0) {

			i = 1;

			for (Phone phone : this.phones) {

				output += String.format("\n #%d %s", i++, phone.toString());
			}
		}

		// Information output

		return output;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Person clone() throws CloneNotSupportedException {

		// Variable statement

		Person cloned = null;

		// Data processing

		// Shallow cloning

		cloned = (Person) super.clone();

		// Deep cloning

		if (this.dateOfBirth != null) {

			cloned.dateOfBirth = this.dateOfBirth.clone();
		}

		if (this.rg != null) {

			cloned.rg = this.rg.clone();
		}

		if (this.cpf != null) {

			cloned.cpf = (CPF) this.cpf;
		}

		if (this.electoralTitle != null) {

			cloned.electoralTitle = this.electoralTitle.clone();
		}

		if (this.email != null) {

			cloned.email = this.email;
		}

		if (this.user != null) {

			cloned.user = this.user;
		}

		if (this.addresses != null) {

			cloned.addresses = (Set<Address>) this.addresses;
		}

		if (this.phones != null) {

			cloned.phones = (Set<Phone>) this.phones;
		}

		// Information output

		return cloned;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addresses == null) ? 0 : addresses.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((electoralTitle == null) ? 0 : electoralTitle.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
		result = prime * result + ((personCode == null) ? 0 : personCode.hashCode());
		result = prime * result + ((phones == null) ? 0 : phones.hashCode());
		result = prime * result + ((rg == null) ? 0 : rg.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Person)) {
			return false;
		}
		Person other = (Person) obj;
		if (addresses == null) {
			if (other.addresses != null) {
				return false;
			}
		} else if (!addresses.equals(other.addresses)) {
			return false;
		}
		if (cpf == null) {
			if (other.cpf != null) {
				return false;
			}
		} else if (!cpf.equals(other.cpf)) {
			return false;
		}
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null) {
				return false;
			}
		} else if (!dateOfBirth.equals(other.dateOfBirth)) {
			return false;
		}
		if (electoralTitle == null) {
			if (other.electoralTitle != null) {
				return false;
			}
		} else if (!electoralTitle.equals(other.electoralTitle)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (gender == null) {
			if (other.gender != null) {
				return false;
			}
		} else if (!gender.equals(other.gender)) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		if (middleName == null) {
			if (other.middleName != null) {
				return false;
			}
		} else if (!middleName.equals(other.middleName)) {
			return false;
		}
		if (personCode == null) {
			if (other.personCode != null) {
				return false;
			}
		} else if (!personCode.equals(other.personCode)) {
			return false;
		}
		if (phones == null) {
			if (other.phones != null) {
				return false;
			}
		} else if (!phones.equals(other.phones)) {
			return false;
		}
		if (rg == null) {
			if (other.rg != null) {
				return false;
			}
		} else if (!rg.equals(other.rg)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!status.equals(other.status)) {
			return false;
		}
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
			return false;
		}
		return true;
	}
}
