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
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author GERRI
 */
public class ServidorNIO extends Thread implements Observer {
    protected Model_Servidor model;
    protected ServerSocketChannel ssc;
    protected ServerSocket ss;
    protected Selector selector;

    public ServidorNIO(int port, Model_Servidor model) throws IOException {
        this.model = model;
        model.addObserver(this);
        
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

                        ferEcho(clau);
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
    }

    public void ferEcho(SelectionKey clau) throws IOException {

        SocketChannel s = ((SocketChannel) clau.channel());
        ByteBuffer espai = ByteBuffer.allocate(1024);
        s.read(espai);
        System.out.println("ECHO!" + espai);

        espai.flip();
        while (espai.hasRemaining()) {
            s.write(espai);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        //Fer broadcast a tothome!! Com el fer echo, però a tothom
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
