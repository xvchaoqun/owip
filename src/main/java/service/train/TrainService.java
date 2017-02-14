package service.train;

import domain.train.Train;
import domain.train.TrainExample;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.Date;

@Service
public class TrainService extends BaseMapper {

    @Transactional
    public void insertSelective(Train record){

        record.setTotalCount(0);
        record.setIsClosed(false);
        record.setCourseNum(0);
        record.setStatus(SystemConstants.AVAILABLE);
        trainMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        TrainExample example = new TrainExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        trainMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(Train record){

        return trainMapper.updateByPrimaryKeySelective(record);
    }

    // 修改评课关闭时间
    @Transactional
    public void updateEvaCloseTime(int id, boolean isClosed, Date closeTime) {

        Train trainCourse = trainMapper.selectByPrimaryKey(id);
        if(isClosed) {
            String sql = "update train set is_closed=1, close_time=null where id="+id;
            updateMapper.excuteSql(sql);
        }else{
            Train record = new Train();
            record.setId(id);
            record.setIsClosed(isClosed);
            record.setCloseTime(closeTime);
            trainMapper.updateByPrimaryKeySelective(record);
        }
    }

    // 1:已关闭评课 3：评课已结束
    public int evaIsClosed(int trainId){

        Train train = trainMapper.selectByPrimaryKey(trainId);
        if(BooleanUtils.isTrue(train.getIsClosed())){
            return 1;
        }

        Date now = new Date();
        Date closeTime = train.getCloseTime();

        if(closeTime!=null && now.after(closeTime)){
            return 3;
        }

        return 0;
    }
}
