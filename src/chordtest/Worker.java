/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chordtest;

import de.uniba.wiai.lspi.chord.service.Chord;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import datastructures.*;
import de.uniba.wiai.lspi.chord.console.command.entry.Value;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utility.PropertyLoad;
import utility.RingDownloder;
import utility.RingUploder;

/**
 *
 * @author Ashish Worker class is used to receive task token from bootstrapper
 * and according to task token execmap()
 */
public class Worker implements Runnable {

    public TaskToken tasktoken;
    public Socket servsock;

    Worker(Socket servsock) {
        this.servsock = servsock;
    }

    @Override
    public void run() {

        ObjectInputStream os;
        TaskToken tasktoken;

        try {
            if (servsock.isConnected()) {
                os = new ObjectInputStream(servsock.getInputStream());
                tasktoken = (TaskToken) os.readObject();

                if (tasktoken instanceof Maps) {
                    System.out.println("Found Map Task");
                    //Chord chord=MainClass.chord;
                    Maps temp = (Maps) tasktoken;
                    //servsock.close();//Close connection to server after you get tasktoken
                    long ls = System.currentTimeMillis();
                    execmap(temp);
                    long end = System.currentTimeMillis();

                    System.out.println("Execution time for map " + temp.mapno + " " + String.valueOf(end - ls));

                } else if (tasktoken instanceof Reduce) {
                    System.out.println("Found Reduce Task");
                    execred((Reduce)tasktoken);
                    servsock.close();
                } else if (tasktoken instanceof EmptyTask) {
                    System.out.println("No task in queue ... You are Free");
                    servsock.close();
                }
            } else {

            }
        } catch (Exception ex) {

            //System.out.println("Exception in connection "+ ex);   
            // Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    public void execmap(Maps map) throws Exception {
        Chord chord = MainClass.chord;
        String appid = map.appid;
        int mapno = map.mapno;

        List<File> filelist = RingDownloder.downloadFile(appid, appid + "_input" + mapno);
        List<File> jarlist = RingDownloder.downloadFile(appid, appid + "_mapred.jar");

        File jarfile = jarlist.iterator().next();
        for (File file : filelist) {
            long starttime = System.currentTimeMillis();
            Process proc = Runtime.getRuntime().exec("java -jar " + jarfile.getAbsolutePath() + " " + file.getAbsolutePath() + " Map " + appid + " " + mapno);

            proc.waitFor();
            long endtime = System.currentTimeMillis();
            int returnval = proc.exitValue();
            // Then retreive the process output
            InputStream in = proc.getInputStream();
            InputStream err = proc.getErrorStream();

            byte b[] = new byte[in.available()];
            in.read(b, 0, b.length);
            System.out.println(new String(b));

            byte c[] = new byte[err.available()];
            err.read(c, 0, c.length);
            System.out.println(new String(c));

            if (returnval == 0) {

                File keyfile = new File(file.getParent() + File.separatorChar + "Mapoutput" + File.separatorChar + mapno + File.separatorChar + "keyfile");
                File[] mapfile = keyfile.getParentFile().listFiles();
                System.out.println("found mapfile list " + " for" + keyfile.getParent());
                for (File ufile : mapfile) {
                    if (ufile.equals(keyfile)) {
                        //send keyfile to server
                    } else {
                        RingUploder.uploadFile(Arrays.asList(ufile));
                    }
                }
                System.out.println("starting mapresopnse");
                MapResponse mapr = new MapResponse(appid, mapno, true, starttime, endtime, keyfile);
                sendMapRes(mapr);

            } else {
                MapResponse mapr = new MapResponse(appid, mapno, false, starttime, endtime, null);
                sendMapRes(mapr);

            }

        }
        System.out.println("herw22");

    }

    public static boolean sendMapRes(MapResponse mapr) throws Exception {
        Socket maprs = new Socket(PropertyLoad.getString("bootstrapperip"), PropertyLoad.getInteger("responsehandlerport"));
        System.out.println("in sendMapres");
        ObjectOutputStream ops = new ObjectOutputStream(maprs.getOutputStream());
        ops.writeObject(mapr);
        maprs.close();
        return true;

    }
    
    public static boolean sendRedRes(ReduceResponse redr) throws Exception {
        Socket redrs = new Socket(PropertyLoad.getString("bootstrapperip"), PropertyLoad.getInteger("responsehandlerport"));
        System.out.println("in sendReduceres");
        ObjectOutputStream ops = new ObjectOutputStream(redrs.getOutputStream());
        ops.writeObject(redr);
        redrs.close();
        return true;

    }

    public void execred(Reduce red) {
        if (red == null) {
            return;
        }

        Chord chord = MainClass.chord;
        String appid = red.appid;
        String redkey = red.redkey;
        try{
        File redfile=RingDownloder.downredFile(appid,redkey);
        List<File> jarlist = RingDownloder.downloadFile(appid, appid + "_mapred.jar");

        File jarfile = jarlist.iterator().next();
         long starttime = System.currentTimeMillis();
         String comm="java -jar " + jarfile.getAbsolutePath() + " " + redfile.getAbsolutePath() + " Reduce " + appid + " " + redkey;
            System.out.println(comm); 
         Process proc = Runtime.getRuntime().exec(comm);

            proc.waitFor();
            long endtime=System.currentTimeMillis();
            InputStream in = proc.getInputStream();
            InputStream err = proc.getErrorStream();

            byte b[] = new byte[in.available()];
            in.read(b, 0, b.length);
            System.out.println(new String(b));

            byte c[] = new byte[err.available()];
            err.read(c, 0, c.length);
            System.out.println(new String(c));
            
           // File redoutdir=new File(appid+File.separatorChar+"reduceoutput"+File.separatorChar+appid+"_results");
            File redoutdir=new File(appid+File.separatorChar+"reduceoutput");
            
           RingUploder.uploadFile(Arrays.asList(redoutdir.listFiles()));
           
          // RingFile f1=new RingFile("f1",Files.readAllBytes(new File("23").toPath()));
          // RingFile f2=new RingFile("f1",Files.readAllBytes(new File("24").toPath()));
                   
         //  MainClass.chord.insert(new Key(appid+"_results"),new Value(new String(Files.readAllBytes(redoutdir.toPath()))));
         //  MainClass.chord.insert(new Key(appid+"_results"),new Value("j23o"));
           
           
          //  MainClass.chord.insert(new Key(appid+"_results"), new RingFile(appid+"_results",Files.readAllBytes(new File(redoutdir.getAbsolutePath()+File.separatorChar+appid+"_results").toPath())));
           for(File s:redoutdir.listFiles())
               s.delete();
            ReduceResponse redres=new ReduceResponse(appid,redkey,true,starttime,endtime,endtime-starttime);
            
            sendRedRes(redres);
            

        
        
        
        }
        catch(Exception e)
        {System.out.println(e);
        }
        
        
        }

    

}
