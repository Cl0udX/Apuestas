


import java.io.IOException;
import java.net.*;
/**
 * 
 * @author Santiago
 * * clase que ese encarga de esparar el localhost para la ejecucion de la pagina web que mostrara los datos de las bases de datos

 */
public class WebServerHack1 extends Thread {

	public WebServerHack1()
	{
		
	}

	public void run() {
		
		System.out.println("Webserver Started");
		try {
			ServerSocket serverSocket =  new ServerSocket(80);
			while(true) 
			{
				System.out.println("Waiting for the client request");
				Socket remote = serverSocket.accept();
				System.out.println("Connection made");
				new Thread(new ClientHandlerHack1(remote)).start();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}