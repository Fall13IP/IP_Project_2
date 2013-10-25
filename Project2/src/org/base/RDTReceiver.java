package org.base;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class RDTReceiver {

	private RDTSharedVariables rdtSharedVariables = RDTSharedVariables.getInstance();
	
	public DatagramPacket receive(int port){
		
		//MulticastSocket socket;
		DatagramSocket socket;
		InetAddress group;
		DatagramPacket datagramPacket = null;
		try {
			socket = new DatagramSocket(port);
			//socket = new MulticastSocket(Constants.MULTICAST_SOCKET);
			//group = InetAddress.getByName(Constants.GROUP_IP);
			//socket.joinGroup(group);
			
			//DatagramSocket dSocket = new DatagramSocket(Constants.MULTICAST_SOCKET);
			
			byte [] byteData = new byte[1024];
			InetAddress address = InetAddress.getByName("192.168.2.23");
			//DatagramPacket packet = new DatagramPacket(byteData, byteData.length);
			datagramPacket = new DatagramPacket(byteData, byteData.length);
			socket.receive(datagramPacket);
			System.out.println("Received");
			
			//ACK handling
			/*if(segment.type == Constants.AckPacket){
				
			}//DATA packet handling
			else if(segment.type == Constants.DataPacket){
				//write file to disk
				if(segment.isLastSegment == true){
					
				}else{
					String value = new String(segment.data);
					System.out.println(value);
					Segment ackSegment = new Segment();
					ackSegment.type = Constants.AckPacket;
				}
			}
			
			System.out.println("bbye bbye");*/
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datagramPacket;
		

		
		
	}
	
	public static void main(String args[]){
		RDTReceiver rdtReceiver = new RDTReceiver();
		rdtReceiver.receive(4444);
	}
}
