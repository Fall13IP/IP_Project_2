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
	int timeout;
	public RDTSender(Segment s, String ServerIP){
		segment =s;
		serverIP = ServerIP;
		
	}
	public RDTSender(Segment s, String ServerIP, int Timeout){
		segment =s;
		serverIP = ServerIP;
		timeout=Timeout;
		
	}
	private RDTSharedVariables rdtSharedVariables = RDTSharedVariables.getInstance();
	
	public void send(){
		
		
		DatagramPacket ackPacket;
		DatagramSocket socket;
		try {
			socket = new DatagramSocket();
		
		byte[] buf;
		buf = SerializerDeserializer.serialize(segment);//makes the packet so it can be sent on the network, as we are sending segments of data. which is not like TCP.
	

		InetAddress address = InetAddress.getByName(serverIP);
		InetAddress add2 = InetAddress.getLocalHost();
		DatagramPacket packet = new DatagramPacket(buf, buf.length,address, Constants.SERVER_UDP_SOCKET);
        DatagramSocket sockettoSend = new DatagramSocket(Constants.CLIENT_UDP_SOCKET);
		do{
		socket.send(packet);
        //socket.close();
        //System.out.println("Send Data packet:"+address);
        
		//RDTReceiver rdtReciever =new RDTReceiver();
       ackPacket = RDTReceiver.receive(sockettoSend, timeout);
       if(ackPacket==null) 
    	   System.out.println("Error Detected with packet");
        
        }while(ackPacket==null);
        System.out.println("Recieved ACK PAcket");
        sockettoSend.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void SendOneClient() {
		
	}
	
}