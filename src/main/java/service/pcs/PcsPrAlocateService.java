package service.pcs;

import domain.pcs.PcsPrAllocate;
import domain.pcs.PcsPrAllocateExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class PcsPrAlocateService extends BaseMapper {


    // 批量设置
    @Transactional
    public void batchAdd(int configId, List<PcsPrAllocate> records) {

        for (PcsPrAllocate record : records) {

            record.setConfigId(configId);
            insertSelective(record);
        }
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
