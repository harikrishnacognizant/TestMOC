package com.mdm.processor.service;

import java.io.IOException;
import java.util.ArrayList;

import com.mdm.processor.model.AttribScoreMap;
import com.sforce.soap.WS_POTMatch.MapFields;
import com.sforce.soap.WS_POTMatch.PMAttributes;
import com.sforce.soap.WS_POTMatch.PMMatchedRecord;

public interface IPMComparison {

	public abstract boolean CreatePotMatchedRecordsByMatchedCriteria(
			String sPartyClass, PMAttributes[] objPMAs,
			MapFields[] objMTFields, MapFields[] objGCFields);

	public abstract boolean CompareGoldenNMDMTempFields(PMAttributes objPMA,
			MapFields objMTFields, MapFields objGCFields) throws IOException;

	public abstract void ShowPotMatchRecords();

	public abstract PMMatchedRecord[] GetGoldenMatchedRecords();

	public abstract PMMatchedRecord[] GetMatchedRecords();

	public abstract void ShowMatchedRecords(PMMatchedRecord[] lobjMR);
	
	public void refreshMatchedRecords();
	
	public boolean CreatePotMatchedRecordsByMatchedCriteriaMap(String sPartyClass,
			ArrayList<AttribScoreMap> latrScoreMap , MapFields[] objMTFields,
			MapFields[] objGCFields) ;

}