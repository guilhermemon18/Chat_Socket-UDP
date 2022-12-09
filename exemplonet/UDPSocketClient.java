package exemplonet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalTime;

import server.Pacote;

public class UDPSocketClient {
	DatagramSocket Socket;

	public UDPSocketClient() {
		createAndListenSocket();
	}

	public void createAndListenSocket() {
		try {

			Socket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName("localhost");
			byte[] incomingData = new byte[1024];
			Pacote student = new Pacote(1,2,"Guilherme","Carolina","Olá",LocalTime.now());
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(outputStream);
			os.writeObject(student);


			byte[] data = outputStream.toByteArray();
			DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);
			Socket.send(sendPacket);
			System.out.println("Message sent from client");
			
			while(true) {
			DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
			Socket.receive(incomingPacket);
			String response = new String(incomingPacket.getData());
			System.out.println("Response from server:" + response);

			Thread.sleep(2000);
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		UDPSocketClient client = new UDPSocketClient();
		//client.createAndListenSocket();
	}
}