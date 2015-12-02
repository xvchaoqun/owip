package service.party;

import domain.MemberStay;
import domain.MemberStayExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class MemberStayService extends BaseMapper {

    public boolean idDuplicate(Integer id, Integer userId){

        MemberStayExample example = new MemberStayExample();
        MemberStayExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return memberStayMapper.countByExample(example) > 0;
    }

    @Transactional
    public int insertSelective(MemberStay record){

        return memberStayMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        memberStayMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberStayExample example = new MemberStayExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberStayMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberStay record){
        return memberStayMapper.updateByPrimaryKeySelective(record);
    }
}
