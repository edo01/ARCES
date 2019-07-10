/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpolation;

import java.util.Vector;


/**
 *
 * @author Edoardo Carrà
 */
public class Alarm {
    
    private double threshold;
    private int L_limit, G_limit, range;
    private Prediction[][] predictions;
    private Email email;
    private boolean up = true;
    private Prediction bestPrediction;
    
    public Alarm(double threshold, int lower_limit , int greater_limit,
            Email email, boolean up){
        this.threshold = threshold;
        this.email = email;
        this.up=up;
        if(greater_limit-lower_limit<0){
            int temp = greater_limit;
            greater_limit = lower_limit;
            lower_limit = temp;
        }
        this.G_limit = greater_limit;
        this.L_limit = lower_limit;
        range = G_limit-L_limit;
        predictions = new Prediction[range][4];
        for (int i=0;i<predictions.length;i++) {
            for (short j = 0; j < predictions[i].length; j++) {
                predictions[i][j] = new Prediction((short)(j+1), i,
                        lower_limit, greater_limit);
            }
        }
    }
    
    public Alarm(double threshold, int lower_limit , int greater_limit,
            Email email){
        this.threshold = threshold;
        this.email = email;
        if(greater_limit-lower_limit<0){
            int temp = greater_limit;
            greater_limit = lower_limit;
            lower_limit = temp;
        }
        this.G_limit = greater_limit;
        this.L_limit = lower_limit;
        range = G_limit-L_limit;
        predictions = new Prediction[range][4];
        for (int i=0;i<predictions.length;i++) {
            for (short j = 0; j < predictions[i].length; j++) {
                predictions[i][j] = new Prediction((short)(j+1), i,
                        lower_limit, greater_limit);
            }
        }
    }
    
    public Alarm(double threshold, int lower_limit , int greater_limit,
            boolean up){
        this.threshold = threshold;
        this.email = new Email();
        this.up=up;
        if(greater_limit-lower_limit<0){
            int temp = greater_limit;
            greater_limit = lower_limit;
            lower_limit = temp;
        }
        this.G_limit = greater_limit;
        this.L_limit = lower_limit;
        range = G_limit-L_limit;
        predictions = new Prediction[range][4];
        for (int i=0;i<predictions.length;i++) {
            for (short j = 0; j < predictions[i].length; j++) {
                predictions[i][j] = new Prediction((short)(j+1), i,
                        lower_limit, greater_limit);
            }
        }
    }
    
    public boolean isAnAlarm(Interpolation function){
        Vector realPoints = function.getPoints();
        int verification_range = realPoints.size()/10;
        for (int i = realPoints.size()-1; i > realPoints.size()-verification_range-1; i--) {
            System.out.print(((Point)realPoints.elementAt(i)).getY()+"\t");
            if(firstGradeAlarm(((Point)realPoints.elementAt(i)).getY())) return true;
        }
        System.out.println("");
        for (Prediction[] prediction1 : predictions) {
            for (Prediction prediction : prediction1) {
                prediction.updateError(function);
                prediction.calcPrediction(function);
            }
        }
        
        double min = Double.MAX_VALUE;
        for (Prediction[] prediction1 : predictions) {
            for (Prediction prediction : prediction1) {
                if(prediction.getError()<min){
                    min = prediction.getError();
                    bestPrediction = prediction;
                }
            }
        }
        System.out.println(bestPrediction);
        return (alarmWithPrediction(bestPrediction.getPrediction()));
        
    }
    
    private boolean firstGradeAlarm(double num){
        if(up){
            if(num>threshold){
                System.out.println("ALARM OF FIRST GRADE, sending email...");
                email.sendEmail(Email.gravityHIGHKEY);
                return true;
            }
        }else{
            if(num<threshold){
                System.out.println("ALARM OF FIRST GRADE, sending email...");
                email.sendEmail(Email.gravityHIGHKEY);
                return true;
            }
        }
        return false;
    }
    
    private boolean alarmWithPrediction(double num){
        if(up){
            if(num>threshold){
                System.out.println("ALARM OF SECOND GRADE, sending email...");
                email.sendEmail(Email.gravityMIDDLEKEY);
                return true;
            }
        }else{
            if(num<threshold){
                System.out.println("ALARM OF SECOND GRADE, sending email...");
                email.sendEmail(Email.gravityMIDDLEKEY);
                return true;
            }
        }
        return false;
    }
    
}
