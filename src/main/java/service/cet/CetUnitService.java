package service.cet;

import domain.cet.CetUnit;
import domain.cet.CetUnitExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CetUnitService extends BaseMapper {

    public boolean idDuplicate(Integer id, int unitId){

        CetUnitExample example = new CetUnitExample();
        CetUnitExample.Criteria criteria = example.createCriteria().andUnitIdEqualTo(unitId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetUnitMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CetUnit record){

        cetUnitMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetUnitMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetUnitExample example = new CetUnitExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetUnitMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetUnit record){

        return cetUnitMapper.updateByPrimaryKeySelective(record);
    }
}
