import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import Interfaz.tablero;
/**
 * 
 * @author Santiago
 * se encarga de manejar todos los clientes tanto del chat de voz y simulacion de la carrera de caballos
 */
public class cliente {

	Socket clienteServer;
	private udpMuti ddd;

	public class udpMuti extends Thread {

		UdpMulticastClient dp;
		

		public udpMuti() {
		
		}

		public void run() {
			dp = new UdpMulticastClient();
			dp.iniciar();
		}
	}
	

	public cliente() {
		// TODO Auto-generated constructor stub

		ddd = new udpMuti();
	
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		cliente u = new cliente();
		u.inicar();
		int puert = 1234;
		u.clienteServer = new Socket("localhost", puert);
		escritorThread fff = new escritorThread(u.clienteServer);
		fff.start();

		PrintWriter dd = new PrintWriter(u.clienteServer.getOutputStream(), true);
		Scanner g = new Scanner(System.in);
		while (true) {
			String d = g.nextLine();
			dd.println(d);

		}

	}

	private void inicar() {
		// TODO Auto-generated method stub
		ddd.start();
	}
}
