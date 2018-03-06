
package vo.address;

import java.io.Serializable;

import vo.address.type.AddressType;
import vo.cep.Street;
import vo.person.Person;

/**
 * Class to represent the Address entity.
 * 
 * @author Baracho
 * 
 * @since 2017 jan, 05
 * 
 * @version 1.0
 *
 */
public class Address implements Serializable, Cloneable {

	/**
	 * Generate serial version ID
	 */
	private static final long serialVersionUID = 1424461505100282637L;

	/**
	 * Instance variables
	 */

	private Integer addressCode;

	private Street street;

	private Integer addressNumber;

	private String complement;

	private AddressType addressType;

	private Person person;

	/**
	 * Default constructor
	 */
	public Address() {
		// TODO Auto-generated constructor stub

		this.street = new Street();

		this.person = new Person();

		this.addressType = new AddressType();
	}

	/**
	 * Parameterized constructor
	 * 
	 * @param street
	 *            the street to set
	 * @param person
	 *            the person to set
	 * @param addressType
	 *            the addressType to set
	 * @param addressNumber
	 *            the addressNumber to set
	 * @param complement
	 *            the complement to set
	 */
	public Address(Street street, Person person, AddressType addressType, Integer addressNumber, String complement) {

		this.setStreet(street);

		this.setPerson(person);

		this.setAddressType(addressType);

		this.setAddressNumber(addressNumber);

		this.setComplement(complement);
	}

	/**
	 * Access methods
	 */

	/**
	 * @return the addressCode
	 */
	public Integer getAddressCode() {
		return addressCode;
	}

	/**
	 * @param addressCode
	 *            the addressCode to set
	 */
	public void setAddressCode(Integer addressCode) {
		this.addressCode = addressCode;
	}

	/**
	 * @return the street
	 */
	public Street getStreet() {
		return street;
	}

	/**
	 * @param street
	 *            the street to set
	 */
	public void setStreet(Street street) {
		this.street = street;
	}

	/**
	 * @return the addressNumber
	 */
	public Integer getAddressNumber() {
		return addressNumber;
	}

	/**
	 * @param addressNumber
	 *            the addressNumber to set
	 */
	public void setAddressNumber(Integer addressNumber) {
		this.addressNumber = addressNumber;
	}

	/**
	 * @return the complement
	 */
	public String getComplement() {
		return complement;
	}

	/**
	 * @param complement
	 *            the complement to set
	 */
	public void setComplement(String complement) {
		this.complement = complement;
	}

	/**
	 * @return the addressType
	 */
	public AddressType getAddressType() {
		return addressType;
	}

	/**
	 * @param addressType
	 *            the addressType to set
	 */
	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

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

	/**
	 * Class operations
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub

		// Variables declaration

		String output = null;

		// Data processing

		output = String.format("\n Address Code: %s", this.addressCode != null ? this.addressCode.toString() : "");

		if (this.street != null) {

			output += this.street.toString();
		}

		output += String.format("\n Address Number: %s",
				this.addressNumber != null ? this.addressNumber.toString() : "");

		output += String.format("\n Complement: %s",
				this.complement != null && !this.complement.equals("") ? this.complement : "");

		if (this.addressType != null) {

			output += this.addressType.toString();
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
	public Address clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub

		// Variables declaration

		Address cloned = null;

		// Shallow clone

		cloned = (Address) super.clone();

		// Deep clone

		cloned.street = this.street.clone();

		cloned.person = this.person.clone();

		cloned.addressType = this.addressType.clone();

		cloned.person = this.person.clone();

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
		result = prime * result + ((addressCode == null) ? 0 : addressCode.hashCode());
		result = prime * result + ((addressNumber == null) ? 0 : addressNumber.hashCode());
		result = prime * result + ((addressType == null) ? 0 : addressType.hashCode());
		result = prime * result + ((complement == null) ? 0 : complement.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
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
		if (!(obj instanceof Address)) {
			return false;
		}
		Address other = (Address) obj;
		if (addressCode == null) {
			if (other.addressCode != null) {
				return false;
			}
		} else if (!addressCode.equals(other.addressCode)) {
			return false;
		}
		if (addressNumber == null) {
			if (other.addressNumber != null) {
				return false;
			}
		} else if (!addressNumber.equals(other.addressNumber)) {
			return false;
		}
		if (addressType == null) {
			if (other.addressType != null) {
				return false;
			}
		} else if (!addressType.equals(other.addressType)) {
			return false;
		}
		if (complement == null) {
			if (other.complement != null) {
				return false;
			}
		} else if (!complement.equals(other.complement)) {
			return false;
		}
		if (person == null) {
			if (other.person != null) {
				return false;
			}
		} else if (!person.equals(other.person)) {
			return false;
		}
		if (street == null) {
			if (other.street != null) {
				return false;
			}
		} else if (!street.equals(other.street)) {
			return false;
		}
		return true;
	}
}
