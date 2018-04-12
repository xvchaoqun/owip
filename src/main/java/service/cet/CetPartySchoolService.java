package service.cet;

import domain.cet.CetPartySchool;
import domain.cet.CetPartySchoolExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CetPartySchoolService extends BaseMapper {

    public boolean idDuplicate(Integer id, int partySchoolId){

        CetPartySchoolExample example = new CetPartySchoolExample();
        CetPartySchoolExample.Criteria criteria = example.createCriteria().andPartySchoolIdEqualTo(partySchoolId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetPartySchoolMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CetPartySchool record){

        cetPartySchoolMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetPartySchoolMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetPartySchoolExample example = new CetPartySchoolExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetPartySchoolMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetPartySchool record){

        return cetPartySchoolMapper.updateByPrimaryKeySelective(record);
    }
}
