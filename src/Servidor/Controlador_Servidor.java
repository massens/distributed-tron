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
    protected int[] provisional_Key;
    protected Timer t;

    public Controlador_Servidor(Model_Servidor model) {
        this.model = model;


        provisional_Key = new int[2];
        provisional_Key[0] = Const.NOACTION;
        provisional_Key[1] = Const.NOACTION;

    }

    public void inici() {
        //Funcio Update
        t = new Timer();
        t.scheduleAtFixedRate(new UpdateTasca(), 0, Const.TASKPERIOD);
        System.out.println("[Timer] Start schedule");
    }
    
    public void acaba() {
        t.cancel();
        System.out.println("[Timer] End schedule");

    }

    public void keyPressed(int teclaRebuda, int indexJugador) {
        provisional_Key[indexJugador] = teclaRebuda;
    }

    class UpdateTasca extends TimerTask {

        public void run() {

            model.update(provisional_Key[0], provisional_Key[1]);

            provisional_Key[0] = Const.NOACTION;
            provisional_Key[1] = Const.NOACTION;

        }
    }

}
