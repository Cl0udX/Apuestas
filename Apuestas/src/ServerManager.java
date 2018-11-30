

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.plaf.SliderUI;

import org.omg.PortableServer.ServantRetentionPolicyValue;
/**
 * 
 * @author Santiago
 *la clase de mandar la informacion inicial de server
 */
public class ServerManager {

	private serverHilo miserver;
	
	
	
	public ServerManager() {
		// TODO Auto-generated constructor stub
	
	}
	
	public void iniciar() {
		try {
			int port = 1234;

			// Create a server in the port #1234
			miserver=new serverHilo(new ServerSocket(port));
			contador cuenta=new contador(30,miserver);
			cuenta.start();
			miserver.start();
			
			
		} catch (IOException ioe) {
			
		}
	}
	
	
	
	
	
	
}
