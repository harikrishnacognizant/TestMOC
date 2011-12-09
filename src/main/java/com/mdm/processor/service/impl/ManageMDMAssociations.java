package com.mdm.processor.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sforce.soap.WS_POTMatch.AssociationsTemp;
import com.sforce.soap.WS_POTMatch.PartyAssociationType;
import com.sforce.soap.WS_POTMatch.PartyAssociations;
import com.sforce.soap.WS_POTMatch.PartyIdGroup;
import com.sforce.soap.WS_POTMatch.SoapConnection;
import com.sforce.soap.WS_POTMatch.ValidAssociations;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.PartnerConnection;

import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.mdm.processor.exception.BaseExceptionFactory;
import com.mdm.processor.exception.BaseExceptionUtil;
import com.mdm.processor.exception.IBaseException;
import com.mdm.processor.service.IMDMRecordsCount;
import com.mdm.processor.service.IMDMWebServiceAccess;
import com.mdm.processor.service.IManageMDMAssociations;
import com.mdm.processor.service.IPMEngineNew;

public class ManageMDMAssociations implements IManageMDMAssociations {
	// public SoapConnection wsMDMBinding;
	// IMDMWebServiceAccess objWSAccess;

	@Autowired
	IMDMWebServiceAccess objWSAccess;

	// public HashMap<String, String> PersonMapPrtyIds;
	// public HashMap<String, String> OrgMapPrtyIds;
	// public HashMap<String, String> AsscMapTypes;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ManageMDMAssociations.class);
	private Map<String, Map<String, String>> retMap;
	
	@Autowired
	IPMEngineNew objPME;


	@Autowired
	IMDMRecordsCount objMRC;
	
