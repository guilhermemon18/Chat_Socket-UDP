package client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

import server.Pacote;
import server.Pacote.MessageType;
import server.User;
import view.Chat;

public class ReceptorDeMensagem implements Runnable {

	private DatagramSocket socket;
	private Chat telaChat;
	private byte[] incomingData;



	public ReceptorDeMensagem(DatagramSocket socket, Chat telaChat) {
		this.socket = socket;
		this.telaChat = telaChat;
		this.incomingData = new byte[20048];
	}


	public void run() {
		while (true ){//enquanto a entrada tiver mensagem ele obtem a msg e coloca na tela do chat, funciona bem para o chat global.
			
//			while(true) {
//				DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
//				try {
//					socket.receive(incomingPacket);
//					String response = new String(incomingPacket.getData());
//					System.out.println("Response from server:" + response);
//
//					Thread.sleep(2000);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					break;
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			
//				}
			
			Pacote mensagem = null;
			DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);

			try {
				socket.receive(incomingPacket);
				byte[] data = incomingPacket.getData();
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(in);
				
				mensagem = (Pacote) is.readObject();
				System.out.println("Message object received in Receptor de Mensagem= "+ mensagem);
				//mensagem = null;//debug
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}	
			//System.out.println("Recebencdo MSG " + mensagem);
			if(mensagem != null) {

				if(mensagem.getTipo().equals(MessageType.GETUSERS)) {
					System.out.println("Recebendo lista de usuuários conectados no receptor de msgs!");
					List<User> l = (List<User>) mensagem.getMessage();
					System.out.println("LIsta de usuários!");
					if(l != null)
						for (User user : l) {
							System.out.println(user);
						}
					telaChat.setUsers(l);
				}else {


					//tratamentos por meio de ifs para entregar a msg corretamente.
					if(mensagem.getTipo().equals(MessageType.ALLUSERS)) {
						//arqui vai ter que ser feito um tratamento de acordo com o objeto que vier ;
						String[] aux = mensagem.getMessage().toString().split(" "); 
						String newMessage = mensagem.getMessage().toString();
						if(mensagem.getIdOrigem().equals(telaChat.getId())) {
							newMessage = newMessage.replace(aux[0], "Você") + "\n"; 
						}
						else {
							newMessage = newMessage + "\n"; 
						}
						this.telaChat.adicionaMensagem(newMessage);
					}


					//				if(mensagem.getTipo().equals(MessageType.GETUSERS)) {
					//					telaChat.setUsers((List<User>) mensagem.getMessage());
					//					System.out.println("SEtando os usu[arios na tela!");
					//				}

					if(mensagem.getTipo().equals(MessageType.ID)) {
						System.out.println("Setando o ID do cliente: "+ mensagem.getMessage());
						System.out.println("Merda do Id: " + mensagem.getMessage().toString());
						telaChat.setId((Integer) mensagem.getMessage());
					}
					//conversa privada entre uma pessoa e outra.
					if(mensagem.getTipo().equals(MessageType.PRIVATE) && mensagem.getIdDestino().equals(this.telaChat.getId())) {

						telaChat.adicionaMSGPrivada(mensagem);
					}

					if(mensagem.getTipo().equals(MessageType.DISCONNET)) {
						this.telaChat.adicionaMensagem(mensagem.getMessage().toString() + "\n");
						this.telaChat.desconectaConversaPrivada(mensagem);
					}

				}
			}

		}
	}

}
