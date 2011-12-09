package com.mdm.processor.service.impl;

import com.mdm.processor.exception.BaseExceptionFactory;
import com.mdm.processor.service.IPotMatchedRecords;
import com.sforce.soap.WS_POTMatch.*;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class PotMatchedRecords implements IPotMatchedRecords {
	
	private static final Logger LOGGER = LoggerFactory
	.getLogger(PotMatchedRecords.class);

	
	private HashMap<String,ArrayList<MatchedRecord>> objPMRHashMap;
	//private HashMap<String,ArrayList<MatchedRecord>> objExactHashMap;
	//private HashMap<String,ArrayList<MatchedRecord>> objPotMatchHashMap;
	//private HashMap<String,ArrayList<MatchedRecord>> objNoMatchHashMap;
	
	public HashMap<String, ArrayList<MatchedRecord>> getObjPMRHashMap() {
		return objPMRHashMap;
	}
	public void setObjPMRHashMap(
			HashMap<String, ArrayList<MatchedRecord>> objPMRHashMap) {
		this.objPMRHashMap = objPMRHashMap;
	}
	/* (non-Javadoc)
	 * @see com.mdm.processor.service.IPotMatchedRecords#GetMatchedRecords()
	 */
	@Override
	public HashMap<String,ArrayList<MatchedRecord>> GetMatchedRecords()
	{
		if(objPMRHashMap!=null && objPMRHashMap.size()>0)
		{
			LOGGER.info("Matched Records size"+objPMRHashMap.size()); 
			//PMStatic.ReadLine();
			return objPMRHashMap;
		}
		return null;
	}
	public void init()
	{
		objPMRHashMap=new HashMap<String,ArrayList<MatchedRecord>>();
	}
	
	  	
	   /* (non-Javadoc)
	 * @see com.mdm.processor.service.IPotMatchedRecords#PrepareMatchedSource(java.lang.String, java.lang.String, com.sforce.soap.WS_POTMatch.MapFields, java.lang.String, com.sforce.soap.WS_POTMatch.MapFields, java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public void PrepareMatchedSource(String  partyClass,String  UniqueMdmRecordId, MapFields mapMdmTmp,String sGCPartyId ,MapFields objGCParty,Integer criteriaId, Integer score,String MatchType)
	   {
		   if(IsMdmRecordExist(UniqueMdmRecordId))
	        {
			   LOGGER.info("PMR^^^going 2 ReWriteRecord^^^^^^ MatchType:"+ MatchType +"PartyID: "+mapMdmTmp.getPartyID() +"-> "+score);
			   ReWriteRecord(UniqueMdmRecordId, mapMdmTmp, partyClass, MatchType, score, sGCPartyId, criteriaId);
	         
	        }
	        else
	        {
			   ArrayList<MatchedRecord> objArrLst=new ArrayList<MatchedRecord>();
			   MatchedRecord objMR=new MatchedRecord();
	           objMR.setPartyClass(partyClass) ;
	           objMR.setCriteriaId(criteriaId);
	           objMR.setScore(score);
	           objMR.setMdmRecord(mapMdmTmp);
	           objMR.setGCPartyID(sGCPartyId);
	           objMR.setMatchType(MatchType);
	           LOGGER.info("PMR^^^^^^^^^ MatchType:"+ MatchType +"PartyID: "+mapMdmTmp.getPartyID() +"-> "+score);
	           objArrLst.add(objMR);
	           objPMRHashMap.put(UniqueMdmRecordId,objArrLst);
	        }
	   } 
	   
	   /* (non-Javadoc)
	 * @see com.mdm.processor.service.IPotMatchedRecords#PrepareNoMatchedSource(java.lang.String, java.lang.String, com.sforce.soap.WS_POTMatch.MapFields, java.lang.String, com.sforce.soap.WS_POTMatch.MapFields, java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public void PrepareNoMatchedSource(String  partyClass,String  UniqueMdmRecordId, MapFields mapMdmTmp,String sGCPartyId ,MapFields objGCParty,Integer criteriaId, Integer score,String MatchType)
	   {
		   if(!IsMdmRecordExist(UniqueMdmRecordId))
	        {
		   ArrayList<MatchedRecord> objArrLst=new ArrayList<MatchedRecord>();
		   MatchedRecord objMR=new MatchedRecord();
		   objMR.setPartyClass(partyClass) ;
           objMR.setCriteriaId(criteriaId);
           objMR.setScore(score);
           objMR.setMdmRecord(mapMdmTmp);
           objMR.setGCPartyID(sGCPartyId);
           objMR.setMatchType(MatchType);
           LOGGER.info("PMR^^^^^^^^^ "+mapMdmTmp.getPartyID() +"-> "+score);
           objArrLst.add(objMR);
           objPMRHashMap.put(UniqueMdmRecordId,objArrLst);
	       }
	   }
	   
	   /* (non-Javadoc)
	 * @see com.mdm.processor.service.IPotMatchedRecords#IsMdmRecordExist(java.lang.String)
	 */
	@Override
	public boolean IsMdmRecordExist(String UniqueRecordId)
	   {
	      if(objPMRHashMap!=null && objPMRHashMap.size()>0)
	      {
	        return objPMRHashMap.containsKey(UniqueRecordId);         
	      }
	      
	     return false; 
	   }
	   
	   /* (non-Javadoc)
	 * @see com.mdm.processor.service.IPotMatchedRecords#ReWriteRecord(java.lang.String, com.sforce.soap.WS_POTMatch.MapFields, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public void ReWriteRecord(String UniqueRecordId,MapFields mapMdmRecord,String  partyClass,String MatchType,Integer score,String sGCPartyId,Integer criteriaId)
	   {
	     if(objPMRHashMap!=null && objPMRHashMap.size()>0)
	      {
	    	 	
	            ArrayList<MatchedRecord> lstMR = objPMRHashMap.get(UniqueRecordId);
	            ArrayList<MatchedRecord> lstMRNew = new ArrayList<MatchedRecord>();
	            ArrayList<MatchedRecord> lstMRPotNew = new ArrayList<MatchedRecord>();
	            boolean isPotMatchRecord=false;
	            LOGGER.info("ReWriteRecord :lstMR ------------------- "+lstMR.size());
	            
	            for(MatchedRecord objMR : lstMR)
	            {
	                        
	            	 if( "NoMatch".equals(MatchType) &&  "NoMatch".equals(objMR.getMatchType()))
		                {
		                	LOGGER.info("NM& NM -- old Score:"+objMR.getScore()+" New Score: "+score);
		                	//ArrayList<MatchedRecord> lstMRNoMatch = objPMRHashMap.get(UniqueRecordId);
		                	//MatchedRecord objMRNoMatch = lstMRNoMatch.get(0);
		                	if(score>objMR.getScore())
		                	{
			                	objPMRHashMap.remove(UniqueRecordId); 
	//		                    objMR.MatchType=MatchType;
	//		                    objMR.Score=score; 
	//		                    objMR.GCPartyID =sGCPartyId;
	//		                    objMR.CriteriaId =criteriaId;
	//		                    
			     	            objMR.setCriteriaId(criteriaId);
			     	            objMR.setScore(score);
			     	            objMR.setGCPartyID(sGCPartyId);
			     	            objMR.setMatchType(MatchType);
			                    
			                    lstMRNew.add(objMR);
			                    objPMRHashMap.put(UniqueRecordId, lstMRNew); 
		                	}
		                }
	            	 else if( "PotMatch".equals(MatchType) &&  "NoMatch".equals(objMR.getMatchType()))
	                {
	                	LOGGER.info("PM && NM");
	                	objPMRHashMap.remove(UniqueRecordId); 
//	                    objMR.MatchType=MatchType;
//	                    objMR.Score=score; 
//	                    objMR.GCPartyID =sGCPartyId;
//	                    objMR.CriteriaId =criteriaId;
//	                    
	     	            objMR.setCriteriaId(criteriaId);
	     	            objMR.setScore(score);
	     	            objMR.setGCPartyID(sGCPartyId);
	     	            objMR.setMatchType(MatchType);
	                    
	                    lstMRNew.add(objMR);
	                    objPMRHashMap.put(UniqueRecordId, lstMRNew);                  
	                }
	                else if("ExactMatch".equals(MatchType) &&  "NoMatch".equals(objMR.getMatchType()))
	                {
	                	LOGGER.info("EM && NM");
	                    objPMRHashMap.remove(UniqueRecordId); 
	                    objMR.setCriteriaId(criteriaId);
	     	            objMR.setScore(score);
	     	            objMR.setGCPartyID(sGCPartyId);
	     	            objMR.setMatchType(MatchType);
	                    lstMRNew.add(objMR);
	                    objPMRHashMap.put(UniqueRecordId, lstMRNew);                      
	                }
	                else if("ExactMatch".equals(MatchType) && "PotMatch".equals(objMR.getMatchType()))
	                {
	                	LOGGER.info("EM && PM");
	                    objPMRHashMap.remove(UniqueRecordId); 
	                    objMR.setCriteriaId(criteriaId);
	     	            objMR.setScore(score);
	     	            objMR.setGCPartyID(sGCPartyId);
	     	            objMR.setMatchType(MatchType);
	                    lstMRNew.add(objMR);
	                    objPMRHashMap.put(UniqueRecordId, lstMRNew);                      
	                }
	                else if("PotMatch".equals(MatchType) && "PotMatch".equals(objMR.getMatchType()))
	                {
	                	LOGGER.info("PM && PM");
	                    MatchedRecord objNMR= new MatchedRecord();
	                    
	                    objNMR.setPartyClass(partyClass) ;
	                    objNMR.setCriteriaId(criteriaId);
	                    objNMR.setScore(score);
	                    objNMR.setMdmRecord(mapMdmRecord);
	                    objNMR.setGCPartyID(sGCPartyId);
	                    objNMR.setMatchType(MatchType);
	                    objNMR.setMDMTempPartyID(mapMdmRecord.getPartyID());
	                    
	                    
	                    lstMRPotNew = objPMRHashMap.get(UniqueRecordId);
	                    LOGGER.info("PM && PM Old rcrds: "+lstMRPotNew);
	                    LOGGER.info("PM && PM current"+objNMR);
	                    lstMRPotNew.add(objNMR);
	                    LOGGER.info("PM && PM new potset:"+lstMRPotNew);
	                    isPotMatchRecord=true;
	                    break; 
	                    /*lstMRNew.add(objNMR);
	                    mapMatchedRcrds.remove(UniqueRecordId);                    
	                    mapMatchedRcrds.put(UniqueRecordId, lstMRNew);*/   
	                    
	                }
	                else if("ExactMatch".equals(MatchType) && "ExactMatch".equals(objMR.getMatchType()))
	                {
	                	LOGGER.info("EM EM --- Checking for any other criteria UniqueRecordId:"+UniqueRecordId +" MatchType:"+MatchType+ "   objMR.getMatchType()" +objMR.getMatchType() +" score:"+score+" gcPartyId:"+sGCPartyId);
	                	
	                }
	                else 
	                {
	                	if(UniqueRecordId==null)
	                	{
	                	   	String strExp= partyClass + "for the Match:"+ MatchType+ ": =========Data is not in correct format : "+sGCPartyId;
		                	throw BaseExceptionFactory.getInstance(new Exception(strExp)); 
		             
	                	}
	                	LOGGER.info("EMPMNM  EM PM NM Checking for any other criteria UniqueRecordId:"+UniqueRecordId +" MatchType:"+MatchType+"   objMR.getMatchType()" +objMR.getMatchType() + " score:"+score+" gcPartyId:"+sGCPartyId);
	                	 
	                	
	                }
	                //}
	            }
	            if(isPotMatchRecord)
	            {
	            	objPMRHashMap.remove(UniqueRecordId);
	            	objPMRHashMap.put(UniqueRecordId, lstMRPotNew);
	            	LOGGER.info("PM UniqueRecordId::"+ UniqueRecordId+" PM && PM new potset:" +objPMRHashMap.get(UniqueRecordId));
	            }
	            LOGGER.info("ReWriteRecord :++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	        }
	     
	   }
	   
	   
	   
	   /* (non-Javadoc)
	 * @see com.mdm.processor.service.IPotMatchedRecords#PrintMatchedRecord(java.lang.String, java.lang.Integer, com.mdm.processor.service.MatchedRecord, java.lang.String)
	 */
	@Override
	public void PrintMatchedRecord(String MatchType,Integer score, MatchedRecord objMR, String GCName)
	   {
	  //   LOGGER.info("Match -check "+MatchType+ " score "+score+ " GCName "+GCName);
	   //  LOGGER.info("Match Record "+objMR.MatchType+" name "+objMR.MdmRecord.getFields(8).getFieldValue()+" score "+objMR.Score+" GCPartyID "+objMR.GCPartyID+" CriteriaId "+objMR.CriteriaId+" GCName "+GCName);
	   }
	   
	   
	  
}

