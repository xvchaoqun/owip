package domain.cet;

import org.springframework.format.annotation.DateTimeFormat;
import persistence.cet.CetExpertMapper;
import service.cet.CetCourseItemService;
import sys.constants.CetConstants;
import sys.tags.CmTag;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class CetCourse implements Serializable {

    public String getSn(){

        String prefix = "C";
        switch (type){
            case CetConstants.CET_COURSE_TYPE_SELF:
                prefix = "ZZ"; break;
            case CetConstants.CET_COURSE_TYPE_PRACTICE:
                prefix = "SJ"; break;
            case CetConstants.CET_COURSE_TYPE_SPECIAL:
                prefix = "ZT"; break;
        }

        return String.format("%s%4d%04d", prefix, year, num);
    }

    public CetExpert getCetExpert(){

        if(expertId==null) return null;
        CetExpertMapper cetExpertMapper = CmTag.getBean(CetExpertMapper.class);
        return cetExpertMapper.selectByPrimaryKey(expertId);
    }

    public BigDecimal getTotalPeriod(){

        if(type==CetConstants.CET_COURSE_TYPE_SPECIAL){
            BigDecimal totalPeriod = BigDecimal.valueOf(0);
            CetCourseItemService cetCourseItemService = CmTag.getBean(CetCourseItemService.class);
            Map<Integer, CetCourseItem> cetCourseItemMap = cetCourseItemService.findAll(id);
            for (CetCourseItem cetCourseItem : cetCourseItemMap.values()) {

                totalPeriod = totalPeriod.add(cetCourseItem.getPeriod());
            }

            return totalPeriod;
        }
        return null;
    }

    private Integer id;

    private Integer year;

    private Byte type;

    private Integer num;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date foundDate;

    private String name;

    private String address;

    private Boolean hasSummary;

    private Integer expertId;

    private Integer teachMethod;

    private BigDecimal period;

    private BigDecimal duration;

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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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