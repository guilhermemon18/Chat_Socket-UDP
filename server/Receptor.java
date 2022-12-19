package server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/*
Para o ServerSocket deve ser criado um objeto da classe RECEPTOR.
A tarefa de um objeto da classe RECEPTOR é aguardar as mensagens enviadas pelos
usuários.
*/
public class Receptor implements Runnable {

	private DatagramSocket serverSocket;
	private byte[] incomingData;
	private Distribuidor distribuidor;

	//Construtor
	public Receptor(DatagramSocket socket, Distribuidor distribuidor) {
		this.distribuidor = distribuidor;
		this.serverSocket = socket;
		this.incomingData = new byte[20048];
	}
	

	public void run() {
		while (true){
					
			Pacote mensagem = null;
			DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);

			try {
				serverSocket.receive(incomingPacket);
				byte[] data = incomingPacket.getData();
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(in);
				mensagem = (Pacote) is.readObject();
				this.distribuidor.distribuiMensagem(mensagem);
				System.out.println("Message object received = "+ mensagem);
				mensagem = null;//debug
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}	
		}
	}	
}

