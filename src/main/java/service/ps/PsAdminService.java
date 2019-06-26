package service.ps;

import domain.ps.PsAdmin;
import domain.ps.PsAdminExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class PsAdminService extends PsBaseMapper {

    public boolean idDuplicate(Integer id, int userId){


        PsAdminExample example = new PsAdminExample();
        PsAdminExample.Criteria criteria = example.createCriteria()
                .andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return psAdminMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(PsAdmin record){

        psAdminMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        psAdminMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PsAdminExample example = new PsAdminExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        psAdminMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PsAdmin record){
        psAdminMapper.updateByPrimaryKeySelective(record);
    }
}
