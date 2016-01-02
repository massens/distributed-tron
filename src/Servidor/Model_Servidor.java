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
        
        //Coord centre[] = new Coord[2];

	//Direcció inicial
	int currentDirection[];
        int positionsToSend[];
	//Mides de la finestra
	int windowHeight;
	int windowWidth;

	//Quant avançen a cada "refrescada"
	ArrayList<Integer> pathx1;
	ArrayList<Integer> pathy1;
	ArrayList<Integer> pathx2;
	ArrayList<Integer> pathy2;
        
        //ArrayList<Coord> path1;
        //ArrayList<Coord> path2;

	Vista_Client v;

        int[] tipusJugadors = new int[2];
        

	public Model_Servidor(){
		//ConstRUIR VARS

		centrex1 = 40;
		centrey1 = 40;
		centrex2 = 600;
		centrey2 = 440;
                
                //centre[0] = new Coord(40,40);
                //centre[1] = new Coord(600,440);

		//Direcció inicial
                currentDirection = new int[2];
		currentDirection[0] = Const.RIGHT;
		currentDirection[1] = Const.LEFT;
                
                //Número de posicións que s'han d'enviar pel jugador 1 i 2
                positionsToSend = new int[2];

		//Quant avançen a cada "refrescada"
		pathx1 = new ArrayList<Integer>();
		pathy1 = new ArrayList<Integer>();
		pathx2 = new ArrayList<Integer>();
		pathy2 = new ArrayList<Integer>();
                
                //path1 = new ArrayList<Coord>();
                //path2 = new ArrayList<Coord>();


	}


	//Getters

	public ArrayList<Integer> getPathX1(){ return pathx1; }
	public ArrayList<Integer> getPathY1(){ return pathy1; }
	public ArrayList<Integer> getPathX2(){ return pathx2; }
	public ArrayList<Integer> getPathY2(){ return pathy2; }
        
        //public ArrayList<Coord> getPath1(){ return path1; }
	//public ArrayList<Coord> getPath2(){ return path2; }

        public int getTipusJugadors(int index) {
            return tipusJugadors[index];
        }
        
        


	public void update(int provisional_Direction1, int provisional_Direction2){
                updateJugador(provisional_Direction1, 0);
                updateJugador(provisional_Direction2, 1);  
                //System.out.println("Direccions Jugadors:" + currentDirection[0]+currentDirection[1]);
                dibuixaLineas();
                
//                avisarObservadors();
	}  
        
        
	//Funcions especials per tractar dades
	public void dibuixaLineas(){
            
                //System.out.println("Dibuixa Lineas amb direccions: "+ currentDirection[0] +" "+currentDirection[1]);
		
                //JUGADOR 1
		switch(currentDirection[0]){
		case Const.UP:
                    if (centrey1>0){    //if (centre[1].getY()>0){
			centrey1-=Const.STEP;
                        //centre[0].setY(centre[0].getY-Const.STEP);
                    } else {
			centrey1 = Const.SCREENY;
                        //centre[0].setY(Const.SCREENY);
                    }
                    break;
		case Const.RIGHT:
                    if (centrex1 < Const.SCREENX){
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


                //Afegeix les noves posicions
		pathx1.add(centrex1);
		pathy1.add(centrey1);
		pathx2.add(centrex2);
		pathy2.add(centrey2);
                
                //path1.add(centre[0]);
                //path2.add(centre[1]);

		//Avisa a la Vista_Client
		avisarObservadors(Const.UPDATE_POSITION);
                
		//CONDICIÓ DE COL·LISIÓ
                
	    for (int x = 0; x<pathx1.size()-1 ;x++){
	    	if (((centrex1 == pathx1.get(x)) && (centrey1 == pathy1.get(x))) ){
                    //1 ha xocat, guanya 2
                    System.out.println("Victoria de J2");
                    acabaPartida();
                } else if (((centrex2 == pathx1.get(x)) && (centrey2 == pathy1.get(x)))){
                    //2 ha xocat, guanya 1
                    System.out.println("Victoria de J1");
                    acabaPartida();
                }
            }
            for (int x=0;x<pathx2.size()-1;x++){
                
                if ((( centrex1 == pathx2.get(x)) && (centrey1 == pathy2.get(x)))){
                    //1 ha xocat, guanya 2
                    System.out.println("Victoria de J2");
                    acabaPartida();
                } else if((centrex2 == pathx2.get(x)) && (centrey2 == pathy2.get(x))){ 
                    //CAMBIAR PER FER "GAME OVER"
                    //2 ha xocat, guanya 1
                    System.out.println("Victoria de J1");
                    acabaPartida();
                    //System.exit(0);
	    	}
            }   
	}

        public void accioEspecial(int indexJugador) {
            //Això s'haurà de mirar, de moment peta el joc quan d'utilitza la bomba.
            positionsToSend[indexJugador] = 0;

            if (tipusJugadors[indexJugador] == Const.BOMBER) {
                for (int i = -Const.RADI_BOMBA; i<Const.RADI_BOMBA+1; i++){
                    for (int j= -Const.RADI_BOMBA; j<Const.RADI_BOMBA+1; j++){
                        if (((currentDirection[indexJugador] == Const.UP || currentDirection[indexJugador] == Const.DOWN ) && i == 0) || 
                                (currentDirection[indexJugador] == Const.LEFT || currentDirection[indexJugador] == Const.RIGHT ) && j == 0) {
                            //Do nothing
                        } else {
                            positionsToSend[indexJugador]+= 1;

                            if (indexJugador == 0){
                                pathx1.add(centrex1+i);
                                pathy1.add(centrey1+j);
                                System.out.println("Bomba en " + (centrex1+i) + ":" + (centrey1+j));
                            }
                            else if (indexJugador == 1){
                                pathx2.add(centrex2+i);
                                pathy2.add(centrey2+j);
                            }
                        }
                    }
                }
                
                
                //avisarObservadors(Const.UPDATE_POSITION);
            } else if (tipusJugadors[indexJugador] == Const.JUMPER) {
                positionsToSend[indexJugador] = 1;

                if (indexJugador == 0){
                    switch (currentDirection[indexJugador]){
                        case Const.UP:
                            centrey1-=Const.JUMP_LENGTH;
                            break;
                        case Const.DOWN:
                            centrey1+=Const.JUMP_LENGTH;
                            break;
                        case Const.LEFT:
                            centrex1-=Const.JUMP_LENGTH;
                            break;
                        case Const.RIGHT:
                            centrex1+=Const.JUMP_LENGTH;
                            break;
                    }
                } else if (indexJugador == 1){
                    switch (currentDirection[indexJugador]){
                        case Const.UP:
                            centrey2-=Const.JUMP_LENGTH;
                            break;
                        case Const.DOWN:
                            centrey2+=Const.JUMP_LENGTH;
                            break;
                        case Const.LEFT:
                            centrex2-=Const.JUMP_LENGTH;
                            break;
                        case Const.RIGHT:
                            centrex2+=Const.JUMP_LENGTH;
                            break;
                    }
                }
            } else {
                //Do nothing
            }
            //System.out.println("Finalitza accio especial");
            
        }

	public void afegirVista(Vista_Client obsr){
		v = obsr;
		addObserver(obsr);
	}

	protected void avisarObservadors(int accio){
		setChanged();
                
                if (accio == Const.UPDATE_POSITION) notifyObservers(getLastPositions(positionsToSend[0], positionsToSend[1]));
                if (accio == Const.ACABA_PARTIDA) notifyObservers(Const.ACABA_PARTIDA);

	}


        //updateJugador
        //Actualitza la direccio només en els casos permesos
        private void updateJugador(int direccioRebuda, int indexJugador){
            
            //Tecles Premudes
            if ( direccioRebuda == Const.NOACTION){
                //No fem res
                positionsToSend[indexJugador] = 1;
            } else if (direccioRebuda == Const.UP && currentDirection[indexJugador] != Const.DOWN){
                currentDirection[indexJugador] = direccioRebuda;
                positionsToSend[indexJugador] = 1;
            } else if (direccioRebuda == Const.DOWN && currentDirection[indexJugador] != Const.UP){
                currentDirection[indexJugador] = direccioRebuda;
                positionsToSend[indexJugador] = 1;
            } else if (direccioRebuda == Const.RIGHT && currentDirection[indexJugador] != Const.LEFT){
                currentDirection[indexJugador] = direccioRebuda;
                positionsToSend[indexJugador] = 1;
            } else if (direccioRebuda == Const.LEFT && currentDirection[indexJugador] != Const.RIGHT){
                currentDirection[indexJugador] = direccioRebuda;
                positionsToSend[indexJugador] = 1;
            } else if (direccioRebuda == Const.SPACEBAR){
                accioEspecial(indexJugador);
            } 
            //Rebem el tipus de Jugador que volen ser
            else if (direccioRebuda == Const.BOMBER){
                tipusJugadors[indexJugador]=direccioRebuda;
                System.out.println("El jugador " + indexJugador +" es de tipus BOMBER");
            } else if (direccioRebuda == Const.NORMAL){
                tipusJugadors[indexJugador]=direccioRebuda;
                System.out.println("El jugador " + indexJugador +" es de tipus NORMAL");
            } else if (direccioRebuda == Const.JUMPER){
                tipusJugadors[indexJugador]=direccioRebuda;
                System.out.println("El jugador " + indexJugador +" es de tipus JUMPER");
            }
        }
        
        private int[] getLastPositions(int positionsToSend1, int positionsToSend2){
            int positionsToSend = Math.max(positionsToSend1, positionsToSend2);
            int[] positions = new int[positionsToSend*4];
            
            for (int i = 0; i < positionsToSend; i++){
                
                if(-positionsToSend1+i < 0){
                    positions[i*4] = pathx1.get(pathx1.size()-positionsToSend1+i);
                    positions[i*4+1] = pathy1.get(pathy1.size()-positionsToSend1+i);
                }else{
                    positions[i*4] = -1;
                    positions[i*4+1] = -1;  
                }

                if(-positionsToSend2+i < 0){
                    positions[i*4+2] = pathx2.get(pathx2.size()-positionsToSend2+i);
                    positions[i*4+3] = pathy2.get(pathy2.size()-positionsToSend2+i);
                }else{
                    positions[i*4+2] = -1;
                    positions[i*4+3] = -1;
                }
            }
            return positions;
        }
        
        private void acabaPartida(){
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
                
                avisarObservadors(Const.ACABA_PARTIDA);

        }
        
        
                
        //Setters
	public void setWindowSize(int windowWidth, int windowHeight){
		this.windowHeight = windowHeight;
		this.windowWidth = windowWidth;
	}

        public void setTipusJugadors(int tipusJugador, int index) {
            tipusJugadors[index] = tipusJugador;
        }
        
        
}