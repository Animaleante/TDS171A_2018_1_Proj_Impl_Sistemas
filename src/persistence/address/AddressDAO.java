package persistence.address;

import static persistence.Persistence.DELETE_DATA;
import static persistence.Persistence.DELETE_ERROR;
import static persistence.Persistence.DELETE_NOT_SUCCESS;
import static persistence.Persistence.DELETE_SUCCESS;
import static persistence.Persistence.FLAG;
import static persistence.Persistence.RECOVERY_NOT_SUCCESS;
import static persistence.Persistence.RECOVERY_SUCCESS;
import static persistence.Persistence.SAVE_DATA;
import static persistence.Persistence.SAVE_ERROR;
import static persistence.Persistence.SAVE_SUCCESS;
import static persistence.Persistence.UPDATE_DATA;
import static persistence.Persistence.UPDATE_ERROR;
import static persistence.Persistence.UPDATE_NOT_SUCCESS;
import static persistence.Persistence.UPDATE_SUCCESS;
import static persistence.Persistence.V_TABLE_ADDRESS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import persistence.address.type.AddressTypePersistence;
import persistence.cep.database.StreetPersistence;
import persistence.person.PersonPersistence;
import vo.address.Address;
import vo.address.AddressConstraints;
import vo.address.type.AddressType;
import vo.address.type.AddressTypeConstraints;
import vo.cep.StreetConstraints;
import vo.cep.Street;
import vo.person.PersonConstraints;
import vo.person.Person;

