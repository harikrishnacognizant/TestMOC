package com.mdm.processor.service.impl;

import com.sforce.soap.WS_POTMatch.*;

public class MatchedRecord {
	private String PartyClass;
	private Integer CriteriaId;
	private String GCPartyID;
	private String MDMTempPartyID;
	private String POTNewID;
	private String MatchType;
	private Integer Score;
	private boolean isGoldenCopy;

	public String getPartyClass() {
		return PartyClass;
	}

	public void setPartyClass(String partyClass) {
		PartyClass = partyClass;
	}

	public Integer getCriteriaId() {
		return CriteriaId;
	}

	public void setCriteriaId(Integer criteriaId) {
		CriteriaId = criteriaId;
	}

	public String getGCPartyID() {
		return GCPartyID;
	}

	public void setGCPartyID(String gCPartyID) {
		GCPartyID = gCPartyID;
	}

	public String getMDMTempPartyID() {
		return MDMTempPartyID;
	}

	public void setMDMTempPartyID(String mDMTempPartyID) {
		MDMTempPartyID = mDMTempPartyID;
	}

	public String getPOTNewID() {
		return POTNewID;
	}

	public void setPOTNewID(String pOTNewID) {
		POTNewID = pOTNewID;
	}

	public String getMatchType() {
		return MatchType;
	}

	public void setMatchType(String matchType) {
		MatchType = matchType;
	}

	public Integer getScore() {
		return Score;
	}

	public void setScore(Integer score) {
		Score = score;
	}

	public boolean isGoldenCopy() {
		return isGoldenCopy;
	}

	public void setGoldenCopy(boolean isGoldenCopy) {
		this.isGoldenCopy = isGoldenCopy;
	}

	public MapFields getMdmRecord() {
		return MdmRecord;
	}

	public void setMdmRecord(MapFields mdmRecord) {
		MdmRecord = mdmRecord;
	}

	private MapFields MdmRecord;
}