package vo.cep;

import java.io.Serializable;

/**
 * Class to represent a FederationUnity entity.
 * 
 * @author Baracho
 * 
 * @since 2017 jan, 05
 * 
 * @version 1.0
 *
 */
public class FederationUnity implements Cloneable, Serializable {

	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -4769801896412520630L;

	/**
	 * Instance variables
	 */

	private Integer federationUnityCode;

	private String description;

	private String stateAbbreviation;

	/**
	 * Default constructor
	 */
	public FederationUnity() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Parameterized constructor
	 * 
	 * @param description
	 *            the description to set
	 * @param stateAbbreviation
	 *            the stateAbbreviation to set
	 */
	public FederationUnity(String description, String stateAbbreviation) {

		this.setDescription(description);

		this.setStateAbbreviation(stateAbbreviation);
	}

	/**
	 * Access methods
	 */

	/**
	 * @return the federationUnityCode
	 */
	public Integer getFederationUnityCode() {
		return federationUnityCode;
	}

	/**
	 * @param federationUnityCode
	 *            the federationUnityCode to set
	 */
	public void setFederationUnityCode(Integer federationUnityCode) {
		this.federationUnityCode = federationUnityCode;
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
	 * @return the stateAbbreviation
	 */
	public String getStateAbbreviation() {
		return stateAbbreviation;
	}

	/**
	 * @param stateAbbreviation
	 *            the stateAbbreviation to set
	 */
	public void setStateAbbreviation(String stateAbbreviation) {
		this.stateAbbreviation = stateAbbreviation;
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

		output = "\n Federation Unity Code: ";

		if (this.federationUnityCode != null) {

			output += this.federationUnityCode.toString();
		}

		output += "\n Description: ";

		if (this.description != null && !this.description.equals("")) {

			output += this.description;
		}

		output += "\n State Abbreviation: ";

		if (this.stateAbbreviation != null && !this.stateAbbreviation.equals("")) {

			output += this.stateAbbreviation;
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
	public FederationUnity clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub

		// Variables declaration

		FederationUnity cloned = null;

		// Shallow clone

		cloned = (FederationUnity) super.clone();

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
		result = prime * result + ((federationUnityCode == null) ? 0 : federationUnityCode.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((stateAbbreviation == null) ? 0 : stateAbbreviation.hashCode());
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
		if (!(obj instanceof FederationUnity)) {
			return false;
		}
		FederationUnity other = (FederationUnity) obj;
		if (federationUnityCode == null) {
			if (other.federationUnityCode != null) {
				return false;
			}
		} else if (!federationUnityCode.equals(other.federationUnityCode)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (stateAbbreviation == null) {
			if (other.stateAbbreviation != null) {
				return false;
			}
		} else if (!stateAbbreviation.equals(other.stateAbbreviation)) {
			return false;
		}
		return true;
	}
}
