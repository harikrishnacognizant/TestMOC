package com.mdm.processor.service.impl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mdm.processor.service.IProcessingMDMData;
import com.sforce.soap.WS_POTMatch.*;
// This is not used 
public class ProcessingMDMData implements IProcessingMDMData {
	/*private HashMap<String, String> PersonPartyIdGroupMap;
	private HashMap<String, String> OrgPartyIdGroupMap;
	public String[] PersonPartyIDs4GC;
	public String[] SrcPersonPartyIDs4GC;
	public String[] OrgPartyIDs4GC;
	public String[] SrcOrgPartyIDs4GC;
	public String[] PersonPartyIDs4PM;
	public String[] SrcPersonPartyIDs4PM;
	public String[] OrgPartyIDs4PM;
	public String[] SrcOrgPartyIDs4PM;
	
	private static final Logger LOGGER = LoggerFactory
	.getLogger(PotMatchedRecords.class);
	
	
	public ProcessingMDMData()
	{
		PersonPartyIdGroupMap=new HashMap<String, String>();
		OrgPartyIdGroupMap=new HashMap<String, String>();
	}
	
	 (non-Javadoc)
	 * @see com.mdm.processor.service.IProcessingMDMData#AssigningProcessedPartyIds(java.lang.String, java.lang.String, java.lang.String[], java.lang.String[])
	 
	@Override
	public void AssigningProcessedPartyIds(String PartyClass,String MatchType,String[]  GCPartyIDs,String[]  SrcPartyIDs)
	{
		if(PartyClass.equals(PMStatic.PERSON_PARTY_CLASS) && MatchType.equals(PMStatic.NO_MATCH_TYPE))
		{
			PersonPartyIDs4GC=GCPartyIDs;
			SrcPersonPartyIDs4GC=SrcPartyIDs;
			
		}
		else if(PartyClass.equals(PMStatic.ORG_PARTY_CLASS) && MatchType.equals(PMStatic.NO_MATCH_TYPE))
		{
			OrgPartyIDs4GC=GCPartyIDs;
			SrcOrgPartyIDs4GC=SrcPartyIDs;
		
		}
		else if(PartyClass.equals(PMStatic.PERSON_PARTY_CLASS) && MatchType.equals(PMStatic.POT_MATCH_TYPE))
		{
			PersonPartyIDs4PM=GCPartyIDs;
			SrcPersonPartyIDs4PM=SrcPartyIDs;
			
		}
		else if(PartyClass.equals(PMStatic.ORG_PARTY_CLASS) && MatchType.equals(PMStatic.POT_MATCH_TYPE))
		{
			OrgPartyIDs4PM=GCPartyIDs;
			SrcOrgPartyIDs4PM=SrcPartyIDs;
		
		}
	
	}
	
	 (non-Javadoc)
	 * @see com.mdm.processor.service.IProcessingMDMData#CreatePersonMappingPartyIds(com.sforce.soap.WS_POTMatch.PartyIdGroup[])
	 
	@Override
	public boolean CreatePersonMappingPartyIds(PartyIdGroup[] aobjPIG)
	{
		if(aobjPIG.length>0)
		{
			for(Integer iCnt=0; iCnt<aobjPIG.length;iCnt++)
			{
				PersonPartyIdGroupMap.put(aobjPIG[iCnt].getSrcPartyID(), aobjPIG[iCnt].getGCPartyID());
			}
			return true;
		}
		return false;
	}
	
	 (non-Javadoc)
	 * @see com.mdm.processor.service.IProcessingMDMData#CreateOrgMappingPartyIds(com.sforce.soap.WS_POTMatch.PartyIdGroup[])
	 
	@Override
	public boolean CreateOrgMappingPartyIds(PartyIdGroup[] aobjPIG)
	{
		if(aobjPIG.length>0)
		{
			for(Integer iCnt=0; iCnt<aobjPIG.length;iCnt++)
			{
				OrgPartyIdGroupMap.put(aobjPIG[iCnt].getSrcPartyID(), aobjPIG[iCnt].getGCPartyID());
			}
			return true;
		}
		return false;
	}
	
	 (non-Javadoc)
	 * @see com.mdm.processor.service.IProcessingMDMData#GetMappedGCPartyID(java.lang.String, java.lang.String)
	 
	@Override
	public String GetMappedGCPartyID(String sPartyClass, String SrcPartyID)
	{
		if(sPartyClass.equals(PMStatic.PERSON_PARTY_CLASS))
		{
			if(PersonPartyIdGroupMap!=null && PersonPartyIdGroupMap.keySet().size()>0)
			{
				return PersonPartyIdGroupMap.get(SrcPartyID);
			}
		}	
		else if(sPartyClass.equals(PMStatic.ORG_PARTY_CLASS))
		{
			if(OrgPartyIdGroupMap!=null && OrgPartyIdGroupMap.keySet().size()>0)
			{
				return OrgPartyIdGroupMap.get(SrcPartyID);
			}
		}	
		return null;
	}
	 (non-Javadoc)
	 * @see com.mdm.processor.service.IProcessingMDMData#GetMappedPersonPartyIds()
	 
	@Override
	public HashMap<String, String> GetMappedPersonPartyIds()
	{
		if(PersonPartyIdGroupMap!=null && PersonPartyIdGroupMap.keySet().size()>0)
		{
			return PersonPartyIdGroupMap;
		}
		return null;
	}
	

	 (non-Javadoc)
	 * @see com.mdm.processor.service.IProcessingMDMData#GetMappedOrgPartyIds()
	 
	@Override
	public HashMap<String, String> GetMappedOrgPartyIds()
	{
		if(OrgPartyIdGroupMap!=null && OrgPartyIdGroupMap.keySet().size()>0)
		{
			return OrgPartyIdGroupMap;
		}
		return null; 
	}*/
}

