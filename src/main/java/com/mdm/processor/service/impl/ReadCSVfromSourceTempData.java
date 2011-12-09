package com.mdm.processor.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.csvreader.CsvReader;
import com.mdm.processor.exception.BaseExceptionFactory;
import com.mdm.processor.exception.BaseExceptionUtil;
import com.mdm.processor.service.IMDMWebServiceAccess;
import com.mdm.processor.service.IReadCSVfromSourceTempData;
import com.mdm.processor.util.FileUtil;
import com.sforce.soap.WS_POTMatch.SoapConnection;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class ReadCSVfromSourceTempData implements IReadCSVfromSourceTempData{

	@Autowired
	IMDMWebServiceAccess objWSAccess;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReadCSVfromSourceTempData.class);

	
	
	
	private long getRecordCnt(String sFilePath)
	{
		CsvReader csvMDMTemp;
		int iTotRecCnt=0;
		try {
			csvMDMTemp = new CsvReader(
					FileUtil.getInputStream(sFilePath),
					FileUtil.getCharSet(PMStatic.ENCODING));
			
			while(csvMDMTemp.readRecord())
			{
				iTotRecCnt++;
			}
			
			PMStatic.Print("iTotRecCnt::::"+iTotRecCnt);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			csvMDMTemp=null;
		}
		return iTotRecCnt;
	}
	
	public void fetchnInsertMDMTempData(){
		
		PartnerConnection connection;
		try {
			
			String filePath = PMStatic.readValue(PMStatic.MDM_TEMP_FILE);
			Long iTotalRecordCnt =  getRecordCnt(filePath);
			
			//CsvReader csvMDMTemp = new CsvReader(PMStatic.readValue(PMStatic.MDM_TEMP_FILE));
			CsvReader csvMDMTemp = new CsvReader(
					FileUtil.getInputStream(PMStatic.readValue(PMStatic.MDM_TEMP_FILE)),
					FileUtil.getCharSet(PMStatic.ENCODING));
			csvMDMTemp.readHeaders();
			iTotalRecordCnt--;
			SObject[] lobjMDMTemp;
			 connection=objWSAccess.getPartnerConnection();
			int iCnt=0;
			
			if(iTotalRecordCnt<PMStatic.LOAD_TEMP_MAX_RECORDS)
				lobjMDMTemp=new SObject[Integer.parseInt(iTotalRecordCnt.toString())];
			else
				lobjMDMTemp=new SObject[PMStatic.LOAD_TEMP_MAX_RECORDS];
			SaveResult[] objRslt=null;
			while (csvMDMTemp.readRecord())
			{
				SObject objMDMTemp=new SObject();
				objMDMTemp.setType("MDM_Temp__c");
				objMDMTemp.setField("Party_Class_Code__c", csvMDMTemp.get("PARTY_CLASS_CODE__C"));
				objMDMTemp.setField("Party_ID__c", csvMDMTemp.get("PARTY_ID__C"));
				objMDMTemp.setField("First_Name__c", csvMDMTemp.get("FIRST_NAME__C"));
				objMDMTemp.setField("Last_Name__c", csvMDMTemp.get("LAST_NAME__C"));
				objMDMTemp.setField("SSN__c", csvMDMTemp.get("SSN__C"));
				objMDMTemp.setField("Middle_Name__c", csvMDMTemp.get("MIDDLE_NAME__C"));
				objMDMTemp.setField("Nick_Name__c", csvMDMTemp.get("NICK_NAME__C"));
				objMDMTemp.setField("Birth_Date__c", csvMDMTemp.get("BIRTH_DATE__C"));
				objMDMTemp.setField("Org_Name__c", csvMDMTemp.get("ORG_NAME__C"));
				objMDMTemp.setField("Registered_Number__c", csvMDMTemp.get("REGISTERED_NUMBER__C"));
				objMDMTemp.setField("Registered_Name__c", csvMDMTemp.get("REGISTERED_NAME__C"));
				objMDMTemp.setField("NASDAQ_Code__c", csvMDMTemp.get("NASDAQ_CODE__C"));
				objMDMTemp.setField("DUNS_Number__c", csvMDMTemp.get("DUNS_NUMBER__C"));
				objMDMTemp.setField("Tax_ID__c", csvMDMTemp.get("TAX_ID__C"));
				objMDMTemp.setField("Trade_Mark_ID__c", csvMDMTemp.get("TRADE_MARK_ID__C"));
				objMDMTemp.setField("State_License_ID__c", csvMDMTemp.get("STATE_LICENSE_ID__C"));
				objMDMTemp.setField("Alias_Name__c", csvMDMTemp.get("ALIAS_NAME__C"));
				
				objMDMTemp.setField("Primary_Email__c", csvMDMTemp.get("PRIMARY_EMAIL__C"));
				objMDMTemp.setField("Secondary_Email__c", csvMDMTemp.get("SECONDARY_EMAIL__C"));
				objMDMTemp.setField("Alternate_Email__c", csvMDMTemp.get("ALTERNATE_EMAIL__C"));
				
				objMDMTemp.setField("Primary_Address_Line_1__c", csvMDMTemp.get("PRIMARY_ADDRESS_LINE_1__C"));
				objMDMTemp.setField("Primary_Address_Line_2__c", csvMDMTemp.get("PRIMARY_ADDRESS_LINE_2__C"));
				objMDMTemp.setField("Primary_Address_Line_3__c", csvMDMTemp.get("PRIMARY_ADDRESS_LINE_3__C"));
				objMDMTemp.setField("Primary_Address_Line_4__c", csvMDMTemp.get("PRIMARY_ADDRESS_LINE_4__C"));
				objMDMTemp.setField("Primary_Address_Line_5__c", csvMDMTemp.get("PRIMARY_ADDRESS_LINE_5__C"));
				objMDMTemp.setField("Primary_Address_City__c", csvMDMTemp.get("PRIMARY_ADDRESS_CITY__C"));
				objMDMTemp.setField("Primary_Address_State__c", csvMDMTemp.get("PRIMARY_ADDRESS_STATE__C"));
				objMDMTemp.setField("Primary_Address_Country__c", csvMDMTemp.get("PRIMARY_ADDRESS_COUNTRY__C"));
				objMDMTemp.setField("Primary_Address_Zipcode__c", csvMDMTemp.get("PRIMARY_ADDRESS_ZIPCODE__C"));
				
				objMDMTemp.setField("Billing_Address_Line_1__c", csvMDMTemp.get("BILLING_ADDRESS_LINE_1__C"));
				objMDMTemp.setField("Billing_Address_Line_2__c", csvMDMTemp.get("BILLING_ADDRESS_LINE_2__C"));
				objMDMTemp.setField("Billing_Address_Line_3__c", csvMDMTemp.get("BILLING_ADDRESS_LINE_3__C"));
				objMDMTemp.setField("Billing_Address_Line_4__c", csvMDMTemp.get("BILLING_ADDRESS_LINE_4__C"));
				objMDMTemp.setField("Billing_Address_Line_5__c", csvMDMTemp.get("BILLING_ADDRESS_LINE_5__C"));
				objMDMTemp.setField("Billing_Address_City__c", csvMDMTemp.get("BILLING_ADDRESS_CITY__C"));
				objMDMTemp.setField("Billing_Address_State__c", csvMDMTemp.get("BILLING_ADDRESS_STATE__C"));
				objMDMTemp.setField("Billing_Address_Country__c", csvMDMTemp.get("BILLING_ADDRESS_Country__C"));
				objMDMTemp.setField("Billing_Address_Zipcode__c", csvMDMTemp.get("BILLING_ADDRESS_ZIPCODE__C"));
				
				objMDMTemp.setField("Shipping_Address_Line_1__c", csvMDMTemp.get("SHIPPING_ADDRESS_LINE_1__C"));
				objMDMTemp.setField("Shipping_Address_Line_1__c", csvMDMTemp.get("SHIPPING_ADDRESS_LINE_2__C"));
				objMDMTemp.setField("Shipping_Address_Line_1__c", csvMDMTemp.get("SHIPPING_ADDRESS_LINE_3__C"));
				objMDMTemp.setField("Shipping_Address_Line_1__c", csvMDMTemp.get("SHIPPING_ADDRESS_LINE_4__C"));
				objMDMTemp.setField("Shipping_Address_Line_1__c", csvMDMTemp.get("SHIPPING_ADDRESS_LINE_5__C"));
				objMDMTemp.setField("Shipping_Address_City__c", csvMDMTemp.get("SHIPPING_ADDRESS_CITY__C"));
				objMDMTemp.setField("Shipping_Address_State__c", csvMDMTemp.get("SHIPPING_ADDRESS_STATE__C"));
				objMDMTemp.setField("Shipping_Address_Country__c", csvMDMTemp.get("SHIPPING_ADDRESS_COUNTRY__C"));
				objMDMTemp.setField("Shipping_Address_Zipcode__c", csvMDMTemp.get("SHIPPING_ADDRESS_ZIPCODE__C"));
				
				objMDMTemp.setField("Rep_Address_Line_1__c", csvMDMTemp.get("REP_ADDRESS_LINE_1__C"));
				objMDMTemp.setField("Rep_Address_Line_1__c", csvMDMTemp.get("REP_ADDRESS_LINE_2__C"));
				objMDMTemp.setField("Rep_Address_Line_1__c", csvMDMTemp.get("REP_ADDRESS_LINE_3__C"));
				objMDMTemp.setField("Rep_Address_Line_1__c", csvMDMTemp.get("REP_ADDRESS_LINE_4__C"));
				objMDMTemp.setField("Rep_Address_Line_1__c", csvMDMTemp.get("REP_ADDRESS_LINE_5__C"));
				objMDMTemp.setField("Rep_Address_City__c", csvMDMTemp.get("REP_ADDRESS_CITY__C"));
				objMDMTemp.setField("Rep_Address_State__c", csvMDMTemp.get("REP_ADDRESS_STATE__C"));
				objMDMTemp.setField("Rep_Address_Country__c", csvMDMTemp.get("REP_ADDRESS_COUNTRY__C"));
				objMDMTemp.setField("Rep_Address_Zipcode__c", csvMDMTemp.get("REP_ADDRESS_ZIPCODE__C"));
				
				objMDMTemp.setField("Birth_Date__c", csvMDMTemp.get("BIRTH_DATE__C"));
				objMDMTemp.setField("Cell_Number__c", csvMDMTemp.get("CELL_NUMBER__C"));
				objMDMTemp.setField("Driving_License_ID__c", csvMDMTemp.get("DRIVING_LICENSE_ID__C"));
				//objMDMTemp.setField("Enriched_Flag__c", csvMDMTemp.get("ENRICHED_FLAG__C"));
				objMDMTemp.setField("Fax_Number__c", csvMDMTemp.get("FAX_NUMBER__C"));
				objMDMTemp.setField("Gender__c", csvMDMTemp.get("GENDER__C"));
				objMDMTemp.setField("Home_Number__c", csvMDMTemp.get("HOME_NUMBER__C"));
				
				objMDMTemp.setField("Office_Extn_1__c", csvMDMTemp.get("OFFICE_EXTN_1__C"));
				objMDMTemp.setField("Office_Extn_2__c", csvMDMTemp.get("OFFICE_EXTN_2__C"));
				objMDMTemp.setField("Office_Number_1__c", csvMDMTemp.get("OFFICE_NUMBER_1__C"));
				objMDMTemp.setField("Office_Number_2__c", csvMDMTemp.get("OFFICE_NUMBER_2__C"));
				objMDMTemp.setField("Party_Class_Code_Meaning__c", csvMDMTemp.get("PARTY_CLASS_CODE_MEANING__C"));
				objMDMTemp.setField("Party_Role_Status__c", csvMDMTemp.get("PARTY_ROLE_STATUS__C"));
				objMDMTemp.setField("Party_Role_Type__c", csvMDMTemp.get("PARTY_ROLE_TYPE__C"));
				objMDMTemp.setField("Party_Role_Type_Meaning__c", csvMDMTemp.get("PARTY_ROLE_TYPE_MEANING__C"));
				objMDMTemp.setField("Passport_Number__c", csvMDMTemp.get("PASSPORT_NUMBER__C"));
				objMDMTemp.setField("Popular_Name__c", csvMDMTemp.get("POPULAR_NAME__C"));
				objMDMTemp.setField("Prefix__c", csvMDMTemp.get("PREFIX__C"));
				objMDMTemp.setField("Suffix__c", csvMDMTemp.get("SUFFIX__C"));
				objMDMTemp.setField("Source_Identifier__c", csvMDMTemp.get("SOURCE_IDENTIFIER__C"));
				objMDMTemp.setField("Souce_System_Code__c", csvMDMTemp.get("SOUCE_SYSTEM_CODE__C"));
				objMDMTemp.setField("Source_System_Meaning__c", csvMDMTemp.get("SOURCE_SYSTEM_MEANING__C"));
				objMDMTemp.setField("Source_System_Description__c", csvMDMTemp.get("SOURCE_SYSTEM_DESCRIPTION__C"));
				objMDMTemp.setField("URL__c", csvMDMTemp.get("URL__C"));
				
				//objMDMTemp.setField("To_Be_Enriched_Flag__c", csvMDMTemp.get("TO_BE_ENRICHED_FLAG__c"));
				

				lobjMDMTemp[iCnt]= objMDMTemp;
				iCnt++;
				PMStatic.Print("-----------------iCnt-------------"+iCnt+"csvMDMClassifTemp.get(PARTY__C) :::"+csvMDMTemp.get("Party_ID"));
				if(iCnt==PMStatic.LOAD_TEMP_MAX_RECORDS && iTotalRecordCnt>PMStatic.LOAD_TEMP_MAX_RECORDS)
				{
					objRslt =	connection.create(lobjMDMTemp);
					PMStatic.Print("-----------Now "+objRslt.length+"   objRslt[0].getId():  "+objRslt[0].getId());
				}
				else if(iCnt==iTotalRecordCnt)
				{
					 objRslt =connection.create(lobjMDMTemp);
					PMStatic.Print("----------Now "+objRslt.length+"   objRslt[0].getId():  "+objRslt[0].getId());
				}
				
			}
			PMStatic.Print("Total result set: "+objRslt);
		
			System.out.println( " List  -- :" + lobjMDMTemp.length);
			csvMDMTemp.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			connection=null;
		}
		
		
}
	
