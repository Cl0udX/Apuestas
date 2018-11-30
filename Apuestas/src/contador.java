/**
 * 
 * @author Santiago
 *	se encarga de hacer la cuenta regresiva para el inicio de la carrera
 */
public class contador extends Thread{
	
	public int cuenta;
	serverHilo server;
	public contador(int regre,serverHilo h) {
		cuenta=regre;
		server=h;
	}
	
	public void run() {
		
		while(cuenta>0) {
			try {
				sleep(1000);
				cuenta--;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		server.closeSocket();
		
		
	}
}
