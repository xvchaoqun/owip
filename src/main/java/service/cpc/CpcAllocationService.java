package service.cpc;

import domain.cpc.CpcAllocation;
import domain.cpc.CpcAllocationExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class CpcAllocationService extends BaseMapper {


    @Transactional
    public void insertSelective(CpcAllocation record){

        cpcAllocationMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cpcAllocationMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CpcAllocationExample example = new CpcAllocationExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cpcAllocationMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CpcAllocation record){
        return cpcAllocationMapper.updateByPrimaryKeySelective(record);
    }

    // 更新配置
    public void update(List<CpcAllocation> records) {

        for (CpcAllocation record : records) {

            CpcAllocationExample example = new CpcAllocationExample();
            example.createCriteria().andUnitIdEqualTo(record.getUnitId()).andAdminLevelIdEqualTo(record.getAdminLevelId());
            cpcAllocationMapper.deleteByExample(example);

            cpcAllocationMapper.insertSelective(record);
        }
    }
}
