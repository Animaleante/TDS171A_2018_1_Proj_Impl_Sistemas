package vo.address.type;

import java.io.Serializable;

/**
 * Class to represent the AddressType entity.
 * 
 * @author Baracho
 * 
 * @since 5 de jan de 2017
 * 
 * @version 1.0
 *
 */
public class AddressType implements Cloneable, Serializable {

	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -1456837207242062214L;

	/**
	 * Instance variables
	 */

	private Integer addressTypeCode;

	private String type;

	/**
	 * Default constructor
	 */
	public AddressType() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Parameterized constructor
	 * 
	 * @param type
	 *            the type to set
	 */
	public AddressType(String type) {
		this.setType(type);
	}

	/**
	 * Access methods
	 */

	/**
	 * @return the addressTypeCode
	 */
	public Integer getAddressTypeCode() {
		return addressTypeCode;
	}

	/**
	 * @param addressTypeCode
	 *            the addressTypeCode to set
	 */
	public void setAddressTypeCode(Integer addressTypeCode) {
		this.addressTypeCode = addressTypeCode;
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

		// Variables declaration

		String output = null;

		// Data processing

		output = String.format("\n Address Type Code: %s",
				this.addressTypeCode != null ? this.addressTypeCode.toString() : "");

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
	public AddressType clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub

		// Variables declaration

		AddressType cloned = null;

		// Shallow clone

		cloned = (AddressType) super.clone();

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
		result = prime * result + ((addressTypeCode == null) ? 0 : addressTypeCode.hashCode());
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
		if (!(obj instanceof AddressType)) {
			return false;
		}
		AddressType other = (AddressType) obj;
		if (addressTypeCode == null) {
			if (other.addressTypeCode != null) {
				return false;
			}
		} else if (!addressTypeCode.equals(other.addressTypeCode)) {
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
