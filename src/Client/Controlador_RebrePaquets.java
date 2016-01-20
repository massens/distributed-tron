package Client;

import Utils.Const;
import java.util.Arrays;

public class Controlador_RebrePaquets extends Thread {

    protected Model_Client model;
    protected ClientStream comms;

    public Controlador_RebrePaquets(Model_Client model, ClientStream comms) {
        this.model = model;
        this.comms = comms;
    }

    @Override
    public void run() {
        System.out.println("[Thread Start] El 'ControladorComunicacions' s'encarrega de rebre paquets");

        while(true){
           
             int[] posicions = comms.rebre(); // rebre missatge servidor i accions corresponents
             if (posicions[0] == Const.ENVIA_PUNTIACIONS && posicions[1] == Const.ENVIA_PUNTIACIONS){
                 model.setScores(posicions[2], posicions[3]);
                 //Aprofitem que les puntuacions s'màximes s'envien només al
                 //inici del joc per informar que ha començat
                 System.out.println("~ Comença el Joc! ~");
                 System.out.println("Puntuacions Màximes Rebudes");
             }
             model.afegeixPosicio(posicions);
             if (Arrays.equals(posicions, Const.FINISH_CODE)){
                 System.out.println("[Thread Closed] Tanquem el thread al 'ControladorComunicacions', encarregat de rebre");
                 return;
             }
             
    
        }
    }
}