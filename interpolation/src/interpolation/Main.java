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
        /*
        Point[] p = new Point[10];
        p[0]=new Point(0,2.3);
        p[1]=new Point(1,9);
        p[2]=new Point(2,5);
        p[3]=new Point(3,7.2);
        p[4]=new Point(4,5);
        p[5]=new Point(5,6);
        p[6]=new Point(6,9);
        p[7]=new Point(7,1);
        p[8]=new Point(8,0.6);
        p[9]=new Point(9,9);
        Interpolation interpolation = new Interpolation(p);
        System.out.println(interpolation);
        
        Polinomial_Function function = new Polinomial_Function(1,3,-9,4,1);
        //Polinomial_Function function = new Polinomial_Function(1,1,1,1,1);
        function.when_positive();
       
        Email email = new Email();
        email.sendEmail();
        */
        //double[] d = new double[]{28.54, 28.84, 28.74, 29.42, 28.93, 28.74, 27.08, 29.13, 28.74, 30.01, 28.93, 30.11, 29.42, 29.33, 28.84, 28.93, 30.11, 28.64, 28.15, 26.98, 29.81, 27.96, 28.45, 28.93, 29.33, 28.45, 28.64, 26.88, 28.54, 28.35, 27.76, 28.93, 29.42, 29.23, 28.84, 29.03, 29.62, 28.64, 28.84, 28.64, 29.03, 31.96, 29.03, 29.13,33.43, 32.65, 30.69, 34.41, 28.74, 30.69, 30.69, 31.87, 30.69, 32.55, 32.36, 31.67, 32.36, 31.77, 31.67, 32.45, 30.69, 30.6, 32.94, 31.67, 30.11,33.43, 32.65, 30.69, 34.41, 28.74, 30.69, 30.69, 31.87, 30.69, 32.55, 32.36, 31.67, 32.36, 31.77, 31.67, 32.45, 30.69, 30.6, 32.94, 31.67, 30.11, 32.16, 31.87, 32.65, 32.16, 38.32, 34.21, 33.43, 33.43, 32.75, 31.67, 30.11, 32.75, 30.79, 33.14, 32.94, 33.04, 32.36, 28.15, 32.84, 32.16, 32.45, 32.06, 32.45, 32.06, 32.65, 29.91, 31.09, 32.36, 31.87, 32.55, 32.55, 32.94, 33.04, 33.14, 33.33, 32.94, 31.67, 33.33, 33.24, 31.96, 32.26, 33.33, 33.24};
        /*
        for (int i = 0; i < d.length; i++) {
            p.add(new Point(i,d[i]));
        }
        System.out.println(interpolation);
        /*Polinomial_Function function = new Polinomial_Function(1,3,-9,4,1);
        //Polinomial_Function function = new Polinomial_Function(1,1,1,1,1);
        */
        Interpolation interpolation = new Interpolation();
        FileReader f;
        f=new FileReader("data/datas.txt");

        BufferedReader b;
        b=new BufferedReader(f);
        double[] d = new double[100];
        for (int i = 0; i < 2280+d.length; i++) {
            /*Point p = new Point(conta,Double.parseDouble(b.readLine()));
            interpolation.addPoint(p);
            */
            if(i<=2280){b.readLine();continue;}
            d[i-2280]=Double.parseDouble(b.readLine());
            //if(i%1000==0)System.out.println(i);
            /*System.out.println("value of interpolative function in "
                     + p +":"+interpolation.valueIn(p.getX()));
            System.out.println("value of the first derivate of the interpolative function in "
                     + p +":"+interpolation.calc_derivative(1).valueIn(p.getX())); 
            System.out.println("value of the second derivate of the interpolative function in "
                     + p +":"+interpolation.calc_derivative(2).valueIn(p.getX()));
            double first = interpolation.valueIn(p.getX());
            double second = interpolation.calc_derivative(1).valueIn(p.getX());
            double third = interpolation.calc_derivative(2).valueIn(p.getX());*/
            /*
            int soglia1=30;
            int primo=0;
            int secondo=0;
            int terzo=0;
            if(first>34) {
                System.out.println("ALLARMEEEEEEEE:PRIMO");
                primo++;
            }
            if(first>32 && second>1){
                System.out.println("ALLARMEEEEEEEE:SECONDO");
                secondo++;
            }
            if(first>31 && second>1 && third>0){
                System.out.println("ALLARMEEEEEEEE:TERZO");
                terzo++;
            }
            
        }
        System.out.println("primo:"+primo);
        System.out.println("secondo:"+secondo);
        System.out.println("terzo:"+terzo);*/
        }
        Vector points= new Vector();
        for (int i = 0; i < d.length; i++) {
            points.add(new Point(i,d[i]));
        }
        interpolation.setPoints(points);
        System.out.println(interpolation+"\n\n\n\n\n");
            
    }
}
