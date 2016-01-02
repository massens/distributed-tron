/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Utils.Comms;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 *
 * @author marcassens
 */
public class Client {
    
    protected ClientStream clientComunicacions;
    
    public Client() throws UnknownHostException, IOException{
        clientComunicacions = new ClientStream(Comms.portServidor);

	Model_Client model = new Model_Client();
	final Vista_Client gui = new Vista_Client(model);
	
        Controlador_Client controlador = new Controlador_Client(gui,model, clientComunicacions);
        
        ControladorComunicacions controladorComms = new ControladorComunicacions(model, clientComunicacions);
        controladorComms.start();
    }
    
    
    
    
    public static void main(String[] args) throws UnknownHostException, IOException{
        ClientStream clientComunicacions = new ClientStream(Comms.portServidor);

	Model_Client model = new Model_Client();
	final Vista_Client gui = new Vista_Client(model);
	
        Controlador_Client controlador = new Controlador_Client(gui,model, clientComunicacions);
        
        ControladorComunicacions controladorComms = new ControladorComunicacions(model, clientComunicacions);
        controladorComms.start();
    }

    public ClientStream getClientStream() {
        
        return clientComunicacions;
        
    }
    
    
}
