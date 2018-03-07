package test.driven.development;

import java.util.Comparator;

/**
 * Class to compare the objects of the Element class.
 * 
 * @author Baracho
 * 
 * @since December 29, 2017
 * 
 * @version 1.0
 *
 */
public class ElementComparator implements Comparator<Element> {

	/**
	 * Default constructor
	 */
	public ElementComparator() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Element obj1, Element obj2) {
		// TODO Auto-generated method stub

		return obj1.getLoginName().compareTo(obj2.getLoginName());
	}
}
