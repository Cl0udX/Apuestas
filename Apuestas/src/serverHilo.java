import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import Interfaz.tablero;
/**
 * 
 * @author Santiago
 *maneja los hilos de los datos temporales de las apuestas
 */
public class serverHilo extends Thread {

	
	ServerSocket server;
	public int[] caballosApuestas;
	public int[] nApuestas;
	caballito caballos;
	public ArrayList<Datos> datosApuestas;

	private HashMap<String, ClientManager> hash;
	private HashMap<String, Integer> yaApostaron;

	public serverHilo(ServerSocket serverSocket) {
		server = serverSocket;
		yaApostaron = new HashMap<>();
		caballosApuestas = new int[6];
		hash = new HashMap<>();
		nApuestas=new int[6];
		datosApuestas=new ArrayList<>();

	}

	public void closeSocket() {
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block

		}
	}

	public void paintResult() {
		sendCaballo();
		int[] caballos = caballosApuestas;
		for (int i = 0; i < caballos.length; i++) {

			sendDatos(i + " " + caballos[i]);
			sendDatos(i + " " + nApuestas[i]);
			
		}
		
		this.caballos = new caballito(this,datosApuestas);
		this.caballos.start();

	}

	private void sendCaballo() {
		// TODO Auto-generated method stub
		Iterator<ClientManager> hilos = hash.values().iterator();

		while (hilos.hasNext()) {
			ClientManager aux = hilos.next();
			Integer valor=yaApostaron.get(aux.getSocket().getInetAddress().getHostAddress());
			if(valor!=null) {
				aux.recibirMensaje("" +valor );
				
			}
			
			
		}
	}

	public void run() {
		Socket client;
		try {
			while (true) {

				client = server.accept();

//				System.out.println(client.getInetAddress().getHostAddress());
				ClientManager cm = new ClientManager(client, this);
				hash.put(client.getInetAddress().getHostAddress(), cm);
				cm.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			paintResult();
		}

	}

	public void sendMessage(String id,String caballo, String cantidad, String ip) {
//		System.out.println(hash.get(address));
		try {
			if (!yaApostaron.containsKey(ip)) {
				Calendar c=new GregorianCalendar();
				String dia=Integer.toString(c.get(Calendar.DATE));
				String mes=Integer.toString(c.get(Calendar.MONTH));
				String ano=Integer.toString(c.get(Calendar.YEAR));
				String fecha=dia+"/"+mes+"/"+ano;
				datosApuestas.add(new Datos(id, caballo, cantidad, fecha));

				int horse = Integer.parseInt(caballo);
				nApuestas[horse-1]++;
				int money = Integer.parseInt(cantidad);
				caballosApuestas[horse - 1] += money;
				yaApostaron.put(ip, horse);
			}

		} catch (Exception e) {
			System.out.println("Almenos un usuario no esta colocando valores numericos");
		}
	}

	public void sendDatos(String datos) {
		Iterator<ClientManager> hilos = hash.values().iterator();

		while (hilos.hasNext()) {
			ClientManager aux = hilos.next();

			aux.recibirMensaje(datos);

		}
	}
	
}
