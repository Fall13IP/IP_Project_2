package org.base;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RDTSender extends Thread {
	@Override
	public void run() {
		// TODO Auto-generated method stub
		send();
		
	}
	Segment segment;
	String serverIP;
	int timeout;
	int portNumber;
	public RDTSender(Segment s, String ServerIP, int portNo){
		segment =s;
		serverIP = ServerIP;
		portNumber = portNo;
		
	}
	public RDTSender(Segment s, String ServerIP,int portNo, int Timeout){
		segment =s;
		serverIP = ServerIP;
		timeout=Timeout;
		portNumber = portNo;
		
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
		DatagramPacket packet = new DatagramPacket(buf, buf.length,address, portNumber);
		 System.out.println("Destination IP:"+address); 
	      System.out.println("Destination Portno:"+portNumber); 
	      System.out.println("Packet Sent Number:"+segment.SequenceNumber); 
		do{
		socket.send(packet);
        //socket.close();
        //System.out.println("Send Data packet:"+address);
        
		//RDTReceiver rdtReciever =new RDTReceiver();
       ackPacket = RDTReceiver.receive(socket, timeout);
       if(ackPacket==null) 
    	   System.out.println("Error Detected with packet");
        
        }while(ackPacket==null);
        
        socket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void SendOneClient() {
		
	}
	
}