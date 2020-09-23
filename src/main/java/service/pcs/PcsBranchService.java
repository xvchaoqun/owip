package service.pcs;

import domain.pcs.PcsBranch;
import domain.pcs.PcsBranchExample;
import domain.pcs.PcsRecommendExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class PcsBranchService extends PcsBaseMapper {

    public PcsBranch get(int configId, int partyId, Integer branchId) {

        PcsBranchExample example = new PcsBranchExample();
        PcsBranchExample.Criteria criteria = example.createCriteria().andConfigIdEqualTo(configId).andPartyIdEqualTo(partyId);
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        List<PcsBranch> pcsBranchs = pcsBranchMapper.selectByExample(example);

        return pcsBranchs.size() == 0 ? null : pcsBranchs.get(0);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PcsBranch record) {

         pcsBranchMapper.updateByPrimaryKeySelective(record);

         //更新分党委统计数量

        PcsBranch pcsBranch=pcsBranchMapper.selectByPrimaryKey(record.getId());
        iPcsMapper.updatePcsPartyCount(pcsBranch.getConfigId(),pcsBranch.getPartyId());

    }

    // 设置不参与党代会的党支部
    @Transactional
    public void exclude(Integer[] ids ,Boolean isDeleted){

        if(ids==null || ids.length==0) return;

        PcsBranch record =new PcsBranch();
        record.setIsDeleted(isDeleted);

        PcsBranchExample example = new PcsBranchExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsBranchMapper.updateByExampleSelective(record,example);

        //更新分党委统计数量
        for(Integer id:ids){
            PcsBranch pcsBranch=pcsBranchMapper.selectByPrimaryKey(id);
            iPcsMapper.updatePcsPartyCount(pcsBranch.getConfigId(),pcsBranch.getPartyId());
        }

    }

    // 删除党支部
    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        for(Integer id:ids){

            PcsBranch pcsBranch=pcsBranchMapper.selectByPrimaryKey(id);
            int configId = pcsBranch.getConfigId();
            int partyId = pcsBranch.getPartyId();
            Integer branchId = pcsBranch.getBranchId();
            PcsRecommendExample example = new PcsRecommendExample();
            PcsRecommendExample.Criteria criteria = example.createCriteria().andConfigIdEqualTo(configId)
                    .andPartyIdEqualTo(partyId);
            if(branchId!=null){
                criteria.andBranchIdEqualTo(branchId);
            }
            if(pcsRecommendMapper.countByExample(example)>0){
                // 存在被推荐的记录，假删除，设置为不参与党代会
                PcsBranch record = new PcsBranch();
                record.setId(id);
                record.setIsDeleted(true);
                pcsBranchMapper.updateByPrimaryKey(record);
            }else {
                // 不存在被推荐的记录，真删除
                pcsBranchMapper.deleteByPrimaryKey(id);
            }

            //更新分党委统计数量
            iPcsMapper.updatePcsPartyCount(configId, partyId);
        }
    }
}
