/*
 * General cartesian point format: P(x,y).
 */
package it.unibo.arces.wot.sepa.apps.alarmgenerator.model;

/**
 *
 * @author Edoardo Carrà
 */
public class Point {
    //TODO: handle polar coordinates
    
    /**
     * Cartesian format P(x,y).
     */
    private double x,y;
    
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" + "x=" + x + ", y=" + y + '}';
    }
}
