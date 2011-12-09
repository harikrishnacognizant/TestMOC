package com.mdm.processor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sforce.soap.WS_POTMatch.PartyAssociations;
import com.sforce.soap.WS_POTMatch.ValidAssociations;
import com.sforce.soap.partner.sobject.SObject;

public interface IManageMDMAssociations {

	public abstract HashMap<String, String> GetAllAssociationTypes();

	public abstract ArrayList<PartyAssociations> CheckValidityOfAssociations(
			SObject[] objMAT, ValidAssociations[] objVA, String fromPartyClass,
			String toPartyClass,Map<String, String> fromMapPrtyIds,Map<String, String> toMapPrtyIds);

	public abstract void ProcessMDMAssociations(String fromPartyClass,
			String toPartyClass);

	public abstract Map<String, Map<String,String>> getManageMDMAssociations();

}