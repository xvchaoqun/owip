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
            List<CetProject> cetProjects = cetProjectMapper.selectByExample(new CetProjectExample());
            for (CetProject cetProject : cetProjects) {
                cetProjectObjService.refreshAllObjsFinishPeriod(cetProject.getId());
            }

            iCetMapper.removeDeletedCetRecords();
            cetRecordService.syncAllUpperTrain();
            cetRecordService.syncAllProjectObj();
            cetRecordService.syncAllUnitTrian();

            List<CetAnnual> cetAnnuals = cetAnnualMapper.selectByExample(new CetAnnualExample());
            for (CetAnnual cetAnnual : cetAnnuals) {
                cetAnnualObjService.archiveFinishPeriod(cetAnnual.getId());
            }

        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
