package Servidor;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import Utils.Comms;
import java.sql.Connection;

/**
 *
 * @author marcel
 */
public class Servidor {
     public static void main(String[] args)throws IOException{
        Model_Servidor model = new Model_Servidor();
        Controlador_Servidor controlador = new Controlador_Servidor(model);
        ControladorSQL SQLContr = new ControladorSQL(model);
        new ServidorNIO(Comms.portServidor, model, controlador).start();
    }
}
