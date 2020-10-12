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
import sys.constants.PcsConstants;
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

        if(stage== PcsConstants.PCS_STAGE_SECOND && !pcsOwService.hasIssue(configId, PcsConstants.PCS_STAGE_FIRST)){
            return false;
        }

        if(stage== PcsConstants.PCS_STAGE_THIRD && !pcsOwService.hasIssue(configId, PcsConstants.PCS_STAGE_SECOND)){
            return false;
        }

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
                .selectPcsBranchBeanList(configId, stage, partyId, null, null,null, new RowBounds());
        for (PcsBranchBean pcsBranchBean : pcsBranchBeans) {

            if(BooleanUtils.isNotTrue(pcsBranchBean.getIsFinished())) records.add(pcsBranchBean);
        }

        return records;
    }

    // 两委委员分党委管理员报送，报送后数据不可修改
    @Transactional
    public void report(int partyId, int configId, byte stage) {

        int size = notFinishedPcsBranchBeans(partyId, configId, stage).size();
        if(size>0){
            throw new OpException("您还有{0}个支部没有完成填报。", size);
        }

        Integer userId = ShiroHelper.getCurrentUserId();

        // 归档各支部的党员数量
        List<PcsBranchBean> pcsBranchBeans = iPcsMapper
                .selectPcsBranchBeanList(configId, stage, partyId, null, null, true, new RowBounds());
        for (PcsBranchBean pcsBranchBean : pcsBranchBeans) {

            PcsRecommend record = new PcsRecommend();
            record.setId(pcsBranchBean.getRecommendId());
            record.setMemberCount(pcsBranchBean.getMemberCount());
            record.setPositiveCount(pcsBranchBean.getPositiveCount());
            record.setTeacherMemberCount(pcsBranchBean.getTeacherMemberCount());
            record.setRetireMemberCount(pcsBranchBean.getRetireMemberCount());
            record.setStudentMemberCount(pcsBranchBean.getStudentMemberCount());

            pcsRecommendMapper.updateByPrimaryKeySelective(record);
        }

        PcsAdminReport record = new PcsAdminReport();
        record.setPartyId(partyId);
        record.setUserId(userId);
        record.setConfigId(configId);
        record.setStage(stage);
        record.setCreateTime(new Date());

        // 归档分党委数量
        PcsParty pcsParty = get(configId, partyId);
        record.setBranchCount(pcsParty.getBranchCount());
        record.setMemberCount(pcsParty.getMemberCount());
        record.setPositiveCount(pcsParty.getPositiveCount());
        record.setTeacherMemberCount(pcsParty.getTeacherMemberCount());
        record.setRetireMemberCount(pcsParty.getRetireMemberCount());
        record.setStudentMemberCount(pcsParty.getStudentMemberCount());

        record.setIp(ContextHelper.getRealIp());

        pcsAdminReportMapper.insertSelective(record);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PcsParty record) {

        return pcsPartyMapper.updateByPrimaryKeySelective(record);
    }

    // 同步当前党组织
    @Transactional
    public void syncPcsPartyAndBranch(Integer pcsPartyId,Integer pcsBranchId) {

        PcsConfig pcsConfig=pcsConfigService.getCurrentPcsConfig();

        if(pcsConfig!=null){

            Integer configId = pcsConfig.getId();

            //根据分党委Id同步更新分党委统计数量，分党委下党支部统计数量
            if(pcsPartyId!=null){
                PcsParty pcsParty = pcsPartyMapper.selectByPrimaryKey(pcsPartyId);
                List<PcsParty> pcsParties=iPcsMapper.expectPcsPartyList(configId,pcsParty.getPartyId());
                List<PcsBranch> pcsBranches=iPcsMapper.expectPcsBranchList(configId,pcsParty.getPartyId(),null);

                if(pcsParties.size()>0) {
                    PcsParty _pcsParty= pcsParties.get(0);
                    _pcsParty.setId(pcsParty.getId());
                    _pcsParty.setCurrentStage(pcsParty.getCurrentStage());
                    _pcsParty.setIsDeleted(pcsParty.getIsDeleted());
                   pcsPartyMapper.updateByPrimaryKey(_pcsParty);

                    for (PcsBranch record:pcsBranches){
                        Integer partyId=record.getPartyId();
                        Integer branchId=record.getBranchId();

                        PcsBranch pcsBranch=pcsBranchService.get(configId,partyId,branchId);

                        if(pcsBranch!=null) {
                            record.setId(pcsBranch.getId());
                            record.setIsDeleted(pcsBranch.getIsDeleted());
                            pcsBranchMapper.updateByPrimaryKey(record);
                        }else{
                            record.setIsDeleted(false);
                            pcsBranchMapper.insertSelective(record);
                        }
                    }
                   iPcsMapper.updatePcsPartyCount(configId,pcsParty.getPartyId());
               }
               return;
            }

            //根据党支部Id同步更新党支部统计数量
            if(pcsBranchId!=null){
                PcsBranch pcsBranch = pcsBranchMapper.selectByPrimaryKey(pcsBranchId);
                List<PcsBranch> pcsBranches=iPcsMapper.expectPcsBranchList(configId,pcsBranch.getPartyId(),pcsBranch.getBranchId());

                if(pcsBranches.size()>0) {
                    PcsBranch _pcsBranch= pcsBranches.get(0);
                    _pcsBranch.setId(pcsBranch.getId());
                    _pcsBranch.setIsDeleted(pcsBranch.getIsDeleted());
                    pcsBranchMapper.updateByPrimaryKey(_pcsBranch);
                    iPcsMapper.updatePcsPartyCount(configId,pcsBranch.getPartyId());
                }
                return;
            }

            //同步更新所有分党委及党支部统计数量
           List<PcsParty> pcsParties=iPcsMapper.expectPcsPartyList(configId,null);
           List<PcsBranch> pcsBranches=iPcsMapper.expectPcsBranchList(configId,null,null);

           for (PcsParty record:pcsParties){

               Integer partyId=record.getPartyId();

               PcsParty pcsParty=get(configId,partyId);

               if(pcsParty!=null) {
                   record.setId(pcsParty.getId());
                   record.setIsDeleted(pcsParty.getIsDeleted());
                   record.setCurrentStage(pcsParty.getCurrentStage());
                   pcsPartyMapper.updateByPrimaryKey(record);
               }else{
                    record.setIsDeleted(false);
                   pcsPartyMapper.insertSelective(record);
               }
           }

           for (PcsBranch record:pcsBranches){
               Integer partyId=record.getPartyId();
               Integer branchId=record.getBranchId();

               PcsBranch pcsBranch=pcsBranchService.get(configId,partyId,branchId);

               if(pcsBranch!=null) {
                   record.setId(pcsBranch.getId());
                   record.setIsDeleted(pcsBranch.getIsDeleted());
                   pcsBranchMapper.updateByPrimaryKey(record);
               }else{
                   record.setIsDeleted(false);
                   pcsBranchMapper.insertSelective(record);
               }
           }

           //更新所有分党委统计数量
            for(PcsParty record:pcsParties){

                Integer partyId=record.getPartyId();
                iPcsMapper.updatePcsPartyCount(configId,partyId);
            }

        }
    }

    // 删除分党委，同时删除党支部
    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        {
            PcsBranchExample example = new PcsBranchExample();
            example.createCriteria().andPartyIdIn(Arrays.asList(ids));
            List<PcsBranch> pcsBranches = pcsBranchMapper.selectByExample(example);

            if(pcsBranches.size()>0) {
                List<Integer> pcsBranchIds = pcsBranches.stream().map(PcsBranch::getId).collect(Collectors.toList());
                pcsBranchService.batchDel(pcsBranchIds.toArray(new Integer[]{}));
            }
        }

        {
            PcsPartyExample example = new PcsPartyExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));

            PcsParty record = new PcsParty();
            record.setIsDeleted(true);
            pcsPartyMapper.updateByExample(record, example);
        }


    }
}
