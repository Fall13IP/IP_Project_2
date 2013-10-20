package org.base;

public class RDTSharedVariables {
	
	private boolean sendNextSegment = false;
	private Object lock = new Object();
	private static RDTSharedVariables instance = null;
	
	private RDTSharedVariables(){
		
	}
	public static RDTSharedVariables getInstance(){
		
		if(instance == null){
			instance = new RDTSharedVariables();
		}
		
		return instance;
			
	}
	public boolean getSendNextSegment() {
		
		boolean temp;
		synchronized (lock) {
			temp = sendNextSegment;
		}
		return temp;
	}
	public void setSendNextSegment(boolean sendNextSegment) {
		synchronized (lock) {
			this.sendNextSegment = sendNextSegment;
		}
		
	}
}
