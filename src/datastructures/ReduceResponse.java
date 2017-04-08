/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructures;

/**
 *
 * @author Ashish
 */
public class ReduceResponse extends TaskToken {
    
    public String appid;
    public String redkey;
    public boolean status;
    public long starttime;
    public long endtime;
    public long timetaken;

    public ReduceResponse(String appid, String redkey, boolean status, long starttime, long endtime, long timetaken) {
        this.appid = appid;
        this.redkey = redkey;
        this.status = status;
        this.starttime = starttime;
        this.endtime = endtime;
        this.timetaken = timetaken;
    }
    
    
    
    
    
}
