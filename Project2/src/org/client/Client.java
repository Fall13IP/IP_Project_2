package org.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.base.Constants;
import org.base.RDTSender;
import org.base.Segment;

public class Client {

	public static void main(String[] args) {
		int noOfServers = 0;
		String [] serverIPsStrings = null;
		String fileName= null;
		int portNo = 0;
		int MSS = 0;
		int PacketNumber=0;
		 int timeout = 0;
		
		if(args.length > 0){
			noOfServers = Integer.parseInt(args[0]);
		}
		
		if(noOfServers != 0){
			serverIPsStrings = new String[noOfServers];
			//check if no of server ip passed is equal to as noOf Servers
			if(args.length == noOfServers + 5){
				for(int i=1; i <= noOfServers; i ++){
					serverIPsStrings[i - 1] = args[i];
				}
				
				portNo = Integer.parseInt(args[noOfServers + 1]);
				fileName = args[noOfServers + 2];
				MSS = Integer.parseInt(args[noOfServers + 3]);
				timeout = Integer.parseInt(args[noOfServers + 4]);
			}
			
		}
		//Constants.CLIENT_UDP_SOCKET = portNo;
		//System.out.println("Port number "+serverIPsStrings[0]);
		byte[] SendMessegeBytes = new byte[MSS];
		byte[] SendMessegeLastBytes;
		RDTSender [] rdtSender = new RDTSender[serverIPsStrings.length];
		File file = new File(fileName);
		FileInputStream FIStream,FIStream2;
		try {
			FIStream2 = new FileInputStream(file);
			FIStream = new FileInputStream(file);
			
		
		int content;
		int content2=0;
		String checkSevenBit=null;
		String checkSevenBit2=null;
		char[] cheksum;
		String Messege[] = new String[MSS/2];
		String LastFrame[];
		int iterator=0;
		int tracker=0;
		
		int x=FIStream.available();;
		
		
			//now I have to calcuate and provide the checksum, how to do that?, I have input byte stream, how to deal with it?
			do{
				if(iterator!=MSS && FIStream.available()>0){
					content = (short) FIStream.read();
					if(tracker==1){
					
									
					checkSevenBit = Integer.toBinaryString(content);
					checkSevenBit = bitCorrector( checkSevenBit);
					checkSevenBit2 = Integer.toBinaryString(content2);
					checkSevenBit2=bitCorrector(checkSevenBit2);
					checkSevenBit=checkSevenBit.concat(checkSevenBit2);//converts to 16 bit values.
					//System.out.println(checkSevenBit);
					Messege[(iterator)/2] = checkSevenBit;//converts integer to binary representation
					
					
					tracker=0;
					}else{
						content2=content;
						tracker=1;
					}
					iterator++;
				}
				else if(FIStream.available()==0 && iterator<MSS){
					LastFrame = new String[FIStream2.available()];
					LastFrame = Messege;
					cheksum=ChecksumClass(LastFrame).clone();
					//System.out.println(cheksum);
					x=-1;
					System.out.println("File Availible"+FIStream2.available());
					SendMessegeLastBytes = new byte[FIStream2.available()];
					FIStream2.read(SendMessegeLastBytes);
					System.out.println("File Segment LAst"+SendMessegeLastBytes.length);
					PacketNumber = PacketNumber+1;
					Segment s = new Segment();
					s.setType(Constants.DataPacket);
					s.setData(SendMessegeLastBytes);
					s.setLastSegment(true);
					s.setSequenceNumber(PacketNumber);
					//s.setSequenceNumber(tracker);
					for(int i=0;i<serverIPsStrings.length;i++){
						
						rdtSender[i] = new RDTSender(s,serverIPsStrings[i],timeout);
						 rdtSender[i].start();
					}
					for(int i=0;i<serverIPsStrings.length;i++){
						try {
							rdtSender[i].join();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}
				}
				else{
				//checksum calculation
				//and data send, I have 500 bytes of data in Messege, and I am going to add
						iterator=0;
						cheksum=ChecksumClass(Messege).clone();
						
						FIStream2.read(SendMessegeBytes);
						System.out.println("Segment Size "+SendMessegeBytes.length);
						PacketNumber = PacketNumber+1;
						Segment s = new Segment();
						s.setType(Constants.DataPacket);
						
						s.setData(SendMessegeBytes);
						s.setSequenceNumber(PacketNumber);
						
						for(int i=0;i<serverIPsStrings.length;i++){
							System.out.println("calling server for Frame Number"+i);
							 rdtSender[i] = new RDTSender(s,serverIPsStrings[i],timeout);
							 rdtSender[i].start();
						}
						System.out.println("Waiting to return");
						for(int i=0;i<serverIPsStrings.length;i++){
							try {
								rdtSender[i].join();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						//call sender here. and wait.
						System.out.println(cheksum);
					}
			}
			while(x>=0);	
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static String bitCorrector(String checkSevenBit) {
		// TODO Auto-generated method stub
		if(checkSevenBit.length()<=7){
			if(checkSevenBit.length()==7){
				checkSevenBit=checkSevenBit.concat("0");
			}
			else if(checkSevenBit.length()==6)
			{
			checkSevenBit=checkSevenBit.concat("00");
			} 
			else if(checkSevenBit.length()==5)
			{
				checkSevenBit=checkSevenBit.concat("000");
			}
			else if(checkSevenBit.length()==4)
			{
				checkSevenBit=checkSevenBit.concat("0000");
			}
			
			
		}
		return checkSevenBit;
	}

	private static char[] ChecksumClass(String [] messege) {
		// TODO Auto-generated method stub
		char[] checksum = null;
		char[] Charecter1,Charecter2,Charecter3;
		int x;
		//first taking first 2 elements checksum.
		
		Charecter1 = messege[0].toCharArray();
		//System.out.println(Charecter1);
		Charecter2=messege[1].toCharArray();
		//System.out.println(Charecter2);
		Charecter3 = AddBits(Charecter1,Charecter2);
		//System.out.println(Charecter3);
		for(x=2;x<messege.length;x++)
		{
			Charecter1 = messege[x].toCharArray();
			Charecter3 = AddBits(Charecter1, Charecter3);	
			//System.out.println(Charecter3);
		}
		checksum=Charecter3.clone();
		
		return checksum;
	}

	  private static char[] AddBits(char[] charecter1, char[] charecter2) {
			// TODO Auto-generated method stub
			int iterator = 0;
			int carry=0;
			char item1=1;
			//System.out.println(charecter2);
			//System.out.println(charecter1);
			char[] chksum = new char[16];
			char[] ZeroArray = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','1'};
			String a;
			char first,second;
			for(iterator=charecter1.length-1;iterator>=0;iterator--){
				first = charecter1[iterator];
				second=charecter2[iterator];
				
				//System.out.println("char 1 :"+charecter1[iterator]+"char 2:"+charecter2[iterator]);
				
				if(charecter1[iterator]=='1' && charecter2[iterator]=='1' && carry==0){
					chksum[iterator]='0';
					carry=1;
				}
				else if(charecter1[iterator]=='1' && charecter2[iterator]=='1' && carry==1){
					chksum[iterator]='1';
					carry=1;
				}
				else if(charecter1[iterator]=='0' && charecter2[iterator]=='1' &&carry==0){
					chksum[iterator]='1';
					carry=0;
				}
				else if(charecter1[iterator]=='0' && charecter2[iterator]=='1' &&carry==1){
					chksum[iterator]='0';
					carry=1;
				}
				else if(charecter1[iterator]=='1' && charecter2[iterator]=='0' && carry==0){
					chksum[iterator]='1';
					carry=0;
				}
				else if(charecter1[iterator]=='1' && charecter2[iterator]=='0' &&carry==1){
					chksum[iterator]='0';
					carry=1;
				}
				else if(charecter1[iterator]=='0' && charecter2[iterator]=='0' && carry ==0){
					chksum[iterator]='0';
					carry=0;
				}
				else if(charecter1[iterator]=='0' && charecter2[iterator]=='0' && carry ==1){
					chksum[iterator]='1';
					carry=0;
				}
				//System.out.println(chksum[iterator]);
			}
			if(carry==1){
				//System.out.println(ZeroArray);
				
				chksum = AddBits(chksum, ZeroArray);
			}
			else{
			return chksum;
			}
			return chksum;
		}

}
