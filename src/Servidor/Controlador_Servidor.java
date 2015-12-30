/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Utils.*;
import java.util.*;

/**
 *
 * @author marcassens
 */
public class Controlador_Servidor {

    protected Model_Servidor model;
    protected int[] provisional_Direction;
    protected Timer t;

    public Controlador_Servidor(Model_Servidor model) {
        this.model = model;


        provisional_Direction = new int[2];
        provisional_Direction[0] = Const.NOACTION;
        provisional_Direction[1] = Const.NOACTION;

    }

    public void inici() {
        //Funcio Update
        t = new Timer();
        t.scheduleAtFixedRate(new UpdateTasca(), 0, Const.TASKPERIOD);
    }
    
    public void acaba() {
        t.cancel();
    }

    public void keyPressed(int direccioRebuda, int indexJugador) {

        if (direccioRebuda == Const.UP) {
            provisional_Direction[indexJugador] = Const.UP;
        } else if (direccioRebuda == Const.DOWN) {
            provisional_Direction[indexJugador] = Const.DOWN;
        } else if (direccioRebuda == Const.RIGHT) {
            provisional_Direction[indexJugador] = Const.RIGHT;
        } else if (direccioRebuda == Const.LEFT) {
            provisional_Direction[indexJugador] = Const.LEFT;
        }

    }

    class UpdateTasca extends TimerTask {

        public void run() {

            model.updateDireccio(provisional_Direction[0], provisional_Direction[1]);

            provisional_Direction[0] = Const.NOACTION;
            provisional_Direction[1] = Const.NOACTION;

        }
    }

}
