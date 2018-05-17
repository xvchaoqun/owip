package domain.cet;

import org.springframework.format.annotation.DateTimeFormat;
import service.cet.CetCourseService;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class CetTrainCourse implements Serializable {

    public CetCourse getCetCourse(){

        if(courseId==null) return null;
        CetCourseService cetCourseService = CmTag.getBean(CetCourseService.class);
        return cetCourseService.get(courseId);
    }
    public CetTrainEvaTable getTrainEvaTable(){

        if(evaTableId==null) return null;
        return CmTag.getCetTrainEvaTable(evaTableId);
    }

    private Integer id;

    private Integer trainId;

    private Integer courseId;

    private String name;

    private String teacher;

    private Integer applyLimit;

    private Byte applyStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    private Boolean isGlobal;

    private Integer evaTableId;

    private String address;

    private Integer sortOrder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher == null ? null : teacher.trim();
    }

    public Integer getApplyLimit() {
        return applyLimit;
    }

    public void setApplyLimit(Integer applyLimit) {
        this.applyLimit = applyLimit;
    }

    public Byte getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Byte applyStatus) {
        this.applyStatus = applyStatus;
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

    public Boolean getIsGlobal() {
        return isGlobal;
    }

    public void setIsGlobal(Boolean isGlobal) {
        this.isGlobal = isGlobal;
    }

    public Integer getEvaTableId() {
        return evaTableId;
    }

    public void setEvaTableId(Integer evaTableId) {
        this.evaTableId = evaTableId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}