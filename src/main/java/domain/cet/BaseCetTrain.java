package domain.cet;

import domain.base.MetaType;
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

        return String.format("%s[%s]%s号",
                metaType.getName(),
                year, num);
    }

    public static Byte getSwitchStatus(Byte enrollStatus, Date startTime, Date endTime) {

        // 手动开关判断
        if (enrollStatus != CetConstants.CET_TRAIN_ENROLL_STATUS_DEFAULT) {
            return enrollStatus;
        }

        // 自动开关判断
        Date now = new Date();
        if (startTime != null && endTime != null) {

            if (now.after(startTime) && now.before(endTime)) {
                return CetConstants.CET_TRAIN_ENROLL_STATUS_OPEN;
            }
        } else if (startTime != null) {

            if (now.after(startTime)) {
                return CetConstants.CET_TRAIN_ENROLL_STATUS_OPEN;
            }
        } else if (endTime != null) {

            if (now.before(endTime)) {
                return CetConstants.CET_TRAIN_ENROLL_STATUS_OPEN;
            }
        }

        return CetConstants.CET_TRAIN_ENROLL_STATUS_CLOSED;
    }

    public static String getSwitchStatusText(boolean autoSwitch, Byte enrollStatus, Date startTime, Date endTime){

        Date now = new Date();
        if(autoSwitch){
            if(startTime==null && endTime==null){
                return "未启动选课";
            }else{
                if(startTime!=null && startTime.after(now)){
                    return "未启动选课";
                }else if(endTime!=null && endTime.after(now)){
                    return "正在选课";
                }
                return "选课结束";
            }
        }

        return CetConstants.CET_TRAIN_ENROLL_STATUS_MAP.get(enrollStatus);
    }

}
