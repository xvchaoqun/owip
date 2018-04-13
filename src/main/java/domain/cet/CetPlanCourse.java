package domain.cet;

import org.springframework.format.annotation.DateTimeFormat;
import service.cet.CetCourseService;
import service.cet.CetPlanCourseService;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class CetPlanCourse implements Serializable {

    public CetCourse getCetCourse(){

        if(courseId==null) return null;
        CetCourseService cetCourseService = CmTag.getBean(CetCourseService.class);
        return cetCourseService.get(courseId);
    }

    public Long getSelectedCount(){

        CetPlanCourseService cetPlanCourseService = CmTag.getBean(CetPlanCourseService.class);
        return cetPlanCourseService.getSelectedCount(id);
    }

    private Integer id;

    private Integer planId;

    private Integer courseId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    private Boolean needNote;

    private String fileName;

    private String filePath;

    private Integer sortOrder;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getNeedNote() {
        return needNote;
    }

    public void setNeedNote(Boolean needNote) {
        this.needNote = needNote;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}