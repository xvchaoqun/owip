package domain.sc.scSubsidy;

import domain.dispatch.Dispatch;
import persistence.dispatch.DispatchMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class ScSubsidyDispatchView implements Serializable {

    public Dispatch getDispatch(){

        return CmTag.getBean(DispatchMapper.class).selectByPrimaryKey(dispatchId);
    }

    public String getHrCode(){
        if(hrType==null || hrNum==null) return null;
        return ScSubsidy.getHrCode(hrType, year, hrNum);
    }

    public String getFeCode(){
        if(feType==null || feNum==null) return null;
        return ScSubsidy.getFeCode(feType, year, feNum);
    }

    private Integer id;

    private Integer subsidyId;

    private Integer dispatchId;

    private String remark;

    private Date infoDate;

    private Short year;

    private Integer hrType;

    private Integer hrNum;

    private String hrFilePath;

    private Integer feType;

    private Integer feNum;

    private String feFilePath;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubsidyId() {
        return subsidyId;
    }

    public void setSubsidyId(Integer subsidyId) {
        this.subsidyId = subsidyId;
    }

    public Integer getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(Integer dispatchId) {
        this.dispatchId = dispatchId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getInfoDate() {
        return infoDate;
    }

    public void setInfoDate(Date infoDate) {
        this.infoDate = infoDate;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public Integer getHrType() {
        return hrType;
    }

    public void setHrType(Integer hrType) {
        this.hrType = hrType;
    }

    public Integer getHrNum() {
        return hrNum;
    }

    public void setHrNum(Integer hrNum) {
        this.hrNum = hrNum;
    }

    public String getHrFilePath() {
        return hrFilePath;
    }

    public void setHrFilePath(String hrFilePath) {
        this.hrFilePath = hrFilePath == null ? null : hrFilePath.trim();
    }

    public Integer getFeType() {
        return feType;
    }

    public void setFeType(Integer feType) {
        this.feType = feType;
    }

    public Integer getFeNum() {
        return feNum;
    }

    public void setFeNum(Integer feNum) {
        this.feNum = feNum;
    }

    public String getFeFilePath() {
        return feFilePath;
    }

    public void setFeFilePath(String feFilePath) {
        this.feFilePath = feFilePath == null ? null : feFilePath.trim();
    }
}