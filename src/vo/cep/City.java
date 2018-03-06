package vo.cep;

import java.io.Serializable;

/**
 * Class to represent a City entity.
 * 
 * @author Baracho
 * 
 * @since 2017 jan, 05
 * 
 * @version 1.0
 *
 */
public class City implements Cloneable, Serializable {

	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 7627683304395603601L;

	/**
	 * Instance variables
	 */

	private Integer cityCode;

	private String description;

	private FederationUnity federationUnity;

	/**
	 * Default constructor
	 */
	public City() {
		// TODO Auto-generated constructor stub

		this.federationUnity = new FederationUnity();
	}

	/**
	 * Parameterized constructor
	 * 
	 * @param descrition
	 *            the descripton to set
	 * @param federationUnity
	 *            the federationUnity to set
	 */
	public City(String descrition, FederationUnity federationUnity) {

		this.setDescrition(descrition);

		this.setFederationUnity(federationUnity);
	}

	/**
	 * Access methods
	 */

	/**
	 * @return the cityCode
	 */
	public Integer getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode
	 *            the cityCode to set
	 */
	public void setCitycode(Integer cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return the descrition
	 */
	public String getDescrition() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescrition(String description) {
		this.description = description;
	}

	/**
	 * @return the federationUnity
	 */
	public FederationUnity getFederationUnity() {
		return federationUnity;
	}

	/**
	 * @param federationUnity
	 *            the federationUnity to set
	 */
	public void setFederationUnity(FederationUnity federationUnity) {
		this.federationUnity = federationUnity;
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

		output = "\n City Code: ";

		if (this.cityCode != null) {

			output += this.cityCode;
		}

		output += "\n Description: ";

		if (this.description != null && !this.description.equals("")) {

			output += this.description;
		}

		if (this.federationUnity != null) {

			output += this.federationUnity.toString();
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
	public City clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub

		// Variables declaration

		City cloned = null;

		// Shallow clone

		cloned = (City) super.clone();

		// Deep clone

		cloned.federationUnity = this.federationUnity.clone();

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
		result = prime * result + ((cityCode == null) ? 0 : cityCode.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((federationUnity == null) ? 0 : federationUnity.hashCode());
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
		if (!(obj instanceof City)) {
			return false;
		}
		City other = (City) obj;
		if (cityCode == null) {
			if (other.cityCode != null) {
				return false;
			}
		} else if (!cityCode.equals(other.cityCode)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (federationUnity == null) {
			if (other.federationUnity != null) {
				return false;
			}
		} else if (!federationUnity.equals(other.federationUnity)) {
			return false;
		}
		return true;
	}
}
