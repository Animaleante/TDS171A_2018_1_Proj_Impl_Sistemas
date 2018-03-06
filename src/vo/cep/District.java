package vo.cep;

import java.io.Serializable;

/**
 * Class to represent a District entity.
 * 
 * @author Baracho
 * 
 * @since 2017 jan, 05
 * 
 * @version 1.0
 *
 */
public class District implements Cloneable, Serializable {

	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 3877978304182091082L;

	/**
	 * Instance variables
	 */

	private Integer districtCode;

	private String description;

	private City city;

	/**
	 * Default constructor
	 */
	public District() {
		// TODO Auto-generated constructor stub

		this.city = new City();
	}

	/**
	 * Parameterized constructor
	 * 
	 * @param description
	 *            the description to set
	 * @param city
	 *            the city to set
	 */
	public District(String description, City city) {

		this.setDescription(description);

		this.setCity(city);

	}

	/**
	 * Access methods
	 */

	/**
	 * @return the districtCode
	 */
	public Integer getDistrictCode() {
		return districtCode;
	}

	/**
	 * @param districtCode
	 *            the districtCode to set
	 */
	public void setDistrictCode(Integer districtCode) {
		this.districtCode = districtCode;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the city
	 */
	public City getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(City city) {
		this.city = city;
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

		output = "\n District Code: ";

		if (this.districtCode != null) {

			output += this.districtCode.toString();
		}

		output += "\n Description: ";

		if (this.description != null && !this.description.equals("")) {

			output += this.description;
		}

		if (this.city != null) {

			output += this.city.toString();
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
	public District clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub

		// Variables declaration

		District cloned = null;

		// Shallow clone

		cloned = (District) super.clone();

		// Deep clone

		cloned.city = this.city.clone();

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
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((districtCode == null) ? 0 : districtCode.hashCode());
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
		if (!(obj instanceof District)) {
			return false;
		}
		District other = (District) obj;
		if (city == null) {
			if (other.city != null) {
				return false;
			}
		} else if (!city.equals(other.city)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (districtCode == null) {
			if (other.districtCode != null) {
				return false;
			}
		} else if (!districtCode.equals(other.districtCode)) {
			return false;
		}
		return true;
	}
}
