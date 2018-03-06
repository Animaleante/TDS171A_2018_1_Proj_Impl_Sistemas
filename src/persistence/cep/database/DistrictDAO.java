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
import static persistence.Persistence.V_TABLE_DISTRICT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import vo.cep.City;
import vo.cep.CityConstraints;
import vo.cep.DistrictConstraints;
import vo.cep.District;

/**
 * Class to implement the operations against to the database to the District
 * entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017
 * 
 * @version 1.0
 *
 */
class DistrictDAO implements IDistrictDAO {

	/**
	 * Instance variables
	 */

	private Connection connection;

	private CityPersistence cityPersistence;

	/**
	 * Default constructor
	 */
	public DistrictDAO() {
		// TODO Auto-generated constructor stub

		this.cityPersistence = new CityPersistence();
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
	 * @see persistence.cep.database.IDistrictDAO#save(vo.cep.District)
	 */
	public District save(District district) {

		// Variables declaration

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		int i = 1;

		DistrictConstraints districtConstraints = null;

		// Data processing

		if (district != null) {

			try {

				query = "INSERT INTO ";

				query += V_TABLE_DISTRICT;

				query += " DISTRICT (";

				query += "DISTRICT.CD_BAIRRO, ";

				query += "DISTRICT.CD_CIDADE, ";

				query += "DISTRICT.DS_BAIRRO_NOME) ";

				query += "VALUES (?, ?, ?)";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, district.getDistrictCode().intValue());

				preparedStatement.setInt(i++, district.getCity().getCityCode().intValue());

				preparedStatement.setString(i++, district.getDescription());

				counter = new Integer(preparedStatement.executeUpdate());

				if (counter != null && counter.equals(1)) {

					this.connection.commit();

					msg = SAVE_SUCCESS;

					districtConstraints = this.search();

					if (districtConstraints != null) {

						district = this.search(districtConstraints);

						if (district != null) {

							msg += RECOVERY_SUCCESS;

						} else {

							msg += RECOVERY_NOT_SUCCESS;
						}

					} else {

						district = null;

						msg = RECOVERY_ERROR;
					}

				} else {

					district = null;

					this.connection.rollback();

					msg = SAVE_NOT_SUCCESS;
				}

			} catch (SQLException e) {
				// TODO: handle exception

				district = null;

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

					System.err.println(String.format("\n %s%s: %s", this.getClass().getSimpleName(),
							".save(District district)", msg));

					if (district != null) {

						System.err.println(String.format("\n %s: %s", SAVE_DATA, district.toString()));
					}
				}
			}
		}

		// Information output

