package vo.phone;

import java.io.Serializable;

import vo.person.Person;
import vo.phone.type.PhoneType;

/**
 * Class to represent the Phone entity.
 * 
 * @author Baracho
 * 
 * @since 2017 jan, 05
 * 
 * @version 1.0
 *
 */
public class Phone implements Serializable, Cloneable {

	/**
	 * generated serial version ID
	 */
	private static final long serialVersionUID = 1005524094700812583L;

	/**
	 * Instance variables
	 */

	private Integer phoneCode;

	private Integer prefixPhone;

	private Integer numberPhone;

	private PhoneType phoneType;

	private Person person;

	/**
	 * Default constructor
	 */
	public Phone() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Parameterized constructor
	 * 
	 * @param prefixPhone
	 *            the phonePrefix to set
	 * @param numberPhone
	 *            the phoneNumber to set
	 * @param phoneType
	 *            the phoneType to set
	 */
	public Phone(Integer prefixPhone, Integer numberPhone, PhoneType phoneType) {

		this.setPrefixPhone(prefixPhone);

		this.setNumberPhone(numberPhone);

		this.setPhoneType(phoneType);
	}

	/**
	 * Access methods
	 */

	/**
	 * @return the phoneCode
	 */
	public Integer getPhoneCode() {
		return phoneCode;
	}

	/**
	 * @param phoneCode
	 *            the phoneCode to set
	 */
	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}

	/**
	 * @return the prefixPhone
	 */
	public Integer getPrefixPhone() {
		return prefixPhone;
	}

	/**
	 * @param prefixPhone
	 *            the prefixPhone to set
	 */
	public void setPrefixPhone(Integer prefixPhone) {
		this.prefixPhone = prefixPhone;
	}

	/**
	 * @return the numberPhone
	 */
	public Integer getNumberPhone() {
		return numberPhone;
	}

	/**
	 * @return the numberPhone
	 */
	public String getNumberPhoneString() {
		return converteNumberPhoneToString();
	}

	/**
	 * @param numberPhone
	 *            the numberPhone to set
	 */
	public void setNumberPhone(Integer numberPhone) {
		this.numberPhone = numberPhone;
	}

	/**
	 * @return the phoneType
	 */
	public PhoneType getPhoneType() {
		return phoneType;
	}

	/**
	 * @param phoneType
	 *            the phoneType to set
	 */
	public void setPhoneType(PhoneType phoneType) {
		this.phoneType = phoneType;
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

		output = String.format("\n Phone Code: %s", this.phoneCode != null ? this.phoneCode.toString() : "");

		output += String.format("\n Prefix Phone: %s", this.prefixPhone != null ? this.prefixPhone.toString() : "");

		output += String.format("\n Numero Phone: %s", this.numberPhone != null ? this.getNumberPhoneString() : "");

		if (this.phoneType != null) {

			output += this.phoneType.toString();
		}

		// Information output

		return output;
	}

	/**
	 * @return the numberPhone to String
	 */
	public String converteNumberPhoneToString() {

		// Variables declaration

		String numberPhoneString = null;

		String numberPhoneValue = null;

		// Data processing

		if (this.numberPhone != null) {

			numberPhoneValue = this.numberPhone.toString();

			if (numberPhoneValue.length() == 8) {

				numberPhoneString = new String();

				for (int i = 0; i < numberPhoneValue.length(); i++) {

					if (i == 4) {

						numberPhoneString += "-";
					}

					numberPhoneString += numberPhoneValue.charAt(i);
				}

			} else {

				if (numberPhoneValue.length() == 9) {

					numberPhoneString = new String();

					for (int i = 0; i < numberPhoneValue.length(); i++) {

						if (i == 5) {

							numberPhoneString += "-";
						}

						numberPhoneString += numberPhoneValue.charAt(i);
					}
				}
			}
		}

		// Information output

		return numberPhoneString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Phone clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub

		// Variables declaration

		Phone cloned = null;

		// Shallow clone

		cloned = (Phone) super.clone();

		// Deep clone

		cloned.phoneType = this.phoneType.clone();

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
		result = prime * result + ((phoneCode == null) ? 0 : phoneCode.hashCode());
		result = prime * result + ((numberPhone == null) ? 0 : numberPhone.hashCode());
		result = prime * result + ((prefixPhone == null) ? 0 : prefixPhone.hashCode());
		result = prime * result + ((phoneType == null) ? 0 : phoneType.hashCode());
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
		if (!(obj instanceof Phone)) {
			return false;
		}
		Phone other = (Phone) obj;
		if (phoneCode == null) {
			if (other.phoneCode != null) {
				return false;
			}
		} else if (!phoneCode.equals(other.phoneCode)) {
			return false;
		}
		if (numberPhone == null) {
			if (other.numberPhone != null) {
				return false;
			}
		} else if (!numberPhone.equals(other.numberPhone)) {
			return false;
		}
		if (prefixPhone == null) {
			if (other.prefixPhone != null) {
				return false;
			}
		} else if (!prefixPhone.equals(other.prefixPhone)) {
			return false;
		}
		if (phoneType == null) {
			if (other.phoneType != null) {
				return false;
			}
		} else if (!phoneType.equals(other.phoneType)) {
			return false;
		}
		return true;
	}

}
