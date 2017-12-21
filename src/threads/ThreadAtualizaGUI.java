package threads;

import javax.swing.JLabel;

import controller.ControllerDadosServer;

public class ThreadAtualizaGUI extends Thread {
	private JLabel relogioLogico;
	private ControllerDadosServer controller = ControllerDadosServer.getInstance();
	
	
	public ThreadAtualizaGUI(JLabel relogioLogico){
		this.relogioLogico = relogioLogico;
	}
	
	
	public void run(){
		while(true){
			relogioLogico.setText(controller.getMeurelogio() + "");
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
