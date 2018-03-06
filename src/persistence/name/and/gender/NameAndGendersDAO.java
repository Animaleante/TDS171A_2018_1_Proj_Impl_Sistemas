package persistence.name.and.gender;

import static persistence.Persistence.DELETE_DATA;
import static persistence.Persistence.DELETE_ERROR;
import static persistence.Persistence.DELETE_NOT_SUCCESS;
import static persistence.Persistence.DELETE_SUCCESS;
import static persistence.Persistence.FLAG;
import static persistence.Persistence.LIST_DATA;
import static persistence.Persistence.QUERY_ERROR;
import static persistence.Persistence.QUERY_NOT_SUCCESS;
import static persistence.Persistence.QUERY_SUCCESS;
import static persistence.Persistence.RECOVERY_NOT_SUCCESS;
import static persistence.Persistence.RECOVERY_SUCCESS;
import static persistence.Persistence.SAVE_DATA;
import static persistence.Persistence.SAVE_ERROR;
import static persistence.Persistence.SAVE_NOT_SUCCESS;
import static persistence.Persistence.SAVE_SUCCESS;
import static persistence.Persistence.UPDATE_DATA;
import static persistence.Persistence.UPDATE_ERROR;
import static persistence.Persistence.UPDATE_NOT_SUCCESS;
import static persistence.Persistence.UPDATE_SUCCESS;
import static persistence.Persistence.V_TABLE_NAME_AND_GENDER;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import vo.person.Person;

/**
 * Class to implement the operations against the database for the NameAndGender
 * entity.
 * 
 * @author Baracho
 * 
 * @since November 29, 2017
 * 
 * @version 1.0
 *
 */
class NameAndGendersDAO implements INameAndGender {

	/**
	 * Instance variables
	 */

	private Connection connection;

	/**
	 * Default constructor
	 */
	public NameAndGendersDAO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Access methods
	 */

	/**
	 * @param connection
	 */
	public void setConnection(Connection connection) {
		// TODO Auto-generated method stub
		this.connection = connection;
	}

