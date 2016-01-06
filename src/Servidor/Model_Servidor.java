/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

/**
 *
 * @author marcassens
 */
import Client.Vista_Client;
import Utils.*;

import java.util.ArrayList;

import java.util.*;

public class Model_Servidor extends Observable {

//    Coord centre[] = new Coord[2];
//
//    int currentDirection[];
//    int positionsToSend[];
//
//    ArrayList<Coord> path1;
//    ArrayList<Coord> path2;
//
//    int tipusJugadors[];
    
    Jugador jugador[];
    
    //Mides de la finestra
    int windowHeight;
    int windowWidth;
    
    Vista_Client v;

    public Model_Servidor() {
        //Construir vars

        jugador = new Jugador[Const.NUM_JUGADORS];
        
        for(int i=0; i<Const.NUM_JUGADORS; i++){
            jugador[i]=new Jugador();
        }
        
        //A partir d'aqui considerem que tenim només 2 jugadors.
//        centre[0] = new Coord(40, 40);
//        centre[1] = new Coord(600, 440);
        jugador[0].setCentre(new Coord(40,40));
        jugador[1].setCentre(new Coord(600,440));

        //Direcció inicial
        
        //currentDirection = new int[2];
        
//        currentDirection[0] = Const.RIGHT;
//        currentDirection[1] = Const.LEFT;
        jugador[0].setCurrentDirection(Const.RIGHT);
        jugador[1].setCurrentDirection(Const.LEFT);

        //Número de posicións que s'han d'enviar pel jugador 1 i 2
        //positionsToSend = new int[2];

        //Quant avancen a cada "refrescada"

//        path1 = new ArrayList<Coord>();
//        path2 = new ArrayList<Coord>();
        
//        tipusJugadors = new int[2];

    }

    //Getters

    public ArrayList<Coord> getPath1() {
        //return path1;
        return jugador[0].getPath();
    }

    public ArrayList<Coord> getPath2() {
        //return path2;
        return jugador[1].getPath();
    }

//    public int getTipusJugadors(int index) {
//        return tipusJugadors[index];
//    }

    public void update(int provisional_Direction1, int provisional_Direction2) {
        updateJugador(provisional_Direction1, 0);
        updateJugador(provisional_Direction2, 1);
        //System.out.println("Direccions Jugadors:" + currentDirection[0]+currentDirection[1]);
        dibuixaLineas();

//                avisarObservadors();
    }

    //Funcions especials per tractar dades
    

