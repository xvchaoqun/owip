package service.party;

import domain.MemberTransfer;
import domain.MemberTransferExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class MemberTransferService extends BaseMapper {

    public boolean idDuplicate(Integer id, Integer userId){

        MemberTransferExample example = new MemberTransferExample();
        MemberTransferExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return memberTransferMapper.countByExample(example) > 0;
    }

    @Transactional
    public int insertSelective(MemberTransfer record){

        return memberTransferMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        memberTransferMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberTransferExample example = new MemberTransferExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberTransferMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberTransfer record){
        return memberTransferMapper.updateByPrimaryKeySelective(record);
    }
}
