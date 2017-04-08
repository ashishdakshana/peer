
package datastructures;

import de.uniba.wiai.lspi.chord.service.ServiceException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;




public final class RingFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2144157610883545353L;
	/**
	 * 
	 */
	public String value;
        public byte[] filebytes;

         public RingFile(String value, byte[] filebytes) {
                      this.value = value;
                      this.filebytes = filebytes;
             }
        
        

	public String toString() {
		return this.value;
	}

	public boolean equals(Object o) {
		if (o instanceof RingFile) {
			return (this.filebytes.equals(((RingFile) o).filebytes));
		}
		return false;
	}

	public int hashCode() {
		return this.value.hashCode();
	}

}
