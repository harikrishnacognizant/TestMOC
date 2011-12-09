package com.mdm.processor.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mdm.processor.exception.BaseExceptionFactory;
import com.mdm.processor.exception.BaseExceptionUtil;
import com.mdm.processor.service.IMDMRecordsCount;
import com.mdm.processor.service.IMDMWebServiceAccess;
import com.sforce.soap.WS_POTMatch.SoapConnection;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class MDMRecordsCount implements IMDMRecordsCount {

	@Autowired
	IMDMWebServiceAccess objWSAccess;

	// //Thread safe map to hold POT match engine start up record set
	// public final static Map<String,Integer> startUpRecordSet =
	// Collections.synchronizedMap(new HashMap<String,Integer>(1));
	//
	// //Thread safe map to hold POT match engine updated record set
	// public final static Map<String,Integer> upToDateRecordSet =
	// Collections.synchronizedMap(new HashMap<String,Integer>(1));

	/*
	 * public static final String GC_ORG_COUNT = "GC_ORG_COUNT"; public static
	 * final String GC_PERSON_COUNT = "GC_PERSON_COUNT"; public static final
	 * String PM_ORG_COUNT = "PM_ORG_COUNT"; public static final String
	 * PM_PERSON_COUNT = "PM_PERSON_COUNT"; public static final String
	 * EM_ORG_COUNT = "EM_ORG_COUNT"; public static final String EM_PERSON_COUNT
	 * = "EM_PERSON_COUNT";
	 */

	// private Integer MDMPersonsCount;
	// private Integer MDMOrgsCount;
	// private Integer GCPersonsCount;
	// private Integer GCOrgsCount;
	// private Integer MDMCLTCnt;
	// private Integer MDMASTCnt;
	// private Integer GCPersonVCCnt;
	// private Integer GCOrgsVCCnt;
	// private Integer POTMatchCount;
	// private Integer GoldenCopyCount;

	// public Integer getMDMPersonsCount() {
	// return MDMPersonsCount;
	// }
	//
	// public void setMDMPersonsCount(Integer mDMPersonsCount) {
	// MDMPersonsCount = mDMPersonsCount;
	// }
	//
	// public Integer getMDMOrgsCount() {
	// return MDMOrgsCount;
	// }
	//
	// public void setMDMOrgsCount(Integer mDMOrgsCount) {
	// MDMOrgsCount = mDMOrgsCount;
	// }
	//
	// public Integer getGCPersonsCount() {
	// return GCPersonsCount;
	// }
	//
	// public void setGCPersonsCount(Integer gCPersonsCount) {
	// GCPersonsCount = gCPersonsCount;
	// }
	//
	// public Integer getGCOrgsCount() {
	// return GCOrgsCount;
	// }
	//
	// public void setGCOrgsCount(Integer gCOrgsCount) {
	// GCOrgsCount = gCOrgsCount;
	// }
	//
	// public Integer getMDMCLTCnt() {
	// return MDMCLTCnt;
	// }
	//
	// public void setMDMCLTCnt(Integer mDMCLTCnt) {
	// MDMCLTCnt = mDMCLTCnt;
	// }
	//
	// public Integer getMDMASTCnt() {
	// return MDMASTCnt;
	// }
	//
	// public void setMDMASTCnt(Integer mDMASTCnt) {
	// MDMASTCnt = mDMASTCnt;
	// }
	//
	// public Integer getGCPersonVCCnt() {
	// return GCPersonVCCnt;
	// }
	//
	// public void setGCPersonVCCnt(Integer gCPersonVCCnt) {
	// GCPersonVCCnt = gCPersonVCCnt;
	// }
	//
	// public Integer getGCOrgsVCCnt() {
	// return GCOrgsVCCnt;
	// }
	//
	// public void setGCOrgsVCCnt(Integer gCOrgsVCCnt) {
	// GCOrgsVCCnt = gCOrgsVCCnt;
	// }

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MDMRecordsCount.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mdm.processor.service.IMDMRecordsCount#GettingPartyAssociationsCount
	 * ()
	 */
	@Override
	public Integer gettingPartyAssociationsCount() {
		Integer iCnt = 0;

		try {
			PartnerConnection connection = objWSAccess.getPartnerConnection();
			String soqlQry = "Select count(Id) from Party_Associations__c";

			QueryResult qr = connection.query(soqlQry);

			LOGGER.info("MDM GC PartyAssociations Count: " + qr.toString());
			iCnt = qr.getRecords().length;
			LOGGER.info("MDM GC PartyAssociations Count: " + iCnt);
		} catch (ConnectionException connExp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(connExp));
			throw BaseExceptionFactory.getInstance(connExp);
		} catch (Exception exp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(exp));
			throw BaseExceptionFactory.getInstance(exp);
		}

		return iCnt;

	}

	public MDMRecordsCount() {
	}

	// public void getMDMRecordsCount(SoapConnection wsBinding,
	// IMDMWebServiceAccess objWSAccess) {
	//
	// SoapConnection wsMDMBinding =
	// objWSAccess.getWSPotMatchEngineConnection();
	//
	// try {
	// if (wsMDMBinding != null) {
	// MDMPersonsCount = wsMDMBinding.getPersonsCount();
	// MDMOrgsCount = wsMDMBinding.getOrgsCount();
	// GCPersonsCount = wsMDMBinding.getGCPersonsCount();
	// GCOrgsCount = wsMDMBinding.getGCOrgsCount();
	// MDMCLTCnt = wsMDMBinding.getTotalMDMTempClassificationsCount();
	// MDMASTCnt = wsMDMBinding.getTotalMDMTempAssociationsCount();
	// GCPersonVCCnt = wsMDMBinding
	// .getTotalValidClassificationsPersonCount();
	// GCOrgsVCCnt = wsMDMBinding
	// .getTotalValidClassificationsOrgCount();
	// }
	// } catch (ConnectionException connExp) {
	// LOGGER.error("Exception : "
	// + BaseExceptionUtil.printStackTrace(connExp));
	// throw BaseExceptionFactory.getInstance(connExp);
	// } catch (Exception exp) {
	// LOGGER.error("Exception : "
	// + BaseExceptionUtil.printStackTrace(exp));
	// throw BaseExceptionFactory.getInstance(exp);
	// }
	// }
	//
	// public Map getPieChartData(){
	//
	// Map<String, String> hashMap = new HashMap<String, String>();
	//
	// //Fetch the records and return
	//
	// SoapConnection wsMDMBinding =
	// objWSAccess.getWSPotMatchEngineConnection();
	//
	// int personGoldenCopy = 0;
	// int orgGoldenCopy = 0;
	// int personPotMatched = 0;
	//
	//
	// /*
	// * getTotalNumberOfGoldenRecords());
	// LOGGER.info(processStats.getTotalNumberOfNoMatchRecords());
	// LOGGER.info(processStats.getTotalNumberOfPOTMatchRecords());
	// LOGGER.info(processStats.getTotalNumberOfRecordsProcessed()
	// *
	// * */
	//
	// try {
	// if (wsMDMBinding != null) {
	// GoldenCopyCount = wsMDMBinding.getTotalGCPartyCount();
	// POTMatchCount = wsMDMBinding.getTotalPMPartyCount();
	//
	// GCPersonsCount = wsMDMBinding.getGCPersonsCount();
	// GCOrgsCount = wsMDMBinding.getGCOrgsCount();
	//
	// MDMCLTCnt = wsMDMBinding.getTotalMDMTempClassificationsCount();
	// MDMASTCnt = wsMDMBinding.getTotalMDMTempAssociationsCount();
	// GCPersonVCCnt = wsMDMBinding
	// .getTotalValidClassificationsPersonCount();
	// GCOrgsVCCnt = wsMDMBinding
	// .getTotalValidClassificationsOrgCount();
	// }
	// } catch (ConnectionException connExp) {
	// LOGGER.error("Exception : "
	// + BaseExceptionUtil.printStackTrace(connExp));
	// throw BaseExceptionFactory.getInstance(connExp);
	// } catch (Exception exp) {
	// LOGGER.error("Exception : "
	// + BaseExceptionUtil.printStackTrace(exp));
	// throw BaseExceptionFactory.getInstance(exp);
	// }
	//
	//
	//
	// return hashMap;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mdm.processor.service.IMDMRecordsCount#ShowDataVolumeinSFDC()
	 */
	public void init() {

		SoapConnection wsMDMBinding = objWSAccess
				.getWSPotMatchEngineConnection();

		try {
			if (wsMDMBinding != null) {
				if (startUpRecordSet != null && startUpRecordSet.isEmpty()) {
					// Testing Block on Records in MDM & GC
					// LOGGER.info(" Current Records Output in MDM World");
					// LOGGER.info(" ===================================");

					startUpRecordSet.put(PMStatic.GC_PERSON_COUNT,
							wsMDMBinding.getGCPersonsCount());
					startUpRecordSet.put(PMStatic.GC_ORG_COUNT,
							wsMDMBinding.getGCOrgsCount());
					startUpRecordSet.put(PMStatic.MDM_TEMP_PERSON_COUNT,
							wsMDMBinding.getPersonsCount());
					startUpRecordSet.put(PMStatic.MDM_TEMP_ORG_COUNT,
							wsMDMBinding.getOrgsCount());
					startUpRecordSet.put(PMStatic.MDM_TEMP_CLASSIF_COUNT,
							getClassificationTempCount());
					startUpRecordSet.put(PMStatic.MDM_TEMP_ASSOC_COUNT,
							getAssociationTempCount());

					// TODO : Implement code to validate start up exceptional
					// scenario
					//
					// if(wsMDMBinding.getMatchedRecordCount("NoMatch","Person")
					// wsMDMBinding.getMatchedRecordCount("PotMatch","Person")
					// LOGGER.info(" Matched Record Persons ExactMatch Count: "+
					// wsMDMBinding.getMatchedRecordCount("ExactMatch","Person"));
					// LOGGER.info(" Matched Record Organization NoMatch Count: "+
					// wsMDMBinding.getMatchedRecordCount("NoMatch","Organization"));
					// LOGGER.info(" Matched Record Organization PotMatch Count: "
					// +
					// wsMDMBinding.getMatchedRecordCount("PotMatch","Organization"));
					// LOGGER.info(" Matched Record Organization ExactMatch Count: "+
					// wsMDMBinding.getMatchedRecordCount("ExactMatch","Organization"))

					LOGGER.info(" MDM Temp Persons Count: "
							+ wsMDMBinding.getPersonsCount());
					LOGGER.info(" MDM Temp Organization Count: "
							+ wsMDMBinding.getOrgsCount());
					LOGGER.info(" GOlden Copy Persons Count: "
							+ wsMDMBinding.getGCPersonsCount());
					LOGGER.info(" GOlden Copy Organization Count: "
							+ wsMDMBinding.getGCOrgsCount());
					// LOGGER.info(" Getting Total ClassificationsCount : "+
					// wsMDMBinding.getTotalMDMTempAssociationsCount());
					// LOGGER.info(" Getting Total alMDMTempAssociationsCount : "+
					// wsMDMBinding.getTotalMDMTempAssociationsCount());

					LOGGER.info(" Getting Total MDMTempClassificationsCount : "
							+ wsMDMBinding
									.getTotalMDMTempClassificationsCount());
					LOGGER.info(" Getting Total TotalMDMTempAssociationsCount : "
							+ wsMDMBinding.getTotalMDMTempAssociationsCount());
					LOGGER.info(" Matched Record Persons NoMatch Count: "
							+ wsMDMBinding.getMatchedRecordCount("NoMatch",
									"Person"));
					LOGGER.info(" Matched Record Persons PotMatch Count: "
							+ wsMDMBinding.getMatchedRecordCount("PotMatch",
									"Person"));
					LOGGER.info(" Matched Record Persons ExactMatch Count: "
							+ wsMDMBinding.getMatchedRecordCount("ExactMatch",
									"Person"));
					LOGGER.info(" Matched Record Organization NoMatch Count: "
							+ wsMDMBinding.getMatchedRecordCount("NoMatch",
									"Organization"));
					LOGGER.info(" Matched Record Organization PotMatch Count: "
							+ wsMDMBinding.getMatchedRecordCount("PotMatch",
									"Organization"));
					LOGGER.info(" Matched Record Organization ExactMatch Count: "
							+ wsMDMBinding.getMatchedRecordCount("ExactMatch",
									"Organization"));

					LOGGER.info(" TotalPartyCount: "
							+ wsMDMBinding.getTotalPartyCount());
					LOGGER.info(" Persons TotalPartyCount: "
							+ wsMDMBinding.getPersonPartyCount());
					LOGGER.info(" Org TotalPartyCount: "
							+ wsMDMBinding.getOrgPartyCount());
					LOGGER.info(" Golden Copy TotalPartyCount: "
							+ wsMDMBinding.getTotalGCPartyCount());
					LOGGER.info(" Potential match TotalPartyCount: "
							+ wsMDMBinding.getTotalPMPartyCount());

					LOGGER.info(" ===================================");
				}
				// else if
			}

		} catch (ConnectionException connExp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(connExp));
			throw BaseExceptionFactory.getInstance(connExp);
		} catch (Exception exp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(exp));
			throw BaseExceptionFactory.getInstance(exp);
		}
	}

	@Override
	public void showDataVolumeinSFDC() {

		SoapConnection wsMDMBinding = objWSAccess
				.getWSPotMatchEngineConnection();

		try {
			if (wsMDMBinding != null) {
				if (upToDateRecordSet != null && upToDateRecordSet.isEmpty()) {
					// Testing Block on Records in MDM & GC
					// LOGGER.info(" Current Records Output in MDM World");
					// LOGGER.info(" ===================================");

					upToDateRecordSet.put(PMStatic.GC_PERSON_COUNT,
							wsMDMBinding.getGCPersonsCount());
					upToDateRecordSet.put(PMStatic.GC_ORG_COUNT,
							wsMDMBinding.getGCOrgsCount());
					upToDateRecordSet.put(PMStatic.MDM_TEMP_PERSON_COUNT,
							wsMDMBinding.getPersonsCount());
					upToDateRecordSet.put(PMStatic.MDM_TEMP_ORG_COUNT,
							wsMDMBinding.getOrgsCount());

				}
				// TODO : Implement code to validate start up exceptional
				// scenario
				//
				// if(wsMDMBinding.getMatchedRecordCount("NoMatch","Person")
				// wsMDMBinding.getMatchedRecordCount("PotMatch","Person")
				// LOGGER.info(" Matched Record Persons ExactMatch Count: "+
				// wsMDMBinding.getMatchedRecordCount("ExactMatch","Person"));
				// LOGGER.info(" Matched Record Organization NoMatch Count: "+
				// wsMDMBinding.getMatchedRecordCount("NoMatch","Organization"));
				// LOGGER.info(" Matched Record Organization PotMatch Count: " +
				// wsMDMBinding.getMatchedRecordCount("PotMatch","Organization"));
				// LOGGER.info(" Matched Record Organization ExactMatch Count: "+
				// wsMDMBinding.getMatchedRecordCount("ExactMatch","Organization"))

				LOGGER.info(" MDM Temp Persons Count: "
						+ wsMDMBinding.getPersonsCount());
				LOGGER.info(" MDM Temp Organization Count: "
						+ wsMDMBinding.getOrgsCount());

				LOGGER.info(" GOlden Copy Persons Count: "
						+ wsMDMBinding.getGCPersonsCount());

				LOGGER.info(" GOlden Copy Organization Count: "
						+ wsMDMBinding.getGCOrgsCount());

				LOGGER.info(" Getting Total MDMTempClassificationsCount : "
						+ wsMDMBinding.getTotalMDMTempClassificationsCount());
				LOGGER.info(" Getting Total TotalMDMTempAssociationsCount : "
						+ wsMDMBinding.getTotalMDMTempAssociationsCount());
				LOGGER.info(" Matched Record Persons NoMatch Count: "
						+ wsMDMBinding.getMatchedRecordCount("NoMatch",
								"Person"));
				LOGGER.info(" Matched Record Persons PotMatch Count: "
						+ wsMDMBinding.getMatchedRecordCount("PotMatch",
								"Person"));
				LOGGER.info(" Matched Record Persons ExactMatch Count: "
						+ wsMDMBinding.getMatchedRecordCount("ExactMatch",
								"Person"));
				LOGGER.info(" Matched Record Organization NoMatch Count: "
						+ wsMDMBinding.getMatchedRecordCount("NoMatch",
								"Organization"));
				LOGGER.info(" Matched Record Organization PotMatch Count: "
						+ wsMDMBinding.getMatchedRecordCount("PotMatch",
								"Organization"));
				LOGGER.info(" Matched Record Organization ExactMatch Count: "
						+ wsMDMBinding.getMatchedRecordCount("ExactMatch",
								"Organization"));
				LOGGER.info(" TotalPartyCount: "
						+ wsMDMBinding.getTotalPartyCount());
				LOGGER.info(" Persons TotalPartyCount: "
						+ wsMDMBinding.getPersonPartyCount());
				LOGGER.info(" Org TotalPartyCount: "
						+ wsMDMBinding.getOrgPartyCount());
				LOGGER.info(" Golden Copy TotalPartyCount: "
						+ wsMDMBinding.getTotalGCPartyCount());
				LOGGER.info(" Potential match TotalPartyCount: "
						+ wsMDMBinding.getTotalPMPartyCount());

				LOGGER.info(" ===================================");
			}
			// else if

		} catch (ConnectionException connExp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(connExp));
			throw BaseExceptionFactory.getInstance(connExp);
		} catch (Exception exp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(exp));
			throw BaseExceptionFactory.getInstance(exp);
		}
	}

	public long getTotal() {

//		long total = 0;
//
//		long personCount = 0;
//		long orgCount = 0;
//		long classifTempCount = 0;
//		long assocTempCount = 0;
//
//		if (!CollectionHelper.isNullorEmpty(startUpRecordSet)) {
//			if (CollectionHelper.isHavingValue(startUpRecordSet,
//					PMStatic.MDM_TEMP_PERSON_COUNT)) {
//				personCount = Long.parseLong(startUpRecordSet.get(
//						PMStatic.MDM_TEMP_PERSON_COUNT).toString());
//			}
//			if (CollectionHelper.isHavingValue(startUpRecordSet,
//					PMStatic.MDM_TEMP_ORG_COUNT)) {
//				orgCount = Long.parseLong(startUpRecordSet.get(
//						PMStatic.MDM_TEMP_ORG_COUNT).toString());
//			}
//			if (CollectionHelper.isHavingValue(startUpRecordSet,
//					PMStatic.MDM_TEMP_CLASSIF_COUNT)) {
//				classifTempCount = Long.parseLong(startUpRecordSet.get(
//						PMStatic.MDM_TEMP_CLASSIF_COUNT).toString());
//			}
//			if (CollectionHelper.isHavingValue(startUpRecordSet,
//					PMStatic.MDM_TEMP_ASSOC_COUNT)) {
//				assocTempCount = Long.parseLong(startUpRecordSet.get(
//						PMStatic.MDM_TEMP_ASSOC_COUNT).toString());
//			}
//			
//		}
//
//		//total = personCount + orgCount + classifTempCount + assocTempCount;
//		total = personCount + orgCount +  assocTempCount;

		return getCurrentTotal();

	}

	
	public long getCurrentTotal() {

		long total = 0;

		long personCount = 0;
		long orgCount = 0;
		long classifTempCount = 0;
		long assocTempCount = 0;
		SoapConnection wsMDMBinding ;
		try
		{
		 wsMDMBinding = objWSAccess.getWSPotMatchEngineConnection();
		if(wsMDMBinding!=null)
		{
			personCount= wsMDMBinding.getPersonsCount();
			orgCount= wsMDMBinding.getOrgsCount();
			//classifTempCount= getClassificationTempCount();
			assocTempCount= getAssociationTempCount();
		
			//total = personCount + orgCount + classifTempCount + assocTempCount;

		total = personCount + orgCount +  assocTempCount;
		return total;
		}
		
		} catch (ConnectionException connExp) {
			LOGGER.error("ConnectionException : Unable to Initialize Connection with SFDC, Please click Setup Demo Button again. "
					+ BaseExceptionUtil.printStackTrace(connExp));
			throw BaseExceptionFactory.getInstance(connExp);
		} catch (Exception exp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(exp));
			throw BaseExceptionFactory.getInstance(exp);
		} finally {
			wsMDMBinding = null;
		}
		return total;

	}
	
	public long getExactMatchCount() {
		PartnerConnection connection;
		connection = objWSAccess.getPartnerConnection();
		// String soqlQry =
		// "Select count(id) from Exception_Header__c where Exception_type__c='"+PMStatic.EXACT_MATCH_TYPE
		// +"'";
		String soqlQry = "Select id from Exception_Header__c where Exception_type__c='"
				+ PMStatic.EXACT_MATCH_EXCEPTION + "'";
		QueryResult qr;
		long iExtCnt = 0;

		try {
			LOGGER.info("soqlQry : " + soqlQry);
			qr = connection.query(soqlQry);
			SObject[] objExMatCnt = qr.getRecords();
			iExtCnt = objExMatCnt.length;
			LOGGER.info("getExactMatchCount: " + iExtCnt);

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

		return iExtCnt;
	}

	public int getAssociationTempCount() {
		PartnerConnection connection;
		connection = objWSAccess.getPartnerConnection();
		// String soqlQry =
		// "Select count(id) from Exception_Header__c where Exception_type__c='"+PMStatic.EXACT_MATCH_TYPE
		// +"'";
		String soqlQry = "Select id from MDM_Association_Temp__c";
		QueryResult qr;
		int iAsscCnt = 0;

		try {
		
			LOGGER.info("soqlQry : " + soqlQry);
			qr = connection.query(soqlQry);
			SObject[] objAsscCnt = qr.getRecords();
			iAsscCnt = objAsscCnt.length;
			LOGGER.info("AssociationTempCount: " + iAsscCnt);

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

		return iAsscCnt;
	}

	public int getClassificationTempCount() {
		PartnerConnection connection;
		connection = objWSAccess.getPartnerConnection();
		// String soqlQry =
		// "Select count(id) from Exception_Header__c where Exception_type__c='"+PMStatic.EXACT_MATCH_TYPE
		// +"'";
		String soqlQry = "Select id from MDM_Classification_Temp__c";
		QueryResult qr;
		int iClssCnt = 0;

		try {
			SoapConnection wsMDMBinding = objWSAccess
					.getWSPotMatchEngineConnection();
			LOGGER.info("soqlQry : " + soqlQry);
			qr = connection.query(soqlQry);
			SObject[] objClassif = qr.getRecords();
			iClssCnt = objClassif.length;
			LOGGER.info("Classification TempCount: " + iClssCnt);

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

		return iClssCnt;
	}

	@Override
	public int getPotentialMatchDeterminantCount(String PartyClass,String MatchType) {
		return 0;
	}
	
	@Override
	public int getPotentialMatchDeterminantCount(String PartyClass) {
		PartnerConnection connection;
		connection = objWSAccess.getPartnerConnection();
		
		//String soqlQry = "Select  id  from Potential_Match_Attribute__c where Active_Flag__c=true and PM_Determinant__r.Party_Class__r.Party_Class_Code__c='"+PartyClass+"'";
		//String soqlQry = "Select  id  from Potential_Match_Attribute__c where Active_Flag__c=true and PM_Determinant__r.Party_Class__r.Party_Class_Code__c='"+PartyClass+"' and Match_Type__c='"+ MatchType +"'";
		String soqlQry = "Select id from Potential_Match_Determinant__c where Party_Class__r.Party_Class_Code__c='"+PartyClass+"'";
		QueryResult qr;
		int iCnt = 0;

		try {
			
			LOGGER.info("soqlQry : " + soqlQry);
			qr = connection.query(soqlQry);
			SObject[] objPMDtrmCnt = qr.getRecords();
			iCnt = objPMDtrmCnt.length;
			LOGGER.info("getExactMatchCount: " + iCnt);

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

		return iCnt;
	}
	
	public void reloadStartUpMap() {

		try {

			SoapConnection wsMDMBinding = objWSAccess
					.getWSPotMatchEngineConnection();
			if (wsMDMBinding != null) {
				startUpRecordSet.put(PMStatic.GC_PERSON_COUNT,
						wsMDMBinding.getGCPersonsCount());
				startUpRecordSet.put(PMStatic.GC_ORG_COUNT,
						wsMDMBinding.getGCOrgsCount());
				startUpRecordSet.put(PMStatic.MDM_TEMP_PERSON_COUNT,
						wsMDMBinding.getPersonsCount());
				startUpRecordSet.put(PMStatic.MDM_TEMP_ORG_COUNT,
						wsMDMBinding.getOrgsCount());
				startUpRecordSet.put(PMStatic.MDM_TEMP_CLASSIF_COUNT,
						getClassificationTempCount());
				startUpRecordSet.put(PMStatic.MDM_TEMP_ASSOC_COUNT,
						getAssociationTempCount());
				
				//LOGGER.info("reloadStartUpMap : MDM Temp org Count()" + wsMDMBinding.getOrgsCount());
				//LOGGER.info("reloadStartUpMap : MDM Temp Count()" + wsMDMBinding.getPersonsCount());
				//LOGGER.info("reloadStartUpMap : MDM Temp Classif Count()" + getClassificationTempCount());			
				//LOGGER.info("reloadStartUpMap : MDM Temp Association Count()" + getAssociationTempCount());
				
				//getCurrentTotal();
			
			}

		} catch (ConnectionException connExp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(connExp));
			throw BaseExceptionFactory.getInstance(connExp);
		} catch (Exception exp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(exp));
			throw BaseExceptionFactory.getInstance(exp);
		}

	}
}
