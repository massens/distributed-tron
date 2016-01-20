/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.ArrayList;

/**
 *
 * @author Guillem
 */
public class Jugador {
    
    //Cami recorregut pel jugador
    protected ArrayList<Coord> path;
    
    //Direcció actual del jugador
    protected int currentDirection;
    
    //Centre actual del jugador
    protected Coord centre;
    
    //Número de posicións que s'han d'enviar pel jugador 1 i 2
    protected int positionsToSend;
    
    //Patró estrategia: classe que defineix l'acció que realitzarà el jugador quan premi la barra espaciadora
    protected AccioEspecial accio;
    protected int tipusJugador;
    
    
    
    public Jugador(){
        path = new ArrayList<Coord>();
        
        //Per defecte, el tipus de Jugador és NORMAL. El client ho pot canviar
        setTipusJugador(Const.NORMAL);
    }

    public ArrayList<Coord> getPath() {
        return path;
    }

    public int getCurrentDirection() {
        return currentDirection;
    }

    public Coord getCentre() {
        return centre;
    }

    public int getTipusJugador() {
        return tipusJugador;
    }

    public int getPositionsToSend() {
        return positionsToSend;
    }

    public AccioEspecial getAccio() {
        return accio;
    }

    public void setCurrentDirection(int currentDirection) {
        this.currentDirection = currentDirection;
    }

    public void setCentre(Coord centre) {
        this.centre = centre;
    }

    public void setTipusJugador(int tipusJugador) {
        this.tipusJugador = tipusJugador;
        if (tipusJugador == Const.BOMBER) {
            setAccio(new AccioBomber());
        } else if (tipusJugador == Const.JUMPER) {
            setAccio(new AccioJumper());
        } else if (tipusJugador == Const.NORMAL) {
            setAccio(new AccioNormal());
        }
    }

    public void setPositionsToSend(int positionsToSend) {
        this.positionsToSend = positionsToSend;
    }

    public void setAccio(AccioEspecial accio) {
        this.accio = accio;
    }
    
    public void incPositionsToSend(int valor){
        positionsToSend+=valor;
    }
    
    public void addCoordToPath(Coord c){
        path.add(c);
    }
    
    public void movimentJugador(){
        switch (currentDirection){                
            case Const.UP:
                if (centre.getY() > 0) {                
                    centre.incY(-Const.STEP);
                } else {
                    centre.setY(Const.SCREENY);
                }
                break;
            case Const.RIGHT:
                if (centre.getX() < Const.SCREENX) {
                    centre.incX(Const.STEP);
                } else {
                    centre.setX(0);
                }
                break;
            case Const.DOWN:
                if (centre.getY() < Const.SCREENY) {
                    centre.incY(Const.STEP);
                } else {
                    centre.setY(0);
                }
                break;
            case Const.LEFT:
                if (centre.getX() > 0) {                
                    centre.incX(-Const.STEP);
                } else {
                    centre.setX(Const.SCREENX);
                }
                break;
        }
        
    }
    public void accioEspecial(){
        accio.accio(this);
    }
}
