package com.mdm.processor.service.impl;

import java.util.ArrayList;

import com.mdm.processor.service.IMDMWebServiceAccess;
import com.mdm.processor.service.IPrepareMultiKeyMap;
import com.sforce.soap.WS_POTMatch.PMAttributes;
import com.sforce.soap.WS_POTMatch.SoapConnection;

//import org.apache.commons.collections.keyvalue.MultiKey;
//import org.apache.commons.collections.map.MultiKeyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class PrepareMultiKeyMap implements IPrepareMultiKeyMap 
{

	/*@Autowired
	IMDMWebServiceAccess objWSAccess;

	private static final Logger LOGGER = LoggerFactory
	.getLogger(PrepareMultiKeyMap.class);
	
	public MultiKeyMap objMKMAttrib;

	
	 public String PrepareQuery4GCPersons()
	 {
		 return "";
	 }
	 
	 public String PrepareQuery4GCOrgs()
	 {
		 return "";
	 } 
	 
	 public void CheckingMultiKeyMap()
	 {
		 MultiKeyMap objMKM
	 }
	
	 public class AttribScoreMap
	 {
		 public String partyID;
		 public int crtrId;
		 public ArrayList<String> attrbNames;
		 public int totalScore;
		 public String partyClass;
	 }
	 
	 
	 
	 public ArrayList<AttribScoreMap> PrepareAllAttributeKeyMap()
	 {
		 ArrayList<AttribScoreMap> latrScoreMap=null;
		 try {
				SoapConnection wsMDMBinding = objWSAccess.getWSPotMatchEngineConnection();
				PMAttributes[] objPMAs = wsMDMBinding.getAllAttributes();
				
				MultiKey objMKCrtr1;
				MultiKey objMKCrtr2;
				MultiKey objMKCrtr3;
				MultiKey objMKCrtr4;
				int iCrtrID=1;

				ArrayList<String> attrbNames;
				
				if(objPMAs!=null && objPMAs.length>0)
				{
					latrScoreMap= new ArrayList<AttribScoreMap>();
					for(int iCnt=1;iCnt<5;iCnt++) // here 5 is temporary check - need to change 4 no of criterias
					{
						AttribScoreMap attrbScoresMap=new AttribScoreMap();
						attrbNames=new ArrayList<String>();
						//double iScore=0;
						int iScore=0;
						for (PMAttributes objPMA : objPMAs) 
						{
							if(iCnt==objPMA.getCriteriaID())
							{
								attrbScoresMap.crtrId=objPMA.getCriteriaID();
						  		attrbNames.add(objPMA.getAttributeName());
						  		iScore = iScore + objPMA.getScore();
						  		attrbScoresMap.partyClass=objPMA.getPartyClass();
						    }
						}
						attrbScoresMap.attrbNames =attrbNames;
						attrbScoresMap.totalScore = iScore;
						latrScoreMap.add(attrbScoresMap);
					}
				}
							
		 } catch (Exception rExp) {
				LOGGER.info("Problem while processMDM Matched Records : "
						+ rExp.getMessage());
				rExp.printStackTrace();
			}
		 return latrScoreMap;
	 }*/
	 
	/* public ArrayList<AttribScoreMap> PrepareAllAttributeKeyMap()
	 {
		 ArrayList<AttribScoreMap> latrScoreMap=null;
		 try {
				SoapConnection wsMDMBinding = objWSAccess.getWSPotMatchEngineConnection();
				PMAttributes[] objPMAs = wsMDMBinding.getAllAttributes();
				
				MultiKey objMKCrtr1;
				MultiKey objMKCrtr2;
				MultiKey objMKCrtr3;
				MultiKey objMKCrtr4;
				int iCrtrID=1;

				ArrayList<String> attrbNames;
				
				if(objPMAs!=null && objPMAs.length>0)
				{
					latrScoreMap= new ArrayList<AttribScoreMap>();
					for(int iCnt=1;iCnt<5;iCnt++) // here 5 is temporary check - need to change 4 no of criterias
					{
						AttribScoreMap attrbScoresMap=new AttribScoreMap();
						attrbNames=new ArrayList<String>();
						int iScore=0;
						for (PMAttributes objPMA : objPMAs) 
						{
							if(iCnt==objPMA.getCriteriaID())
							{
								attrbScoresMap.crtrId=objPMA.getCriteriaID();
						  		attrbNames.add(objPMA.getAttributeName());
						  		iScore = iScore + objPMA.getScore();
						  		attrbScoresMap.partyClass=objPMA.getPartyClass();
						    }
						}
						attrbScoresMap.attrbNames =attrbNames;
						attrbScoresMap.totalScore = iScore;
						latrScoreMap.add(attrbScoresMap);
					}
				}
							
		 } catch (Exception rExp) {
				LOGGER.info("Problem while processMDM Matched Records : "
						+ rExp.getMessage());
				rExp.printStackTrace();
			}
		 return latrScoreMap;
	 }*/
}
