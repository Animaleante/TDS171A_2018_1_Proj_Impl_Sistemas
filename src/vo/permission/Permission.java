package vo.permission;

import java.io.Serializable;

/**
 * Class to represent the Permission entity.
 * 
 * @author Baracho
 * 
 * @since 2017 dez, 04
 * 
 * @version 1.0
 *
 */
public class Permission implements Serializable, Cloneable {

	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 5647327322306949106L;

	/**
	 * Instance variables
	 */

	private Integer permissionCode;

	private String permission;

	/**
	 * Default constructor
	 */
	public Permission() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Parameterized constructor
	 * 
	 * @param permission
	 *            the permission to set
	 */
	public Permission(String permission) {

		this.setPermission(permission);

	}

	/**
	 * Access methods
	 */

	/**
	 * @return the permissionCode
	 */
	public Integer getPermissionCode() {
		return permissionCode;
	}

	/**
	 * @param permissionCode
	 *            the permissionCode to set
	 */
	public void setPermissionCode(Integer permissionCode) {
		this.permissionCode = permissionCode;
	}

	/**
	 * @return the permission
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * @param permission
	 *            the permission to set
	 */
	public void setPermission(String permission) {
		this.permission = permission;
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

		output = String.format("\n Permission Code: %s",
				this.permissionCode != null ? this.permissionCode.toString() : "");

		output += String.format("\n Permission: %s",
				this.permission != null && !this.permission.equals("") ? this.permission : "");

		// Information output

		return output;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Permission clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub

		// Variables declaration

		Permission cloned = null;

		// Data processing

		// Shallow copy

		cloned = (Permission) super.clone();

		// Deep cloned

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
		result = prime * result + ((permission == null) ? 0 : permission.hashCode());
		result = prime * result + ((permissionCode == null) ? 0 : permissionCode.hashCode());
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
		if (!(obj instanceof Permission)) {
			return false;
		}
		Permission other = (Permission) obj;
		if (permission == null) {
			if (other.permission != null) {
				return false;
			}
		} else if (!permission.equals(other.permission)) {
			return false;
		}
		if (permissionCode == null) {
			if (other.permissionCode != null) {
				return false;
			}
		} else if (!permissionCode.equals(other.permissionCode)) {
			return false;
		}
		return true;
	}
}
