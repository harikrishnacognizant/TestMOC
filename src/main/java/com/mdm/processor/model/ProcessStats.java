package com.mdm.processor.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProcessStats {
	
	
	
	

	// The Force.com JPA provider only supports annotating fields. Don't annotate
	// the getter and setter properties below.
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
    
	private long totalNumberOfGoldenRecords;
	private long totalNumberOfExactMatchRecords;
	private long totalNumberOfPOTMatchRecords;
	
	

	// TODO: Add additional commented-out sample fields
	
	
	

	public String getId() {
		return id;
	}

	public long getTotalNumberOfGoldenRecords() {
		return totalNumberOfGoldenRecords;
	}

	public void setTotalNumberOfGoldenRecords(long totalNumberOfGoldenRecords) {
		this.totalNumberOfGoldenRecords = totalNumberOfGoldenRecords;
	}

	public long getTotalNumberOfExactMatchRecords() {
		return totalNumberOfExactMatchRecords;
	}

	public void setTotalNumberOfExactMatchRecords(
			long totalNumberOfExactMatchRecords) {
		this.totalNumberOfExactMatchRecords = totalNumberOfExactMatchRecords;
	}

	public long getTotalNumberOfPOTMatchRecords() {
		return totalNumberOfPOTMatchRecords;
	}

	public void setTotalNumberOfPOTMatchRecords(long totalNumberOfPOTMatchRecords) {
		this.totalNumberOfPOTMatchRecords = totalNumberOfPOTMatchRecords;
	}

	public void setId(String id) {
		this.id = id;
	}
}
