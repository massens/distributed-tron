package Client;
import Utils.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;


public class Controlador_Client implements KeyListener{

	protected ClientStream comunicacions;
	protected Vista_Client vista;
	protected Model_Client model;
	protected int provisional_Direction1;

	public Controlador_Client(Vista_Client vista, Model_Client model, ClientStream comunicacions){
		this.vista = vista;
		this.model = model;
		this.comunicacions = comunicacions;

		vista.afegirControlador(this);

		provisional_Direction1 = Const.NOACTION;
	}


	public void keyPressed(KeyEvent e) {
            
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			provisional_Direction1 = Const.UP;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				provisional_Direction1 = Const.DOWN;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				provisional_Direction1 = Const.RIGHT;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				provisional_Direction1 = Const.LEFT;
		}
                comunicacions.enviar(provisional_Direction1);

	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent arg0) {}

}