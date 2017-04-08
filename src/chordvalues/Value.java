
package chordvalues;

import java.io.Serializable;


public final class Value implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2144157610883545352L;
	/**
	 * 
	 */
	private String value;

	/** 
	 * Creates a new instance of Value 
	 * @param value1 
	 */
	public Value(String value1) {
		this.value = value1;
	}

	public String toString() {
		return this.value;
	}

	public boolean equals(Object o) {
		if (o instanceof Value) {
			return (this.value.equals(((Value) o).value));
		}
		return false;
	}

	public int hashCode() {
		return this.value.hashCode();
	}

}
