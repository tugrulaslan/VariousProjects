package com.tugrulaslan;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.log4j.Logger;

/**
 * Created by Tugrul on 17.5.2014.
 */
public class JobClass implements Job {

    static final Logger logger = Logger.getLogger(JobClass.class);

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {

        logger.debug("executing job");
        System.out.println("Scheduled job is running");

    }
}
