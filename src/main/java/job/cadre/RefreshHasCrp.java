package job.cadre;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.cadre.common.ICadreMapper;

public class RefreshHasCrp implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ICadreMapper iCadreMapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {

            iCadreMapper.refreshHasCrp();
        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
