package vo.cep;

/**
 * Class to represent the constraints of the Street entity.
 * 
 * @author Baracho
 * 
 * @since 2017 jan, 19
 * 
 * @version 1.0
 *
 */
public class StreetConstraints {

	/**
	 * Instance variables
	 */

	private Integer CD_LOGRADOURO;

	private Integer NO_LOGRADOURO_CEP;

	/**
	 * Default constructor
	 */
	public StreetConstraints() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Access methods
	 */

	/**
	 * @return the CD_LOGRADOURO
	 */
	public Integer getCD_LOGRADOURO() {
		return CD_LOGRADOURO;
	}

	/**
	 * @param CD_LOGRADOURO
	 *            the CD_LOGRADOURO to set
	 */
	public void setCD_LOGRADOURO(Integer CD_LOGRADOURO) {
		this.CD_LOGRADOURO = CD_LOGRADOURO;
	}

	/**
	 * @return the NO_LOGRADOURO_CEP
	 */
	public Integer getNO_LOGRADOURO_CEP() {
		return NO_LOGRADOURO_CEP;
	}

	/**
	 * @param NO_LOGRADOURO_CEP
	 *            the NO_LOGRADOURO_CEP to set
	 */
	public void setNO_LOGRADOURO_CEP(Integer NO_LOGRADOURO_CEP) {
		this.NO_LOGRADOURO_CEP = NO_LOGRADOURO_CEP;
	}
}
