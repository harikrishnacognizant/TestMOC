package com.mdm.processor.service;

import com.sforce.soap.WS_POTMatch.SoapConnection;
import com.sforce.soap.partner.PartnerConnection;

public interface IMDMWebServiceAccess {

	public abstract PartnerConnection getPartnerConnection();

	public abstract SoapConnection getWSPotMatchEngineConnection();

}