    public void dibuixaLineas() {

        //System.out.println("Dibuixa Lineas amb direccions: "+ currentDirection[0] +" "+currentDirection[1]);
        
        jugador[0].movimentJugador();
        jugador[1].movimentJugador();

        //Afegeix les noves posicions
//        path1.add(new Coord(centre[0]));
//        path2.add(new Coord(centre[1]));
        jugador[0].addCoordToPath(new Coord(jugador[0].getCentre()));
        jugador[1].addCoordToPath(new Coord(jugador[1].getCentre()));

        //Avisa a la Vista_Client
        avisarObservadors(Const.UPDATE_POSITION);

        //CONDICIÓ DE COL·LISIÓ
        for (int x = 0; x < jugador[0].getPath().size() - 1; x++) {
            if (((jugador[0].getCentre().equals(jugador[0].getPath().get(x))))) {
                //1 ha xocat, guanya 2
                System.out.println("Victoria de J2");
                acabaPartida();
            } else if (jugador[1].getCentre().equals(jugador[0].getPath().get(x))) {
                //2 ha xocat, guanya 1
                System.out.println("Victoria de J1");
                acabaPartida();
            }
        }
        for (int x = 0; x < jugador[1].getPath().size() - 1; x++) {

            if (jugador[0].getCentre().equals(jugador[1].getPath().get(x))) {
                //1 ha xocat, guanya 2
                System.out.println("Victoria de J2");
                acabaPartida();
            } else if (jugador[1].getCentre().equals(jugador[1].getPath().get(x))) {
                //2 ha xocat, guanya 1
                System.out.println("Victoria de J1");
                acabaPartida();
            }
        }
    }

//    public void accioEspecial(int indexJugador) {
//        
//        positionsToSend[indexJugador] = 1;
//
//        if (tipusJugadors[indexJugador] == Const.BOMBER) {
//            for (int i = -Const.RADI_BOMBA; i < Const.RADI_BOMBA + 1; i++) {
//                for (int j = -Const.RADI_BOMBA; j < Const.RADI_BOMBA + 1; j++) {
//                    if (((currentDirection[indexJugador] == Const.UP || currentDirection[indexJugador] == Const.DOWN) && i == 0)
//                            || (currentDirection[indexJugador] == Const.LEFT || currentDirection[indexJugador] == Const.RIGHT) && j == 0) {
//                        //Do nothing
//                    } else {
//                        positionsToSend[indexJugador] += 1;
//
//                        if (indexJugador == 0) {
////                            pathx1.add(centrex1 + i);
////                            pathy1.add(centrey1 + j);
//                            path1.add(new Coord(jugador[indexJugador].getCentre().getX()+i,jugador[indexJugador].getCentre().getY()+j));
//                            System.out.println("Bomba en " + (jugador[indexJugador].getCentre().getX() + i) + ":" + (jugador[indexJugador].getCentre().getY() + j));
//                        } else if (indexJugador == 1) {
////                            pathx2.add(centrex2 + i);
////                            pathy2.add(centrey2 + j);
//                            path2.add(new Coord(jugador[indexJugador].getCentre().getX()+i,jugador[indexJugador].getCentre().getY()+j));
//                            System.out.println("Bomba en " + (jugador[indexJugador].getCentre().getX() + i) + ":" + (jugador[indexJugador].getCentre().getY() + j));
//                        }
//                    }
//                }
//            }
//
//            
//        } else if (tipusJugadors[indexJugador] == Const.JUMPER) {
//            positionsToSend[indexJugador] = 1;
//
//            switch (currentDirection[indexJugador]) {
//                case Const.UP:
////                        centrey1 -= Const.JUMP_LENGTH;
//                    jugador[indexJugador].getCentre().setY(jugador[indexJugador].getCentre().getY() - Const.JUMP_LENGTH);
//                    break;
//                case Const.DOWN:
////                        centrey1 += Const.JUMP_LENGTH;
//                    jugador[indexJugador].getCentre().setY(jugador[indexJugador].getCentre().getY() + Const.JUMP_LENGTH);
//                    break;
//                case Const.LEFT:
////                        centrex1 -= Const.JUMP_LENGTH;
//                    jugador[indexJugador].getCentre().setX(jugador[indexJugador].getCentre().getX() - Const.JUMP_LENGTH);
//                    break;
//                case Const.RIGHT:
////                        centrex1 += Const.JUMP_LENGTH;
//                    jugador[indexJugador].getCentre().setX(jugador[indexJugador].getCentre().getX() + Const.JUMP_LENGTH);
//                    break;
//            }
//
//                
//            
//        } else {
//            //Do nothing
//        }
//        //System.out.println("Finalitza accio especial");
//
//    }

    public void afegirVista(Vista_Client obsr) {
        v = obsr;
        addObserver(obsr);
    }

    protected void avisarObservadors(int accio) {
        setChanged();

        if (accio == Const.UPDATE_POSITION) {
            notifyObservers(getLastPositions(jugador[0].getPositionsToSend(), jugador[1].getPositionsToSend()));
        }
        if (accio == Const.ACABA_PARTIDA) {
            notifyObservers(Const.ACABA_PARTIDA);
        }

    }

