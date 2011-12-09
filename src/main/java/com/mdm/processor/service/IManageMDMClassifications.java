package com.mdm.processor.service;

import java.util.ArrayList;

import com.sforce.soap.WS_POTMatch.ClassificationsTemp;
import com.sforce.soap.WS_POTMatch.PartyClassifications;
import com.sforce.soap.WS_POTMatch.ValidClassifications;

public interface IManageMDMClassifications {

	public abstract void ProcessMDMClassifications(String[] partyIds,
			String[] srcPartyIds, String partyClass);

	public abstract ArrayList<PartyClassifications> CheckValidityOfClassifications(
			ClassificationsTemp[] objCLT, ValidClassifications[] objVC,
			String[] SrcpartyIds, String[] partyIds);

}