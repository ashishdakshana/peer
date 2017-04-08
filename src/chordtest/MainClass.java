/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chordtest;

import de.uniba.wiai.lspi.chord.data.URL;
import de.uniba.wiai.lspi.chord.service.Chord;
import java.io.File;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import utility.PropertyLoad;

/**
 *
 * @author Ashish
 */
public class MainClass {
    
    
     public static Chord chord=null;
       public static URL localURL = null;
       public static URL bootURL=null;
       public static Socket clientsocket=null;

    public static boolean initpeer() throws MalformedURLException, UnknownHostException
    {  try{
        de.uniba.wiai.lspi.chord.service.PropertiesLoader.loadPropertyFile();
        String bootip=PropertyLoad.getString("bootstrapperip");
        String peerip=PropertyLoad.getString("peerip");
        int bootsport=PropertyLoad.getInteger("bootstrapperport");
        int peerport=PropertyLoad.getInteger("peerport");
        //int peerport=8596;
        
       
        chord=new de.uniba.wiai.lspi.chord.service.impl.ChordImpl();
        chord.join(new URL ( "ocsocket" + "://"+peerip+":"+peerport+"/"),
                new URL ( "ocsocket" + "://"+bootip+":"+bootsport+"/"));
        }
        catch(Exception e)
        {e.printStackTrace();
            System.out.println("Error in peer initalization ");
            return false;
        }
        return true;
    }
      
    public static void main(String[] args) throws Exception
    {   cleanup();
        initpeer();
        TaskExec texec=new TaskExec();
        Thread th=new Thread(texec);
        th.start();
        
        
    }
    public static void cleanup()
    {File chorddata=new File("chorddata");
    if(chorddata.exists())
        chorddata.delete();
        
    }
    
    
}
