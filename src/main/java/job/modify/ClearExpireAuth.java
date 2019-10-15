package job.modify;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.modify.common.IModifyMapper;
import sys.constants.CadreConstants;

public class ClearExpireAuth implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IModifyMapper iModifyMapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("清理干部和已过期干部信息修改权限的规则...");
        try {
            iModifyMapper.removeAllCadres(CadreConstants.CADRE_STATUS_SET);
            iModifyMapper.clearExpireAuth();
        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}