package com.mdm.processor.service.impl;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mdm.processor.service.IManageMDMClassifications;
import com.mdm.processor.service.IProcessingMDMData;
import com.sforce.soap.WS_POTMatch.ClassificationsTemp;
import com.sforce.soap.WS_POTMatch.PartyClassifications;
import com.sforce.soap.WS_POTMatch.SoapConnection;
import com.sforce.soap.WS_POTMatch.ValidClassifications;
//This class is not used 
public class ManageMDMClassifications implements IManageMDMClassifications 
{
	private SoapConnection wsMDMBinding;
	private IProcessingMDMData objPMD;
	
	private static final Logger LOGGER = LoggerFactory
	.getLogger(ManageMDMAssociations.class);
	
	public ManageMDMClassifications(SoapConnection wsBinding,IProcessingMDMData objPMD)
	{
		try
		{
			wsMDMBinding = wsBinding;
			this.objPMD=objPMD;
		}
		catch(Exception Exp)
		{
			LOGGER.info("Problem in Constructor ManageMDMClassifications : " +Exp.getMessage());
			Exp.printStackTrace();
		}
	}
	/* (non-Javadoc)
	 * @see com.mdm.processor.service.IManageMDMClassifications#ProcessMDMClassifications(java.lang.String[], java.lang.String[], java.lang.String)
	 */
	@Override
	public void ProcessMDMClassifications(String[] partyIds,String[] srcPartyIds, String partyClass)
	{
		try
		{
			
			LOGGER.info("ProcessMDMClassifications : created PartyIds = "+partyIds.length);
			LOGGER.info("ProcessMDMClassifications : created PartyIds = "+srcPartyIds.length);
		//	PMStatic.ReadLine();
			//Integer	iMDMCLTpbCnt =	GetSourcePageBlockSize(objMRC.MDMCLTCnt);
			ValidClassifications[] objVC= wsMDMBinding.getValidClassifications(partyClass);
			LOGGER.info("ProcessMDMClassifications : system ValidClassifications = "+objVC.length);
		//	PMStatic.ReadLine();
			//String partyIds[]=new String[]{"One","Baba"};
			//for(Integer iCnt=0;iCnt<iMDMCLTpbCnt;iCnt++)
			//{
			   ClassificationsTemp[] objCLT=wsMDMBinding.getMDMClassificationsTemp(partyClass, srcPartyIds);
			   LOGGER.info(" getMDMClassificationsTemp system giving ClassificationsTemp = "+objCLT.length);
				//PMStatic.ReadLine();
			   ArrayList<PartyClassifications> objlPC = CheckValidityOfClassifications(objCLT,objVC,srcPartyIds,partyIds);
			   LOGGER.info("CheckValidityOfClassifications : PartyClassifications system  "+objlPC.size());
			  // PMStatic.PMStatic.PrintArray()
			   PartyClassifications[] objPC=new PartyClassifications[objlPC.size()];
			  // PMStatic.ReadLine();
			   objlPC.toArray(objPC);
			   LOGGER.info("CheckValidityOfClassifications : PartyClassifications[] system  "+objPC.length);
			//   PMStatic.ReadLine();
			   wsMDMBinding.CreateClassificationsOnCloud(objPC);
			   
			//}
		//wsMDMBinding.getMDMClassificationsTemp('Person', )	
		
		}
		catch(Exception Exp)
		{
			LOGGER.info("Problem in Constructor PMEngineNew : " +Exp.getMessage());
			Exp.printStackTrace();
		}
	}
	
	public String GetMappedGCPartyID(String[] SrcpartyIds, String[] partyIds, String SrcPartyID)
	{
		
		if(SrcPartyID!=null && SrcpartyIds!=null && SrcpartyIds.length>0 && partyIds!=null && partyIds.length>0)
		{
			
			for(int iCnt=0;iCnt<SrcpartyIds.length;iCnt++)
			{
				if(SrcPartyID.equals(SrcpartyIds[iCnt]))
					return partyIds[iCnt];
			}
		}
			return "";
	}
	
