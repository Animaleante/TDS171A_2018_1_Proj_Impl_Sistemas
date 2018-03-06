package vo.phone.type;

import java.io.Serializable;

/**
 * Class to represent the PhoneType entity.
 * 
 * @author Baracho
 * 
 * @since 2017 jan, 05
 * 
 * @version 1.0
 *
 */
public class PhoneType implements Serializable, Cloneable {

	/**
	 * Generate serial version ID
	 */
	private static final long serialVersionUID = 1747312580258823840L;

	/**
	 * Instance variables
	 */

	private Integer phoneTypeCode;

	private String type;

	/**
	 * Default constructor
	 */
	public PhoneType() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Parameterized constructor
	 * 
	 * @param type
	 *            the value to set
	 */
	public PhoneType(String type) {
		this.setType(type);
	}

	/**
	 * Access methods
	 */

	/**
	 * @return the phoneTypeCode
	 */
	public Integer getPhoneTypeCode() {
		return phoneTypeCode;
	}

	/**
	 * @param phoneTypeCode
	 *            the phoneTypeCode to set
	 */
	public void setPhoneTypeCode(Integer phoneTypeCode) {
		this.phoneTypeCode = phoneTypeCode;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
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

		// variables declaration

		String output = null;

		// Data processing

		output = String.format("\n Phone Type Code: %s",
				this.phoneTypeCode != null ? this.phoneTypeCode.toString() : "");

		output += String.format("\n Type: %s", this.type != null && !this.type.equals("") ? this.type : "");

		// Information output

		return output;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public PhoneType clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub

		// Variables declaration

		PhoneType cloned = null;

		// Shallow clone

		cloned = (PhoneType) super.clone();

		// Deep clone

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
		result = prime * result + ((phoneTypeCode == null) ? 0 : phoneTypeCode.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (!(obj instanceof PhoneType)) {
			return false;
		}
		PhoneType other = (PhoneType) obj;
		if (phoneTypeCode == null) {
			if (other.phoneTypeCode != null) {
				return false;
			}
		} else if (!phoneTypeCode.equals(other.phoneTypeCode)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}

}
