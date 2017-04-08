/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

/**
 *
 * @author Ashish
 */
import chordvalues.FileasValue;
import de.uniba.wiai.lspi.chord.service.ServiceException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileBreaker {

    public static long breakfile(String inputfilename,String appid) throws FileNotFoundException, ServiceException, Exception {
        

        //first argument is the file path
        File file = new File(inputfilename);

        //second argument is the number of lines per chunk
        //In particular the smaller files will have numLinesPerChunk lines
       // long numLinesPerChunk = Integer.parseInt(args[1]);

        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line;        

        long start = System.currentTimeMillis();
        int i=0;
        
        List<File> filelist=new ArrayList<File>();

        try {
            line = reader.readLine();
            for (i = 1; line != null; i++) {
                writer = new PrintWriter(new FileWriter(inputfilename +  i ));
		File file1 =new File(inputfilename + i );                
		for (;file1.length()<118000000&&line!=null;) {
			//System.out.println(file1.length());
                  
                    writer.println(line);
                    line = reader.readLine();
			
                }
                writer.flush();
                filelist.add(file1);
                
                
            }
            
            
            writer.close();

        long end = System.currentTimeMillis();
        RingUploder.uploadFile(filelist);
        
        System.out.println("Taken time[sec]:");
        System.out.println((end - start) / 1000);
        return i;
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return 0;

    }

}
