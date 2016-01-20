package Client;


import Utils.Const;
import java.util.ArrayList;

import java.util.*;

public class Model_Client extends Observable {

    int centrex1;
    int centrey1;
    int centrex2;
    int centrey2;

    //Quant avançen a cada "refrescada"
    ArrayList<Integer> pathx1;
    ArrayList<Integer> pathy1;
    ArrayList<Integer> pathx2;
    ArrayList<Integer> pathy2;

    Vista_Client v;
    
    int[] bestScores;

    public Model_Client() {

        //Quant avançen a cada "refrescada"
        pathx1 = new ArrayList<Integer>();
        pathy1 = new ArrayList<Integer>();
        pathx2 = new ArrayList<Integer>();
        pathy2 = new ArrayList<Integer>();
        
        bestScores = new int[2];
        bestScores[0] = 0;
        bestScores[1] = 0;
        

    }

    //Funcions especials per tractar dades
    public void afegeixPosicio(int[] posicions) {

        if (Arrays.equals(posicions, Const.FINISH_CODE)) {
            //Acaba la frame del joc
            v.closeFrame();
            return;
        }
        
        centrex1 = posicions[0];
        centrey1 = posicions[1];
        centrex2 = posicions[2];
        centrey2 = posicions[3];

        //Afegeix les noves posicions
        if (centrex1 != -1) pathx1.add(centrex1);
        if (centrey1 != -1) pathy1.add(centrey1);
        if (centrex2 != -1) pathx2.add(centrex2);
        if (centrey2 != -1) pathy2.add(centrey2);

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
    public ArrayList<Integer> getPathX1() {
        return pathx1;
    }

    public ArrayList<Integer> getPathY1() {
        return pathy1;
    }

    public ArrayList<Integer> getPathX2() {
        return pathx2;
    }

    public ArrayList<Integer> getPathY2() {
        return pathy2;
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
