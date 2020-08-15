package service.pcs;

import controller.global.OpException;
import domain.pcs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.party.PartyService;

import java.util.*;

@Service
public class PcsPollService extends PcsBaseMapper {

    @Autowired
    private PcsConfigService pcsConfigService;
    @Autowired
    private PartyService partyService;

    @Transactional
    public void insertSelective(PcsPoll record){

        pcsPollMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        pcsPollMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        //党代会为假删除，其他内容真删除
        if(ids==null || ids.length==0) return;

        PcsPoll record = new PcsPoll();
        record.setInspectorNum(0);
        record.setInspectorFinishNum(0);
        record.setPositiveFinishNum(0);
        record.setIsDeleted(true);

        PcsPollExample example = new PcsPollExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsPollMapper.updateByExampleSelective(record, example);

        PcsPollResultExample resultExample = new PcsPollResultExample();
        resultExample.createCriteria().andPollIdIn(Arrays.asList(ids));
        pcsPollResultMapper.deleteByExample(resultExample);

        PcsPollInspectorExample inspectorExample = new PcsPollInspectorExample();
        inspectorExample.createCriteria().andPollIdIn(Arrays.asList(ids));
        pcsPollInspectorMapper.deleteByExample(inspectorExample);

        PcsPollCandidateExample candidateExample = new PcsPollCandidateExample();
        candidateExample.createCriteria().andPollIdIn(Arrays.asList(ids));
        pcsPollCandidateMapper.deleteByExample(candidateExample);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PcsPoll record){

        pcsPollMapper.updateByPrimaryKeySelective(record);
    }

    //党代会投票是否已存在
    public Boolean isPcsPollExisted(PcsPoll record) {

        Integer partyId = record.getPartyId();
        Integer branchId = record.getBranchId();
        if (partyId != null && branchId == null) {
            if (!partyService.isDirectBranch(partyId)) {
                throw new OpException("请选择所属党支部");
            }
        }
        PcsPollExample example = new PcsPollExample();
        PcsPollExample.Criteria criteria = example.createCriteria().andConfigIdEqualTo(record.getConfigId());
        if (partyId != null){
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null){
            criteria.andBranchIdEqualTo(branchId);
        }
        if(record.getId()!=null){
            criteria.andIdNotEqualTo(record.getId());
        }

        return pcsPollMapper.countByExample(example)>0;
    }

    @Transactional
    public void batchReport(Integer[] ids) {

        if(ids==null || ids.length==0) return;
        PcsPoll record = new PcsPoll();
        record.setHasReport(true);

        PcsPollExample example = new PcsPollExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsPollMapper.updateByExampleSelective(record, example);
    }

    //得到当前党代会投票的ids
    public List<Integer> getCurrentPcsPollId() {

        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        Integer configId = pcsConfig.getId();

        PcsPollExample example = new PcsPollExample();
        example.createCriteria().andConfigIdEqualTo(configId).andIsDeletedEqualTo(false);
        List<PcsPoll> pcsPolls = pcsPollMapper.selectByExample(example);
        List<Integer> pollIdList = new ArrayList<>();
        if (pcsPolls != null && pcsPolls.size() > 0) {
            for (PcsPoll pcsPoll : pcsPolls) {
                pollIdList.add(pcsPoll.getId());
            }
        }

        return pollIdList;
    }
}
