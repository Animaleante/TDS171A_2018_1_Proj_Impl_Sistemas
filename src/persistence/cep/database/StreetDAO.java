package persistence.cep.database;

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
import static persistence.Persistence.UPDATE_NOT_SUCCESS;
import static persistence.Persistence.UPDATE_SUCCESS;
import static persistence.Persistence.V_TABLE_CITY;
import static persistence.Persistence.V_TABLE_DISTRICT;
import static persistence.Persistence.V_TABLE_STREET;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import vo.cep.City;
import vo.cep.DistrictConstraints;
import vo.cep.StreetConstraints;
import vo.cep.District;
import vo.cep.Street;

/**
 * Class to implement the operations against to the database to the Street
 * entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
class StreetDAO implements IStreetDAO {

	/**
	 * Instance variables
	 */

	private Connection connection;

	private DistrictPersistence districtPersistence;

	/**
	 * Default constructor
	 */

	public StreetDAO() {
		// TODO Auto-generated constructor stub

		this.districtPersistence = new DistrictPersistence();
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
	 * @see persistence.cep.database.IStreetDAO#save(vo.cep.Street)
	 */
	public Street save(Street street) {

		// Variables declaration

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		int i = 1;

		StreetConstraints streetConstraints = null;

		// Data processing

		if (street != null) {

			try {

				query = "INSERT INTO ";

				query += V_TABLE_STREET;

				query += " LOGRADOURO (";

				query += "LOGRADOURO.CD_LOGRADOURO, ";

				query += "LOGRADOURO.CD_BAIRRO, ";

				query += "LOGRADOURO.NO_LOGRADOURO_CEP, ";

				query += "LOGRADOURO.CD_TIPO_LOGRADOUROS, ";

				query += "LOGRADOURO.DS_LOGRADOURO_NOME) ";

				query += "VALUES (?, ?, ?, ?, ?)";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, street.getStreetCode().intValue());

				preparedStatement.setInt(i++, street.getDistrict().getDistrictCode().intValue());

				preparedStatement.setInt(i++, street.getCep().intValue());

				if (street.getTipoLogradouro() != null) {

					preparedStatement.setInt(i++, street.getTipoLogradouro().intValue());

				} else {

					preparedStatement.setInt(i++, 1);
				}

				preparedStatement.setString(i++, street.getDecription());

				counter = new Integer(preparedStatement.executeUpdate());

				if (counter != null && counter.equals(1)) {

					this.connection.commit();

					msg = SAVE_SUCCESS;

					streetConstraints = this.search();

					if (streetConstraints != null) {

						street = this.search(streetConstraints);

						if (street != null) {

							msg += RECOVERY_SUCCESS;

						} else {

							msg += RECOVERY_NOT_SUCCESS;
						}

					} else {

						street = null;

						msg = RECOVERY_ERROR;
					}

				} else {

					street = null;

					this.connection.rollback();

					msg = SAVE_NOT_SUCCESS;
				}

			} catch (SQLException e) {
				// TODO: handle exception

				street = null;

				msg = SAVE_ERROR + e.getMessage();

				e.printStackTrace();

			} finally {

				if (preparedStatement != null) {

					try {

						preparedStatement.close();

					} catch (SQLException e) {
						// logar excecao
						e.printStackTrace();

					}
				}

				if (FLAG.equals(true) && msg != null) {

					System.err.println(
							String.format("\n %s%s: %s", this.getClass().getSimpleName(), ".save(Street street)", msg));

					if (street != null) {

						System.err.println(String.format("\n %s: %s", SAVE_DATA, street.toString()));
					}
				}
			}
		}

		// Information output

		return street;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#list()
	 */
	public List<Street> list() {
		// TODO Auto-generated method stub

		// Variables declaration

		List<Street> streets = null;

		Street street = null;

		StreetConstraints streetConstraints = null;

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		String msg = null;

		// Data processing

		try {

			query = "SELECT STREET.CD_LOGRADOURO, STREET.NO_LOGRADOURO_CEP FROM ";

			query += V_TABLE_STREET;

			query += " STREET ";

			statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(query);

			if (resultSet != null && resultSet.last()) {

				resultSet.beforeFirst();

				streets = new ArrayList<Street>();

				while (resultSet.next()) {

					streetConstraints = new StreetConstraints();

					streetConstraints.setCD_LOGRADOURO(new Integer(resultSet.getInt("CD_LOGRADOURO")));

					streetConstraints.setNO_LOGRADOURO_CEP(new Integer(resultSet.getInt("NO_LOGRADOURO_CEP")));

					street = search(streetConstraints);

					if (street != null) {

						streets.add(street);
					}
				}

				msg = QUERY_SUCCESS;

			} else {

				msg = QUERY_NOT_SUCCESS;
			}

		} catch (Exception e) {
			// TODO: handle exception

			streets = null;

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

				}
			}

			if (FLAG.equals(true) && msg != null) {

				System.err.println(String.format("\n %s%s: %s", this.getClass().getSimpleName(), ".list()", msg));

				if (streets != null && streets.size() > 0) {

					System.err.print(String.format("\n %s", LIST_DATA));

					for (Street s : streets) {

						System.err.println(s.toString());
					}
				}
			}
		}

		// Information output

		return streets;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#update(vo.cep.Street,
	 * vo.cep.ConstraintsStreet)
	 */
	public Street update(Street street, StreetConstraints streetConstraints) {
		// TODO Auto-generated method stub

		// Variables declaration

		Street streetAux = null;

		String msg = null;

		// Data processing

		if (street != null) {

			streetAux = this.search(streetConstraints);

			if (streetAux != null) {

				if (this.delete(streetAux)) {

					street = this.save(street);

					if (street != null) {

						msg = UPDATE_SUCCESS;

					} else {

						msg = UPDATE_NOT_SUCCESS;
					}
				} else {

					msg = UPDATE_NOT_SUCCESS;
				}

			} else {

				msg = UPDATE_NOT_SUCCESS;
			}

			if (FLAG.equals(true) && msg != null) {

				System.err.println(
						String.format("\n %s%s: %s", this.getClass().getSimpleName(), ".update(Street street)", msg));

				if (street != null) {

					System.err.println(String.format("\n %s %s", UPDATE_DATA, street.toString()));
				}
			}
		}

		// Information output

		return street;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#delete(vo.cep.Street)
	 */
	public Boolean delete(Street street) {
		// TODO Auto-generated method stub

		// Variables declaration

		Boolean flag = null;

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		int i = 1;

		// Data processing

		if (street != null) {

			try {

				query = "DELETE FROM ";

				query += V_TABLE_STREET;

				query += " LOGRADOURO ";

				query += "WHERE ";

				query += "LOGRADOURO.CD_LOGRADOURO = ? AND LOGRADOURO.NO_LOGRADOURO_CEP = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, street.getStreetCode().intValue());

				preparedStatement.setInt(i++, street.getCep().intValue());

				counter = new Integer(preparedStatement.executeUpdate());

				if (counter != null && counter.equals(1)) {

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
							".delete(Street street, ConstraintsStreet streetConstraints)", msg));

					System.err.println(String.format(String.format("\n %s %s", DELETE_DATA,
							flag != null && flag.equals(true) ? street != null ? street.toString() : ""
									: "Is was not possivle do delete the data.")));
				}
			}
		}

		// Information output

		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#search()
	 */
	public StreetConstraints search() {
		// TODO Auto-generated method stub

		// Variables declaration

		Statement statement = null;

		StreetConstraints streetConstraints = null;

		ResultSet resultSet = null;

		String query = null;

		// Data processing

		try {

			query = "SELECT STREET.CD_LOGRADOURO, STREET.NO_LOGRADOURO_CEP FROM ";

			query += V_TABLE_STREET;

			query += " STREET ";

			query += "WHERE ";

			query += "STREET.CD_LOGRADOURO = ";

			query += "(SELECT MAX(STREET.CD_LOGRADOURO) FROM ";

			query += V_TABLE_STREET;

			query += " STREET)";

			statement = this.connection.createStatement();

			resultSet = statement.executeQuery(query);

			streetConstraints = new StreetConstraints();

			if (resultSet != null && resultSet.next()) {

				streetConstraints.setCD_LOGRADOURO(new Integer(resultSet.getInt("CD_LOGRADOURO")));

				streetConstraints.setNO_LOGRADOURO_CEP(new Integer(resultSet.getInt("NO_LOGRADOURO_CEP")));

			} else {

				streetConstraints.setCD_LOGRADOURO(new Integer(0));

				streetConstraints.setNO_LOGRADOURO_CEP(new Integer(0));
			}

		} catch (SQLException e) {
			// TODO: handle exception

			streetConstraints = null;

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

				}
			}
		}

		// Information output

		return streetConstraints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#search(vo.cep.StreetConstraints)
	 */
	@Override
	public Street search(StreetConstraints streetConstraints) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		Street street = null;

		District district = null;

		DistrictConstraints districtConstraints = null;

		int i = 1;

		// Data processing

		if (streetConstraints != null) {

			try {

				query = "SELECT * FROM ";

				query += V_TABLE_STREET;

				query += " LOGRADOUROS ";

				query += "WHERE ";

				query += "LOGRADOUROS.CD_LOGRADOURO = ? AND LOGRADOUROS.NO_LOGRADOURO_CEP = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, streetConstraints.getCD_LOGRADOURO().intValue());

				preparedStatement.setInt(i++, streetConstraints.getNO_LOGRADOURO_CEP().intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.next()) {

					districtConstraints = new DistrictConstraints();

					districtConstraints.setCd_bairro(new Integer(resultSet.getInt("CD_BAIRRO")));

					district = this.districtPersistence.search(districtConstraints);

					if (district != null) {

						street = new Street();

						street.setDistrict(district);

						street.setStreeCode(new Integer(resultSet.getInt("CD_LOGRADOURO")));

						street.setCep((new Integer(resultSet.getInt("NO_LOGRADOURO_CEP"))));

						street.setDescrition(resultSet.getString("DS_LOGRADOURO_NOME"));

						street.setTipoLogradouro(new Integer(resultSet.getInt("CD_TIPO_LOGRADOUROS")));
					}
				}

			} catch (SQLException e) {
				// TODO: handle exception

				street = null;

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

			if (resultSet != null) {

				try {

					resultSet.close();

				} catch (SQLException e) {
					// TODO: handle exception

					e.printStackTrace();
				}
			}
		}

		// Information output

		return street;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#search(java.lang.Integer)
	 */
	@Override
	public Street search(Integer streetCode) {

		// Variables declaration

		Street street = null;

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		StreetConstraints streetConstraints = null;

		int i = 1;

		// Data processing

		if (streetCode != null) {

			try {

				query = "SELECT LOGRADOURO.CD_LOGRADOURO, LOGRADOURO.NO_LOGRADOURO_CEP  ";

				query += "FROM ";

				query += V_TABLE_STREET;

				query += " LOGRADOURO ";

				query += "WHERE ";

				query += "LOGRADOURO.CD_LOGRADOURO = ?";

				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setInt(i++, streetCode.intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.next()) {

					streetConstraints = new StreetConstraints();

					streetConstraints.setCD_LOGRADOURO(new Integer(resultSet.getInt("CD_LOGRADOURO")));

					streetConstraints.setNO_LOGRADOURO_CEP(new Integer(resultSet.getInt("NO_LOGRADOURO_CEP")));

					street = this.search(streetConstraints);
				}

			} catch (SQLException e) {
				// TODO: handle exception

				street = null;

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

		return street;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#searchCEP(java.lang.Integer)
	 */
	@Override
	public Street searchCEP(Integer cep) {

		// Variables declaration

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		Street street = null;

		StreetConstraints streetConstraints = null;

		int i = 1;

		// Data processing

		if (cep != null) {

			try {

				query = "SELECT L.CD_LOGRADOURO, L.DS_LOGRADOURO_NOME ";

				query += "FROM ";

				query += V_TABLE_STREET;

				query += " L ";

				query += "INNER JOIN BAIRROS B ";

				query += "ON L.NO_LOGRADOURO_CEP = ? AND L.CD_BAIRRO = B.CD_BAIRRO ";

				query += "INNER JOIN CIDADES C ";

				query += "ON B.CD_CIDADE = C.CD_CIDADE ";

				query += "INNER JOIN UF U ";

				query += "ON C.CD_UF = U.CD_UF";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, cep.intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.next()) {

					streetConstraints = new StreetConstraints();

					streetConstraints.setCD_LOGRADOURO(new Integer(resultSet.getInt("CD_LOGRADOURO")));

					streetConstraints.setNO_LOGRADOURO_CEP(new Integer(resultSet.getInt("NO_LOGRADOURO_CEP")));

					street = this.search(streetConstraints);
				}

			} catch (Exception e) {
				// TODO: handle exception

				cep = null;

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

				if (resultSet != null) {

					try {

						resultSet.close();

					} catch (SQLException e2) {
						// TODO: handle exception

						e2.printStackTrace();
					}
				}
			}
		}

		// Information output

		return street;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#search(vo.cep.District)
	 */
	@Override
	public List<Street> search(District district) {

		// Variables declaration

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		List<Street> streets = null;

		Street street = null;

		DistrictConstraints districtConstraints = null;

		int i = 1;

		// Data processing

		if (district != null) {

			try {

				query = "SELECT * FROM ";

				query += V_TABLE_STREET;

				query += " LOGRADOURO ";

				query += "WHERE ";

				query += "LOGRADOUROS.CD_BAIRRO = ? ";

				query += "ORDER BY ";

				query += "LOGRADOURO.DS_LOGRADOURO_NOME ";

				query += "ASC";

				preparedStatement = this.connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				preparedStatement.setInt(i++, district.getDistrictCode().intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.last()) {

					resultSet.beforeFirst();

					streets = new ArrayList<Street>();

					while (resultSet.next()) {

						districtConstraints = new DistrictConstraints();

						districtConstraints.setCd_bairro(new Integer(resultSet.getInt("CD_BAIRRO")));

						district = this.districtPersistence.search(districtConstraints);

						if (district != null) {

							street = new Street();

							street.setDistrict(district);

							street.setStreeCode(new Integer(resultSet.getInt("CD_LOGRADOURO")));

							street.setCep(new Integer(resultSet.getInt("NO_LOGRADOURO_CEP")));

							street.setTipoLogradouro(new Integer(resultSet.getInt("CD_TIPO_LOGRADOURO")));

							street.setDescrition(resultSet.getString("DS_LOGRADOURO_NOME"));

							streets.add(street);
						}
					}

				}

			} catch (Exception e) {
				// TODO: handle exception

				streets = null;

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

				if (resultSet != null) {

					try {

						resultSet.close();

					} catch (SQLException e2) {
						// TODO: handle exception

						e2.printStackTrace();
					}
				}
			}
		}

		// Information output

		return streets;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#search(vo.cep.City)
	 */
	@Override
	public List<StreetConstraints> search(City city) {

		// Variables declaration

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		List<StreetConstraints> streetsConstraints = null;

		StreetConstraints streetConstraints = null;

		int i = 1;

		// Data processing

		if (city != null) {

			try {

				query = "SELECT L.CD_LOGRADOURO, L.NO_LOGRADOURO_CEP ";

				query += "FROM ";

				query += V_TABLE_STREET;

				query += " L ";

				query += "INNER JOIN ";

				query += V_TABLE_DISTRICT;

				query += " B ";

				query += "ON L.CD_BAIRRO = B.CD_BAIRRO ";

				query += "INNER JOIN ";

				query += V_TABLE_CITY;

				query += " C ";

				query += "ON B.CD_CIDADE = C.CD_CIDADE AND C.CD_CIDADE = ?";

				preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				preparedStatement.setInt(i++, city.getCityCode().intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.last()) {

					resultSet.beforeFirst();

					streetsConstraints = new ArrayList<StreetConstraints>();

					while (resultSet.next()) {

						streetConstraints = new StreetConstraints();

						streetConstraints.setCD_LOGRADOURO(new Integer(resultSet.getInt("CD_LOGRADOURO")));

						streetConstraints.setNO_LOGRADOURO_CEP(new Integer(resultSet.getInt("NO_LOGRADOURO_CEP")));

						if (streetConstraints != null) {

							streetsConstraints.add(streetConstraints);
						}
					}

				}

			} catch (Exception e) {
				// TODO: handle exception

				streetsConstraints = null;

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

				if (resultSet != null) {

					try {

						resultSet.close();

					} catch (SQLException e2) {
						// TODO: handle exception

						e2.printStackTrace();
					}
				}
			}
		}

		// Information output

		return streetsConstraints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#searchConstraintsStreet()
	 */
	@Override
	public List<StreetConstraints> searchStreetConstraints() {

		// Variables declaration

		List<StreetConstraints> streetsConstraints = null;

		StreetConstraints streetConstraints = null;

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		// Data processing

		try {

			query = "SELECT STREET.CD_LOGRADOURO, STREET.NO_LOGRADOURO_CEP FROM ";

			query += V_TABLE_STREET;

			query += " STREET";

			statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(query);

			if (resultSet != null && resultSet.last()) {

				resultSet.beforeFirst();

				streetsConstraints = new ArrayList<StreetConstraints>();

				while (resultSet != null && resultSet.next()) {

					streetConstraints = new StreetConstraints();

					streetConstraints.setCD_LOGRADOURO(new Integer(resultSet.getInt("CD_LOGRADOURO")));

					streetConstraints.setNO_LOGRADOURO_CEP(new Integer(resultSet.getInt("NO_LOGRADOURO_CEP")));

					streetsConstraints.add(streetConstraints);
				}
			}

		} catch (SQLException e) {
			// TODO: handle exception

			e.printStackTrace();

		} finally {

			if (statement != null) {

				try {

					statement.close();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (resultSet != null) {

				try {

					resultSet.close();

				} catch (SQLException e2) {
					// TODO: handle exception

					e2.printStackTrace();
				}
			}
		}

		// Information output

		return streetsConstraints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IStreetDAO#streetCode()
	 */
	public Integer streetCode() {

		// Variables declaration

		Integer streetCode = null;

		StreetConstraints streetConstraints = null;

		// Data input

		streetConstraints = this.search();

		// Data processing

		if (streetConstraints != null) {

			streetCode = new Integer(streetConstraints.getCD_LOGRADOURO().intValue() + 1);
		}

		// Information output

		return streetCode;
	}
}
