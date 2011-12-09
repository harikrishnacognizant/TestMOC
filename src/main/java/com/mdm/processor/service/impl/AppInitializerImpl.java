package com.mdm.processor.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mdm.processor.service.AppInitializer;

@Service
public class AppInitializerImpl implements AppInitializer{
	
	private static final Logger LOGGER = LoggerFactory
	.getLogger(AppInitializerImpl.class);
	
	public void initializeApp(){
		
		LOGGER.info("*************************************************************");
		LOGGER.info("**********Initializing MDM on Cloud application*************");
		LOGGER.info("*                                          				 ");
		LOGGER.info("*    *   *  * *    *   *                         			 ");
		LOGGER.info("*    * * *  *   *  * * *                          			 ");
		LOGGER.info("*    *   *  *   *  *   *                         			 ");
		LOGGER.info("*    *   *  * *    *   *                         			 ");
		LOGGER.info("*                                                           ");
		LOGGER.info("*               * * *   *    *                 			 ");
		LOGGER.info("*               *   *   * *  *                  			 ");
		LOGGER.info("*               *   *   *  * *                 			 ");
		LOGGER.info("*               * * *   *    *                   			 ");
		LOGGER.info("*                                          				 ");
		LOGGER.info("*                        * * *  *      * * *  *   * * *     ");
		LOGGER.info("*                        *      *      *   *  *   * *   *   ");
		LOGGER.info("*                        *      *      *   *  *   * *   *   ");
		LOGGER.info("*                        * * *  * * *  * * *  * * * * *     ");
		LOGGER.info("************************************************************");
		
		
		
	}
	
	
	public boolean reloadData(Object arbitraryArgument){
		
		boolean isReloadingCompleted = false;
		
		LOGGER.info("*************************************************************");
		LOGGER.info("*************************************************************");
		LOGGER.info("******       Realoading Data Will Start       ***************");
		LOGGER.info("*************************************************************");
		LOGGER.info("*************************************************************");
		
		LOGGER.info("*************************************************************");
		LOGGER.info("*************************************************************");
		LOGGER.info("******       Realoading Data Has Ended        ***************");
		LOGGER.info("*************************************************************");
		LOGGER.info("*************************************************************");
		
		return isReloadingCompleted;
	
	}
	

}
