/*
 * Interpolation function of given points.
 */
package interpolation;

import java.util.Vector;

/**
 *
 * @author Edoardo Carrà
 */
public class Interpolation extends Polinomial_Function{
    
    /**
     * Array of the points from which to calculate the 
     * interpolative function.
     */
    private Vector points;
    /**
     * The points split in their coordinates x and y.
     */
    private Vector xi, yi;
    /**
     * Parameters of the interpolative function(ax^4+bx^3+cx^2+dx+e).
     */    
    
    /**
     * 
     * @param points array of the points from which to calculate the 
     * interpolative function.
     */
    public Interpolation(Vector points){
        this.points = points;
        xi = new Vector();
        yi = new Vector();
        //splitting the coordinates of the points
        for(int i = 0; i<points.size(); i++){
            xi.add(((Point)points.elementAt(i)).getX());
            yi.add(((Point)points.elementAt(i)).getY());
            System.out.println("point:"+(Point)points.elementAt(i));
        }
        /*calculating the parameters of the interpolation function from the
         * points given.
         */
        calc_interpolation();
    }

    Interpolation() {
        super();
        points = new Vector();
        xi = new Vector();
        yi = new Vector();
    }
    
    public Vector getPoints(){
        return points;
    }
       
    public void setPoints(Vector points){
        this.points = points;
        xi = new Vector(points.size());
        yi = new Vector(points.size());
        //splitting the coordinates of the points
        for(int i = 0; i<points.size(); i++){
            xi.add(((Point)points.elementAt(i)).getX());
            yi.add(((Point)points.elementAt(i)).getY());
            //System.out.println("point:"+(Point)points.elementAt(i));
        }
        /*calculating the parameters of the interpolation function from the
         * points given.
         */
        calc_interpolation();
    }
    
