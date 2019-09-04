package service.cg;

import domain.cg.CgUnit;
import domain.cg.CgUnitExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;

@Service
public class CgUnitService extends CgBaseMapper {

    public boolean idDuplicate(Integer id, Integer teamId, boolean isCurrent){

        if(!isCurrent) return false;

        CgUnitExample example = new CgUnitExample();
        CgUnitExample.Criteria criteria = example.createCriteria()
                .andTeamIdEqualTo(teamId)
                .andIsCurrentEqualTo(isCurrent);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cgUnitMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CgUnit record){

        Assert.isTrue(!idDuplicate(null, record.getUnitId(), record.getIsCurrent()), "duplicate");
        cgUnitMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cgUnitMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CgUnitExample example = new CgUnitExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cgUnitMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CgUnit record){
        Assert.isTrue(!idDuplicate(record.getId(), record.getUnitId(), record.getIsCurrent()), "duplicate");
        cgUnitMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void updateCgRuleStatus(Integer[] ids, boolean isCurrent){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids){
            CgUnit record = new CgUnit();
            record.setId(id);
            record.setIsCurrent(isCurrent);
            cgUnitMapper.updateByPrimaryKeySelective(record);
        }
    }
}
