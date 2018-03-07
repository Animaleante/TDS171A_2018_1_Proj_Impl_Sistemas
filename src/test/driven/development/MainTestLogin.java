package test.driven.development;

import static persistence.Persistence.FLAG;
import static persistence.Persistence.LIST_DATA;
import static persistence.Persistence.QUERY_ERROR;
import static persistence.Persistence.QUERY_NOT_SUCCESS;
import static persistence.Persistence.QUERY_SUCCESS;
import static persistence.Persistence.SAVE_ERROR;
import static persistence.Persistence.V_TABLE_USER;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.edu.uniopet.Chronometer;
import br.edu.uniopet.ConnectionFactory;

/**
 * Program to test the login.
 * 
 * @author Baracho
 * 
 * @since 2017 dez, 29
 * 
 * @version 1.0
 *
 */
public class MainTestLogin {

	private static Connection connection = ConnectionFactory.getInstance();

	private static Formatter formatter;

	private static int loginsSize;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Variables declaration

		List<String> logins = null;

		Set<String> loginNames = null;

		List<Element> elements = null;

		// Data processing

		Chronometer chronometer = new Chronometer("Test Login");

		chronometer.start();

		Chronometer chronometer1 = new Chronometer("list()");

		chronometer1.start();

		logins = list();

		logins = loginsStandardization(logins);

		loginsSize = logins.size();

		System.out.println("\n logins.size == " + new DecimalFormat().format(loginsSize));

		chronometer1.stop();

		if (logins != null && logins.size() > 0) {

			loginNames = loginNamesStandardization(logins);

			System.out.println("\n loginsNames.size == " + new DecimalFormat().format(loginNames.size()));

			if (loginNames != null && loginNames.size() > 0) {

				Chronometer chronometer2 = new Chronometer("Generate Score");

				chronometer2.start();

				// elements = generateScore(loginNames);

				elements = generateScore(logins, loginNames);

				chronometer2.stop();

				Chronometer chronometer3 = new Chronometer("Generate File");

				chronometer3.start();

				if (elements != null && elements.size() > 0) {

					Collections.sort(elements, new ElementComparator());

					generateFile(elements);
				}

				chronometer3.stop();
			}
		}

		chronometer.stop();

		// Information output

		ConnectionFactory.closeConnection();

