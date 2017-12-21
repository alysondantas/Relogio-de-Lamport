package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ControllerDadosServer {

	private static ControllerDadosServer unicaInstancia;
	private int relogio1 = 0;
	private String ipServer1;
	private int portaServer1;
	private int relogio2 = 0;
	private String ipServer2;
	private int portaServer2;
	private int relogio3 = 0;
	private String ipServer3;
	private int portaServer3;
	private int meurelogio = 0;
	private int euSou = 1;


	private ControllerDadosServer() {

	}

	/**
	 * controla o instanciamento de objetos Controller
	 *
	 * @return unicaInstancia
	 */
	public static synchronized ControllerDadosServer getInstance() {
		if (unicaInstancia == null) {
			unicaInstancia = new ControllerDadosServer();
		}
		return unicaInstancia;
	}

	/**
	 * reseta o objeto Controller ja instanciado
	 */
	public static void zerarSingleton() {
		unicaInstancia = null;
	}

	public void setIpPorta(String ip1,int porta1, String ip2, int porta2, String ip3, int porta3){
		this.ipServer1 = ip1;
		this.ipServer2 = ip2;
		this.ipServer3 = ip3;
		this.portaServer1 = porta1;
		this.portaServer2 = porta2;
		this.portaServer3 = porta3;
	}

	public String enviaMsg(int server) throws UnknownHostException, IOException, ClassNotFoundException{
		meurelogio++;
		String pack = "0|" + euSou + "|" + meurelogio; // envia protocolo 1 para avisar que o cliente quer jogar
		int porta = portaServer2;
		String ip = ipServer2;
		switch (server) {
		case 1:
			porta = portaServer1;
			ip = ipServer1;
			break;

		case 2:
			porta = portaServer2;
			ip = ipServer2;
			break;

		case 3:
			porta = portaServer3;
			ip = ipServer3;
			break;
		}

		//Cria o Socket para buscar o arquivo no servidor 
		Socket rec = new Socket(ip,porta);

		//Enviando o nome do arquivo a ser baixado do servidor
		ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());
		saida.writeObject(pack);
		saida.flush();

		ObjectInputStream entrada = new ObjectInputStream(rec.getInputStream());//recebo o pacote do cliente
		String recebido = (String) entrada.readObject(); 
		saida.close();//fecha a comunicação com o servidor
		entrada.close();
		rec.close();
		
		return recebido;
	}

	public boolean recebeuMsg(String server, String relogio) {
		int relogioi = Integer.parseInt(relogio);
		max(relogioi);
		if(relogioi > meurelogio){
			return true;
		}else{
			return false;
		}

	}
	
	public void max(int recebido){
		if(recebido>meurelogio){
			meurelogio = recebido;
		}
		meurelogio++;
	}

	public void incrementaRelogio(){
		meurelogio++;
	}

	public int getRelogio1() {
		return relogio1;
	}

	public int getRelogio2() {
		return relogio2;
	}

	public int getMeurelogio() {
		return meurelogio;
	}

	public int getRelogio3() {
		return relogio3;
	}

	public int getEuSou() {
		return euSou;
	}

	public void setEuSou(int euSou) {
		this.euSou = euSou;
	}




}
