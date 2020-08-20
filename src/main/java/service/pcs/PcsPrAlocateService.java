package service.pcs;

import controller.global.OpException;
import domain.pcs.PcsPrAllocate;
import domain.pcs.PcsPrAllocateExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import sys.utils.NumberUtils;

import java.util.Arrays;
import java.util.List;

@Service
public class PcsPrAlocateService extends PcsBaseMapper {

    //读取分党委的代表最大推荐数量
    public int getPrMaxCount(int configId, int partyId) {

        PcsPrAllocate pcsPrAllocate = get(configId, partyId);

        if(pcsPrAllocate==null){
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
