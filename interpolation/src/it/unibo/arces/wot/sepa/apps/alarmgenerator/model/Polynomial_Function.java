/*
 * A generic polynomial function.
 */
package it.unibo.arces.wot.sepa.apps.alarmgenerator.model;

/**
 *
 * @author Edoardo Carrà
 */
public class Polynomial_Function {
    /**
     * Parameters of the interpolative function(ax^4+bx^3+cx^2+dx+e).
     */    
    protected double a,b,c,d,e;

    /**
     * function(ax^4+bx^3+cx^2+dx+e).
     */
    public Polynomial_Function(double a, double b, double c, double d, double e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }
    /**
     * The function will be f(x)=0.
     */
    public Polynomial_Function(){
        this.a = this.b = this.c = this.d = this.e = 0;
    }
    
    /**
     * calculates the n-derivative of a Polynomial_Function.
     * @param n
     * @return 
     */
    public Polynomial_Function calc_derivative(int n){
        if(n<1) return this;
        if(n>4) return new Polynomial_Function();
        double a=this.a;
        double b=this.b;
        double c=this.c;
        double d=this.d;
        double e =0;
        for (int i = 0; i < n; i++) {
            a*=4-i;
            if(b!=0) b*=3-i;
            if(c!=0) c*=2-i;
            if(d!=0) d*=1-i;
        }
        if(b==0 && c==0 && d==0) 
            return new Polynomial_Function(0, 0, 0, 0, a);
        else if(c==0 && d==0)
            return new Polynomial_Function(0, 0, 0, a, b);
        else if(d==0)
            return new Polynomial_Function(0, 0, a, b, c);
        else
            return new Polynomial_Function(0, a, b, c, d);
    }
    /**
     * working in progress.
     * @return
     * @deprecated
     */
    public double[] when_positive(){
        double[] solutions = new double[4];
        double q = 12 * a * e - 3 * b * d + Math.pow(c, 2);
        System.out.println("q:"+q);
        double s = 27 * a * Math.pow(d, 2) + 72 * a * c * e +27 * Math.pow(b, 2) 
                * e+ 9 * b * c * d + 2 * Math.pow(c, 3);
        System.out.println("s:"+s);
        double p =(8*a*c-3*Math.pow(b, 2))/8*Math.pow(a, 2);
        System.out.println("p:"+p);
        double S = (8 * Math.pow(a, 2) * d - 4*a*b*c+Math.pow(b, 3))/8*Math.pow(a, 3);
        System.out.println("S:"+S);
        double delta;
        if((s+Math.sqrt(Math.pow(s,2)-4*Math.pow(q, 3)))/2<0)
            delta = - Math.pow((-(s+Math.sqrt(Math.pow(s,2)-4*Math.pow(q, 3)))/2), 1/3.0);
        else
            delta = Math.pow(((s+Math.sqrt(Math.pow(s,2)-4*Math.pow(q, 3)))/2), 1/3.0);
        System.out.println("delta:" + delta);
        double Q = 0.5*Math.sqrt(-(2/3)*p+(((delta+q)/delta)/(3*a)));
        System.out.println("Q:"+Q);
        System.out.println();
        solutions[0] = -(b/4*a)-Q+0.5*Math.sqrt(-4*Math.pow(Q,2)-2*p+(S/Q));
        System.out.println("zero numero 1:" + solutions[0]);
        solutions[1] = -(b/4*a)-Q-0.5*Math.sqrt(-4*Math.pow(Q,2)-2*p+S/Q);
        System.out.println("zero numero 2:" + solutions[1]);
        solutions[2] = -(b/4*a)+Q+0.5*Math.sqrt(-4*Math.pow(Q,2)-2*p+S/Q);
        System.out.println("zero numero 3:" + solutions[2]);
        solutions[3] = -(b/4*a)+Q-0.5*Math.sqrt(-4*Math.pow(Q,2)-2*p+S/Q);
        System.out.println("zero numero 4:" + solutions[3]);
        if(isZero(solutions[1])) System.out.println("soluzione 4 buona");
        return solutions;
    }
    /**
     * If x is a zero of the function.
     * @param x 
     * @return 
     */
    public boolean isZero(double x){
        return a*Math.pow(x, 4)+b*Math.pow(x, 3)+c*Math.pow(x, 2)+d*x+e==0;
    }
    /**
     * The value in x.
     * @param x
     * @return 
     */
    public double valueIn(double x){
        return a*Math.pow(x, 4)+b*Math.pow(x, 3)+c*Math.pow(x, 2)+d*x+e;
    }
    /**
     * The average value of the integer numbers of an interval.
     * @param from the starting number.
     * @param to the last number.
     * @return the average value.
     */
    public double averageValueInRange(int from, int to){
        if(to-from<0){
            double t = to;
            to = from;
            from = to;
        }
        double somma=0;
        for (int i = from; i <=to; i++) {
            somma += valueIn(i);
        }
        return somma/(to-from);
    }
    /**
     * Working in progress. 
     * @deprecated
     */
    public double toBeTheNumber(double number,int start, int threshold){
        for (int i = start; i < start+threshold; i++) {
            if(valueIn(i)<number){
                return i;
            }
        }
        return 0;
    }
    
    /**
     * 
     * @return the explicit form of the interpolative function
     */
    @Override
    public String toString() {
        //return a+"\n"+b+"\n"+c+"\n"+d+"\n"+e;
        return "Interpolation function: f(x) = ("+a+")x^4 + ("+b+")x^3 + ("+c+")x^2 + ("+d+")x + ("+e+")";
    }
}
