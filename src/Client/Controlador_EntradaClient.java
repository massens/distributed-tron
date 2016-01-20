package Client;
import Utils.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;


public class Controlador_EntradaClient implements KeyListener{

	protected ClientStream comunicacions;
	protected Vista_Client vista;
	protected Model_Client model;
	protected int provisional_key;

	public Controlador_EntradaClient(Vista_Client vista, Model_Client model, ClientStream comunicacions){
		this.vista = vista;
		this.model = model;
		this.comunicacions = comunicacions;

		vista.afegirControlador(this);

		provisional_key = Const.NOACTION;
	}


	public void keyPressed(KeyEvent e) {
            
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			provisional_key = Const.UP;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
				provisional_key = Const.DOWN;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
				provisional_key = Const.RIGHT;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
				provisional_key = Const.LEFT;
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                                provisional_key = Const.SPACEBAR;
                }
                comunicacions.enviar(provisional_key);

	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent arg0) {}

}