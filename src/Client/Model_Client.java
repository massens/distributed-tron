package Client;


import Utils.Const;
import Utils.Coord;
import Utils.Jugador;
import java.util.ArrayList;

import java.util.*;

public class Model_Client extends Observable {


    Jugador jugadors[];

    Vista_Client v;
    
    int[] bestScores;

    public Model_Client() {

        //Quant avançen a cada "refrescada"

        
        jugadors = new Jugador[2];
        jugadors[0] = new Jugador();
        jugadors[1] = new Jugador();
        
        jugadors[0].setCentre(new Coord());
        jugadors[1].setCentre(new Coord());
        
        bestScores = new int[2];
        bestScores[0] = 0;
        bestScores[1] = 0;
        

    }

    //Funcions especials per tractar dades
    public void avancaPosicio(int[] posicions) {

        if (Arrays.equals(posicions, Const.FINISH_CODE)) {
            //Acaba la frame del joc
            v.closeFrame();
            return;
        }
        
        jugadors[0].getCentre().setX(posicions[0]);
        jugadors[0].getCentre().setY(posicions[1]);
        jugadors[1].getCentre().setX(posicions[2]);
        jugadors[1].getCentre().setY(posicions[3]);

        //Afegeix les noves posicions
        if ((jugadors[0].getCentre().getX() != -1) || (jugadors[0].getCentre().getY() != -1)){
            jugadors[0].addCoordToPath(new Coord(jugadors[0].getCentre()));
        }
        if ((jugadors[1].getCentre().getX() != -1) || (jugadors[1].getCentre().getY() != -1)){
            jugadors[1].addCoordToPath(new Coord(jugadors[1].getCentre()));
        }
        //Avisa a la Vista_Client
        avisarObservadors();

    }

    public void afegirVista(Vista_Client obsr) {
        v = obsr;
        addObserver(obsr);
    }

    protected void avisarObservadors() {
        setChanged();
        notifyObservers();
    }

    //Getters
    public ArrayList<Coord> getPath1() {
        return jugadors[0].getPath();
    }


    public ArrayList<Coord> getPath2() {
        return jugadors[1].getPath();
    }


    
    public void setScores(int score1, int score2){
        bestScores[0] = score1;
        bestScores[1] = score2;
        
        //DECISIÓ DE DISENY
        //Decidim no fer avisarObservadors() ja que la actualizació de
        //les puntuacions es fa una sola vegada al inici del joc
        //Per millorar la efiència, no obliguem a la vista a comprovar que tingui
        //les puntuacions cada cop que es crida update()
        v.paintScores(bestScores);
    }
}
