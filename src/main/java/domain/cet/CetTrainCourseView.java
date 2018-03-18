package domain.cet;

import service.cet.CetCourseService;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class CetTrainCourseView implements Serializable {

    public CetCourse getCetCourse(){

        if(courseId==null) return null;
        CetCourseService cetCourseService = CmTag.getBean(CetCourseService.class);
        return cetCourseService.get(courseId);
    }

    private Integer id;

    private Integer trainId;

    private Integer courseId;

    private Date startTime;

    private Date endTime;

    private String address;

    private Integer sortOrder;

    private Integer selectedCount;

    private Integer finishCount;

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

    public Integer getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCount(Integer selectedCount) {
        this.selectedCount = selectedCount;
    }

    public Integer getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(Integer finishCount) {
        this.finishCount = finishCount;
    }
}