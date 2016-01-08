/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author marcassens
 */
public class Score {
    private int jugador1;
    private int jugador2;
    
    public Score(int p1, int p2){
        
        jugador1 = p1;
        jugador2 = p2;
        
    }
   
    public void setScore(int p1, int p2){
        
        jugador1 = p1;
        jugador2 = p2;
    }
    public int[] getScore(){
        return new int[] {jugador1, jugador2};
    }
}
