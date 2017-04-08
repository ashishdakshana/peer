
package chordvalues;

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


public final class FileasValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2144157610883545353L;
	/**
	 * 
	 */
	public String value;
        public byte[] filebytes;

	
	public FileasValue(String filename) throws FileNotFoundException, IOException, ServiceException {
                File file=new File(filename);
                this.value=filename;
               filebytes=Files.readAllBytes(file.toPath());
              
               chordtest.MainClass.chord.insert(new Key(filename), this);
                
               // filebytes=Files.readAllBytes(filename);
                
                
                
	}

	public String toString() {
		return this.value;
	}

	public boolean equals(Object o) {
		if (o instanceof FileasValue) {
			return (this.value.equals(((FileasValue) o).value));
		}
		return false;
	}

	public int hashCode() {
		return this.value.hashCode();
	}

}
