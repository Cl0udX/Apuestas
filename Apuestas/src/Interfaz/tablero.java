package Interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class tablero extends JFrame {

	private int[] caballo;
	private int[] advance;
	private int[] nApuestas;
	private int caballoAapostar;
	public int cuentaRegresiva;
	public void menosSe() {
		cuentaRegresiva--;
	}
	public class hilito extends Thread{
		public tablero dd;
		public hilito(tablero d) {
			dd=d;
			
		}
		public void run() {
			try {
			while(true) {
					sleep(1000);
					dd.menosSe();
					dd.repaint();
					
			}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public tablero(int segundos) {
		cuentaRegresiva=segundos;
		caballo = new int[6];
		advance = new int[6];
		caballoAapostar = -1;
		nApuestas=new int[6];
		hilito d=new hilito(this);
		d.start();
		JOptionPane.showMessageDialog(this, "la carrera comienza en 60 segundo desde que inicio el server\n(Para no ponerte a esperar profe), haga su apuesta", "AVISO", 1);
		JOptionPane.showMessageDialog(this, "Para hacer alguna apuesta, en la consola coloca el numero del caballo \n+ (Espacio) + la cantida a apostar ", "AVISO", 1);
		
//		System.out.println("C#1	C#2	C#3	C#4	C#5	C#6");
		setSize(700, 500);
		setVisible(true);
		
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		int x = 50;
		int y = 50;
		g2.setColor(new Color(255, 0, 0));
		String[] avaces=new String[6];
		for (int i = 0; i < advance.length; i++) {
			avaces[i]="";
			for (int j = 0; j < advance[i]; j++) {
				avaces[i]=avaces[i]+"--";
			}
		}
		if (caballoAapostar == -1) {
			for (int i = 0; i < advance.length; i++) {

				g2.drawString("Caba# " + (i + 1)+" "+avaces[i], x, y);
				
				y += 30;
			}
		} else {
			for (int i = 0; i < advance.length; i++) {
				if(caballoAapostar==i) {
					g2.setColor(Color.BLACK);
				}else {
					g2.setColor(new Color(255, 0, 0));
				}
				g2.drawString("Caba#" + (i + 1)+" "+avaces[i], x, y);
				y += 30;
			}
		}
		
		g2.setColor(Color.BLACK);
		x=600;
		y=70;
		int minutos=0;
		int segundos=0;
		if(cuentaRegresiva>0) {			
				 minutos=(int)((double)cuentaRegresiva/60.0);
				 segundos=(int) ((((double)cuentaRegresiva/60.0)-minutos)*60);
				
				
		}
		g2.drawString(minutos+" : "+segundos, x, y);
		x=50;
		y=260;
		for (int i = 0; i < avaces.length; i++) {
			g2.drawString("El caballo #" + (i + 1) + ". Tiene un total de:"+nApuestas[i]+", con un total de: $" + caballo[i], x, y);
			y+=30;
		}
		g2.setColor(Color.BLUE);
		x=400;
		y=260;
		g2.drawString("usted hizo una apuesta al #:", x, y);
		y+=50;
		x+=30;
		g2.setFont(new Font("Tahoma", Font.BOLD, 46));
		if(caballoAapostar==-1) {
			g2.drawString("Ninguno", x, y);
			
		}else {
			g2.drawString((caballoAapostar+1)+"", x, y);
			
		}
		
	}

	public int[] getCaballo() {
		return caballo;
	}

	public void setCaballo(int[] caballo) {
		this.caballo = caballo;
	}

	public int[] getAdvance() {
		return advance;
	}

	public void setAdvance(int[] advance) {
		this.advance = advance;
	}

	public void apuestas(String mensaje) {
		String[] f = mensaje.split(" ");
		int i = Integer.parseInt(f[0]);
		int valor = Integer.parseInt(f[1]);
		caballo[i] = valor;
	}

	public void actualizarCaballos(String mensaje) {
		// TODO Auto-generated method stub
		if (mensaje.equals("THE END")) {
			int max = 0;
			int pos = -1;
			for (int j = 0; j < advance.length; j++) {
				if (advance[j] > max) {
					max = advance[j];
					pos = j;
				}

			}
			pos++;
			for (int i = 0; i < advance.length; i++) {
				System.out.println("El caballo #" + (i + 1) + ". Tiene un total en la apuesta de:" + caballo[i]);
				System.out.println(" ");
			}
			System.out.println("El caballo GANADOR es el #" + pos);
			JOptionPane.showMessageDialog(this, "El caballo GANADOR es el # "+ pos, "Ganador", 1);
		} else {
			String[] f = mensaje.split(" ");

			for (int j = 0; j < f.length; j++) {
				advance[j] += Integer.parseInt(f[j]);
			}

		}

	}

	public void setCaballo(int parseInt) {
		// TODO Auto-generated method stub
		caballoAapostar = parseInt - 1;
	}

	public void cantidad(String mensaje) {
		String[] canti=mensaje.split(" ");
		int i=Integer.parseInt(canti[0]);
		int n=Integer.parseInt(canti[1]);
		nApuestas[i]=n;
	}

}
