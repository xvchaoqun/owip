package domain.cet;

import org.springframework.format.annotation.DateTimeFormat;
import persistence.cet.CetExpertMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CetCourse implements Serializable {

    public String getSn(){

        return String.format("C%4d%1d%03d", year, isOnline?2:1, num);
    }

    public CetExpert getCetExpert(){

        if(expertId==null) return null;
        CetExpertMapper cetExpertMapper = CmTag.getBean(CetExpertMapper.class);
        return cetExpertMapper.selectByPrimaryKey(expertId);
    }

    private Integer id;

    private Integer year;

    private Boolean isOnline;

    private Integer num;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date foundDate;

    private String name;

    private Boolean hasSummary;

    private Integer expertId;

    private Integer teachMethod;

    private BigDecimal period;

    private BigDecimal duration;

    private Integer courseTypeId;

    private Integer sortOrder;

    private String remark;

    private Boolean isDeleted;

    private String summary;

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

    public Boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getHasSummary() {
        return hasSummary;
    }

    public void setHasSummary(Boolean hasSummary) {
        this.hasSummary = hasSummary;
    }

    public Integer getExpertId() {
        return expertId;
    }

    public void setExpertId(Integer expertId) {
        this.expertId = expertId;
    }

    public Integer getTeachMethod() {
        return teachMethod;
    }

    public void setTeachMethod(Integer teachMethod) {
        this.teachMethod = teachMethod;
    }

    public BigDecimal getPeriod() {
        return period;
    }

    public void setPeriod(BigDecimal period) {
        this.period = period;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public Integer getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(Integer courseTypeId) {
        this.courseTypeId = courseTypeId;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }
}