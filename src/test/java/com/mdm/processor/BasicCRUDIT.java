package com.mdm.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.force.sdk.connector.ForceServiceConnector;

import com.mdm.processor.controller.HomeController;
import com.mdm.processor.model.ClusteredColumnData;
import com.mdm.processor.model.IModel;
import com.mdm.processor.model.MDMSourceTemp;
import com.mdm.processor.model.MyEntity;
import com.mdm.processor.model.ProcessStats;
import com.mdm.processor.service.EntityService;
import com.mdm.processor.service.IMDMRecordsCount;
import com.mdm.processor.service.IManageMDMLiveData;
import com.mdm.processor.service.IPMEngineNew;
import com.mdm.processor.service.IReadCSVfromSourceTempData;
import com.mdm.processor.service.impl.GUIHelperImpl;
import com.sforce.ws.ConnectionException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-context.xml")
public class BasicCRUDIT {

	private static final Logger logger = LoggerFactory
	.getLogger(HomeController.class);

	@Autowired
	IMDMRecordsCount objMRC;
	
	
	@Autowired
	IReadCSVfromSourceTempData objReadCSV;

	@Autowired
	IManageMDMLiveData	objMML;

	@Inject
	private EntityService entityService;

	@Inject
	private ForceServiceConnector connector;

	@Inject
	IPMEngineNew objPME;

	@Test
	public void testEntityCRUD() {

		String entityId = null;

		try {
			// // This little trick turns on traces for both the connector *and*
			// the
			// // entity manager because they share the same underlying
			// connection object.
			// // When trace is on, you'll see the SOAP requests and responses
			// on stdout.
			// connector.getConnection().getConfig().setTraceMessage(true);
			// connector.getConnection().getConfig().setPrettyPrintXml(true);
			//
			// MyEntity entity = new MyEntity();
			//
			// ProcessStats m = new ProcessStats();
			// //
			// // //entity.setName("A Test Entity");
			// //
			// // //m.setBilling_Address_City__c("Test City");
			// //
			// m.setTotalNumberOfExactMatchRecords("50000");
			// m.setTotalNumberOfGoldenRecords("25000");
			// // m.setTotalNumberOfNoMatchRecords("10000");
			// m.setTotalNumberOfPOTMatchRecords("15000");
			// // m.setTotalNumberOfRecordsProcessed("100000");
			// entityService.save(m);
			//
			// m = entityService.findEntity("a0xx00000008OIP");
			//
			// // assertNotNull(entity.getId());
			// // entityId = entity.getId();
			// //
			// // entity = entityService.findEntity(entityId);
			// //
			// assertEquals("50000", m.getTotalNumberOfExactMatchRecords());
			// System.out.println(m.getTotalNumberOfPOTMatchRecords());
			// //
			// // entity.setName("A Modified Test Entity");
			// //
			// // entityService.save(entity);
			// //
			// // entity = entityService.findEntity(entityId);
			// //
			// // assertEquals("A Modified Test Entity",entity.getName());
			// //
			// // entityService.delete(entityId);
			// //
			// // entity = entityService.findEntity(entityId);
			// //
			// // assertNull(entity);
			// //
			// // // In case the entity is not null, the finally block will try
			// to clean up using
			// // // the native connection
			// // if(entity==null) entityId = null;
			
			
			logger.info("Started  loadData processing...from Testing BasicCRUDIT." + new Date());
			
			
			// Autowire and call method
			// Bind your code
			objMRC.showDataVolumeinSFDC();
			objMML.deleteLastSessionPartyData();
			objReadCSV.deleteLastSessionTempData();
			objReadCSV.fetchnInsertMDMTempData();
			objReadCSV.fetchnInsertMDMClassificationTempData();
			objReadCSV.fetchnInsertMDMAssociationTempData();
			objMRC.showDataVolumeinSFDC();
			logger.info(" Loading data completes ");
			
			//Testing only Load data this line commented
			// objPME.process();
			
			 // System.out.println("Hello there");
			// List list = entityService.getAllClusteredColumnData();
			// Iterator itr = list.iterator();
			// while(itr.hasNext()){
			// ClusteredColumnData ccd = (ClusteredColumnData)itr.next();
			// System.out.println(ccd.getId() + "|" +
			// ccd.getIncrementalGoldenCopy());
			// }

			/*MDMSourceTemp model = new MDMSourceTemp();
			model.setFirst_Name("Hari Krishna");
			model.setLast_Name("Vajja");
			model.setSSN("239-96-8634");
			entityService.save(model);*/
			
			

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {
			if (entityId != null) {
				try {

					connector.getConnection().delete(new String[] { entityId });
				} catch (ConnectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
