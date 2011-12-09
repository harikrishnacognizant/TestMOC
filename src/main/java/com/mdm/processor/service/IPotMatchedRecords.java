package com.mdm.processor.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.mdm.processor.service.impl.MatchedRecord;
import com.sforce.soap.WS_POTMatch.MapFields;

public interface IPotMatchedRecords {

	public abstract HashMap<String, ArrayList<MatchedRecord>> GetMatchedRecords();

	public abstract void PrepareMatchedSource(String partyClass,
			String UniqueMdmRecordId, MapFields mapMdmTmp, String sGCPartyId,
			MapFields objGCParty, Integer criteriaId, Integer score,
			String MatchType);

	public abstract void PrepareNoMatchedSource(String partyClass,
			String UniqueMdmRecordId, MapFields mapMdmTmp, String sGCPartyId,
			MapFields objGCParty, Integer criteriaId, Integer score,
			String MatchType);

	public abstract boolean IsMdmRecordExist(String UniqueRecordId);

	public abstract void ReWriteRecord(String UniqueRecordId,
			MapFields mapMdmRecord, String partyClass, String MatchType,
			Integer score, String sGCPartyId, Integer criteriaId);

	public abstract void PrintMatchedRecord(String MatchType, Integer score,
			MatchedRecord objMR, String GCName);
	
	public HashMap<String, ArrayList<MatchedRecord>> getObjPMRHashMap();
	
	public void setObjPMRHashMap(
			HashMap<String, ArrayList<MatchedRecord>> objPMRHashMap); 
	

}