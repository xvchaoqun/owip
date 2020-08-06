package domain.cet;

import org.springframework.format.annotation.DateTimeFormat;
import service.cet.CetPlanCourseObjService;
import service.cet.CetPlanCourseService;
import sys.tags.AuthTag;
import sys.tags.CmTag;
import sys.utils.ContextHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CetPlanCourse implements Serializable {

    public String getSign(){
        return AuthTag.sign(filePath);
    }

    // 选课学员数量
    public Long getSelectedCount(){

        CetPlanCourseService cetPlanCourseService = CmTag.getBean(CetPlanCourseService.class);
        return cetPlanCourseService.getSelectedCount(id);
    }

    public Map getObjInfo() {

        HttpServletRequest request = ContextHelper.getRequest();
        if (request == null) return null;

        Map<String, Object> resultMap = new HashMap<>();

        Integer userId = (Integer) request.getAttribute("userId");
        if (userId == null ) return null;

        // 培训方案选课页面(自主学习和上级专题班)
        CetPlanCourseObjService cetPlanCourseObjService = CmTag.getBean(CetPlanCourseObjService.class);
        CetPlanCourseObj cpo = cetPlanCourseObjService.getByUserId(userId, id);
        if(cpo!=null) {
            resultMap.put("planCourseObjId", cpo.getId());
            resultMap.put("note", cpo.getNote());
            resultMap.put("isFinished", cpo.getIsFinished());
        }
        return resultMap;
    }

    private Integer id;

    private Integer planId;

    private Integer courseId;

    private String name;

    private String unit;

    private BigDecimal period;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getPeriod() {
        return period;
    }

    public void setPeriod(BigDecimal period) {
        this.period = period;
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