package Client;

import Utils.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import pantalla_principal.PantallaPrincipal;

public class Vista_Client implements Observer {

    Model_Client model;
    JFrame f;
    PanellPintar panelDibuix;
    PantallaPrincipal p;

    public Vista_Client(Model_Client model) {
        this.model = model;
        model.afegirVista(this);

        f = new JFrame("Tron");
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                f.dispose();     //S'hauria de tenir una vista al servidor per tractar allà el tancament de la finestra tmb (acabar joc)??
                p = new PantallaPrincipal();
                p.init();
            }
        });
        f.setSize(Const.SCREENX, Const.SCREENY);
        panelDibuix = new PanellPintar();
        f.add(panelDibuix);
        //f.pack();
        f.setVisible(true);

    }

    public void update(Observable ignorarObs, Object ignorarObj) { //Pull update

        f.repaint();

    }

    public void afegirControlador(KeyListener listener) { //Afegeix controladors
        f.addKeyListener(listener);
    }

    public void closeFrame() {
        f.dispose();
        p = new PantallaPrincipal();
        p.init();
        // terminar joc
    }

	//--------------------------------------------
    //				Panell Pintar
    //--------------------------------------------
    class PanellPintar extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            //Pintar
            g.setColor(Color.BLACK); //Fica tot el fons a negre
            g.fillRect(0, 0, Const.SCREENX, Const.SCREENY);
            for (int x = 0; x < model.getPathX1().size(); x++) {
                g.setColor(Color.green);
                g.fillRect(model.getPathX1().get(x), model.getPathY1().get(x), Const.PATHSIZE, Const.PATHSIZE);
                g.setColor(Color.red);
                g.fillRect(model.getPathX2().get(x), model.getPathY2().get(x), Const.PATHSIZE, Const.PATHSIZE);
            }
        }

    }

}
