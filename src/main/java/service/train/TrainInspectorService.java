package service.train;

import domain.train.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.DBErrorException;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class TrainInspectorService extends BaseMapper {

    @Autowired
    private TrainService trainService;

    @Transactional
    public void delAbolished(Integer id) {

        {
            TrainInspectorCourseExample example = new TrainInspectorCourseExample();
            example.createCriteria().andInspectorIdEqualTo(id);
            trainInspectorCourseMapper.deleteByExample(example);
        }

        TrainInspectorExample example = new TrainInspectorExample();
        example.createCriteria().andIdEqualTo(id)
                .andStatusEqualTo(SystemConstants.TRAIN_INSPECTOR_STATUS_ABOLISH);

        trainInspectorMapper.deleteByPrimaryKey(id);

    }

/*    @Transactional
    public int delAllAbolished(int trainId) {

        TrainInspectorExample example = new TrainInspectorExample();
        example.createCriteria().andTrainIdEqualTo(trainId)
                .andStatusEqualTo(SystemConstants.TRAIN_INSPECTOR_STATUS_ABOLISH);
        return trainInspectorMapper.deleteByExample(example);
    }*/

    /**
     * @param trainId
     * @param type       1列表生成 2个别生成
     * @param totalCount
     */
    @Transactional
    public void generateInspector(int trainId, byte type, int totalCount) {

        Train train = trainMapper.selectByPrimaryKey(trainId);
        int closed = trainService.evaIsClosed(trainId);
        if (closed == 1) {
            throw new RuntimeException("评课已关闭。");
        } else if (closed == 3) {
            throw new RuntimeException("评课已结束于" + DateUtils.formatDate(train.getCloseTime(), DateUtils.YYYY_MM_DD_HH_MM));
        }

        int newCount = totalCount;
        Date now = new Date();


        if (type == 1) { // 列表生成
            newCount = totalCount - train.getTotalCount() > 0 ? totalCount - train.getTotalCount() : 0;
        }

        if (newCount > 0) {
            for (int i = 0; i < newCount; i++) {

                TrainInspector record = new TrainInspector();
                record.setTrainId(trainId);
                record.setUsername(buildUsername());
                record.setPasswd(RandomStringUtils.randomNumeric(6));
                record.setType(type);
                record.setStatus(SystemConstants.TRAIN_INSPECTOR_STATUS_INIT);
                record.setCreateTime(now);

                trainInspectorMapper.insert(record);
            }

            TrainInspectorExample _example = new TrainInspectorExample();
            _example.createCriteria().andTrainIdEqualTo(trainId).andStatusNotEqualTo(SystemConstants.TRAIN_INSPECTOR_STATUS_ABOLISH);
            int count = trainInspectorMapper.countByExample(_example);

            Train record = new Train();
            record.setId(trainId);
            record.setTotalCount(count);
            trainMapper.updateByPrimaryKeySelective(record);
        }
    }

    public String buildUsername() {

        //String username = RandomStringUtils.randomAlphabetic(6).toLowerCase() + RandomStringUtils.randomNumeric(2);
        String username = RandomStringUtils.random(6, "abcdefghijkmnpqrstuvwxy")
                + RandomStringUtils.random(2, "23456789");
        TrainInspectorExample example = new TrainInspectorExample();
        example.createCriteria().andUsernameEqualTo(username);
        while (trainInspectorMapper.countByExample(example) > 0) {

            //username = RandomStringUtils.randomAlphabetic(6).toLowerCase() + RandomStringUtils.randomNumeric(2);
            username = RandomStringUtils.random(6, "abcdefghijkmnpqrstuvwxy")
                    + RandomStringUtils.random(2, "23456789");
        }

        return username;
    }

    @Transactional
    public void abolish(int inspectorId) {

        TrainInspector inspector = trainInspectorMapper.selectByPrimaryKey(inspectorId);

        TrainInspector _inspector = new TrainInspector();
        _inspector.setId(inspectorId);
        _inspector.setStatus(SystemConstants.TRAIN_INSPECTOR_STATUS_ABOLISH);

        if (trainInspectorMapper.updateByPrimaryKeySelective(_inspector) != 1)
            throw new DBErrorException("abolish error1.");

        Integer trainId = inspector.getTrainId();
        Train train = trainMapper.selectByPrimaryKey(trainId);
        Train _train = new Train();
        _train.setId(trainId);
        _train.setTotalCount(Math.max(0, train.getTotalCount() - 1));
        if (trainMapper.updateByPrimaryKeySelective(_train) != 1)
            throw new DBErrorException("abolish error2.");

        // 已完成的测评人，所有关联课程的总完成数量要减一
        updateMapper.abolishTrainInspector(inspectorId);

        {
            TrainEvaResultExample example = new TrainEvaResultExample();
            example.createCriteria().andInspectorIdEqualTo(inspectorId);
            trainEvaResultMapper.deleteByExample(example);
        }
    }

    @Transactional
    public void changepasswd(int inspectorId, String passwd) {

        TrainInspector record = new TrainInspector();
        record.setId(inspectorId);
        record.setPasswd(passwd);
        record.setPasswdChangeType(SystemConstants.TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF);

        trainInspectorMapper.updateByPrimaryKeySelective(record);
    }

    public TrainInspector tryLogin(String username, String password) {

        username = StringUtils.trimToNull(username);
        password = StringUtils.trimToNull(password);
        if(username==null || password==null) return null;

        TrainInspectorExample example = new TrainInspectorExample();
        example.createCriteria().andUsernameEqualTo(username)
                .andPasswdEqualTo(password);
        List<TrainInspector> trainInspectors = trainInspectorMapper.selectByExample(example);
        if(trainInspectors.size()==1){
            TrainInspector trainInspector = trainInspectors.get(0);
            if(trainInspector.getStatus()==SystemConstants.TRAIN_INSPECTOR_STATUS_ABOLISH){
                throw new TrainInspectorAbolishException("该账号已经作废");
            }
            if(trainService.evaIsClosed(trainInspector.getTrainId())!=0){
                throw new TrainInspectorAbolishException("测评已经结束");
            }

            return trainInspector;
        }

        return null;
    }
}
