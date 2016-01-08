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

    Jugador jugador[];
    
    //Mides de la finestra
    int windowHeight;
    int windowWidth;
    
    private Score bestScore;
    private Score lastScore;
    
    Vista_Client v;

    public Model_Servidor() {
        //Construir vars
        lastScore = new Score(0,0);
        bestScore = new Score(0,0);
        jugador = new Jugador[Const.NUM_JUGADORS];
        
        for(int i=0; i<Const.NUM_JUGADORS; i++){
            jugador[i]=new Jugador();
        }
        
        //A partir d'aqui considerem que tenim només 2 jugadors.
        jugador[0].setCentre(new Coord(40,40));
        jugador[1].setCentre(new Coord(600,440));

        //Direcció inicial
        jugador[0].setCurrentDirection(Const.RIGHT);
        jugador[1].setCurrentDirection(Const.LEFT);

        

    }

    public void update(int provisional_Direction1, int provisional_Direction2) {
        updateJugador(provisional_Direction1, 0);
        updateJugador(provisional_Direction2, 1);
        dibuixaLineas();

    }

    //Funcions especials per tractar dades
    

    public void dibuixaLineas() {
        
        jugador[0].movimentJugador();
        jugador[1].movimentJugador();

        //Afegeix les noves posicions
        jugador[0].addCoordToPath(new Coord(jugador[0].getCentre()));
        jugador[1].addCoordToPath(new Coord(jugador[1].getCentre()));

        //Avisa a la Vista_Client
        avisarObservadors(Const.UPDATE_POSITION);
        
        //CONDICIÓ DE COL·LISIÓ per al path del Jugador 1
        for (int x = 0; x < jugador[0].getPath().size() - 1; x++) {
            if (((jugador[0].getCentre().equals(jugador[0].getPath().get(x))))) {
                //1 ha xocat, guanya 2
                System.out.println("Victoria de J2");
                lastScore.setScore(0,jugador[1].getPath().size());
                acabaPartida();
                
            } else if (jugador[1].getCentre().equals(jugador[0].getPath().get(x))) {
                //2 ha xocat, guanya 1
                System.out.println("Victoria de J1");
                lastScore.setScore(jugador[0].getPath().size(),0);
                acabaPartida();
            }
        }

        //CONDICIÓ DE COL·LISIÓ per al path del Jugador 2
        for (int x = 0; x < jugador[1].getPath().size() - 1; x++) {
            if (jugador[0].getCentre().equals(jugador[1].getPath().get(x))) {
                //1 ha xocat, guanya 2
                System.out.println("Victoria de J2");
                lastScore.setScore(0,jugador[1].getPath().size());
                acabaPartida();

            } else if (jugador[1].getCentre().equals(jugador[1].getPath().get(x))) {
                //2 ha xocat, guanya 1
                System.out.println("Victoria de J1");
                lastScore.setScore(jugador[0].getPath().size(),0);
                acabaPartida();
            }
        }
    }

    public void afegirVista(Vista_Client obsr) {
        v = obsr;
        addObserver(obsr);
    }

    protected void avisarObservadors(int accio) {
        setChanged();
        if (accio == Const.UPDATE_POSITION)  notifyObservers(getLastPositions(jugador[0].getPositionsToSend(), jugador[1].getPositionsToSend()));
        if (accio == Const.ACABA_PARTIDA)  notifyObservers(Const.ACABA_PARTIDA);
    }

    //UPDATEJUGADOR 
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
        }
        //Rebem el tipus de Jugador que volen ser
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

    //ÚLTIMES POSICIONS DELS JUGADORS
    //Normalment només s'ha d'enviar una coordenada per a cada jugador. En el cas
    //de que un jugador hagi enviat una bomba, s'hauràn d'enviar totes les coordenades 
    //de la bomba. Per enviar més coordenades d'un jugador que de l'altre, 
    //s'omplen coordenades amb -1, -1. Per indicar que no s'ha de tenir en compte
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
    
    public void setBestScore(int score1, int score2){
        bestScore.setScore(score1, score2);
    }
    
    //Getters

    public ArrayList<Coord> getPath1() {
        return jugador[0].getPath();
    }

    public ArrayList<Coord> getPath2() {
        return jugador[1].getPath();
    }
    
    public int[] getScore(){
        return lastScore.getScore();
    }
    
    public int[] getBestScore(){
        return bestScore.getScore();
    }

}
