/*
 * Alarm generator.
 */
package it.unibo.arces.wot.sepa.apps.alarmgenerator.control;

import it.unibo.arces.wot.sepa.apps.alarmgenerator.model.Prediction;
import it.unibo.arces.wot.sepa.apps.alarmgenerator.model.Point;
import it.unibo.arces.wot.sepa.apps.alarmgenerator.model.Interpolation;
import it.unibo.arces.wot.sepa.apps.alarmgenerator.model.Email;
import java.util.Vector;

/**
 *
 * @author Edoardo Carrà
 */
public class Alarm {

    /**
     * The threshold value which won't never be exceeded.
     */
    private double threshold;
    /**
     * The interval of values to control like [L_limit;G_limit].
     */
    private int L_limit, G_limit, range;
    /**
     * The array of all the predictions from the first to the last point of the
     * range.
     */
    private Prediction[][] predictions;
    /**
     * The email object to send in case of an alarm.
     */
    private Email email;
    /**
     * True-If the values can't be higher than the threshold,True-If the values
     * can't be lower than the threshold.
     */
    private boolean up = true;
    /**
     * If you want the prediction.
     */
    private boolean withPrediction = true;
    /**
     * The point which in the case of exceeding of threshold generates the
     * alarm.
     */
    private Prediction bestPrediction;

    /**
     *
     * @param threshold The Threshold value which won't never be exceeded.
     * @param lower_limit The lowest limit of the values interval.
     * @param greater_limit The greatest limit of the values interval.
     * @param email The email object to send in case of an alarm.
     * @param up True-If the values can't be higher than the threshold,True-If
     * the values can't be lower than the threshold.
     */
    public Alarm(double threshold, int lower_limit, int greater_limit,
            Email email, boolean up) {
        this.threshold = threshold;
        this.email = email;
        this.up = up;
        if (greater_limit - lower_limit < 0) {
            int temp = greater_limit;
            greater_limit = lower_limit;
            lower_limit = temp;
        }
        this.G_limit = greater_limit;
        this.L_limit = lower_limit;
        range = G_limit - L_limit;
        predictions = new Prediction[range][4];
        for (int i = 0; i < predictions.length; i++) {
            for (short j = 0; j < predictions[i].length; j++) {
                predictions[i][j] = new Prediction((short) (j + 1), i,
                        lower_limit, greater_limit);
            }
        }
    }

    /**
     *
     * @param threshold The Threshold value which won't never be exceeded.
     * @param lower_limit The lowest limit of the values interval.
     * @param greater_limit The greatest limit of the values interval.
     * @param email The email object to send in case of an alarm.
     */
    public Alarm(double threshold, int lower_limit, int greater_limit,
            Email email) {
        this.threshold = threshold;
        this.email = email;
        if (greater_limit - lower_limit < 0) {
            int temp = greater_limit;
            greater_limit = lower_limit;
            lower_limit = temp;
        }
        this.G_limit = greater_limit;
        this.L_limit = lower_limit;
        range = G_limit - L_limit;
        predictions = new Prediction[range][4];
        for (int i = 0; i < predictions.length; i++) {
            for (short j = 0; j < predictions[i].length; j++) {
                predictions[i][j] = new Prediction((short) (j + 1), i,
                        lower_limit, greater_limit);
            }
        }
    }

    public void update(){
        
    }
    
    /**
     *
     * @param threshold The Threshold value which won't never be exceeded.
     * @param lower_limit The lowest limit of the values interval.
     * @param greater_limit The greatest limit of the values interval.
     * @param up True-If the values can't be higher than the threshold,True-If
     * the values can't be lower than the threshold.
     */
    public Alarm(double threshold, int lower_limit, int greater_limit,
            boolean up) {
        this.threshold = threshold;
        this.email = new Email();
        this.up = up;
        if (greater_limit - lower_limit < 0) {
            int temp = greater_limit;
            greater_limit = lower_limit;
            lower_limit = temp;
        }
        this.G_limit = greater_limit;
        this.L_limit = lower_limit;
        range = G_limit - L_limit;
        predictions = new Prediction[range][4];
        for (int i = 0; i < predictions.length; i++) {
            for (short j = 0; j < predictions[i].length; j++) {
                predictions[i][j] = new Prediction((short) (j + 1), i,
                        lower_limit, greater_limit);
            }
        }
    }

    /**
     * 
     * @param function the function which describes the last range elements
     * @return 
     */
    public boolean isAnAlarm(Interpolation function) {
        Vector realPoints = function.getPoints();
        int verification_range = realPoints.size() / 10;
        for (int i = realPoints.size() - 1; i > realPoints.size() - 
                verification_range - 1; i--) {
            System.out.print(((Point) realPoints.elementAt(i)).getY() + "\t");
            if (firstGradeAlarm(((Point) realPoints.elementAt(i)).getY())) {
                return true;
            }
        }
        //updating the error of all the points predictions
        System.out.println("");
        for (Prediction[] prediction1 : predictions) {
            for (Prediction prediction : prediction1) {
                prediction.updateError(function);
                prediction.calcPrediction(function);
            }
        }
        //find the best "predictor"
        double min = Double.MAX_VALUE;
        for (Prediction[] prediction1 : predictions) {
            for (Prediction prediction : prediction1) {
                if (prediction.getError() < min) {
                    min = prediction.getError();
                    bestPrediction = prediction;
                }
            }
        }
        System.out.println(bestPrediction);
        //comparing the prediciton with the threshold
        return (alarmWithPrediction(bestPrediction.getPrediction()));

    }
    
    //the value is higher than threshold now.
    boolean firstGradeAlarm(double num) {
        if (up) {
            if (num > threshold) {
                System.out.println("ALARM OF FIRST GRADE, sending email...");
                email.sendEmail(Email.gravityHIGHKEY);
                return true;
            }
        } else {
            if (num < threshold) {
                System.out.println("ALARM OF FIRST GRADE, sending email...");
                email.sendEmail(Email.gravityHIGHKEY);
                return true;
            }
        }
        return false;
    }

    //the value will be higher than the threshold soon.
    private boolean alarmWithPrediction(double num) {
        if (up) {
            if (num > threshold) {
                System.out.println("ALARM OF SECOND GRADE, sending email...");
                email.sendEmail(Email.gravityMIDDLEKEY);
                return true;
            }
        } else {
            if (num < threshold) {
                System.out.println("ALARM OF SECOND GRADE, sending email...");
                email.sendEmail(Email.gravityMIDDLEKEY);
                return true;
            }
        }
        return false;
    }
    
    public boolean isWithPrediction(){
        return withPrediction;
    }
    
    public void ablePrediction(){
        withPrediction = true;
    }
    
    public void disablePredicition(){
        withPrediction = false;
    }
    
    public int getRange() {
        return range;
    }
}
