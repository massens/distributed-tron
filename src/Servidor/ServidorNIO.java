package Servidor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import Utils.Comms;
import java.net.ServerSocket;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GERRI
 */
public class ServidorNIO extends Thread implements Observer {
    protected Model_Servidor model;
    protected ServerSocketChannel ssc;
    protected ServerSocket ss;
    protected Selector selector;
    
    protected ArrayList<SocketChannel> arraySocketChannels;

    public ServidorNIO(int port, Model_Servidor model) throws IOException {
        this.model = model;
        model.addObserver(this);
        
        arraySocketChannels = new ArrayList<SocketChannel>();
        
        ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false); //Configurem que NO bloquejant
        ss = ssc.socket();
        ss.bind(new InetSocketAddress(port));
    }

    @Override
    public void run() {
        try {
            System.out.println("OPEN!");
            selector = Selector.open();
            ssc.register(selector, SelectionKey.OP_ACCEPT);// ssc.register(selector.SelectionKey.OP_ACCEPT);
            while (true) {
                int canalsPreparats = selector.select(); //Bloquejant
                if (canalsPreparats == 0) {
                    continue;
                }
                Set<SelectionKey> clausSeleccionades = selector.selectedKeys();
                Iterator<SelectionKey> iterador = clausSeleccionades.iterator();
                while (iterador.hasNext()) {
                    SelectionKey clau = iterador.next();
                    if (clau.isAcceptable()) {
						//
                        //Fer accept
                        ferAccept(clau);
                        //					
                    } else if (clau.isReadable()) {
						//
                        // Fer echo
                        //  

                        rebre(clau);
                    }
                    iterador.remove();
                }

            }
        } catch (IOException ex) {
        }

    }

    public void ferAccept(SelectionKey clau) throws IOException {
        //A la seguent Línea, el casting no se si está ben fet
        System.out.println("ACCEPT!");
        SocketChannel s = ((ServerSocketChannel) clau.channel()).accept();
        s.configureBlocking(false);
        s.register(selector, SelectionKey.OP_READ);
        
        arraySocketChannels.add(s);
    }

    public void rebre(SelectionKey clau) throws IOException{
        SocketChannel s = ((SocketChannel) clau.channel());
        ByteBuffer espai = ByteBuffer.allocate(1024);
        s.read(espai);
        
        
        int userId = arraySocketChannels.indexOf(s);
        
        Charset charset = Charset.forName("UTF-8");
        CharsetDecoder decodificador = charset.newDecoder();     
        espai.flip();
        
        StringBuffer sb = new StringBuffer();
        sb.append(decodificador.decode(espai));    
        String entrada = new String(sb);
        
        System.out.println("Entrada: " + entrada + "User: "+userId);
        model.updateDireccio(Character.getNumericValue(entrada.charAt(0)), userId);
        
    }
    
//    public void ferEcho(SelectionKey clau) throws IOException {
//
//        SocketChannel s = ((SocketChannel) clau.channel());
//        ByteBuffer espai = ByteBuffer.allocate(1024);
//        s.read(espai);
//        System.out.println("ECHO!" + espai);
//
//        espai.flip();
//        while (espai.hasRemaining()) {
//            s.write(espai);
//        }
//    }

    @Override
    public void update(Observable o, Object arg) {
        //Fer broadcast a tothome!! Com el fer echo, però a tothom
        int[] dir = (int[]) arg;
        System.out.println("Update: "+ dir[0]+dir[1]);
            
        String s = String.format("%d%d", dir[0], dir[1]);
        
        try {

            System.out.println("Broadcast" + s);

            Charset charset = Charset.forName("UTF-8");
            CharsetEncoder codificador = charset.newEncoder();
            ByteBuffer bb;
            bb = codificador.encode(CharBuffer.wrap(s));
            arraySocketChannels.get(0).write(bb);
        } catch (Exception ex) {
            Logger.getLogger(ServidorNIO.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
}
