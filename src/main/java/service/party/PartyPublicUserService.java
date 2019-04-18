package service.party;

import domain.party.PartyPublicUser;
import domain.party.PartyPublicUserExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class PartyPublicUserService extends BaseMapper {

    public boolean idDuplicate(Integer id, int publicId, int userId){

        PartyPublicUserExample example = new PartyPublicUserExample();
        PartyPublicUserExample.Criteria criteria = example.createCriteria()
                .andPublicIdEqualTo(publicId).andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return partyPublicUserMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(PartyPublicUser record){

        Assert.isTrue(!idDuplicate(null, record.getPublicId(), record.getUserId()), "duplicate");
        partyPublicUserMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PartyPublicUserExample example = new PartyPublicUserExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        partyPublicUserMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(PartyPublicUser record){

        Assert.isTrue(!idDuplicate(record.getId(), record.getPublicId(), record.getUserId()), "duplicate");
        return partyPublicUserMapper.updateByPrimaryKeySelective(record);
    }
}
