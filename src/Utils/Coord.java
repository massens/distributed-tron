/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author Guillem
 */
public class Coord {
    private int x;
    private int y;
    
    public Coord(){
        
        x=0;
        y=0;
        
    }
    
    public Coord(int x, int y){
        
        this.x=x;
        this.y=y;
        
    }
    public Coord(Coord c){
        this.x=c.getX();
        this.y=c.getY();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void incX(int valor){
        x+=valor;
    }
    
    public void incY(int valor){
        y+=valor;
    }
    
    public boolean equals(Coord c){
        return (this.x == c.getX()) && (this.y == c.getY());
    }
}
