package domain.cet;

import org.springframework.format.annotation.DateTimeFormat;
import service.cet.CetTrainObjService;
import sys.helper.CetHelper;
import sys.tags.CmTag;
import sys.utils.ContextHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CetTrainCourse implements Serializable {

    public CetTrainEvaTable getTrainEvaTable(){

        if(evaTableId==null) return null;
        return CetHelper.getCetTrainEvaTable(evaTableId);
    }

    public CetTrainObjView getTrainObj(){

        HttpServletRequest request = ContextHelper.getRequest();
        if(request==null) return null;
        Integer userId = (Integer) request.getAttribute("userId");
        if(userId==null) return null;

        CetTrainObjService cetTrainObjService = CmTag.getBean(CetTrainObjService.class);
        return cetTrainObjService.get(userId, id);
    }

    private Integer id;

    private Integer projectId;

    private Integer trainId;

    private Boolean isOnline;

    private Integer courseId;

    private BigDecimal period;

    private String name;

    private String teacher;

    private Integer applyLimit;

    private Byte applyStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    private Integer selectedCount;

    private Integer finishCount;

    private Integer evaFinishCount;

    private Boolean isGlobal;

    private Integer evaTableId;

    private String address;

    private String signToken;

    private Long signTokenExpire;

    private Integer sortOrder;

    private String summary;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public Boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public BigDecimal getPeriod() {
        return period;
    }

    public void setPeriod(BigDecimal period) {
        this.period = period;
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

    public Integer getEvaFinishCount() {
        return evaFinishCount;
    }

    public void setEvaFinishCount(Integer evaFinishCount) {
        this.evaFinishCount = evaFinishCount;
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

    public String getSignToken() {
        return signToken;
    }

    public void setSignToken(String signToken) {
        this.signToken = signToken == null ? null : signToken.trim();
    }

    public Long getSignTokenExpire() {
        return signTokenExpire;
    }

    public void setSignTokenExpire(Long signTokenExpire) {
        this.signTokenExpire = signTokenExpire;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }
}