package persistence.phone;

import static persistence.Persistence.DELETE_DATA;
import static persistence.Persistence.DELETE_ERROR;
import static persistence.Persistence.DELETE_NOT_SUCCESS;
import static persistence.Persistence.DELETE_SUCCESS;
import static persistence.Persistence.FLAG;
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
import static persistence.Persistence.V_TABLE_PHONE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import persistence.person.phone.PersonPhonePersistence;
import persistence.phone.type.PhoneTypePersistence;
import vo.person.Person;
import vo.person.phone.PersonPhoneConstraints;
import vo.phone.PhoneConstraints;
import vo.phone.Phone;
import vo.phone.type.PhoneTypeConstraints;
import vo.phone.type.PhoneType;

/**
 * Class to implement the operations against to the database to the Phone
 * entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
class PhoneDAO implements IPhoneDAO {

	/**
	 * Instance variables
	 */

	private Connection connection;

	private PersonPhonePersistence personPersistence;

	private PhoneTypePersistence phoneTypePersistence;

	/**
	 * Default constructor
	 */
	public PhoneDAO() {
		// TODO Auto-generated constructor stub

		this.personPersistence = new PersonPhonePersistence();

		this.phoneTypePersistence = new PhoneTypePersistence();
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
	 * @see persistence.phone.IPhoneDAO#save(vo.person.Person)
	 */
	@Override
	public Set<Phone> save(Person person) {

		// Variables declaration

		Phone phone = null;

		Iterator<Phone> iterator = null;

		Set<Phone> phones = null;

		Integer counter = null;

		// Data processing

		if (person != null) {

			if (person.getPhones() != null && person.getPhones().size() > 0) {

				iterator = person.getPhones().iterator();

				phones = new HashSet<Phone>();

				counter = new Integer(0);

				while (iterator.hasNext()) {

					phone = iterator.next();

					phone = this.save(phone);

					if (phone != null) {

						phones.add(phone);

						counter = new Integer(counter.intValue() + 1);
					}
				}
			}
		}

		if (counter != null && counter.equals(person.getPhones().size())) {

			try {

				this.connection.commit();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

			phones = null;

			try {

				this.connection.rollback();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Information output

		return phones;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.IPhoneDAO#save(vo.phone.Phone)
	 */
	@Override
	public Phone save(Phone phone) {
		// TODO Auto-generated method stub

		// Variables declaration

		PhoneConstraints phoneConstraints = null;

		Person person = null;

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer count = null;

		int i = 1;

		// Data processing

		if (phone != null && phone.getPerson() != null) {

			person = phone.getPerson();

			phoneConstraints = this.phoneConstraints();

			if (phoneConstraints != null) {

				phone.setPhoneCode(phoneConstraints.getId_tb_phone());

				try {

					query = "INSERT INTO ";

					query += V_TABLE_PHONE;

					query += " PHONE (";

					query += "PHONE.ID_TB_PHONE, ";

					query += "PHONE.ID_TB_PHONE_TYPE, ";

					query += "PHONE.PREFIX, ";

					query += "PHONE.PHONE_NUMBER) ";

					query += "VALUES (?, ?, ?, ?)";

					preparedStatement = this.connection.prepareStatement(query);

					preparedStatement.setInt(i++, phone.getPhoneCode().intValue());

					if (phone.getPhoneType() != null) {

						preparedStatement.setInt(i++, phone.getPhoneType().getPhoneTypeCode().intValue());

					} else {

						preparedStatement.setNull(i++, Types.NULL);
					}

					if (phone.getPrefixPhone() != null) {

						preparedStatement.setInt(i++, phone.getPrefixPhone().intValue());

					} else {

						preparedStatement.setNull(i++, Types.NULL);
					}

					if (phone.getNumberPhone() != null) {

						preparedStatement.setInt(i++, phone.getNumberPhone().intValue());

					} else {

						preparedStatement.setNull(i++, Types.NULL);
					}

					count = new Integer(preparedStatement.executeUpdate());

					if (count != null && count.equals(1)) {

						msg = SAVE_SUCCESS;

						phoneConstraints = this.search();

						phone = this.search(phoneConstraints);

						phone.setPerson(person);

						if (phone != null && phone.getPerson() != null) {

							msg += RECOVERY_SUCCESS;

							if (this.personPersistence.save(phone).equals(true)) {

								this.connection.commit();

							} else {

								phone = null;

								this.connection.rollback();
							}

						} else {

							phone = null;

							this.connection.rollback();

							msg += RECOVERY_NOT_SUCCESS;
						}

					} else {

						phone = null;

						this.connection.rollback();

						msg = SAVE_NOT_SUCCESS;
					}

				} catch (SQLException e) {

					phone = null;

					msg = SAVE_ERROR + e.getMessage();

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

					if (FLAG.equals(true) && msg != null) {

						System.err.println(String.format("\n %s%s: %s", this.getClass().getSimpleName(),
								"save(Phone phone)", msg));

						if (phone != null) {

							System.err.println(String.format("\n %s %s", SAVE_DATA, phone.toString()));
						}
					}
				}
			}
		}

		// Information output

		return phone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.IPhoneDAO#update(vo.person.Person)
	 */
	@Override
	public Set<Phone> update(Person person) {

		// Variables declaration

		Phone phone = null;

		Set<Phone> phones = null;

		Iterator<Phone> iterator = null;

		Integer counter = null;

		// Data processing

		if (person != null) {

			if (person.getPhones() != null && person.getPhones().size() > 0) {

				iterator = person.getPhones().iterator();

				phones = new HashSet<Phone>();

				counter = new Integer(0);

				while (iterator.hasNext()) {

					phone = iterator.next();

					phone = this.update(phone);

					if (phone != null) {

						phones.add(phone);

						counter = new Integer(counter.intValue() + 1);
					}
				}
			}
		}

		if (counter != null && counter.equals(person.getPhones().size())) {

			try {

				this.connection.commit();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

			phones = null;

			try {

				this.connection.rollback();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// Information output

		return phones;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.IPhoneDAO#update(vo.phone.Phone)
	 */
	@Override
	public Phone update(Phone phone) {
		// TODO Auto-generated method stub

		// Variables declaration

		PhoneConstraints phoneConstraints = null;

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		int i = 1;

		// Data processing

		if (phone != null) {

			try {

				query = "UPDATE ";

				query += V_TABLE_PHONE;

				query += " PHONE ";

				query += "SET ";

				query += "PHONE.ID_TB_PHONE_TYPE = ?, ";

				query += " PHONE.PREFIX = ?, ";

				query += " PHONE.PHONE_NUMBER = ?, ";

				query += "WHERE ";

				query += "PHONE.ID_TB_PHONE = ?";

				preparedStatement = this.connection.prepareStatement(query);

				if (phone.getPhoneType() != null) {

					preparedStatement.setInt(i++, phone.getPhoneType().getPhoneTypeCode().intValue());

				} else {

					preparedStatement.setNull(i++, Types.NULL);
				}

				if (phone.getPrefixPhone() != null) {

					preparedStatement.setInt(i++, phone.getPrefixPhone().intValue());

				} else {

					preparedStatement.setNull(i++, Types.NULL);
				}

				if (phone.getNumberPhone() != null) {

					preparedStatement.setInt(i++, phone.getNumberPhone().intValue());

				} else {

					preparedStatement.setNull(i++, Types.NULL);
				}

				preparedStatement.setString(i++, phone.getPhoneCode().toString());

				counter = new Integer(preparedStatement.executeUpdate());

				if (counter != null && counter.equals(1)) {

					msg = UPDATE_SUCCESS;

					phoneConstraints = this.search();

					phone = this.search(phoneConstraints);

					if (phone != null) {

						msg += RECOVERY_SUCCESS;

					} else {

						msg += RECOVERY_NOT_SUCCESS;
					}

				} else {

					phone = null;

					msg = UPDATE_NOT_SUCCESS;
				}

			} catch (SQLException e) {
				// TODO: handle exception

				phone = null;

				msg = UPDATE_ERROR + e.getMessage();

				e.printStackTrace();

			} finally {

				if (preparedStatement != null) {

					try {

						preparedStatement.close();

					} catch (SQLException e1) {
						// TODO: handle exception

						e1.printStackTrace();
					}
				}

				if (FLAG.equals(true) && msg != null) {

					System.err.println(
							String.format("\n %s%s: %s", this.getClass().getSimpleName(), ".update(Phone phone)", msg));

					if (phone != null) {

						System.err.println(String.format("\n %s %s", UPDATE_DATA, phone.toString()));
					}
				}
			}
		}

		// Information output

		return phone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.IPhoneDAO#delete(vo.person.Person)
	 */
	@Override
	public Boolean delete(Person person) {

		// Variables declaration

		Phone phone = null;

		Iterator<Phone> iterator = null;

		Boolean flag = null;

		Integer counter = null;

		// Data processing

		if (person != null) {

			if (person.getPhones() != null && person.getPhones().size() > 0) {

				iterator = person.getPhones().iterator();

				counter = new Integer(0);

				while (iterator.hasNext()) {

					phone = iterator.next();

					if (delete(phone).equals(true)) {

						counter = new Integer(counter.intValue() + 1);
					}
				}
			}
		}

		if (counter != null && counter.equals(person.getPhones().size())) {

			flag = new Boolean(true);

			try {

				this.connection.commit();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

			flag = new Boolean(false);

			try {

				this.connection.rollback();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Information output

		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.IPhoneDAO#delete(vo.phone.Phone)
	 */
	@Override
	public Boolean delete(Phone phone) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Boolean flag = null;

		Integer count = null;

		int i = 1;

		// Data processing

		if (phone != null) {

			try {

				query = "DELETE FROM ";

				query += V_TABLE_PHONE;

				query += " PHONE ";

				query += "WHERE ";

				query += "PHONE.ID_TB_PHONE = ?";

				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setInt(i++, phone.getPhoneCode().intValue());

				count = new Integer(preparedStatement.executeUpdate());

				if (count != null && count.equals(1)) {

					if (personPersistence.delete(phone).equals(true)) {

						flag = new Boolean(true);

						msg = DELETE_SUCCESS;

					} else {

						flag = new Boolean(false);

						msg = DELETE_NOT_SUCCESS;
					}

				} else {

					flag = new Boolean(false);

					msg = DELETE_ERROR;
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

					} catch (SQLException e2) {
						// TODO: handle exception

						e2.printStackTrace();
					}
				}

				if (FLAG.equals(true) && msg != null) {

					System.err.println(
							String.format("\n %s%s: %s", this.getClass().getSimpleName(), ".delete)Phone phone)", msg));

					if (phone != null) {

						System.err.println(String.format("\n %s %s", DELETE_DATA, phone.toString()));

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
	 * @see persistence.phone.IPhoneDAO#search()
	 */
	@Override
	public PhoneConstraints search() {
		// TODO Auto-generated method stub

		// Variables declaration

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		PhoneConstraints phoneConstraints = null;

		// Data processing

		try {

			query = "SELECT PHONE.ID_TB_PHONE FROM ";

			query += V_TABLE_PHONE;

			query += " PHONE ";

			query += "WHERE ";

			query += "PHONE.ID_TB_PHONE = ";

			query += "(SELECT MAX(PHONE.ID_TB_PHONE) FROM ";

			query += V_TABLE_PHONE;

			query += " PHONE)";

			statement = this.connection.createStatement();

			resultSet = statement.executeQuery(query);

			phoneConstraints = new PhoneConstraints();

			if (resultSet != null && resultSet.next()) {

				phoneConstraints.setId_tb_Phone(new Integer(resultSet.getInt("ID_TB_PHONE")));

			} else {

				phoneConstraints.setId_tb_Phone(new Integer(0));
			}

		} catch (SQLException e) {
			// TODO: handle exception

			phoneConstraints = null;

			e.printStackTrace();

		} finally {

			if (statement != null) {

				try {

					statement.close();

				} catch (SQLException e1) {
					// TODO: handle exception

					e1.printStackTrace();
				}
			}
		}

		// Information output

		return phoneConstraints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.IPhoneDAO#search(vo.phone.PhoneConstraints)
	 */
	@Override
	public Phone search(PhoneConstraints constraintsPhone) {
		// TODO Auto-generated method stub

		// Variables declaration

		Phone phone = null;

		PhoneTypeConstraints phoneTypeConstraints = null;

		PhoneType phoneType = null;

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		int i = 1;

		// Data processing

		if (constraintsPhone != null) {

			try {

				query = "SELECT * FROM ";

				query += V_TABLE_PHONE;

				query += " PHONE ";

				query += "WHERE ";

				query += "PHONE.ID_TB_PHONE = ? ";

				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setInt(i++, constraintsPhone.getId_tb_phone().intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.next()) {

					phone = new Phone();

					phone.setPhoneCode(new Integer(resultSet.getInt("ID_TB_PHONE")));

					phoneTypeConstraints = new PhoneTypeConstraints();

					phoneTypeConstraints.setId_tb_Phone_Type(new Integer(resultSet.getInt("ID_TB_PHONE_TYPE")));

					phoneType = this.phoneTypePersistence.search(phoneTypeConstraints);

					if (phoneType != null) {

						phone.setPhoneType(phoneType);
					}

					if (resultSet.getInt("PREFIX") > 0) {

						phone.setPrefixPhone(new Integer(resultSet.getInt("PREFIX")));
					}

					if (resultSet.getInt("PHONE_NUMBER") != 0) {

						phone.setNumberPhone(new Integer(resultSet.getInt("PHONE_NUMBER")));
					}
				}

			} catch (SQLException e) {
				// TODO: handle exception

				phone = null;

				e.printStackTrace();

			} finally {

				if (preparedStatement != null) {

					try {

						preparedStatement.close();

					} catch (SQLException e1) {
						// TODO: handle exception

						e1.printStackTrace();
					}
				}
			}
		}

		// Information output

		return phone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.IPhoneDAO#search(vo.person.Person)
	 */
	@Override
	public Set<Phone> search(Person person) {
		// TODO Auto-generated method stub

		// Variables declaration

		Set<Phone> phones = null;

		Phone phone = null;

		PhoneConstraints phoneConstraints = null;

		Set<PersonPhoneConstraints> personPhones = null;

		PersonPhoneConstraints personPhoneConstraints = null;

		Iterator<PersonPhoneConstraints> iterator = null;

		// Data processing

		if (person != null) {

			personPhones = this.personPersistence.search(person);

			if (personPhones != null && personPhones.size() > 0) {

				iterator = personPhones.iterator();

				phones = new HashSet<Phone>();

				while (iterator.hasNext()) {

					personPhoneConstraints = iterator.next();

					phoneConstraints = new PhoneConstraints();

					phoneConstraints.setId_tb_Phone(personPhoneConstraints.getId_tb_phone());

					phone = this.search(phoneConstraints);

					if (phone != null) {

						phones.add(phone);
					}
				}
			}

		}

		// Information output

		return phones;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.IPhoneDAO#constraintsPhone()
	 */
	@Override
	public PhoneConstraints phoneConstraints() {
		// TODO Auto-generated method stub

		// Variables declaration

		PhoneConstraints phoneConstraints = null;

		// Data processing

		phoneConstraints = this.search();

		if (phoneConstraints != null) {

			phoneConstraints.setId_tb_Phone(phoneConstraints.getId_tb_phone().intValue() + 1);
		}

		// Information output

		return phoneConstraints;
	}
}
