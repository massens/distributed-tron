package Client;

import Client.Client;
import Client.ClientStream;
import Utils.Const;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


/**
 *
 * @author Álvaro
 */
public class PantallaPrincipal {

    protected JFrame principal;
    protected JPanel panell_fons;
    protected JButton play, exit;
    protected ImageIcon img_play, img_exit, img_state1,img_state2;
    protected JCheckBox option1, option2, option3;
    protected ButtonGroup grup;
    protected String online,offline;
    protected JLabel labelCheck, label_player1, label_player2, label_state1,label_state2;

    public void init() {

        principal = new JFrame();
        //Size del fons de pantalla
        principal.setSize(971, 700);
        //Definim Layout
        principal.setLayout(new BorderLayout());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //Localització de la finestra
        principal.setLocation(dim.width / 2 - principal.getSize().width / 2, dim.height / 2 - principal.getSize().height / 2);

        panellFons();
        principal.add(panell_fons, BorderLayout.CENTER);

        //No es pot fer més gran
        principal.setResizable(false);
        principal.setVisible(true);
        principal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        principal.add(panell_fons);

    }

    public void panellFons() {

        panell_fons = new CustomPanel();
        panell_fons.setLayout(null);

        img_play = new ImageIcon(getClass().getResource("/Recursos/PantallaPrincipal_PlayButton.png"));
      //  play_retocada = new ImageIcon(img_play.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
        play = new JButton("Play", img_play);
        play.addActionListener(new ControladorInici());

        //Buttons
        play.setBounds(400, 270, 200, 70);
        play.setFont(new Font("Helvetica", 1, 20));
        play.setHorizontalTextPosition(SwingConstants.CENTER);
        play.setVerticalTextPosition(SwingConstants.CENTER);
        play.setForeground(Color.BLACK);

        img_exit = new ImageIcon(getClass().getResource("/Recursos/PantallaPrincipal_ExitButton.png"));
       // exit_retocada = new ImageIcon(img_exit.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
        exit = new JButton("Exit", img_exit);
        exit.addActionListener(new ControladorSortir());

        exit.setHorizontalTextPosition(SwingConstants.CENTER);
        exit.setVerticalTextPosition(SwingConstants.CENTER);
        exit.setFont(new Font("Helvetica Neue Regular", 1, 20));
        exit.setBounds(860, 620, 90, 40);
        exit.setForeground(Color.BLACK);

        //CheckBoxes
        option1 = new JCheckBox("Bomber", false);
        //Retoquem
        option1.setBounds(370, 380, 90, 90);
        option1.setOpaque(false);
        option1.setForeground(Color.WHITE);
        option1.setFont(new Font("Helvetica Neue Regular", 1, 14));

        option2 = new JCheckBox("Normal", true);
        //Retoquem
        option2.setBounds(470, 380, 90, 90);
        option2.setOpaque(false);
        option2.setForeground(Color.WHITE);
        option2.setFont(new Font("Helvetica Neue Regular", 1, 14));

        option3 = new JCheckBox("Jumper", false);
        //Retoquem
        option3.setBounds(570, 380, 90, 90);
        option3.setOpaque(false);
        option3.setForeground(Color.WHITE);
        option3.setFont(new Font("Helvetica Neue Regular", 1, 14));

        //Controlem que nomes 1 boto estigui apretat
        grup = new ButtonGroup();

        grup.add(option1);
        grup.add(option2);
        grup.add(option3);
        
        //Aixo s'ha de canviar, ara mateix no funciona be
        
        // Label seleccio checkbox
        labelCheck = new JLabel("Select your player style!", Label.LEFT);
        labelCheck.setBounds(390, 290, 245, 220);
        labelCheck.setFont(new Font("Helvetica Neue Regular", 3, 18));
        labelCheck.setForeground(Color.RED);

        
        

        
        panell_fons.add(play);
        panell_fons.add(exit);
        panell_fons.add(option1);
        panell_fons.add(option2);
        panell_fons.add(option3);
        panell_fons.add(labelCheck);
        

    }

    class ControladorInici implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                //Inicia Joc
                Client c = new Client();
                ClientStream cc = c.getClientStream();
                //Obté el tipus de jugador seleccionat
                int tipus_jugador;

                if (option1.isSelected()) {
                    // s'ha seleccionat x cosa
                    tipus_jugador = Const.BOMBER;
                } else if (option3.isSelected()) {
                    tipus_jugador = Const.JUMPER;
                } else {
                    tipus_jugador = Const.NORMAL;
                }
                //Enviem el tipus selecconat al servidor
                cc.enviar(tipus_jugador);
                
                //amaga el menu
                principal.setVisible(false);
            } catch (IOException ex) {
                Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    class ControladorSortir implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.exit(0);
        }

    }


    class CustomPanel extends JPanel {

        protected URL dir_fons = principal.getClass().getResource("/Recursos/PantallaPrincipal_Fons.png");
        protected Image fons = new ImageIcon(dir_fons).getImage();

        @Override
        public void paint(Graphics g) {
            g.drawImage(fons, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }
    

    public static void main(String[] args) {
        PantallaPrincipal menu = new PantallaPrincipal();
        menu.init();
    }

}
