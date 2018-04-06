package service.cet;

import domain.cet.CetTrainee;
import domain.cet.CetTraineeExample;
import domain.cet.CetTraineeView;
import domain.cet.CetTraineeViewExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class CetTraineeService extends BaseMapper {


    public CetTraineeView get(int userId, int trainId){

        CetTraineeViewExample example = new CetTraineeViewExample();
        example.createCriteria().andUserIdEqualTo(userId).andTrainIdEqualTo(trainId);
        List<CetTraineeView> cetTrainees = cetTraineeViewMapper.selectByExample(example);

        return cetTrainees.size()>0?cetTrainees.get(0):null;
    }

    @Transactional
    public void del(Integer id){

        cetTraineeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetTraineeExample example = new CetTraineeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetTraineeMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetTrainee record){

        return cetTraineeMapper.updateByPrimaryKeySelective(record);
    }
}
