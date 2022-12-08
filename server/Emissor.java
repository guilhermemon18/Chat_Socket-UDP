package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/*
Para cada usuário cadastrado no chat da K19 deve ser criado um objeto da classe EMISSOR.
A tarefa de um objeto da classe EMISSOR é enviar as mensagens do chat para o usuário
correspondente.*/
public class Emissor{

	private InetAddress IPAddress;
	private DatagramSocket socket;
	private Integer id;
	
	public Emissor(DatagramSocket socket,InetAddress IPAdress, Integer id) {
		this.socket = socket;
		this.IPAddress = IPAdress;
		this.id = id;
	}
	
	public Emissor(InetAddress IPAdreess, Integer id) throws SocketException {
		this(new DatagramSocket(),IPAdreess,id);
	}
	
	public Integer getId() {
		return id;
	}

	public void envia(Object mensagem) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(outputStream);
		os.writeObject(mensagem);
		byte[] data = outputStream.toByteArray();
		DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);//olhar se tá certo isso,//pelo visto vai mandar pro server
		socket.send(sendPacket);
		Pacote m = (Pacote) mensagem;
		System.out.println("Message sent from client: " + m);
	}

	@Override
	public String toString() {
		return "Emissor [" + id + "]";
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Emissor m = (Emissor) obj;
		return this.id.equals(m.id);
	}




}
