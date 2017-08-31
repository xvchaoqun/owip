package service.pcs;

import domain.pcs.PcsAdminReport;
import domain.pcs.PcsAdminReportExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import shiro.ShiroHelper;
import sys.utils.ContextHelper;

import java.util.Date;

/**
 * Created by lm on 2017/8/31.
 */
@Service
public class PcsPartyService extends BaseMapper {

    @Autowired
    private PcsOwService pcsOwService;

    // 分党委是否可以修改当前阶段的数据
    public boolean allowModify(int partyId, int configId, byte stage){

        // 分党委已经下发名单之后，则分党委不可修改数据
        if(pcsOwService.hasIssue(configId, stage)) return false;

        // 分党委已经上报，不可以修改数据
        PcsAdminReportExample example = new PcsAdminReportExample();
        example.createCriteria().andPartyIdEqualTo(partyId)
                .andConfigIdEqualTo(configId)
                .andStageEqualTo(stage);

        return pcsAdminReportMapper.countByExample(example)==0;
    }

    // 管理员上报，上报后数据不可修改
    @Transactional
    public void report(int partyId, int configId, byte stage) {

        Integer userId = ShiroHelper.getCurrentUserId();

        PcsAdminReport record = new PcsAdminReport();
        record.setPartyId(partyId);
        record.setUserId(userId);
        record.setConfigId(configId);
        record.setStage(stage);
        record.setCreateTime(new Date());
        record.setIp(ContextHelper.getRealIp());

        pcsAdminReportMapper.insertSelective(record);
    }
}
