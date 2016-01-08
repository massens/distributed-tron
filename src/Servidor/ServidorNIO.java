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
            System.out.println("[Server Opened]");
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
                        
                        //CONCURRENCIA claus
                        //Les claus son un recurs compartit, ja que quan finalitza
                        //el joc, el métode update() les cancela. Per aquest motiu
                        //quan en fem us, synchronitzem el objecte, i comprovem
                        //que siguin vàlides
                        synchronized(this){
                            if (clau.isValid() && clau.isAcceptable()) {
                                ferAccept(clau);
                            } else if (clau.isValid() && clau.isReadable()) {
                                rebre(clau);
                            }
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

        System.out.println("Conexió Acceptada | "+arraySocketChannels.size()+" jugadors conectats");

        //INICI DEL JOC 
        //Inicia el Joc quan es conecta el 2n Jugador
        if (arraySocketChannels.size() > 1) {
            System.out.println("~Comença el Joc!~");
            controlador.inici();
            
            //Fem Broadcast de les millors puntuacions
            int[] score = model.getBestScore();
            enviar(new int[] {Const.ENVIA_PUNTIACIONS, Const.ENVIA_PUNTIACIONS, score[0], score[1]});
        }
        
      

    }

    //CONCURRENCIA rebre i enviar
    //Aquests dos métodes utilitzen recursos compartits, els buffers d'enviament
    //i recepció, per tant els synchronitzem.
    public synchronized void rebre(SelectionKey clau) throws IOException {
        SocketChannel s = ((SocketChannel) clau.channel());
        int userId = arraySocketChannels.indexOf(s);
        bbReceptor.clear();
        s.read(bbReceptor);
        bbReceptor.flip();

        //Comprovem que hagi rebut un Integer
        if(bbReceptor.remaining() == 4) controlador.keyPressed(bbReceptor.getInt(), userId);
    }

    public synchronized void enviar(int[] paquet){
        //Algoritme que envia Integers de 4 en 4. El argument 'paquet' sempre
        //és multiple de 4.
        for(int i = 0; i < paquet.length; i += 4){
            bbEnviador.clear();
            bbEnviador.asIntBuffer().put(paquet, i, 4);
            for(SocketChannel sc : arraySocketChannels){
                bbEnviador.position(0);
                try {
                    sc.write(bbEnviador);
                } catch (IOException ex) {
                    Logger.getLogger(ServidorNIO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
    
    @Override
    public void update(Observable o, Object arg) {
        
        //ENVIAR POSICIONS
        //Si el que volem és actualitzar les posicions al joc
        if (arg instanceof int[]) {
            int[] lastPosition = (int[]) arg;
            enviar(lastPosition);
        }
        
        //ACABAR PARTIDA        
        //Si el que volem és acabar la partida
        else if (arg instanceof Integer) {
            if ((int) arg == Const.ACABA_PARTIDA) {
                System.out.println("~Partida Acabada | Desconectem " + arraySocketChannels.size() + " jugadors");
                controlador.acaba();
                enviar(Const.FINISH_CODE);
                

                System.out.print("Cancelació de les Claus...");
                
                //CONCURRENCIA claus
                //Les claus son recursos compartits. Un dels problemes que ens podem trobar, 
                //es que abans de que els clients rebin el FINISH_CODE, enviïn un paquet,
                //i quan es procesi la clau sigui cancelada.
                
                //CANCELEM LES CLAUS
                //Hem de cancelar només les claus que pertanyen a conexions amb
                //clients, que son les que retornen un SocketChannel quan invoquem
                //key.channel()
                synchronized(this){
                    for (SelectionKey key : selector.keys()) {
                        if( key.channel() instanceof SocketChannel && arraySocketChannels.contains((SocketChannel) key.channel())){
                            key.cancel();
                            System.out.print(" | Clau cancelada");
                        }
                    }   
                }
                arraySocketChannels.clear();
                System.out.println("\nArray de SocketChannels 'cleared'");
            }
        }
    }
}
