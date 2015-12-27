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

public class Model_Servidor extends Observable{
	
	int centrex1;
	int centrey1;
	int centrex2;
	int centrey2;

	//Direcció inicial
	int currentDirection[];

	//Mides de la finestra
	int windowHeight;
	int windowWidth;

	//Quant avançen a cada "refrescada"
	ArrayList<Integer> pathx1;
	ArrayList<Integer> pathy1;
	ArrayList<Integer> pathx2;
	ArrayList<Integer> pathy2;

	Vista_Client v;


	public Model_Servidor(){
		//ConstRUIR VARS

		centrex1 = 40;
		centrey1 = 40;
		centrex2 = 600;
		centrey2 = 440;

		//Direcció inicial
                currentDirection = new int[2];
		currentDirection[0] = Const.RIGHT;
		currentDirection[1] = Const.LEFT;


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
                updateDireccioJugador(provisional_Direction1, 0);
                updateDireccioJugador(provisional_Direction2, 1);  
                System.out.println("Direccions Jugadors:" + currentDirection[0]+currentDirection[1]);
                dibuixaLineas();
                
//                avisarObservadors();
	}

	public void setWindowSize(int windowWidth, int windowHeight){
		this.windowHeight = windowHeight;
		this.windowWidth = windowWidth;
	}

	//Funcions especials per tractar dades
	public void dibuixaLineas(){
            
                System.out.println("Dibuixa Lineas amb direccions: "+ currentDirection[0] +" "+currentDirection[1]);
		//JUGADOR 1
                
		 
		switch(currentDirection[0]){
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
		switch(currentDirection[1]){
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
                
                System.out.println("Pathx1 : "+pathx1);
                System.out.println("Pathy1 : "+pathy1);

		//CONDICIÓ DE COL·LISIÓ
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



		//Avisa a la Vista_Client
		avisarObservadors();

	}



	public void afegirVista(Vista_Client obsr){
		v = obsr;
		addObserver(obsr);
	}

	protected void avisarObservadors(){
		setChanged();
		notifyObservers(getLastPosition());
	}


        //updateDireccioJugador
        //Actualitza la direccio només en els casos permesos
        private void updateDireccioJugador(int direccioRebuda, int indexJugador){
            if ( direccioRebuda == Const.NOACTION){
                //No fem res
            } else if (direccioRebuda == Const.UP && currentDirection[indexJugador] != Const.DOWN){
                currentDirection[indexJugador] = direccioRebuda;
            } else if (direccioRebuda == Const.DOWN && currentDirection[indexJugador] != Const.UP){
                currentDirection[indexJugador] = direccioRebuda;
            } else if (direccioRebuda == Const.RIGHT && currentDirection[indexJugador] != Const.LEFT){
                currentDirection[indexJugador] = direccioRebuda;
            } else if (direccioRebuda == Const.LEFT && currentDirection[indexJugador] != Const.RIGHT){
                currentDirection[indexJugador] = direccioRebuda;
            }
        }
        
        private int[] getLastPosition(){
            int[] lastPosition = new int[4];
            lastPosition[0] = pathx1.get(pathx1.size()-1);
            lastPosition[1] = pathy1.get(pathy1.size()-1);
            lastPosition[2] = pathx2.get(pathx2.size()-1);
            lastPosition[3] = pathy2.get(pathy2.size()-1);
            return lastPosition;
        }
        
        
}