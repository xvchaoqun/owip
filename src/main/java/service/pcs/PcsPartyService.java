package service.pcs;

import controller.global.OpException;
import domain.pcs.*;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.pcs.common.PcsBranchBean;
import shiro.ShiroHelper;
import sys.utils.ContextHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lm on 2017/8/31.
 */
@Service
public class PcsPartyService extends PcsBaseMapper {

    @Autowired
    private PcsOwService pcsOwService;
    @Autowired
    private PcsConfigService pcsConfigService;
    @Autowired
    private PcsBranchService pcsBranchService;

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

    @Transactional
    public int updateByPrimaryKeySelective(PcsParty record) {

        return pcsPartyMapper.updateByPrimaryKeySelective(record);
    }

    // 同步当前党组织
    @Transactional
    public void sync() {

        PcsConfig pcsConfig=pcsConfigService.getCurrentPcsConfig();

        if(pcsConfig!=null){

            Integer configId = pcsConfig.getId();
            List<PcsParty> pcsParties=iPcsMapper.expectPcsPartyList(configId);
            List<PcsBranch> pcsBranches=iPcsMapper.expectPcsBranchList(pcsConfig.getId());

           for (PcsParty record:pcsParties){

               Integer partyId=record.getPartyId();

               PcsParty pcsParty=get(configId,partyId);

               if(pcsParty!=null) {
                   pcsPartyMapper.updateByPrimaryKey(record);
               }else{
                   pcsPartyMapper.insertSelective(record);
               }
           }

           for (PcsBranch record:pcsBranches){
               Integer partyId=record.getPartyId();
               Integer branchId=record.getBranchId();

               PcsBranch pcsBranch=pcsBranchService.get(configId,partyId,branchId);

               if(pcsBranch!=null) {
                   record.setId(pcsBranch.getId());
                   pcsBranchMapper.updateByPrimaryKeySelective(record);
               }else{
                   record.setIsDeleted(false);
                   pcsBranchMapper.insertSelective(record);
               }
           }

        }
    }

    // 同步当前党组织
    @Transactional
    public void batchSync(Integer[] ids) {

        PcsConfig pcsConfig=pcsConfigService.getCurrentPcsConfig();

        if(pcsConfig!=null){

            PcsPartyExample example = new PcsPartyExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            List<PcsParty> pcsPartyList = pcsPartyMapper.selectByExample(example);
            Integer[]  partyIds =pcsPartyList.stream().map(PcsParty::getPartyId)
                                         .collect(Collectors.toList()).stream().toArray(Integer[]::new);
            Integer configId = pcsConfig.getId();
           /* List<PcsParty> pcsPartys=iPcsMapper.expectPcsPartyList(configId, StringUtils.join(partyIds, ","));*/
         /*   List<PcsBranch> pcsBtanchs=iPcsMapper.expectPcsBranchList(pcsConfig.getId());*/

          /*  for (PcsParty record:pcsPartys){
                Integer partyId=record.getPartyId();

                PcsParty pcsParty=get(configId,partyId);

                if(pcsParty!=null) {
                    pcsPartyMapper.updateByPrimaryKey(record);
                }else{
                    pcsPartyMapper.insertSelective(record);
                }
            }*/

           /* for (PcsBranch record:pcsBtanchs){
                Integer partyId=record.getPartyId();
                Integer branchId=record.getBranchId();

                PcsBranch pcsBranch=pcsBranchService.get(configId,partyId,branchId);

                if(pcsBranch!=null) {
                    pcsBranchMapper.updateByPrimaryKey(record);
                }else{
                    pcsBranchMapper.insertSelective(record);
                }
            }*/

        }
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;
        PcsPartyExample example = new PcsPartyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsPartyMapper.deleteByExample(example);

    }
}
