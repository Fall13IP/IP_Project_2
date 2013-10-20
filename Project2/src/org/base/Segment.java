package org.base;

import java.io.Serializable;

public class Segment implements Serializable{
	private static final long serialVersionUID = 7830492147281088365L;
	short ChecksumVal,type;
	int  SequenceNumber;
	byte[] data;
	public synchronized short getType() {
		return type;
	}
	public synchronized void setType(short type) {
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
