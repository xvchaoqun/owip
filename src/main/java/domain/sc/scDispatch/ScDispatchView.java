package domain.sc.scDispatch;

import domain.dispatch.DispatchType;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class ScDispatchView implements Serializable {

    public DispatchType getDispatchType(){
        return CmTag.getDispatchType(dispatchTypeId);
    }

    private Integer id;

    private Integer year;

    private Integer dispatchTypeId;

    private Integer code;

    private Date meetingTime;

    private Date pubTime;

    private String filePath;

    private String signFilePath;

    private String remark;

    private Integer appointCount;

    private Integer dismissCount;

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

    public Integer getDispatchTypeId() {
        return dispatchTypeId;
    }

    public void setDispatchTypeId(Integer dispatchTypeId) {
        this.dispatchTypeId = dispatchTypeId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Date getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(Date meetingTime) {
        this.meetingTime = meetingTime;
    }

    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getSignFilePath() {
        return signFilePath;
    }

    public void setSignFilePath(String signFilePath) {
        this.signFilePath = signFilePath == null ? null : signFilePath.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getAppointCount() {
        return appointCount;
    }

    public void setAppointCount(Integer appointCount) {
        this.appointCount = appointCount;
    }

    public Integer getDismissCount() {
        return dismissCount;
    }

    public void setDismissCount(Integer dismissCount) {
        this.dismissCount = dismissCount;
    }
}