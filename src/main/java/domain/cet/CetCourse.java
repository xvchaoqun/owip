package domain.cet;

import org.springframework.format.annotation.DateTimeFormat;
import persistence.cet.CetExpertMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CetCourse implements Serializable {

    public String getSn(){

        return String.format("C%04d", id);
    }

    public CetExpert getCetExpert(){

        if(expertId==null) return null;
        CetExpertMapper cetExpertMapper = CmTag.getBean(CetExpertMapper.class);
        return cetExpertMapper.selectByPrimaryKey(expertId);
    }

    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date foundDate;

    private String name;

    private Integer expertId;

    private BigDecimal period;

    private Integer courseTypeId;

    private Integer sortOrder;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getExpertId() {
        return expertId;
    }

    public void setExpertId(Integer expertId) {
        this.expertId = expertId;
    }

    public BigDecimal getPeriod() {
        return period;
    }

    public void setPeriod(BigDecimal period) {
        this.period = period;
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
}