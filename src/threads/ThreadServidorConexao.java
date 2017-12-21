package threads;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.regex.Pattern;

import javax.swing.JTextArea;

import controller.ControllerDadosServer;

public class ThreadServidorConexao extends Thread {

    private Socket cliente;//socket do cliente
    private ServerSocket server;//socket do servidor
    private JTextArea textField;//para atualizar a interface
    private ControllerDadosServer controller = ControllerDadosServer.getInstance();//instancia do controller
    
    public ThreadServidorConexao(ServerSocket server, JTextArea textField, Socket cliente) {//recebe o socket server e o textArea
        this.server = server;
        this.cliente = cliente;
        this.textField = textField;
    }
    
    public void run() {
        try {
            //Inicia thread do cliente aceitando clientes

            //ObjectInputStream para receber o nome do arquivo
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());//cria um objeto de entrada
            ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());//cria um objeto de saida
            String pack = (String) entrada.readObject();//obtem o pacote de entrada
            String informacoes[] = pack.split(Pattern.quote("|"));
            int opcao = Integer.parseInt(informacoes[0]);//recebe a opcao que o cliente mandou
            String s = "erro";//string de log com erro
            switch (opcao) {
                case 0://Cadastro de novo jogador
                    String server = informacoes[1];//recebe as informa��es para cadastro
                    String relogio = informacoes[2];
                    s = "Nova mensagem recebida! de: " + server;//string de log
                    controller.recebeuMsg(server, relogio);//controller cadastra cliente
                    
                    saida.flush();
                    break;
               
            }
            System.out.println("\nCliente atendido com sucesso: " + s + cliente.getRemoteSocketAddress().toString());
            textField.setText(textField.getText() + "\nCliente atendido com sucesso: " + s + cliente.getRemoteSocketAddress().toString());//coloca o log no textArea

            entrada.close();//finaliza a entrada
            saida.close();//finaliza a saida
            cliente.close();//fecha o cliente
        } catch (SocketException e) {
            System.out.println("Filanizou o atendimento.");
            textField.setText(textField.getText() + "\nAtendimento foi finalizado.");//caso alguma exce��o desconheciada seja lan�ada ela encerra a thread e � exibida
            try {
                cliente.close();   //finaliza o cliente
            } catch (Exception ec) {
                textField.setText(textField.getText() + "\nErro fatal cliente n�o finalizado: " + ec.getMessage());//cliente n�o foi finalizado
            }
        } catch (Exception e) {//caso alguma exce��o seja lan�ada
            e.printStackTrace();
            System.out.println("Excecao ocorrida na thread: " + e);
            textField.setText(textField.getText() + "\nExcecao ocorrida na thread: " + e.getMessage());//caso alguma exce��o desconheciada seja lan�ada ela encerra a thread e � exibida
            try {
                cliente.close();   //finaliza o cliente
            } catch (Exception ec) {
                textField.setText(textField.getText() + "\nErro fatal cliente n�o finalizado: " + ec.getMessage());//cliente n�o foi finalizado
            }
        }
    }

}
