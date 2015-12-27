package Client;

import Utils.*;
import java.io.IOException;
import java.net.*;
import java.nio.*;
import java.nio.channels.SocketChannel;
import java.nio.charset.*;


public class ClientStream { //implements Comunicacions {

    protected SocketChannel sc;

    public ClientStream(int port) throws UnknownHostException, IOException {
        sc = SocketChannel.open(new InetSocketAddress(2222));
        sc.configureBlocking(true);

    }

    public void enviar(int provisional_Direction1) {
        try {
            //Posibilitat d'inicialitzar el buffer al comenament
            //per a que no es creï un buffer cada cop, que es reutilitzi
            //Un INT son 4 bytes!!!
            System.out.println("Envia direccio: "+ provisional_Direction1);
            ByteBuffer bb = ByteBuffer.allocate(4);
            bb.asIntBuffer().put(provisional_Direction1);

            sc.write(bb);
            bb.clear();

        } catch (Exception e) {
        }
    }

    public int[] rebre() {
        System.out.println("Rebre!");

//        String missatge = new String();
        int[] directions = new int[4];
        ByteBuffer bb = ByteBuffer.allocate(16);

        try {
//            Charset charset = Charset.forName("UTF-8");
//            CharsetEncoder codificador = charset.newEncoder();
//            CharsetDecoder decodificador = charset.newDecoder();
//
//            bb = codificador.encode(CharBuffer.wrap("hola"));

//            bb.clear();

            //bb.clear();
            sc.read(bb);
            //Descodificar bb
            bb.flip();
//            
//            StringBuffer sb = new StringBuffer();
//            sb.append(decodificador.decode(bb));
//            missatge = new String(sb);
//            
//            System.out.println("Missatge rebut" + missatge);

        } catch (Exception e) {
            
        }
        
        directions[0] = bb.getInt(0);
        directions[1] = bb.getInt(4);
        directions[2] = bb.getInt(8);
        directions[3] = bb.getInt(12);

        System.out.println("reb " + directions[0] + directions[1]);

        return directions;//retorna el missatge rebut
    }
}
