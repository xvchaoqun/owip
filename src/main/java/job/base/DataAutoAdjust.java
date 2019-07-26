package job.base;

import domain.cadre.CadrePartyExample;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.cadre.CadrePartyMapper;
import persistence.cadre.common.ICadreMapper;
import sys.constants.CadreConstants;

import java.util.List;

/**
 * 系统数据自动校正
 */
public class DataAutoAdjust implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ICadreMapper iCadreMapper;
    @Autowired
    private CadrePartyMapper cadrePartyMapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {
            {
                // 清理民主党派为群众，同时是中共党员的情况
                List<Integer> dpIds = iCadreMapper.selectCadrePartyIds();
                CadrePartyExample example = new CadrePartyExample();
                example.createCriteria().andIdIn(dpIds).andTypeEqualTo(CadreConstants.CADRE_PARTY_TYPE_DP);
                cadrePartyMapper.deleteByExample(example);
            }

            // 统计是否有挂职经历
            iCadreMapper.refreshHasCrp();
        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
