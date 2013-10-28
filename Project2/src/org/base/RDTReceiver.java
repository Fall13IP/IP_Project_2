package org.base;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;

public class RDTReceiver {

	private RDTSharedVariables rdtSharedVariables = RDTSharedVariables.getInstance();
	
	public static DatagramPacket receive(int port){
		DatagramPacket datagramPacket = receive(port, 0);
		return datagramPacket;
	}
	
	public static DatagramPacket receive(int port,int Timeout) {
		
		//MulticastSocket socket;
		DatagramSocket socket;
		InetAddress group;
		DatagramPacket datagramPacket = null;
		try {
			
			socket = new DatagramSocket(port);
			if(Timeout!=0){
			socket.setSoTimeout(Timeout);
			}
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
			socket.close();
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
			
		} catch(SocketTimeoutException e2){
			datagramPacket=null;
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return datagramPacket;
		

		
		
	}
	
	
}