    /**
     * this is the determinant formula for a 5x5 matrix.
     * @param m matrix 5x5 
     * @return the determinant
     */
    public static double calc_determinant(double[][] m){
       return (-m[0][3]*m[1][4]*m[2][2]*m[3][1]*m[4][0]+m[0][2]*m[1][4]*m[2][3]*m[3][1]*m[4][0]-m[1][3]*m[0][2]*m[2][4]*m[3][1]*m[4][0]+m[0][3]*m[1][2]*m[2][4]*m[3][1]*m[4][0]+m[0][3]*m[1][4]*m[2][1]*m[3][2]*m[4][0]-m[0][1]*m[1][4]*m[2][3]*m[3][2]*m[4][0]+m[1][3]*m[0][1]*m[2][4]*m[3][2]*m[4][0]-m[0][3]*m[1][1]*m[2][4]*m[3][2]*m[4][0]-m[0][2]*m[1][4]*m[2][1]*m[3][3]*m[4][0]+m[0][1]*m[1][4]*m[2][2]*m[3][3]*m[4][0]+m[0][2]*m[1][1]*m[2][4]*m[3][3]*m[4][0]-m[0][1]*m[1][2]*m[2][4]*m[3][3]*m[4][0]+m[1][3]*m[0][2]*m[2][1]*m[3][4]*m[4][0]-m[0][3]*m[1][2]*m[2][1]*m[3][4]*m[4][0]-m[1][3]*m[0][1]*m[2][2]*m[3][4]*m[4][0]+m[0][3]*m[1][1]*m[2][2]*m[3][4]*m[4][0]-m[0][2]*m[1][1]*m[2][3]*m[3][4]*m[4][0]+m[0][1]*m[1][2]*m[2][3]*m[3][4]*m[4][0]+m[0][3]*m[1][4]*m[2][2]*m[3][0]*m[4][1]-m[0][2]*m[1][4]*m[2][3]*m[3][0]*m[4][1]+m[1][3]*m[0][2]*m[2][4]*m[3][0]*m[4][1]-m[0][3]*m[1][2]*m[2][4]*m[3][0]*m[4][1]-m[0][3]*m[1][4]*m[2][0]*m[3][2]*m[4][1]+m[0][0]*m[1][4]*m[2][3]*m[3][2]*m[4][1]-m[1][3]*m[0][0]*m[2][4]*m[3][2]*m[4][1]+m[0][3]*m[1][0]*m[2][4]*m[3][2]*m[4][1]+m[0][2]*m[1][4]*m[2][0]*m[3][3]*m[4][1]-m[0][0]*m[1][4]*m[2][2]*m[3][3]*m[4][1]-m[0][2]*m[1][0]*m[2][4]*m[3][3]*m[4][1]+m[0][0]*m[1][2]*m[2][4]*m[3][3]*m[4][1]-m[1][3]*m[0][2]*m[2][0]*m[3][4]*m[4][1]+m[0][3]*m[1][2]*m[2][0]*m[3][4]*m[4][1]+m[1][3]*m[0][0]*m[2][2]*m[3][4]*m[4][1]-m[0][3]*m[1][0]*m[2][2]*m[3][4]*m[4][1]+m[0][2]*m[1][0]*m[2][3]*m[3][4]*m[4][1]-m[0][0]*m[1][2]*m[2][3]*m[3][4]*m[4][1]-m[0][3]*m[1][4]*m[2][1]*m[3][0]*m[4][2]+m[0][1]*m[1][4]*m[2][3]*m[3][0]*m[4][2]-m[1][3]*m[0][1]*m[2][4]*m[3][0]*m[4][2]+m[0][3]*m[1][1]*m[2][4]*m[3][0]*m[4][2]+m[0][3]*m[1][4]*m[2][0]*m[3][1]*m[4][2]-m[0][0]*m[1][4]*m[2][3]*m[3][1]*m[4][2]+m[1][3]*m[0][0]*m[2][4]*m[3][1]*m[4][2]-m[0][3]*m[1][0]*m[2][4]*m[3][1]*m[4][2]-m[0][1]*m[1][4]*m[2][0]*m[3][3]*m[4][2]+m[0][0]*m[1][4]*m[2][1]*m[3][3]*m[4][2]+m[0][1]*m[1][0]*m[2][4]*m[3][3]*m[4][2]-m[0][0]*m[1][1]*m[2][4]*m[3][3]*m[4][2]+m[1][3]*m[0][1]*m[2][0]*m[3][4]*m[4][2]-m[0][3]*m[1][1]*m[2][0]*m[3][4]*m[4][2]-m[1][3]*m[0][0]*m[2][1]*m[3][4]*m[4][2]+m[0][3]*m[1][0]*m[2][1]*m[3][4]*m[4][2]-m[0][1]*m[1][0]*m[2][3]*m[3][4]*m[4][2]+m[0][0]*m[1][1]*m[2][3]*m[3][4]*m[4][2]+m[0][2]*m[1][4]*m[2][1]*m[3][0]*m[4][3]-m[0][1]*m[1][4]*m[2][2]*m[3][0]*m[4][3]-m[0][2]*m[1][1]*m[2][4]*m[3][0]*m[4][3]+m[0][1]*m[1][2]*m[2][4]*m[3][0]*m[4][3]-m[0][2]*m[1][4]*m[2][0]*m[3][1]*m[4][3]+m[0][0]*m[1][4]*m[2][2]*m[3][1]*m[4][3]+m[0][2]*m[1][0]*m[2][4]*m[3][1]*m[4][3]-m[0][0]*m[1][2]*m[2][4]*m[3][1]*m[4][3]+m[0][1]*m[1][4]*m[2][0]*m[3][2]*m[4][3]-m[0][0]*m[1][4]*m[2][1]*m[3][2]*m[4][3]-m[0][1]*m[1][0]*m[2][4]*m[3][2]*m[4][3]+m[0][0]*m[1][1]*m[2][4]*m[3][2]*m[4][3]+m[0][2]*m[1][1]*m[2][0]*m[3][4]*m[4][3]-m[0][1]*m[1][2]*m[2][0]*m[3][4]*m[4][3]-m[0][2]*m[1][0]*m[2][1]*m[3][4]*m[4][3]+m[0][0]*m[1][2]*m[2][1]*m[3][4]*m[4][3]+m[0][1]*m[1][0]*m[2][2]*m[3][4]*m[4][3]-m[0][0]*m[1][1]*m[2][2]*m[3][4]*m[4][3]-m[1][3]*m[0][2]*m[2][1]*m[3][0]*m[4][4]+m[0][3]*m[1][2]*m[2][1]*m[3][0]*m[4][4]+m[1][3]*m[0][1]*m[2][2]*m[3][0]*m[4][4]-m[0][3]*m[1][1]*m[2][2]*m[3][0]*m[4][4]+m[0][2]*m[1][1]*m[2][3]*m[3][0]*m[4][4]-m[0][1]*m[1][2]*m[2][3]*m[3][0]*m[4][4]+m[1][3]*m[0][2]*m[2][0]*m[3][1]*m[4][4]-m[0][3]*m[1][2]*m[2][0]*m[3][1]*m[4][4]-m[1][3]*m[0][0]*m[2][2]*m[3][1]*m[4][4]+m[0][3]*m[1][0]*m[2][2]*m[3][1]*m[4][4]-m[0][2]*m[1][0]*m[2][3]*m[3][1]*m[4][4]+m[0][0]*m[1][2]*m[2][3]*m[3][1]*m[4][4]-m[1][3]*m[0][1]*m[2][0]*m[3][2]*m[4][4]+m[0][3]*m[1][1]*m[2][0]*m[3][2]*m[4][4]+m[1][3]*m[0][0]*m[2][1]*m[3][2]*m[4][4]-m[0][3]*m[1][0]*m[2][1]*m[3][2]*m[4][4]+m[0][1]*m[1][0]*m[2][3]*m[3][2]*m[4][4]-m[0][0]*m[1][1]*m[2][3]*m[3][2]*m[4][4]-m[0][2]*m[1][1]*m[2][0]*m[3][3]*m[4][4]+m[0][1]*m[1][2]*m[2][0]*m[3][3]*m[4][4]+m[0][2]*m[1][0]*m[2][1]*m[3][3]*m[4][4]-m[0][0]*m[1][2]*m[2][1]*m[3][3]*m[4][4]-m[0][1]*m[1][0]*m[2][2]*m[3][3]*m[4][4]+m[0][0]*m[1][1]*m[2][2]*m[3][3]*m[4][4]+m[1][3]*m[2][2]*m[3][1]*m[4][0]*m[0][4]-m[1][2]*m[2][3]*m[3][1]*m[4][0]*m[0][4]-m[1][3]*m[2][1]*m[3][2]*m[4][0]*m[0][4]+m[1][1]*m[2][3]*m[3][2]*m[4][0]*m[0][4]+m[1][2]*m[2][1]*m[3][3]*m[4][0]*m[0][4]-m[1][1]*m[2][2]*m[3][3]*m[4][0]*m[0][4]-m[1][3]*m[2][2]*m[3][0]*m[4][1]*m[0][4]+m[1][2]*m[2][3]*m[3][0]*m[4][1]*m[0][4]+m[1][3]*m[2][0]*m[3][2]*m[4][1]*m[0][4]-m[1][0]*m[2][3]*m[3][2]*m[4][1]*m[0][4]-m[1][2]*m[2][0]*m[3][3]*m[4][1]*m[0][4]+m[1][0]*m[2][2]*m[3][3]*m[4][1]*m[0][4]+m[1][3]*m[2][1]*m[3][0]*m[4][2]*m[0][4]-m[1][1]*m[2][3]*m[3][0]*m[4][2]*m[0][4]-m[1][3]*m[2][0]*m[3][1]*m[4][2]*m[0][4]+m[1][0]*m[2][3]*m[3][1]*m[4][2]*m[0][4]+m[1][1]*m[2][0]*m[3][3]*m[4][2]*m[0][4]-m[1][0]*m[2][1]*m[3][3]*m[4][2]*m[0][4]-m[1][2]*m[2][1]*m[3][0]*m[4][3]*m[0][4]+m[1][1]*m[2][2]*m[3][0]*m[4][3]*m[0][4]+m[1][2]*m[2][0]*m[3][1]*m[4][3]*m[0][4]-m[1][0]*m[2][2]*m[3][1]*m[4][3]*m[0][4]-m[1][1]*m[2][0]*m[3][2]*m[4][3]*m[0][4]+m[1][0]*m[2][1]*m[3][2]*m[4][3]*m[0][4]);
    }
    
