package domain.dispatch;

import domain.sc.scDispatch.ScDispatchView;
import service.sc.scDispatch.ScDispatchService;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class DispatchView implements Serializable {
    public String getDispatchCode(){
        return CmTag.getDispatchCode(code, dispatchTypeId, year);
    }
    public DispatchType getDispatchType(){
        return CmTag.getDispatchType(dispatchTypeId);
    }
    public ScDispatchView getScDispatch(){

        if(scDispatchId==null) return null;

        ScDispatchService scDispatchService = CmTag.getBean(ScDispatchService.class);
        if(scDispatchService==null) return null;
        return scDispatchService.get(scDispatchId);
    }
    private Integer id;

    private Integer scDispatchId;

    private Integer year;

    private Integer dispatchTypeId;

    private Integer code;

    private String category;

    private Date meetingTime;

    private Date pubTime;

    private Date workTime;

    private Integer appointCount;

    private Integer realAppointCount;

    private Integer dismissCount;

    private Integer realDismissCount;

    private Boolean hasChecked;

    private String file;

    private String fileName;

    private String ppt;

    private String pptName;

    private String remark;

    private Integer sortOrder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScDispatchId() {
        return scDispatchId;
    }

    public void setScDispatchId(Integer scDispatchId) {
        this.scDispatchId = scDispatchId;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
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

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public Integer getAppointCount() {
        return appointCount;
    }

    public void setAppointCount(Integer appointCount) {
        this.appointCount = appointCount;
    }

    public Integer getRealAppointCount() {
        return realAppointCount;
    }

    public void setRealAppointCount(Integer realAppointCount) {
        this.realAppointCount = realAppointCount;
    }

    public Integer getDismissCount() {
        return dismissCount;
    }

    public void setDismissCount(Integer dismissCount) {
        this.dismissCount = dismissCount;
    }

    public Integer getRealDismissCount() {
        return realDismissCount;
    }

    public void setRealDismissCount(Integer realDismissCount) {
        this.realDismissCount = realDismissCount;
    }

    public Boolean getHasChecked() {
        return hasChecked;
    }

    public void setHasChecked(Boolean hasChecked) {
        this.hasChecked = hasChecked;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file == null ? null : file.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getPpt() {
        return ppt;
    }

    public void setPpt(String ppt) {
        this.ppt = ppt == null ? null : ppt.trim();
    }

    public String getPptName() {
        return pptName;
    }

    public void setPptName(String pptName) {
        this.pptName = pptName == null ? null : pptName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}