/**
 * Class to implement the operations against to the database to the Address
 * entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
class AddressDAO implements IAddressDAO {

	/**
	 * Instance variables
	 */

	private Connection connection;

	private StreetPersistence streetPersistence;

	private AddressTypePersistence addressTypePersistence;

	/**
	 * Default constructor
	 */
	public AddressDAO() {
		// TODO Auto-generated constructor stub

		this.streetPersistence = new StreetPersistence();

		this.addressTypePersistence = new AddressTypePersistence();
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
	 * @see persistence.address.IAddressDAO#salve(vo.person.Person)
	 */
	@Override
	public Set<Address> salve(Person person) {

		// Instance variables

		Address address = null;

		Iterator<Address> iterator = null;

		Set<Address> adresses = null;

		Integer counter = null;

		// Data processing

		if (person != null && (person.getAddresses() != null && person.getAddresses().size() > 0)) {

			iterator = person.getAddresses().iterator();

			adresses = new HashSet<Address>();

			counter = new Integer(0);

			while (iterator.hasNext()) {

				address = iterator.next();

				address = this.salve(address);

				if (address != null) {

					adresses.add(address);

					counter = new Integer(counter.intValue() + 1);
				}
			}
		}

		if (counter != null && counter.equals(person.getAddresses().size())) {

			if (this.connection != null) {

				try {

					this.connection.commit();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {

				adresses = null;

				if (this.connection != null) {

					try {

						this.connection.rollback();

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		} else {

			adresses = null;

			if (this.connection != null) {

				try {

					this.connection.rollback();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// Information output

		return adresses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.IAddressDAO#salve(vo.address.Address)
	 */
	@Override
	public Address salve(Address address) {
		// TODO Auto-generated method stub

		// Variables declaration

		AddressConstraints addressConstraints = null;

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer count = null;

		int i = 1;

		// Data processing

		if (address != null) {

			addressConstraints = this.addressConstraints();

			if (addressConstraints != null) {

				address.setAddressCode(addressConstraints.getId_tb_address());

				try {

					query = "INSERT INTO ";

					query += V_TABLE_ADDRESS;

					query += " ADDRESS (";

					query += "ADDRESS.ID_TB_ADDRESS, ";

					query += "ADDRESS.ID_TB_USER, ";

					query += "ADDRESS.ID_TB_PERSON, ";

					query += "ADDRESS.CD_LOGRADOURO, ";

					query += "ADDRESS.NO_LOGRADOURO_CEP, ";

					query += "ADDRESS.ID_TB_ADDRESS_TYPE, ";

					query += "ADDRESS.ADDRESS_NUMBER, ";

					query += "ADDRESS.COMPLEMENT ";

					query += ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

					preparedStatement = connection.prepareStatement(query);

					preparedStatement.setInt(i++, address.getAddressCode().intValue());

					preparedStatement.setInt(i++, address.getPerson().getUser().getUserCode().intValue());

					preparedStatement.setInt(i++, address.getPerson().getPersonCode().intValue());

					if (address.getStreet() != null && address.getStreet().getStreetCode() != null) {

						preparedStatement.setInt(i++, address.getStreet().getStreetCode().intValue());

					} else {

						preparedStatement.setNull(i++, Types.NULL);
					}

					if (address.getStreet() != null && address.getStreet().getCep() != null) {

						preparedStatement.setInt(i++, address.getStreet().getCep().intValue());

					} else {

						preparedStatement.setNull(i++, Types.NULL);
					}

					if (address.getAddressType() != null && address.getAddressType().getAddressTypeCode() != null) {

						preparedStatement.setInt(i++, address.getAddressType().getAddressTypeCode().intValue());

					} else {

						preparedStatement.setNull(i++, Types.NULL);
					}

					if (address.getAddressNumber() != null) {

						preparedStatement.setInt(i++, address.getAddressNumber().intValue());

					} else {

						preparedStatement.setNull(i++, Types.NULL);
					}

					if (address.getComplement() != null && !address.getComplement().equals("")) {

						preparedStatement.setString(i++, address.getComplement());

					} else {

						preparedStatement.setNull(i++, Types.NULL);
					}

					count = new Integer(preparedStatement.executeUpdate());

					if (count != null && count.equals(1)) {

						msg = SAVE_SUCCESS;

						addressConstraints = this.search();

						address = this.search(addressConstraints);

						if (address != null) {

							msg += RECOVERY_SUCCESS;

						} else {

							msg += RECOVERY_NOT_SUCCESS;
						}

					} else {

						address = null;

						msg = SAVE_ERROR;
					}

				} catch (SQLException e) {

					address = null;

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
								".salve(Address address)", msg));

						if (address != null) {

							System.err.println(String.format("\n %s %s", SAVE_DATA, address.toString()));
						}
					}
				}
			}
		}

		// Information output

		return address;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.IAddressDAO#update(vo.person.Person)
	 */
	@Override
	public Set<Address> update(Person person) {

		// Instance variables

		Address address = null;

		Set<Address> adresses = null;

		Iterator<Address> iterator = null;

		Integer counter = null;

		// Data processing

		if (person.getAddresses() != null && person.getAddresses().size() > 0) {

			iterator = person.getAddresses().iterator();

			adresses = new HashSet<Address>();

			counter = new Integer(0);

			while (iterator.hasNext()) {

				address = iterator.next();

				address = this.update(address);

				if (address != null) {

					adresses.add(address);

					counter = new Integer(counter.intValue() + 1);
				}
			}
		}

		// Information output

		if (counter != null && counter.equals(person.getPhones().size())) {

			if (this.connection != null) {

				try {

					this.connection.commit();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {

				adresses = null;

				if (this.connection != null) {

					try {

						this.connection.rollback();

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		} else {

			adresses = null;

			if (this.connection != null) {

				try {

					this.connection.rollback();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// Information output

		return adresses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.IAddressDAO#update(vo.address.Address)
	 */
	@Override
	public Address update(Address address) {
		// TODO Auto-generated method stub

		// Variables declaration

		AddressConstraints addressConstraints = null;

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer count = null;

		int i = 1;

		// Data processing

		if (address != null) {

			try {

				query = "UPDATE ";

				query += V_TABLE_ADDRESS;

				query += " ADDRESS SET ";

				query += "ADDRESS.CD_LOGRADOURO = ?, ";

				query += "ADDRESS.NO_LOGRADOURO_CEP = ?, ";

				query += "ADDRESS.ID_TB_ADDRESS_TYPE = ?, ";

				query += "ADDRESS.ADDRESS_NUMBER = ?, ";

				query += "ADDRESS.COMPLEMENT = ?";

				query += "WHERE ";

				query += "ADDRESS.ID_TB_ADDRESS = ? ";

				query += "AND ADDRESS.ID_TB_USER = ? ";

				query += "AND ADDRESS.ID_TB_PERSON = ? ";

				preparedStatement = connection.prepareStatement(query);

				if (address.getStreet() != null && address.getStreet().getStreetCode() != null) {

					preparedStatement.setInt(i++, address.getStreet().getStreetCode().intValue());

				} else {

					preparedStatement.setNull(i++, Types.NULL);
				}

				if (address.getStreet() != null && address.getStreet().getCep() != null) {

					preparedStatement.setInt(i++, address.getStreet().getCep().intValue());

				} else {

					preparedStatement.setNull(i++, Types.NULL);
				}

				if (address.getAddressType() != null && address.getAddressType().getAddressTypeCode() != null) {

					preparedStatement.setInt(i++, address.getAddressType().getAddressTypeCode().intValue());

				} else {

					preparedStatement.setNull(i++, Types.NULL);
				}

				if (address.getAddressNumber() != null) {

					preparedStatement.setInt(i++, address.getAddressNumber().intValue());

				} else {

					preparedStatement.setNull(i++, Types.NULL);
				}

				if (address.getComplement() != null && !address.getComplement().equals("")) {

					preparedStatement.setString(i++, address.getComplement());

				} else {

					preparedStatement.setNull(i++, Types.NULL);
				}

				preparedStatement.setInt(i++, address.getAddressCode().intValue());

				preparedStatement.setInt(i++, address.getPerson().getUser().getUserCode().intValue());

				preparedStatement.setInt(i++, address.getPerson().getPersonCode().intValue());

				count = new Integer(preparedStatement.executeUpdate());

				if (count != null && count.equals(1)) {

					msg = UPDATE_SUCCESS;

					addressConstraints = new AddressConstraints();

					addressConstraints.setId_tb_address(address.getAddressCode());

					addressConstraints.setId_tb_person(address.getPerson().getPersonCode());

					addressConstraints.setId_tb_user(address.getPerson().getUser().getUserCode());

					address = search(addressConstraints);

					if (address != null) {

						msg += RECOVERY_SUCCESS;

					} else {

						msg += RECOVERY_NOT_SUCCESS;
					}

				} else {

					address = null;

					msg = UPDATE_NOT_SUCCESS;
				}

			} catch (SQLException e) {
				// TODO: handle exception

				address = null;

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

					System.err.println(String.format("\n %s%s: %s", this.getClass().getSimpleName(),
							".update(Address address)", msg));
				}

				if (address != null) {

					System.err.println(String.format("\n %s %s", UPDATE_DATA, address.toString()));
				}
			}
		}

		// Information output

		return address;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.IAddressDAO#delete(vo.person.Person)
	 */
	@Override
	public Boolean delete(Person person) {
		// TODO Auto-generated method stub

		// Variables declaration

		Address address = null;

		Iterator<Address> iterator = null;

		Integer counter = null;

		Boolean flag = null;

		// Data processing

		if (person.getAddresses() != null && person.getAddresses().size() > 0) {

			iterator = person.getAddresses().iterator();

			counter = new Integer(0);

			while (iterator.hasNext()) {

				address = iterator.next();

				if (this.delete(address).equals(true)) {

					counter = new Integer(counter.intValue() + 1);
				}
			}
		}

		if (counter != null) {

			if (counter.equals(person.getPhones().size())) {

				flag = new Boolean(true);

				if (this.connection != null) {

					try {

						this.connection.commit();

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} else {

				flag = new Boolean(false);

				if (this.connection != null) {

					try {

						this.connection.rollback();

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		} else {

			if (this.connection != null) {

				try {

					this.connection.rollback();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// Information output

		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.IAddressDAO#delete(vo.address.Address)
	 */
	@Override
	public Boolean delete(Address address) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Boolean flag = null;

		Integer count = null;

		int i = 1;

		// Data processing

		if (this.connection == null) {

			return null;
		}

		if (address != null) {

			try {

				query = "DELETE FROM ";

				query += V_TABLE_ADDRESS;

				query += " ADDRESS ";

				query += "WHERE ";

				query += "ADDRESS.ID_TB_ADDRESS = ? ";

				query += "AND ADDRESS.ID_TB_USER = ? ";

				query += "AND ADDRESS.ID_TB_PERSON = ? ";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, address.getAddressCode().intValue());

				preparedStatement.setInt(i++, address.getPerson().getUser().getUserCode().intValue());

				preparedStatement.setInt(i++, address.getPerson().getPersonCode().intValue());

				count = new Integer(preparedStatement.executeUpdate());

				if (count != null && count.equals(1)) {

					flag = new Boolean(true);

					msg = DELETE_SUCCESS;

				} else {

					address = null;

					flag = new Boolean(false);

					msg = DELETE_NOT_SUCCESS;
				}

			} catch (SQLException e) {
				// TODO: handle exception

				address = null;

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
							".delete(Address address)", msg));

					if (address != null) {

						System.err.println(String.format("\n %s %s", DELETE_DATA, address.toString()));
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
	 * @see persistence.address.IAddressDAO#search()
	 */
	@Override
	public AddressConstraints search() {
		// TODO Auto-generated method stub

		// Variables declaration

		AddressConstraints addressConstraints = null;

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		// Data processing

		try {

			query = "SELECT ";

			query += "ADDRESS.ID_TB_ADDRESS, ";

			query += "ADDRESS.ID_TB_USER, ";

			query += "ADDRESS.ID_TB_PERSON ";

			query += "FROM ";

			query += V_TABLE_ADDRESS;

			query += " ADDRESS ";

			query += "WHERE ";

			query += "ADDRESS.ID_TB_ADDRESS = ";

			query += "(SELECT MAX(ADDRESS.ID_TB_ADDRESS) FROM ";

			query += V_TABLE_ADDRESS;

			query += " ADDRESS)";

			preparedStatement = this.connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();

			addressConstraints = new AddressConstraints();

			if (resultSet != null && resultSet.next()) {

				addressConstraints.setId_tb_address(new Integer(resultSet.getInt("ID_TB_ADDRESS")));

				addressConstraints.setId_tb_user(new Integer(resultSet.getInt("ID_TB_USER")));

				addressConstraints.setId_tb_person(new Integer(resultSet.getInt("ID_TB_PERSON")));

			} else {

				addressConstraints.setId_tb_address(new Integer(0));

				addressConstraints.setId_tb_user(new Integer(0));

				addressConstraints.setId_tb_person(new Integer(0));

			}

		} catch (SQLException e) {
			// TODO: handle exception

			addressConstraints = null;

			e.printStackTrace();

		} finally {

			if (preparedStatement != null) {

				try {

					preparedStatement.close();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// Information output

		return addressConstraints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.IAddressDAO#search(vo.address.AddressConstraints)
	 */
	@Override
	public Address search(AddressConstraints addressConstraints) {
		// TODO Auto-generated method stub

		// Variables declaration

		Address address = null;

		Person person = null;

		PersonConstraints personConstraints = null;

		Street street = null;

		StreetConstraints streetConstraints = null;

		AddressType addressType = null;

		AddressTypeConstraints addressTypeConstraints = null;

		PreparedStatement preparedStatement = null;

		PersonPersistence personPersistence = null;

		ResultSet resultSet = null;

		String query = null;

		int i = 1;

		// Data processing

		try {

			query = "SELECT * FROM ";

			query += V_TABLE_ADDRESS;

			query += " ADDRESS ";

			query += "WHERE ";

			query += "ADDRESS.ID_TB_ADDRESS = ? ";

			query += "AND ADDRESS.ID_TB_USER = ? ";

			query += "AND ADDRESS.ID_TB_PERSON = ? ";

			preparedStatement = this.connection.prepareStatement(query);

			preparedStatement.setInt(i++, addressConstraints.getId_tb_address().intValue());

			preparedStatement.setInt(i++, addressConstraints.getId_tb_User().intValue());

			preparedStatement.setInt(i++, addressConstraints.getId_tb_person().intValue());

			resultSet = preparedStatement.executeQuery();

			if (resultSet != null && resultSet.next()) {

				address = new Address();

				address.setAddressCode(new Integer(resultSet.getInt("ID_TB_ADDRESS")));

				personConstraints = new PersonConstraints();

				personConstraints.setId_tb_User(new Integer(resultSet.getInt("ID_TB_USER")));

				personConstraints.setId_tb_Person(new Integer(resultSet.getInt("ID_TB_PERSON")));

				personPersistence = new PersonPersistence();

				person = personPersistence.search(personConstraints);

				if (person != null) {

					address.setPerson(person);
				}

				if (resultSet.getInt("CD_LOGRADOURO") > 0 && resultSet.getInt("NO_LOGRADOURO_CEP") > 0) {

					streetConstraints = new StreetConstraints();

					streetConstraints.setCD_LOGRADOURO(new Integer(resultSet.getInt("CD_LOGRADOURO")));

					streetConstraints.setNO_LOGRADOURO_CEP(new Integer(resultSet.getInt("NO_LOGRADOURO_CEP")));

					street = this.streetPersistence.search(streetConstraints);

					if (street != null) {

						address.setStreet(street);
					}
				}

				if (resultSet.getInt("ID_TB_ADDRESS_TYPE") > 0) {

					addressTypeConstraints = new AddressTypeConstraints();

					addressTypeConstraints.setId_tb_address_type(new Integer(resultSet.getInt("ID_TB_ADDRESS_TYPE")));

					addressType = this.addressTypePersistence.search(addressTypeConstraints);

					if (addressType != null) {

						address.setAddressType(addressType);
					}
				}

				if (resultSet.getInt("ADDRESS_NUMBER") > 0) {

					address.setAddressNumber(new Integer(resultSet.getInt("ADDRESS_NUMBER")));
				}

				if (resultSet.getString("COMPLEMENT") != null) {

					address.setComplement(resultSet.getString("COMPLEMENT"));
				}
			}

		} catch (SQLException e) {
			// TODO: handle exception

			address = null;

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

		// Information output

		return address;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.IAddressDAO#search(vo.person.Person)
	 */
	@Override
	public Set<Address> search(Person person) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		Set<Address> adresses = null;

		Address address = null;

		AddressConstraints addressConstraints = null;

		int i = 1;

		// Data processing

		if (person != null) {

			try {

				query = "SELECT ";

				query += "ADDRESS.ID_TB_ADDRESS, ";

				query += "ADDRESS.ID_TB_USER, ";

				query += "ADDRESS.ID_TB_PERSON ";

				query += "FROM ";

				query += V_TABLE_ADDRESS;

				query += " ADDRESS ";

				query += "WHERE ";

				query += "ADDRESS.ID_TB_USER = ? ";

				query += "AND ADDRESS.ID_TB_PERSON = ?";

				preparedStatement = this.connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				preparedStatement.setInt(i++, person.getPersonCode().intValue());

				preparedStatement.setInt(i++, person.getUser().getUserCode().intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet.last()) {

					resultSet.beforeFirst();

					adresses = new HashSet<Address>();

					while (resultSet.next()) {

						addressConstraints = new AddressConstraints();

						addressConstraints.setId_tb_address(new Integer(resultSet.getInt("ID_TB_ADDRESS")));

						addressConstraints.setId_tb_person(new Integer(resultSet.getInt("ID_TB_PERSON")));

						addressConstraints.setId_tb_user(new Integer(resultSet.getInt("ID_TB_USER")));

						address = this.search(addressConstraints);

						if (address != null) {

							adresses.add(address);
						}
					}
				}

			} catch (Exception e) {
				// TODO: handle exception

				adresses = null;

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

		return adresses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.address.IAddressDAO#addressConstraints()
	 */
	public AddressConstraints addressConstraints() {

		// Variables declaration

		AddressConstraints addressConstraints = null;

		// Data processing

		addressConstraints = search();

		addressConstraints.setId_tb_address(addressConstraints.getId_tb_address().intValue() + 1);

		// Information output

		return addressConstraints;
	}
}
