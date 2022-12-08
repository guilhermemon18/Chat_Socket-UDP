package client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalTime;

import server.Pacote;

/*Na aplicação cliente, deve existir um objeto da classe EMISSORDEMENSAGEM que envia
as mensagens digitadas pelo usuário para a aplicação servidora.*/
public class EmissorDeMensagem {

	private InetAddress IPAddress;
	private DatagramSocket socket;
	
	public EmissorDeMensagem(DatagramSocket socket,InetAddress IPAdress ) {
		this.socket = socket;
		this.IPAddress = IPAdress;
	}

	public void envia(Object mensagem) throws IOException {
		
		
//		Pacote student = new Pacote(1,2,"Guilherme","Carolina","Olá",LocalTime.now());
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		ObjectOutputStream os = new ObjectOutputStream(outputStream);
//		os.writeObject(student);
//		byte[] data = outputStream.toByteArray();
//		DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);
//		socket.send(sendPacket);
//		System.out.println("Message sent from client");
		
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(outputStream);
		os.writeObject(mensagem);
		byte[] data = outputStream.toByteArray();
		DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);
		socket.send(sendPacket);
		System.out.println("Message sent from client: " + mensagem);
	}

}



