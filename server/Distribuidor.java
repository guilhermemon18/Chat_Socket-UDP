package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import server.Pacote.MessageType;

/*Na aplicação servidora, deve existir um objeto da classe DISTRIBUIDOR que tem como
tarefa receber as mensagens dos receptores e repassá-las para os emissores.*/
public class Distribuidor{


	private Collection<Emissor> emissores = new ArrayList<Emissor>();
	private List<User> users;
	
	public Distribuidor(List<User> users) {
		super();
		this.users = users;
	}

	public List<User> getUsers() {
		return users;
	}

	public void adicionaEmissor(Emissor emissor) {
		this.emissores.add(emissor);
	}
	
	private void removeEmissor(Emissor emissor) {
		this.emissores.remove(emissor);
	}
	
	public void distribuiMensagem(Object mensagem) throws IOException {
		//System.out.println(mensagem);
		
		
		Pacote msg = (Pacote) mensagem;
		
		if(msg.getTipo().equals(MessageType.DISCONNET)) {
			System.out.println("Entrou desconectar um cliente!");
			removeEmissor(new Emissor(null,msg.getIdOrigem()));
			this.users.remove(new User(msg.getIdOrigem(),null));
			List<User> teste = new LinkedList<User>(this.users);
			this.distribuiMensagem(new Pacote(teste));
			
		}
		
		for (Emissor emissor : this.emissores) {
			emissor.envia(mensagem );
		}
	}

	
}
