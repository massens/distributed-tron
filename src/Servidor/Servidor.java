package Servidor;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import Utils.Comms;

/**
 *
 * @author marcel
 */
public class Servidor {
     public static void main(String[] args)throws IOException{
        Model_Servidor model = new Model_Servidor();

        new ServidorNIO(Comms.portServidor, model).start();
    }
}
