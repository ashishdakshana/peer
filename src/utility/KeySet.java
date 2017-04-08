/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Ashish
 */
public class KeySet {
    
    
    public static Set<String> filetoKeySet(File keyfile) throws FileNotFoundException, IOException
    {   Set<String>st=new HashSet<String>();
    
        BufferedReader br=new BufferedReader(new FileReader(keyfile));
        while(br.ready())
        {   String line=br.readLine();
            st.add(line);
           // System.out.println(line);
        }
            
        return st;
        
    }
    /*Test Function
    public static void main(String args[]) throws IOException
    {
        filetoKeySet(new File("G:\\New folder\\as.txt"));
        
    }
    */
    
}
