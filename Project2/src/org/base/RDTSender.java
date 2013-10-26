package org.base;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class RDTSender extends Thread {
	@Override
	public void run() {
		// TODO Auto-generated method stub
		send();
		
	}
	Segment segment;
	String serverIP;
	public RDTSender(Segment s, String ServerIP){
		segment =s;
		serverIP = ServerIP;
		
	}
	private RDTSharedVariables rdtSharedVariables = RDTSharedVariables.getInstance();
	
	public void send(){
		DatagramSocket socket;
		try {
			socket = new DatagramSocket(Constants.SERVER_UDP_SOCKET);
		
		byte[] buf;
		buf = SerializerDeserializer.serialize(segment);//makes the packet so it can be sent on the network, as we are sending segments of data. which is not like TCP.
	

		InetAddress address = InetAddress.getByName(serverIP);
		InetAddress add2 = InetAddress.getLocalHost();
		
        DatagramPacket packet = new DatagramPacket(buf, buf.length,address, 4444);
        socket.send(packet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void SendOneClient() {
		
	}
	
}
