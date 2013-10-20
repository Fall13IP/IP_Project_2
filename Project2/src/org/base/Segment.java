package org.base;

import java.io.Serializable;

public class Segment implements Serializable{
	private static final long serialVersionUID = 7830492147281088365L;
	short ChecksumVal;
	int type;
	
	int  SequenceNumber;
	byte[] data;
	boolean isLastSegment = false;
	public boolean isLastSegment() {
		return isLastSegment;
	}
	public void setLastSegment(boolean isLastSegment) {
		this.isLastSegment = isLastSegment;
	}
	public synchronized int getType() {
		return type;
	}
	public synchronized void setType(int type) {
		this.type = type;
	}
	
	
	public synchronized short getChecksumVal() {
		return ChecksumVal;
	}
	public synchronized void setChecksumVal(short checksumVal) {
		ChecksumVal = checksumVal;
	}
	
	public synchronized int getSequenceNumber() {
		return SequenceNumber;
	}
	public synchronized void setSequenceNumber(int sequenceNumber) {
		SequenceNumber = sequenceNumber;
	}
	public synchronized byte[] getData() {
		return data;
	}
	public synchronized void setData(byte[] data) {
		this.data = data;
	}
}
