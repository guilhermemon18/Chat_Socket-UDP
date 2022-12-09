package server;

import java.io.IOException;
import java.net.DatagramSocket;

public class Server {

	private Distribuidor distribuidor;
	private Registrador registrador;
	//private DatagramSocket serverSocket;
	
	
	//Constrói o server para rodar.
	/*
	 * public Server() throws IOException { super(); distribuidor = new
	 * Distribuidor();
	 * 
	 * serverSocket = new ServerSocket(9876);
	 * 
	 * //O registrador ele já tem o serversocket lá nele,então dá para acessar por
	 * lá. registrador = new Registrador(distribuidor, serverSocket); Thread pilha =
	 * new Thread(registrador); pilha.start();
	 * System.out.println("Criou o server!");
	 * System.out.println("Servidor ouvindo a porta 10000"); }
	 */

	public Distribuidor getDistribuidor() {
		return distribuidor;
	}

	public Registrador getRegistrador() {
		return registrador;
	}


	public static void main(String[] args) throws IOException {
		//Distribuidor distribuidor = new Distribuidor();

		DatagramSocket serverSocket = new DatagramSocket(9876);

		//O registrador ele já tem o serversocket lá nele,então dá para acessar por lá.
		Registrador registrador = new Registrador(serverSocket);
		Thread pilha = new Thread(registrador);
		pilha.start();
		System.out.println("Criou o server!");
		System.out.println("Servidor ouvindo a porta 9876");
	}

	

}
