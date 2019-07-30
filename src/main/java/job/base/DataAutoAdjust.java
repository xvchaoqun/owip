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
import persistence.common.CommonMapper;
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
    @Autowired
    private CommonMapper commonMapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {
            {
                // 清理民主党派为群众，同时是中共党员的情况
                List<Integer> dpIds = iCadreMapper.selectCadrePartyIds();
                if(dpIds.size()>0) {
                    CadrePartyExample example = new CadrePartyExample();
                    example.createCriteria().andIdIn(dpIds).andTypeEqualTo(CadreConstants.CADRE_PARTY_TYPE_DP);
                    cadrePartyMapper.deleteByExample(example);
                }
            }

            // 自动修改干部的称谓（仅更新还没设置称谓的）
            commonMapper.excuteSql("update sys_user_info ui, cadre c set ui.msg_title=concat(left(ui.realname,1), '老师') " +
                    "where ui.user_id=c.user_id and ui.msg_title is null");

            // 统计是否有挂职经历
            iCadreMapper.refreshHasCrp();
        }catch (Exception ex){
            logger.error("异常", ex);
        }
    }
}
