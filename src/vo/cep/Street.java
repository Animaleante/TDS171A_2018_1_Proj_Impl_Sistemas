package vo.cep;

import java.io.Serializable;

/**
 * Class to represent a Logradouro entity.
 * 
 * @author Baracho
 * 
 * @since 2017 jan, 05
 * 
 * @version 1.0
 *
 */
public class Street implements Cloneable, Serializable {

	/**
	 * Generated serial versionID
	 */
	private static final long serialVersionUID = -7761292496872775277L;

	/**
	 * Instance variables
	 */

	private Integer streetCode;

	private String description;

	private District district;

	private Integer cep;

	private Integer tipoLogradouro;

	/**
	 * Default constructor
	 */

	public Street() {
		// TODO Auto-generated constructor stub

		this.district = new District();
	}

	/**
	 * Parameterized constructor
	 * 
	 * @param descrition
	 *            the description to set
	 * @param cep
	 *            the cep to set
	 * @param district
	 *            the district to set
	 */
	public Street(String descrition, Integer cep, District district) {

		this.setDescrition(descrition);

		this.setCep(cep);

		this.setDistrict(district);
	}

	/**
	 * Access methods
	 */

	/**
	 * @return the streetCode
	 */
	public Integer getStreetCode() {
		return streetCode;
	}

	/**
	 * @param streetCode
	 *            the streetCode to set
	 */
	public void setStreeCode(Integer streetCode) {
		this.streetCode = streetCode;
	}

	/**
	 * @return the description
	 */
	public String getDecription() {
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
	 * @return the district
	 */
	public District getDistrict() {
		return district;
	}

	/**
	 * @param district
	 *            the district to set
	 */
	public void setDistrict(District district) {
		this.district = district;
	}

	/**
	 * @return the cep
	 */
	public Integer getCep() {
		return cep;
	}

	/**
	 * @param cep
	 *            the cep to set
	 */
	public void setCep(Integer cep) {
		this.cep = cep;
	}

	/**
	 * @return the tipoLogradouro
	 */
	public Integer getTipoLogradouro() {
		return tipoLogradouro;
	}

	/**
	 * @param tipoLogradouro
	 *            the tipoLogradouro to set
	 */
	public void setTipoLogradouro(Integer tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
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

		output = "\n Street Code: ";

		if (this.streetCode != null) {

			output += this.streetCode.toString();
		}

		output += "\n Description: ";

		if (this.description != null && !this.description.equals("")) {

			output += this.description;
		}

		if (this.district != null) {

			output += this.district.toString();
		}

		output += "\n CEP: ";

		if (this.cep != null && !this.cep.equals("")) {

			output += this.cep;
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
	public Street clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub

		// Variables declaration

		Street cloned = null;

		// Shallow clone

		cloned = (Street) super.clone();

		// Deeep clone

		cloned.district = this.district.clone();

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
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((district == null) ? 0 : district.hashCode());
		result = prime * result + ((streetCode == null) ? 0 : streetCode.hashCode());
		result = prime * result + ((tipoLogradouro == null) ? 0 : tipoLogradouro.hashCode());
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
		if (!(obj instanceof Street)) {
			return false;
		}
		Street other = (Street) obj;
		if (cep == null) {
			if (other.cep != null) {
				return false;
			}
		} else if (!cep.equals(other.cep)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (district == null) {
			if (other.district != null) {
				return false;
			}
		} else if (!district.equals(other.district)) {
			return false;
		}
		if (streetCode == null) {
			if (other.streetCode != null) {
				return false;
			}
		} else if (!streetCode.equals(other.streetCode)) {
			return false;
		}
		if (tipoLogradouro == null) {
			if (other.tipoLogradouro != null) {
				return false;
			}
		} else if (!tipoLogradouro.equals(other.tipoLogradouro)) {
			return false;
		}
		return true;
	}
}
