

import java.net.*;
import java.util.StringTokenizer;
import java.io.*;
/**
 * 
 * @author Santiago
 * clase que ese encarga de inicializar	 el localhost para la ejecucion de la pagina web que mostrara los datos de las bases de datos
 */
public class ClientHandlerHack1 implements Runnable{
	
	private final Socket socket;
	
	

	public ClientHandlerHack1(Socket socket)
	{
		this.socket =  socket;
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("\nClientHandler Started for " + this.socket);
		while(true) 
		{
			handleRequest(this.socket);
		}		
		//System.out.println("ClientHandler Terminated for "+  this.socket + "\n");
	}
	
	public void handleRequest(Socket socket)
	{
		try {
			
			
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String headerLine = in.readLine();
			if(headerLine!=null)
			{
				
			
				System.out.println(headerLine);
				// A tokenizer is a process that splits text into a series of tokens
				StringTokenizer tokenizer =  new StringTokenizer(headerLine);
				//The nextToken method will return the next available token
				String httpMethod = tokenizer.nextToken();
				// The next code sequence handles the GET method. A message is displayed on the
				// server side to indicate that a GET method is being processed
				if(httpMethod.equals("GET"))
				{
					System.out.println("Get method processed");
					String httpQueryString = tokenizer.nextToken();
					System.out.println(httpQueryString);
					if(httpQueryString.equals("/"))
					{
						StringBuilder responseBuffer =  new StringBuilder();
						String str="";
						BufferedReader buf = new BufferedReader(new FileReader("./Datos/ClienteConsultor.html"));
						
						while ((str = buf.readLine()) != null) {
							responseBuffer.append(str);
					    }
						sendResponse(socket, 200, responseBuffer.toString());		
					    buf.close();
					}
					if(httpQueryString.contains("/?gift="))
					{
						System.out.println("Get method processed");
						String[] response =  httpQueryString.split("gift=");
						String identificador=response[1];
						File f=new File("./Datos/BaseDatos/"+identificador+".txt");
						String datos="No Hay Datos";
						if(f.exists()) {
							datos="";
							BufferedReader lectura=new BufferedReader(new FileReader(f));
							String dat=lectura.readLine();
							while(dat!=null) {
								datos+=dat+"<br>";
								dat=lectura.readLine();
							}
						}
						StringBuilder responseBuffer =  new StringBuilder();
						responseBuffer
						.append("<html>")
						.append("<head> <title> Informacion de base de datos </title> "
								+ "<script> "
								+ "window.setTimeout(kuky, 1000);"
								+ "function kuky(){"
							
								+ "alert(\"Datos ultra secretos. CUIDADO\");"
							
								+ "};"								
								+ "</script> "
								+ "<style>"
								+ "body{"								
								+ "cursor: url(\"http://www.banderas-del-mundo.com/America_del_Sur/Colombia/colombia_mwd.gif\"), auto;"
								+ "}"
								+ "</style>"
								+ "</head> ")
						.append("<body bgcolor='black'>")
						
						.append("<h1 style='color:white'> Datos de Carrera de caballos </h1>")
						.append("<h1 style='color:white'> "+datos+"</h1>")
						.append("<img src='https://s2.glbimg.com/QJD0YP7szRqJuSEUdGHPF_2Dwqs=/850x446/s.glbimg.com/po/tt/f/original/2012/06/01/pirata-e1314380534977.jpg'>")
						.append("<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/mK4t8U3eSAI?autoplay=1\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" ></iframe>")
						.append("</body>")
						.append("</html>");
						sendResponse(socket, 200, responseBuffer.toString());		
					    
					}
										    
				}
				
				else
				{
					System.out.println("The HTTP method is not recognized");
					sendResponse(socket, 405, "Method Not Allowed");
				}
			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void sendResponse(Socket socket, int statusCode, String responseString)
	{
		String statusLine;
		String serverHeader = "Server: WebServer\r\n";
		String contentTypeHeader = "Content-Type: text/html\r\n";
		
		try {
			DataOutputStream out =  new DataOutputStream(socket.getOutputStream());
			if (statusCode == 200) 
			{
				statusLine = "HTTP/1.0 200 OK" + "\r\n";
				String contentLengthHeader = "Content-Length: "
				+ responseString.length() + "\r\n";
				out.writeBytes(statusLine);
				out.writeBytes(serverHeader);
				out.writeBytes(contentTypeHeader);
				out.writeBytes(contentLengthHeader);
				out.writeBytes("\r\n");
				out.writeBytes(responseString);
				} 
			else if (statusCode == 405) 
			{
				statusLine = "HTTP/1.0 405 Method Not Allowed" + "\r\n";
				out.writeBytes(statusLine);
				out.writeBytes("\r\n");
			} 
			else 
			{
				statusLine = "HTTP/1.0 404 Not Found" + "\r\n";
				out.writeBytes(statusLine);
				out.writeBytes("\r\n");
			}
			//out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}