	@Override
	public Map<String, Map<String, String>> getManageMDMAssociations() {

		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();

		// IMDMWebServiceAccess wsAccess

		try {
			SoapConnection wsMDMBinding = objWSAccess
					.getWSPotMatchEngineConnection();
			// objWSAccess = wsAccess;
			// wsMDMBinding = wsBinding;

			Integer iCntPersonGC = wsMDMBinding.getGCPersonsCount();
			Integer iCntOrgGC = wsMDMBinding.getGCOrgsCount();
			PartyIdGroup[] objPersonPrtyIdGrp = new PartyIdGroup[iCntPersonGC];
			PartyIdGroup[] objOrgPrtyIdGrp = new PartyIdGroup[iCntOrgGC];

			objPersonPrtyIdGrp = wsMDMBinding.GetAllPartyIds(
					PMStatic.PERSON_PARTY_CLASS, 1);
			objOrgPrtyIdGrp = wsMDMBinding.GetAllPartyIds(
					PMStatic.ORG_PARTY_CLASS, 1);

			Map<String, String> PersonMapPrtyIds = new HashMap<String, String>();

			for (Integer iCnt = 0; iCnt < objPersonPrtyIdGrp.length; iCnt++) {
				PersonMapPrtyIds.put(objPersonPrtyIdGrp[iCnt].getSrcPartyID(),
						objPersonPrtyIdGrp[iCnt].getGCPartyID());
			}

			Map<String, String> OrgMapPrtyIds = new HashMap<String, String>();

			for (Integer iCnt = 0; iCnt < objOrgPrtyIdGrp.length; iCnt++) {
				OrgMapPrtyIds.put(objOrgPrtyIdGrp[iCnt].getSrcPartyID(),
						objOrgPrtyIdGrp[iCnt].getGCPartyID());
			}
			Map<String, String> AsscMapTypes = GetAllAssociationTypes();

			map.put(PMStatic.PERSON_MAPED_PARTY_IDS, PersonMapPrtyIds);
			map.put(PMStatic.ORG_MAPED_PARTY_IDS, OrgMapPrtyIds);
			map.put(PMStatic.ASSC_MAPED_TYPES, AsscMapTypes);

		} catch (ConnectionException connExp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(connExp));
			throw BaseExceptionFactory.getInstance(connExp);
		} catch (Exception exp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(exp));
			throw BaseExceptionFactory.getInstance(exp);
		}

		return map;

	}

	public Map<String, String> getTypedMap(String mapType) {
		if (mapType != null && !"".equals(mapType)) {
			if (retMap == null)
				retMap = getManageMDMAssociations();

			if (retMap != null && !retMap.isEmpty()) {
				return retMap.get(mapType);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mdm.processor.service.IManageMDMAssociations#GetAllAssociationTypes()
	 */
	@Override
	public HashMap<String, String> GetAllAssociationTypes() {

		HashMap<String, String> objAssTypes = new HashMap<String, String>();
		PartyAssociationType[] aPAT;
		try {
			SoapConnection wsMDMBinding = objWSAccess
					.getWSPotMatchEngineConnection();
			aPAT = wsMDMBinding.getAllAssociationTypes();

			for (Integer iCnt = 0; iCnt < aPAT.length; iCnt++) {
				objAssTypes.put(aPAT[iCnt].getAssociationTypeID(),
						aPAT[iCnt].getAssociationType());
			}
			LOGGER.info("Total Assoction Types I got here: " + aPAT.length);
			// PMStatic.ReadLine();
			if (objAssTypes != null && objAssTypes.size() > 0)
				return objAssTypes;
		} catch (ConnectionException connExp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(connExp));
			throw BaseExceptionFactory.getInstance(connExp);
		} catch (Exception exp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(exp));
			throw BaseExceptionFactory.getInstance(exp);
		}

		return null;
	}

	/*
	 * public ArrayList<PartyAssociations> CheckValidityOfAssociations(SObject[]
	 * objMAT,ValidAssociations[] objVA,String fromPartyClass,String
	 * toPartyClass) { ArrayList<PartyAssociations> lPA=new
	 * ArrayList<PartyAssociations>(); HashMap<String, String>
	 * fromMapPrtyIds=null; HashMap<String, String> toMapPrtyIds=null;
	 * 
	 * if(PMStatic.PERSON_PARTY_CLASS.equalsIgnoreCase(fromPartyClass)) {
	 * fromMapPrtyIds = PersonMapPrtyIds; toMapPrtyIds = OrgMapPrtyIds; } else
	 * if (PMStatic.ORG_PARTY_CLASS.equalsIgnoreCase(fromPartyClass)) {
	 * fromMapPrtyIds = OrgMapPrtyIds ; toMapPrtyIds = PersonMapPrtyIds; }
	 * 
	 * if(objVA!=null && objMAT!=null && objVA.length>0 && objMAT.length>0) {
	 * for(Integer iCnt=0;iCnt<objMAT.length;iCnt++) { for(Integer
	 * jCnt=0;jCnt<objVA.length;jCnt++) {
	 * //LOGGER.info("objAT[iCnt].getFromPartyClass()==="
	 * +objMAT[iCnt].getFrom_Party_Class__c());
	 * LOGGER.info("objVA[jCnt].getFromPartyClass()==="
	 * +objVA[jCnt].getFromPartyClass());
	 * LOGGER.info("objAT[iCnt].getToPartyClass()==="
	 * +objMAT[iCnt].getTo_Party_Class__c
	 * ()+"objVA[jCnt].getToPartyClass()==="+objVA[jCnt].getToPartyClass());
	 * LOGGER.info("objAT[iCnt].getPartyAssociationType()==="+objMAT[iCnt].
	 * getParty_Association_Type__c
	 * ()+"objVA[jCnt].getAssociationTypeID()==="+objVA
	 * [jCnt].getAssociationTypeID());
	 * 
	 * LOGGER.info("objVA[jCnt].getAssociationTypeID(): "+objVA[jCnt].
	 * getAssociationTypeID());
	 * LOGGER.info("objAssocTypes.get(arg0): "+AsscMapTypes
	 * .get(objVA[jCnt].getAssociationTypeID())); //PMStatic.ReadLine();
	 * if(objMAT
	 * [iCnt].getFrom_Party_Class__c().equals(objVA[jCnt].getFromPartyClass())
	 * &&
	 * objMAT[iCnt].getTo_Party_Class__c().equals(objVA[jCnt].getToPartyClass())
	 * &&
	 * objMAT[iCnt].getParty_Association_Type__c().equals(AsscMapTypes.get(objVA
	 * [jCnt].getAssociationTypeID())) ) { PartyAssociations objPA=new
	 * PartyAssociations();
	 * objPA.setAssociationTypeID(objVA[jCnt].getAssociationTypeID());
	 * objPA.setFromPartyID
	 * (fromMapPrtyIds.get(objMAT[iCnt].getFrom_Party_ID__c()));
	 * objPA.setToPartyID(toMapPrtyIds.get(objMAT[iCnt].getTo_Party_ID__c()));
	 * //objPC.setPartyID(objAT[iCnt].getPartyID());
	 * if(objMAT[iCnt].getComment__c()!=null)
	 * objPA.setComments(objMAT[iCnt].getComment__c());
	 * if(objMAT[iCnt].getSource_System__c()!=null)
	 * objPA.setSourceSystemCode(objMAT[iCnt].getSource_System__c());
	 * 
	 * LOGGER.info("objAT[iCnt].getFromPartyClass()==="+objMAT[iCnt].
	 * getFrom_Party_Class__c
	 * ()+"objVA[jCnt].getFromPartyClass()==="+objVA[jCnt].getFromPartyClass());
	 * LOGGER
	 * .info("objAT[iCnt].getToPartyClass()==="+objMAT[iCnt].getTo_Party_Class__c
	 * ()+"objVA[jCnt].getToPartyClass()==="+objVA[jCnt].getToPartyClass());
	 * LOGGER.info("objAT[iCnt].getPartyAssociationType()==="+objMAT[iCnt].
	 * getParty_Association_Type__c
	 * ()+"objVA[jCnt].getAssociationTypeID()==="+objVA
	 * [jCnt].getAssociationTypeID());
	 * 
	 * //PMStatic.ReadLine(); lPA.add(objPA); } } } return lPA; } return null; }
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mdm.processor.service.IManageMDMAssociations#CheckValidityOfAssociations
	 * (com.sforce.soap.partner.sobject.SObject[],
	 * com.sforce.soap.WS_POTMatch.ValidAssociations[], java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public ArrayList<PartyAssociations> CheckValidityOfAssociations(
			SObject[] objMAT, ValidAssociations[] objVA, String fromPartyClass,
			String toPartyClass, Map<String, String> fromMapPrtyIds,
			Map<String, String> toMapPrtyIds) {
		ArrayList<PartyAssociations> lPA = new ArrayList<PartyAssociations>();

		if (objVA != null && objMAT != null && objVA.length > 0
				&& objMAT.length > 0) {
			for (Integer iCnt = 0; iCnt < objMAT.length; iCnt++) {
				for (Integer jCnt = 0; jCnt < objVA.length; jCnt++) {
					// LOGGER.info("objAT[iCnt].getFromPartyClass()==="+objMAT[iCnt].getFrom_Party_Class__c());
					LOGGER.info("objVA[jCnt].getFromPartyClass()==="
							+ objVA[jCnt].getFromPartyClass());
					LOGGER.info("objAT[iCnt].getToPartyClass()==="
							+ objMAT[iCnt].getField("To_Party_Class__c")
							+ "objVA[jCnt].getToPartyClass()==="
							+ objVA[jCnt].getToPartyClass());
					LOGGER.info("objAT[iCnt].getPartyAssociationType()==="
							+ objMAT[iCnt]
									.getField("Party_Association_Type__c")
							+ "objVA[jCnt].getAssociationTypeID()==="
							+ objVA[jCnt].getAssociationTypeID());

					LOGGER.info("objVA[jCnt].getAssociationTypeID(): "
							+ objVA[jCnt].getAssociationTypeID());
					LOGGER.info("objAssocTypes.get(arg0): "
							+ getTypedMap(PMStatic.ASSC_MAPED_TYPES).get(
									objVA[jCnt].getAssociationTypeID()));
					// PMStatic.ReadLine();
					if (objMAT[iCnt].getField("From_Party_Class__c").equals(
							objVA[jCnt].getFromPartyClass())
							&& objMAT[iCnt].getField("To_Party_Class__c")
									.equals(objVA[jCnt].getToPartyClass())
							&& objMAT[iCnt]
									.getField("Party_Association_Type__c")
									.equals(getTypedMap(
											PMStatic.ASSC_MAPED_TYPES).get(
											objVA[jCnt].getAssociationTypeID()))) {
						PartyAssociations objPA = new PartyAssociations();
						objPA.setAssociationTypeID(objVA[jCnt]
								.getAssociationTypeID());
						objPA.setFromPartyID(fromMapPrtyIds.get(objMAT[iCnt]
								.getField("From_Party_ID__c")));
						objPA.setToPartyID(toMapPrtyIds.get(objMAT[iCnt]
								.getField("To_Party_ID__c")));
						// objPC.setPartyID(objAT[iCnt].getPartyID());
						if (objMAT[iCnt].getField("Comment__c") != null)
							objPA.setComments((String) objMAT[iCnt]
									.getField("Comment__c"));
						if (objMAT[iCnt].getField("Source_System__c") != null)
							objPA.setSourceSystemCode((String) objMAT[iCnt]
									.getField("Source_System__c"));

						LOGGER.info("objAT[iCnt].getFromPartyClass()==="
								+ objMAT[iCnt].getField("From_Party_Class__c")
								+ "objVA[jCnt].getFromPartyClass()==="
								+ objVA[jCnt].getFromPartyClass());
						LOGGER.info("objAT[iCnt].getToPartyClass()==="
								+ objMAT[iCnt].getField("To_Party_Class__c")
								+ "objVA[jCnt].getToPartyClass()==="
								+ objVA[jCnt].getToPartyClass());
						LOGGER.info("objAT[iCnt].getPartyAssociationType()==="
								+ objMAT[iCnt]
										.getField("Party_Association_Type__c")
								+ "objVA[jCnt].getAssociationTypeID()==="
								+ objVA[jCnt].getAssociationTypeID());
						LOGGER.info("fromPartyID"
								+ fromMapPrtyIds.get(objMAT[iCnt]
										.getField("From_Party_ID__c")));
						LOGGER.info("toPartyID"
								+ toMapPrtyIds.get(objMAT[iCnt]
										.getField("To_Party_ID__c")));
						// PMStatic.ReadLine();
						lPA.add(objPA);
					}
				}
			}
			return lPA;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * This function process all MDM Associations for existing Golden & PotMatch Party Ids. 
	 * @see
	 * com.mdm.processor.service.IManageMDMAssociations#ProcessMDMAssociations
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public void ProcessMDMAssociations(String fromPartyClass,
			String toPartyClass) {

		PartnerConnection connection;
		connection = objWSAccess.getPartnerConnection();
		String soqlQry = "Select From_Party_Class__c,To_Party_Class__c,From_Party_ID__c, To_Party_ID__c, Party_Association_Type__c,Source_System__c,Comment__c from MDM_Association_Temp__c where From_Party_Class__c='"
				+ fromPartyClass
				+ "' and To_Party_Class__c='"
				+ toPartyClass
				+ "'";

		QueryResult qr;
		try {
			SoapConnection wsMDMBinding = objWSAccess
					.getWSPotMatchEngineConnection();
			qr = connection.query(soqlQry);

			LOGGER.info("MDM Temp Assc Size: " + qr.getSize());
			LOGGER.info("MDM Temp Assc Size: " + qr.getRecords());
			// PMStatic.ReadLine();
			boolean isDone = false;
			boolean isSuccess = false;
			boolean noValidAssFound=true;
			Integer iCnt = 0;
			if(qr.getRecords().length>0)
			{
				while (!isDone) {
					iCnt++;
					SObject[] assRecords = qr.getRecords();
	
					if (assRecords != null && assRecords.length > 0) {
	
						/*
						 * MDM_Association_Temp__c[] objMDMAssTemp =new
						 * MDM_Association_Temp__c[assRecords.length];
						 * 
						 * 
						 * for(Integer jCnt=0;jCnt<assRecords.length;jCnt++) {
						 * objMDMAssTemp[jCnt] = (MDM_Association_Temp__c)
						 * assRecords[jCnt]; }
						 */
	
						Map<String, String> fromMapPrtyIds = null;
						Map<String, String> toMapPrtyIds = null;
	
						if (PMStatic.PERSON_PARTY_CLASS
								.equalsIgnoreCase(fromPartyClass)) {
							fromMapPrtyIds = getTypedMap(PMStatic.PERSON_MAPED_PARTY_IDS);
							toMapPrtyIds = getTypedMap(PMStatic.ORG_MAPED_PARTY_IDS);
						} else if (PMStatic.ORG_PARTY_CLASS
								.equalsIgnoreCase(fromPartyClass)) {
							fromMapPrtyIds = getTypedMap(PMStatic.ORG_MAPED_PARTY_IDS);
							toMapPrtyIds = getTypedMap(PMStatic.PERSON_MAPED_PARTY_IDS);
						}
	
						ValidAssociations[] objVA = wsMDMBinding
								.getValidAssociations(fromPartyClass, toPartyClass);
						LOGGER.info("ProcessMDMAssociations : system ValidAssociations = "
								+ objVA.length);
						// PMStatic.ReadLine();
						if (objVA != null && objVA.length > 0 && assRecords != null
								&& assRecords.length > 0) {
							LOGGER.info(" getMDMAssociations from Query more = "
									+ assRecords.length);
							ArrayList<PartyAssociations> objlPA = CheckValidityOfAssociations(
									assRecords, objVA, fromPartyClass,
									toPartyClass, fromMapPrtyIds, toMapPrtyIds);
	
							LOGGER.info("ProcessMDMAssociations : PartyAssociations system  "
									+ objlPA.size());
							if (objlPA != null && objlPA.size() > 0) {
								PartyAssociations[] objPA = new PartyAssociations[objlPA
										.size()];
								objlPA.toArray(objPA);
								LOGGER.info("ProcessMDMAssociations : PartyAssociations[] system  "
										+ objPA.length);
								// PMStatic.ReadLine();
	
								isSuccess = wsMDMBinding
										.CreateAssociationsOnCloud(objPA);
								LOGGER.info("isSuccess: " + isSuccess
										+ "ProcessMDMAssociations running: " + iCnt
										+ " I got Associations: " + objlPA.size());
								LOGGER.info("Assc from party id Length:"+fromMapPrtyIds.keySet().toArray(new String[0]).length);
								LOGGER.info("Assc to party id Length::"+toMapPrtyIds.keySet().toArray(new String[0]).length);
								wsMDMBinding.DeleteAssociationTemp(fromPartyClass,
										fromMapPrtyIds.keySet().toArray(new String[0]),
										toPartyClass,
										toMapPrtyIds.keySet().toArray(new String[0]));
							}
							
							
						}
					
						isDone = qr.isDone();
						LOGGER.info("Assc isDone:"+isDone);
						
						if (isDone)
							break;
						qr = connection.queryMore(qr.getQueryLocator());
	
						// PMStatic.ReadLine();
					}
					int latestVal = iCnt;
					int mdmAssCnt= objMRC.getAssociationTempCount()/ PMStatic.TEMP_ASSOC_MAX_RECORDS; 
					if (iCnt == 0) {
						// At the start up
						objPME.latestBlockSize.put(
								PMStatic.LATEST_NUMBER_OF_ASSOC_PROCESSED_REC,
								PMStatic.TEMP_ASSOC_MAX_RECORDS);
					} else if (latestVal == - 1) {
						// Processing last time ... hence set the total number of
						// mdm count as the processed records
						objPME.latestBlockSize.put(
								PMStatic.LATEST_NUMBER_OF_ASSOC_PROCESSED_REC,
								mdmAssCnt);
					} else {
						objPME.latestBlockSize.put(
								PMStatic.LATEST_NUMBER_OF_ASSOC_PROCESSED_REC,
								PMStatic.TEMP_ASSOC_MAX_RECORDS * mdmAssCnt);
					}
				 }
						  boolean isDelDone =	DeleteAssociationTemp();
						  LOGGER.info("isDelDone: "+isDelDone);
				
				
			}

		} catch (ConnectionException connExp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(connExp));
			throw BaseExceptionFactory.getInstance(connExp);
		} catch (Exception exp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(exp));
			throw BaseExceptionFactory.getInstance(exp);
		} finally {
			connection = null;
		}

	}
	
	public boolean DeleteAssociationTemp() {
		PartnerConnection connection;
		connection = objWSAccess.getPartnerConnection();
		// String soqlQry =
		// "Select count(id) from Exception_Header__c where Exception_type__c='"+PMStatic.EXACT_MATCH_TYPE
		// +"'";
		String soqlQry = "Select id from MDM_Association_Temp__c";
		QueryResult qr;
		int iAssocTmpnt = 0;
		boolean isSuccess = false;

		try {
			SoapConnection wsMDMBinding = objWSAccess
					.getWSPotMatchEngineConnection();
			LOGGER.info("soqlQry : " + soqlQry);
			
			boolean isDone = false;
			
			qr = connection.query(soqlQry);	
			if(qr.getRecords()!=null && qr.getRecords().length>0)
			{
				while(!isDone)
				{
					SObject[] objAssocTmp = qr.getRecords();
					String[] objAssocIDs = getAssocTempIds(objAssocTmp);
					iAssocTmpnt = objAssocTmp.length;
					LOGGER.info("AssociationTempCount: " + objAssocIDs);
					DeleteResult[] objDR = connection.delete(objAssocIDs);
					for(int iCnt=0;iCnt<objDR.length;iCnt++)
						if(objDR[0].getSuccess())
						{
							isSuccess=true;
						}
							
					isDone = qr.isDone();
					LOGGER.info("Assc isDone:"+isDone);
					
					if (isDone)
						break;
					qr = connection.queryMore(qr.getQueryLocator());
				}
			}	

		} catch (ConnectionException connExp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(connExp));
			throw BaseExceptionFactory.getInstance(connExp);
		} catch (Exception exp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(exp));
			throw BaseExceptionFactory.getInstance(exp);
		} finally {
			connection = null;
		}

		return isSuccess;
	}

	private String[] getAssocTempIds(SObject[] arrsObj) {
		String[] objIDs=null;
		
		if(arrsObj!=null && arrsObj.length>0)
		{
			objIDs=new String[arrsObj.length];
			for(int iCnt=0;iCnt<arrsObj.length;iCnt++)
			{
				objIDs[iCnt]=arrsObj[iCnt].getId();
			}
		}
		return objIDs;
	}
	

}
