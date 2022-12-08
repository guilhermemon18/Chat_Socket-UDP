package server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.LocalTime;

public class UDPSocketServer {
	private DatagramSocket socket = null;

	public UDPSocketServer() {
		createAndListenSocket();
	}

	public void createAndListenSocket() {
		try {
			socket = new DatagramSocket(9876);
			byte[] incomingData = new byte[1024];

			while (true) {
				DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
				socket.receive(incomingPacket);
				byte[] data = incomingPacket.getData();
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(in);
				try {
					Pacote student = (Pacote) is.readObject();
					System.out.println("Student object received = "+student);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}


				//Ele está devolvendo para o carinha que entrou em contato com o servidor, uma MSG para ele, pode ser o PACOTE.
				InetAddress IPAddress = incomingPacket.getAddress();
				int port = incomingPacket.getPort();
				String reply = "Thank you for the message";
				byte[] replyBytea = reply.getBytes();
				//cria um pacote para enviar para quem pediu novamente.
//				int i = 0;
//				while(i < 5) {
//					DatagramPacket replyPacket =
//							new DatagramPacket(replyBytea, replyBytea.length, IPAddress, port);
//					socket.send(replyPacket);
//
//					i++;
//					Thread.sleep(2000);
//				}
				
				//debug
				Pacote student = new Pacote(1,2,"Guilherme","Carolina","Olá",LocalTime.now());
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(outputStream);
				os.writeObject(student);
				data = outputStream.toByteArray();
				int i = 0;
				while(i < 5) {
				DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, port);
				socket.send(sendPacket);
					i++;
					Thread.sleep(2000);
				}
				//debug
				
				
				
				//System.exit(0);
			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		UDPSocketServer server = new UDPSocketServer();
		//server.createAndListenSocket();
	}
}
