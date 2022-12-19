package client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import server.Pacote;
import view.Chat;

public class Client {

	//Componentes do cliente:
	private DatagramSocket  socket;		// aqui está a conexão que deve ser encerrada ao terminar o chat.
	private InetAddress IPAddress;
	private EmissorDeMensagem emissor;	//emissor de msg
	private Chat telaChat;				//tela para visualizar as ações
	private ReceptorDeMensagem receptor;//receptor para receber as informações e setar na tela.
	private Thread pilha;				//a thread para que flua.
	private JTextField txtNome;

	//construtor do cliente.
	public Client(String nomeCliente) throws UnknownHostException, IOException {
		socket = new DatagramSocket();
		IPAddress = InetAddress.getByName("localhost");
		emissor = new EmissorDeMensagem(socket,IPAddress);
		telaChat = new Chat(emissor,nomeCliente, this);
		emissor.envia(new Pacote(nomeCliente));
		receptor = new ReceptorDeMensagem(socket,telaChat);
		pilha = new Thread(receptor);
		pilha.start();
	}

	//construtor do cliente.
	public Client() throws UnknownHostException, IOException {
		JLabel lblMessage = new JLabel("Criando Cliente!");
		IPAddress = InetAddress.getByName("localhost");
		JLabel lblinserirnome = new JLabel("Insira o nome!");
		txtNome = new JTextField();
		Object[] texts = {lblMessage, lblinserirnome, txtNome };
		JOptionPane.showMessageDialog(null, texts);
		socket = new DatagramSocket();
		emissor = new EmissorDeMensagem(socket,IPAddress);
		emissor.envia(new Pacote(txtNome.getText()));
		telaChat = new Chat(emissor,txtNome.getText(), this);
		receptor = new ReceptorDeMensagem(socket,telaChat);
		pilha = new Thread(receptor);
		pilha.start();

	}

	public void fecharChat() throws IOException {
		socket.close();
	}

	public DatagramSocket getSocket() {
		return socket;
	}

	public static void main(String[] args)  {
		try {
			//Novos clientes
			new Client("Guilherme");
			new Client("Gisele");

			System.out.println("Creating the client");
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




	}
}
