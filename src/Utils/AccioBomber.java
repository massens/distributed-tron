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
public class AccioBomber implements AccioEspecial {
    public void accio(Jugador jug){
        //Fer accio del bomber
            jug.setPositionsToSend(0);
            for (int i = -Const.RADI_BOMBA; i < Const.RADI_BOMBA + 1; i++) {
                for (int j = -Const.RADI_BOMBA; j < Const.RADI_BOMBA + 1; j++) {
                    if (((jug.getCurrentDirection() == Const.UP || jug.getCurrentDirection() == Const.DOWN) && i == 0)
                            || (jug.getCurrentDirection() == Const.LEFT || jug.getCurrentDirection() == Const.RIGHT) && j == 0) {
                        //Do nothing
                        
                    } else {
                        jug.addCoordToPath(new Coord(jug.getCentre().getX()+i,jug.getCentre().getY()+j));
                        jug.incPositionsToSend(1);
              
                    }
                }
            }
    }
}
