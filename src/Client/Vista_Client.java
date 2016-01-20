package Client;

import Utils.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Vista_Client implements Observer {

    Model_Client model;
    JFrame f;
    PanellPintar panelDibuix;
    PantallaPrincipal p;
    
    JLabel score1;
    JLabel score2;

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
        //Afegit
        JPanel taulerPuntuacions = new JPanel();
        taulerPuntuacions.setLayout(new FlowLayout());
        taulerPuntuacions.setPreferredSize(new Dimension(Const.SCREENX, 20));
         
         score1 = new JLabel(" ");
        score1.setPreferredSize(new Dimension(100, 10));
        taulerPuntuacions.add(score1);
        
        JLabel text = new JLabel("Best Score");
        text.setPreferredSize(new Dimension(140, 10));
        taulerPuntuacions.add(text);

       
        
         score2 = new JLabel(" ");
        score2.setPreferredSize(new Dimension(100, 10));
        taulerPuntuacions.add(score2);
        
        f.add(taulerPuntuacions, BorderLayout.SOUTH);
        
        f.setSize(Const.SCREENX, Const.SCREENY + 20);
        panelDibuix = new PanellPintar();
        f.add(panelDibuix, BorderLayout.CENTER);
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
    
    public void paintScores(int[] scores){
        //[!] s'ha d'executar al thread d'events!!!!!
        javax.swing.SwingUtilities.invokeLater(new TascaScores(scores));

    }
    private class TascaScores implements Runnable{
        protected int[] scores;
        public TascaScores(int[] score){
            scores = score;
        }
        public void run(){
            score1.setText(""+scores[0]);
            score2.setText(""+scores[1]);
            if (javax.swing.SwingUtilities.isEventDispatchThread()){
                System.out.println("M'executo al Thread d'events");
            }
            else {
                System.out.println("No m'executo al Thread d'events");
            }
        }
    }
    //--------------------------------------------
    //			Panell Pintar
    //--------------------------------------------
    class PanellPintar extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            //Pintar
            g.setColor(Color.BLACK); //Fica tot el fons a negre
            g.fillRect(0, 0, Const.SCREENX, Const.SCREENY);
            for (int i = 0; i < model.getPath1().size(); i++) {
                g.setColor(Color.green);
                g.fillRect(model.getPath1().get(i).getX(), model.getPath1().get(i).getY(), Const.PATHSIZE, Const.PATHSIZE);
            }

            for (int i = 0; i < model.getPath2().size(); i++) {
                g.setColor(Color.red);
                g.fillRect(model.getPath2().get(i).getX(), model.getPath2().get(i).getY(), Const.PATHSIZE, Const.PATHSIZE);
            }

        }

    }
    
    

}
