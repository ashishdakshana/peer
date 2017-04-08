/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructures;

import datastructures.TaskToken;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import utility.KeySet;

/**
 *
 * @author Ashish
 */
public class MapResponse extends TaskToken implements java.io.Serializable{

    
    
    
    public String appid;
    public int  mapno;
    public boolean status;
    public long starttime;
    public long endtime;
    public long timetaken;
    public Set<String>keyset;
    
    
    public MapResponse(String appid,int mapno,boolean status,long starttime,long endttime,File Keyfile) throws IOException
    {
        this.appid=appid;
        this.mapno=mapno;
        this.status=status;
        this.keyset=KeySet.filetoKeySet(Keyfile);
        this.starttime=starttime;
        this.endtime=endttime;
        if(status)
        this.timetaken=endttime-starttime;
        
        
        
    }
    
    
    public String getAppid() {
        return appid;
    }

    public int getMapno() {
        return mapno;
    }

    public boolean isStatus() {
        return status;
    }   
    
}
