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
public class AccioJumper implements AccioEspecial{
    public void accio(Jugador jug){
        //Fer accio jumper
        jug.setPositionsToSend(1);

            switch (jug.getCurrentDirection()) {
                case Const.UP:
//                        centrey1 -= Const.JUMP_LENGTH;
                    jug.getCentre().incY(-Const.JUMP_LENGTH);
                    break;
                case Const.DOWN:
//                        centrey1 += Const.JUMP_LENGTH;
                    jug.getCentre().incY(Const.JUMP_LENGTH);
                    break;
                case Const.LEFT:
//                        centrex1 -= Const.JUMP_LENGTH;
                    jug.getCentre().incX(-Const.JUMP_LENGTH);
                    break;
                case Const.RIGHT:
//                        centrex1 += Const.JUMP_LENGTH;
                    jug.getCentre().incX(Const.JUMP_LENGTH);
                    break;
            }
    }
}
