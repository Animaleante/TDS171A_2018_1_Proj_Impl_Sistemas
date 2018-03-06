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
import static persistence.Persistence.UPDATE_ERROR;
import static persistence.Persistence.UPDATE_NOT_SUCCESS;
import static persistence.Persistence.UPDATE_SUCCESS;
import static persistence.Persistence.V_TABLE_CITY;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import vo.cep.City;
import vo.cep.CityConstraints;
import vo.cep.FederationUnityConstraints;
import vo.cep.FederationUnity;

/**
 * Class to implement the operations against to the database to the City entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
class CityDAO implements ICityDAO {

	/**
	 * Instance variables
	 */

	private Connection connection;

	private FederationUnityPersistence federationUnityPersistence;

	/**
	 * Default constructor
	 */
	public CityDAO() {
		// TODO Auto-generated constructor stub

		this.federationUnityPersistence = new FederationUnityPersistence();
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
	 * @see persistence.cep.database.IStreetDAO#save(vo.cep.City)
	 */
	public City save(City city) {

		// Variables declaration

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		int i = 1;

		CityConstraints cityConstraints = null;

		// Data processing

		if (city != null) {

			try {

				query = "INSERT INTO ";

				query += V_TABLE_CITY;

				query += " CIDADE (";

				query += "CIDADE.CD_CIDADE, ";

				query += "CIDADE.CD_UF, ";

				query += "CIDADE.DS_CIDADE_NOME) ";

				query += "VALUES (?, ?, ?)";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, city.getCityCode().intValue());

				preparedStatement.setInt(i++, city.getFederationUnity().getFederationUnityCode().intValue());

				preparedStatement.setString(i++, city.getDescrition());

				counter = new Integer(preparedStatement.executeUpdate());

				if (counter != null && counter.equals(1)) {

					this.connection.commit();

					msg = SAVE_SUCCESS;

					cityConstraints = this.search();

					if (cityConstraints != null) {

						city = this.search(cityConstraints);

						if (city != null) {

							msg += RECOVERY_SUCCESS;

						} else {

							msg += RECOVERY_NOT_SUCCESS;
						}

					} else {

						city = null;

						msg = RECOVERY_ERROR;
					}

				} else {

					city = null;

					this.connection.rollback();

					msg = SAVE_NOT_SUCCESS;
				}

			} catch (SQLException e) {
				// TODO: handle exception

				city = null;

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
							String.format("\n %s%s: %s", this.getClass().getSimpleName(), ".save(City city)", msg));

					if (city != null) {

						System.err.println(String.format("\n %s: %s", SAVE_DATA, city.toString()));
					}
				}
			}
		}

		// Information output

		return city;
	}

	public List<City> list() {
		// TODO Auto-generated method stub

		// Variables declaration

		List<City> cities = null;

		City city = null;

		CityConstraints cityConstraints = null;

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		String msg = null;

		// Data processing

		try {

			query = "SELECT CIDADE.CD_CIDADE FROM ";

			query += V_TABLE_CITY;

			query += " CIDADE ";

			statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(query);

			if (resultSet != null && resultSet.last()) {

				resultSet.beforeFirst();

				cities = new ArrayList<City>();

				while (resultSet.next()) {

					cityConstraints = new CityConstraints();

					cityConstraints.setCd_cidade(new Integer(resultSet.getInt("CD_CIDADE")));

					city = search(cityConstraints);

					if (city != null) {

						cities.add(city);
					}
				}

				msg = QUERY_SUCCESS;

			} else {

				msg = QUERY_NOT_SUCCESS;
			}

		} catch (Exception e) {
			// TODO: handle exception

			cities = null;

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

				if (cities != null && cities.size() > 0) {

					System.err.print(String.format("\n %s", LIST_DATA));

					for (City s : cities) {

						System.err.println(s.toString());
					}
				}
			}
		}

		// Information output

		return cities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.ICityDAO#update(vo.cep.City)
	 */
	public City update(City city) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		int i = 1;

		CityConstraints cityConstraints = null;

		// Data processing

		if (city != null) {

			try {

				query = "UPDATE ";

				query += V_TABLE_CITY;

				query += " CIDADE SET ";

				query += "CIDADE.CD_UF = ?, ";

				query += "CIDADE.DS_CIDADE_NOME = ?, ";

				query += "WHERE ";

				query += "CIDADE.CD_CIDADE = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, city.getFederationUnity().getFederationUnityCode().intValue());

				preparedStatement.setString(i++, city.getDescrition());

				preparedStatement.setInt(i++, city.getCityCode());

				counter = new Integer(preparedStatement.executeUpdate());

				if (counter != null && counter.equals(1)) {

					this.connection.commit();

					msg = UPDATE_SUCCESS;

					cityConstraints = new CityConstraints();

					cityConstraints.setCd_cidade(city.getCityCode());

					city = search(cityConstraints);

					if (city != null) {

						msg += RECOVERY_SUCCESS;

					} else {

						msg += RECOVERY_NOT_SUCCESS;
					}

				} else {

					msg = UPDATE_NOT_SUCCESS;
				}

			} catch (SQLException e) {
				// TODO: handle exception

				city = null;

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

					System.err.println(
							String.format("\n %s%s: %s", this.getClass().getSimpleName(), ".update(City city)", msg));

					if (city != null) {

						System.err.println(String.format("\n %s %s", UPDATE_DATA, city.toString()));
					}
				}
			}
		}

		// Information output

		return city;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.ICityDAO#delete(vo.cep.City)
	 */
	public Boolean delete(City city) {
		// TODO Auto-generated method stub

		// Variables declaration

		Boolean flag = null;

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		int i = 1;

		// Data processing

		if (city != null) {

			try {

				query = "DELETE FROM ";

				query += V_TABLE_CITY;

				query += " CIDADE ";

				query += "WHERE ";

				query += "CIDADE.CD_CIDADE = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, city.getCityCode());

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

					System.err.println(
							String.format("\n %s%s: %s", this.getClass().getSimpleName(), ".delete(City city)", msg));

					System.err.println(String.format(String.format("\n %s %s", DELETE_DATA,
							flag != null && flag.equals(true) ? city != null ? city.toString() : ""
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
	 * @see persistence.cep.database.ICityDAO#search()
	 */
	public CityConstraints search() {
		// TODO Auto-generated method stub

		// Variables declaration

		Statement statement = null;

		CityConstraints cityConstraints = null;

		ResultSet resultSet = null;

		String query = null;

		// Data processing

		try {

			query = "SELECT CIDADE.CD_CIDADE FROM ";

			query += V_TABLE_CITY;

			query += " CIDADE ";

			query += "WHERE ";

			query += "CIDADE.CD_CIDADE = ";

			query += "(SELECT MAX(CIDADE.CD_CIDADE) FROM ";

			query += V_TABLE_CITY;

			query += " CIDADE)";

			statement = this.connection.createStatement();

			resultSet = statement.executeQuery(query);

			cityConstraints = new CityConstraints();

			if (resultSet != null && resultSet.next()) {

				cityConstraints.setCd_cidade(new Integer(resultSet.getInt("CD_CIDADE")));

			} else {

				cityConstraints.setCd_cidade(new Integer(0));
			}

		} catch (SQLException e) {
			// TODO: handle exception

			cityConstraints = null;

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

		return cityConstraints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.ICityDAO#search(vo.cep.ConstraintsCity)
	 */
	@Override
	public City search(CityConstraints cityConstraints) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		City city = null;

		FederationUnity federationUnity = null;

		FederationUnityConstraints federationUnityConstraints = null;

		int i = 1;

		// Data processing

		if (cityConstraints != null) {

			try {

				query = "SELECT * FROM ";

				query += V_TABLE_CITY;

				query += " CIDADE ";

				query += "WHERE CIDADE.CD_CIDADE = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, cityConstraints.getCd_cidade().intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.next()) {

					federationUnityConstraints = new FederationUnityConstraints();

					federationUnityConstraints.setCd_uf(new Integer(resultSet.getInt("CD_UF")));

					federationUnity = this.federationUnityPersistence.search(federationUnityConstraints);

					if (federationUnity != null) {

						city = new City();

						city.setFederationUnity(federationUnity);

						city.setCitycode(new Integer(resultSet.getInt("CD_CIDADE")));

						city.setDescrition(resultSet.getString("DS_CIDADE_NOME"));
					}
				}

			} catch (SQLException e) {
				// TODO: handle exception

				city = null;

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

		return city;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.ICityDAO#search(vo.cep.FederationUnity)
	 */
	@Override
	public List<City> search(FederationUnity federationUnity) {

		// Variables declaration

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		List<City> cidades = null;

		City city = null;

		CityConstraints cityConstraints = null;

		int i = 1;

		// Data processing

		if (federationUnity != null) {

			try {

				query = "SELECT CIDADE.CD_CIDADE FROM ";

				query += V_TABLE_CITY;

				query += " CIDADE ";

				query += "WHERE ";

				query += "CIDADE.CD_UF = ? ";

				query += "ORDER BY CIDADE.DS_CIDADE_NOME ASC";

				preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				preparedStatement.setInt(i++, federationUnity.getFederationUnityCode().intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.last()) {

					resultSet.beforeFirst();

					cidades = new ArrayList<City>();

					while (resultSet.next()) {

						cityConstraints = new CityConstraints();

						cityConstraints.setCd_cidade(new Integer(resultSet.getInt("CD_CIDADE")));

						city = this.search(cityConstraints);

						if (city != null) {

							cidades.add(city);
						}
					}
				}

			} catch (Exception e) {
				// TODO: handle exception

				cidades = null;

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

		return cidades;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.ICityDAO#cityCode()
	 */
	public Integer cityCode() {

		// Variables declaration

		Integer cityCode = null;

		CityConstraints cityConstraints = null;

		// Data input

		cityConstraints = this.search();

		// Data processing

		cityCode = new Integer(cityConstraints.getCd_cidade().intValue() + 1);

		// Information output

		return cityCode;
	}
}
