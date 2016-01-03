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
import Utils.Const;
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
    protected ByteBuffer bbReceptor;
    protected ByteBuffer bbEnviador;
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
        
        bbReceptor = ByteBuffer.allocate(4);
        bbEnviador = ByteBuffer.allocate(16);
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
                    
//                    if(!clau.isValid()) ;
                    
                    if (clau.isAcceptable()) {
                        ferAccept(clau);
                    } else if (clau.isReadable()) {
                        rebre(clau);
                    }
                    iterador.remove();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorNIO.class.getName()).log(Level.SEVERE, null, ex);
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

    public synchronized void rebre(SelectionKey clau) throws IOException {
        SocketChannel s = ((SocketChannel) clau.channel());
        int userId = arraySocketChannels.indexOf(s);
        
        bbReceptor.clear();
        s.read(bbReceptor);
        bbReceptor.flip();
        controlador.keyPressed(bbReceptor.getInt(), userId);

    }

    @Override
    public synchronized void update(Observable o, Object arg) {

        //Si el que volem és actualitzar les posicions al joc
        if (arg instanceof int[]) {
            int[] lastPosition = (int[]) arg;
            
            bbEnviador.clear();
            bbEnviador.asIntBuffer().put(lastPosition);

            if (arraySocketChannels.size() > 1) {
                try {
                    arraySocketChannels.get(0).write(bbEnviador);
                    bbEnviador.position(0);
                    arraySocketChannels.get(1).write(bbEnviador);
                } catch (IOException ex) {
                    Logger.getLogger(ServidorNIO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }         
        //Si el que volem és acabar la partida
        else if (arg instanceof Integer) {
            if ((int) arg == Const.ACABA_PARTIDA) {
                System.out.println("AVCABA PARTIDA!" + arraySocketChannels.size());
                controlador.acaba();
                
                bbEnviador.clear();
                bbEnviador.asIntBuffer().put(Const.finishCode);
                
                for (SocketChannel s : arraySocketChannels) {
                    try {
                        s.write(bbEnviador);
                        bbEnviador.position(0);
                        System.out.println("REMOVE SOCKET!");
                        s.close();

                    } catch (IOException ex) {}
                }
                arraySocketChannels.clear();
                
                                //Aixó serveix per a algo??

                //Aixó serveix per a algo??
                for(SelectionKey key : selector.keys()){
                    key.cancel();
                }
            }
        }
    }
}
