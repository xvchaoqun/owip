package job.party;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import service.party.BranchMemberGroupService;
import service.party.PartyMemberGroupService;
import service.party.PartyService;
import shiro.ShiroHelper;
import sys.HttpResponseMethod;

import javax.servlet.http.HttpServletRequest;

public class PartyRemindJob implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PartyMemberGroupService partyMemberGroupService;
    @Autowired
    private BranchMemberGroupService branchMemberGroupService;

    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("基层党委换届提醒");
        try {
            branchMemberGroupService.tranRemind();
        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