	/**
	 * Class operations
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.name.and.gender.INameAndGender#salve(vo.person.Person)
	 */
	@Override
	public Person salve(Person person) {
		// TODO Auto-generated method stub

		// Variables declaration

		Integer personCode = null;

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		int i = 1;

		// Data processing

		if (person != null) {

			person.setPersonCode(this.personCode());

			try {

				query = "INSERT INTO ";

				query += V_TABLE_NAME_AND_GENDER;

				query += " PERSON (";

				query += "PERSON.PERSON_CODE, ";

				query += "PERSON.FIRST_NAME, ";

				query += "PERSON.MIDDLE_NAME, ";

				query += "PERSON.LAST_NAME, ";

				query += "PERSON.GENDER";

				query += ") VALUES (?, ?, ?, ?, ?)";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, person.getPersonCode());

				preparedStatement.setString(i++, person.getFirstName());

				if (person.getMiddleName() != null) {

					preparedStatement.setString(i++, person.getMiddleName());

				} else {

					preparedStatement.setNull(i++, Types.NULL);
				}

				preparedStatement.setString(i++, person.getLastName());

				preparedStatement.setString(i++, person.getGender().toString());

				counter = new Integer(preparedStatement.executeUpdate());

				if (counter != null && counter.equals(1)) {

					this.connection.commit();

					msg = SAVE_SUCCESS;

					personCode = this.search();

					if (personCode != null) {

						person = search(personCode);

						msg += RECOVERY_SUCCESS;

					} else {

						msg += RECOVERY_NOT_SUCCESS;
					}

				} else {

					msg = SAVE_NOT_SUCCESS;
				}

			} catch (SQLException e) {
				// TODO: handle exception

				person = null;

				msg = SAVE_ERROR + e.getMessage();

				e.printStackTrace();

			} finally {

				if (preparedStatement != null) {

					try {

						preparedStatement.close();

					} catch (SQLException e) {
						// logar excecao
						e.printStackTrace();

					} catch (RuntimeException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

				if (FLAG.equals(true) && msg != null) {

					System.err.println(String.format("\n %s%s: %s", this.getClass().getSimpleName(),
							".salve(Person person)", msg));

					if (person != null) {

						System.err.println(String.format("\n %s %s", SAVE_DATA, person.toString()));
					}
				}
			}
		}

		// Information output

		return person;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.name.and.gender.INameAndGender#list()
	 */
	@Override
	public List<Person> list() {
		// TODO Auto-generated method stub

		// Variables declaration

		List<Person> persons = null;

		Person person = null;

		Integer personCode = null;

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		String msg = null;

		// Data processing

		try {

			query = "SELECT PERSON.PERSON_CODE FROM ";

			query += V_TABLE_NAME_AND_GENDER;

			query += " PERSON ";

			statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(query);

			if (resultSet.last()) {

				resultSet.beforeFirst();

				persons = new ArrayList<Person>();

				while (resultSet.next()) {

					personCode = new Integer(resultSet.getInt("PERSON_CODE"));

					person = this.search(personCode);

					if (person != null) {

						persons.add(person);
					}
				}

				msg = QUERY_SUCCESS;

			} else {

				msg = QUERY_NOT_SUCCESS;
			}

		} catch (Exception e) {
			// TODO: handle exception

			persons = null;

			msg = QUERY_ERROR + e.getMessage();

			e.printStackTrace();

		} finally {

			if (statement != null) {

				try {

					statement.close();

				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();

				} catch (RuntimeException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			if (resultSet != null) {

				try {

					resultSet.close();

				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();

				} catch (RuntimeException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			if (FLAG.equals(true) && msg != null) {

				System.err.println(String.format("\n %s%s: %s", this.getClass().getSimpleName(), ".list()", msg));

				if (persons != null && persons.size() > 0) {

					System.err.print(LIST_DATA);

					for (Person people : persons) {

						System.err.println(people.toString());
					}
				}
			}
		}

		// Information output

		return persons;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.name.and.gender.INameAndGender#update(vo.person.Person)
	 */
	@Override
	public Person update(Person person) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer count = null;

		int i = 1;

		// Data processing

		if (person != null) {

			try {

				query = "UPDATE ";

				query += V_TABLE_NAME_AND_GENDER;

				query += " SET ";

				query += "PERSON.FIRST_NAME = ?, ";

				query += "PERSON.MIDDLE_NAME = ?, ";

				query += "PERSON.LAST_NAME = ?, ";

				query += "PERSON.GENDER = ? ";

				query += "WHERE ";

				query += "PERSON.PERSON_CODE = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setString(i++, person.getFirstName());

				if (person.getMiddleName() != null && !person.getMiddleName().equals("")) {

					preparedStatement.setString(2, person.getMiddleName());

				} else {

					preparedStatement.setNull(2, Types.NULL);
				}

				if (person.getLastName() != null && !person.getLastName().equals("")) {

					preparedStatement.setString(3, person.getLastName());

				} else {

					preparedStatement.setNull(3, Types.NULL);
				}

				if (person.getGender() != null) {

					preparedStatement.setString(4, person.getGender().toString());

				} else {

					preparedStatement.setNull(4, Types.NULL);
				}

				preparedStatement.setInt(5, person.getPersonCode());

				count = new Integer(preparedStatement.executeUpdate());

				if (count != null && count.equals(1)) {

					this.connection.commit();

					msg = UPDATE_SUCCESS;

					person = search(person.getPersonCode());

				} else {

					msg = UPDATE_NOT_SUCCESS;
				}

			} catch (SQLException e) {
				// TODO: handle exception

				person = null;

				msg = UPDATE_ERROR + e.getMessage();

				e.printStackTrace();

			} finally {

				if (preparedStatement != null) {

					try {

						preparedStatement.close();

					} catch (SQLException e) {
						// TODO: handle exception
						e.printStackTrace();

					} catch (RuntimeException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

				if (FLAG.equals(true) && msg != null) {

					System.err.println(String.format("\n %s%s: %s", this.getClass().getSimpleName(),
							".update(Person person)", msg));

					if (person != null) {

						System.err.println(String.format("\n %s %s", UPDATE_DATA, person.toString()));
					}
				}
			}
		}

		// Information output

		return person;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.name.and.gender.INameAndGender#delete(vo.person.Person)
	 */
	@Override
	public Boolean delete(Person person) {
		// TODO Auto-generated method stub

		// Variables declaration

		Boolean flag = null;

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer count = null;

		int i = 1;

		// Data processing

		if (person != null) {

			try {

				query = "DELETE FROM ";

				query += V_TABLE_NAME_AND_GENDER;

				query += " WHERE ";

				query += "PERSON.PERSON_CODE = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, person.getPersonCode());

				count = new Integer(preparedStatement.executeUpdate());

				if (count != null && count.equals(1)) {

					this.connection.commit();

					flag = new Boolean(true);

					msg = DELETE_SUCCESS;

				} else {

					flag = new Boolean(false);

					msg = DELETE_NOT_SUCCESS;
				}

			} catch (SQLException e) {
				// TODO: handle exception

				flag = null;

				msg = DELETE_ERROR + e.getMessage();

				e.printStackTrace();

			} finally {

				if (preparedStatement != null) {

					try {

						preparedStatement.close();

					} catch (SQLException e) {
						// TODO: handle exception
						e.printStackTrace();

					} catch (RuntimeException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

				if (FLAG.equals(true) && msg != null) {

					System.err.println(String.format("\n %s%s: %s", this.getClass().getSimpleName(),
							".delete(Person person)", msg));

					System.err.println(String.format("\n %s %s", DELETE_DATA,
							(flag != null && flag.equals(true)) ? person != null ? person.toString() : ""
									: "Data not deleted"));
				}
			}
		}

		// Information output

		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.name.and.gender.INameAndGender#search()
	 */
	public Integer search() {
		// TODO Auto-generated method stub

		// Variables declaration

		Integer personCode = null;

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		// Data processing

		try {

			query = "SELECT PERSON.PERSON_CODE FROM ";

			query += V_TABLE_NAME_AND_GENDER;

			query += " PERSON ";

			query += "WHERE ";

			query += "PERSON.PERSON_CODE = ";

			query += "(SELECT MAX(PERSON.PERSON_CODE) FROM ";

			query += V_TABLE_NAME_AND_GENDER;

			query += " PERSON)";

			statement = this.connection.createStatement();

			resultSet = statement.executeQuery(query);

			if (resultSet.next()) {

				personCode = new Integer(resultSet.getInt("PERSON_CODE"));

			} else {

				personCode = new Integer(0);
			}

		} catch (SQLException e) {
			// TODO: handle exception

			personCode = null;

			e.printStackTrace();

		} finally {

			if (statement != null) {

				try {

					statement.close();

				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();

				} catch (RuntimeException e) {
					// TODO: handle exception
					e.printStackTrace();

				}
			}

			if (resultSet != null) {

				try {

					resultSet.close();

				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();

				} catch (RuntimeException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}

		// Information output

		return personCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.name.and.gender.INameAndGender#search(java.lang.Integer)
	 */
	public Person search(Integer personCode) {
		// TODO Auto-generated method stub

		// Variables declaration

		Person person = null;

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		int i = 1;

		// Data processing

		if (personCode != null) {

			try {

				query = "SELECT * FROM ";

				query += V_TABLE_NAME_AND_GENDER;

				query += " PERSON ";

				query += "WHERE ";

				query += "PERSON.PERSON_CODE = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, personCode.intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.next()) {

					person = new Person();

					person.setPersonCode(resultSet.getInt("PERSON_CODE"));

					person.setFirstName(resultSet.getString("FIRST_NAME"));

					if (resultSet.getString("MIDDLE_NAME") != null) {

						person.setMiddleName(resultSet.getString("MIDDLE_NAME"));
					}

					person.setLastName(resultSet.getString("LAST_NAME"));

					person.setGender(new Character(resultSet.getString("GENDER").charAt(0)));

				}

			} catch (SQLException e) {
				// TODO: handle exception

				person = null;

				e.printStackTrace();

			} finally {

				if (preparedStatement != null) {

					try {

						preparedStatement.close();

					} catch (SQLException e) {
						// TODO: handle exception
						e.printStackTrace();

					} catch (RuntimeException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

				if (resultSet != null) {

					try {

						resultSet.close();

					} catch (SQLException e) {
						// TODO: handle exception
						e.printStackTrace();

					} catch (RuntimeException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		}

		// Information output

		return person;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.name.and.gender.INameAndGender#search(java.lang.String)
	 */
	@Override
	public List<Person> search(String name) {
		// TODO Auto-generated method stub

		// Variables declaration

		List<Person> result = null;

		Person person = null;

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		int i = 1;

		// Data processing

		if (name != null) {

			try {

				name = "%" + name.toUpperCase() + "%";

				query = "SELECT * FROM ";

				query += V_TABLE_NAME_AND_GENDER;

				query += " WHERE ";

				query += "PERSON.FIRST_NAME || ' ' || PERSON.MIDDLE_NAME || ' ' || PERSON.LAST_NAME LIKE ?";

				preparedStatement = this.connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				preparedStatement.setString(i++, name);

				resultSet = preparedStatement.executeQuery();

				if (resultSet.last()) {

					resultSet.beforeFirst();

					result = new ArrayList<Person>();

					while (resultSet.next()) {

						person = new Person();

						person.setPersonCode(resultSet.getInt("PERSON_CODE"));

						person.setFirstName(resultSet.getString("FIRST_NAME"));

						if (resultSet.getString("MIDDLE_NAME") != null) {

							person.setMiddleName(resultSet.getString("MIDDLE_NAME"));
						}

						if (resultSet.getString("LAST_NAME") != null) {

							person.setLastName(resultSet.getString("LAST_NAME"));
						}

						if (resultSet.getString("GENDER") != null) {

							person.setGender(new Character(resultSet.getString("GENDER").charAt(0)));
						}

						result.add(person);
					}
				}

			} catch (SQLException e) {
				// TODO: handle exception

				result = null;

				e.printStackTrace();

			} finally {

				if (preparedStatement != null) {

					try {

						preparedStatement.close();

					} catch (SQLException e) {
						// TODO: handle exception
						e.printStackTrace();

					} catch (RuntimeException e) {
						// TODO: handle exception
						e.printStackTrace();
					}

					if (resultSet != null) {

						try {

							resultSet.close();

						} catch (SQLException e) {
							// TODO: handle exception
							e.printStackTrace();

						} catch (RuntimeException e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}
			}
		}

		// Information output

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.name.and.gender.INameAndGender#personCode()
	 */
	public Integer personCode() {

		// Variables declaration

		Integer personCode = null;

		// Data processing

		personCode = this.search();

		if (personCode != null) {

			personCode = new Integer(personCode.intValue() + 1);
		}

		// Information output

		return personCode;
	}
}
