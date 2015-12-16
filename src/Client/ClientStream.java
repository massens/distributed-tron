package Client;

import Utils.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class ClientStream { //implements Comunicacions {

    protected SocketChannel sc;
    protected BufferedReader teclat;

    public ClientStream(int port) throws UnknownHostException, IOException {
        sc = SocketChannel.open(new InetSocketAddress(2222));
        sc.configureBlocking(true);
        teclat = new BufferedReader(new InputStreamReader(System.in));

    }

    public void enviar(int provisional_Direction1) {
        try {

      //Formatejem les dos int a un string. 
            String s = String.format("%d", provisional_Direction1);

            System.out.println("envia " + s);

            Charset charset = Charset.forName("UTF-8");
            CharsetEncoder codificador = charset.newEncoder();
            ByteBuffer bb;
            bb = codificador.encode(CharBuffer.wrap(s));
            sc.write(bb);
            bb.clear();

        } catch (Exception e) {
        }
    }

    public int[] rebre() {
        System.out.println("Rebre!");

        String missatge = new String();
        int[] directions = new int[2];
        try {
            ByteBuffer bb = null;
            Charset charset = Charset.forName("UTF-8");
            CharsetEncoder codificador = charset.newEncoder();
            CharsetDecoder decodificador = charset.newDecoder();

            bb = codificador.encode(CharBuffer.wrap("hola"));

            bb.clear();

            //bb.clear();
            sc.read(bb);
            //Descodificar bb
            bb.flip();
            StringBuffer sb = new StringBuffer();
            sb.append(decodificador.decode(bb));
            missatge = new String(sb);
            System.out.println("Missatge rebut" + missatge);

        } catch (Exception e) {
            
        }
        directions[0] = Character.getNumericValue(missatge.charAt(0));
        directions[1] = Character.getNumericValue(missatge.charAt(1));
        System.out.println("reb " + directions[0] + directions[1]);

        return directions;//retorna el missatge rebut
    }
}
