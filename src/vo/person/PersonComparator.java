/**
 * 
 */
package vo.person;

import java.util.Comparator;

/**
 * Class to compare the objects of the Person class.
 * 
 * @author Baracho
 * 
 * @since janeiro 23, 2018
 * 
 * @version 1.0
 * 
 */
public class PersonComparator implements Comparator<Person> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Person obj1, Person obj2) {
		// TODO Auto-generated method stub

		String string1 = obj1.getFirstName();

		if (obj1.getMiddleName() != null && !obj1.getMiddleName().equals("")) {

			string1 += " " + obj1.getMiddleName();
		}

		if (obj1.getLastName() != null && !obj1.getLastName().equals("")) {

			string1 += " " + obj1.getLastName();
		}

		String string2 = obj2.getFirstName();

		if (obj2.getMiddleName() != null && !obj2.getMiddleName().equals("")) {

			string2 += " " + obj2.getMiddleName();
		}

		if (obj2.getLastName() != null && !obj2.getLastName().equals("")) {

			string2 += " " + obj2.getLastName();
		}

		return string1.compareTo(string2);
	}
}
