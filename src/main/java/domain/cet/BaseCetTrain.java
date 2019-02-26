package domain.cet;

import domain.base.MetaType;
import persistence.cet.common.ICetMapper;
import sys.constants.CetConstants;
import sys.tags.CmTag;

import java.util.Date;

/**
 * Created by lm on 2018/3/17.
 */
public class BaseCetTrain {

    public static String getSn(Integer type, Integer year, Integer num){

        if(type==null) return null;
        MetaType metaType = CmTag.getMetaType(type);
        if(metaType==null) return null;

        return String.format("%s〔%s〕%s号",
                metaType.getName(),
                year, num);
    }

    public static Byte getSwitchStatus(int trainId, Byte enrollStatus, Date startTime, Date endTime) {

        ICetMapper iCetMapper = CmTag.getBean(ICetMapper.class);
        CetProject cetProject = iCetMapper.getCetProject(trainId);
        if(cetProject!=null && cetProject.getStatus()!=CetConstants.CET_PROJECT_STATUS_START){
            return CetConstants.CET_TRAIN_ENROLL_STATUS_NOT_BEGIN;
        }

        // 手动开关判断
        if (enrollStatus != CetConstants.CET_TRAIN_ENROLL_STATUS_DEFAULT) {
            return enrollStatus;
        }

        // 自动开关判断
        Date now = new Date();
        if (startTime != null && endTime != null) {

            if(startTime.after(now)){

                return CetConstants.CET_TRAIN_ENROLL_STATUS_NOT_BEGIN;
            }else if (now.after(startTime) && now.before(endTime)) {

                return CetConstants.CET_TRAIN_ENROLL_STATUS_OPEN;
            }
        } else if (startTime != null) {

            if(startTime.after(now)){
                return CetConstants.CET_TRAIN_ENROLL_STATUS_NOT_BEGIN;
            }else {
                return CetConstants.CET_TRAIN_ENROLL_STATUS_OPEN;
            }
        } else if (endTime != null) {

            if (now.before(endTime)) {
                return CetConstants.CET_TRAIN_ENROLL_STATUS_OPEN;
            }
        }else{
            return CetConstants.CET_TRAIN_ENROLL_STATUS_NOT_BEGIN;
        }

        return CetConstants.CET_TRAIN_ENROLL_STATUS_CLOSED;
    }

}
