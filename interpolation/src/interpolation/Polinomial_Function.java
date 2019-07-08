/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpolation;

/**
 *
 * @author Edoardo Carrà
 */
public class Polinomial_Function {
    
    protected double a,b,c,d,e;

    public Polinomial_Function(double a, double b, double c, double d, double e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }
    
    public Polinomial_Function(){
        this.a = this.b = this.c = this.d = this.e = 0;
    }
    
    public Polinomial_Function calc_derivative(int n){
        if(n<1) return this;
        if(n>4) return new Polinomial_Function();
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
            return new Polinomial_Function(0, 0, 0, 0, a);
        else if(c==0 && d==0)
            return new Polinomial_Function(0, 0, 0, a, b);
        else if(d==0)
            return new Polinomial_Function(0, 0, a, b, c);
        else
            return new Polinomial_Function(0, a, b, c, d);
    }
    
    @Deprecated
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
    
    public boolean isZero(double x){
        return a*Math.pow(x, 4)+b*Math.pow(x, 3)+c*Math.pow(x, 2)+d*x+e==0;
    }
    
    public double valueIn(double x){
        return a*Math.pow(x, 4)+b*Math.pow(x, 3)+c*Math.pow(x, 2)+d*x+e;
    }
    
    /**
     * 
     * @return the explicit form of the interpolative function
     */
    @Override
    public String toString() {
        return "Interpolation function: f(x) = ("+a+")x^4 + ("+b+")x^3 + ("+c+")x^2 + ("+d+")x + ("+e+")";
    }
}
