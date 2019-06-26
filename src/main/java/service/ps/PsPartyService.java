package service.ps;

import domain.ps.PsParty;
import domain.ps.PsPartyExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class PsPartyService extends PsBaseMapper {

    @Transactional
    public void insertSelective(PsParty record){

        psPartyMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        psPartyMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PsPartyExample example = new PsPartyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        psPartyMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PsParty record){

        psPartyMapper.updateByPrimaryKeySelective(record);
    }
}
