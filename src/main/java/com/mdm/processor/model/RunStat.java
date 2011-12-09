package com.mdm.processor.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class RunStat {
	

	
	// The Force.com JPA provider only supports annotating fields. Don't annotate
	// the getter and setter properties below.
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
    
    private String numberOfRecordsProcessed;
	private String numberOfGoldenRecords;
	private String numberOfNoMatchRecords;
	private String numberOfExactMatchRecords;
	private String numberOfPOTMatchRecords;
    
	
	// TODO: Add additional commented-out sample fields
	
	public String getNumberOfRecordsProcessed() {
		return numberOfRecordsProcessed;
	}

	public void setNumberOfRecordsProcessed(String numberOfRecordsProcessed) {
		this.numberOfRecordsProcessed = numberOfRecordsProcessed;
	}

	public String getNumberOfGoldenRecords() {
		return numberOfGoldenRecords;
	}

	public void setNumberOfGoldenRecords(String numberOfGoldenRecords) {
		this.numberOfGoldenRecords = numberOfGoldenRecords;
	}

	public String getNumberOfNoMatchRecords() {
		return numberOfNoMatchRecords;
	}

	public void setNumberOfNoMatchRecords(String numberOfNoMatchRecords) {
		this.numberOfNoMatchRecords = numberOfNoMatchRecords;
	}

	public String getNumberOfExactMatchRecords() {
		return numberOfExactMatchRecords;
	}

	public void setNumberOfExactMatchRecords(String numberOfExactMatchRecords) {
		this.numberOfExactMatchRecords = numberOfExactMatchRecords;
	}

	public String getNumberOfPOTMatchRecords() {
		return numberOfPOTMatchRecords;
	}

	public void setNumberOfPOTMatchRecords(String numberOfPOTMatchRecords) {
		this.numberOfPOTMatchRecords = numberOfPOTMatchRecords;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	
	
	
}
