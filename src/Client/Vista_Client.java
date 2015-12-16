package Client;

import Utils.ScreenManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Vista_Client implements Observer{
	Model_Client model;

	private static final DisplayMode modes[] = 
		{
		//new DisplayMode(1920,1080,32,0),
		new DisplayMode(1680,1050,32,0),
		//new DisplayMode(1280,1024,32,0),
		new DisplayMode(800,600,32,0),
		new DisplayMode(800,600,24,0),
		new DisplayMode(800,600,16,0),
		new DisplayMode(640,480,32,0),
		new DisplayMode(640,480,24,0),
		new DisplayMode(640,480,16,0),
		};

	public ScreenManager sm;
	protected Window w;


	public Vista_Client(Model_Client model){
		this.model = model;
		model.afegirVista(this);

		sm = new ScreenManager();
		DisplayMode dm = sm.findFirstCompatibaleMode(modes);
		sm.setFullScreen(dm);
		w = sm.getFullScreenWindow();
		w.setFont(new Font("Arial",Font.PLAIN,20));
		w.setBackground(Color.WHITE);
		w.setForeground(Color.RED);

		model.setWindowSize(sm.getWidth(), sm.getHeight());

	}

	public void update(Observable ignorarObs, Object ignorarObj){ //Pull update
		
        System.out.println("Vista Update!");
		Graphics2D g = sm.getGraphics();


		//Pintar
		g.setColor(Color.BLACK); //Fica tot el fons a negre
		g.fillRect(0, 0, sm.getWidth(), sm.getHeight());
		for (int x = 0;x<model.getPathX1().size();x++){
			g.setColor(Color.green);
			g.fillRect(model.getPathX1().get(x), model.getPathY1().get(x), 10, 10);
			g.setColor(Color.red);
			g.fillRect(model.getPathX2().get(x), model.getPathY2().get(x), 10, 10);
		}

		sm.update();

	}


	public void afegirControlador(KeyListener listener) { //Afegeix controladors
		w.addKeyListener(listener);
	}
	
}