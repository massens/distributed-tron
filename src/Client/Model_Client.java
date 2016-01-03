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

    public Model_Client() {

        //Quant avançen a cada "refrescada"
        pathx1 = new ArrayList<Integer>();
        pathy1 = new ArrayList<Integer>();
        pathx2 = new ArrayList<Integer>();
        pathy2 = new ArrayList<Integer>();
        

    }

    //Funcions especials per tractar dades
    public void dibuixaLineas(int[] posicions) {

        if (Arrays.equals(posicions, Const.finishCode)) {
            //Acaba la frame del joc
            System.out.println("CloseFrame()0");

            v.closeFrame();
            System.out.println("CloseFrame()1");
            return;
        }
        
        centrex1 = posicions[0];
        centrey1 = posicions[1];
        centrex2 = posicions[2];
        centrey2 = posicions[3];

        //Afegeix les noves posicions
        pathx1.add(centrex1);
        pathy1.add(centrey1);
        pathx2.add(centrex2);
        pathy2.add(centrey2);

        //Avisa a la Vista_Client
        avisarObservadors();

//        //CONDICIÓ DE COL·LISIÓ
//       
//        for (int x = 0; x < pathx1.size() - 1; x++) {
//            if (((centrex1 == pathx1.get(x)) && (centrey1 == pathy1.get(x)))
//                    || ((centrex2 == pathx2.get(x)) && (centrey2 == pathy2.get(x)))
//                    || ((centrex1 == pathx2.get(x)) && (centrey1 == pathy2.get(x)))
//                    || ((centrex2 == pathx1.get(x)) && (centrey2 == pathy1.get(x)))) {
//
//                //Acaba la frame del joc
//                 v.closeFrame();
//            }
//        }

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
}
