package org.base;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ACKSender {

	Segment segment;
	String serverIP;
	public ACKSender(Segment s, String ServerIP){
		segment =s;
		serverIP = ServerIP;
		
	}
	
	
	public void send(){
		DatagramSocket socket;
		try {
			socket = new DatagramSocket();
		
		byte[] buf;
		buf = SerializerDeserializer.serialize(segment);//makes the packet so it can be sent on the network, as we are sending segments of data. which is not like TCP.
	

		InetAddress address = InetAddress.getByName(serverIP);
		InetAddress add2 = InetAddress.getLocalHost();
		DatagramPacket packet = new DatagramPacket(buf, buf.length,address, Constants.CLIENT_UDP_SOCKET);
        socket.send(packet);
        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
