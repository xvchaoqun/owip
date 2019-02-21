package job.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.sys.SchedulerJobService;
import sys.constants.SystemConstants;

/**
 * Created by lm on 2018/9/8.
 */
public class SysJobListener implements JobListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SchedulerJobService schedulerJobService;

    @Override
    public String getName() {

        return this.getClass().getName();
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {

        JobKey jobKey = context.getJobDetail().getKey();
        schedulerJobService.jobLog(jobKey, SystemConstants.SCHEDULER_JOB_TOBEEXECUTED);
        logger.debug("jobName={}, jobGroup={} to be executed.", jobKey.getName(), jobKey.getGroup());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {

        JobKey jobKey = context.getJobDetail().getKey();
        schedulerJobService.jobLog(jobKey, SystemConstants.SCHEDULER_JOB_EXECUTIONVETOED);
        logger.debug("jobName={}, jobGroup={} to be execution vetoed.", jobKey.getName(), jobKey.getGroup());
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {

        JobKey jobKey = context.getJobDetail().getKey();
        schedulerJobService.jobLog(jobKey, SystemConstants.SCHEDULER_JOB_WASEXECUTED);
        logger.debug("jobName={}, jobGroup={} was executed. {}", jobKey.getName(), jobKey.getGroup(),
                jobException==null?"success":jobException.getMessage());
    }
}
