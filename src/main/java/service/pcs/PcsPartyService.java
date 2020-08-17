package service.pcs;

import controller.global.OpException;
import domain.pcs.PcsAdminReport;
import domain.pcs.PcsAdminReportExample;
import domain.pcs.PcsParty;
import domain.pcs.PcsPartyExample;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.pcs.common.PcsBranchBean;
import shiro.ShiroHelper;
import sys.utils.ContextHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lm on 2017/8/31.
 */
@Service
public class PcsPartyService extends PcsBaseMapper {

    @Autowired
    private PcsOwService pcsOwService;

     public PcsParty get(int configId, int partyId){

        PcsPartyExample example = new PcsPartyExample();
        example.createCriteria().andConfigIdEqualTo(configId).andPartyIdEqualTo(partyId);

        List<PcsParty> pcsPartys = pcsPartyMapper.selectByExample(example);
        
        return pcsPartys.size()==0?null:pcsPartys.get(0);
    }

    // 分党委是否可以修改当前阶段的数据
    public boolean allowModify(int partyId, int configId, byte stage){

        // 组织部已经下发该阶段名单之后，则分党委不可修改数据
        if(pcsOwService.hasIssue(configId, stage)) return false;

        // 分党委已经报送，不可以修改数据
        PcsAdminReportExample example = new PcsAdminReportExample();
        example.createCriteria().andPartyIdEqualTo(partyId)
                .andConfigIdEqualTo(configId)
                .andStageEqualTo(stage);

        return pcsAdminReportMapper.countByExample(example)==0;
    }

    // 报送之前，需要先判断一下是不是所有的支部的推荐情况都完成了
    public List<PcsBranchBean> notFinishedPcsBranchBeans(int partyId, int configId, byte stage){
        List<PcsBranchBean> records = new ArrayList<>();
        List<PcsBranchBean> pcsBranchBeans = iPcsMapper
                .selectPcsBranchBeanList(configId, stage, partyId, null, null, new RowBounds());
        for (PcsBranchBean pcsBranchBean : pcsBranchBeans) {

            if(BooleanUtils.isNotTrue(pcsBranchBean.getIsFinished())) records.add(pcsBranchBean);
        }

        return records;
    }

    // 分党委管理员报送，报送后数据不可修改
    @Transactional
    public void report(int partyId, int configId, byte stage) {

        int size = notFinishedPcsBranchBeans(partyId, configId, stage).size();
        if(size>0){
            throw new OpException("您还有{0}个支部没有完成填报。", size);
        }

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
