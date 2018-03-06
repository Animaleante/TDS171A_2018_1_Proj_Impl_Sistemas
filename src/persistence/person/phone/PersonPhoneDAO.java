package persistence.person.phone;

import static persistence.Persistence.DELETE_DATA;
import static persistence.Persistence.DELETE_ERROR;
import static persistence.Persistence.DELETE_NOT_SUCCESS;
import static persistence.Persistence.DELETE_SUCCESS;
import static persistence.Persistence.FLAG;
import static persistence.Persistence.SAVE_DATA;
import static persistence.Persistence.SAVE_ERROR;
import static persistence.Persistence.SAVE_NOT_SUCCESS;
import static persistence.Persistence.SAVE_SUCCESS;
import static persistence.Persistence.V_TABLE_PERSON_PHONE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import vo.person.Person;
import vo.person.phone.PersonPhoneConstraints;
import vo.phone.PhoneConstraints;
import vo.phone.Phone;

/**
 * Class to implement the operations against to the database to the PersonPhone
 * entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
class PersonPhoneDAO implements IPersonPhone {

	/**
	 * Instance variables
	 */

	private Connection connection;

	/**
	 * Default constructor
	 */
	public PersonPhoneDAO() {
		// TODO Auto-generated constructor stub
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
	 * @see persistence.person.phone.IPersonPhone#save(vo.person.Person)
	 */
	@Override
	public Boolean save(Person person) {

		// Variables declaration

		Phone phone = null;

		Iterator<Phone> iterator = null;

		Integer counter = null;

		Boolean flag = null;

		// Data processing

		if (person != null) {

			if (person.getPhones() != null && person.getPhones().size() > 0) {

				iterator = person.getPhones().iterator();

				counter = new Integer(0);

				while (iterator.hasNext()) {

					phone = iterator.next();

					if (this.save(phone).equals(true)) {

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

			flag = new Boolean(true);

		} else {

			try {

				this.connection.commit();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			flag = new Boolean(false);
		}

		// Information output

		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.phone.IPersonPhone#save(vo.phone.Phone)
	 */
	@Override
	public Boolean save(Phone phone) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Boolean flag = null;

		Integer counter = null;

		int i = 1;

		// Data processing

		if (phone != null && (phone.getPerson() != null && phone.getPerson().getUser() != null)) {

			try {

				query = "INSERT INTO ";

				query += V_TABLE_PERSON_PHONE;

				query += " PERSON_PHONE ( ";

				query += "PERSON_PHONE.ID_TB_PERSON, ";

				query += "PERSON_PHONE.ID_TB_USER, ";

				query += " PERSON_PHONE.ID_TB_PHONE";

				query += ") VALUES (?, ?, ?)";

				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setInt(i++, phone.getPerson().getPersonCode().intValue());

				preparedStatement.setInt(i++, phone.getPerson().getUser().getUserCode().intValue());

				preparedStatement.setInt(i++, phone.getPhoneCode().intValue());

				counter = new Integer(preparedStatement.executeUpdate());

				if (counter != null && counter.equals(1)) {

					flag = new Boolean(true);

					msg = SAVE_SUCCESS;

				} else {

					flag = new Boolean(false);

					msg = SAVE_NOT_SUCCESS;
				}

			} catch (SQLException e) {

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

					System.err.println(
							String.format("\n %s%s: %s", this.getClass().getSimpleName(), ".salve(Phone phone)", msg));

					if (phone != null) {

						System.err.println(String.format("\n %s %s", SAVE_DATA, phone.toString()));
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
	 * @see persistence.person.phone.IPersonPhone#delete(vo.person.Person)
	 */
	@Override
	public Boolean delete(Person person) {
		// TODO Auto-generated method stub

		// Variables declaration

		Phone phone = null;

		Iterator<Phone> iterator;

		Integer counter = null;

		Boolean flag = null;

		// Data processing

		if (person != null) {

			if (person.getPhones() != null && person.getPhones().size() > 0) {

				iterator = person.getPhones().iterator();

				counter = new Integer(0);

				while (iterator.hasNext()) {

					phone = iterator.next();

					if (this.delete(phone).equals(true)) {

						counter = new Integer(counter.intValue() + 1);
					}
				}
			}
		}

		if (counter != null && counter.equals(person.getPhones().size())) {

			flag = new Boolean(true);

		} else {

			flag = new Boolean(false);

		}

		// Information output

		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.phone.IPersonPhone#delete(vo.phone.Phone)
	 */
	@Override
	public Boolean delete(Phone phone) {

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

				query += V_TABLE_PERSON_PHONE;

				query += " PERSON_PHONE ";

				query += "WHERE ";

				query += "PERSON_PHONE.ID_TB_PHONE = ?";

				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setInt(i++, phone.getPhoneCode().intValue());

				count = new Integer(preparedStatement.executeUpdate());

				if (count != null && count.equals(1)) {

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

					} catch (SQLException e2) {
						// TODO: handle exception

						e2.printStackTrace();
					}
				}

				if (FLAG.equals(true) && msg != null) {

					System.err.println(
							String.format("\n %s%s: %s", this.getClass().getSimpleName(), ".delete(Phone phone)", msg));

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
	 * @see persistence.person.phone.IPersonPhone#search(vo.person.Person)
	 */
	@Override
	public Set<PersonPhoneConstraints> search(Person person) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		PersonPhoneConstraints personPhoneConstraints = null;

		Set<PersonPhoneConstraints> personPhonesConstraints = null;

		int i = 1;

		// Data processing

		if (person != null) {

			try {

				query = "SELECT * FROM ";

				query += V_TABLE_PERSON_PHONE;

				query += " PERSON_PHONE ";

				query += "WHERE ";

				query += "PERSON_PHONE.ID_TB_USER = ? AND PERSON_PHONE.ID_TB_PERSON = ?";

				preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				preparedStatement.setInt(i++, person.getUser().getUserCode().intValue());

				preparedStatement.setInt(i++, person.getPersonCode().intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.last()) {

					resultSet.beforeFirst();

					personPhonesConstraints = new HashSet<PersonPhoneConstraints>();

					while (resultSet.next()) {

						personPhoneConstraints = new PersonPhoneConstraints();

						personPhoneConstraints.setId_tb_Phone(new Integer(resultSet.getInt("ID_TB_PHONE")));

						personPhoneConstraints.setId_tb_User(new Integer(resultSet.getInt("ID_TB_USER")));

						personPhoneConstraints.setId_tb_Person(new Integer(resultSet.getInt("ID_TB_PERSON")));

						if (personPhoneConstraints != null) {

							personPhonesConstraints.add(personPhoneConstraints);
						}
					}
				}

			} catch (SQLException e) {
				// TODO: handle exception

				personPhoneConstraints = null;

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

		return personPhonesConstraints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.person.phone.IPersonPhone#search(vo.phone.PhoneConstraints)
	 */
	@Override
	public PersonPhoneConstraints search(PhoneConstraints phoneConstraints) {
		// TODO Auto-generated method stub

		// Variables declaration

		PersonPhoneConstraints personPhoneConstraints = null;

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		int i = 1;

		// Data processing

		if (phoneConstraints != null) {

			try {

				query = "SELECT * FROM ";

				query += V_TABLE_PERSON_PHONE;

				query += " PERSON_PHONE ";

				query += "WHERE ";

				query += "PERSON_PHONE.ID_TB_PHONE = ?";

				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setInt(i++, phoneConstraints.getId_tb_phone().intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.next()) {

					personPhoneConstraints = new PersonPhoneConstraints();

					personPhoneConstraints.setId_tb_Phone(new Integer(resultSet.getInt("ID_TB_PHONE")));

					personPhoneConstraints.setId_tb_Person(new Integer(resultSet.getInt("ID_TB_PERSON")));

					personPhoneConstraints.setId_tb_User(new Integer(resultSet.getInt("ID_TB_USER")));
				}

			} catch (SQLException e) {
				// TODO: handle exception

				personPhoneConstraints = null;

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

		return personPhoneConstraints;
	}
}
