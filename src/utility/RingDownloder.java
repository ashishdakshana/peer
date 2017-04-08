/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import chordtest.MainClass;
import java.io.File;
import java.util.List;
import datastructures.*;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Ashish
 */
public class RingDownloder {
    
    
    public static List<File> downloadFile(String appid,String filename)
    {
        System.out.println("Downloading File " + filename);
        List<File> filelist=new ArrayList<File>();
       try{
       Set<Serializable> vs=MainClass.chord.retrieve(new Key(filename));
       
        File dir=new File(appid);
           if(!dir.exists())
               dir.mkdirs();
        String dirpath=dir.getAbsolutePath();
       Iterator<Serializable> it=vs.iterator();
       while(it.hasNext())
       {
          
           FileOutputStream fs=new FileOutputStream(dirpath+File.separatorChar+filename);
           filelist.add(new File(dirpath+File.separatorChar+filename));
           fs.write(((RingFile)it.next()).filebytes);
           fs.close();
           
       }
       System.out.println("File Download Complete :"+filename);
       return filelist; 
        
       }
       catch(Exception e)
       {
           
       }
       
       return filelist;
        
    }
    
    
    public static File downredFile(String appid,String redkey) throws Exception
    {
        File dir=new File("Reducedata"+File.separatorChar+appid);
           if(!dir.exists())
               dir.mkdirs();
        String dirpath=dir.getAbsolutePath();
        Set<Serializable> vs=MainClass.chord.retrieve(new Key(appid+"_"+redkey));
        
        FileOutputStream fs=new FileOutputStream(dirpath+File.separatorChar+redkey);
        Iterator<Serializable> it=vs.iterator();
       while(it.hasNext())
       {   
           fs.write(((RingFile)it.next()).filebytes);   
       }
       fs.close();
       
       return new File(dirpath+File.separatorChar+redkey);           
    }
}
