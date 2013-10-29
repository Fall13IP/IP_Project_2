package org.server;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;

import org.base.ACKSender;
import org.base.Constants;
import org.base.RDTReceiver;
import org.base.Segment;
import org.base.SerializerDeserializer;

public class Server {

	public static void main(String[] args) {
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		boolean lastSegmentReceived = false;
		if(args.length == 3){
			int portNo = Integer.parseInt(args[0]);
			String filename = args[1];
			double probability = Double.parseDouble(args[2]);
			Random random = new Random();
			int expectedSequenceNumber = 1;
			try{
				DatagramSocket socket = new DatagramSocket(Constants.SERVER_UDP_SOCKET);
				while(!lastSegmentReceived){
					
					DatagramPacket packet = RDTReceiver.receive(socket);
					double randValue = random.nextDouble();
					//should packet drop be simulated
					if(randValue > probability)
					{
						Segment segment = SerializerDeserializer.deserialize(packet.getData());
						if(segment.getType() == Constants.DataPacket){
							
							System.out.println("Received: seq num: " + segment.getSequenceNumber() + "length: " + segment.getData().length);
							//received as expected
							if(segment.getSequenceNumber() == expectedSequenceNumber)
							{
								try {
									byteArrayOutputStream.write(segment.getData());
									Segment ack = new Segment();
									ack.setSequenceNumber(expectedSequenceNumber);
									ack.setType(Constants.AckPacket);
									byte [] ackBytes = SerializerDeserializer.serialize(ack);
									DatagramPacket ackPacket = new DatagramPacket(ackBytes, ackBytes.length, packet.getAddress(), packet.getPort());
									System.out.println("Sending ack to ip: " +packet.getAddress() + " port: " + packet.getPort());
									socket.send(ackPacket);
									//ACKSender ackSender = new ACKSender(ack, packet.getAddress().getHostAddress(), packet.getPort());
									//ackSender.send();
									
								
									if(segment.isLastSegment() == true){
										lastSegmentReceived = true;
									}
									expectedSequenceNumber++;
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							//earlier packets received again
							else if(segment.getSequenceNumber() < expectedSequenceNumber){
								Segment ack = new Segment();
								ack.setSequenceNumber(segment.getSequenceNumber());
								ack.setType(Constants.AckPacket);
								byte [] ackBytes = SerializerDeserializer.serialize(ack);
								DatagramPacket ackPacket = new DatagramPacket(ackBytes, ackBytes.length, packet.getAddress(), packet.getPort());
								System.out.println("Sending ack to ip: " +packet.getAddress() + " port: " + packet.getPort());
								socket.send(ackPacket);
								//ACKSender ackSender = new ACKSender(ack, packet.getAddress().getHostAddress(), packet.getPort());
								//ackSender.send();
							}
							
						}
					}
					else
						System.out.println("Packet loss simulated, value of rand: " + randValue);
				}
			}catch(IOException e){
				e.printStackTrace();
			}
			System.out.println("Total file size: " + byteArrayOutputStream.size());
			
			try {
				FileOutputStream fileOutputStream = new FileOutputStream(filename);
				fileOutputStream.write(byteArrayOutputStream.toByteArray());
				fileOutputStream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

}
