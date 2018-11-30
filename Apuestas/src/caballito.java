import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
/**
 * Clase que se encarga de correr el tiempo de la carrera y que cuando termina.. manda la informacion del caballo ganador
 */
public class caballito extends Thread {

	private Random rand;
	public static final int LIMITE = 20;
	private serverHilo h;
	private ArrayList<Datos> lista;
	
	

	public caballito(serverHilo h,ArrayList<Datos> datos) {
		rand=new Random();
		this.h = h;
		lista=datos;
	}

	public void run() {
		
		try {
			int numeros[]=new int[6];
			int d = 0;
			while (d != LIMITE) {

				int uno = rand.nextInt(6);				
				int uno1 = rand.nextInt(6);
				int uno2 = rand.nextInt(6);
				int uno3 = rand.nextInt(6);
				int uno4 = rand.nextInt(6);
				int uno5 = rand.nextInt(6);
				numeros[0]+=uno;
				numeros[1]+=uno1;
				numeros[2]+=uno2;
				numeros[3]+=uno3;
				numeros[4]+=uno4;
				numeros[5]+=uno5;
				h.sendDatos(uno + " " + uno1 + " " + uno2 + " " + uno3 + " " + uno4 + " " + uno5);

				sleep(1500);

				d++;
			}
			int mayor=0;
			for (int i = 1; i < numeros.length; i++) {
				if(numeros[i]>numeros[mayor]) {
					mayor=i;
				}
			}
			mayor++;
			System.out.println(lista.size());
			for (int i = 0; i < lista.size(); i++) {		
				System.out.println(lista.get(i).getId());
				File baseDatos=new File("./Datos/BaseDatos/"+lista.get(i).getId()+".txt");
				BufferedWriter bf=new BufferedWriter(new FileWriter(baseDatos,true));
				String gano="";
				if(Integer.parseInt(lista.get(i).getCaballo())==mayor) {
					gano="SI";
				}else {
					gano="NO";
				}
				bf.write("Monto: "+lista.get(i).getCantidad()+", Fecha: "+lista.get(i).getFecha()+", Caballo :"+lista.get(i).getCaballo()+", Victoria: "+gano+"\n");
				bf.close();
			}
			h.sendDatos("THE END");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
