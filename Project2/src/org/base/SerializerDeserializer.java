package org.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializerDeserializer {

	public static byte [] serialize(Segment segment){
		
		byte [] serializedData = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(bos);
			oos.writeObject(segment);
			serializedData = bos.toByteArray();
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return serializedData;
	}
	
	public static Segment deserialize(byte [] serializedData){
		
		Segment segmentClass = null;
		
		ObjectInputStream iStream;
		try {
			iStream = new ObjectInputStream(new ByteArrayInputStream(serializedData));
			segmentClass = (Segment) iStream.readObject();
			iStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return segmentClass;
	}
	
	public static void main(String args[]){
		
		Segment segment = new Segment();
		segment.SequenceNumber = 666;
		segment.type =  Constants.AckPacket;
		
		byte [] serializedData = SerializerDeserializer.serialize(segment);
		System.out.println("Length: " + serializedData.length);
		
		Segment deSegment = SerializerDeserializer.deserialize(serializedData);
		System.out.println(deSegment.SequenceNumber);
		System.out.println(deSegment.type);
	}
}
