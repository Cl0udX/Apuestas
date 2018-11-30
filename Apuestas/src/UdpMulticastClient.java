
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
/**
 * 
 * @author Santiago
 *cliente de chat de voz
 */
public class UdpMulticastClient {

	private final static String INET_ADDR = "228.5.6.7";

	private final static int PORT = 8888;

	private MulticastSocket multicastSocket;

	private InetAddress inetAddress;

	private AudioInputStream audioInputStream;

	private SourceDataLine sourceDataLine;

	public UdpMulticastClient() {
		// TODO Auto-generated constructor stub
		startMulticast();
	}

	private void initialize() throws IOException {
		this.multicastSocket = new MulticastSocket(PORT);
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

	private void playAudio() {
		// TODO Auto-generated method stub
		byte[] buffer = new byte[10000];
		try {
			int count = 0;
			while (true) {
				count = audioInputStream.read(buffer, 0, buffer.length);
				if (count == -1) {
					break;
				}
				sourceDataLine.write(buffer, 0, buffer.length);
				
			}
		} catch (Exception e) {
			// Handle exceptions
			e.printStackTrace();
		}
	}

	public void startMulticast() {
		System.out.println("UDP Multicast Time Client Started");
		try {
			/**
			 * We are going to make an InetAddress instance using the multicast address of
			 * 228.5.6.7. The client then joins the multicast group using the joinGroup
			 * method
			 */
			initialize();
			this.multicastSocket.joinGroup(this.inetAddress);

			/**
			 * A DatagramPacket instance is needed to receive messages that were sent to the
			 * client.
			 */
			byte[] data = new byte[10000];
			DatagramPacket packet = new DatagramPacket(data, 0, data.length);

			/**
			 * The client application then enters an infinite loop where it blocks at the
			 * receive method until the server sends a message. Once the message has
			 * arrived, the message is displayed
			 */
			AudioFormat audioFormat = getAudioFormat();
			DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);

			sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();

			while (true) {
				multicastSocket.receive(packet);

				byte audioData[] = packet.getData();

				sourceDataLine.write(audioData, 0, audioData.length);

				String message = new String(packet.getData(), 0, packet.getLength());
//				System.out.println("Message from: " + packet.getAddress() + " Message: [" + message + "]");
			}
		} catch (IOException | LineUnavailableException ex) {
			ex.printStackTrace();

		}
		System.out.println("UDP Multicast Time Client Terminated");
	}

	
	public void iniciar() {
		new UdpMulticastClient();
		
	}

}
