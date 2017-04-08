
package chordvalues;

/**
 * Represents a key used within 
 * {@link de.uniba.wiai.lspi.chord.console.Main console} to 
 * store a {@link Value} within a chord network. 
 * 
 * @author  sven
 * @version 1.0.5
 */
public final class Key implements de.uniba.wiai.lspi.chord.service.Key {

	/**
	 * 
	 */
	private String key;

	/** 
	 * Creates a new instance of Key 
	 * @param key1 
	 */
	public Key(String key1) {
		this.key = key1;
	}

	/**
	 * Returns the byte for this key which is then used to calculate a unique ID
	 * for storage in the chord network.
	 * 
	 * @return Byte representation of the key.
	 */
	public byte[] getBytes() {
		return this.key.getBytes();
	}

	public String toString() {
		return "[Key: " + this.key + "]";
	}

	public boolean equals(Object o) {
		if (o instanceof Key) {
			return (this.key.equals(((Key) o).key));
		} else {
			return false;
		}
	}

	public int hashCode() {
		return this.key.hashCode();
	}
}
