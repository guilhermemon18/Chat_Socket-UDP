package server;

import java.io.Serializable;
import java.net.InetAddress;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	private Integer port;
	private InetAddress IPAddress;
	
	public User(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	public User(Integer id, String nome, Integer port, InetAddress IPAddress) {
		this(id,nome);
		this.port = port;
		this.IPAddress = IPAddress;
	}
	
	public Integer getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	
	public Integer getPort() {
		return port;
	}

	@Override
	public String toString() {
		return  id + "-" + nome;
	}
	@Override
	public boolean equals(Object obj) {
		User u = (User) obj;
		return u != null && this.id.equals(u.id);
	}

	public InetAddress getIPAddress() {
		return IPAddress;
	}
	
	
}
