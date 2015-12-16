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
	protected int provisional_Direction2;

	protected Timer t;



	public Controlador_Client(Vista_Client vista, Model_Client model, ClientStream comunicacions){
		this.vista = vista;
		this.model = model;
		this.comunicacions = comunicacions;

		//Afegim controlador a vista???
		//Perill, acabar de veure
		vista.afegirControlador(this);


		provisional_Direction1 = Const.NOACTION;
		provisional_Direction2 = Const.NOACTION;

		//Funcio Update
		t = new Timer();

	}

	public void inici(){
		t.scheduleAtFixedRate(new UpdateTasca(), 2000, 20);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (provisional_Direction1 != Const.DOWN){
			provisional_Direction1 = Const.UP;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (provisional_Direction1 != Const.UP){
				provisional_Direction1 = Const.DOWN;
				}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (provisional_Direction1 != Const.LEFT){
				provisional_Direction1 = Const.RIGHT;
				}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (provisional_Direction1 != Const.RIGHT){
				provisional_Direction1 = Const.LEFT;
				}
		}
		if (e.getKeyCode() == KeyEvent.VK_W){
			if (provisional_Direction2 != Const.DOWN){
			provisional_Direction2 = Const.UP;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			if (provisional_Direction2 != Const.UP){
				provisional_Direction2 = Const.DOWN;
				}
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			if (provisional_Direction2 != Const.LEFT){
				provisional_Direction2 = Const.RIGHT;
				}
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			if (provisional_Direction2 != Const.RIGHT){
				provisional_Direction2 = Const.LEFT;
				}
		}
	}

	public void keyReleased(KeyEvent e) {}

	class UpdateTasca extends TimerTask{
		public void run(){

			//Aquest Update ha de ser el que es
			comunicacions.enviar(provisional_Direction1,provisional_Direction2);
			// model.updateDireccio(provisional_Direction1, provisional_Direction2);


			provisional_Direction1 = Const.NOACTION;
			provisional_Direction2 = Const.NOACTION;
		}
	}

	public void keyTyped(KeyEvent arg0) {}

}