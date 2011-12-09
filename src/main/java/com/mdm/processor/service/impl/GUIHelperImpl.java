package com.mdm.processor.service.impl;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdm.processor.model.ClusteredColumnData;
import com.mdm.processor.model.ProcessStats;
import com.mdm.processor.service.EntityService;
import com.mdm.processor.service.IMDMRecordsCount;
import com.mdm.processor.service.IPMEngineNew;

@Service
public class GUIHelperImpl {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GUIHelperImpl.class);

	@PersistenceContext
	EntityManager em;

	@Autowired
	IMDMRecordsCount objMRC;

	@Autowired
	IPMEngineNew objPME;

	@Autowired
	EntityService entityService;
	
	private boolean clusterColDataHaveBeenUpdated; 

	// boolean first = true;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public void setPieChartData(final long goldenCopy, final long exactMatched,
			final long potMatched) {

		LOGGER.info("While setting -  Overall Golden Copy @ the end of the process "
				+ goldenCopy);
		LOGGER.info("While setting -  Overall Exact match @ the end of the process "
				+ exactMatched);
		LOGGER.info("While setting -  Overall POT match @ the end of the process "
				+ potMatched);

		// Store this to the datastore
		// Find the latest record
		// Delete it
		List list = entityService.getAll();
		if (list != null && list.size() == 1) {
			boolean hasDeleted = entityService.delete(((ProcessStats) list
					.get(0)).getId());
			if (hasDeleted) {
				LOGGER.info("Deleted last pie data");
			}
		} else {
			// Throw a business exception
		}
		// Save this one
		ProcessStats newInstance = new ProcessStats();
		newInstance.setTotalNumberOfExactMatchRecords(exactMatched);
		newInstance.setTotalNumberOfGoldenRecords(goldenCopy);
		newInstance.setTotalNumberOfPOTMatchRecords(potMatched);

		entityService.save(newInstance);
		if (newInstance != null) {
			LOGGER.info("Saved new process stat with following id "
					+ newInstance.getId());
		} else {
			LOGGER.info("Unable to save the record");
		}

	}

	public void setClusteredColumnData(final long goldenCopy,
			final long exactMatched, final long potMatched) {

		long incrementalGoldenCopy = 0;
		long incrementalExactCopy = 0;
		long incrementalPOTCopy = 0;

		LOGGER.info("While setting for clustered column -  Overall Golden Copy @ the end of the process "
				+ goldenCopy);
		LOGGER.info("While setting for clustered column -  Overall Exact match @ the end of the process "
				+ exactMatched);
		LOGGER.info("While setting for clustered column  -  Overall POT match @ the end of the process "
				+ potMatched);

		if(goldenCopy != 0 || exactMatched != 0 || potMatched != 0){
		
		List list = entityService.getAllClusteredColumnData();
		if (list != null && list.size() > 0) {
			// Get latest gold, exact and pot records

			Iterator itr = list.iterator();

			incrementalGoldenCopy = goldenCopy;
			incrementalExactCopy = exactMatched;
			incrementalPOTCopy = potMatched;

			while (itr.hasNext()) {

				ClusteredColumnData sourceCCD = (ClusteredColumnData) itr
						.next();

				LOGGER.info(" Clustered column golden val "
						+ sourceCCD.getIncrementalGoldenCopy());
				LOGGER.info(" Clustered column exact val "
						+ sourceCCD.getIncrementalExactCopy());
				LOGGER.info(" Clustered column pot val "
						+ sourceCCD.getIncrementalPOTCopy());

				// Get incremental values
				incrementalGoldenCopy = incrementalGoldenCopy
						- sourceCCD.getIncrementalGoldenCopy();
				incrementalExactCopy = incrementalExactCopy
						- sourceCCD.getIncrementalExactCopy();
				incrementalPOTCopy = incrementalPOTCopy
						- sourceCCD.getIncrementalPOTCopy();
			}

			long total = incrementalGoldenCopy + incrementalExactCopy
					+ incrementalPOTCopy;

			LOGGER.info("Total -----> " + total);

			// Save this delta values to the data store

			// Save this one
			ClusteredColumnData newInstance = new ClusteredColumnData();

			LOGGER.info(" Golden Copy " + incrementalGoldenCopy);
			LOGGER.info(" Exact Copy " + incrementalExactCopy);
			LOGGER.info(" POT Copy " + incrementalPOTCopy);

			// Set the latest sequence number to the list size...
			// If the list size fetched is 5, that means indices runs from 0 to
			// 4
			// Now sequence number starts from 0 to mirror list index
			// Therefore the newly added element to the datastore should have a
			// sequence number 5 which is presently the
			// list size... therofore setting list size presently as the
			// sequence value
			newInstance.setSequence(list.size());
			newInstance.setIncrementalExactCopy(incrementalExactCopy);
			newInstance.setIncrementalGoldenCopy(incrementalGoldenCopy);
			newInstance.setIncrementalPOTCopy(incrementalPOTCopy);

			entityService.save(newInstance);

			if (newInstance != null) {
				LOGGER.info("Saved new clustered column data following id "
						+ newInstance.getId());
			} else {
				LOGGER.info("Unable to save the record");
			}

		} else {
			// Get latest gold, exact and pot records
			// ClusteredColumnData sourceCCD = new ClusteredColumnData();

			LOGGER.info("Creating clustered column record first time");

			// Get incremental values
			incrementalGoldenCopy = goldenCopy;
			incrementalExactCopy = exactMatched;
			incrementalPOTCopy = potMatched;

			// if (incrementalGoldenCopy < 0){incrementalGoldenCopy =
			// incrementalGoldenCopy * -1;}
			// if (incrementalExactCopy < 0){incrementalExactCopy =
			// incrementalExactCopy * -1;}
			// if (incrementalPOTCopy < 0){incrementalPOTCopy =
			// incrementalPOTCopy * -1;}

			long total = incrementalGoldenCopy + incrementalExactCopy
					+ incrementalPOTCopy;

			LOGGER.info("Total -----> " + total);

			// Save this delta values to the data store

			// Save this one
			ClusteredColumnData newInstance = new ClusteredColumnData();

			// double d1E = incrementalExactCopy / total;
			// double d2G = incrementalGoldenCopy / total;
			// double d3P = incrementalPOTCopy / total;

			LOGGER.info(" Golden Copy " + incrementalGoldenCopy);
			LOGGER.info(" Exact Copy " + incrementalExactCopy);
			LOGGER.info(" POT Copy " + incrementalPOTCopy);

			// Set the latest sequence number to the list size...
			// If the list size fetched is 5, that means indices runs from 0 to
			// 4
			// Now sequence number starts from 0 to mirror list index
			// Therefore the newly added element to the datastore should have a
			// sequence number 5 which is presently the
			// list size... therofore setting list size presently as the
			// sequence value
			newInstance.setSequence(0);
			newInstance.setIncrementalExactCopy(incrementalExactCopy);
			newInstance.setIncrementalGoldenCopy(incrementalGoldenCopy);
			newInstance.setIncrementalPOTCopy(incrementalPOTCopy);

			entityService.save(newInstance);

			if (newInstance != null) {
				LOGGER.info("Saved new clustered column data following id "
						+ newInstance.getId());
			} else {
				LOGGER.info("Unable to save the record");
			}
		}
		}
		clusterColDataHaveBeenUpdated = true;

	}
	
	public boolean getIfClusterColDataHaveBeenUpdated(){
		return this.clusterColDataHaveBeenUpdated;
	}

	public String getClusteredColumnData() {

		String retString = "";
		long goldenCopy = 0;
		long exactMatched = 0;
		long potMatched = 0;

		String goldenStr = "";
		String exactStr = "";
		String potStr = "";

		List list = entityService.getAllClusteredColumnData();

		// Get the size of the list
		int size = 0;
		if (list != null && list.size() > 0) {
			size = list.size();
			LOGGER.info("Size of the clustered data " + size);

			if (size >= 4) {

				LOGGER.info("Clustered column table is having " + size
						+ " records");

				for (int index = size - 1; index >= size - 4; index--) {
					ClusteredColumnData ccd = (ClusteredColumnData) list
							.get(index);

					// Compute total
					long total = ccd.getIncrementalGoldenCopy()
							+ ccd.getIncrementalPOTCopy()
							+ ccd.getIncrementalExactCopy();
					goldenCopy = (Math.round(Double.parseDouble(String
							.valueOf(ccd.getIncrementalGoldenCopy()))
							/ Double.parseDouble(String.valueOf(total)) * 100));
					potMatched = (Math.round(Double.parseDouble(String
							.valueOf(ccd.getIncrementalPOTCopy()))
							/ Double.parseDouble(String.valueOf(total)) * 100));
					exactMatched = (Math.round(Double.parseDouble(String
							.valueOf(ccd.getIncrementalExactCopy()))
							/ Double.parseDouble(String.valueOf(total)) * 100));

					goldenStr = goldenStr + goldenCopy;
					potStr = potStr + potMatched;
					exactStr = exactStr + exactMatched;

					LOGGER.info("Sequence number of the data "
							+ ccd.getSequence());

					if (index != size - 4) {
						goldenStr = goldenStr + "|";
						potStr = potStr + "|";
						exactStr = exactStr + "|";

					}

				}

				retString = retString + goldenStr + "|" + potStr + "|"
						+ exactStr;

				LOGGER.info("Return String value for clustered column "
						+ retString);

			} else {
				// Get the list size
				int listSize = list.size();
				// Iterate the list
				for (int index = listSize - 1; index >= 0; index--) {
					ClusteredColumnData ccd = (ClusteredColumnData) list
							.get(index);

					// Compute total
					long total = ccd.getIncrementalGoldenCopy()
							+ ccd.getIncrementalPOTCopy()
							+ ccd.getIncrementalExactCopy();
					goldenCopy = (Math.round(Double.parseDouble(String
							.valueOf(ccd.getIncrementalGoldenCopy()))
							/ Double.parseDouble(String.valueOf(total)) * 100));
					potMatched = (Math.round(Double.parseDouble(String
							.valueOf(ccd.getIncrementalPOTCopy()))
							/ Double.parseDouble(String.valueOf(total)) * 100));
					exactMatched = (Math.round(Double.parseDouble(String
							.valueOf(ccd.getIncrementalExactCopy()))
							/ Double.parseDouble(String.valueOf(total)) * 100));

					goldenStr = goldenStr + goldenCopy;
					potStr = potStr + potMatched;
					exactStr = exactStr + exactMatched;

					if (index != 0) {
						goldenStr = goldenStr + "|";
						potStr = potStr + "|";
						exactStr = exactStr + "|";

					}

				}

				LOGGER.info("Return String value for clustered column "
						+ retString);
				// Parse it on the basis of |
				for (int index = (4 - listSize); index > 0; index--) {
					goldenStr = goldenStr + "|0";
					potStr = potStr + "|0";
					exactStr = exactStr + "|0";
				}

				retString = retString + goldenStr + "|" + potStr + "|"
						+ exactStr;
				LOGGER.info("Final return string " + retString);
				// Append 0| on the basis of this
			}

		} else {
			retString = "0|0|0|0|0|0|0|0|0|0|0|0";
		}

		return retString;

	}

	public String getPieChartData() {

		String retString = "";
		long goldenCopy = 0;
		long exactMatched = 0;
		long potMatched = 0;

		List list = entityService.getAll();

		if (list != null && list.size() == 1) {
			exactMatched = ((ProcessStats) list.get(0))
					.getTotalNumberOfExactMatchRecords();
			goldenCopy = ((ProcessStats) list.get(0))
					.getTotalNumberOfGoldenRecords();
			potMatched = ((ProcessStats) list.get(0))
					.getTotalNumberOfPOTMatchRecords();
		}

		// ProcessStats processStats = findEntity("a0xx00000008OIP");

		LOGGER.info("While getting -  Overall Golden Copy @ the end of the process "
				+ goldenCopy);
		LOGGER.info("While getting -  Overall Exact match @ the end of the process "
				+ exactMatched);
		LOGGER.info("While getting -  Overall POT match @ the end of the process "
				+ potMatched);

		if (goldenCopy != 0 || potMatched != 0 || exactMatched != 0) {

			// This is the typical scenario when presence of any three factor
			// should yield into a pie chart
			return goldenCopy + "|" + potMatched + "|" + exactMatched;
		} else {
			// Make sure that the chart is not rendered at all. However this
			// happens the first time application runs
			return 0 + "|" + 0 + "|" + 0;
		}

	}

	// @Transactional(readOnly = true)
	// public ProcessStats findEntity(String id) {
	//
	// // Uncomment when you have defined your entity
	// //
	// if ("new".equals(id)) {
	// return new ProcessStats();
	// } else {
	// return em.find(ProcessStats.class, id);
	// }
	// }

	// public void getProcessUpdate() {
	// // TODO Auto-generated method stub
	//
	// }

	public long getNumberOfSteps() {
		// Batch size
		long personCnt = Integer.parseInt(String
				.valueOf(IMDMRecordsCount.startUpRecordSet
						.get(PMStatic.MDM_TEMP_PERSON_COUNT)));
		long orgCnt = Integer.parseInt(String
				.valueOf(IMDMRecordsCount.startUpRecordSet
						.get(PMStatic.MDM_TEMP_ORG_COUNT)));
		long totalCnt = personCnt + orgCnt;
		long batchSize = PMStatic.TEMP_MAX_RECORDS;

		long scale = Math.round(totalCnt / batchSize);

		return scale;

	}

	public long getTotalRecCnt() {
		return objMRC.getTotal();
	}

	public long getNumberOfRecProcessed() {
		// TODO Auto-generated method stub

		long lCnt = objMRC.getCurrentTotal();

		return lCnt;

	}
}
