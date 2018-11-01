package domain.sc.scDispatch;

import domain.dispatch.DispatchType;
import domain.sc.scCommittee.ScCommittee;
import persistence.sc.IScMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ScDispatchView implements Serializable {

    public String getDispatchCode(){
        return CmTag.getDispatchCode(code, dispatchTypeId, year);
    }

    public DispatchType getDispatchType(){
        return CmTag.getDispatchType(dispatchTypeId);
    }
    public List<ScCommittee> getScCommittees(){

        return CmTag.getBean(IScMapper.class).getScDispatchCommittees(id);
    }

    private Integer id;

    private Integer year;

    private Integer dispatchTypeId;

    private Integer code;

    private String title;

    private Date meetingTime;

    private Date pubTime;

    private String filePath;

    private String wordFilePath;

    private String signFilePath;

    private String remark;

    private Integer dispatchId;

    private Integer dispatchTypeSortOrder;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
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

    public String getWordFilePath() {
        return wordFilePath;
    }

    public void setWordFilePath(String wordFilePath) {
        this.wordFilePath = wordFilePath == null ? null : wordFilePath.trim();
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

    public Integer getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(Integer dispatchId) {
        this.dispatchId = dispatchId;
    }

    public Integer getDispatchTypeSortOrder() {
        return dispatchTypeSortOrder;
    }

    public void setDispatchTypeSortOrder(Integer dispatchTypeSortOrder) {
        this.dispatchTypeSortOrder = dispatchTypeSortOrder;
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