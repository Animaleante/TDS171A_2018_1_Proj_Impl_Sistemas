package persistence.person;

import static persistence.Persistence.DELETE_DATA;
import static persistence.Persistence.DELETE_ERROR;
import static persistence.Persistence.DELETE_NOT_SUCCESS;
import static persistence.Persistence.DELETE_SUCCESS;
import static persistence.Persistence.FLAG;
import static persistence.Persistence.LIST_DATA;
import static persistence.Persistence.QUERY_ERROR;
import static persistence.Persistence.QUERY_NOT_SUCCESS;
import static persistence.Persistence.QUERY_SUCCESS;
import static persistence.Persistence.RECOVERY_ERROR;
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
import static persistence.Persistence.V_TABLE_PERSON;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.edu.uniopet.CPF;
import br.edu.uniopet.ElectoralTitle;
import br.edu.uniopet.Email;
import br.edu.uniopet.PersonDate;
import br.edu.uniopet.RG;
import persistence.address.AddressPersistence;
import persistence.phone.PhonePersistence;
import persistence.user.UserPersistence;
import vo.address.Address;
import vo.person.PersonConstraints;
import vo.person.Person;
import vo.phone.Phone;
import vo.user.UserConstraints;
import vo.user.User;

/**
 * Class to implement the operations against the database to the Person entity.
 * 
 * @author Baracho
 * 
 * @since 2017 nov, 05
 * 
 * @version 2.0
 * 
 */

/**
 * Class to implement the operations against to the database to the Person
 * entity.
 * 
 * @author Baracho
 * 
 * @since January 08, 2017
 * 
 * @version 1.0
 *
 */
class PersonDAO implements IPersonDAO {

	/**
	 * Instance variables
	 */

	private Connection connection;

	private UserPersistence userPersistence;

	private AddressPersistence addressPersistence;

	private PhonePersistence phonePersistence;

	/**
	 * Default constructor
	 */

	public PersonDAO() {
		// TODO Auto-generated constructor stub

		this.userPersistence = new UserPersistence();

		this.addressPersistence = new AddressPersistence();

		this.phonePersistence = new PhonePersistence();
	}

	/**
	 * Access methods
	 */

