package com.mdm.processor.controller;

import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdm.processor.service.EntityService;
import com.mdm.processor.service.IMDMRecordsCount;
import com.mdm.processor.service.IPMEngineNew;
import com.mdm.processor.service.impl.GUIHelperImpl;
import com.mdm.processor.service.IReadCSVfromSourceTempData;
import com.mdm.processor.service.IManageMDMLiveData;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	IMDMRecordsCount objMRC;

	@Inject
	EntityService entityService;

	@Inject
	GUIHelperImpl gUIHelperImpl;

	@Autowired
	IPMEngineNew objPME;

	@Autowired
	IReadCSVfromSourceTempData objReadCSV;
	
	@Autowired
	IManageMDMLiveData	objMML;

	private long numberOfSteps = 0;
	
	private long total = 0;

	private String oldPercentage = "";

	// @Inject
	// MDProcessorMainClazz mDProcessorMainClazz;

	
	public HomeController()
	{
		
	}
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		logger.info("requesting home");
		
		
		return "home";
	}

	// /**
	// * Set interval at runtime
	// */
	// @RequestMapping(value="/setInterval", method=RequestMethod.POST)
	// public String setInterval() {
	// logger.info("Setting new interval at "+ new Date());
	// return "home";
	// }

	public long getNumberOfSteps() {
		return numberOfSteps;
	}

	public void setNumberOfSteps(long numberOfSteps) {
		this.numberOfSteps = numberOfSteps;
	}

	/**
	 * Start the process manually
	 */
	@RequestMapping(value = "/startProcess", method = RequestMethod.GET)
	public @ResponseBody
	String startProcess() {
		logger.info("Start process manually " + new Date());
		// gUIHelperImpl.getPieChartData();
		objPME.process();
		//
		return "TRUE";
	}

	@RequestMapping(value = "/loadData", method = RequestMethod.GET)
	public @ResponseBody
	String loadData() {
		logger.info("Started  loadData processing...." + new Date());
		// Autowire and call method
		// Bind your code
		objMRC.showDataVolumeinSFDC();
		
		objMML.deleteLastSessionPartyData();
		objReadCSV.deleteLastSessionTempData();
		objReadCSV.fetchnInsertMDMTempData();
		objReadCSV.fetchnInsertMDMClassificationTempData();
		objReadCSV.fetchnInsertMDMAssociationTempData();
		objMRC.showDataVolumeinSFDC();
		if(gUIHelperImpl!=null)
			total = gUIHelperImpl.getTotalRecCnt();
		logger.info(" loadData total..Temp Records.." + total);
		logger.info(" Loading data completes ");
		return "TRUE";
	}

	/**
	 * Update the progress bar Finds the percentage completed already .. Total
	 * record / already processed record
	 */
	@RequestMapping(value = "/getProcessUpdate", method = RequestMethod.GET)
	public @ResponseBody
	String getProcessUpdate() {
		if(total == 0){total = gUIHelperImpl.getTotalRecCnt();}
		logger.info("total --------------------------------------------------------------------- > "
				+ total);
		long numberOfRecordsLeft = gUIHelperImpl.getNumberOfRecProcessed();
		logger.info("numberOfRecordsLeft --------------------------------------------------------------------- > "
				+ numberOfRecordsLeft);

		long numberOfRecordsProcessed = total - numberOfRecordsLeft;

		logger.info("Number of records processed as of now  - "
				+ numberOfRecordsProcessed);

		String retVal = String.valueOf(Math.round(Double.parseDouble(String
				.valueOf(numberOfRecordsProcessed))
				/ Double.parseDouble(String.valueOf(total)) * 100));

		logger.info("Percentage computed as of now --------------------------------------------------------------------- > "
				+ retVal);

		// Save old record for all cases other than when retVal == 100
		if (!"100".equals(retVal)) {
			oldPercentage = retVal;
		}
		// Return recent unless retVal == 100 and !clusterColDataHaveBeenUpdated
		// This way 100 percentage will only get displayed once clustered column
		// gets updated
		boolean clusterColDataHaveBeenUpdated = gUIHelperImpl
				.getIfClusterColDataHaveBeenUpdated();
		logger.info("clusterColDataHaveBeenUpdated"+clusterColDataHaveBeenUpdated);
		if ("100".equals(retVal) && clusterColDataHaveBeenUpdated) {
			return retVal;
		} else {
			return oldPercentage;
		}

	}

	/**
	 * Get historical record count
	 */
	@RequestMapping(value = "/getHistoricalRecCount", method = RequestMethod.GET)
	public @ResponseBody
	String getHistoricalRecCount() {
		// First time the number of steps get set
		if (getNumberOfSteps() == 0) {
			setNumberOfSteps(gUIHelperImpl.getNumberOfSteps());
			logger.info("Scale is - " + getNumberOfSteps());
		}

		return getNumberOfSteps() + "|"
				+ String.valueOf(Math.getExponent((Math.random() * 100)));

	}

	/**
	 * Get historical record count
	 */
	@RequestMapping(value = "/getStartUpData", method = RequestMethod.GET)
	public @ResponseBody
	String getStartUpData() {
		// First time the number of steps get set
		if (getNumberOfSteps() == 0) {
			setNumberOfSteps(gUIHelperImpl.getNumberOfSteps());
			logger.info("Scale is - " + getNumberOfSteps());
		}

		return String.valueOf(getNumberOfSteps());

	}

	/**
	 * Get pie chart data
	 */
	@RequestMapping(value = "/getPieChartData", method = RequestMethod.GET)
	public @ResponseBody
	String getPieChartData() {
		logger.info("Get pie chart start up data " + new Date());
		// Query and build the array

		// Ends
		return String.valueOf(gUIHelperImpl.getPieChartData());

	}

	/**
	 * Get clustered column data
	 */
	@RequestMapping(value = "/getClusteredColumnsData", method = RequestMethod.GET)
	public @ResponseBody
	String getClusteredColumnsData() {
		logger.info("Get clustered column data " + new Date());
		// Query and build the array
		return String.valueOf(gUIHelperImpl.getClusteredColumnData());

	}
}
