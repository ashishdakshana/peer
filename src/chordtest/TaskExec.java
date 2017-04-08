/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chordtest;

import de.uniba.wiai.lspi.chord.data.URL;
import de.uniba.wiai.lspi.chord.service.Chord;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utility.PropertyLoad;

/**
 *
 * @author Ashish
 */
public class TaskExec implements Runnable {
    
    
    
                   
    
    @Override
    public void run() {
            
        
        
        try {
              String bootsip=PropertyLoad.getString("bootstrapperip");
              int tokenlistenport=PropertyLoad.getInteger("tokenlistenport");
              int sleepint=PropertyLoad.getInteger("tokensleepinterval");
            
            while(true)
            {
               //check for resources 
                
                Socket sc=new Socket(bootsip,tokenlistenport);
                if(sc.isConnected())
                {
                Worker worker=new Worker(sc);
                Thread th=new Thread(worker);
                th.start();
                th.join();
                if(sc.isConnected())
                sc.close();
                
                }
                System.out.println("Sleeping for Time (ms): "+sleepint);
                Thread.sleep(sleepint);
                
               
            }
            
            
        } catch (IOException ex) {
            
            System.out.println("Unable to connect to server");
            Logger.getLogger(TaskExec.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TaskExec.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
