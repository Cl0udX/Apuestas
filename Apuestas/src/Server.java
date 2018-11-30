

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author Santiago
 * * se encarga de manejar todos los servidores tanto de la pagina WEB, chat de voz y simulacion de la carrera de caballos

 */

public class Server {

private udpMuti ddd;
	
	public class udpMuti extends Thread{
		
		UdpMulticastService dp;
		
		
		public udpMuti( ) {
			
		}
		public void run() {
			dp=new UdpMulticastService();
			dp.iniciar();
		}
	}
	public class tcpHo extends Thread{
		ServerManager sm;
		public tcpHo() {
		}
		public void run() {
			sm=new ServerManager();
			sm.iniciar();
		}
	}
	public tcpHo aaa;
	private WebServerHack1 fff;
	public Server() {
		aaa=new tcpHo();
		ddd=new udpMuti();
		fff = new WebServerHack1();
	}
	public static void main(String[] args) {
		Server s=new Server();
		s.iniciar();
		
		
	}
	private void iniciar() {
		// TODO Auto-generated method stub
		aaa.start();
		ddd.start();
		fff.start();
		
	}


}
