package com.mdm.processor.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mdm.processor.exception.BaseExceptionFactory;
import com.mdm.processor.exception.BaseExceptionUtil;
import com.mdm.processor.model.AttribScoreMap;
import com.sforce.soap.WS_POTMatch.*;

public class PMStatic {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PMStatic.class);

	public static String PERSON_PARTY_CLASS = "Person";
	public static String ORG_PARTY_CLASS = "Organization";
	public static Integer GC_MAX_RECORDS = 2000;
	public static Integer TEMP_MAX_RECORDS = 200;
	public static Integer TEMP_ASSOC_MAX_RECORDS = 2000;
	public static String NO_MATCH_TYPE = "NoMatch";
	public static String POT_MATCH_TYPE = "PotMatch";
	public static String EXACT_MATCH_TYPE = "ExactMatch";
	public static final String APPLICATION_PROPERTIES = "application.properties";
	public static final String GC_ORG_COUNT = "GC_ORG_COUNT";
	public static final String GC_PERSON_COUNT = "GC_PERSON_COUNT";
	public static final String PM_ORG_COUNT = "PM_ORG_COUNT";
	public static final String PM_PERSON_COUNT = "PM_PERSON_COUNT";
	public static final String EM_ORG_COUNT = "EM_ORG_COUNT";
	public static final String EM_PERSON_COUNT = "EM_PERSON_COUNT";
	public static final String MDM_TEMP_ORG_COUNT = "MDM_TEMP_ORG_COUNT";
	public static final String MDM_TEMP_PERSON_COUNT = "MDM_TEMP_PERSON_COUNT";
	public static final String MDM_TEMP_CLASSIF_COUNT = "MDM_TEMP_CLASSIF_COUNT";
	public static final String MDM_TEMP_ASSOC_COUNT = "MDM_TEMP_ASSOC_COUNT";

	public static final String PERSON_MAPED_PARTY_IDS = "PERSON_MAPED_PARTY_IDS";
	public static final String ORG_MAPED_PARTY_IDS = "ORG_MAPED_PARTY_IDS";
	public static final String ASSC_MAPED_TYPES = "ASSC_MAPED_TYPES";

	public static final String PARTNER_CONNECTION = "PARTNER_CONNECTION";
	public static final String ENTERPRISE_CONNECTION = "ENTERPRISE_CONNECTION";

	public static final String EXACT_MATCH_EXCEPTION = "Exact Match Exception";
	public static final String POT_MATCH_EXCEPTION = "Potential Match Exception";

	public static final String LATEST_BLOCK_SIZE = "LATEST_BLOCK_SIZE";

	public static final String LATEST_NUMBER_OF_PERSON_PROCESSED_REC = "LATEST_NUMBER_OF_PERSON_PROCESSED_REC";
	public static final String LATEST_NUMBER_OF_ORG_PROCESSED_REC = "LATEST_NUMBER_OF_ORG_PROCESSED_REC";
	public static final String LATEST_NUMBER_OF_ASSOC_PROCESSED_REC = "LATEST_NUMBER_OF_ASSOC_PROCESSED_REC";

	public static final String ENCODING = "UTF-8";

	public static String MDM_TEMP_FILE = "MDMTempFile";
	public static String MDM_ASSOCIATION_TEMP_FILE = "MDMAssociationTempFile";
	public static String MDM_CLASSIFICATION_TEMP_FILE = "MDMClassificationTempFile";

	public static Integer LOAD_TEMP_MAX_RECORDS = 200;
	
	public static String DATABASE_SELECT="SELECT ";
	public static String DATABASE_FROM=" FROM ";
	public static String DATABASE_LIMIT="LIMIT 200";
	public static String PM_EXACT_MATCH="Exact";
	public static String PM_MIS_MATCH="MisMatch";
	
	//This flag holds the current status of Pot Match Engine process
	public static boolean POTMATCH_PROCESS=false;
	

	public static void PrintPMMatchedRecord(PMMatchedRecord objMR) {
		// LOGGER.info("Match Record "+objMR.getMatchType()+" name "+objMR.getMdmRecord().getFields()getFieldValue()+" score "+objMR.getScore()+" GCPartyID "+objMR.getGCPartyID()+" CriteriaId "+objMR.getCriteriaId());
	}

	public static void PrintMatchedRecord(PMMatchedRecord objMR) {
		// LOGGER.info("Match Record "+objMR.getMatchType()+" name "+objMR.getMdmRecord().getFields(1).getFieldValue()+" score "+objMR.getScore()+" GCPartyID "+objMR.getGCPartyID()+" CriteriaId "+objMR.getCriteriaId());
	}

	public static void PrintArray(String[] aStrValue) {

		for (int iCnt = 0; iCnt < aStrValue.length; iCnt++) {
			LOGGER.info(iCnt + " Value: " + aStrValue[iCnt]);
		}
	}

	public static void Print(String strValue) {
		LOGGER.info(strValue);
	}

	public static String readValue(String fieldName) {
		File f = new File(PMStatic.APPLICATION_PROPERTIES);
		String fieldValue = "";
		// Get the inputStream
		InputStream inputStream = PMStatic.class.getClassLoader()
				.getResourceAsStream(PMStatic.APPLICATION_PROPERTIES);
		try {
			if (inputStream != null) {
				Properties pro = new Properties();
				// FileInputStream in;
				// in = new FileInputStream(f);
				pro.load(inputStream);
				fieldValue = pro.getProperty(fieldName);
			} else {
				LOGGER.error("No properties file File not found in the following destination "
						+ PMStatic.APPLICATION_PROPERTIES);
			}
		} catch (FileNotFoundException connExp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(connExp));
			throw BaseExceptionFactory.getInstance(connExp);
		} catch (Exception exp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(exp));
			throw BaseExceptionFactory.getInstance(exp);
		}
		return fieldValue;
	}

	
	public static void ReadLine() {
		// try{
		//
		// System.in.read();
		//
		// }catch(IOException ioExp)
		// {
		// LOGGER.info("Problem while input : " +ioExp.getMessage());
		// ioExp.printStackTrace();
		// }
	}
}
