/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Ashish
 */
public  class PropertyLoad {
    
    public static Properties prop;
    public static InputStream fos;
    private  static void init() 
    {   try{
        prop=new Properties();
        fos=new FileInputStream("cloud.properties");
        //System.out.println(fs.toString());
        prop.load(fos);
       // System.out.println(fos.toString());
            }
          catch(Exception e)
          {
              System.out.println("Unable to get Cloud Properties : " +e);
          }
        
    }
    public static String getString(String property) 
    {  if(prop==null)
        init();
         
         return prop.getProperty(property);
    }
    public static int getInteger(String property)
    {   if(prop==null)
        init();
        return Integer.parseInt(prop.getProperty(property));
    }
    
    
}
