package it.unibo.arces.wot.sepa.apps.alarmgenerator.model;

import it.unibo.arces.wot.sepa.apps.alarmgenerator.model.Polynomial_Function;
import it.unibo.arces.wot.sepa.apps.alarmgenerator.model.Point;
import it.unibo.arces.wot.sepa.apps.alarmgenerator.model.Interpolation;

/**
 *
 * @author Edoardo Carrà
 */
public class Prediction {
    /**
     * The prediction is calculated like grade:
     * f'(x).averageInRange(range)*NumberOfMeasuresBeforeThePrediction+f(startingNumber)
     */
    public final short averageOfDerivative1 = 1;
    /**
     * The prediction is calculated like Taylor polynomial of second grade:
     * f(startingNumber)+f'(x).averageInRange(range)*NumberOfMeasuresBeforeThePrediction+0.5*f''(x).averageInRange(range)*(NumberOfMeasuresBeforeThePrediction)^2
     */
    public final short averageOfDerivative1and2 = 2;
    /**
     *  The prediction is calculated like Taylor polynomial of first grade:
     *  f'(startingNumber)*NumberOfMeasuresBeforeThePrediction+f(startingNumber)
     */
    public final short derivative1 = 3;
    /**
     * The prediction is calculated like Taylor polynomial of second grade:
     * f(startingNumber)+f'(startingNumber)*NumberOfMeasuresBeforeThePrediction+0.5*f''(startingNumber)(NumberOfMeasuresBeforeThePrediction)^2 
     */
    public final short derivative1and2 = 4;
    
    /**
     * What kind of prediction is set.
     */
    private short key; 
    
    /**
     * the X value of the point.
     */
    private final int starting_number;
    
    private final int L_limit;
    private final int G_limit;
    private final int range;
    /**
     * The value of the prediction.
     */
    private double prediction;
    /**
     * the sum of all the deviations during all the updates.
     */
    private double error = 0;
    /**
     * 
     * @param key What kind of prediction is set.
     * @param from the starting prediction point.
     * @param lower_limit
     * @param greater_limit 
     */
    public Prediction(short key, int from, int lower_limit , int greater_limit){
        switch(key){
            case averageOfDerivative1:
                this.key = averageOfDerivative1;
                break;
            case averageOfDerivative1and2:
                this.key = averageOfDerivative1and2;
                break;
            case derivative1:
                this.key = derivative1;
                break;
            case derivative1and2:
                this.key =  derivative1and2;
                break;
            default:
                this.key = 1;
        }          
        if(greater_limit-lower_limit<0){
            int temp = greater_limit;
            greater_limit = lower_limit;
            lower_limit = temp;
        }
        this.G_limit = greater_limit;
        this.L_limit = lower_limit;
        range = G_limit-L_limit;
        this.starting_number = from;
    }
       
    //la previsione è sul numero finale del prossimo range preso in considerazione
    /**
     * Predict the last number of the next range.
     * @param function
     * @return the prediction based on the key.
     */
    public double calcPrediction(Interpolation function){
        Polynomial_Function derivata1 = function.calc_derivative(1);
        Polynomial_Function derivata2 = function.calc_derivative(2);
        switch(key){
            case averageOfDerivative1:
                prediction = derivata1.averageValueInRange(L_limit, G_limit)*
                        (2*G_limit-starting_number-L_limit) + 
                        function.valueIn(L_limit+starting_number);
                break;
            case averageOfDerivative1and2:
                prediction = 0.5*derivata2.averageValueInRange(L_limit, G_limit)*
                        (G_limit+starting_number-L_limit)*(G_limit+
                        starting_number-L_limit)+
                        derivata1.averageValueInRange(L_limit, G_limit)*
                        (G_limit+starting_number-L_limit) + 
                        function.valueIn(L_limit+starting_number);
            case derivative1:
                prediction = function.valueIn(L_limit+starting_number) +
                        derivata1.valueIn(
                            function.valueIn(L_limit+starting_number))*
                            (G_limit-L_limit+starting_number);
            case derivative1and2:
                prediction = function.valueIn(L_limit+starting_number) + 
                    derivata1.valueIn(function.valueIn(L_limit+starting_number))
                    *(range+starting_number)+0.5*derivata2.valueIn
                    (function.valueIn(L_limit+starting_number))*
                    (range+starting_number)*(range+starting_number);
        }       
        return prediction;
    }
    
    /**
     * Updates the total deviations.
     * @param function 
     */
    public void updateError(Interpolation function){
        error += Math.abs(prediction-
                    ((Point)(function.getPoints().lastElement())).getY());
    }

    public double getPrediction() {
        return prediction;
    }

    public double getError() {
        return error;
    }

    public void setKey(short key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Prediction{" + "key=" + key + ", starting_number=" + starting_number + ", prediction=" + prediction + ", error=" + error + '}';
    }
    
    
    
}
