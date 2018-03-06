package persistence.address.type;

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
import static persistence.Persistence.SAVE_SUCCESS;
import static persistence.Persistence.UPDATE_DATA;
import static persistence.Persistence.UPDATE_ERROR;
import static persistence.Persistence.UPDATE_NOT_SUCCESS;
import static persistence.Persistence.UPDATE_SUCCESS;
import static persistence.Persistence.V_TABLE_ADDRESS_TYPE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import br.edu.uniopet.ConnectionFactory;
import vo.address.type.AddressType;
import vo.address.type.AddressTypeConstraints;

/**
 * Class to implement the operations against to the database to the AddressType
 * entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
class AddressTypeDAO implements IAddressTypeDAO {

	/**
	 * Instance variables
	 */

	private Connection connection;

	/**
	 * 
	 * Default constructor
	 */
	public AddressTypeDAO() {
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
	 * @see persistence.address.type.IAddressType#salve(vo.address.AddressType)
	 */
	@Override
	public AddressType salve(AddressType addressType) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer count = null;

		AddressTypeConstraints addressTypeConstraints = null;

		int i = 1;

		// Data processing

		if (addressType != null) {

			addressTypeConstraints = this.constraintsAddressType();

			addressType.setAddressTypeCode(addressTypeConstraints.getId_tb_address_type());

			try {

				query = "INSERT INTO ";

				query += V_TABLE_ADDRESS_TYPE;

				query += " ADDRESS_TYPE (";

				query += "ADDRESS_TYPE.ID_TB_ADDRESS_TYPE, ";

				query += "ADDRESS_TYPE.TYPE) ";

				query += "VALUES (?, ?)";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, addressType.getAddressTypeCode());

				preparedStatement.setString(i++, addressType.getType());

				count = new Integer(preparedStatement.executeUpdate());

				if (count != null && count.equals(1)) {

					this.connection.commit();

					msg = SAVE_SUCCESS;

					addressTypeConstraints = search();

					addressType = this.search(addressTypeConstraints);

					if (addressType != null) {

						msg += RECOVERY_SUCCESS;

					} else {

						msg += RECOVERY_NOT_SUCCESS;
					}

				} else {

					this.connection.rollback();

					addressType = null;

					msg = SAVE_SUCCESS;
				}

			} catch (SQLException e) {

				try {

					this.connection.rollback();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				addressType = null;

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
							".salve(AddressType addressType)", msg));

					if (addressType != null) {

						System.err.println(String.format("\n %s %s", SAVE_DATA, addressType.toString()));
					}
				}
			}
		}

		// Information output

		return addressType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.type.IAddressType#list()
	 */
	@Override
	public Set<AddressType> list() {
		// TODO Auto-generated method stub

		// Variables declaration

		Set<AddressType> addressTypes = null;

		AddressType addressType = null;

		AddressTypeConstraints addressTypeConstraints = null;

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		String msg = null;

		// Data processing

		if (this.connection == null) {

			this.connection = ConnectionFactory.getInstance();

			if (this.connection == null) {

				return null;
			}
		}

		try {

			query = "SELECT ADDRESS_TYPE.ID_TB_ADDRESS_TYPE FROM ";

			query += V_TABLE_ADDRESS_TYPE;

			query += " ADDRESS_TYPE ";

			query += "ORDER BY ";

			query += "ADDRESS_TYPE.ID_TB_ADDRESS_TYPE ASC";

			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(query);

			if (resultSet != null && resultSet.last()) {

				resultSet.beforeFirst();

				addressTypes = new HashSet<AddressType>();

				while (resultSet.next()) {

					addressTypeConstraints = new AddressTypeConstraints();

					addressTypeConstraints.setId_tb_address_type(new Integer(resultSet.getInt("ID_TB_ADDRESS_TYPE")));

					addressType = this.search(addressTypeConstraints);

					if (addressType != null) {

						addressTypes.add(addressType);
					}
				}

				msg = QUERY_SUCCESS;

			} else {

				msg = QUERY_NOT_SUCCESS;
			}

		} catch (Exception e) {
			// TODO: handle exception

			addressTypes = null;

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

				System.err.println(
						String.format(String.format("\n %s%s: %s", this.getClass().getSimpleName(), ".list()", msg)));

				if (addressTypes != null && addressTypes.size() > 0) {

					System.err.println(String.format("\n %s", LIST_DATA));

					Iterator<AddressType> iterator = addressTypes.iterator();

					while (iterator.hasNext()) {

						addressType = iterator.next();

						System.err.println(addressType.toString());
					}
				}
			}
		}

		// Information output

		return addressTypes;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.type.IAddressType#update(vo.address.AddressType)
	 */
	@Override
	public AddressType update(AddressType addressType) {
		// TODO Auto-generated method stub

		// Variables declaration

		AddressTypeConstraints addressTypeConstraints = null;

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer count = null;

		int i = 1;

		// Data processing

		if (addressType != null) {

			try {

				query = "UPDATE ";

				query += V_TABLE_ADDRESS_TYPE;

				query += " ADDRESS_TYPE ";

				query += "SET ";

				query += "ADDRESS_TYPE.TYPE = ?";

				query += "WHERE ";

				query += "ADDRESS_TYPE.ID_TB_ADDRESS_TYPE = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setString(i++, addressType.getType());

				preparedStatement.setInt(i++, addressType.getAddressTypeCode().intValue());

				count = new Integer(preparedStatement.executeUpdate());

				if (count != null && count.equals(1)) {

					this.connection.commit();

					msg = UPDATE_SUCCESS;

					addressTypeConstraints = new AddressTypeConstraints();

					addressTypeConstraints.setId_tb_address_type(addressType.getAddressTypeCode());

					addressType = search(addressTypeConstraints);

					if (addressType != null) {

						msg += RECOVERY_SUCCESS;

					} else {

						msg += RECOVERY_NOT_SUCCESS;
					}

				} else {

					addressType = null;

					this.connection.rollback();

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

				addressType = null;

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

					System.err.println(String.format(String.format("\n %s%s: %s", this.getClass().getSimpleName(),
							".update(AddressType addressType)", msg)));

					if (addressType != null) {

						System.err.println(String.format("\n %s %s", UPDATE_DATA, addressType.toString()));
					}
				}
			}
		}

		// Information output

		return addressType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.type.IAddressType#delete(vo.address.AddressType)
	 */
	@Override
	public Boolean delete(AddressType addressType) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Boolean flag = null;

		Integer count = null;

		int i = 1;

		// Data processing

		if (addressType != null) {

			try {

				query = "DELETE FROM ";

				query += V_TABLE_ADDRESS_TYPE;

				query += " ADDRESS_TYPE ";

				query += "WHERE ";

				query += "ADDRESS_TYPE.ID_TB_ADDRESS_TYPE = ?";

				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setInt(i++, addressType.getAddressTypeCode().intValue());

				count = new Integer(preparedStatement.executeUpdate());

				if (count != null && count.equals(1)) {

					this.connection.commit();

					flag = new Boolean(true);

					msg = DELETE_SUCCESS;

				} else {

					this.connection.rollback();

					flag = new Boolean(false);

					msg = DELETE_NOT_SUCCESS;
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
							".delete(AddressType addressType)", msg));

					System.err.println(String.format("\n %s %s", DELETE_DATA, addressType.toString()));
				}
			}
		}

		// Information output

		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.type.IAddressType#search()
	 */
	@Override
	public AddressTypeConstraints search() {
		// TODO Auto-generated method stub

		// Variables declaration

		AddressTypeConstraints addressTypeConstraints = null;

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		// Data processing

		try {

			query = "SELECT ADDRESS_TYPE.ID_TB_ADDRESS_TYPE FROM ";

			query += V_TABLE_ADDRESS_TYPE;

			query += " ADDRESS_TYPE ";

			query += "WHERE ";

			query += "ADDRESS_TYPE.ID_TB_ADDRESS_TYPE = ";

			query += "(SELECT MAX(ADDRESS_TYPE.ID_TB_ADDRESS_TYPE)  FROM ";

			query += V_TABLE_ADDRESS_TYPE;

			query += " ADDRESS_TYPE)";

			statement = this.connection.createStatement();

			resultSet = statement.executeQuery(query);

			addressTypeConstraints = new AddressTypeConstraints();

			if (resultSet != null && resultSet.next()) {

				addressTypeConstraints.setId_tb_address_type(new Integer(resultSet.getInt("ID_TB_ADDRESS_TYPE")));

			} else {

				addressTypeConstraints.setId_tb_address_type(new Integer(0));
			}

		} catch (SQLException e) {
			// TODO: handle exception

			addressTypeConstraints = null;

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

		return addressTypeConstraints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.type.IAddressType#search(vo.address.
	 * ConstraintsAddressType)
	 */
	@Override
	public AddressType search(AddressTypeConstraints constraintsAddressType) {
		// TODO Auto-generated method stub

		// Variables declaration

		AddressType addressType = null;

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		int i = 1;

		// Data processing

		if (constraintsAddressType != null) {

			try {

				query = "SELECT * FROM ";

				query += V_TABLE_ADDRESS_TYPE;

				query += " ADDRESS_TYPE ";

				query += "WHERE ";

				query += "ADDRESS_TYPE.ID_TB_ADDRESS_TYPE = ?";

				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setInt(i++, constraintsAddressType.getId_tb_address_type().intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.next()) {

					addressType = new AddressType();

					addressType.setAddressTypeCode(new Integer(resultSet.getInt("ID_TB_ADDRESS_TYPE")));

					addressType.setType(resultSet.getString("TYPE"));
				}

			} catch (SQLException e) {
				// TODO: handle exception

				addressType = null;

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

		return addressType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.type.IAddressType#constraintsAddressType()
	 */
	public AddressTypeConstraints constraintsAddressType() {

		// Variables declaration

		AddressTypeConstraints addressTypeConstraints = null;

		// Data processing

		addressTypeConstraints = this.search();

		addressTypeConstraints
				.setId_tb_address_type(new Integer(addressTypeConstraints.getId_tb_address_type().intValue() + 1));

		// Information output

		return addressTypeConstraints;
	}
}