	/**
	 * @param connection
	 *            the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Class operations
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.IPersonDAO#salve(vo.person.Person)
	 */
	@Override
	public Person salve(Person person) {
		// TODO Auto-generated method stub

		// Variables declaration

		User user = null;

		Set<Address> addresses = null;

		Set<Phone> phones = null;

		PersonConstraints personConstraints = null;

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		int i = 1;

		// Data processing

		if (person != null && person.getUser() != null) {

			user = this.userPersistence.save(person.getUser());

			if (user != null) {

				person.setUser(user);

				personConstraints = this.personConstraints();

				person.setPersonCode(personConstraints.getId_tb_person());

				addresses = person.getAddresses();

				phones = person.getPhones();

				try {

					query = "INSERT INTO ";

					query += V_TABLE_PERSON;

					query += " PERSON (";

					query += "PERSON.ID_TB_PERSON, ";

					query += "PERSON.ID_TB_USER, ";

					query += "PERSON.FIRST_NAME, ";

					query += "PERSON.MIDDLE_NAME, ";

					query += "PERSON.LAST_NAME, ";

					query += "PERSON.DOB, ";

					query += "PERSON.GENDER, ";

					query += "PERSON.RG, ";

					query += "PERSON.CPF, ";

					query += "PERSON.ELECTORAL_TITLE, ";

					query += "PERSON.EMAIL, ";

					query += "PERSON.STATUS";

					query += ")";

					query += "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

					preparedStatement = this.connection.prepareStatement(query);

					preparedStatement.setInt(i++, person.getPersonCode());

					preparedStatement.setInt(i++, person.getUser().getUserCode());

					preparedStatement.setString(i++, person.getFirstName());

					if (person.getMiddleName() != null && !person.getMiddleName().equals("")) {

						preparedStatement.setString(i++, person.getMiddleName());

					} else {

						preparedStatement.setNull(i++, Types.NULL);
					}

					if (person.getLastName() != null && !person.getLastName().equals("")) {

						preparedStatement.setString(i++, person.getLastName());

					} else {

						preparedStatement.setNull(i++, Types.NULL);
					}

					if (person.getDateOfBirth() != null) {

						preparedStatement.setDate(i++, new Date(person.getDateOfBirth().getValue().getTime()));

					} else {

						preparedStatement.setNull(i++, Types.NULL);
					}

					if (person.getGender() != null) {

						preparedStatement.setString(i++, person.getGender().toString());

					} else {

						preparedStatement.setNull(i++, Types.NULL);
					}

					if (person.getRg() != null && !person.getRg().getValue().equals("")) {

						preparedStatement.setString(i++, person.getRg().getValue());

					} else {

						preparedStatement.setNull(i++, Types.NULL);
					}

					if (person.getCpf() != null && person.getCpf().isValid().booleanValue()) {

						preparedStatement.setString(i++, person.getCpf().getValueString());

					} else {

						preparedStatement.setNull(i++, Types.NULL);
					}

					if (person.getElectoralTitle() != null && !person.getElectoralTitle().getValue().equals("")) {

						preparedStatement.setString(i++, person.getElectoralTitle().getValue());

					} else {

						preparedStatement.setNull(i++, Types.NULL);
					}

					if (person.getEmail() != null
							&& (person.getEmail().getValue() != null && !person.getEmail().getValue().equals(""))) {

						preparedStatement.setString(i++, person.getEmail().getValue());

					} else {

						preparedStatement.setNull(i++, Types.NULL);
					}

					if (person.getStatus() != null) {

						if (person.getStatus().equals(true)) {

							preparedStatement.setInt(i++, 1);

						} else {

							if (person.getStatus().equals(false)) {

								preparedStatement.setInt(i++, 2);
							}
						}

					} else {

						preparedStatement.setInt(i++, 0);
					}

					counter = new Integer(preparedStatement.executeUpdate());

					if (counter != null && counter.equals(1)) {

						this.connection.commit();

						msg = SAVE_SUCCESS;

						personConstraints = this.search();

						if (personConstraints != null) {

							person = this.search(personConstraints);

							if (person != null) {

								msg += RECOVERY_SUCCESS;

							} else {

								msg += RECOVERY_NOT_SUCCESS;
							}

						} else {

							person = null;

							msg = RECOVERY_ERROR;
						}

						if (addresses != null && addresses.size() > 0) {

							person.setAddresses(addresses);

							addresses = this.addressPersistence.salve(person);

							if (addresses != null && addresses.size() > 0) {

								person.setAddresses(addresses);

							} else {

								person.setAddresses(null);
							}

						} else {

							person.setAddresses(null);
						}

						if (phones != null && phones.size() > 0) {

							person.setPhones(phones);

							phones = this.phonePersistence.save(person);

							if (phones != null && phones.size() > 0) {

								person.setPhones(phones);

							} else {

								person.setPhones(null);
							}

						} else {

							person.setPhones(null);
						}

					} else {

						person = null;

						this.connection.rollback();

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
								".save(Person person)", msg));

						if (person != null) {

							System.err.println(String.format("\n %s: %s", SAVE_DATA, person.toString()));
						}
					}
				}

			} else {

				if (this.connection != null) {

					try {

						this.connection.rollback();

						person = null;

					} catch (SQLException e) {
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
	 * @see persistence.person.IPersonDAO#list()
	 */
	@Override
	public List<Person> list() {
		// TODO Auto-generated method stub

		// Variables declaration

		List<Person> persons = null;

		Person person = null;

		PersonConstraints personConstraints = null;

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		String msg = null;

		// Data processing

		try {

			query = "SELECT PERSON.ID_TB_PERSON, PERSON.ID_TB_USER FROM ";

			query += V_TABLE_PERSON;

			query += " PERSON ";

			query += "ORDER BY PERSON.FIRST_NAME, MIDDLE_NAME, LAST_NAME ASC";

			statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(query);

			if (resultSet != null && resultSet.last()) {

				resultSet.beforeFirst();

				persons = new ArrayList<Person>();

				while (resultSet.next()) {

					personConstraints = new PersonConstraints();

					personConstraints.setId_tb_Person(new Integer(resultSet.getInt("ID_TB_PERSON")));

					personConstraints.setId_tb_User(new Integer(resultSet.getInt("ID_TB_USER")));

					person = search(personConstraints);

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

					System.err.print(String.format("\n %s", LIST_DATA));

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
	 * @see persistence.person.IPersonDAO#update(vo.person.Person)
	 */
	@Override
	public Person update(Person person) {
		// TODO Auto-generated method stub

		// Variables declaration

		User user = null;

		Set<Address> addresses = null;

		Set<Phone> phones = null;

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		int i = 1;

		PersonConstraints personConstraints = null;

		// Data processing

		if (person != null) {

			if (person.getUser() != null) {

				user = this.userPersistence.update(person.getUser());

				if (user != null) {

					person.setUser(user);

					addresses = person.getAddresses();

					phones = person.getPhones();

					try {

						query = "UPDATE ";

						query += V_TABLE_PERSON;

						query += " PERSON SET ";

						query += "PERSON.FIRST_NAME = ?, ";

						query += "PERSON.MIDDLE_NAME = ?, ";

						query += "PERSON.LAST_NAME = ?, ";

						query += "PERSON.DOB = ?, ";

						query += "PERSON.GENDER = ?, ";

						query += "PERSON.RG = ?, ";

						query += "PERSON.CPF = ?, ";

						query += "PERSON.TITULO_ELEITORAL = ?, ";

						query += "PERSON.EMAIL = ?, ";

						query += "PERSON.STATUS = ? ";

						query += "WHERE ";

						query += "PERSON.ID_TB_PERSON = ? AND PERSON.ID_TB_USER = ?";

						preparedStatement = this.connection.prepareStatement(query);

						preparedStatement.setString(i++, person.getFirstName());

						if (person.getMiddleName() != null && !person.getMiddleName().equals("")) {

							preparedStatement.setString(i++, person.getMiddleName());

						} else {

							preparedStatement.setNull(i++, Types.NULL);
						}

						if (person.getLastName() != null && !person.getLastName().equals("")) {

							preparedStatement.setString(i++, person.getLastName());

						} else {

							preparedStatement.setNull(i++, Types.NULL);
						}

						if (person.getDateOfBirth() != null) {

							preparedStatement.setDate(i++, new Date(person.getDateOfBirth().getValue().getTime()));

						} else {

							preparedStatement.setNull(i++, Types.NULL);
						}

						if (person.getGender() != null) {

							preparedStatement.setString(i++, person.getGender().toString());

						} else {

							preparedStatement.setNull(i++, Types.NULL);
						}

						if (person.getRg() != null && !person.getRg().getValue().equals("")) {

							preparedStatement.setString(i++, person.getRg().getValue());

						} else {

							preparedStatement.setNull(i++, Types.NULL);
						}

						if (person.getCpf() != null && person.getCpf().isValid().booleanValue()) {

							preparedStatement.setString(i++, person.getCpf().getValueString());

						} else {

							preparedStatement.setNull(i++, Types.NULL);
						}

						if (person.getElectoralTitle() != null && !person.getElectoralTitle().getValue().equals("")) {

							preparedStatement.setString(i++, person.getElectoralTitle().getValue());

						} else {

							preparedStatement.setNull(i++, Types.NULL);
						}

						if (person.getEmail() != null
								&& (person.getEmail().getValue() != null && !person.getEmail().getValue().equals(""))) {

							preparedStatement.setString(i++, person.getEmail().getValue());

						} else {

							preparedStatement.setNull(i++, Types.NULL);
						}

						if (person.getStatus() != null) {

							if (person.getStatus().equals(true)) {

								preparedStatement.setInt(i++, 1);

							} else {

								preparedStatement.setInt(i++, 2);
							}

						} else {

							preparedStatement.setInt(i++, 0);
						}

						preparedStatement.setInt(i++, person.getPersonCode());

						preparedStatement.setInt(i++, person.getUser().getUserCode());

						counter = new Integer(preparedStatement.executeUpdate());

						if (counter != null && counter.equals(1)) {

							this.connection.commit();

							msg = UPDATE_SUCCESS;

							personConstraints = new PersonConstraints();

							personConstraints.setId_tb_User(person.getUser().getUserCode());

							personConstraints.setId_tb_Person(person.getPersonCode());

							person = search(personConstraints);

							if (person != null) {

								if (addresses != null && addresses.size() > 0) {

									person.setAddresses(addresses);

									addresses = this.addressPersistence.update(person);

									if (addresses != null && addresses.size() > 0) {

										person.setAddresses(addresses);
									}
								}

								if (phones != null && phones.size() > 0) {

									person.setPhones(phones);

									phones = this.phonePersistence.update(person);

									if (phones != null && phones.size() > 0) {

										person.setPhones(phones);
									}
								}
							}

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

				} else {

					try {

						this.connection.rollback();

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					person = null;
				}
			}
		}

		// Information output

		return person;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.IPersonDAO#delete(vo.person.Person)
	 */
	@Override
	public Boolean delete(Person person) {
		// TODO Auto-generated method stub

		// Variables declaration

		Boolean flag = null;

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		int i = 1;

		// Data processing

		if (person != null) {

			if (person.getUser() != null) {

				if (this.userPersistence.delete(person.getUser()).equals(true)) {

					try {

						query = "DELETE FROM ";

						query += V_TABLE_PERSON;

						query += " PERSON ";

						query += "WHERE ";

						query += "PERSON.ID_TB_PERSON = ? AND PERSON.ID_TB_USER = ?";

						preparedStatement = this.connection.prepareStatement(query);

						preparedStatement.setInt(i++, person.getPersonCode());

						preparedStatement.setInt(i++, person.getUser().getUserCode());

						counter = new Integer(preparedStatement.executeUpdate());

						if (counter != null && counter.equals(1)) {

							this.connection.commit();

							flag = new Boolean(true);

							msg = DELETE_SUCCESS;

							if (person.getAddresses() != null && person.getAddresses().size() > 0) {

								this.addressPersistence.delete(person);
							}

							if (person.getPhones() != null && person.getPhones().size() > 0) {

								this.phonePersistence.delete(person);
							}

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
									".delete(person person)", msg));

							System.err.println(String.format(String.format("\n %s %s", DELETE_DATA,
									flag != null && flag.equals(true) ? person != null ? person.toString() : ""
											: "Is was not possivle do delete the data.")));
						}
					}

				} else {

					try {

						this.connection.rollback();

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					person = null;
				}
			}
		}

		// Information output

		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.IPersonDAO#search()
	 */
	@Override
	public PersonConstraints search() {
		// TODO Auto-generated method stub

		// Variables declaration

		Statement statement = null;

		PersonConstraints personConstraints = null;

		ResultSet resultSet = null;

		String query = null;

		// Data processing

		try {

			query = "SELECT PERSON.ID_TB_PERSON, PERSON.ID_TB_USER FROM ";

			query += V_TABLE_PERSON;

			query += " PERSON ";

			query += "WHERE ";

			query += "PERSON.ID_TB_PERSON = ";

			query += "(SELECT MAX(PERSON.ID_TB_PERSON) FROM ";

			query += V_TABLE_PERSON;

			query += " PERSON)";

			statement = this.connection.createStatement();

			resultSet = statement.executeQuery(query);

			personConstraints = new PersonConstraints();

			if (resultSet != null && resultSet.next()) {

				personConstraints.setId_tb_Person(new Integer(resultSet.getInt("ID_TB_PERSON")));

				personConstraints.setId_tb_User(new Integer(resultSet.getInt("ID_TB_USER")));

			} else {

				personConstraints.setId_tb_Person(new Integer(0));

				personConstraints.setId_tb_User(new Integer(0));
			}

		} catch (SQLException e) {
			// TODO: handle exception

			personConstraints = null;

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

		return personConstraints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.IPersonDAO#search(vo.person.PersonConstraints)
	 */
	@Override
	public Person search(PersonConstraints personConstraints) {
		// TODO Auto-generated method stub

		// Variables declaration

		Person person = null;

		User user = null;

		UserConstraints userConstraints = null;

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		int i = 1;

		// Data processing

		try {

			query = "SELECT * FROM ";

			query += V_TABLE_PERSON;

			query += " PERSON ";

			query += "WHERE PERSON.ID_TB_PERSON = ? AND PERSON.ID_TB_USER = ?";

			preparedStatement = this.connection.prepareStatement(query);

			preparedStatement.setInt(i++, personConstraints.getId_tb_person());

			preparedStatement.setInt(i++, personConstraints.getId_tb_user());

			resultSet = preparedStatement.executeQuery();

			if (resultSet != null && resultSet.next()) {

				person = new Person();

				person.setPersonCode(resultSet.getInt("ID_TB_PERSON"));

				userConstraints = new UserConstraints();

				userConstraints.setId_tb_user(new Integer(resultSet.getInt("ID_TB_USER")));

				user = this.userPersistence.search(userConstraints);

				person.setUser(user);

				person.setFirstName(resultSet.getString("FIRST_NAME"));

				if (resultSet.getString("MIDDLE_NAME") != null) {

					person.setMiddleName(resultSet.getString("MIDDLE_NAME"));
				}

				if (resultSet.getString("LAST_NAME") != null) {

					person.setLastName(resultSet.getString("LAST_NAME"));
				}

				if (resultSet.getDate("DOB") != null) {

					PersonDate personDate = new PersonDate();

					personDate.setValue(new java.util.Date(resultSet.getDate("DOB").getTime()));

					person.setDateOfBirth(personDate);
				}

				if (resultSet.getString("GENDER") != null) {

					person.setGender(new Character(resultSet.getString("GENDER").charAt(0)));
				}

				if (resultSet.getString("RG") != null) {

					RG rg = new RG();

					rg.setValue(resultSet.getString("RG"));

					person.setRg(rg);
				}

				if (resultSet.getString("CPF") != null) {

					CPF cpf = new CPF();

					cpf.setValue(resultSet.getString("CPF"));

					person.setCpf(cpf);
				}

				if (resultSet.getString("ELECTORAL_TITLE") != null) {

					ElectoralTitle electoralTitle = new ElectoralTitle();

					electoralTitle.setValue(resultSet.getString("ELECTORAL_TITLE"));

					person.setElectoralTitle(electoralTitle);
				}

				if (resultSet.getString("EMAIL") != null) {

					Email email = new Email();

					email.setValue(resultSet.getString("EMAIL"));

					person.setEmail(email);
				}

				if (resultSet.getInt("STATUS") > 0) {

					if (resultSet.getInt("STATUS") == 1) {

						person.setStatus(new Boolean(true));

					} else {

						if (resultSet.getInt("STATUS") == 2) {

							person.setStatus(new Boolean(false));
						}
					}
				}
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

		// Information output

		return person;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.IPersonDAO#search(java.lang.String)
	 */
	@Override
	public List<Person> search(String name) {
		// TODO Auto-generated method stub

		// Variables declaration

		PersonConstraints personConstraints = null;

		List<Person> persons = null;

		Person person = null;

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		int i = 1;

		// Data processing

		try {

			name = "%" + name.toUpperCase() + "%";

			query = "SELECT PERSON.ID_TB_PERSON, PERSON.ID_TB_USER FROM ";

			query += V_TABLE_PERSON;

			query += " PERSON ";

			query += "WHERE ";

			query += "PERSON.FIRST_NAME || ' ' || PERSON.MIDDLE_NAME || ' ' || PERSON.LAST_NAME ";

			query += "LIKE ?";

			preparedStatement = this.connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			preparedStatement.setString(i++, name);

			resultSet = preparedStatement.executeQuery();

			if (resultSet != null && resultSet.last()) {

				resultSet.beforeFirst();

				persons = new ArrayList<Person>();

				while (resultSet.next()) {

					personConstraints = new PersonConstraints();

					personConstraints.setId_tb_Person(new Integer(resultSet.getInt("ID_TB_PERSON")));

					personConstraints().setId_tb_User(new Integer(resultSet.getInt("ID_TB_USER")));

					person = this.search(personConstraints);

					if (person != null) {

						persons.add(person);
					}
				}
			}

		} catch (SQLException e) {
			// TODO: handle exception

			persons = null;

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

		// Information output

		return persons;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.IPersonDAO#personConstraints()
	 */
	public PersonConstraints personConstraints() {

		// Variables declaration

		PersonConstraints personConstraints = null;

		// Data processing

		personConstraints = this.search();

		if (personConstraints != null) {

			personConstraints.setId_tb_Person(new Integer(personConstraints.getId_tb_person().intValue() + 1));

		}

		// Information output

		return personConstraints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.IPersonDAO#validateCPF(br.edu.uniopet.CPF)
	 */
	public Boolean validateCPF(CPF cpf) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		Boolean flag = null;

		int i = 1;

		// Data processing

		if (cpf != null && cpf.getValueArray() != null) {

			try {

				query = "SELECT PERSON.CPF FROM ";

				query += V_TABLE_PERSON;

				query += " PERSON ";

				query += "WHERE ";

				query += "PERSON.CPF = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setString(i++, cpf.getValueString());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.next()) {

					flag = new Boolean(true);

				} else {

					flag = new Boolean(false);
				}

			} catch (SQLException e) {
				// TODO: handle exception

				flag = null;

				e.printStackTrace();

			} finally {

				if (preparedStatement != null) {

					try {

						preparedStatement.close();

					} catch (SQLException e2) {
						// TODO: handle exception

						e2.printStackTrace();
					}
				}
			}
		}

		// Information output

		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.IPersonDAO#listNames()
	 */
	public List<Person> listNames() {

		// Variables declaration

		List<Person> persons = null;

		Person person = null;

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		String msg = null;

		CPF cpf = null;

		// Data processing

		try {

			query = "SELECT ";

			query += "PERSON.ID_TB_PERSON, ";

			query += "PERSON.FIRST_NAME, ";

			query += "PERSON.MIDDLE_NAME, ";

			query += "PERSON.LAST_NAME, ";

			query += "PERSON.CPF ";

			query += "FROM ";

			query += V_TABLE_PERSON;

			query += " PERSON ";

			statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(query);

			if (resultSet != null && resultSet.last()) {

				resultSet.beforeFirst();

				persons = new ArrayList<Person>();

				while (resultSet.next()) {

					person = new Person();

					person.setPersonCode(new Integer(resultSet.getInt("id_tb_person")));

					person.setFirstName(resultSet.getString("FIRST_NAME"));

					person.setMiddleName(resultSet.getString("MIDDLE_NAME"));

					person.setLastName(resultSet.getString("LAST_NAME"));

					cpf = new CPF();

					cpf.setValue(resultSet.getString("CPF"));

					persons.add(person);
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

					System.err.print(String.format("\n %s", LIST_DATA));

					for (Person people : persons) {

						System.err.println(people.toString());
					}
				}
			}
		}

		// Information output

		return persons;
	}
}