package service.pcs;

import com.alibaba.fastjson.JSONObject;
import controller.global.OpException;
import domain.pcs.PcsPrAllocate;
import domain.pcs.PcsPrAllocateExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import sys.utils.NumberUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PcsPrAllocateService extends PcsBaseMapper {

    public PcsPrAllocate statPcsPrAllocate(int configId){

        Map<Integer, Integer> totalPrCountMap = new HashMap<>();
        int femaleCount = 0;
        int minorityCount = 0;
        int underFiftyCount = 0;

        PcsPrAllocateExample example = new PcsPrAllocateExample();
        example.createCriteria().andConfigIdEqualTo(configId);
        List<PcsPrAllocate> pcsPrAllocates = pcsPrAllocateMapper.selectByExample(example);

        for (PcsPrAllocate pcsPrAllocate : pcsPrAllocates) {

            Map<Integer, Integer> prCountMap = pcsPrAllocate.getPrCountMap();
            for (Map.Entry<Integer, Integer> entry : prCountMap.entrySet()) {
                int type = entry.getKey();
                int count = entry.getValue();
                if(!totalPrCountMap.containsKey(type)){
                    totalPrCountMap.put(type, 0);
                }
                totalPrCountMap.put(type, totalPrCountMap.get(type)+count);
            }

            femaleCount += pcsPrAllocate.getFemaleCount();
            minorityCount += pcsPrAllocate.getMinorityCount();
            underFiftyCount += pcsPrAllocate.getUnderFiftyCount();
        }
        PcsPrAllocate pcsPrAllocate = new PcsPrAllocate();
        pcsPrAllocate.setPrCount(JSONObject.toJSONString(totalPrCountMap));
        pcsPrAllocate.setFemaleCount(femaleCount);
        pcsPrAllocate.setMinorityCount(minorityCount);
        pcsPrAllocate.setUnderFiftyCount(underFiftyCount);

        return pcsPrAllocate;
    }

    // 读取某个分党委的代表最大推荐数量（投票阶段）
    public int getPrMaxCount(int configId, int partyId) {

        PcsPrAllocate pcsPrAllocate = get(configId, partyId);

        if(pcsPrAllocate==null || NumberUtils.trimToZero(pcsPrAllocate.getCandidateCount())<=0){
            throw new OpException("请联系学校党委设置所在院系党委的候选人（代表）推荐人选的数量");
        }

        return NumberUtils.trimToZero(pcsPrAllocate.getCandidateCount());
    }

    // 批量设置
    @Transactional
    public void batchUpdate(int configId, List<PcsPrAllocate> records) {

        for (PcsPrAllocate record : records) {

            PcsPrAllocate pcsPrAllocate = get(configId, record.getPartyId());
            if(pcsPrAllocate!=null){
                record.setId(pcsPrAllocate.getId());
                pcsPrAllocateMapper.updateByPrimaryKeySelective(record);
            }else {
                record.setConfigId(configId);
                pcsPrAllocateMapper.insertSelective(record);
            }
        }
    }

    public PcsPrAllocate get(int configId, int partyId){

        PcsPrAllocateExample example = new PcsPrAllocateExample();
        PcsPrAllocateExample.Criteria criteria = example.createCriteria().andConfigIdEqualTo(configId)
                .andPartyIdEqualTo(partyId);

        List<PcsPrAllocate> pcsPrAllocates = pcsPrAllocateMapper.selectByExample(example);
        return (pcsPrAllocates.size()==0)?null:pcsPrAllocates.get(0);
    }

    public boolean idDuplicate(Integer id, int configId, int partyId) {

        PcsPrAllocateExample example = new PcsPrAllocateExample();
        PcsPrAllocateExample.Criteria criteria = example.createCriteria().andConfigIdEqualTo(configId)
                .andPartyIdEqualTo(partyId);
        if (id != null) criteria.andIdNotEqualTo(id);

        return pcsPrAllocateMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(PcsPrAllocate record) {

        Assert.isTrue(!idDuplicate(null, record.getConfigId(), record.getPartyId()), "分配重复");
        pcsPrAllocateMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PcsPrAllocateExample example = new PcsPrAllocateExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        pcsPrAllocateMapper.deleteByExample(example);
    }
}