    //updateJugador
    //Actualitza la direccio només en els casos permesos
    private void updateJugador(int direccioRebuda, int indexJugador) {

        Jugador j = jugador[indexJugador];
        //Tecles Premudes
        if (direccioRebuda == Const.NOACTION) {
            //No fem res
            j.setPositionsToSend(1);
        } else if (direccioRebuda == Const.UP && j.getCurrentDirection() != Const.DOWN) {
            j.setCurrentDirection(direccioRebuda);
            j.setPositionsToSend(1);
        } else if (direccioRebuda == Const.DOWN && j.getCurrentDirection() != Const.UP) {
            j.setCurrentDirection(direccioRebuda);
            j.setPositionsToSend(1);
        } else if (direccioRebuda == Const.RIGHT && j.getCurrentDirection() != Const.LEFT) {
            j.setCurrentDirection(direccioRebuda);
            j.setPositionsToSend(1);
        } else if (direccioRebuda == Const.LEFT && j.getCurrentDirection() != Const.RIGHT) {
            j.setCurrentDirection(direccioRebuda);
            j.setPositionsToSend(1);
        } else if (direccioRebuda == Const.SPACEBAR) {
            j.accioEspecial();
        } //Rebem el tipus de Jugador que volen ser
        else if (direccioRebuda == Const.BOMBER) {
            j.setTipusJugador(direccioRebuda);
            //System.out.println("El jugador " + indexJugador + " es de tipus BOMBER");
        } else if (direccioRebuda == Const.NORMAL) {
            j.setTipusJugador(direccioRebuda);
            //System.out.println("El jugador " + indexJugador + " es de tipus NORMAL");
        } else if (direccioRebuda == Const.JUMPER) {
            j.setTipusJugador(direccioRebuda);
            //System.out.println("El jugador " + indexJugador + " es de tipus JUMPER");
        }
    }

    private int[] getLastPositions(int positionsToSend1, int positionsToSend2) {
        int positionsToSend = Math.max(positionsToSend1, positionsToSend2);
        int[] positions = new int[positionsToSend * 4];

        for (int i = 0; i < positionsToSend; i++) {

            if (-positionsToSend1 + i < 0) {
                positions[i * 4] = jugador[0].getPath().get(jugador[0].getPath().size() - positionsToSend1 + i).getX();
                positions[i * 4 + 1] = jugador[0].getPath().get(jugador[0].getPath().size() - positionsToSend1 + i).getY();
            } else {
                positions[i * 4] = -1;
                positions[i * 4 + 1] = -1;
            }

            if (-positionsToSend2 + i < 0) {
                positions[i * 4 + 2] = jugador[1].getPath().get(jugador[1].getPath().size() - positionsToSend2 + i).getX();
                positions[i * 4 + 3] = jugador[1].getPath().get(jugador[1].getPath().size() - positionsToSend2 + i).getY();
            } else {
                positions[i * 4 + 2] = -1;
                positions[i * 4 + 3] = -1;
            }
        }
        return positions;
    }

    private void acabaPartida() {

        
//        centre[0] = new Coord(40, 40);
//        centre[1] = new Coord(600, 440);
//
//        //Direcció inicial
//        currentDirection = new int[2];
//        currentDirection[0] = Const.RIGHT;
//        currentDirection[1] = Const.LEFT;
//      
//        path1 = new ArrayList<Coord>();
//        path2 = new ArrayList<Coord>();

        jugador = new Jugador[Const.NUM_JUGADORS];
        
        for(int i = 0; i<Const.NUM_JUGADORS; i++){
            jugador[i] = new Jugador();
        }
        

        jugador[0].setCentre(new Coord(40,40));
        jugador[1].setCentre(new Coord(600,440));


        jugador[0].setCurrentDirection(Const.RIGHT);
        jugador[1].setCurrentDirection(Const.LEFT);

        avisarObservadors(Const.ACABA_PARTIDA);

    }

    //Setters
    public void setWindowSize(int windowWidth, int windowHeight) {
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
    }

//    public void setTipusJugadors(int tipusJugador, int index) {
//        tipusJugadors[index] = tipusJugador;
//    }

}
