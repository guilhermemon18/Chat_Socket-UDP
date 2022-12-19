package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

import server.Pacote.MessageType;

/*
Na aplicação servidora, deve existir um objeto da classe DISTRIBUIDOR que tem como
tarefa receber as mensagens dos receptores e repassá-las para os users.
*/
public class Distribuidor{

	private DatagramSocket serverSocket;
	private List<User> users;
	
	//Construtor
	public Distribuidor(List<User> users) {
		super();
		this.users = users;
	}
	
	// Lista de usuarios
	public Distribuidor(List<User> users, DatagramSocket serverSocket) {
		this(users);
		this.serverSocket = serverSocket;
	}

	// Remove clientes
	private void  removeUser(User u) {
		this.users.remove(u);
	}

	// Distribui mensagem
	public void distribuiMensagem(Object mensagem) throws IOException {
		Pacote msg = (Pacote) mensagem;
		
		//Se pedir para desconectar, então remove da lista de usuários e manda a nova lista de clientes conectados para todos os users.
		if(msg.getTipo().equals(MessageType.DISCONNET)) {
			System.out.println("Entrou desconectar um cliente!");
			removeUser(new User(msg.getIdOrigem(),null));
			List<User> teste = new LinkedList<User>(this.users);
			this.distribuiMensagem(new Pacote(teste));
		}
		

		//Compartilha a msg para todos os usuários conectados!
		for (User user : users) {
			byte[] data;
			InetAddress IPAddress = user.getIPAddress();
			int port = user.getPort();
			Pacote id = (Pacote) mensagem;
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(outputStream);
			os.writeObject(id);
			data = outputStream.toByteArray();
			DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, port);
			serverSocket.send(sendPacket);
		}
	}

	
}
