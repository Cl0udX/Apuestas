import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;

import Interfaz.tablero;
/**
 * 
 * @author Santiago
 *se encarga de comunicarse con la interfaz grafica y mandar todos los datos a ella
 */
public class escritorThread extends Thread{

	private BufferedReader lector;
	private tablero interfaz;
	
	public escritorThread(Socket clienteServer) {
		// TODO Auto-generated constructor stub
	try {
		lector=new BufferedReader(new InputStreamReader(clienteServer.getInputStream()));
		interfaz=new tablero(30);
	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	public BufferedReader getLector() {
		return lector;
	}
	public void run() {
		
		String mensajeAntiguo="";
		int cantidad=12;
		boolean bandera=false;
		while(true) {
			try {
				String mensaje=lector.readLine();
				
				 if(mensaje!=null&&!mensaje.equals(" ")&&!mensaje.equals("")&&!mensaje.equals(mensajeAntiguo)) {
					 if(mensaje.length()==1) {
						 interfaz.setCaballo(Integer.parseInt(mensaje));
						 
					 }
					 else if(cantidad>0) {
						 if(!bandera) {							 
							 interfaz.apuestas(mensaje);
						 }else {
							 interfaz.cantidad(mensaje);
						 }
						bandera=!bandera;
						cantidad--;
					}
					else {
						interfaz.actualizarCaballos(mensaje);
					}
					mensajeAntiguo=mensaje;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
