/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Patrons;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author marcassens
 */
public abstract class ModelAbstracte extends Observable {

    public void afegirObservador(Observer obsr) {
        addObserver(obsr);
    }

    protected void avisarObservadors() {
        setChanged();
        notifyObservers();
    }

}