package vo.cep;

/**
 * Class to represent the constraints of the City entity.
 * 
 * @author Baracho
 * 
 *         2017 jan, 19
 * 
 * @version 1.0
 *
 */
public class CityConstraints {

	/**
	 * Instance variables
	 */

	private Integer cd_cidade;

	/**
	 * 
	 * Default constructor
	 */
	public CityConstraints() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Access methods
	 */

	/**
	 * @return the cd_cidade
	 */
	public Integer getCd_cidade() {
		return cd_cidade;
	}

	/**
	 * @param cd_cidade
	 *            the cd_cidade to set
	 */
	public void setCd_cidade(Integer cd_cidade) {
		this.cd_cidade = cd_cidade;
	}
}
