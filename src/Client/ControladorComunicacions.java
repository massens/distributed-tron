package Client;

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
           
             int[] directions= comms.rebre(); // rebre missatge servidor i accions corresponents
            
             model.updateDireccio(directions[0], directions[1]);
    
        }
    }
}