package org.client;

public class Client {

	public static void main(String[] args) {
		int noOfServers = 0;
		String [] serverIPsStrings = null;
		String fileName= null;
		int portNo = 0;
		int mss = 0;
		if(args.length > 0){
			noOfServers = Integer.parseInt(args[0]);
		}
		if(noOfServers != 0){
			serverIPsStrings = new String[noOfServers];
			//check if no of server ip passed is equal to as noOf Servers
			if(args.length == noOfServers + 4){
				for(int i=1; i <= noOfServers; i ++){
					serverIPsStrings[i - 1] = args[i];
				}
				
				portNo = Integer.parseInt(args[noOfServers + 1]);
				fileName = args[noOfServers + 2];
				mss = Integer.parseInt(args[noOfServers + 3]);
			}
			
		}
		
		System.out.println(noOfServers);
		if(serverIPsStrings != null){
			for(int i=0; i < serverIPsStrings.length; i++){
				System.out.println(serverIPsStrings[i]);
			}
			System.out.println(portNo);
			System.out.println(fileName);
			System.out.println(mss);
		}

	}

}
