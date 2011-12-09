package com.mdm.processor.service.impl;

import java.util.ArrayList;

import com.mdm.processor.model.AttribScoreMap;
import com.mdm.processor.service.IMDMRecordsCount;
import com.sforce.soap.WS_POTMatch.PMAttributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class PMECommanMethods 
{
   private static final Logger LOGGER = LoggerFactory.getLogger(PMECommanMethods.class);
	
   @Autowired
	IMDMRecordsCount objMRC;
   
   public ArrayList<AttribScoreMap> prepareAllAttributeKeyMap(PMAttributes[] objPMAs,String partyClass)
	 {
		 ArrayList<AttribScoreMap> latrScoreMap=null;
		 try {
				
				/*MultiKey objMKCrtr1;
				MultiKey objMKCrtr2;
				MultiKey objMKCrtr3;
				MultiKey objMKCrtr4;
				int iCrtrID=1;*/
				 
				if(objPMAs!=null && objPMAs.length>0)
				{
					latrScoreMap= new ArrayList<AttribScoreMap>();
					ArrayList<String> extAttrbNames;
					ArrayList<String> misMatchAttrbNames;
					
					
				    int totDetermntCnt =objMRC.getPotentialMatchDeterminantCount(partyClass);
					for(int iCnt=1;iCnt<=totDetermntCnt;iCnt++) 
					{
						AttribScoreMap attrbScoresMap=new AttribScoreMap();
						extAttrbNames=new ArrayList<String>();
						misMatchAttrbNames=new ArrayList<String>();
						int iScore=0;
						int iMismatchScore=0;
						
						for (PMAttributes objPMA : objPMAs) 
						{
							if(iCnt==objPMA.getCriteriaID() && objPMA.getPartyClass().equals(partyClass))
							{
								attrbScoresMap.setPartyClass(objPMA.getPartyClass());
					  			attrbScoresMap.setCrtrId(objPMA.getCriteriaID());
								
						  		if(PMStatic.PM_EXACT_MATCH.equalsIgnoreCase(objPMA.getMatchType()))
						  		{
						  			extAttrbNames.add(objPMA.getAttributeName());
						  			iScore = iScore + objPMA.getScore();
						  			attrbScoresMap.setMatchType(PMStatic.PM_EXACT_MATCH);
						  		}
						  		else
						  		{
						  			misMatchAttrbNames.add(objPMA.getAttributeName());
						  			iMismatchScore = iMismatchScore + objPMA.getScore();
						  			attrbScoresMap.setMatchType(PMStatic.PM_MIS_MATCH);
						  		}
						    }
						}
						if(extAttrbNames.size()>0)
						{
							attrbScoresMap.setExtAttrbNames(extAttrbNames);
							attrbScoresMap.setExactTotalScore(iScore);
							if (misMatchAttrbNames.size()>0)
							{
								attrbScoresMap.setMisMatchAttrbNames(misMatchAttrbNames);
								attrbScoresMap.setMismatchTotalScore(iMismatchScore);
							}
						}
							latrScoreMap.add(attrbScoresMap);
					}
				}
							
		 } catch (Exception rExp) {
				LOGGER.info("Problem while processMDM Matched Records : "
						+ rExp.getMessage());
				rExp.printStackTrace();
			}
		 return latrScoreMap;
	 }
}
