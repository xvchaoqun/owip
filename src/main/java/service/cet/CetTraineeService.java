package service.cet;

import controller.global.OpException;
import domain.cet.CetProjectObj;
import domain.cet.CetTrainee;
import domain.cet.CetTraineeExample;
import domain.cet.CetTraineeView;
import domain.cet.CetTraineeViewExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class CetTraineeService extends BaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());
    public CetTraineeView get(int userId, int trainId){

        CetTraineeViewExample example = new CetTraineeViewExample();
        example.createCriteria().andUserIdEqualTo(userId).andTrainIdEqualTo(trainId);
        List<CetTraineeView> cetTrainees = cetTraineeViewMapper.selectByExample(example);

        return cetTrainees.size()>0?cetTrainees.get(0):null;
    }

    public CetTraineeView createIfNotExist(int userId, int trainId){

        CetTraineeView cetTrainee = get(userId, trainId);
        if(cetTrainee!=null) {
            return cetTrainee;
        }

        CetProjectObj cetProjectObj = iCetMapper.getCetProjectObj(userId, trainId);
        if(cetProjectObj==null){
            logger.error("培训学员不存在, userId={}, trainId={}", userId, trainId);
            throw new OpException("培训学员不存在");
        }
        int objId = cetProjectObj.getId();

        CetTrainee record = new CetTrainee();
        record.setObjId(objId);
        record.setIsQuit(false);
        record.setTrainId(trainId);
        cetTraineeMapper.insertSelective(record);

        return get(userId, trainId);
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
