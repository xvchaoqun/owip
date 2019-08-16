package domain.pm;

import persistence.pm.IPmMapper;
import persistence.pm.PmQuarterCountBean;
import sys.tags.CmTag;

import java.io.Serializable;

public class PmQuarter implements Serializable {
    public PmQuarterCountBean getF(){

      //  if(isFinish) return null;

        IPmMapper iPmMapper = CmTag.getBean(IPmMapper.class);
        return iPmMapper.getPmQuarterCount(id);
    }
    private Integer id;

    private Integer year;

    private Byte quarter;

    private Boolean isFinish;

    private Byte type;

    private Integer partyId;

    private Integer num;

    private Integer dueNum;

    private Integer finishNum;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Byte getQuarter() {
        return quarter;
    }

    public void setQuarter(Byte quarter) {
        this.quarter = quarter;
    }

    public Boolean getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Boolean isFinish) {
        this.isFinish = isFinish;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getDueNum() {
        return dueNum;
    }

    public void setDueNum(Integer dueNum) {
        this.dueNum = dueNum;
    }

    public Integer getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(Integer finishNum) {
        this.finishNum = finishNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}