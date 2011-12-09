package com.mdm.processor.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.force.sdk.connector.ForceConnectorConfig;
import com.force.sdk.connector.ForceServiceConnector;
import com.mdm.processor.exception.BaseExceptionFactory;
import com.mdm.processor.exception.BaseExceptionUtil;
import com.mdm.processor.service.IMDMWebServiceAccess;
import com.sforce.soap.WS_POTMatch.SoapConnection;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;

public class MDMWebServiceAccess implements IMDMWebServiceAccess {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MDMWebServiceAccess.class);

	private final Map<String, Object> connectionPool = Collections
			.synchronizedMap(new HashMap<String, Object>(1));

	// private PartnerConnection ptnConn = null;

	// private static final String PMStatic.APPLICATION_PROPERTIES =
	// PMStatic.APPLICATION_PROPERTIES;

	// LoginResult loginResult;
	// PartnerConnection ptnConn;
	// SoapConnection testConnection;

	// private PartnerConnection loginWSC() {
	// boolean success = false;
	//
	// // String userId = readValue("Username");
	// // String passwd = readValue("Password");
	// // String authEndPoint = readValue("AuthEndPoint");
	// // String serviceEndpoint = readValue("ServiceEndpoint");
	// // String proxyRequired = readValue("ProxyRequired");
	// // String proxyHost = readValue("ProxyHost");
	// // String proxyPort = readValue("ProxyPort");
	//
	// String userId = "dinesh.nasipuri@cognizant.com";
	// String passwd = "password-5jJs1O0iTBjJX6DW8QLgmrkTsz";
	// String authEndPoint = "force://vmf01.t.salesforce.com";
	// String serviceEndpoint =
	// "https://vmf01.t.salesforce.com/services/Soap/class/WS_POTMatch";
	// String proxyRequired = "NO";
	// String proxyHost = "";
	// String proxyPort = "";
	//
	// // String authEndPoint =
	// // "https://test.salesforce.com/services/Soap/c/21.0";
	// ForceConnectorConfig fConfig = new ForceConnectorConfig();
	// try {
	//
	// if (testConnection == null) {
	// // config = new ConnectorConfig();
	// // config.setUsername(userId);
	// // config.setPassword(passwd);
	// if (("YES").equalsIgnoreCase(proxyRequired)) {
	// fConfig.setProxy(proxyHost, Integer.parseInt(proxyPort));
	// }
	// //
	// // //System.out.println("AuthEndPoint: " + authEndPoint);
	// // config.setAuthEndpoint(authEndPoint);
	// // //config.setTraceFile("traceLogs.txt");
	// // config.setTraceMessage(true);
	// // config.setPrettyPrintXml(true);
	// // connection = new EnterpriseConnection(config);
	// fConfig.setUsername(userId);
	// fConfig.setPassword(passwd);
	// fConfig.setAuthEndpoint(authEndPoint);
	// fConfig.setServiceEndpoint(serviceEndpoint);
	//
	// ForceServiceConnector fsc = new ForceServiceConnector();
	//
	// fsc.setConnectorConfig(fConfig);
	// // Get partner connection
	// PartnerConnection ptnConn = fsc.getConnection();
	// // Using the session id of the partner connection
	// fConfig.setSessionId(ptnConn.getSessionHeader().getSessionId());
	//
	// com.sforce.soap.partner.GetUserInfoResult userInfo = ptnConn
	// .getUserInfo();
	// // connection.setSessionHeader(config.getSessionId());
	// LOGGER.info("\nLogging in ...\n");
	// LOGGER.info("UserID: " + userInfo.getUserId());
	// LOGGER.info("User Full Name: " + userInfo.getUserFullName());
	// LOGGER.info("User Email: " + userInfo.getUserEmail());
	// LOGGER.info("SessionID: " + fConfig.getSessionId());
	// LOGGER.info("Auth End Point: " + fConfig.getAuthEndpoint());
	// LOGGER.info("Service End Point: "
	// + fConfig.getServiceEndpoint());
	//
	// // ConnectorConfig soapConfig=new ConnectorConfig();
	// // soapConfig.setSessionId(config.getSessionId());
	// // soapConfig.setServiceEndpoint(serviceEndpoint);
	// // soapConfig.setConnectionTimeout(6000);
	// testConnection = new SoapConnection(fConfig);
	// // testConnection.setSessionHeader(config.getSessionId());
	// success = true;
	// }
	//
	// success = true;
	//
	// } catch (ConnectionException connExp) {
	// // connExp.printStackTrace();
	// LOGGER.error("Exception : "
	// + BaseExceptionUtil.printStackTrace(connExp));
	// throw BaseExceptionFactory.getInstance(connExp);
	// } catch (Exception exp) {
	// LOGGER.error("Exception : "
	// + BaseExceptionUtil.printStackTrace(exp));
	// throw BaseExceptionFactory.getInstance(exp);
	// }
	// return success;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mdm.processor.service.IMDMWebServiceAccess#GetPartnerConnection()
	 */
	@Override
	public PartnerConnection getPartnerConnection() {

		// Return partner connection

		PartnerConnection ptnConn = null;
		// (PartnerConnection) connectionPool
		// .get(PMStatic.PARTNER_CONNECTION);
		// if (ptnConn == null) {

		String userId = PMStatic.readValue("Username");
		String passwd = PMStatic.readValue("Password");
		String authEndPoint = PMStatic.readValue("AuthEndPoint");
		String serviceEndpoint = PMStatic.readValue("ServiceEndpoint");
		String proxyRequired = PMStatic.readValue("ProxyRequired");
		String proxyHost = PMStatic.readValue("ProxyHost");
		String proxyPort = PMStatic.readValue("ProxyPort");

		// String userId = "dinesh.nasipuri@cognizant.com";
		// String passwd = "password-5jJs1O0iTBjJX6DW8QLgmrkTsz";
		// String authEndPoint = "force://vmf01.t.salesforce.com";
		// String serviceEndpoint =
		// "https://vmf01.t.salesforce.com/services/Soap/class/WS_POTMatch";
		// String proxyRequired = "NO";
		// String proxyHost = "";
		// String proxyPort = "";
		ForceConnectorConfig fConfig = new ForceConnectorConfig();
		try {

			if (("YES").equalsIgnoreCase(proxyRequired)) {
				fConfig.setProxy(proxyHost, Integer.parseInt(proxyPort));
			}
			fConfig.setUsername(userId);
			fConfig.setPassword(passwd);
			fConfig.setAuthEndpoint(authEndPoint);
			fConfig.setServiceEndpoint(serviceEndpoint);

			ForceServiceConnector fsc = new ForceServiceConnector();

			fsc.setConnectorConfig(fConfig);
			// Get partner connection
			ptnConn = fsc.getConnection();

			connectionPool.put(PMStatic.PARTNER_CONNECTION, ptnConn);
		}

		catch (ConnectionException connExp) {
			// connExp.printStackTrace();
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(connExp));
			throw BaseExceptionFactory.getInstance(connExp);
		} catch (Exception exp) {
			LOGGER.error("Exception : "
					+ BaseExceptionUtil.printStackTrace(exp));
			throw BaseExceptionFactory.getInstance(exp);
		}
		// }

		return ptnConn;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mdm.processor.service.IMDMWebServiceAccess#GetWSPMStub()
	 */
	@Override
	public SoapConnection getWSPotMatchEngineConnection() {
		boolean success = false;
		SoapConnection enterpriseConnection = null;

		String userId = PMStatic.readValue("Username");
		String passwd = PMStatic.readValue("Password");
		String authEndPoint = PMStatic.readValue("AuthEndPoint");
		String serviceEndpoint = PMStatic.readValue("ServiceEndpoint");
		String proxyRequired = PMStatic.readValue("ProxyRequired");
		String proxyHost = PMStatic.readValue("ProxyHost");
		String proxyPort = PMStatic.readValue("ProxyPort");

		// String userId = "dinesh.nasipuri@cognizant.com";
		// String passwd = "password-5jJs1O0iTBjJX6DW8QLgmrkTsz";
		// String authEndPoint = "force://vmf01.t.salesforce.com";
		// String serviceEndpoint =
		// "https://vmf01.t.salesforce.com/services/Soap/class/WS_POTMatch";
		// String proxyRequired = "NO";
		// String proxyHost = "";
		// String proxyPort = "";

		// (SoapConnection) connectionPool.get(PMStatic.ENTERPRISE_CONNECTION);

		ForceConnectorConfig fConfig = new ForceConnectorConfig();

		if (enterpriseConnection == null) {

			try {

				if (("YES").equalsIgnoreCase(proxyRequired)) {
					fConfig.setProxy(proxyHost, Integer.parseInt(proxyPort));
				}

				fConfig.setUsername(userId);
				fConfig.setPassword(passwd);
				fConfig.setAuthEndpoint(authEndPoint);
				fConfig.setServiceEndpoint(serviceEndpoint);

				ForceServiceConnector fsc = new ForceServiceConnector();

				fsc.setConnectorConfig(fConfig);
				// Get partner connection
				PartnerConnection ptnConn = getPartnerConnection();
				// Using the session id of the partner connection
				fConfig.setSessionId(ptnConn.getSessionHeader().getSessionId());

				com.sforce.soap.partner.GetUserInfoResult userInfo = ptnConn
						.getUserInfo();
				// connection.setSessionHeader(config.getSessionId());
				LOGGER.info("\nLogging in ...\n");
				LOGGER.info("UserID: " + userInfo.getUserId());
				LOGGER.info("User Full Name: " + userInfo.getUserFullName());
				LOGGER.info("User Email: " + userInfo.getUserEmail());
				LOGGER.info("SessionID: " + fConfig.getSessionId());
				LOGGER.info("Auth End Point: " + fConfig.getAuthEndpoint());
				LOGGER.info("Service End Point: "
						+ fConfig.getServiceEndpoint());

				enterpriseConnection = new SoapConnection(fConfig);

				connectionPool.put(PMStatic.ENTERPRISE_CONNECTION,
						enterpriseConnection);

				// success = true;

			} catch (ConnectionException connExp) {
				// connExp.printStackTrace();
				LOGGER.error("Exception : "
						+ BaseExceptionUtil.printStackTrace(connExp));
				throw BaseExceptionFactory.getInstance(connExp);
			} catch (Exception exp) {
				LOGGER.error("Exception : "
						+ BaseExceptionUtil.printStackTrace(exp));
				throw BaseExceptionFactory.getInstance(exp);
			}
		}
		return enterpriseConnection;
	}

	// Read from a file
	// private String readValue(String fieldName){
	// File f = new File(PMStatic.APPLICATION_PROPERTIES);
	// String fieldValue="";
	// try {
	// if(f.exists()){
	// Properties pro = new Properties();
	// FileInputStream in;
	// in = new FileInputStream(f);
	// pro.load(in);
	// fieldValue = pro.getProperty(fieldName);
	// }else{
	// System.out.println("File not found");
	// }
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return fieldValue;
	// }

}
