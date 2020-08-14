package service.pcs;

import domain.pcs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PcsPollService extends PcsBaseMapper {

    @Autowired
    private PcsConfigService pcsConfigService;

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

    public Map<Integer, PcsPoll> findAll() {

        PcsPollExample example = new PcsPollExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<PcsPoll> records = pcsPollMapper.selectByExample(example);
        Map<Integer, PcsPoll> map = new LinkedHashMap<>();
        for (PcsPoll record : records) {
            map.put(record.getId(), record);
        }

        return map;
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
