package com.mdm.processor.service.impl;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sforce.soap.WS_POTMatch.SoapConnection;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.mdm.processor.controller.HomeController;
import com.mdm.processor.exception.BaseExceptionFactory;
import com.mdm.processor.service.IMDMRecordsCount;
import com.mdm.processor.service.IMDMWebServiceAccess;
import com.mdm.processor.service.IManageMDMLiveData;

public class ManageMDMLiveData implements IManageMDMLiveData{

	private static final Logger logger = LoggerFactory
	.getLogger(ManageMDMLiveData.class);
	
	@Autowired
	GUIHelperImpl gUIHelperImpl;
	
	@Autowired
	IMDMWebServiceAccess objWSAccess;
	
	@Autowired
	IMDMRecordsCount objMRC;
	
	@Override
	public void deleteLastSessionPartyData()
	{
		PartnerConnection connection;
    	MDMWebServiceAccess mDMWebServiceAccess = new MDMWebServiceAccess();
    	
    	
    	
    	try {
    		if(!PMStatic.POTMATCH_PROCESS) // if potmatch is not processing the temp data then we can't delete
        	{
        		logger.info("Last Session Processed data deleting ...");
    		    connection=mDMWebServiceAccess.getPartnerConnection();
    		    if(connection!=null)
    		    {
	    		    	// Time duration is in minutes...
		    		 int time = 20;
		    		 deleteTableData(connection,"Party__c",time);
		    		 deleteTableData(connection,"MatchedRecords__c",time);
		    		 deleteTableData(connection,"Exception_Header__c",time);
		    		 deleteTableData(connection,"Potential_Match__c",time);
		    		 deleteTableData(connection,"ClusteredColumnData__c",time);
		    		 updateProcessStatsObject();
		    		 logger.info("Loading Process ended...");
    		    }
    		 
        	}
    		else
    	    {
    	      logger.info("Now I can't delete the records, because Potmatch Engine Processing ...");
    	    }
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	finally{
    		connection=null;
    	}
    	
    	
      
	}
	
	private static boolean deleteAllTableData(PartnerConnection connection, String tableName) throws ConnectionException{
    	
    	boolean isDeleted = false;
		boolean isDataOver=true;
		HashMap<Integer,String[]> hmAllCustObjIds = retreiveAllIds(connection,tableName);
		
		 try{
			 if(hmAllCustObjIds!=null && hmAllCustObjIds.size()>0)
        	for ( int i = 0; i < hmAllCustObjIds.size();++i ) {
        		String[] objIds = hmAllCustObjIds.get(i);
        		if(objIds!=null && objIds.length>0)
        		{
        			//DleteResult[] objDR = connection.delete(objIds);
        			connection.delete(objIds);
        			isDeleted=true;
        		}
        	}
        	
		 }catch(ConnectionException conexp){
			 conexp.printStackTrace(); 
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
    	
    	return isDeleted;
    }
    
    private static HashMap<Integer,String[]> retreiveAllIds(PartnerConnection connection,String tableName) throws ConnectionException{
    	
    	QueryResult qResult = null;
    	HashMap<Integer,String[]> hmAllObjectIds=null;
    	try{
  	
    		        	
        	String soqlQuery = "select id from "+tableName+" limit 200";
        	qResult = connection.query(soqlQuery);  
        	 boolean done = false;
        	 String[] strIds = new String[200];
        	 hmAllObjectIds=new HashMap<Integer,String[]>();
        	 int jCnt=0;
    	    if (qResult.getSize() > 0) {
    	    	
    	        while (! done) {
    	        	
    	        	SObject[] records = qResult.getRecords();
	    	    	for ( int i = 0; i < records.length; ++i ) {
	    	    		logger.info("Now Deleting following Table:"+tableName+"with these Ids: "+records[i].getId());
		        		strIds[i] = records[i].getId();
		        	}
	    	    	hmAllObjectIds.put(jCnt, strIds);
	    	    	jCnt++;
    	          if (qResult.isDone()) {
    	            done = true;
    	          } else {
    	            qResult = 
    	                connection.queryMore(qResult.getQueryLocator());
    	          }
    	        }
    	      } 
        	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return hmAllObjectIds;
    }
    
	 private static boolean deleteTableData(PartnerConnection connection, String tableName,int time) throws ConnectionException{
	    	
	    	boolean isDeleted = false;
			boolean isDataOver=true;
			HashMap<Integer,String[]> hmAllCustObjIds = retreiveIds(connection,tableName,time);
			
			 try{
				 if(hmAllCustObjIds!=null && hmAllCustObjIds.size()>0)
	        	for ( int i = 0; i < hmAllCustObjIds.size();++i ) {
	        		String[] objIds = hmAllCustObjIds.get(i);
	        		if(objIds!=null && objIds.length>0)
	        			//DleteResult[] objDR = connection.delete(objIds);
	        			connection.delete(objIds);
	        	}
	        	
			 }catch(ConnectionException conexp){
				 conexp.printStackTrace(); 
		    	}catch(Exception e){
		    		e.printStackTrace();
		    	}
	    	
	    	return isDeleted;
	    }
	    
	    private static HashMap<Integer,String[]> retreiveIds(PartnerConnection connection,String tableName,int time) throws ConnectionException{
	    	
	    	QueryResult qResult = null;
	    	HashMap<Integer,String[]> hmAllObjectIds=null;
	    	try{
	  	
	    		// Retreive the createdDate from Process_Stats__c and convert the date format...
	        	Date javaDate = StringtoDate(retreiveCreatedDate(connection).substring(0, 19));
	        	
	        	// Get the time 30 mins from now....
	        	java.util.GregorianCalendar gc = new java.util.GregorianCalendar();
	        	gc.setTime(javaDate);
	        	gc.add(java.util.GregorianCalendar.MINUTE, -time); 
	        	javaDate = gc.getTime();
	        	
	        	//Convert the JavaDate to date which is accepted by SOQL query...
	        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");   
	        	String s = df.format(javaDate); 
	        	
	        	String soqlQuery = "select id from "+tableName+" where createdDate > "+s.substring(0, 19)+".000Z limit 200";
	        	qResult = connection.query(soqlQuery);  
	        	 boolean done = false;
	        	 String[] strIds = new String[200];
	        	 hmAllObjectIds=new HashMap<Integer,String[]>();
	        	 int jCnt=0;
	    	    if (qResult.getSize() > 0) {
	    	    	
	    	        while (! done) {
	    	        	
	    	        	SObject[] records = qResult.getRecords();
		    	    	for ( int i = 0; i < records.length; ++i ) {
		    	    		logger.info("Now Deleting following Table:"+tableName+"with these Ids: "+records[i].getId());
			        		strIds[i] = records[i].getId();
			        	}
		    	    	hmAllObjectIds.put(jCnt, strIds);
		    	    	jCnt++;
	    	          if (qResult.isDone()) {
	    	            done = true;
	    	          } else {
	    	            qResult = 
	    	                connection.queryMore(qResult.getQueryLocator());
	    	          }
	    	        }
	    	      } 
	        	
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	
	    	return hmAllObjectIds;
	    }
	    
	    private static String retreiveCreatedDate(PartnerConnection connection) throws ConnectionException{
	    	
	    	QueryResult qResult = null;
	    	String str = null;
	    	try{
	        	String soqlQuery = "select CreatedDate from ProcessStats__c";
	        	qResult = connection.query(soqlQuery);  
	        	SObject[] records = qResult.getRecords();
	        	
	        	for ( int i = 0; i < records.length; ++i ) {
	        		str = records[i].getField("CreatedDate").toString();
	        	}
	        	
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	return str;
	    }
	    
	    private static Date StringtoDate(String str_date){
	    	
	    	Date date = null ; 
	    	try {  
		    	 DateFormat formatter ; 
		    	 formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		    	 date = (Date)formatter.parse(str_date);  
	    	} catch (ParseException e){
	    		System.out.println("Exception :"+e);  }  
	        return date;
	    }
	    
   private  void updateProcessStatsObject() {
	    	
	    	
	    	try{
	    		logger.info("I am calling setPieChartData while loading data to reset the ProcessStats : ");
				SoapConnection wsMDMBinding = objWSAccess.getWSPotMatchEngineConnection();
	    		gUIHelperImpl.setPieChartData(wsMDMBinding.getTotalGCPartyCount(),
						objMRC.getExactMatchCount(),
						wsMDMBinding.getTotalPMPartyCount());	    	
	    	} catch (Exception Exp) {
				logger.info("Problem in setPieChartData or updateProcessStatsTable : "
						+ Exp.getMessage());
				Exp.printStackTrace();
				throw BaseExceptionFactory.getInstance(Exp);
			}
	    	
	    }
}
