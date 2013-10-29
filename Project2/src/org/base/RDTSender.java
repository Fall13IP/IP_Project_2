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
		sendto();
		
	}
	int i;
	Segment segment;
	String serverIP;
	int timeout;
	public RDTSender(Segment s, String ServerIP){
		i=1;
		segment =s;
		serverIP = ServerIP;
		timeout=0;
		
	}
	public RDTSender(Segment s, String ServerIP, int Timeout){
		i=1;
		segment =s;
		serverIP = ServerIP;
		timeout=Timeout;
		
	}
	private RDTSharedVariables rdtSharedVariables = RDTSharedVariables.getInstance();
	public void sendto(){
		try {
			String s = "Hello All, how are you doing";
			byte[] buf = s.getBytes();
			InetAddress address = InetAddress.getByName("192.168.2.11");
			DatagramSocket socket = new DatagramSocket();
			//System.out.println("Buffer Length"+ buf.length);
			DatagramPacket packet = new DatagramPacket(buf, buf.length, address, Constants.SERVER_UDP_SOCKET);
			socket.send(packet);
			while(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void send(){
		
		
		
		try {
			InetAddress address = InetAddress.getByName(serverIP);
			DatagramPacket ackPacket;
			DatagramSocket socket= new DatagramSocket();
		
		byte[] buf;
		String msg="Boom , its working";
		//buf = SerializerDeserializer.serialize(segment);//makes the packet so it can be sent on the network, as we are sending segments of data. which is not like TCP.
	buf = msg.getBytes();

		
		//InetAddress add2 = InetAddress.getLocalHost();
		DatagramPacket packet = new DatagramPacket(buf, buf.length,address, Constants.SERVER_UDP_SOCKET);
       System.out.println("Adress to send:"+address + "Port "+Constants.SERVER_UDP_SOCKET);
		do{
		socket.send(packet);
        //socket.close();
        //System.out.println("Send Data packet:"+address);
      System.out.println("Destination IP:"+address); 
      System.out.println("Destination Portno:"+Constants.SERVER_UDP_SOCKET); 
       System.out.println("Socket Port"+socket.getPort());
       System.out.println("local socket port"+socket.getLocalPort());
      System.out.println("Packet Sent Number:"+segment.SequenceNumber); 
		RDTReceiver rdtReciever =new RDTReceiver();
      ackPacket = RDTReceiver.receive(socket, timeout);
       if(ackPacket==null) 
       {
    	  System.out.println("Error Detected with packet");
       }
       }while(ackPacket==null);
		i++;
        System.out.println("Recieved ACK PAcket");
        socket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void SendOneClient() {
		
	}
	
}