package com.mdm.processor.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ClusteredColumnData {
	
	
	// The Force.com JPA provider only supports annotating fields. Don't annotate
	// the getter and setter properties below.
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
    
    
    private long incrementalGoldenCopy;
	private long incrementalExactCopy;
	private long incrementalPOTCopy;
	
	public long getSequence() {
		return sequence;
	}
	public void setSequence(long sequence) {
		this.sequence = sequence;
	}
	private long sequence;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getIncrementalGoldenCopy() {
		return incrementalGoldenCopy;
	}
	public void setIncrementalGoldenCopy(long incrementalGoldenCopy) {
		this.incrementalGoldenCopy = incrementalGoldenCopy;
	}
	public long getIncrementalExactCopy() {
		return incrementalExactCopy;
	}
	public void setIncrementalExactCopy(long incrementalExactCopy) {
		this.incrementalExactCopy = incrementalExactCopy;
	}
	public long getIncrementalPOTCopy() {
		return incrementalPOTCopy;
	}
	public void setIncrementalPOTCopy(long incrementalPOTCopy) {
		this.incrementalPOTCopy = incrementalPOTCopy;
	}

}
