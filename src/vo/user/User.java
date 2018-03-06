package vo.user;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import vo.permission.Permission;

/**
 * Class to represent a User entity.
 * 
 * @author Baracho
 * 
 * @since 2017njan, 05
 * 
 * @version 1.0
 * 
 */
public class User implements Serializable, Cloneable {

	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -2441147884620028375L;

	/**
	 * Instance variables
	 */

	private Integer userCode;

	private String login;

	private String password;

	private Set<Permission> permissions;

	/**
	 * Default constructor
	 */

	public User() {

	}

	/**
	 * Parameterized constructor
	 * 
	 * @param login
	 *            the login to set
	 * @param password
	 *            the password to set
	 * @param permissions
	 *            the userPermission to set
	 */

	public User(String login, String password, Set<Permission> permissions) {

		this.setLogin(login);

		this.setPassword(password);

		this.setPermissions(permissions);
	}

	/**
	 * Access methods
	 */

	/**
	 * @return the userCode
	 */
	public Integer getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode
	 *            the userCode to set
	 */
	public void setUserCode(Integer userCode) {
		this.userCode = userCode;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the permissions
	 */
	public Set<Permission> getPermissions() {
		return permissions;
	}

	/**
	 * @param permissions
	 *            the permissions to set
	 */
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * @param permission
	 *            the permission to set
	 */
	public void setPermission(Permission permission) {

		if (this.permissions == null) {

			this.permissions = new HashSet<Permission>();
		}

		this.permissions.add(permission);
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

		// Variables declarations

		String output = null;

		int i = 0;

		// Data processing

		output = String.format("\n User Code: %s", this.userCode != null ? this.userCode.toString() : "");

		output += String.format("\n Login: %s", this.login != null && !this.login.equals("") ? this.login : "");

		output += String.format("\n Password: %s",
				this.password != null && !this.password.equals("") ? this.password : "");

		output += String.format("\n %s", "Pemissions:");

		if (this.permissions != null && this.permissions.size() > 0) {

			Iterator<Permission> iterator = this.permissions.iterator();

			Permission permission = null;

			i = 1;

			while (iterator.hasNext()) {

				permission = iterator.next();

				if (permission != null) {

					output += String.format("\n #%d %s", i++, permission.toString());
				}
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
	public User clone() throws CloneNotSupportedException {

		// Variables declarations

		User cloned = null;

		// Shallow clone

		cloned = (User) super.clone();

		// Deep clone

		cloned.permissions = (Set<Permission>) this.permissions;

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
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userCode == null) ? 0 : userCode.hashCode());
		result = prime * result + ((permissions == null) ? 0 : permissions.hashCode());
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
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (login == null) {
			if (other.login != null) {
				return false;
			}
		} else if (!login.equals(other.login)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (userCode == null) {
			if (other.userCode != null) {
				return false;
			}
		} else if (!userCode.equals(other.userCode)) {
			return false;
		}
		if (permissions == null) {
			if (other.permissions != null) {
				return false;
			}
		} else if (!permissions.equals(other.permissions)) {
			return false;
		}
		return true;
	}
}
