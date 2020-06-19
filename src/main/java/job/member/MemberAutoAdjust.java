package job.member;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.member.common.IMemberMapper;

public class MemberAutoAdjust implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IMemberMapper iMemberMapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {

            int count = iMemberMapper.adjustMemberApply();
            if(count>0){
                logger.info("更新党员发展模块中的预备党员所在党组织与党员库中不一致的情况..." + count);
            }

        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}