		System.err.println("\n END OF THE PROGRAM.");
	}

	public static List<String> list() {
		// TODO Auto-generated method stub

		// Variables declaration

		List<String> logins = null;

		String login = null;

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		String msg = null;

		// Data processing

		try {

			query = "SELECT U.LOGIN FROM ";

			query += V_TABLE_USER;

			query += " U ";

			query += "ORDER BY U.LOGIN ASC";

			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(query);

			if (resultSet != null && resultSet.last()) {

				resultSet.beforeFirst();

				logins = new ArrayList<String>();

				while (resultSet.next()) {

					login = resultSet.getString("LOGIN");

					logins.add(login);
				}

				msg = QUERY_SUCCESS;

			} else {

				msg = QUERY_NOT_SUCCESS;
			}

		} catch (Exception e) {
			// TODO: handle exception

			logins = null;

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

				System.err.println(String.format("\n %s%s: %s", MainTestLogin.class.getSimpleName(), ".list()", msg));

				if (logins != null && logins.size() > 0) {

					System.err.println(String.format("\n %s:", LIST_DATA));

					for (String loginName : logins) {

						System.out.println(loginName);
					}
				}
			}
		}

		// Information output

		return logins;
	}

	public static List<String> loginsStandardization(List<String> logins) {

		// Variables declaration

		List<String> loginsNormalizated = null;

		// Data processing

		if (logins != null && logins.size() > 0) {

			loginsNormalizated = new ArrayList<String>();

			for (String login : logins) {

				login = login.replaceAll("[0-9]", "");

				loginsNormalizated.add(login);
			}
		}

		// Information output

		return loginsNormalizated;
	}

	public static Set<String> loginNamesStandardization(List<String> logins) {

		// Variables declaration

		Set<String> loginNames = null;

		// Data processing

		if (logins != null && logins.size() > 0) {

			loginNames = new HashSet<String>();

			for (String loginName : logins) {

				loginName = loginName.replaceAll("[0-9]", "");

				loginNames.add(loginName);
			}
		}

		// Information output

		return loginNames;
	}

	public static List<Element> generateScore(List<String> logins, Set<String> loginNames) {

		// Variables declaration

		int score = 0;

		Element element = null;

		List<Element> elements = null;

		int sum = 0;

		if (logins != null && logins.size() > 0) {

			if (loginNames != null && loginNames.size() > 0) {

				elements = new ArrayList<Element>();

				sum = 0;

				for (String loginName : loginNames) {

					score = 0;

					for (String login : logins) {

						if (loginName.toUpperCase().equals(login.toUpperCase())) {

							score++;
						}
					}

					sum += score;

					element = new Element();

					element.setLoginName(loginName);

					element.setScore(new Integer(score));

					elements.add(element);
				}
			}
		}

		// Information output

		System.out.println("\n sum == " + new DecimalFormat().format(sum));

		return elements;

	}

	public static List<Element> generateScore(Set<String> loginNames) {

		// Variables declaration

		Integer score = null;

		Element element = null;

		List<Element> elements = null;

		int sum = 0;

		// Data processing

		elements = new ArrayList<Element>();

		for (String loginName : loginNames) {

			score = search(loginName);

			if (score != null) {

				sum += score.intValue();

				element = new Element();

				element.setScore(score);

				element.setLoginName(loginName);

				elements.add(element);
			}
		}

		System.out.println("\n score sum == " + new DecimalFormat().format(sum));

		// Information output

		return elements;
	}

	public static Integer search(String login) {
		// TODO Auto-generated method stub

		// Variables declaration

		Integer score = null;

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		String msg = null;

		int i = 0;

		// Data processing

		login = login.toUpperCase() + "%";

		try {

			query = "SELECT COUNT(U.LOGIN) FROM ";

			query += V_TABLE_USER;

			query += " U ";

			query += "WHERE UPPER(U.LOGIN) LIKE ?";

			preparedStatement = connection.prepareStatement(query);

			i = 1;

			preparedStatement.setString(i++, login);

			resultSet = preparedStatement.executeQuery();

			if (resultSet != null && resultSet.next()) {

				score = new Integer(resultSet.getInt(1));
			}

		} catch (SQLException e) {
			// TODO: handle exception

			score = null;

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

				System.err.println(String.format("\n %s%s: %s", MainTestLogin.class.getSimpleName(),
						".search(String login)", msg));
			}
		}

		// Information output

		return score;
	}

	public static void generateFile(List<Element> elements) {

		int counter = 0;

		int sum = 0;

		openFile();

		if (formatter != null) {

			formatter.format("%-8s %-25s %-8s \n", "Nº", "LOGIN NAME", "SCORE");

			for (Element element : elements) {

				sum += element.getScore();

				formatter.format("%-8d %-25s %-8d \n", ++counter, element.getLoginName(),
						element.getScore().intValue());
			}

			formatter.format("%-15s == %-1s \n", "logins.size", new DecimalFormat().format(loginsSize));

			formatter.format("%-15s == %-1s \n", "elements.size", new DecimalFormat().format(elements.size()));

			formatter.format("%-15s == %-1s", "score sum", new DecimalFormat().format(sum));
		}

		closeFile();
	}

	private static void openFile() {

		final String FILE_NAME = "resources\\TestLogin\\test_login.txt";

		try {

			formatter = new Formatter(new File(FILE_NAME));

			System.err.println("\n File opened with successful!");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

			System.err.println("Error to open file.");

			e.printStackTrace();
		}
	}

	private static void closeFile() {

		if (formatter != null) {

			formatter.close();

			System.err.println("\n File closed with successful!");

		} else {

			System.err.println("\n File is not opened.!");
		}
	}
}
