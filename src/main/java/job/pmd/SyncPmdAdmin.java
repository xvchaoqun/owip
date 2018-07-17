package job.pmd;

import domain.pmd.PmdPayParty;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.pmd.PmdBranchAdminService;
import service.pmd.PmdPartyAdminService;
import service.pmd.PmdPayPartyService;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by lm on 2018/4/2.
 */
public class SyncPmdAdmin implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PmdPartyAdminService pmdPartyAdminService;
    @Autowired
    private PmdPayPartyService pmdPayPartyService;
    @Autowired
    private PmdBranchAdminService pmdBranchAdminService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("同步党建分党委管理员...");
        try {
            pmdPartyAdminService.syncPartyAdmins();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        logger.info("同步党建党支部管理员...");
        try {
            Map<Integer, PmdPayParty> allPayPartyIdSet = pmdPayPartyService.getAllPayPartyIdSet(null);
            pmdBranchAdminService.syncBranchAdmins(new ArrayList<>(allPayPartyIdSet.keySet()));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
