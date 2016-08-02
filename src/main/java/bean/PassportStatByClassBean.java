package bean;

import domain.sys.MetaType;
import sys.tags.CmTag;

import java.util.Map;

/**
 * Created by fafa on 2016/3/14.
 */
public class PassportStatByClassBean {
    public MetaType getPassportClass(){

        Map<Integer, MetaType> passportClassMap = CmTag.getMetaTypes("mc_passport_type");
        return passportClassMap.get(classId);
    }
    private Integer classId;
    private Integer num;
    private Integer keepNum;
    private Integer lostNum;
    private Integer abolishNum;
    private Integer unconfirmNum;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getKeepNum() {
        return keepNum;
    }

    public void setKeepNum(Integer keepNum) {
        this.keepNum = keepNum;
    }

    public Integer getLostNum() {
        return lostNum;
    }

    public void setLostNum(Integer lostNum) {
        this.lostNum = lostNum;
    }

    public Integer getAbolishNum() {
        return abolishNum;
    }

    public void setAbolishNum(Integer abolishNum) {
        this.abolishNum = abolishNum;
    }

    public Integer getUnconfirmNum() {
        return unconfirmNum;
    }

    public void setUnconfirmNum(Integer unconfirmNum) {
        this.unconfirmNum = unconfirmNum;
    }
}
