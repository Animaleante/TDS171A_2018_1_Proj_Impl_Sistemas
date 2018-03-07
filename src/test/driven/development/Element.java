/**
 * 
 */
package test.driven.development;

/**
 * Class to represent a element entity.
 * 
 * @author Baracho
 * 
 * @since 2017 dez, 29
 * 
 * @version 1.0
 *
 */
public class Element {

	private String loginName;

	private Integer score;

	public Element() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName
	 *            the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
}
