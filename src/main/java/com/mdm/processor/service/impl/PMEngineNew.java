package com.mdm.processor.service.impl;

import com.mdm.processor.exception.BaseExceptionFactory;
import com.mdm.processor.model.AttribScoreMap;
import com.mdm.processor.service.AppInitializer;
import com.mdm.processor.service.IMDMRecordsCount;
import com.mdm.processor.service.IMDMWebServiceAccess;
import com.mdm.processor.service.IManageMDMAssociations;
import com.mdm.processor.service.IPMComparison;
import com.mdm.processor.service.IPMEngineNew;
import com.mdm.processor.service.IProcessingMDMData;
import com.sforce.soap.WS_POTMatch.*;
//import com.sforce.soap.partner.PartnerConnection;
//import com.sforce.soap.partner.QueryResult;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.apache.commons.collections.keyvalue.MultiKey;
//import org.apache.commons.collections.map.MultiKeyMap;

@Service
public class PMEngineNew implements IPMEngineNew {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PMEngineNew.class);

	@Autowired
	IMDMWebServiceAccess objWSAccess;

	@Autowired
	IMDMRecordsCount objMRC;
	
	@Autowired
	PMECommanMethods objPCM;

	// PMComparison objPMC;
	// MapFields[] personsFields;
	// MapFields[] personsGCFields;

	// MapFields[] orgsFields;
	// MapFields[] orgsGCFields;
	// ArrayList<String> CLTPartyIds;
	// PartyIdGroup[] objPIG;
	// MDMDataMatchedType objMMT;

	@Autowired
	IProcessingMDMData objPMD;

	// HashMap<String, String> objAssocTypes;
	// PMMatchedRecord[] lobjPersonPMMR;
	// PMMatchedRecord[] lobjOrgPMMR;
	// SoapConnection wsMDMBinding;

	@Autowired
	IManageMDMAssociations objMMA;

	@Autowired
	IPMComparison objPMC;

	@Autowired
	GUIHelperImpl gUIHelperImpl;
	
	@Autowired
	AppInitializer objAppInitializer;
	
	@Autowired
	ReadCSVfromSourceTempData objReadCSV;

	public void process() {

		try {
			
			LOGGER.info("--------------------Process Just Started-----------------------------------------");
			LOGGER.info("--------------------Process Just Started-----------------------------------------");
			LOGGER.info("--------------------Process Just Started-----------------------------------------");
			LOGGER.info("--------------------Process Just Started-----------------------------------------");
			LOGGER.info("--------------------Process Just Started-----------------------------------------");
			LOGGER.info("--------------------Process Just Started-----------------------------------------");
			LOGGER.info("--------------------Process Just Started-----------------------------------------");
			LOGGER.info("--------------------Process Just Started-----------------------------------------");
			LOGGER.info("--------------------Process Just Started-----------------------------------------");
			LOGGER.info("--------------------Process Just Started-----------------------------------------");
			LOGGER.info("--------------------Process Just Started-----------------------------------------");
			LOGGER.info("--------------------Process Just Started-----------------------------------------");
			
			
			
			 
			// objWSAccess = new MDMWebServiceAccess();
			SoapConnection wsMDMBinding = objWSAccess
					.getWSPotMatchEngineConnection();

			LOGGER.info("Logged in " + wsMDMBinding);

			// objMRC.getMDMRecordsCount(wsMDMBinding, objWSAccess);

			// objPMD = new ProcessingMDMData();

			// BringEverythingFromGC();
			// objMRC.ShowDataVolumeinSFDC();
			// LOGGER.info("I am Waiting for u 2 take the log");
			// PMStatic.ReadLine();
			// StartMyPMEngine();

			//This flag holds the current status of Pot Match Engine process
			PMStatic.POTMATCH_PROCESS=true;
			// We don't have anything to pass ... hence passing null
			objAppInitializer.reloadData(null);
			objMRC.reloadStartUpMap();
			StartPersonsPMEngine();
			StartOrgsPMEngine();
				
			// Hiding Associations for testing only PME Logic - August 19th 2011
			try {
				// wsMDMBinding.CreateAssociationsOnCloud(null);
				// objMMA = new ManageMDMAssociations(wsMDMBinding,
				// objWSAccess);
				// LOGGER.info(objMMA.AsscMapTypes.keySet().toString());
				// LOGGER.info("Org Party Ids size: "
				// + objMMA.OrgMapPrtyIds.size());
				// LOGGER.info("Person Party Ids size: "
				// + objMMA.PersonMapPrtyIds.size());
				objMMA.ProcessMDMAssociations(PMStatic.PERSON_PARTY_CLASS,
						PMStatic.ORG_PARTY_CLASS);
				objMMA.ProcessMDMAssociations(PMStatic.ORG_PARTY_CLASS,
						PMStatic.PERSON_PARTY_CLASS);

			} catch (Exception Exp) {
				LOGGER.info("Problem in  CreateAssociationsOnCloud : "
						+ Exp.getMessage());
				Exp.printStackTrace();
				throw Exp;
			}

			objMRC.showDataVolumeinSFDC();
			LOGGER.info("POTMatch Engine Done....");

			// Save pie data
			
			gUIHelperImpl.setPieChartData(wsMDMBinding.getTotalGCPartyCount(),
					objMRC.getExactMatchCount(),
					wsMDMBinding.getTotalPMPartyCount());
			
			// Save clustered column data
			
			gUIHelperImpl.setClusteredColumnData(wsMDMBinding.getTotalGCPartyCount(),
					objMRC.getExactMatchCount(),
					wsMDMBinding.getTotalPMPartyCount());
			
			PMStatic.POTMATCH_PROCESS=false;
			
			
		} catch (Exception Exp) {
			LOGGER.info("Problem in Constructor PMEngineNew : "
					+ Exp.getMessage());
			Exp.printStackTrace();
			throw BaseExceptionFactory.getInstance(Exp);
		}

		// The process completed
		// Save Pie Data
		// Golden Copy

		// POT Match Copy

		// Exact Match Copy

		// Save clustered column data

	}

	
	
	public PMEngineNew() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mdm.processor.service.IPMEngineNew#GetAllPartyIds(java.lang.String,
	 * java.lang.Integer)
	 */
	@Override
	public PartyIdGroup[] GetAllPartyIds(String partyClass, Integer goldenCopy) {
		try {
			SoapConnection wsMDMBinding = objWSAccess
					.getWSPotMatchEngineConnection();
			return wsMDMBinding.GetAllPartyIds(partyClass, goldenCopy);
		} catch (Exception Exp) {
			LOGGER.info("Problem in GetAllPartyIds : " + Exp.getMessage());
			Exp.printStackTrace();
		}
		return null;
	}

	public String GetMappedGCPartyID(String[] SrcpartyIds, String[] partyIds,
			String SrcPartyID) {

		if (SrcPartyID != null && SrcpartyIds != null && SrcpartyIds.length > 0
				&& partyIds != null && partyIds.length > 0) {

			for (int iCnt = 0; iCnt < SrcpartyIds.length; iCnt++) {
				if (SrcPartyID.equals(SrcpartyIds[iCnt]))
					return partyIds[iCnt];
			}
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mdm.processor.service.IPMEngineNew#CheckValidityOfClassifications
	 * (com.sforce.soap.WS_POTMatch.ClassificationsTemp[],
	 * com.sforce.soap.WS_POTMatch.ValidClassifications[], java.lang.String[],
	 * java.lang.String[])
	 */
	@Override
	public ArrayList<PartyClassifications> CheckValidityOfClassifications(
			ClassificationsTemp[] objCLT, ValidClassifications[] objVC,
			String[] SrcpartyIds, String[] partyIds) {
		ArrayList<PartyClassifications> lPC = new ArrayList<PartyClassifications>();

		if (objVC != null && objCLT != null && objVC.length > 0
				&& objCLT.length > 0) {
			for (Integer iCnt = 0; iCnt < objCLT.length; iCnt++) {
				for (Integer jCnt = 0; jCnt < objVC.length; jCnt++) {
					Print("objCLT[iCnt].getPartyClass()==="
							+ objCLT[iCnt].getPartyClass()
							+ "objVC[jCnt].getPartyClass()==="
							+ objVC[jCnt].getPartyClass());
					Print("objCLT[iCnt].getChildClassificationCode()==="
							+ objCLT[iCnt].getChildClassificationCode()
							+ "objVC[jCnt].getChildClassificationCode()==="
							+ objVC[jCnt].getChildClassificationCode());
					Print("objCLT[iCnt].getChildClassificationType()==="
							+ objCLT[iCnt].getChildClassificationType()
							+ "objVC[jCnt].getChildClassificationType()==="
							+ objVC[jCnt].getChildClassificationType());
					Print("objCLT[iCnt].getParentClassificationCode()==="
							+ objCLT[iCnt].getParentClassificationCode()
							+ "objVC[jCnt].getParentClassificationCode()==="
							+ objVC[jCnt].getParentClassificationCode());
					Print("objCLT[iCnt].getParentClassificationType()==="
							+ objCLT[iCnt].getParentClassificationType()
							+ "objVC[jCnt].getParentClassificationType()==="
							+ objVC[jCnt].getParentClassificationType());
					// PMStatic.ReadLine();
					if (objCLT[iCnt].getChildClassificationCode().equals(
							objVC[jCnt].getChildClassificationCode())
							&& objCLT[iCnt].getChildClassificationType()
									.equals(objVC[jCnt]
											.getChildClassificationType())
							&& objCLT[iCnt].getParentClassificationCode()
									.equals(objVC[jCnt]
											.getParentClassificationCode())
							&& objCLT[iCnt].getParentClassificationType()
									.equals(objVC[jCnt]
											.getParentClassificationType()))
					// &&
					// objCLT[iCnt].getPartyID().equals(SrcpartyIds[iCnt]))
					{
						Print("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYAAAAAAAAAAAAAAAAAA"
								+ GetMappedGCPartyID(SrcpartyIds, partyIds,
										objCLT[iCnt].getPartyID()));
						PartyClassifications objPC = new PartyClassifications();
						objPC.setClassificationID(objVC[jCnt]
								.getClassificationID());
						objPC.setPartyID(GetMappedGCPartyID(SrcpartyIds,
								partyIds, objCLT[iCnt].getPartyID()));
						// objPC.setPartyID(objCLT[iCnt].getPartyID());
						if (objCLT[iCnt].getComments() != null)
							objPC.setComments(objCLT[iCnt].getComments());
						if (objCLT[iCnt].getSourceSystemCode() != null)
							objPC.setSourceSystemCode(objCLT[iCnt]
									.getSourceSystemCode());

						Print("=====objCLT[iCnt].getPartyClass()==="
								+ objCLT[iCnt].getPartyClass()
								+ "objVC[jCnt].getPartyClass()==="
								+ objVC[jCnt].getPartyClass());
						Print("=======objCLT[iCnt].getChildClassificationCode()==="
								+ objCLT[iCnt].getChildClassificationCode()
								+ "objVC[jCnt].getChildClassificationCode()==="
								+ objVC[jCnt].getChildClassificationCode());
						Print("========objCLT[iCnt].getChildClassificationType()==="
								+ objCLT[iCnt].getChildClassificationType()
								+ "objVC[jCnt].getChildClassificationType()==="
								+ objVC[jCnt].getChildClassificationType());
						Print("======objCLT[iCnt].getParentClassificationCode()==="
								+ objCLT[iCnt].getParentClassificationCode()
								+ "objVC[jCnt].getParentClassificationCode()==="
								+ objVC[jCnt].getParentClassificationCode());
						Print("======objCLT[iCnt].getParentClassificationType()==="
								+ objCLT[iCnt].getParentClassificationType()
								+ "objVC[jCnt].getParentClassificationType()==="
								+ objVC[jCnt].getParentClassificationType());
						// PMStatic.ReadLine();
						lPC.add(objPC);
					}
				}
			}
			return lPC;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mdm.processor.service.IPMEngineNew#ProcessMDMClassifications(java
	 * .lang.String[], java.lang.String[], java.lang.String)
	 */
	@Override
	public void ProcessMDMClassifications(String[] partyIds,
			String[] srcPartyIds, String partyClass) {
		try {
			SoapConnection wsMDMBinding = objWSAccess
					.getWSPotMatchEngineConnection();
			Print("ProcessMDMClassifications : created PartyIds = "
					+ partyIds.length);
			Print("ProcessMDMClassifications : created PartyIds = "
					+ srcPartyIds.length);
			// PMStatic.ReadLine();
			// Integer iMDMCLTpbCnt = GetSourcePageBlockSize(objMRC.MDMCLTCnt);
			ValidClassifications[] objVC = wsMDMBinding
					.getValidClassifications(partyClass);
			Print("ProcessMDMClassifications : system ValidClassifications = "
					+ objVC.length);
			// PMStatic.ReadLine();
			
			// for(Integer iCnt=0;iCnt<iMDMCLTpbCnt;iCnt++)
			// {
			ClassificationsTemp[] objCLT = wsMDMBinding
					.getMDMClassificationsTemp(partyClass, srcPartyIds);
			Print(" getMDMClassificationsTemp system giving ClassificationsTemp = "
					+ objCLT.length);
			// PMStatic.ReadLine();
			ArrayList<PartyClassifications> objlPC = CheckValidityOfClassifications(
					objCLT, objVC, srcPartyIds, partyIds);
			if (objlPC != null && objlPC.size() > 0) {
				Print("CheckValidityOfClassifications : PartyClassifications system  "
						+ objlPC.size());
				// PMStatic.PrintArray()
				PartyClassifications[] objPC = new PartyClassifications[objlPC
						.size()];
				// PMStatic.ReadLine();
				objlPC.toArray(objPC);
				Print("CheckValidityOfClassifications : PartyClassifications[] system  "
						+ objPC.length);
				// PMStatic.ReadLine();
				wsMDMBinding.CreateClassificationsOnCloud(objPC);
			}

			// }
			// wsMDMBinding.getMDMClassificationsTemp('Person', )

		} catch (Exception Exp) {
			LOGGER.info("Problem in ProcessMDMClassifications : "
					+ Exp.getMessage());
			Exp.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mdm.processor.service.IPMEngineNew#GetRequiredMatchedRecords(com.
	 * sforce.soap.WS_POTMatch.PMMatchedRecord[], java.lang.String)
	 */
	@Override
	public PMMatchedRecord[] GetRequiredMatchedRecords(
			PMMatchedRecord[] lobjPMMR, String sMatchType) {
		// PMMatchedRecord[] objPMMR=new
		for (int iCnt = 0; iCnt < lobjPMMR.length; iCnt++) {
			if (lobjPMMR[iCnt].getMatchType().equals(sMatchType)) {

			}
		}
		return null;
	}

	private void StartPersonsPMEngine() {

		int mdmTempPersonCnt = MDMRecordsCount.startUpRecordSet
				.get(PMStatic.MDM_TEMP_PERSON_COUNT);
		try {
			SoapConnection wsMDMBinding = objWSAccess
					.getWSPotMatchEngineConnection();
			PMAttributes[] objPMAs = wsMDMBinding.getAllAttributes();
			MapFields[][] goldenPersons = BringEverythingFromGCPersons();
			//ArrayList<AttribScoreMap> latrScoreMap = objPCM.prepareAllAttributeKeyMap(objPMAs,PMStatic.PERSON_PARTY_CLASS,PMStatic.PM_EXACT_MATCH);
			ArrayList<AttribScoreMap> latrScoreMap = objPCM.prepareAllAttributeKeyMap(objPMAs,PMStatic.PERSON_PARTY_CLASS);
			Print("objMRC.MDMPersonsCount: " + mdmTempPersonCnt);
			Integer iMDMPersonsCount = GetSourcePageBlockSize(mdmTempPersonCnt);
			Print("iMDMPersonCount Pageblock size: " + iMDMPersonsCount);
			// PMStatic.ReadLine();
			// iMDMPersonsCount=1;
			for (Integer iCnt = 0; iCnt < iMDMPersonsCount; iCnt++) {
				LOGGER.info("Now you are doing ... for Page Block "
						+ (iCnt + 1));

		
				MapFields[] personsFields = wsMDMBinding.getPersonDetails();
				try {

					objMRC.showDataVolumeinSFDC();
					PMMatchedRecord[] lobjPersonPMMR = RunComparisonEngineMap(
							PMStatic.PERSON_PARTY_CLASS, goldenPersons,
							personsFields, latrScoreMap);

					// Refresh global container
					objPMC.refreshMatchedRecords();

					if (lobjPersonPMMR != null && lobjPersonPMMR.length > 0) {
						LOGGER.info("I got here total matched Records"
								+ lobjPersonPMMR.length);
						// GetGoldenMatchedRecords(lobjPersonPMMR);
						wsMDMBinding.AddMatchedRecords(lobjPersonPMMR);
						objMRC.showDataVolumeinSFDC();
						// PMStatic.ReadLine();
					}
				}

				catch (Exception ioExp) {
					LOGGER.info("Problem while Adding Matched Records ioExp: "
							+ ioExp.getMessage());
					ioExp.printStackTrace();
				}

				try {
					try {
						// wsMDMBinding.ProcessMDMMatchedRecords();
						Integer iMatcdRcdCnt4GC = wsMDMBinding
								.getMatchedRecordCount(PMStatic.NO_MATCH_TYPE,
										PMStatic.PERSON_PARTY_CLASS);
						if (iMatcdRcdCnt4GC > 0) {
							// GC records processings
							LOGGER.info("Before  processMDMSourcePersonGoldenRecords");
							// PMStatic.ReadLine();
							PartyIdSet objPrtIdset4GC = wsMDMBinding
									.ProcessMDMSourcePersonGoldenRecords();
							// PMStatic.ReadLine();
							if (objPrtIdset4GC != null
									&& objPrtIdset4GC.getPartyIDsGroup().length > 0) {
								/*
								 * objPMD.CreatePersonMappingPartyIds(objPrtIdset4GC
								 * .getPartyIDsGroup());
								 * objPMD.AssigningProcessedPartyIds(
								 * PMStatic.PERSON_PARTY_CLASS,
								 * PMStatic.NO_MATCH_TYPE,
								 * objPrtIdset4GC.getGCPartyIDs(),
								 * objPrtIdset4GC.getSrcPartyIDs());
								 * LOGGER.info(
								 * " AssigningProcessedPartyIds : objPrtIdset.getGCPartyIDs() length:"
								 * + objPrtIdset4GC.getGCPartyIDs().length +
								 * "objPrtIdset.getSrcPartyIDs(): " +
								 * objPrtIdset4GC.getSrcPartyIDs());
								 */
								// PMStatic.ReadLine();
								// Krishna on August 19th 2011
								ProcessMDMClassifications(objPrtIdset4GC.getGCPartyIDs(),objPrtIdset4GC.getSrcPartyIDs(),PMStatic.PERSON_PARTY_CLASS);
								wsMDMBinding.DeleteMDMTemp(PMStatic.PERSON_PARTY_CLASS,objPrtIdset4GC.getSrcPartyIDs());
								objMRC.showDataVolumeinSFDC();
								wsMDMBinding.DeleteMatchedRecordsforPartyIds(objPrtIdset4GC.getSrcPartyIDs());
								wsMDMBinding.DeleteClassificationsTemp(PMStatic.PERSON_PARTY_CLASS,objPrtIdset4GC.getSrcPartyIDs());
								
								
							}
						}
					} catch (Exception rExp) {
						LOGGER.info("Problem while processMDMSourcePersonGoldenRecords Matched Records : "
								+ rExp.getMessage());
						rExp.printStackTrace();
					}

					try {
						Integer iMatcdRcdCnt4PM = wsMDMBinding
								.getMatchedRecordCount(PMStatic.POT_MATCH_TYPE,
										PMStatic.PERSON_PARTY_CLASS);
						if (iMatcdRcdCnt4PM > 0) {

							// Potential Match records processings
							LOGGER.info("Before  ProcessMDMSourcePersonPotentialRecords");
							// PMStatic.ReadLine();
							PartyIdSet objPrtIdset4PM = wsMDMBinding
									.ProcessMDMSourcePersonPotentialRecords();
							// PMStatic.ReadLine();
							if (objPrtIdset4PM != null
									&& objPrtIdset4PM.getPartyIDsGroup().length > 0) {
								/*
								 * objPMD.CreatePersonMappingPartyIds(objPrtIdset4PM
								 * .getPartyIDsGroup());
								 * objPMD.AssigningProcessedPartyIds(
								 * PMStatic.PERSON_PARTY_CLASS,
								 * PMStatic.POT_MATCH_TYPE,
								 * objPrtIdset4PM.getGCPartyIDs(),
								 * objPrtIdset4PM.getSrcPartyIDs());
								 * LOGGER.info(
								 * " AssigningProcessedPartyIds : objPrtIdset.getGCPartyIDs() length:"
								 * + objPrtIdset4PM.getGCPartyIDs().length +
								 * "objPrtIdset4PM.getSrcPartyIDs(): " +
								 * objPrtIdset4PM.getSrcPartyIDs());
								 */
								// PMStatic.ReadLine();
								
								// Krishna on August 19th 2011
								ProcessMDMClassifications(objPrtIdset4PM.getGCPartyIDs(),objPrtIdset4PM.getSrcPartyIDs(),PMStatic.PERSON_PARTY_CLASS);
								wsMDMBinding.DeleteMDMTemp(PMStatic.PERSON_PARTY_CLASS,objPrtIdset4PM.getSrcPartyIDs());
								wsMDMBinding.DeleteMatchedRecordsforPartyIds(objPrtIdset4PM.getSrcPartyIDs());
								wsMDMBinding.DeleteClassificationsTemp(PMStatic.PERSON_PARTY_CLASS,objPrtIdset4PM.getSrcPartyIDs());
							}
						}
					} catch (Exception rExp) {
						LOGGER.info("Problem while ProcessMDMSourcePersonPotentialRecords Matched Records : "
								+ rExp.getMessage());
						rExp.printStackTrace();
					}
					Integer iMatcdRcdCnt4EM = 0;
					try {
						iMatcdRcdCnt4EM = wsMDMBinding.getMatchedRecordCount(
								PMStatic.EXACT_MATCH_TYPE,
								PMStatic.PERSON_PARTY_CLASS);
						if (iMatcdRcdCnt4EM > 0) {
							LOGGER.info("Before  processMDMSourceExactMatchRecords");
							// PMStatic.ReadLine();
							PartyIdSet objPrtIdset4EM = wsMDMBinding
									.ProcessMDMSourceExactMatchRecords(
											PMStatic.PERSON_PARTY_CLASS,
											PMStatic.EXACT_MATCH_TYPE);
							if (objPrtIdset4EM != null
									&& objPrtIdset4EM.getPartyIDsGroup().length > 0) {
								// Krishna on August 19th 2011
								wsMDMBinding.DeleteMDMTemp(PMStatic.PERSON_PARTY_CLASS,objPrtIdset4EM.getSrcPartyIDs());
								wsMDMBinding.DeleteMatchedRecordsforPartyIds(objPrtIdset4EM.getSrcPartyIDs());
								// Need to change the logic for direct delete of records from Classifications
								wsMDMBinding.DeleteClassificationsTemp(PMStatic.PERSON_PARTY_CLASS,objPrtIdset4EM.getSrcPartyIDs());
							}
							// PMStatic.ReadLine();
						}
						
						/*int latestVal = iCnt;
						if (iCnt == 0) {
							// At the start up
							latestBlockSize.put(
									PMStatic.LATEST_NUMBER_OF_PERSON_PROCESSED_REC,
									iCnt);
						} else if (latestVal == iMDMPersonsCount - 1) {
							// Processing last time ... hence set the total number of
							// mdm count as the processed records
							latestBlockSize.put(
									PMStatic.LATEST_NUMBER_OF_PERSON_PROCESSED_REC,
									mdmTempPersonCnt);
						} else {
							latestBlockSize.put(
									PMStatic.LATEST_NUMBER_OF_PERSON_PROCESSED_REC,
									PMStatic.TEMP_MAX_RECORDS * latestVal);
						}*/

					} catch (Exception rExp) {
						LOGGER.info("Problem while ProcessMDMSourcePersonPotentialRecords Matched Records : "
								+ rExp.getMessage());
						rExp.printStackTrace();
					}

					LOGGER.info("StartPersonsPMEngine Done Page:" + (iCnt + 1));
					objMRC.showDataVolumeinSFDC();
					// PMStatic.ReadLine();

				} catch (Exception rExp) {
					LOGGER.info("Problem while processMDM Matched Records : "
							+ rExp.getMessage());
					rExp.printStackTrace();
				}

			}
		} catch (Exception Exp) {
			LOGGER.info("Problem while Processing MDM Bulk Records : "
					+ Exp.getMessage());
			Exp.printStackTrace();
		}
	}

	private void StartOrgsPMEngine() {

		int mdmTempOrgCnt = MDMRecordsCount.startUpRecordSet
				.get(PMStatic.MDM_TEMP_ORG_COUNT);

		try {
			SoapConnection wsMDMBinding = objWSAccess
					.getWSPotMatchEngineConnection();
			PMAttributes[] objPMAs = wsMDMBinding.getAllAttributes();
			MapFields[][] goldenOrgs = BringEverythingFromGCOrgs();
			ArrayList<AttribScoreMap> latrScoreMap = objPCM.prepareAllAttributeKeyMap(objPMAs,PMStatic.ORG_PARTY_CLASS);
			Print("objMRC.MDMOrgsCount: " + mdmTempOrgCnt);
			Integer iMDMOrgsCount = GetSourcePageBlockSize(mdmTempOrgCnt);
			Print("StartOrgsPMEngine  iMDMOrgCount: " + iMDMOrgsCount);
			// PMStatic.ReadLine();
			// iMDMOrgsCount=1;
			for (Integer iCnt = 0; iCnt < iMDMOrgsCount; iCnt++) {
				LOGGER.info("Now you are doing again... for next Block "
						+ (iCnt + 1));
				MapFields[] orgsFields = wsMDMBinding.getOrgDetails();
				try {
					PMMatchedRecord[] lobjPMMR = RunComparisonEngineMap(
							PMStatic.ORG_PARTY_CLASS, goldenOrgs, orgsFields,
							latrScoreMap);
					// Refresh global container
					objPMC.refreshMatchedRecords();
					// objMRC.ShowDataVolumeinSFDC();
					// PMStatic.ReadLine();
					if (lobjPMMR != null && lobjPMMR.length > 0) {
						LOGGER.info("I got here total matched Records"
								+ lobjPMMR.length);
						wsMDMBinding.AddMatchedRecords(lobjPMMR);
						objMRC.showDataVolumeinSFDC();
						// PMStatic.ReadLine();
					}
				}

				catch (Exception ioExp) {
					LOGGER.info("Problem while Adding Matched Records ioExp: "
							+ ioExp.getMessage());
					ioExp.printStackTrace();
				}
				try {

					try {
						Integer iMatcdRcdCnt4GC = wsMDMBinding
								.getMatchedRecordCount(PMStatic.NO_MATCH_TYPE,
										PMStatic.ORG_PARTY_CLASS);
						if (iMatcdRcdCnt4GC > 0) {
							// wsMDMBinding.processMDMMatchedRecords();
							LOGGER.info("Before  processMDMSourceOrgsGoldenRecords");
							// PMStatic.ReadLine();
							PartyIdSet objPrtIdset4GC = wsMDMBinding
									.ProcessMDMSourceOrgsGoldenRecords();
							if (objPrtIdset4GC != null
									&& objPrtIdset4GC.getPartyIDsGroup().length > 0) {
								/*
								 * objPMD.CreateOrgMappingPartyIds(objPrtIdset4GC
								 * .getPartyIDsGroup());
								 * objPMD.AssigningProcessedPartyIds(
								 * PMStatic.ORG_PARTY_CLASS,
								 * PMStatic.NO_MATCH_TYPE,
								 * objPrtIdset4GC.getGCPartyIDs(),
								 * objPrtIdset4GC.getSrcPartyIDs());
								 * LOGGER.info(
								 * " AssigningProcessedPartyIds : objPrtIdset4GC.getGCPartyIDs() length:"
								 * + objPrtIdset4GC.getGCPartyIDs().length +
								 * "objPrtIdset.getSrcPartyIDs(): " +
								 * objPrtIdset4GC.getSrcPartyIDs());
								 */
								// PMStatic.ReadLine();
								// Krishna on August 19th 2011
								ProcessMDMClassifications(objPrtIdset4GC.getGCPartyIDs(),objPrtIdset4GC.getSrcPartyIDs(),PMStatic.ORG_PARTY_CLASS);
								wsMDMBinding.DeleteMDMTemp(PMStatic.ORG_PARTY_CLASS,objPrtIdset4GC.getSrcPartyIDs());
								wsMDMBinding.DeleteMatchedRecordsforPartyIds(objPrtIdset4GC.getSrcPartyIDs());
								wsMDMBinding.DeleteClassificationsTemp(PMStatic.ORG_PARTY_CLASS,objPrtIdset4GC.getSrcPartyIDs());
							}
						}

					} catch (Exception Exp) {
						LOGGER.info("Problem while Processing processMDMSourceOrgsGoldenRecords Records : "
								+ Exp.getMessage());
						Exp.printStackTrace();
					}

					try {

						Integer iMatcdRcdCnt4PM = wsMDMBinding
								.getMatchedRecordCount(PMStatic.POT_MATCH_TYPE,
										PMStatic.ORG_PARTY_CLASS);
						if (iMatcdRcdCnt4PM > 0) {
							PartyIdSet objPrtIdset4PM = wsMDMBinding
									.ProcessMDMSourceOrgsPotentialRecords();
							if (objPrtIdset4PM != null
									&& objPrtIdset4PM.getPartyIDsGroup().length > 0) {
								/*
								 * objPMD.CreateOrgMappingPartyIds(objPrtIdset4PM
								 * .getPartyIDsGroup());
								 * objPMD.AssigningProcessedPartyIds(
								 * PMStatic.ORG_PARTY_CLASS,
								 * PMStatic.POT_MATCH_TYPE,
								 * objPrtIdset4PM.getGCPartyIDs(),
								 * objPrtIdset4PM.getSrcPartyIDs());
								 * LOGGER.info(
								 * " AssigningProcessedPartyIds : objPrtIdset4GC.getGCPartyIDs() length:"
								 * + objPrtIdset4PM.getGCPartyIDs().length +
								 * "objPrtIdset.getSrcPartyIDs(): " +
								 * objPrtIdset4PM.getSrcPartyIDs());
								 */
								// PMStatic.ReadLine();
								// Krishna on August 19th 2011
								ProcessMDMClassifications(objPrtIdset4PM.getGCPartyIDs(),objPrtIdset4PM.getSrcPartyIDs(),	PMStatic.ORG_PARTY_CLASS);
								wsMDMBinding.DeleteMDMTemp(PMStatic.ORG_PARTY_CLASS,objPrtIdset4PM.getSrcPartyIDs());
								wsMDMBinding.DeleteMatchedRecordsforPartyIds(objPrtIdset4PM.getSrcPartyIDs());
								wsMDMBinding.DeleteClassificationsTemp(PMStatic.ORG_PARTY_CLASS,objPrtIdset4PM.getSrcPartyIDs());
							}
						}

					} catch (Exception Exp) {
						LOGGER.info("Problem while Processing processMDMSourceOrgsPotentialRecords Records : "
								+ Exp.getMessage());
						Exp.printStackTrace();
					}
					// PMStatic.ReadLine();

					Integer iMatcdRcdCnt4EM = wsMDMBinding
							.getMatchedRecordCount(PMStatic.EXACT_MATCH_TYPE,
									PMStatic.ORG_PARTY_CLASS);
					if (iMatcdRcdCnt4EM > 0) {
						LOGGER.info("Before org processMDMSourceExactMatchRecords");
						// PMStatic.ReadLine();
						PartyIdSet objPrtIdset4EM = wsMDMBinding
								.ProcessMDMSourceExactMatchRecords(
										PMStatic.ORG_PARTY_CLASS,
										PMStatic.EXACT_MATCH_TYPE);

						if (objPrtIdset4EM != null
								&& objPrtIdset4EM.getPartyIDsGroup().length > 0) {
							// Krishna on August 19th 2011
							wsMDMBinding.DeleteMDMTemp(PMStatic.ORG_PARTY_CLASS,objPrtIdset4EM.getSrcPartyIDs());
							wsMDMBinding.DeleteMatchedRecordsforPartyIds(objPrtIdset4EM.getSrcPartyIDs());
							wsMDMBinding.DeleteClassificationsTemp(PMStatic.ORG_PARTY_CLASS,objPrtIdset4EM.getSrcPartyIDs());
							LOGGER.info("StartOrgsPMEngine Done Page:"
									+ (iCnt + 1));

						}
						objMRC.showDataVolumeinSFDC();
						// PMStatic.ReadLine();
					}
					
				/*	int latestVal = iCnt;
					if (iCnt == 0) {
						// At the start up
						latestBlockSize.put(
								PMStatic.LATEST_NUMBER_OF_ORG_PROCESSED_REC,
								PMStatic.TEMP_MAX_RECORDS);
					} else if (latestVal == mdmTempOrgCnt - 1) {
						// Processing last time ... hence set the total number of
						// mdm count as the processed records
						latestBlockSize.put(
								PMStatic.LATEST_NUMBER_OF_ORG_PROCESSED_REC,
								mdmTempOrgCnt);
					} else {
						latestBlockSize.put(
								PMStatic.LATEST_NUMBER_OF_ORG_PROCESSED_REC,
								PMStatic.TEMP_MAX_RECORDS * latestVal);
					}*/

				}
				// catch(RemoteException rExp)
				catch (Exception rExp) {
					LOGGER.info("Problem while processMDM Matched Records : "
							+ rExp.getMessage());
					rExp.printStackTrace();
				}

			}
		} catch (Exception Exp) {
			LOGGER.info("Problem while Processing MDM Bulk Records : "
					+ Exp.getMessage());
			Exp.printStackTrace();
		}

	}

	public static void main(String[] args) {
		IPMEngineNew objPME = new PMEngineNew();

	}

	private Integer GetGCPageBlockSize(Integer iGCCnt) {

		Print("iGCPersonCnt: " + iGCCnt);
		float fGCPageBlock = iGCCnt / PMStatic.GC_MAX_RECORDS;
		Print("Page Block in float : " + fGCPageBlock);
		Integer iPgBlkSize = iGCCnt / PMStatic.GC_MAX_RECORDS;
		if (iGCCnt < 10000) {
			if (fGCPageBlock * 10 > 10)
				iPgBlkSize++;

		} else if (iGCCnt > 10000 && iGCCnt < 100000) {
			if (fGCPageBlock * 10 > 100)
				iPgBlkSize++;
		} else if (iGCCnt > 100000 && iGCCnt < 1000000) {
			if (fGCPageBlock * 10 > 1000)
				iPgBlkSize++;
		}

		Print("iPgBlkSize: " + iPgBlkSize);
		// ////PMStatic.ReadLine();

		return iPgBlkSize;
	}

	private Integer GetSourcePageBlockSize(Integer iSrcCnt) {

		Print("iSrcCnt: " + iSrcCnt);
		Integer iPgBlkSize;

		if (iSrcCnt == 0) {
			iPgBlkSize = 0;
		} else if (iSrcCnt <= PMStatic.TEMP_MAX_RECORDS) {
			iPgBlkSize = 1;
		} else {

			float fSrcPageBlock = iSrcCnt / PMStatic.TEMP_MAX_RECORDS;
			Print("Page Block in float : " + fSrcPageBlock);
			iPgBlkSize = iSrcCnt / PMStatic.TEMP_MAX_RECORDS;
			if (iSrcCnt < 10000) {
				if (fSrcPageBlock * 10 > 10)
					iPgBlkSize++;

			} else if (iSrcCnt > 10000 && iSrcCnt < 100000) {
				if (fSrcPageBlock * 10 > 100)
					iPgBlkSize++;
			} else if (iSrcCnt > 100000 && iSrcCnt < 1000000) {
				if (fSrcPageBlock * 10 > 1000)
					iPgBlkSize++;
			}

			Print("iPgBlkSize: " + iPgBlkSize);
			// ////PMStatic.ReadLine();
		}
		return iPgBlkSize;
	}

	private MapFields[][] BringEverythingFromGCPersons() {
		MapFields[][] objGCPersons;

		try {
			Integer iGCPersonCnt = MDMRecordsCount.startUpRecordSet
					.get(PMStatic.GC_PERSON_COUNT);
			;
			Print("iGCPersonCnt size:" + iGCPersonCnt);

			Integer iGCPageBlock;
			if (iGCPersonCnt <= PMStatic.GC_MAX_RECORDS) {
				iGCPageBlock = 1;
			} else {
				iGCPageBlock = GetGCPageBlockSize(iGCPersonCnt);
			}

			objGCPersons = new MapFields[iGCPageBlock][PMStatic.GC_MAX_RECORDS];
			SoapConnection wsMDMBinding = objWSAccess
					.getWSPotMatchEngineConnection();
			String[] personPartyIds = wsMDMBinding.getGCPersonPartyIds();
			Print("personPartyIds size:" + personPartyIds.length);
			// ////PMStatic.ReadLine();
			String[][] batchPersonPartyIds = new String[iGCPageBlock][PMStatic.GC_MAX_RECORDS];
			Integer iIndex = 0;
			Integer iMaxCnt = 0;

			if (iGCPersonCnt > PMStatic.GC_MAX_RECORDS) {
				iMaxCnt = PMStatic.GC_MAX_RECORDS;
			} else {
				iMaxCnt = iGCPersonCnt;
			}
			for (Integer iCnt = 0; iCnt < iGCPageBlock; iCnt++) {
				for (Integer jCnt = 0; jCnt < iMaxCnt; jCnt++) {
					if (iIndex < iGCPersonCnt) {
						batchPersonPartyIds[iCnt][jCnt] = personPartyIds[iIndex];
						iIndex++;
					}
				}
			}

			for (Integer iCnt = 0; iCnt < iGCPageBlock; iCnt++) {
				MapFields[] personsGCFields = wsMDMBinding.getGCPersonDetails(
						iCnt, batchPersonPartyIds[iCnt]);
				objGCPersons[iCnt] = personsGCFields;
			}
			/*
			 * for(Integer iCnt=0;iCnt<6;iCnt++) { Print("objGCPersons["+iCnt+
			 * "]: "+objGCPersons[iCnt]); }
			 */

			return objGCPersons;

		} catch (Exception e) {
			LOGGER.info("Problem while getting person & Org details: "
					+ e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	private MapFields[][] BringEverythingFromGCOrgs() {
		MapFields[][] objGCOrgs;

		try {
			Integer iGCOrgCnt = MDMRecordsCount.startUpRecordSet
					.get(PMStatic.GC_ORG_COUNT);
			;
			LOGGER.info("iGCOrgCnt size:" + iGCOrgCnt);
			Integer iGCPageBlock;
			if (iGCOrgCnt <= PMStatic.GC_MAX_RECORDS) {
				iGCPageBlock = 1;
			} else {
				iGCPageBlock = GetGCPageBlockSize(iGCOrgCnt);
			}

			objGCOrgs = new MapFields[iGCPageBlock][PMStatic.GC_MAX_RECORDS];
			SoapConnection wsMDMBinding = objWSAccess
					.getWSPotMatchEngineConnection();
			String[] OrgPartyIds = wsMDMBinding.getGCOrgPartyIds();
			Print("OrgPartyIds size:" + OrgPartyIds.length);
			// ////PMStatic.ReadLine();
			String[][] batchOrgPartyIds = new String[iGCPageBlock][PMStatic.GC_MAX_RECORDS];
			Integer iIndex = 0;

			Integer iMaxCnt = 0;

			if (iGCOrgCnt > PMStatic.GC_MAX_RECORDS) {
				iMaxCnt = PMStatic.GC_MAX_RECORDS;
			} else {
				iMaxCnt = iGCOrgCnt;
			}

			for (Integer iCnt = 0; iCnt < iGCPageBlock; iCnt++) {
				for (Integer jCnt = 0; jCnt < iGCOrgCnt; jCnt++) {
					if (iIndex < iGCOrgCnt) {
						batchOrgPartyIds[iCnt][jCnt] = OrgPartyIds[iIndex];
						iIndex++;
					}
				}
			}

			for (Integer iCnt = 0; iCnt < iGCPageBlock; iCnt++) {
				MapFields[] orgsGCFields = wsMDMBinding.getGCOrgDetails(iCnt,
						batchOrgPartyIds[iCnt]);
				objGCOrgs[iCnt] = orgsGCFields;
			}

			/*
			 * for(Integer iCnt=0;iCnt<6;iCnt++) { Print("objGCOrgs["+iCnt+
			 * "]: "+objGCOrgs[iCnt]); }
			 */

			return objGCOrgs;

		} catch (Exception e) {
			LOGGER.info("Problem while getting Org & Org details: "
					+ e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	private PMMatchedRecord[] RunComparisonEngineMap(String sPartyClass,
			MapFields[][] goldenRecords, MapFields[] mdmRecords,
			ArrayList<AttribScoreMap> latrScoreMap) {
		boolean bDone;
		try {

			if (mdmRecords.length > 0) {
				Print("Currently Processing MDM Size:" + mdmRecords.length);
				Print("Currently Processing GC 1K * pageblock Size:"
						+ goldenRecords.length);
				for (Integer iCnt = 0; iCnt < goldenRecords.length; iCnt++) {
					Print("Persons Golden Copy Size:"
							+ goldenRecords[iCnt].length);
					// //////PMStatic.ReadLine();
					bDone = objPMC.CreatePotMatchedRecordsByMatchedCriteriaMap(
							sPartyClass, latrScoreMap, mdmRecords,
							goldenRecords[iCnt]);
					
					/*bDone = objPMC.CreatePotMatchedRecordsByMatchedCriteria(
							sPartyClass, objPMAs, mdmRecords,
							goldenRecords[iCnt]);*/
				}
			}

			// LOGGER.info("Pot Match Org Done :" +bDone);
			PMMatchedRecord[] objPMMR = objPMC.GetMatchedRecords();
			objPMC.ShowMatchedRecords(objPMMR);

			return objPMMR;

		} catch (Exception e) {
			LOGGER.info("Problem while getting person & Org details: "
					+ e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	private PMMatchedRecord[] RunComparisonEngine(String sPartyClass,
			MapFields[][] goldenRecords, MapFields[] mdmRecords,
			PMAttributes[] objPMAs) {
		boolean bDone;
		try {

			if (mdmRecords.length > 0) {
				Print("Currently Processing MDM Size:" + mdmRecords.length);
				Print("Currently Processing GC 1K * pageblock Size:"
						+ goldenRecords.length);
				for (Integer iCnt = 0; iCnt < goldenRecords.length; iCnt++) {
					Print("Persons Golden Copy Size:"
							+ goldenRecords[iCnt].length);
					// //////PMStatic.ReadLine();
					bDone = objPMC.CreatePotMatchedRecordsByMatchedCriteria(
							sPartyClass, objPMAs, mdmRecords,
							goldenRecords[iCnt]);
					
					/*bDone = objPMC.CreatePotMatchedRecordsByMatchedCriteria(
							sPartyClass, objPMAs, mdmRecords,
							goldenRecords[iCnt]);*/
				}
			}

			// LOGGER.info("Pot Match Org Done :" +bDone);
			PMMatchedRecord[] objPMMR = objPMC.GetMatchedRecords();
			objPMC.ShowMatchedRecords(objPMMR);

			return objPMMR;

		} catch (Exception e) {
			LOGGER.info("Problem while getting person & Org details: "
					+ e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	private void CheckWSData(MapFields[] mapFields, MapFields[] mapGCFields) {
		// MDM Temp Data Fields
		if (mapFields != null) {

			for (MapFields wsMPs : mapFields) {
				LOGGER.info("Party Id: " + wsMPs.getPartyID());
				LOGGER.info("Index : " + wsMPs.getIndex());
				for (MapField wsMP : wsMPs.getFields()) {
					LOGGER.info("Field Name: " + wsMP.getFieldName());
					LOGGER.info("Field Value: " + wsMP.getFieldValue());
				}
			}
		}
		// Golden Copy Data Fields
		if (mapGCFields != null) {
			for (MapFields wsGCMPs : mapGCFields) {
				LOGGER.info("Party Id: " + wsGCMPs.getPartyID());
				LOGGER.info("Index : " + wsGCMPs.getIndex());
				for (MapField wsMP : wsGCMPs.getFields()) {
					LOGGER.info("Field Name: " + wsMP.getFieldName());
					LOGGER.info("Field Value: " + wsMP.getFieldValue());
				}
			}
		}
	}

	private void Print(String strValue) {
		LOGGER.info(strValue);
	}
	
	/*private void CheckMultiKeyMap()
	{
		
		*//**
		 * goldenMap should be populated every time you add or delete golden
		 * copy. It would act like a cache in VMForce, and will be exact copy of
		 * Salesforce golden table. The size would not be matter at all, since
		 * every map entry will be consist of (scoring facors + Goden key), so
		 * by logic, 512 MD RAM would be able to hold more than 2-3 millon data.
		 * 
		 * Point 1 : For every golden entry record, there will be (no. scoring
		 * factor -1) entries. See the example. Person account is holding 3
		 * scoring factors. So, start with Most scoring factors.
		 * 
		 * Hence, if salesforce is having 30,000 entries, cache will
		 * hold....90,000 entries. To create that, no of loop will be maximum
		 * 30,000.
		 *//*

		MultiKeyMap goldenMap = new MultiKeyMap();

		for (int i = 1; i < 30000; i++) {
			{

				MultiKey mKey = new MultiKey("Sumnata"+i, "Biswas"+i);
				MultiKey mKey2 = new MultiKey("1234"+i, mKey);
				goldenMap.put(mKey, "11111a"+i);
				goldenMap.put(mKey2, "11111b"+i);
			}
			{
				MultiKey mKey = new MultiKey("Hari"+i, "Krisna"+i);
				MultiKey mKey2 = new MultiKey("7894"+i, mKey);
				goldenMap.put(mKey, "2222b"+i);
				goldenMap.put(mKey2, "2222b"+i);
			}
		}
		*//**
		 * CSV file, Extract the values regarding scoring factors example (SSN, FName - depending on presence in CSV)
		 * create a key and checks in CSV file.
		 *//*

		
		 * MultiKey mKey = new MultiKey("Sumnata", "Biswas"); MultiKey mKey2 =
		 * new MultiKey("1234", mKey);
		 * 
		 * System.out.println(map.get(mKey));
		 * System.out.println(map.get(mKey2));
		 
		
		MultiKey mKey = new MultiKey("Sumnata1", "Biswas1");
		Long l = System.currentTimeMillis();
		System.out.println(goldenMap.get(mKey) + ">>" + (l - System.currentTimeMillis()));

	}*/
	
	
	

}
