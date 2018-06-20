package service.cadre;

import domain.cadre.CadreParty;
import domain.cadre.CadrePartyExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.List;

/**
 * Created by lm on 2018/6/20.
 */
@Service
public class CadrePartyService extends BaseMapper{

    private Logger logger = LoggerFactory.getLogger(getClass());

    public boolean idDuplicate(Integer id, int userId, byte type){

        CadrePartyExample example = new CadrePartyExample();
        CadrePartyExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId)
                .andTypeEqualTo(type);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cadrePartyMapper.countByExample(example) > 0;
    }

    public CadreParty get(int userId, byte type){

        CadrePartyExample example = new CadrePartyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andTypeEqualTo(type);

        List<CadreParty> cadreParties = cadrePartyMapper.selectByExample(example);
        return cadreParties.size()>0?cadreParties.get(0):null;
    }
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "Cadre:ALL", allEntries = true)
    })
    public void del(int userId, Byte type){

        CadrePartyExample example = new CadrePartyExample();
        CadrePartyExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(type!=null) {
            criteria.andTypeEqualTo(type);
        }
        cadrePartyMapper.deleteByExample(example);
    }
}
