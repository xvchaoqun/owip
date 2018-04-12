package service.cet;

import domain.cet.CetParty;
import domain.cet.CetPartyExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CetPartyService extends BaseMapper {

    public boolean idDuplicate(Integer id, int partyId){

        CetPartyExample example = new CetPartyExample();
        CetPartyExample.Criteria criteria = example.createCriteria().andPartyIdEqualTo(partyId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetPartyMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CetParty record){

        cetPartyMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetPartyMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetPartyExample example = new CetPartyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetPartyMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetParty record){

        return cetPartyMapper.updateByPrimaryKeySelective(record);
    }
}
