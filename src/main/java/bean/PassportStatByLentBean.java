package bean;

import domain.sys.MetaType;
import sys.tags.CmTag;

import java.util.Map;

/**
 * Created by fafa on 2016/3/14.
 */
public class PassportStatByLentBean {
    public MetaType getPassportClass(){

        Map<Integer, MetaType> passportClassMap = CmTag.getMetaTypes("mc_passport_type");
        return passportClassMap.get(classId);
    }
    private Integer classId;
    private Integer lentNum;
    private Integer num;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getLentNum() {
        return lentNum;
    }

    public void setLentNum(Integer lentNum) {
        this.lentNum = lentNum;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