    private void calc_interpolation(){
        double[][] m = createMatrix();
        double determinante = calc_determinant(m);
        double[] coeff = new double[5];
        //System.out.println("il determinante è:"+calc_determinant(m));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                m[j][i] = summation(xi,yi, 4-j);
            }
            coeff[i] = calc_determinant(m);
            m = createMatrix();
            //System.out.println();
        }
        assignment:{
            if(determinante==0) {
                    a=b=c=d=e=0;
                    System.out.println("sistema indeterminato o impossibile");
                    break assignment;
            }
            for (int i = 0; i < coeff.length; i++) {
                //BigDecimal bg = new BigDecimal(coeff[i]/determinante).setScale(5,BigDecimal.ROUND_DOWN);
                coeff[i] = coeff[i]/determinante;
                //System.out.println(bg);
                //coeff[i] = bg.doubleValue();
                //System.out.println(i+1+":"+coeff[i]);
            }
            a = coeff[0];
            b = coeff[1];
            c = coeff[2];        
            d = coeff[3];
            e = coeff[4];
        }
    }
    
    
    /**
     * This method creates the matrix of the system.
     * @return 
     */
    private double[][] createMatrix(){
        double[][] m = new double[5][5];        
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                m[i][j] = summation(xi,8-i-j);
            }
        }
        return m;
    }
    
    /**
     * The summation form is like: Ʃ[(Xi)^exp] .
     * 
     * @param values the vector of the values to sum
     * @param exp the exponent of the values
     * @return the result of the summation
     */
    private double summation(Vector values, int exp){
        double sum = 0;
        for(int i=0; i<values.size(); i++)  
            sum +=Math.pow((double)values.elementAt(i), exp);
        return sum; 
    }
    
    /**
     * The summation form is like: Ʃ[Yi*(Xi)^exp] .
     * 
     * @param values the values of the first operand
     * @param values2 the values of the second operand
     * @param exp the exponent of the first operand
     * @return the result of the summation
     */
    private double summation(Vector values,Vector values2, int exp){
        double sum = 0;
        for(int i=0 ; i<values.size(); i++){
            sum += (double)values2.elementAt(i)*(Math.pow((double)values.elementAt(i), exp));    
        }
        return sum; 
    }

    public Vector getXi() {
        return xi;
    }

    public Vector getYi() {
        return yi;
    }
    
    public void addPoint(Point point){
        points.add(point);
        xi.add(point.getX());
        yi.add(point.getY());
        System.out.println("point added:"+point);
        calc_interpolation();
    }
    
    /**
     * 
     * @return the array of the parameters values, like: {a,b,c,d,e}.
     */
    public double[] getParameters(){
        return new double[]{a,b,c,d,e};
    }

}
