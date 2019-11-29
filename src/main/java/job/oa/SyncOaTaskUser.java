package job.oa;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.oa.common.IOaTaskUserMapper;
import service.oa.OaTaskUserService;
import sys.constants.OaConstants;

import java.util.List;

public class SyncOaTaskUser implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OaTaskUserService oaTaskUserService;
    @Autowired
    private IOaTaskUserMapper iOaTaskUserMapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("定时更新协同任务统计缓存。。。");
        List<Integer> userIds = iOaTaskUserMapper.getUserIds(OaConstants.OA_TASK_STATUS_PUBLISH);

        for (Integer userId : userIds){

            oaTaskUserService.updateTaskUserCount(userId);
        }
    }
}
