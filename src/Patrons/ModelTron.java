/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Patrons;

import Client.Vista_Client;
import Utils.Const;
import java.util.ArrayList;

/**
 *
 * @author marcassens
 */
public abstract class ModelTron extends ModelAbstracte{

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

        public ModelTron(){
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
        
        	public ArrayList<Integer> getPathX1(){ return pathx1; }
	public ArrayList<Integer> getPathY1(){ return pathy1; }
	public ArrayList<Integer> getPathX2(){ return pathx2; }
	public ArrayList<Integer> getPathY2(){ return pathy2; }
}


