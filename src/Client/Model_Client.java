package Client;
import Patrons.ModelTron;
import Utils.*;

import java.util.ArrayList;

import java.util.*;

public class Model_Client extends Observable{

    
	int centrex1;
	int centrey1;
	int centrex2;
	int centrey2;

	//Direcció inicial
	int currentDirection1;
	int currentDirection2;

	//Quant avançen a cada "refrescada"
	ArrayList<Integer> pathx1;
	ArrayList<Integer> pathy1;
	ArrayList<Integer> pathx2;
	ArrayList<Integer> pathy2;

	Vista_Client v;


	public Model_Client(){
		//ConstRUIR VARS

		centrex1 = 40;
		centrey1 = 40;
		centrex2 = 600;
		centrey2 = 440;

		//Direcció inicial
		currentDirection1 = Const.RIGHT;
		currentDirection2 = Const.LEFT;


		//Quant avançen a cada "refrescada"
		pathx1 = new ArrayList<Integer>();
		pathy1 = new ArrayList<Integer>();
		pathx2 = new ArrayList<Integer>();
		pathy2 = new ArrayList<Integer>();


	}
        
        //Getters

	public ArrayList<Integer> getPathX1(){ return pathx1; }
	public ArrayList<Integer> getPathY1(){ return pathy1; }
	public ArrayList<Integer> getPathX2(){ return pathx2; }
	public ArrayList<Integer> getPathY2(){ return pathy2; }

	//Setters

	public void updateDireccio(int provisional_Direction1, int provisional_Direction2){
		if ( provisional_Direction1 != Const.NOACTION) currentDirection1 = provisional_Direction1;
		if ( provisional_Direction2 != Const.NOACTION) currentDirection2 = provisional_Direction2;
		dibuixaLineas();
	}

	//Funcions especials per tractar dades
	public void dibuixaLineas(){
                System.out.println("Dibuixa Lineas amb direccions: "+ currentDirection1 +" "+currentDirection2);
		//JUGADOR 1
                
		switch(currentDirection1){
		case Const.UP:
			if (centrey1>0){
			centrey1-=Const.STEP;
			} else {
				centrey1 = Const.SCREENX;
			}
			break;
		case Const.RIGHT:
			if (centrex1 < Const.SCREENY){
			centrex1+=Const.STEP;
			} else {
				centrex1 = 0;
			}
			break;
		case Const.DOWN:
			if (centrey1 < Const.SCREENY){
			centrey1+=Const.STEP;
			} else {
				centrey1 = 0;
			}
			break;
		case Const.LEFT:
			if (centrex1>0){
			centrex1-=Const.STEP;
			} else {
				centrex1 = Const.SCREENX;
			}
			break;
		}

		//JUGADOR 2
		switch(currentDirection2){
		case Const.UP:
			if (centrey2>0){
			centrey2-=Const.STEP;
			} else {
				centrey2 = Const.SCREENY;
			}
			break;
		case Const.RIGHT:
			if (centrex2 < Const.SCREENX){
			centrex2+=Const.STEP;
			} else {
				centrex2 = 0;
			}
			break;
		case Const.DOWN:
			if (centrey2 < Const.SCREENY){
				centrey2+=Const.STEP;
			} else {
				centrey2 = 0;
			}
			break;
		case Const.LEFT:
			if (centrex2>0){
			centrex2-=Const.STEP;
			} else {
				centrex2 = Const.SCREENX;
			}
			break;
		}


		//CONDICIÓ DE COL·LISIÓ
	    for (int x = 0; x<pathx1.size();x++){
	    	if (((centrex1 == pathx1.get(x)) && (centrey1 == pathy1.get(x))) || ((centrex2 == pathx2.get(x)) && (centrey2 == pathy2.get(x))) || ((centrex1 == pathx2.get(x)) && (centrey1 == pathy2.get(x))) || ((centrex2 == pathx1.get(x)) && (centrey2 == pathy1.get(x)))){
	    		

	    		//CAMBIAR PER FER "GAME OVER"
	    		System.exit(0);
	    	}
	    }

	    //Afegeix les noves posicions
		pathx1.add(centrex1);
		pathy1.add(centrey1);
		pathx2.add(centrex2);
		pathy2.add(centrey2);

//                System.out.println("Pathx1 : "+pathx1);
//                System.out.println("Pathx1 : "+pathy1);

		//Avisa a la Vista_Client
		avisarObservadors();

	}



	public void afegirVista(Vista_Client obsr){
		v = obsr;
		addObserver(obsr);
	}

	protected void avisarObservadors(){
		setChanged();
		notifyObservers();
	}
}