		return district;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IDistrictDAO#list()
	 */
	public List<District> list() {
		// TODO Auto-generated method stub

		// Variables declaration

		List<District> districts = null;

		District district = null;

		DistrictConstraints districtConstraints = null;

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		String msg = null;

		// Data processing

		try {

			query = "SELECT DISTRICT.CD_BAIRRO FROM ";

			query += V_TABLE_DISTRICT;

			query += " DISTRICT ";

			statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(query);

			if (resultSet != null && resultSet.last()) {

				resultSet.beforeFirst();

				districts = new ArrayList<District>();

				while (resultSet.next()) {

					districtConstraints = new DistrictConstraints();

					districtConstraints.setCd_bairro(new Integer(resultSet.getInt("CD_BAIRRO")));

					district = search(districtConstraints);

					if (district != null) {

						districts.add(district);
					}
				}

				msg = QUERY_SUCCESS;

			} else {

				msg = QUERY_NOT_SUCCESS;
			}

		} catch (Exception e) {
			// TODO: handle exception

			districts = null;

			msg = QUERY_ERROR + e.getMessage();

			e.printStackTrace();

		} finally {

			if (statement != null) {

				try {

					statement.close();

				} catch (SQLException e) {
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

				if (districts != null && districts.size() > 0) {

					System.err.print(String.format("\n %s", LIST_DATA));

					for (District d : districts) {

						System.err.println(d.toString());
					}
				}
			}
		}

		// Information output

		return districts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IDistrictDAO#update(vo.cep.District)
	 */
	public District update(District district) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		int i = 1;

		DistrictConstraints districtConstraints = null;

		// Data processing

		if (district != null) {

			try {

				query = "UPDATE ";

				query += V_TABLE_DISTRICT;

				query += " DISTRICT SET ";

				query += "DISTRICT.CD_CIDADE = ?, ";

				query += "DISTRICT.DS_BAIRRO_NOME = ?, ";

				query += "WHERE ";

				query += "DISTRICT.CD_BAIRRO = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, district.getCity().getCityCode().intValue());

				preparedStatement.setString(i++, district.getDescription());

				preparedStatement.setInt(i++, district.getDistrictCode().intValue());

				counter = new Integer(preparedStatement.executeUpdate());

				if (counter != null && counter.equals(1)) {

					this.connection.commit();

					msg = UPDATE_SUCCESS;

					districtConstraints = new DistrictConstraints();

					districtConstraints.setCd_bairro(district.getDistrictCode());

					district = this.search(districtConstraints);

					if (district != null) {

						msg += RECOVERY_SUCCESS;

					} else {

						msg += RECOVERY_NOT_SUCCESS;
					}

				} else {

					msg = UPDATE_NOT_SUCCESS;
				}

			} catch (SQLException e) {
				// TODO: handle exception

				district = null;

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
							".update(District district)", msg));

					if (district != null) {

						System.err.println(String.format("\n %s %s", UPDATE_DATA, district.toString()));
					}
				}
			}
		}

		// Information output

		return district;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IDistrictDAO#delete(vo.cep.District)
	 */
	public Boolean delete(District district) {
		// TODO Auto-generated method stub

		// Variables declaration

		Boolean flag = null;

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		int i = 1;

		// Data processing

		if (district != null) {

			try {

				query = "DELETE FROM ";

				query += V_TABLE_DISTRICT;

				query += " DISTRICT ";

				query += "WHERE ";

				query += "DISTRICT.CD_BAIRRO = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, district.getDistrictCode());

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
							".delete(district district)", msg));

					System.err.println(String.format(String.format("\n %s %s", DELETE_DATA,
							flag != null && flag.equals(true) ? district != null ? district.toString() : ""
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
	 * @see persistence.cep.database.IDistrictDAO#search()
	 */
	public DistrictConstraints search() {
		// TODO Auto-generated method stub

		// Variables declaration

		Statement statement = null;

		DistrictConstraints districtConstraints = null;

		ResultSet resultSet = null;

		String query = null;

		// Data processing

		try {

			query = "SELECT DISTRICT.CD_BAIRRO FROM ";

			query += V_TABLE_DISTRICT;

			query += " DISTRICT ";

			query += "WHERE ";

			query += "DISTRICT.CD_BAIRRO = ";

			query += "(SELECT MAX(DISTRICT.CD_BAIRRO) FROM ";

			query += V_TABLE_DISTRICT;

			query += " DISTRICT)";

			statement = this.connection.createStatement();

			resultSet = statement.executeQuery(query);

			districtConstraints = new DistrictConstraints();

			if (resultSet != null && resultSet.next()) {

				districtConstraints.setCd_bairro(new Integer(resultSet.getInt("CD_BAIRRO")));

			} else {

				districtConstraints.setCd_bairro(new Integer(0));
			}

		} catch (SQLException e) {
			// TODO: handle exception

			districtConstraints = null;

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

		return districtConstraints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IDistrictDAO#search(vo.cep.DistrictConstraints)
	 */
	@Override
	public District search(DistrictConstraints disctrictConstraints) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		District district = null;

		City city = null;

		CityConstraints cityConstraints = null;

		int i = 1;

		// Data processing

		if (disctrictConstraints != null) {

			try {

				query = "SELECT * FROM ";

				query += V_TABLE_DISTRICT;

				query += " BAIRRO ";

				query += "WHERE ";

				query += "BAIRRO.CD_BAIRRO = ?";

				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setInt(i++, disctrictConstraints.getCd_bairro().intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.next()) {

					cityConstraints = new CityConstraints();

					cityConstraints.setCd_cidade(new Integer(resultSet.getInt("CD_CIDADE")));

					city = this.cityPersistence.search(cityConstraints);

					if (city != null) {

						district = new District();

						district.setCity(city);

						district.setDistrictCode(new Integer(resultSet.getInt("CD_BAIRRO")));

						district.setDescription(resultSet.getString("DS_BAIRRO_NOME"));
					}
				}

			} catch (SQLException e) {
				// TODO: handle exception

				district = null;

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

		return district;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IDistrictDAO#search(vo.cep.City)
	 */
	@Override
	public List<District> search(City city) {

		// Variables declaration

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		District district = null;

		List<District> districts = null;

		DistrictConstraints districtConstraints = null;

		int i = 1;

		// Data processing

		if (city != null) {

			try {

				query = "SELECT BAIRRO.CD_BAIRRO FROM ";

				query += V_TABLE_DISTRICT;

				query += " BAIRRO ";

				query += "WHERE ";

				query += "BAIRRO.CD_CIDADE = ? ";

				query += "ORDER BY BAIRRO.DS_BAIRRO_NOME ASC";

				preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				preparedStatement.setInt(i++, city.getCityCode().intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.last()) {

					resultSet.beforeFirst();

					districts = new ArrayList<District>();

					while (resultSet.next()) {

						districtConstraints = new DistrictConstraints();

						districtConstraints.setCd_bairro(new Integer(resultSet.getInt("CD_BAIRRO")));

						district = this.search(districtConstraints);

						if (district != null) {

							districts.add(district);
						}
					}

				}

			} catch (Exception e) {
				// TODO: handle exception

				districts = null;

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

		return districts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IDistrictDAO#districtCode()
	 */
	public Integer districtCode() {

		// Variables declaration

		Integer districtCode = null;

		DistrictConstraints districtConstraints = null;

		// Data input

		districtConstraints = this.search();

		// Data processing

		if (districtConstraints != null) {

			districtCode = new Integer(districtConstraints.getCd_bairro().intValue() + 1);
		}

		// Information output

		return districtCode;
	}
}
