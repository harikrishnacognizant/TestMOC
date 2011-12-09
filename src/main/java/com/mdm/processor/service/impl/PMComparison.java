package com.mdm.processor.service.impl;

import com.mdm.processor.model.AttribScoreMap;
import com.mdm.processor.exception.BaseExceptionFactory;
import com.mdm.processor.exception.BaseExceptionUtil;
import com.mdm.processor.service.IPMComparison;
import com.mdm.processor.service.IPotMatchedRecords;

import com.sforce.soap.WS_POTMatch.*;
import com.sforce.ws.ConnectionException;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import java.util.List;
import java.io.IOException;
import java.lang.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class PMComparison implements IPMComparison {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PMComparison.class);

	// TODO : Work on eliminating this variable
	//Integer iScore = 0;

	@Autowired
	IPotMatchedRecords objPMR;

	public PMComparison() {
		//objPMR = new PotMatchedRecords();
	}

	public class CheckScore {
		public String CriteriaID;
		public String PartyID;
		public Integer Score;

	}
	
	
	public void refreshMatchedRecords(){		
		objPMR.setObjPMRHashMap(new HashMap<String,ArrayList<MatchedRecord>>());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mdm.processor.service.IPMComparison#
	 * CreatePotMatchedRecordsByMatchedCriteria(java.lang.String,
	 * com.sforce.soap.WS_POTMatch.PMAttributes[],
	 * com.sforce.soap.WS_POTMatch.MapFields[],
	 * com.sforce.soap.WS_POTMatch.MapFields[])
	 */
	@Override
	public boolean CreatePotMatchedRecordsByMatchedCriteria(String sPartyClass,
			PMAttributes[] objPMAs, MapFields[] objMTFields,
			MapFields[] objGCFields) {
		boolean bRecordMatched = false;
		Integer iCrtID = 0;
		Integer iScore = 0;
		try {
			if (objPMAs != null && objMTFields != null && objGCFields != null) {
				for (MapFields objMTP : objMTFields) {
					for (MapFields objGCP : objGCFields) {
						for (PMAttributes objPMA : objPMAs) {
							//LOGGER.info("$$$$$$$$$$$$$$ Starting Score:  "+iScore);
							// LOGGER.info("sPartyClass: "+ sPartyClass+"objPMA.getPartyClass(): "+objPMA.getPartyClass());							// 
							// LOGGER.info("Hello My Shivababa, could u pls check what's happening here ");

							if (objPMA.getPartyClass().equals(sPartyClass)
									&& objPMA.getScore() > 0) // &&
																// objPMA.getCriteriaID()==2)
							{
								// LOGGER.info("I am checking CompareGoldenNMDMTempFields  ......");
								bRecordMatched = CompareGoldenNMDMTempFields(
										objPMA, objGCP, objMTP);
								// LOGGER.info("I am checking CompareGoldenNMDMTempFields: bRecordMatched"+bRecordMatched);
								if (bRecordMatched) {
									iScore = iScore + objPMA.getScore();
									iCrtID = objPMA.getCriteriaID();
								}
								// LOGGER.info("after one attrib iScore: "+iScore);
								if (iScore == 100) {
									break;
								}
							}
						}
						// LOGGER.info("$$$$$$$$$$$$$$ "+iScore);	
						if (iScore > 0) {
							if (iScore > 94)
								objPMR.PrepareMatchedSource(sPartyClass,
										objMTP.getPartyID(), objMTP,
										objGCP.getPartyID(), objGCP, iCrtID,
										iScore, "ExactMatch");
							else if (iScore > 64 && iScore < 95)
								objPMR.PrepareMatchedSource(sPartyClass,
										objMTP.getPartyID(), objMTP,
										objGCP.getPartyID(), objGCP, iCrtID,
										iScore, "PotMatch");
							else
								objPMR.PrepareMatchedSource(sPartyClass,
										objMTP.getPartyID(), objMTP,
										objGCP.getPartyID(), objGCP, iCrtID,
										iScore, "NoMatch");

						} else {
							// LOGGER.info('$$$$$$$$$$$$$$ NOMatch
							// sag'+CrtId+'-> '+score);
							objPMR.PrepareNoMatchedSource(sPartyClass,
									objMTP.getPartyID(), objMTP,
									objGCP.getPartyID(), objGCP, iCrtID,
									iScore, "NoMatch");
						}
						// System.in.read();
						iScore = 0;
					}
				}
			}
		} catch (IOException ioExp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(ioExp));
			throw BaseExceptionFactory.getInstance(ioExp);
		} catch (Exception exp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(exp));
			throw BaseExceptionFactory.getInstance(exp);
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mdm.processor.service.IPMComparison#
	 * CreatePotMatchedRecordsByMatchedCriteria(java.lang.String,
	 * com.sforce.soap.WS_POTMatch.PMAttributes[],
	 * com.sforce.soap.WS_POTMatch.MapFields[],
	 * com.sforce.soap.WS_POTMatch.MapFields[])
	 */
	
	@Override
	public boolean CreatePotMatchedRecordsByMatchedCriteriaMap(String sPartyClass,
			ArrayList<AttribScoreMap> latrScoreMap , MapFields[] objMTFields,
			MapFields[] objGCFields) {
		boolean bRecordMatched = false;
		boolean bFoundExactMatch=false;
		Integer iCrtID = -1;
		Integer iPrvCrtID =-1; 
		Integer iScore = 0;
		try {
			if (latrScoreMap != null && objMTFields != null && objGCFields != null) {
				for (MapFields objMTP : objMTFields) {
					bFoundExactMatch=false;
					for (MapFields objGCP : objGCFields) {
						for (AttribScoreMap objASM : latrScoreMap) {
							//LOGGER.info("$$$$$$$$$$$$$$ Starting Score:  "+iScore);
							// LOGGER.info("sPartyClass: "+
							// sPartyClass+"objPMA.getPartyClass(): "+objPMA.getPartyClass());
							// LOGGER.info("PartyClass:"+objPMA.getPartyClass()+
							// "getScore():"+objPMA.getScore()+
							// "objPMA.getCriteriaID():"+objPMA.getCriteriaID());
							// LOGGER.info("Hello My Shivababa, could u pls check what's happening here ");
							if (objASM.getPartyClass().equals(sPartyClass)
									&& objASM.getExactTotalScore()> 0 && 
									objASM.getExtAttrbNames()!=null && objASM.getExtAttrbNames().size()>0) // &&
																// objPMA.getCriteriaID()==2)
							{
								
								// LOGGER.info("I am checking CompareGoldenNMDMTempFields  ......");
								bRecordMatched = CompareGoldenNMDMTempFieldsMap(
										objASM.getExtAttrbNames(), objGCP, objMTP);
								// LOGGER.info("I am checking CompareGoldenNMDMTempFields: bRecordMatched"+bRecordMatched);
								
									
							//If record found with Criteria it proceeds to check Matching Criteria.	
							if (bRecordMatched) 
							{
								iScore = objASM.getExactTotalScore();
								//if(true) // Here need to check Secondary Attribute
								
									// LOGGER.info("I am checking CompareGoldenNMDMTempFields  ...4 mismatch...");
								boolean	bMisMatchRecord; 
								if(objASM.getMisMatchAttrbNames()!=null)
								{
									bMisMatchRecord = CompareMisMatchGoldenNMDMTempFieldsMap(
											objASM.getMisMatchAttrbNames(), objGCP, objMTP);
									
									if(!bMisMatchRecord)
									{
										iScore = iScore+objASM.getMismatchTotalScore();
									}
								}
								
								LOGGER.info("$$$$$$$$$$$$$$  objASM.exactTotalScore "+objASM.getExactTotalScore());
									
								if (iScore > 0) {
									
									if (iScore > 94)
									{
										objPMR.PrepareMatchedSource(sPartyClass,
												objMTP.getPartyID(), objMTP,
												objGCP.getPartyID(), objGCP, objASM.getCrtrId(),
												iScore, "ExactMatch");
										bFoundExactMatch=true;
									      break;
									}
									else if (iScore > 64 && iScore < 95)
										objPMR.PrepareMatchedSource(sPartyClass,
												objMTP.getPartyID(), objMTP,
												objGCP.getPartyID(), objGCP, objASM.getCrtrId(),
												iScore, "PotMatch");
									else
										objPMR.PrepareMatchedSource(sPartyClass,
												objMTP.getPartyID(), objMTP,
												objGCP.getPartyID(), objGCP, objASM.getCrtrId(),
												iScore, "NoMatch");
		
								}
								else
								{
									LOGGER.info("$$$$$$ iScore<0 $$$$$$$$:"+iScore+"objMTP.getPartyID()"+objMTP.getPartyID()+ "$$$$$$$$ objGCP.getPartyID()"+objGCP.getPartyID() );
								}
							}
							else
							{
								 LOGGER.info("objMTP.getPartyID()"+objMTP.getPartyID()+ "$$$$$$$$ objGCP.getPartyID()"+objGCP.getPartyID() +"$$$$$$ NOMatch"+iScore);
								// sag'+CrtId+'-> '+score);
							 	objPMR.PrepareNoMatchedSource(sPartyClass,objMTP.getPartyID(), objMTP,objGCP.getPartyID(), objGCP, iCrtID,iScore, "NoMatch");
							}
							iScore = 0;
						}
				}
						if(bFoundExactMatch)
						{
							iScore = 0;
							break;
						}
     		}
		}
        }		
		} catch (IOException ioExp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(ioExp));
			throw BaseExceptionFactory.getInstance(ioExp);
		} catch (Exception exp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(exp));
			throw BaseExceptionFactory.getInstance(exp);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mdm.processor.service.IPMComparison#CompareGoldenNMDMTempFields(com
	 * .sforce.soap.WS_POTMatch.PMAttributes,
	 * com.sforce.soap.WS_POTMatch.MapFields,
	 * com.sforce.soap.WS_POTMatch.MapFields)
	 */
	@Override
	public boolean CompareGoldenNMDMTempFields(PMAttributes objPMA,
			MapFields objMTFields, MapFields objGCFields) throws IOException {
		boolean iFoundIt = false;
		
		for (MapField objMMF : objMTFields.getFields()) {
			for (MapField objGCMF : objGCFields.getFields()) {
				// LOGGER.info("objPMA.getAttributeName():"+
				// objPMA.getAttributeName()+" objMMF.getFieldName():"+
				// objMMF.getFieldName()+"  objMMF.getFieldValue():"+objMMF.getFieldValue()
				// +"  objGCMF.getFieldName():"+ objGCMF.getFieldName()
				// +"  objGCMF.getFieldValue():"+objGCMF.getFieldValue());

				if (objPMA.getAttributeName().equals(objMMF.getFieldName())
						&& objPMA.getAttributeName().equals(
								objGCMF.getFieldName())
						&& objMMF.getFieldValue().equals(
								objGCMF.getFieldValue())) {
					LOGGER.info("objPMA.getAttributeName():"
							+ objPMA.getAttributeName()
							+ " objMMF.getFieldName():" + objMMF.getFieldName()
							+ "  objMMF.getFieldValue():"
							+ objMMF.getFieldValue()
							+ "  objGCMF.getFieldName():"
							+ objGCMF.getFieldName()
							+ "  objGCMF.getFieldValue():"
							+ objGCMF.getFieldValue());
					// PMStatic.ReadLine();
					iFoundIt = true;
					break;
				}
			}
			if (iFoundIt)
				break;
		}
		return iFoundIt;
	}

	//@Override
	public boolean CompareGoldenNMDMTempFieldsMap(ArrayList<String> attrbNames,
			MapFields objMTFields, MapFields objGCFields) throws IOException {
		boolean iFoundIt = false;
        int iattrbFound =0;
        boolean bAttrbFound=false;
        
		for (MapField objMMF : objMTFields.getFields()) {
			for (MapField objGCMF : objGCFields.getFields()) {
				for(int iCnt=0;iCnt<attrbNames.size();iCnt++)
				{
				/*	LOGGER.info("attrbNames("+iCnt+"):"
							+ attrbNames.get(iCnt)
							+ " objMMF.getFieldName():" + objMMF.getFieldName()
							+ "  objMMF.getFieldValue():"
							+ objMMF.getFieldValue()
							+ "  objGCMF.getFieldName():"
							+ objGCMF.getFieldName()
							+ "  objGCMF.getFieldValue():"
							+ objGCMF.getFieldValue());*/
				if(objGCMF.getFieldName()!=null && attrbNames.get(iCnt)!=null  && objGCMF.getFieldValue()!=null &&
						 objMMF.getFieldName()!=null && objMMF.getFieldValue()!=null)
				if (attrbNames.get(iCnt).equalsIgnoreCase(objMMF.getFieldName())
						&& attrbNames.get(iCnt).equals(
								objGCMF.getFieldName())
						&& objMMF.getFieldValue().equals(
								objGCMF.getFieldValue())) {
					LOGGER.info("attrbNames("+iCnt+"):"
							+ attrbNames.get(iCnt)
							+ " objMMF.getFieldName():" + objMMF.getFieldName()
							+ "  objMMF.getFieldValue():"
							+ objMMF.getFieldValue()
							+ "  objGCMF.getFieldName():"
							+ objGCMF.getFieldName()
							+ "  objGCMF.getFieldValue():"
							+ objGCMF.getFieldValue());
					// PMStatic.ReadLine();
					//iFoundIt = true;
					//break;
					iattrbFound++;
					//bAttrbFound=true;
					break;
				}
			}
				/*if(bAttrbFound)
					break;*/
		 }
			if (attrbNames.size()==iattrbFound)
			{
				iFoundIt=true;
				break;
			}
		}
		return iFoundIt;
	}
	
	//@Override
	public boolean CompareMisMatchGoldenNMDMTempFieldsMap(ArrayList<String> attrbNames,
			MapFields objMTFields, MapFields objGCFields) throws IOException {
		boolean iFoundIt = false;
        int iattrbFound =0;
        boolean bAttrbFound=false;
        
		for (MapField objMMF : objMTFields.getFields()) {
			for (MapField objGCMF : objGCFields.getFields()) {
				for(int iCnt=0;iCnt<attrbNames.size();iCnt++)
				{
				/*	LOGGER.info("attrbNames("+iCnt+"):"
							+ attrbNames.get(iCnt)
							+ " objMMF.getFieldName():" + objMMF.getFieldName()
							+ "  objMMF.getFieldValue():"
							+ objMMF.getFieldValue()
							+ "  objGCMF.getFieldName():"
							+ objGCMF.getFieldName()
							+ "  objGCMF.getFieldValue():"
							+ objGCMF.getFieldValue());*/
					if(objGCMF.getFieldName()!=null && attrbNames.get(iCnt)!=null  && objGCMF.getFieldValue()!=null &&
							 objMMF.getFieldName()!=null && objMMF.getFieldValue()!=null)
				if (attrbNames.get(iCnt).equalsIgnoreCase(objMMF.getFieldName())
						&& attrbNames.get(iCnt).equals(
								objGCMF.getFieldName())
						&& objMMF.getFieldValue().equals(
								objGCMF.getFieldValue())) {
					LOGGER.info("attrbNames("+iCnt+"):"
							+ attrbNames.get(iCnt)
							+ " objMMF.getFieldName():" + objMMF.getFieldName()
							+ "  objMMF.getFieldValue():"
							+ objMMF.getFieldValue()
							+ "  objGCMF.getFieldName():"
							+ objGCMF.getFieldName()
							+ "  objGCMF.getFieldValue():"
							+ objGCMF.getFieldValue());
					// PMStatic.ReadLine();
					//iFoundIt = true;
					//break;
					iattrbFound++;
					//bAttrbFound=true;
					break;
				}
			}
				/*if(bAttrbFound)
					break;*/
		 }
			if (attrbNames.size()==iattrbFound)
			{
				iFoundIt=true;
				break;
			}
		}
		return iFoundIt;
	}
	
	/*public boolean CompareMisMatchGoldenNMDMTempFieldsMap(ArrayList<String> attrbNames,
			MapFields objMTFields, MapFields objGCFields) throws IOException {
		boolean iFoundIt = false;

		for (MapField objMMF : objMTFields.getFields()) {
			for (MapField objGCMF : objGCFields.getFields()) {
				for(int iCnt=0;iCnt<attrbNames.size();iCnt++)
					LOGGER.info("attrbNames("+iCnt+"):"
							+ attrbNames.get(iCnt)
							+ " objMMF.getFieldName():" + objMMF.getFieldName()
							+ "  objMMF.getFieldValue():"
							+ objMMF.getFieldValue()
							+ "  objGCMF.getFieldName():"
							+ objGCMF.getFieldName()
							+ "  objGCMF.getFieldValue():"
							+ objGCMF.getFieldValue());
					
				if (attrbNames.get(iCnt).equals(objMMF.getFieldName())
						&& attrbNames.get(iCnt).equals(
								objGCMF.getFieldName())
						&& objMMF.getFieldValue().equals(
								objGCMF.getFieldValue())) {
					LOGGER.info("attrbNames("+iCnt+"):"
							+ attrbNames.get(iCnt)
							+ " objMMF.getFieldName():" + objMMF.getFieldName()
							+ "  objMMF.getFieldValue():"
							+ objMMF.getFieldValue()
							+ "  objGCMF.getFieldName():"
							+ objGCMF.getFieldName()
							+ "  objGCMF.getFieldValue():"
							+ objGCMF.getFieldValue());
					// PMStatic.ReadLine();
					iFoundIt = true;
					break;
				}
			}
			if (iFoundIt)
				break;
		}
		return iFoundIt;
	}*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mdm.processor.service.IPMComparison#ShowPotMatchRecords()
	 */
	@Override
	public void ShowPotMatchRecords() {
		HashMap<String, ArrayList<MatchedRecord>> objPMRHashMap = objPMR
				.GetMatchedRecords();
		if (objPMRHashMap != null && objPMRHashMap.size() > 0) {
			Collection<ArrayList<MatchedRecord>> llstMR = objPMR.GetMatchedRecords()
					.values();

			for (List<MatchedRecord> lstMR : llstMR) {
				for (MatchedRecord objMR : lstMR) {
					if (PMStatic.PERSON_PARTY_CLASS.equals(objMR
							.getPartyClass())) {
						// sEmailBody = sEmailBody +
						// " CriteriaId: "+objMR.CriteriaId +
						// " Party Id: "+objMR.MdmRecord.getFields(6).getFieldValue()
						// + " GC Party Id: " +objMR.GCPartyID
						// +" First Name: "+objMR.MdmRecord.get('First_Name__c')+'
						// Last Name: '+objMR.MdmRecord.get('Last_Name__c')+ '
						// SSN : ' +objMR.MdmRecord.get('SSN__c')+' MatchType :
						// ' +objMR.MatchType+' Score: ' +objMR.Score + '\n';
						// LOGGER.info( " CriteriaId: "+objMR.CriteriaId +
						// " Party Id: "+objMR.MdmRecord.getFields(0).getFieldValue()
						// + " GC Party Id: " +objMR.GCPartyID
						// +" First Name: "+objMR.MdmRecord.getFields(1).getFieldValue()+" Last Name: "+objMR.MdmRecord.getFields(2).getFieldValue()+
						// " SSN : "
						// +objMR.MdmRecord.getFields(3).getFieldValue()+" MatchType : "
						// +objMR.MatchType+ " Score: " +objMR.Score + "\n");
						/*
						 * LOGGER.info(" Party Id:
						 * '+objMR.MdmRecord.get('Party_ID__c')); LOGGER.info("
						 * GC Party Id: '+objMR.GCPartyID); LOGGER.info(" First
						 * Name: '+objMR.MdmRecord.get('First_Name__c'));
						 * LOGGER.info(" Last Name:
						 * '+objMR.MdmRecord.get('Last_Name__c')); LOGGER.info("
						 * SSN : ' +objMR.MdmRecord.get('SSN__c'));
						 * LOGGER.info(" MatchType : ' +objMR.MatchType);
						 * LOGGER.info(" Score: ' +objMR.Score);
						 */
					} else {
						// sEmailBody = sEmailBody + ' CriteriaId:
						// '+objMR.CriteriaId +' Party Id:
						// '+objMR.MdmRecord.get('Party_ID__c') + ' GC Party Id:
						// '+objMR.GCPartyID + ' Org Name:
						// '+objMR.MdmRecord.get('Org_Name__c') + ' NASADAQ Code
						// : ' +objMR.MdmRecord.get('NASDAQ_Code__c') + '
						// MatchType : ' +objMR.MatchType + ' Score: '
						// +objMR.Score+ '\n';
						// LOGGER.info( " CriteriaId: "+objMR.CriteriaId +
						// " Party Id: "+objMR.MdmRecord.getFields(0).getFieldValue()
						// + " GC Party Id: " +objMR.GCPartyID
						// +" Org Name : "+objMR.MdmRecord.getFields(1).getFieldValue()+"  NASADAQ Code: "+objMR.MdmRecord.getFields(2).getFieldValue()+" MatchType : "
						// +objMR.MatchType+ " Score: " +objMR.Score + "\n");
						/*
						 * LOGGER.info(" CriteriaId: '+objMR.CriteriaId);
						 * LOGGER.info(" Party Id:
						 * '+objMR.MdmRecord.get('Party_ID__c')); LOGGER.info("
						 * GC Party Id: '+objMR.GCPartyID); LOGGER.info(" Org
						 * Name: '+objMR.MdmRecord.get('Org_Name__c'));
						 * LOGGER.info(" NASADAQ Code : '
						 * +objMR.MdmRecord.get('NASDAQ_Code__c'));
						 * LOGGER.info(" MatchType : ' +objMR.MatchType);
						 * LOGGER.info(" Score: ' +objMR.Score);
						 */
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mdm.processor.service.IPMComparison#GetGoldenMatchedRecords()
	 */
	@Override
	public PMMatchedRecord[] GetGoldenMatchedRecords() {
		PMMatchedRecord[] lobjPMMR = null;
		Integer iCnt = 0;

		HashMap<String, ArrayList<MatchedRecord>> objPMRHashMap = objPMR
				.GetMatchedRecords();

		if (objPMRHashMap != null && objPMRHashMap.size() > 0) {
			LOGGER.info("Matched Records size" + objPMRHashMap.size());
			// PMStatic.ReadLine();
			lobjPMMR = new PMMatchedRecord[objPMRHashMap.size()];
			Collection<ArrayList<MatchedRecord>> objCALMR = objPMRHashMap
					.values();
			for (ArrayList<MatchedRecord> objALMR : objCALMR) {
				if (objALMR.size() > 1) {

				}
				for (MatchedRecord objMR : objALMR) {
					PMMatchedRecord objPMMR = new PMMatchedRecord();
					if (objMR.getCriteriaId() != null)
						objPMMR.setCriteriaId(objMR.getCriteriaId());
					if (objMR.getGCPartyID() != null)
						objPMMR.setGCPartyID(objMR.getGCPartyID());
					if (objMR.getMatchType() != null)
						objPMMR.setMatchType(objMR.getMatchType());
					if (objMR.getMdmRecord().getPartyID() != null)
						objPMMR.setMDMPartyID(objMR.getMdmRecord().getPartyID());
					if (objMR.getMdmRecord() != null)
						objPMMR.setMdmRecord(objMR.getMdmRecord());
					if (objMR.getPartyClass() != null)
						objPMMR.setPartyClass(objMR.getPartyClass());
					if (objMR.getScore() != null)
						objPMMR.setScore(objMR.getScore());
					lobjPMMR[iCnt] = objPMMR;
				}
				iCnt++;
			}
		}

		LOGGER.info("Matched Records size" + lobjPMMR.length);

		return lobjPMMR;

	}

	public Integer GetRecordCount(HashMap<String, ArrayList<MatchedRecord>> objPMRHashMap)
	{
		Integer iTotalCnt=0;
		
		Set<Entry<String, ArrayList<MatchedRecord>>> set = objPMRHashMap.entrySet();
		int numberOfKeys = set.size();
		int totalArrayListLength = 0;
		
		Iterator<Entry<String, ArrayList<MatchedRecord>>> itr = set.iterator();
		while (itr.hasNext())
		{
			int iCnt =itr.next().getValue().size();
			PMStatic.Print("iCnt value:==============="+iCnt);
			totalArrayListLength = totalArrayListLength + iCnt;
		}	
		return totalArrayListLength;
		//return numberOfKeys + totalArrayListLength;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mdm.processor.service.IPMComparison#GetMatchedRecords()
	 */
	@Override
	public PMMatchedRecord[] GetMatchedRecords() {
		PMMatchedRecord[] lobjPMMR = null;
		Integer iCnt = 0;

		HashMap<String, ArrayList<MatchedRecord>> objPMRHashMap = objPMR
				.GetMatchedRecords();

		if (objPMRHashMap != null && objPMRHashMap.size() > 0) {
			LOGGER.info("Matched Records size" + objPMRHashMap.size());
			// PMStatic.ReadLine();
			int iTotalLength= GetRecordCount(objPMRHashMap);
			lobjPMMR = new PMMatchedRecord[iTotalLength];
			Collection<ArrayList<MatchedRecord>> objCALMR = objPMRHashMap.values();
			boolean isPotMatchParent=true;
			for (ArrayList<MatchedRecord> objALMR : objCALMR) {
				isPotMatchParent=true;
				if (objALMR.size() > 1) {
					
					for (MatchedRecord objMR : objALMR) {
						PMMatchedRecord objPMMR = new PMMatchedRecord();
						if (objMR.getCriteriaId() != null)
							objPMMR.setCriteriaId(objMR.getCriteriaId());
						if (objMR.getGCPartyID() != null)
							objPMMR.setGCPartyID(objMR.getGCPartyID());
						if (objMR.getMatchType() != null)
							objPMMR.setMatchType(objMR.getMatchType());
						if (objMR.getMdmRecord().getPartyID() != null)
							objPMMR.setMDMPartyID(objMR.getMdmRecord().getPartyID());
						if (objMR.getMdmRecord() != null)
							objPMMR.setMdmRecord(objMR.getMdmRecord());
						if (objMR.getPartyClass() != null)
							objPMMR.setPartyClass(objMR.getPartyClass());
						if (objMR.getScore() != null)
							objPMMR.setScore(objMR.getScore());
						if(isPotMatchParent)
							objPMMR.setIsGoldenCopy(isPotMatchParent);
						else
							objPMMR.setIsGoldenCopy(isPotMatchParent);
					
						lobjPMMR[iCnt++] = objPMMR;
						isPotMatchParent=false;
				  }
				}
				else
				{
					for (MatchedRecord objMR : objALMR) {
						PMMatchedRecord objPMMR = new PMMatchedRecord();
						if (objMR.getCriteriaId() != null)
							objPMMR.setCriteriaId(objMR.getCriteriaId());
						if (objMR.getGCPartyID() != null)
							objPMMR.setGCPartyID(objMR.getGCPartyID());
						if (objMR.getMatchType() != null)
							objPMMR.setMatchType(objMR.getMatchType());
						if (objMR.getMdmRecord().getPartyID() != null)
							objPMMR.setMDMPartyID(objMR.getMdmRecord().getPartyID());
						if (objMR.getMdmRecord() != null)
							objPMMR.setMdmRecord(objMR.getMdmRecord());
						if (objMR.getPartyClass() != null)
							objPMMR.setPartyClass(objMR.getPartyClass());
						if (objMR.getScore() != null)
							objPMMR.setScore(objMR.getScore());
						if(isPotMatchParent)
							objPMMR.setIsGoldenCopy(isPotMatchParent);
						else
							objPMMR.setIsGoldenCopy(isPotMatchParent);
						
						lobjPMMR[iCnt] = objPMMR;
					}
			  }
				if(isPotMatchParent) 
					iCnt++;
			}
			LOGGER.info("Matched Records size" + lobjPMMR.length);
		}

		return lobjPMMR;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mdm.processor.service.IPMComparison#ShowMatchedRecords(com.sforce
	 * .soap.WS_POTMatch.PMMatchedRecord[])
	 */
	@Override
	public void ShowMatchedRecords(PMMatchedRecord[] lobjMR) {
		if (lobjMR != null && lobjMR.length > 0) {
			for (Integer jCnt = 0; jCnt < lobjMR.length; jCnt++) {
				PMStatic.PrintMatchedRecord(lobjMR[jCnt]);
			}
		}
	}

}
