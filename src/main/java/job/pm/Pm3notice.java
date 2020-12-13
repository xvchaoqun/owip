package job.pm;

import domain.base.ContentTpl;
import domain.pm.Pm3Guide;
import domain.pm.Pm3GuideExample;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.pm.Pm3GuideMapper;
import service.pm.Pm3GuideService;
import sys.constants.ContentTplConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.util.Date;
import java.util.List;

public class Pm3notice implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Pm3GuideMapper pm3GuideMapper;
    @Autowired
    private Pm3GuideService pm3GuideService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("给分党委管理员发送组织生活月报提醒");

        Pm3GuideExample example = new Pm3GuideExample();
        example.createCriteria().andReportTimeGreaterThan(new Date());
        List<Pm3Guide> pm3Guides = pm3GuideMapper.selectByExample(example);

        for (Pm3Guide pm3Guide : pm3Guides) {

            ContentTpl tpl = CmTag.getContentTpl(ContentTplConstants.PM_3_NOTICE_PARTY);
            String notice = String.format(tpl.getContent(), DateUtils.formatDate(pm3Guide.getMeetingMonth(), "yyyy年MM月"));
            pm3GuideService.noticeUnSubmitParty(pm3Guide.getMeetingMonth(), notice);
        }
    }
}
