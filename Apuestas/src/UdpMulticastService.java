
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
/**
 * 
 * @author Santiago
 * cliente del server del chat de voz
 */
public class UdpMulticastService {

	private final static String INET_ADDR = "228.5.6.7";

	private final static int PORT = 8888;

	private MulticastSocket multicastSocket;

	private InetAddress inetAddress;

	private TargetDataLine targetDataLine;

	private  byte audioBuffer[] = new byte[10000];

	private int port;

	public UdpMulticastService() {
		// TODO Auto-generated constructor stub
		this.port = 9877;
		startMulticast();

	}

	private void initialize() throws IOException {
		this.setupAudio();
		this.multicastSocket = new MulticastSocket();
		this.inetAddress = InetAddress.getByName(INET_ADDR);
	}

	private AudioFormat getAudioFormat() {
		float sampleRate = 16000F;
		int sampleSizeInBits = 16;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = false;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

	private void setupAudio() {
		// TODO Auto-generated method stub
		try {
			AudioFormat audioFormat = getAudioFormat();
			DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
			targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
			targetDataLine.open(audioFormat);
			targetDataLine.start();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(0);
		}
	}

	public void startMulticast() {
		System.out.println("UDP Multicast Time Server Started");
		try {
			initialize();
//			this.multicastSocket.joinGroup(this.inetAddress);

			byte[] data;
			DatagramPacket packet;

			/**
			 * The server application will use an infinite loop to broadcast a new date and
			 * time every second. The thread is paused for one second, and then a new date
			 * and time is created using the Data class.
			 **/
			while (true) {
//				Thread.sleep(1000);
				String message = (new Date()).toString();
//				System.out.println("Sending: [" + message + "]");
				data = message.getBytes();
				int count = targetDataLine.read(audioBuffer, 0, audioBuffer.length);
				if (count > 0) {
					packet = new DatagramPacket(audioBuffer, audioBuffer.length, inetAddress, PORT);
					multicastSocket.send(packet);
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("UDP Multicast Time Server Terminated");
	}

	

	public void iniciar() {
		
		new UdpMulticastService();
	}
}