// Fetching MDM Temp Classifications from CSV File & Loading into MDM_Classification_Temp temporary custom object
public void fetchnInsertMDMClassificationTempData(){
		
		PartnerConnection connection;
		try {
			
			String filePath = PMStatic.readValue(PMStatic.MDM_CLASSIFICATION_TEMP_FILE);
			Long iTotalRecordCnt =  getRecordCnt(filePath);
			
			//CsvReader csvMDMClassifTemp = new CsvReader(PMStatic.readValue(PMStatic.MDM_CLASSIFICATION_TEMP_FILE));
			CsvReader csvMDMClassifTemp = new CsvReader(
					FileUtil.getInputStream(PMStatic.readValue(PMStatic.MDM_CLASSIFICATION_TEMP_FILE)),
					FileUtil.getCharSet(PMStatic.ENCODING));
			csvMDMClassifTemp.readHeaders();
			iTotalRecordCnt--;
			SObject[] lobjMDMClassifTemp;
			 connection=objWSAccess.getPartnerConnection();
			int iCnt=0;
			
			if(iTotalRecordCnt<PMStatic.LOAD_TEMP_MAX_RECORDS)
				lobjMDMClassifTemp=new SObject[Integer.parseInt(iTotalRecordCnt.toString())];
			else
				lobjMDMClassifTemp=new SObject[PMStatic.LOAD_TEMP_MAX_RECORDS];
			SaveResult[] objRslt=null;
			while (csvMDMClassifTemp.readRecord())
			{
				SObject objMDMClassifTemp=new SObject();
				objMDMClassifTemp.setType("MDM_Classification_Temp__c");
				objMDMClassifTemp.setField("Party_Class_Code__c", csvMDMClassifTemp.get("PARTY_CLASS_CODE__C"));
				objMDMClassifTemp.setField("Party__c", csvMDMClassifTemp.get("PARTY_ID__C"));
				objMDMClassifTemp.setField("Parent_Classification_Type__c", csvMDMClassifTemp.get("PARENT_CLASSIFICATION_TYPE__C"));
				objMDMClassifTemp.setField("Parent_Classification_Code__c", csvMDMClassifTemp.get("PARENT_CLASSIFICATION_CODE__C"));
				objMDMClassifTemp.setField("Child_Classification_Type__c", csvMDMClassifTemp.get("CHILD_CLASSIFICATION_TYPE__C"));
				objMDMClassifTemp.setField("Child_Classification_Code__c", csvMDMClassifTemp.get("CHILD_CLASSIFICATION_CODE__C"));
				objMDMClassifTemp.setField("Source_System__c", csvMDMClassifTemp.get("SOURCE_SYSTEM__C"));
				objMDMClassifTemp.setField("Comment__c", csvMDMClassifTemp.get("COMMENT__C"));
				//
				lobjMDMClassifTemp[iCnt]= objMDMClassifTemp;
				iCnt++;
				PMStatic.Print("-----------------iCnt-------------"+iCnt+"csvMDMClassifTemp.get(PARTY_ID__C) :::"+csvMDMClassifTemp.get("PARTY_ID__C"));
				if(iCnt==PMStatic.LOAD_TEMP_MAX_RECORDS && iTotalRecordCnt>PMStatic.LOAD_TEMP_MAX_RECORDS)
				{
					objRslt =	connection.create(lobjMDMClassifTemp);
					PMStatic.Print("-----------Now "+objRslt.length+"   objRslt[0].getId():  "+objRslt[0].getId());
				}
				else if(iCnt==iTotalRecordCnt)
				{
					 objRslt =connection.create(lobjMDMClassifTemp);
					PMStatic.Print("----------Now "+objRslt.length+"   objRslt[0].getId():  "+objRslt[0].getId());
				}
				
				
				
			}
			PMStatic.Print("Total result set: "+objRslt);
		
			System.out.println( " List  -- :" + lobjMDMClassifTemp.length);
			csvMDMClassifTemp.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			connection=null;
		}
		
		
}

