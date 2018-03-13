package service.cet;

import domain.cet.CetTrainee;
import domain.cet.CetTraineeExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CetTraineeService extends BaseMapper {

    @Transactional
    public void insertSelective(CetTrainee record){

        cetTraineeMapper.insertSelective(record);
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
