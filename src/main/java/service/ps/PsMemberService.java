package service.ps;

import domain.ps.PsMember;
import domain.ps.PsMemberExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class PsMemberService extends PsBaseMapper {

    public boolean idDuplicate(Integer id, int userId){

        PsMemberExample example = new PsMemberExample();
        PsMemberExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return psMemberMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(PsMember record){

        psMemberMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        psMemberMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PsMemberExample example = new PsMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        psMemberMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PsMember record){

        psMemberMapper.updateByPrimaryKeySelective(record);
    }
}
