package persistence.phone.type;

import static persistence.Persistence.DELETE_DATA;
import static persistence.Persistence.DELETE_ERROR;
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
import static persistence.Persistence.V_TABLE_PHONE_TYPE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import vo.phone.type.PhoneTypeConstraints;
import vo.phone.type.PhoneType;

/**
 * Class to implement the operations against to the database to the PhoneType
 * entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
class PhoneTypeDAO implements IPhoneTypeDAO {

	/**
	 * Instance variables
	 */

	private Connection connection;

	/**
	 * Default constructor
	 */

	public PhoneTypeDAO() {
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
	 * @see persistence.phone.type.IPhoneTypeDAO#salve(vo.phone.type.PhoneType)
	 */
	@Override
	public PhoneType salve(PhoneType phoneType) {
		// TODO Auto-generated method stub

		// Variables declaration

		PhoneTypeConstraints phoneTypeConstraints = null;

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		int i = 1;

		// Data processing

		if (phoneType != null) {

			phoneTypeConstraints = this.constraintsPhoneType();

			phoneType.setPhoneTypeCode(phoneTypeConstraints.getId_tb_phone_type());

			try {

				query = "INSERT INTO ";

				query += V_TABLE_PHONE_TYPE;

				query += " PHONE_TYPE (";

				query += "PHONE_TYPE.ID_TB_PHONE_TYPE, ";

				query += "PHONE_TYPE.TYPE) ";

				query += "VALUES (?, ?)";

				preparedStatement = this.connection.prepareStatement(query);

				i = 1;

				preparedStatement.setInt(i++, phoneType.getPhoneTypeCode().intValue());

				preparedStatement.setString(i++, phoneType.getType());

				counter = new Integer(preparedStatement.executeUpdate());

				if (counter != null && counter.equals(1)) {

					this.connection.commit();

					msg = SAVE_SUCCESS;

					phoneTypeConstraints = this.search();

					phoneType = this.search(phoneTypeConstraints);

					if (phoneType != null) {

						msg += RECOVERY_SUCCESS;

					} else {

						msg += RECOVERY_NOT_SUCCESS;
					}

				} else {

					this.connection.rollback();

					phoneType = null;

					msg = SAVE_NOT_SUCCESS;
				}

			} catch (SQLException e) {

				try {

					this.connection.rollback();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				phoneType = null;

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
							".save(PhoneType phoneType)", msg));

					if (phoneType != null) {

						System.err.println(String.format("\n %s %s", SAVE_DATA, phoneType.toString()));
					}
				}
			}
		}

		// Information output

		return phoneType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.type.IPhoneTypeDAO#list()
	 */
	@Override
	public Set<PhoneType> list() {
		// TODO Auto-generated method stub

		// Variables declaration

		Set<PhoneType> phoneTypes = null;

		PhoneType phoneType = null;

		PhoneTypeConstraints phoneTypeConstraints = null;

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		String msg = null;

		// Data processing

		try {

			query = "SELECT ID_TB_PHONE_TYPE FROM ";

			query += V_TABLE_PHONE_TYPE;

			query += " PHONE_TYPE ";

			query += "ORDER BY ";

			query += "PHONE_TYPE.ID_TB_PHONE_TYPE ASC";

			statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(query);

			if (resultSet != null && resultSet.last()) {

				resultSet.beforeFirst();

				phoneTypes = new HashSet<PhoneType>();

				while (resultSet.next()) {

					phoneTypeConstraints = new PhoneTypeConstraints();

					phoneTypeConstraints.setId_tb_Phone_Type(new Integer(resultSet.getInt("ID_TB_PHONE_TYPE")));

					phoneType = this.search(phoneTypeConstraints);

					if (phoneType != null) {

						phoneTypes.add(phoneType);
					}
				}

				msg = QUERY_SUCCESS;

			} else {

				msg = QUERY_NOT_SUCCESS;
			}

		} catch (Exception e) {
			// TODO: handle exception

			phoneTypes = null;

			msg = QUERY_ERROR + e.getMessage();

			e.printStackTrace();

		} finally {

			if (statement != null) {

				try {

					statement.close();

				} catch (SQLException e2) {
					// TODO: handle exception

					e2.printStackTrace();
				}
			}

			if (FLAG.equals(true) && msg != null) {

				System.err.println(String.format("\n %s%s: %s", this.getClass().getSimpleName(), ".list()", msg));

				if (phoneTypes != null && phoneTypes.size() > 0) {

					Iterator<PhoneType> iterator = phoneTypes.iterator();

					System.err.println(String.format("\n %s", LIST_DATA));

					while (iterator.hasNext()) {

						phoneType = iterator.next();

						System.err.println(phoneType.toString());
					}
				}
			}
		}

		// Information output

		return phoneTypes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.type.IPhoneTypeDAO#update(vo.phone.PhoneType)
	 */
	@Override
	public PhoneType update(PhoneType phoneType) {
		// TODO Auto-generated method stub

		// Variables declaration

		PhoneTypeConstraints phoneTypeConstraints = null;

		Statement statement = null;

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		int i = 1;

		// Data processing

		if (phoneType != null) {

			try {

				query = "UPDATE ";

				query += V_TABLE_PHONE_TYPE;

				query += " PHONE_TYPE SET ";

				query += "PHONE_TYPE.TYPE = ?";

				query += "WHERE ";

				query += "PHONE_TYPE.ID_TB_PHONE_TYPE = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setString(i++, phoneType.getType());

				preparedStatement.setInt(i++, phoneType.getPhoneTypeCode().intValue());

				counter = new Integer(preparedStatement.executeUpdate());

				if (counter != null && counter.equals(1)) {

					this.connection.commit();

					msg = UPDATE_SUCCESS;

					phoneTypeConstraints = new PhoneTypeConstraints();

					phoneTypeConstraints.setId_tb_Phone_Type(phoneType.getPhoneTypeCode());

					phoneType = search(phoneTypeConstraints);

					if (phoneType != null) {

						msg += RECOVERY_NOT_SUCCESS;

					} else {

						msg += RECOVERY_NOT_SUCCESS;
					}

				} else {

					this.connection.rollback();

					phoneType = null;

					msg = UPDATE_NOT_SUCCESS;
				}

			} catch (SQLException e) {
				// TODO: handle exception

				try {

					this.connection.rollback();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				phoneType = null;

				msg = UPDATE_ERROR + e.getMessage();

				e.printStackTrace();

			} finally {

				if (statement != null) {

					try {

						preparedStatement.close();

					} catch (SQLException e1) {
						// TODO: handle exception

						e1.printStackTrace();
					}
				}

				if (FLAG.equals(true) && msg != null) {

					System.err.println(String.format("\n %s%s: %s", this.getClass().getSimpleName(),
							".update(PhoneType phoneType)", msg));

					if (phoneType != null) {

						System.err.println(String.format("\n %s %s", UPDATE_DATA, phoneType.toString()));
					}
				}
			}
		}

		// Information output

		return phoneType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.type.IPhoneTypeDAO#delete(vo.phone.PhoneType)
	 */
	@Override
	public Boolean delete(PhoneType phoneType) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Boolean flag = null;

		Integer count = null;

		int i = 1;

		// Data processing

		if (phoneType != null) {

			try {

				query = "DELETE FROM ";

				query += V_TABLE_PHONE_TYPE;

				query += " PHONE_TYPE ";

				query += "WHERE ";

				query += "PHONE_TYPE.ID_TB_PHONE_TYPE = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, phoneType.getPhoneTypeCode().intValue());

				count = new Integer(preparedStatement.executeUpdate());

				if (count != null && count.equals(1)) {

					this.connection.commit();

					flag = new Boolean(true);

					msg = DELETE_SUCCESS;

				} else {

					this.connection.rollback();

					flag = new Boolean(false);

					msg = DELETE_SUCCESS;
				}

			} catch (SQLException e) {
				// TODO: handle exception

				try {

					this.connection.rollback();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

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

					System.err.println(String.format("\n %s%s: %s", this.getClass().getSimpleName(),
							".delete(PhoneType phoneType)", msg));

					System.err.println(String.format("\n %s %s", DELETE_DATA, phoneType.toString()));
				}
			}
		}

		// Information output

		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.type.IPhoneTypeDAO#search()
	 */
	@Override
	public PhoneTypeConstraints search() {
		// TODO Auto-generated method stub

		// Variables declaration

		PhoneTypeConstraints phoneTypeConstraints = null;

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		// Data processing

		try {

			query = "SELECT PHONE_TYPE.ID_TB_PHONE_TYPE FROM ";

			query += V_TABLE_PHONE_TYPE;

			query += " PHONE_TYPE ";

			query += "WHERE ";

			query += "PHONE_TYPE.ID_TB_PHONE_TYPE = ";

			query += "(SELECT MAX(PHONE_TYPE.ID_TB_PHONE_TYPE) FROM ";

			query += V_TABLE_PHONE_TYPE;

			query += " PHONE_TYPE)";

			statement = this.connection.createStatement();

			resultSet = statement.executeQuery(query);

			phoneTypeConstraints = new PhoneTypeConstraints();

			if (resultSet != null && resultSet.next()) {

				phoneTypeConstraints.setId_tb_Phone_Type(new Integer(resultSet.getInt("ID_TB_PHONE_TYPE")));

			} else {

				phoneTypeConstraints.setId_tb_Phone_Type(new Integer(0));
			}

		} catch (SQLException e) {
			// TODO: handle exception

			phoneTypeConstraints = null;

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

		return phoneTypeConstraints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * persistence.phone.type.IPhoneTypeDAO#search(vo.phone.ConstraintsPhoneType)
	 */
	@Override
	public PhoneType search(PhoneTypeConstraints constraintsPhoneType) {
		// TODO Auto-generated method stub

		// Variables declaration

		PhoneType phoneType = null;

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		int i = 1;

		// Data processing

		if (constraintsPhoneType != null) {

			try {

				query = "SELECT * FROM ";

				query += V_TABLE_PHONE_TYPE;

				query += " PHONE_TYPE ";

				query += "WHERE ";

				query += "PHONE_TYPE.ID_TB_PHONE_TYPE = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, constraintsPhoneType.getId_tb_phone_type().intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.next()) {

					phoneType = new PhoneType();

					phoneType.setPhoneTypeCode(new Integer(resultSet.getInt("ID_TB_PHONE_TYPE")));

					phoneType.setType(resultSet.getString("TYPE"));
				}

			} catch (SQLException e) {
				// TODO: handle exception

				phoneType = null;

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

		return phoneType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.phone.type.IPhoneTypeDAO#constraintsPhoneType()
	 */
	public PhoneTypeConstraints constraintsPhoneType() {

		// Variables declaration

		PhoneTypeConstraints phoneTypeConstraints = null;

		// Data processing

		phoneTypeConstraints = this.search();

		if (phoneTypeConstraints != null) {

			phoneTypeConstraints
					.setId_tb_Phone_Type(new Integer(phoneTypeConstraints.getId_tb_phone_type().intValue() + 1));
		}

		// Information output

		return phoneTypeConstraints;
	}
}
