package job.cet;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.common.CommonMapper;

public class CetAutoAdjust  implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommonMapper commonMapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {

            // 自动修改培训班的结课状态
            commonMapper.excuteSql("update cet_train SET is_finished=1 WHERE is_on_campus=1 AND end_date < NOW()");
        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}