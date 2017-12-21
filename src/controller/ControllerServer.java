package controller;

import java.net.ServerSocket;

import javax.swing.JTextArea;

import threads.ThreadServidorConexao;
import threads.ThreadServidorGUI;

public class ControllerServer {
	
	private ServerSocket server;
    private ThreadServidorConexao thread;
    private static ControllerServer unicaInstancia;
	
	
    /**
	 * Construtor
	 */
	private ControllerServer(){
		//controller.getInstance();
	}
	/**
	 * controla o instanciamento de objetos Controller
	 * @return unicaInstancia
	 */
	public static synchronized ControllerServer getInstance(){
		if(unicaInstancia==null){
			unicaInstancia = new ControllerServer();
		}
		return unicaInstancia;
	}

	/**
	 * reseta o objeto Controller ja instanciado
	 */
	public static void zerarSingleton (){
		unicaInstancia = null;
	}

	
	public String iniciaServer(int porta, JTextArea textArea){
        try {     
		System.out.println("Incializando o servidor...");
		textArea.setText(textArea.getText() + "Incializando o servidor... \n");

		server = new ServerSocket(porta);//instancia um socket server na porta desejada
		System.out.println("Servidor iniciado, ouvindo a porta " + porta);
		textArea.setText(textArea.getText() + "Servidor iniciado, ouvindo a porta " + porta);//indica que o servidor foi ligado em determinada porta

		ThreadServidorGUI threadGUI = new ThreadServidorGUI(thread, textArea, server);//thread que permite a atualiza��o periodica da GUI
		threadGUI.start();

	}catch(Exception e){
		e.printStackTrace();//exibe a exce��o que foi lan�ada
		textArea.setText(textArea.getText() + "Excecao ocorrida ao criar thread: " + e);//caso alguma exce��o desconheciada seja lan�ada ela encerra a thread e � exibida
	}

	return null;
    }

}
