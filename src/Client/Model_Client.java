package Client;


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
    public int dibuixaLineas(int[] posicions) {

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

        //CONDICIÓ DE COL·LISIÓ
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
        
            for (int x = 0; x<pathx1.size()-1 ;x++){
	    	if (((centrex1 == pathx1.get(x)) && (centrey1 == pathy1.get(x))) ){
                    //1 ha xocat, guanya 2
                    System.out.println("Victoria de J2");
                    v.closeFrame();
                    return 0;

                } else if (((centrex2 == pathx1.get(x)) && (centrey2 == pathy1.get(x)))){
                    //2 ha xocat, guanya 1
                    System.out.println("Victoria de J1");
                    v.closeFrame();
                    return 0;

                }
            }
            for (int x=0;x<pathx2.size()-1;x++){
                
                if ((( centrex1 == pathx2.get(x)) && (centrey1 == pathy2.get(x)))){
                    //1 ha xocat, guanya 2
                    System.out.println("Victoria de J2");
                    v.closeFrame();
                    return 0;

                } else if((centrex2 == pathx2.get(x)) && (centrey2 == pathy2.get(x))){ 
                    //CAMBIAR PER FER "GAME OVER"
                    //2 ha xocat, guanya 1
                    System.out.println("Victoria de J1");
                    v.closeFrame();
                    return 0;
                    //System.exit(0);
	    	}
            } 
        return 1;
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
