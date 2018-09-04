package domain.sc.scDispatch;

import org.springframework.format.annotation.DateTimeFormat;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class ScDispatch implements Serializable {

    private Integer id;

    private Integer year;

    private Integer dispatchTypeId;

    private Integer code;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date meetingTime;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date pubTime;

    private String filePath;

    private String signFilePath;

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
}