package service.pmd;

import domain.pmd.PmdPayParty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class PmdService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PmdPartyAdminService pmdPartyAdminService;
    @Autowired
    private PmdPayPartyService pmdPayPartyService;
    @Autowired
    private PmdBranchAdminService pmdBranchAdminService;

    public void syncAdmin(){

        logger.info("同步党建分党委管理员...");
        try {
            pmdPartyAdminService.syncPartyAdmins();
        }catch (Exception ex){
            logger.error("异常", ex);
        }

        logger.info("同步党建党支部管理员...");
        try {
            Map<Integer, PmdPayParty> allPayPartyIdSet = pmdPayPartyService.getAllPayPartyIdSet(null);
            pmdBranchAdminService.syncBranchAdmins(new ArrayList<>(allPayPartyIdSet.keySet()));
        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
