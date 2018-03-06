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
import static persistence.Persistence.V_TABLE_FEDERATION_UNITY;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import vo.cep.FederationUnityConstraints;
import vo.cep.FederationUnity;

/**
 * Class to implement the operations against to the database to the
 * FederationUnity entity.
 * 
 * @author Baracho
 * 
 * @since January 07, 2017.
 * 
 * @version 1.0
 *
 */
class FederationUnityDAO implements IFederationUnityDAO {

	/**
	 * Instance variables
	 */

	private Connection connection;

	/**
	 * Default constructor
	 */
	public FederationUnityDAO() {
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
	 * @see
	 * persistence.cep.database.IFederationUnityDAO#save(vo.cep.FederationUnity)
	 */
	public FederationUnity save(FederationUnity federationUnity) {

		// Variables declaration

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		int i = 1;

		FederationUnityConstraints federationUnityConstraints = null;

		// Data processing

		if (federationUnity != null) {

			try {

				query = "INSERT INTO ";

				query += V_TABLE_FEDERATION_UNITY;

				query += " UF (";

				query += "UF.CD_UF, ";

				query += "UF.DS_UF_SIGLA, ";

				query += "UF.DS_UF_NOME) ";

				query += "VALUES (?, ?, ?)";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, federationUnity.getFederationUnityCode().intValue());

				preparedStatement.setString(i++, federationUnity.getStateAbbreviation());

				preparedStatement.setString(i++, federationUnity.getDescription());

				counter = new Integer(preparedStatement.executeUpdate());

				if (counter != null && counter.equals(1)) {

					this.connection.commit();

					msg = SAVE_SUCCESS;

					federationUnityConstraints = this.search();

					if (federationUnityConstraints != null) {

						federationUnity = this.search(federationUnityConstraints);

						if (federationUnity != null) {

							msg += RECOVERY_SUCCESS;

						} else {

							msg += RECOVERY_NOT_SUCCESS;
						}

					} else {

						federationUnity = null;

						msg = RECOVERY_ERROR;
					}

				} else {

					federationUnity = null;

					this.connection.rollback();

					msg = SAVE_NOT_SUCCESS;
				}

			} catch (SQLException e) {
				// TODO: handle exception

				federationUnity = null;

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
							".save(FederationUnity federationUnity)", msg));

					if (federationUnity != null) {

						System.err.println(String.format("\n %s: %s", SAVE_DATA, federationUnity.toString()));
					}
				}
			}
		}

		// Information output

		return federationUnity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IFederationUnityDAO#list()
	 */
	@Override
	public List<FederationUnity> list() {

		// Variables declaration

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		String msg = null;

		FederationUnityConstraints federationUnityConstraints = null;

		FederationUnity federationUnity = null;

		List<FederationUnity> federationUnities = null;

		// Data processing

		try {

			query = "SELECT UF.CD_UF FROM ";

			query += V_TABLE_FEDERATION_UNITY;

			query += " UF ";

			query += "ORDER BY UF.DS_UF_NOME ASC";

			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(query);

			if (resultSet != null && resultSet.last()) {

				resultSet.beforeFirst();

				federationUnities = new ArrayList<FederationUnity>();

				while (resultSet.next()) {

					federationUnityConstraints = new FederationUnityConstraints();

					federationUnityConstraints.setCd_uf(new Integer(resultSet.getInt("CD_UF")));

					federationUnity = this.search(federationUnityConstraints);

					if (federationUnity != null) {

						federationUnities.add(federationUnity);
					}
				}

				msg = QUERY_SUCCESS;

			} else {

				msg = QUERY_NOT_SUCCESS;
			}

		} catch (Exception e) {
			// TODO: handle exception

			federationUnities = null;

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

			if (FLAG.equals(true)) {

				if (msg != null) {

					System.err.println(String.format("\n %s: %s", this.getClass().getSimpleName(), msg));
				}

				if (federationUnities != null && federationUnities.size() > 0) {

					System.err.print(String.format("\n %s", LIST_DATA));

					for (FederationUnity uf : federationUnities) {

						System.out.println(uf.toString());
					}
				}
			}
		}

		// Information output

		return federationUnities;
	}

	public FederationUnity update(FederationUnity federationUnity) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		int i = 1;

		FederationUnityConstraints federationUnityConstraints = null;

		// Data processing

		if (federationUnity != null) {

			try {

				query = "UPDATE ";

				query += V_TABLE_FEDERATION_UNITY;

				query += " UF SET ";

				query += "UF.DS_UF_SIGLA = ?, ";

				query += "UF.DS_UF_NOME = ?, ";

				query += "WHERE ";

				query += "UF.CD_UF = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setString(i++, federationUnity.getStateAbbreviation());

				preparedStatement.setString(i++, federationUnity.getDescription());

				preparedStatement.setInt(i++, federationUnity.getFederationUnityCode().intValue());

				counter = new Integer(preparedStatement.executeUpdate());

				if (counter != null && counter.equals(1)) {

					this.connection.commit();

					msg = UPDATE_SUCCESS;

					federationUnityConstraints = new FederationUnityConstraints();

					federationUnityConstraints.setCd_uf(federationUnity.getFederationUnityCode());

					federationUnity = this.search(federationUnityConstraints);

					if (federationUnity != null) {

						msg += RECOVERY_SUCCESS;

					} else {

						msg += RECOVERY_NOT_SUCCESS;
					}

				} else {

					msg = UPDATE_NOT_SUCCESS;
				}

			} catch (SQLException e) {
				// TODO: handle exception

				federationUnity = null;

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
							".update(FederationUnity federationUnity)", msg));

					if (federationUnity != null) {

						System.err.println(String.format("\n %s %s", UPDATE_DATA, federationUnity.toString()));
					}
				}
			}
		}

		// Information output

		return federationUnity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * persistence.cep.database.IFederationUnityDAO#delete(vo.cep.FederationUnity)
	 */
	public Boolean delete(FederationUnity federationUnity) {
		// TODO Auto-generated method stub

		// Variables declaration

		Boolean flag = null;

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer counter = null;

		int i = 1;

		// Data processing

		if (federationUnity != null) {

			try {

				query = "DELETE FROM ";

				query += V_TABLE_FEDERATION_UNITY;

				query += " UF ";

				query += "WHERE ";

				query += "UF.CD_UF = ?";

				preparedStatement = this.connection.prepareStatement(query);

				preparedStatement.setInt(i++, federationUnity.getFederationUnityCode().intValue());

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
							".delete(federationUnity federationUnity)", msg));

					System.err.println(String.format(String.format("\n %s %s", DELETE_DATA,
							flag != null && flag.equals(true)
									? federationUnity != null ? federationUnity.toString() : ""
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
	 * @see persistence.cep.database.IFederationUnityDAO#search()
	 */
	public FederationUnityConstraints search() {
		// TODO Auto-generated method stub

		// Variables declaration

		Statement statement = null;

		FederationUnityConstraints federationUnityConstraints = null;

		ResultSet resultSet = null;

		String query = null;

		// Data processing

		try {

			query = "SELECT UF.CD_UF FROM ";

			query += V_TABLE_FEDERATION_UNITY;

			query += " UF ";

			query += "WHERE ";

			query += "UF.CD_UF = ";

			query += "(SELECT MAX(UF.CD_UF) FROM ";

			query += V_TABLE_FEDERATION_UNITY;

			query += " UF)";

			statement = this.connection.createStatement();

			resultSet = statement.executeQuery(query);

			federationUnityConstraints = new FederationUnityConstraints();

			if (resultSet != null && resultSet.next()) {

				federationUnityConstraints.setCd_uf(new Integer(resultSet.getInt("CD_UF")));

			} else {

				federationUnityConstraints.setCd_uf(new Integer(0));
			}

		} catch (SQLException e) {
			// TODO: handle exception

			federationUnityConstraints = null;

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

		return federationUnityConstraints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IFederationUnityDAO#search(vo.cep.
	 * FederationUnityConstraints)
	 */
	@Override
	public FederationUnity search(FederationUnityConstraints constraintsFederationUnity) {
		// TODO Auto-generated method stub

		// Variables declaration

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		FederationUnity federationUnity = null;

		int i = 1;

		// Data processing

		if (constraintsFederationUnity != null) {

			try {

				query = "SELECT * FROM ";

				query += V_TABLE_FEDERATION_UNITY;

				query += " UF ";

				query += "WHERE ";

				query += "UF.CD_UF = ?";

				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setInt(i++, constraintsFederationUnity.getCd_uf().intValue());

				resultSet = preparedStatement.executeQuery();

				if (resultSet != null && resultSet.next()) {

					federationUnity = new FederationUnity();

					federationUnity.setFederationUnityCode(resultSet.getInt("CD_UF"));

					federationUnity.setDescription(resultSet.getString("DS_UF_NOME"));

					federationUnity.setStateAbbreviation(resultSet.getString("DS_UF_SIGLA"));
				}

			} catch (

			SQLException e) {
				// TODO: handle exception

				federationUnity = null;

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

		return federationUnity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see persistence.cep.database.IFederationUnityDAO#federationUnityCode()
	 */
	public Integer federationUnityCode() {

		// Variables declaration

		Integer federationUnityCode = null;

		FederationUnityConstraints federationUnityConstraints = null;

		// Data input

		federationUnityConstraints = this.search();

		// Data processing

		if (federationUnityConstraints != null) {

			federationUnityCode = new Integer(federationUnityConstraints.getCd_uf().intValue() + 1);
		}

		// Information output

		return federationUnityCode;
	}
}