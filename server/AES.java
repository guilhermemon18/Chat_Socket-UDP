package server;


import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

/*
Obs: Classe AES para cirptografar e descriptografar uma mensagem.
AES usam chave simetrica, ou seja, uma chave apara criptar e decriptar.
*/

public class AES {
	public String encriptada;
	public String aEncriptar;
	
	//Construtor
	public AES (){}
	
	// Tamanho de chaves permitidas
	/* 32 caracteres = key com 256 bits;
	 * 24 caracteres = key com 192 bits;
	 * 16 caracteres = chave de 128 bits
	 * */
	
	// Cria uma chave criptografada  
	public SecretKeySpec CriarChave(String chaveSimetrica) {
		try {
			// Converte para bytes a chave string
			byte[] chave = chaveSimetrica.getBytes("Cp1252");		// Pode ser UTF-8; Mudar nas configuracoes do eclipse tambem
			
			// Criptografo com hash a chave
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			chave = md.digest(chave);
			
			/* Com o copyOf, valida a chave para ser no minimo x bits (indicado), bits insuficientes ele completa. 
			 * Se chave passar de 32 bits ele capitura somente x bits (indicado).	
			 * */
			chave = Arrays.copyOf(chave, 32); 
			SecretKeySpec secretKeySpec = new SecretKeySpec(chave, "AES");
			return secretKeySpec;
			
			/*System.out.println(new String(chave));
			for (int i = 0;i< new String(chave).length(); i++) {
				System.out.printf("[%d] %c\n",i,new String(chave).charAt(i));
				
			}*/
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("\nErro ao criar chave: \n");
			e.printStackTrace();
			return null;
		}
		
	}
	
	// Encriptar uma mensagem. Recebe mensagem e chave do tipo string. Retorna string criptografada.
	public String Encriptar(String encriptar, String chaveSimetrica) {
		try {
			SecretKeySpec secretKeySpec = CriarChave(chaveSimetrica);
			
			//Seleciona o algoritmo AES e a opcao de criptografar com a chave definida anteriormente
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
			
			//Converte strinf para bytes, pois a classe cipher usa vetor de bytes
			byte [] mensagem = encriptar.getBytes("Cp1252");
			byte [] mensagemEncriptada = cipher.doFinal(mensagem);	//doFinal: Criptografa ou descriptografa dados em uma operação de parte única ou finaliza uma operação de várias partes
			
			// converter de novo para string
			String mensagemEncriptadaString  = Base64.getEncoder().encodeToString(mensagemEncriptada);
			return mensagemEncriptadaString;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("\nErro ao encriptar mensagem: \n");
			e.printStackTrace();
			return null;
		}
	}
	
	// Decriptar uma mensagem. Recebe mensagem e chave do tipo string. Retorna string decriptografada.
	public String Decriptar(String decriptar, String chaveSimetrica) {
		try {
			SecretKeySpec secretKeySpec = CriarChave(chaveSimetrica);
			Cipher cipher = Cipher.getInstance("AES");
			
			//Seleciona o algoritmo AES e a opcao de degriptografar com a chave definida anteriormente
			cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
			
			byte [] mensagem = Base64.getDecoder().decode(decriptar);
			byte [] mensagemDecriptada = cipher.doFinal(mensagem);
			String mensagemDecriptadaString  = new String(mensagemDecriptada);
			return mensagemDecriptadaString;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("\nErro ao decriptar mensagem: \n");
			e.printStackTrace();
			return null;
		}
	}

}