	/* (non-Javadoc)
	 * @see com.mdm.processor.service.IManageMDMClassifications#CheckValidityOfClassifications(com.sforce.soap.WS_POTMatch.ClassificationsTemp[], com.sforce.soap.WS_POTMatch.ValidClassifications[], java.lang.String[], java.lang.String[])
	 */
	@Override
	public ArrayList<PartyClassifications> CheckValidityOfClassifications(ClassificationsTemp[] objCLT,ValidClassifications[] objVC,String[] SrcpartyIds,String[] partyIds)
	{
		ArrayList<PartyClassifications> lPC=new ArrayList<PartyClassifications>();
		
		if(objVC!=null && objCLT!=null && objVC.length>0 && objCLT.length>0)
		{
			for(Integer iCnt=0;iCnt<objCLT.length;iCnt++)
			{
				for(Integer jCnt=0;jCnt<objVC.length;jCnt++)
				{
					LOGGER.info("objCLT[iCnt].getPartyClass()==="+objCLT[iCnt].getPartyClass()+"objVC[jCnt].getPartyClass()==="+objVC[jCnt].getPartyClass());
					LOGGER.info("objCLT[iCnt].getChildClassificationCode()==="+objCLT[iCnt].getChildClassificationCode()+"objVC[jCnt].getChildClassificationCode()==="+objVC[jCnt].getChildClassificationCode());
					LOGGER.info("objCLT[iCnt].getChildClassificationType()==="+objCLT[iCnt].getChildClassificationType()+"objVC[jCnt].getChildClassificationType()==="+objVC[jCnt].getChildClassificationType());
					LOGGER.info("objCLT[iCnt].getParentClassificationCode()==="+objCLT[iCnt].getParentClassificationCode()+"objVC[jCnt].getParentClassificationCode()==="+objVC[jCnt].getParentClassificationCode());
					LOGGER.info("objCLT[iCnt].getParentClassificationType()==="+objCLT[iCnt].getParentClassificationType()+"objVC[jCnt].getParentClassificationType()==="+objVC[jCnt].getParentClassificationType());
					//PMStatic.ReadLine();
					if(objCLT[iCnt].getChildClassificationCode().equals(objVC[jCnt].getChildClassificationCode()) &&
							objCLT[iCnt].getChildClassificationType().equals(objVC[jCnt].getChildClassificationType()) &&
							objCLT[iCnt].getParentClassificationCode().equals(objVC[jCnt].getParentClassificationCode()) &&
							objCLT[iCnt].getParentClassificationType().equals(objVC[jCnt].getParentClassificationType())) 
							//&& 
							//objCLT[iCnt].getPartyID().equals(SrcpartyIds[iCnt]))
					   {
					    			PartyClassifications objPC=new PartyClassifications();
					    			objPC.setClassificationID(objVC[jCnt].getClassificationID());
					    			objPC.setPartyID(GetMappedGCPartyID(SrcpartyIds, 
											partyIds,
											objCLT[iCnt].getPartyID()));
					    			//objPC.setPartyID(objCLT[iCnt].getPartyID());
					    			if(objCLT[iCnt].getComments()!=null)
					    			objPC.setComments(objCLT[iCnt].getComments());
					    			if(objCLT[iCnt].getSourceSystemCode()!=null)
					    			objPC.setSourceSystemCode(objCLT[iCnt].getSourceSystemCode());
			        
					    			LOGGER.info("=====objCLT[iCnt].getPartyClass()==="+objCLT[iCnt].getPartyClass()+"objVC[jCnt].getPartyClass()==="+objVC[jCnt].getPartyClass());
									LOGGER.info("=======objCLT[iCnt].getChildClassificationCode()==="+objCLT[iCnt].getChildClassificationCode()+"objVC[jCnt].getChildClassificationCode()==="+objVC[jCnt].getChildClassificationCode());
									LOGGER.info("========objCLT[iCnt].getChildClassificationType()==="+objCLT[iCnt].getChildClassificationType()+"objVC[jCnt].getChildClassificationType()==="+objVC[jCnt].getChildClassificationType());
									LOGGER.info("======objCLT[iCnt].getParentClassificationCode()==="+objCLT[iCnt].getParentClassificationCode()+"objVC[jCnt].getParentClassificationCode()==="+objVC[jCnt].getParentClassificationCode());
									LOGGER.info("======objCLT[iCnt].getParentClassificationType()==="+objCLT[iCnt].getParentClassificationType()+"objVC[jCnt].getParentClassificationType()==="+objVC[jCnt].getParentClassificationType());
									//PMStatic.ReadLine();
			                          lPC.add(objPC);
			                    }
				}
			}
			return lPC;
		}
		return null;
	}
}
