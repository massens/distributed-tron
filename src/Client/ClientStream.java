package Client;

import Utils.*;
import java.io.IOException;
import java.net.*;
import java.nio.*;
import java.nio.channels.SocketChannel;
import java.util.*;

import java.nio.charset.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientStream { //implements Comunicacions {

    protected SocketChannel sc;
    protected ByteBuffer bbReceptor;
    protected ByteBuffer bbEnviador;

    public ClientStream(int port) throws UnknownHostException, IOException {
        //bbReceptor te coordenades enteres xy de 2 jugadors,  i cada Integer
        //té 4 bytes. 2x2x4=16 bytes. bbEnviador només envia la tecla premuda
        //com a un enter = 4 bytes.
        sc = SocketChannel.open(new InetSocketAddress(2222));
        sc.configureBlocking(true);
        
        System.out.println("[Connection Established] Esperant a l'altre jugador...");

        bbReceptor = ByteBuffer.allocate(16);
        bbEnviador = ByteBuffer.allocate(4);
    }

    public synchronized void enviar(int provisional_Direction1) {
        //Només hi ha un ByteBuffer Enviador per eficiéncia. Hi hauran tants
        //threads que utilitzin aquest buffer com tecles premudes, per tant
        //el buffer és un recurs compartit i la funció és synchronyzed
        
        bbEnviador.clear();
        System.out.println("Envia direccio: " + provisional_Direction1);
        bbEnviador.asIntBuffer().put(provisional_Direction1);
        
        try {
            sc.write(bbEnviador);
        } catch (Exception e) {
        }
    }

    public int[] rebre() {
        //Només hi ha un ByteBuffer Receptor, que ens construeix en el 
        //constructor, per millorar la eficiéncia. Com que només hi ha un thread
        //que utilitza aquest buffer, la funció no és synchronyzed.

        bbReceptor.clear();
        try {
            sc.read(bbReceptor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bbReceptor.flip();

        int[] directions = new int[4];
        bbReceptor.asIntBuffer().get(directions, 0, 4);

        if (Arrays.equals(directions, Const.FINISH_CODE)){
            try {
                System.out.println("~ Partida Acabada ~");
                sc.close();
                System.out.println("[Connection Closed]");

            } catch (IOException ex) {}            
        }

        return directions;
    }
}
