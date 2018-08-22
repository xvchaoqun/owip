package service.cet;

import domain.cet.CetUpperTrainAdmin;
import domain.cet.CetUpperTrainAdminExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CetUpperTrainAdminService extends BaseMapper {

    public Set<Integer> adminUnitIdSet(int userId){

        CetUpperTrainAdminExample example = new CetUpperTrainAdminExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<CetUpperTrainAdmin> cetUpperTrainAdmins = cetUpperTrainAdminMapper.selectByExample(example);
        Set<Integer> adminUnitIds = new HashSet<>();
        for (CetUpperTrainAdmin cetUpperTrainAdmin : cetUpperTrainAdmins) {
            adminUnitIds.add(cetUpperTrainAdmin.getUnitId());
        }

        return adminUnitIds;
    }

    @Transactional
    public void insertSelective(CetUpperTrainAdmin record){

        cetUpperTrainAdminMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetUpperTrainAdminMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetUpperTrainAdminExample example = new CetUpperTrainAdminExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetUpperTrainAdminMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetUpperTrainAdmin record){
        return cetUpperTrainAdminMapper.updateByPrimaryKeySelective(record);
    }
}
