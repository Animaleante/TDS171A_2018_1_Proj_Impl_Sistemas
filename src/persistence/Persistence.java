package persistence;

/**
 * Class to define the constants for the access layer.
 * 
 * @author Baracho
 * 
 * @since August 22th, 2015
 * 
 * @version 1.0
 *
 */
public class Persistence {

	public final static Boolean FLAG = false;

	public final static String SAVE_SUCCESS = "Save successfully.";

	public final static String SAVE_NOT_SUCCESS = "Could not save the data.";

	public final static String SAVE_ERROR = SAVE_NOT_SUCCESS + " Error: ";

	public final static String QUERY_SUCCESS = "Query successfully.";

	public final static String QUERY_NOT_SUCCESS = "Could not query the data.";

	public final static String QUERY_ERROR = QUERY_NOT_SUCCESS + " Error: ";

	public final static String UPDATE_SUCCESS = "Update successfully.";

	public final static String UPDATE_NOT_SUCCESS = "Could not update the data.";

	public final static String UPDATE_ERROR = UPDATE_NOT_SUCCESS + " Erro: ";

	public final static String DELETE_SUCCESS = "Delete successfully.";

	public final static String DELETE_NOT_SUCCESS = "Could not delete the data.";

	public final static String DELETE_ERROR = DELETE_NOT_SUCCESS + " Erro: ";

	public final static String RECOVERY_SUCCESS = " Recovery successfully.";

	public final static String RECOVERY_NOT_SUCCESS = " Could not recovery the data.";

	public final static String RECOVERY_ERROR = RECOVERY_NOT_SUCCESS + " Error: ";

	public final static String SAVE_DATA = "Saved data";

	public final static String LIST_DATA = "Data list";

	public final static String UPDATE_DATA = "Updated data";

	public final static String DELETE_DATA = "Deleted data";

	public final static String CONNECTION_SUCCESS = "Connection with the database realized with success.";

	public final static String CONNECTION_NOT_SUCCESS = "Could not get connection to database.";

	public final static String CONNECTION_ERROR = "NOT POSSIBLE TO CONNECT WITH THE DATABASE";

	public final static String CONNECTION_ERROR_LOAD_DRIVER = "NOT POSSIBLE TO LOAD THE DRIVER.";

	public final static String CONNECTION_CLOSE_SUCCESS = "Connection with the database closed with success.";

	public final static String CONNECTION_CLOSE_ERROR = "ERROR BY CLOSING THE CONNECTION WITH THE DATABASE.";

	public static final String V_OWNER = "ALUNO";

	public static final String V_TABLE_PERSON = "TB_PERSON";

	public static final String V_TABLE_USER = "TB_USER";

	public static final String V_TABLE_PERMISSION = "TB_PERMISSION";

	public static final String V_TABLE_USER_PERMISSION = "TB_USER_PERMISSION";

	public static final String V_TABLE_ADDRESS = "TB_ADDRESS";

	public static final String V_TABLE_ADDRESS_TYPE = "TB_ADDRESS_TYPE";

	public static final String V_TABLE_FEDERATION_UNITY = "UF";

	public static final String V_TABLE_CITY = "CIDADES";

	public static final String V_TABLE_DISTRICT = "BAIRROS";

	public static final String V_TABLE_STREET = "LOGRADOUROS";

	public static final String V_TABLE_PHONE = "TB_PHONE";

	public static final String V_TABLE_PHONE_TYPE = "TB_PHONE_TYPE";

	public static final String V_TABLE_PERSON_PHONE = "TB_PERSON_PHONE";

	public static final String V_TABLE_NAME_AND_GENDER = "TB_NAME_AND_GENDER";

	public static final String END_OF_THE_PROGRAMA = "END OF THE PROGRAMA.";
}
