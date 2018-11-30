

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
/**
 * 
 * @author Santiago
 * se encarga de desplegar la administracion de los datos que envia el cliente
 */
public class ClientManager extends Thread {

	private Socket client;
	
	private serverHilo server;
	
	private BufferedReader br;
	
	private PrintWriter pw;
	

	public ClientManager(Socket client, serverHilo server) {
		// TODO Auto-generated constructor stub
		this.client = client;
		this.server = server;
		try {
			
			br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			pw = new PrintWriter(client.getOutputStream(), true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 
	

	}
	public Socket getSocket() {
		return client;
	}

	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {

			// Create input and output streams
			while(true) {
				String[] message = br.readLine().split(" ");
				if(message.length==3) {
					
					server.sendMessage(message[0],message[1], message[2],client.getInetAddress().getHostAddress());
					
					
				}else {
					throw new Exception("Porfavor envie el mensaje en el formato correcto");
				}
			}
			// Take the message send by the client

		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}
	public void recibirMensaje(String message) {
		// TODO Auto-generated method stub
		pw.println(message);
		
	}
	
}
