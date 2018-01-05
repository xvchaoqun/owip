package job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.sys.SysUserSyncService;

@Component
public class SyncJzgSalary implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserSyncService sysUserSyncService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("同步本月在职工资信息...");
        try {
            sysUserSyncService.syncJzgSalary(true);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
