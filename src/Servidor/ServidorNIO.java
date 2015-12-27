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
    protected Controlador_Servidor controlador;
    protected ServerSocketChannel ssc;
    protected ServerSocket ss;
    protected Selector selector;

    protected ArrayList<SocketChannel> arraySocketChannels;

    public ServidorNIO(int port, Model_Servidor model, Controlador_Servidor controlador) throws IOException {
        this.model = model;
        model.addObserver(this);
        this.controlador = controlador;

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
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                int canalsPreparats = selector.select();
                if (canalsPreparats == 0) {
                    continue;
                }
                Set<SelectionKey> clausSeleccionades = selector.selectedKeys();
                Iterator<SelectionKey> iterador = clausSeleccionades.iterator();
                while (iterador.hasNext()) {
                    SelectionKey clau = iterador.next();
                    if (clau.isAcceptable()) {
                        //Fer accept
                        ferAccept(clau);
                    } else if (clau.isReadable()) {
                        // Fer echo
                        rebre(clau);
                    }
                    iterador.remove();
                }
            }
        } catch (IOException ex) {
        }

    }

    public void ferAccept(SelectionKey clau) throws IOException {
        SocketChannel s = ((ServerSocketChannel) clau.channel()).accept();
        s.configureBlocking(false);
        s.register(selector, SelectionKey.OP_READ);
        arraySocketChannels.add(s);

        //Inicia el Joc quan es conecta el 2n Jugador
        if (arraySocketChannels.size() > 1) {
            controlador.inici();
        }

    }

    public void rebre(SelectionKey clau) throws IOException {
        SocketChannel s = ((SocketChannel) clau.channel());
        int userId = arraySocketChannels.indexOf(s);

        ByteBuffer espai = ByteBuffer.allocate(4);
        s.read(espai);
        espai.flip();
        controlador.keyPressed(espai.getInt(), userId);

    }

    @Override
    public void update(Observable o, Object arg) {

        int[] lastPosition = (int[]) arg;

        ByteBuffer bb = ByteBuffer.allocate(16);
        bb.asIntBuffer().put(lastPosition);

        if (arraySocketChannels.size() > 1) {
            try {
                arraySocketChannels.get(0).write(bb);
                bb.position(0);
                arraySocketChannels.get(1).write(bb);
            } catch (IOException ex) {
                Logger.getLogger(ServidorNIO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
