package com.mdm.processor.model;

import java.util.ArrayList;

public class AttribScoreMap
{
	 private int crtrId;
	 private ArrayList<String> extAttrbNames;
	 private ArrayList<String> misMatchAttrbNames;
	 private String matchType;
	 private int exactTotalScore;
	 private int mismatchTotalScore;
	 private String partyClass;
	
	 public int getCrtrId() {
		return crtrId;
	}
	public void setCrtrId(int crtrId) {
		this.crtrId = crtrId;
	}
	public ArrayList<String> getExtAttrbNames() {
		return extAttrbNames;
	}
	public void setExtAttrbNames(ArrayList<String> extAttrbNames) {
		this.extAttrbNames = extAttrbNames;
	}
	public ArrayList<String> getMisMatchAttrbNames() {
		return misMatchAttrbNames;
	}
	public void setMisMatchAttrbNames(ArrayList<String> misMatchAttrbNames) {
		this.misMatchAttrbNames = misMatchAttrbNames;
	}
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	public int getExactTotalScore() {
		return exactTotalScore;
	}
	public void setExactTotalScore(int exactTotalScore) {
		this.exactTotalScore = exactTotalScore;
	}
	public int getMismatchTotalScore() {
		return mismatchTotalScore;
	}
	public void setMismatchTotalScore(int mismatchTotalScore) {
		this.mismatchTotalScore = mismatchTotalScore;
	}
	public String getPartyClass() {
		return partyClass;
	}
	public void setPartyClass(String partyClass) {
		this.partyClass = partyClass;
	}
}
