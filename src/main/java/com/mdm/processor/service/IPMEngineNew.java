package com.mdm.processor.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.sforce.soap.WS_POTMatch.ClassificationsTemp;
import com.sforce.soap.WS_POTMatch.PMMatchedRecord;
import com.sforce.soap.WS_POTMatch.PartyClassifications;
import com.sforce.soap.WS_POTMatch.PartyIdGroup;
import com.sforce.soap.WS_POTMatch.ValidClassifications;

public interface IPMEngineNew {

	public abstract PartyIdGroup[] GetAllPartyIds(String partyClass,
			Integer goldenCopy);

	public abstract ArrayList<PartyClassifications> CheckValidityOfClassifications(
			ClassificationsTemp[] objCLT, ValidClassifications[] objVC,
			String[] SrcpartyIds, String[] partyIds);

	public abstract void ProcessMDMClassifications(String[] partyIds,
			String[] srcPartyIds, String partyClass);

	public abstract PMMatchedRecord[] GetRequiredMatchedRecords(
			PMMatchedRecord[] lobjPMMR, String sMatchType);
	
	public void process();
	
	//Thread safe map to hold latest block size
	public final static Map<String,Integer> latestBlockSize = Collections.synchronizedMap(new HashMap<String,Integer>(1));
	
	
	

}