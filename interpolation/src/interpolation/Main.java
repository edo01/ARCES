/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpolation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import javax.mail.MessagingException;

/**
 *
 * @author Edoardo Carrà
 */


public class Main {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MessagingException, InterruptedException, FileNotFoundException, IOException{
        Interpolation interpolation = new Interpolation();
        FileReader f;
        f=new FileReader("data/datas.txt");
        int partenza=000;
        int campioni=40;
        Alarm alarm = new Alarm(27,0,campioni-1,true);
        BufferedReader b;
        b=new BufferedReader(f);
        double[] d = new double[campioni];
        for (int i = 0; i < partenza+8080; i++) {            
            if(i<partenza){b.readLine();continue;}
            if(i%campioni==0 && i>partenza+campioni-1){  
                Vector points= new Vector();
                for (int j = 0; j < d.length; j++) {
                    points.add(new Point(j,d[j]));
                }
                interpolation.setPoints(points);
                System.out.println("");
                alarm.isAnAlarm(interpolation);
                d = new double[campioni];
            }
            d[i%campioni]=Double.parseDouble(b.readLine());
            
        }
    }
}
