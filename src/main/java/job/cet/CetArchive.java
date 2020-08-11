package job.cet;

import domain.cet.CetAnnual;
import domain.cet.CetAnnualExample;
import domain.cet.CetProject;
import domain.cet.CetProjectExample;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.cet.CetAnnualMapper;
import persistence.cet.CetProjectMapper;
import persistence.cet.common.ICetMapper;
import service.cet.CetAnnualObjService;
import service.cet.CetProjectObjService;
import service.cet.CetRecordService;
import sys.constants.CetConstants;

import java.util.List;

public class CetArchive implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CetProjectObjService cetProjectObjService;
    @Autowired
    private CetProjectMapper cetProjectMapper;
    @Autowired
    private CetAnnualObjService cetAnnualObjService;
    @Autowired
    private CetAnnualMapper cetAnnualMapper;
    @Autowired
    private ICetMapper iCetMapper;

    @Autowired
    private CetRecordService cetRecordService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("归档培训学时...");
        try {
            // 过程培训数据归档
            CetProjectExample example = new CetProjectExample();
            example.createCriteria().andIsDeletedEqualTo(false)
                    .andStatusEqualTo(CetConstants.CET_PROJECT_STATUS_PASS)
                    .andHasArchiveEqualTo(false);
            List<CetProject> cetProjects = cetProjectMapper.selectByExample(example);
            for (CetProject cetProject : cetProjects) {
                cetProjectObjService.archiveProject(cetProject.getId());
            }

            // 删除无效培训记录
            iCetMapper.removeDeletedCetRecords();
            // 同步上级调训数据（含党校其他培训）
            cetRecordService.syncAllUpperTrain();
            // 同步过程培训数据
            cetRecordService.syncAllProjectObj(null);
            // 同步二级党委培训数据
            cetRecordService.syncAllUnitTrian();

            // 更新年度学习档案
            List<CetAnnual> cetAnnuals = cetAnnualMapper.selectByExample(new CetAnnualExample());
            for (CetAnnual cetAnnual : cetAnnuals) {
                cetAnnualObjService.archiveFinishPeriod(cetAnnual.getId());
            }

        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
