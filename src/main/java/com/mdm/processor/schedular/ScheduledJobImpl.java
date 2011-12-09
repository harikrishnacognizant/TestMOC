package com.mdm.processor.schedular;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledJobImpl implements ScheduledJob{


	@Scheduled(fixedDelay = 43200000)
	public void processMDMRecord() {
		System.out.println("Triggering process at " + new Date());
		//process
		System.out.println("Process completes at " + new Date());
	}
}
