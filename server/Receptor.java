package server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/*Para cada usuário cadastrado no chat da K19 deve ser criado um objeto da classe RECEPTOR.
A tarefa de um objeto da classe RECEPTOR é aguardar as mensagens enviadas pelo
usuário correspondente.*/
public class Receptor implements Runnable {

	/**
	 * 
	 */
	
	private DatagramSocket socket;
	private byte[] incomingData;
	private Distribuidor distribuidor;

	public Receptor(DatagramSocket socket, Distribuidor distribuidor) {
		this.distribuidor = distribuidor;
		this.socket = socket;
		this.incomingData = new byte[20048];
	}
	

	
	public void run() {
		while (true){
			
			
			Pacote mensagem = null;
			DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);

			try {
				socket.receive(incomingPacket);
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

