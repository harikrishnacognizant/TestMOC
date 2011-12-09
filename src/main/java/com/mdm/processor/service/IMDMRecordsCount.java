package com.mdm.processor.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.sforce.soap.WS_POTMatch.SoapConnection;

public interface IMDMRecordsCount {

	//Thread safe map to hold POT match engine start up record set  
	public final static Map<String,Integer> startUpRecordSet = Collections.synchronizedMap(new HashMap<String,Integer>(1));
	
	//Thread safe map to hold POT match engine updated record set
	public final static Map<String,Integer> upToDateRecordSet = Collections.synchronizedMap(new HashMap<String,Integer>(1));
	
	public abstract Integer gettingPartyAssociationsCount();

	public abstract void showDataVolumeinSFDC();
	
	public long getTotal();
	
	public void reloadStartUpMap() ;
	 public long getExactMatchCount();
	 public int getAssociationTempCount();
	 public int getClassificationTempCount();
	 public long getCurrentTotal();
	 public int getPotentialMatchDeterminantCount(String PartyClass);
	 public int getPotentialMatchDeterminantCount(String PartyClass,String MatchType);


}