// Fetching MDM Temp Associations from CSV File & Loading into MDM_Association_Temp temporary custom object
public void fetchnInsertMDMAssociationTempData(){
	
	PartnerConnection connection;
	try {
		
		String filePath = PMStatic.readValue(PMStatic.MDM_ASSOCIATION_TEMP_FILE);
		Long iTotalRecordCnt =  getRecordCnt(filePath);
		
		//CsvReader csvMDMAssocTemp = new CsvReader(PMStatic.readValue(PMStatic.MDM_ASSOCIATION_TEMP_FILE));
		CsvReader csvMDMAssocTemp = new CsvReader(
				FileUtil.getInputStream(PMStatic.readValue(PMStatic.MDM_ASSOCIATION_TEMP_FILE)),
				FileUtil.getCharSet(PMStatic.ENCODING));
		csvMDMAssocTemp.readHeaders();
		iTotalRecordCnt--;
		SObject[] lobjMDMAssocTemp;
		 connection=objWSAccess.getPartnerConnection();
		int iCnt=0;
		
		if(iTotalRecordCnt<PMStatic.LOAD_TEMP_MAX_RECORDS)
			lobjMDMAssocTemp=new SObject[Integer.parseInt(iTotalRecordCnt.toString())];
		else
			lobjMDMAssocTemp=new SObject[PMStatic.LOAD_TEMP_MAX_RECORDS];
		SaveResult[] objRslt=null;
		while (csvMDMAssocTemp.readRecord())
		{
			SObject objMDMAssocTemp=new SObject();
			objMDMAssocTemp.setType("MDM_Association_Temp__c");
			objMDMAssocTemp.setField("From_Party_Class__c", csvMDMAssocTemp.get("FROM_PARTY_CLASS__C"));
			objMDMAssocTemp.setField("From_Party_ID__c", csvMDMAssocTemp.get("FROM_PARTY_ID__C"))	;
			objMDMAssocTemp.setField("To_Party_Class__c", csvMDMAssocTemp.get("TO_PARTY_CLASS__C"));
			objMDMAssocTemp.setField("To_Party_ID__c", csvMDMAssocTemp.get("TO_PARTY_ID__C"));
			objMDMAssocTemp.setField("Party_Association_Type__c", csvMDMAssocTemp.get("PARTY_ASSOCIATION_TYPE__C"));
			objMDMAssocTemp.setField("Source_System__c", csvMDMAssocTemp.get("SOURCE_SYSTEM__C"));
			objMDMAssocTemp.setField("Comment__c", csvMDMAssocTemp.get("COMMENT__C"));
			lobjMDMAssocTemp[iCnt]= objMDMAssocTemp;
			iCnt++;
			PMStatic.Print("-----------------iCnt-------------"+iCnt+"csvMDMAssocTemp(From_Party_ID__C) :::"+csvMDMAssocTemp.get("FROM_PARTY_ID__C")+"  csvMDMAssocTemp(TO_PARTY_ID__C) :::"+csvMDMAssocTemp.get("TO_PARTY_ID__C"));
			if(iCnt==PMStatic.LOAD_TEMP_MAX_RECORDS && iTotalRecordCnt>PMStatic.LOAD_TEMP_MAX_RECORDS)
			{
				objRslt =	connection.create(lobjMDMAssocTemp);
				PMStatic.Print("-----------Now "+objRslt.length+"   objRslt[0].getId():  "+objRslt[0].getId());
			}
			else if(iCnt==iTotalRecordCnt)
			{
				 objRslt =connection.create(lobjMDMAssocTemp);
				PMStatic.Print("----------Now "+objRslt.length+"   objRslt[0].getId():  "+objRslt[0].getId());
			}
			
			
			
		}
		PMStatic.Print("Total result set: "+objRslt);
	
		System.out.println( " List  -- :" + lobjMDMAssocTemp.length);
		csvMDMAssocTemp.close();
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ConnectionException ce) {
		ce.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		connection=null;
	}
	
	
}
    //Getting last session Source Party Ids & Party Id
	public void deleteLastSessionTempData()
	{
		String soqlQry = "Select id from MDM_Temp__c limit 200";
		DeleteTempData(soqlQry);
		String soqlClassIfQry = "Select id from MDM_Classification_Temp__c limit 200";
		DeleteTempData(soqlClassIfQry);
		String soqlAssQry = "Select id from MDM_Association_Temp__c limit 200";
		DeleteTempData(soqlAssQry);
		
		
	}
	
	
	public boolean DeleteTempData(String soqlQry) {
		PartnerConnection connection;
		connection = objWSAccess.getPartnerConnection();
		// String soqlQry =
		// "Select count(id) from Exception_Header__c where Exception_type__c='"+PMStatic.EXACT_MATCH_TYPE
		// +"'";
		
		QueryResult qr;
		int iTmpnt = 0;
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
					SObject[] objTmp = qr.getRecords();
					String[] objIDs = getAssocTempIds(objTmp);
					iTmpnt = objTmp.length;
					LOGGER.info("AssociationTempCount: " + objIDs);
					DeleteResult[] objDR = connection.delete(objIDs);
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

