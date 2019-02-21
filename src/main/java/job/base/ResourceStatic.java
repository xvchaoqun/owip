package job.base;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.sys.common.ISysMapper;

public class ResourceStatic implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ISysMapper iSysMapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        // 只更新数据库字段role_count，不影响系统缓存
        try {
            logger.debug("权限角色数量统计...");

            iSysMapper.updateResourceRoleCount();

        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
