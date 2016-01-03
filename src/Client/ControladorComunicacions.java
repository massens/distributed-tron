package Client;

import Utils.Const;
import java.util.Arrays;

public class ControladorComunicacions extends Thread {

    protected Model_Client model;
    protected ClientStream comms;

    public ControladorComunicacions(Model_Client model, ClientStream comms) {
        this.model = model;
        this.comms = comms;
    }

    @Override
    public void run() {
        while(true){
           
             int[] posicions= comms.rebre(); // rebre missatge servidor i accions corresponents
             model.dibuixaLineas(posicions);
             if (Arrays.equals(posicions, Const.finishCode)){
                System.out.println("ACABA THREAD rebre");
                return;
             }
        }
    }
}