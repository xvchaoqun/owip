package domain.cet;

import org.springframework.format.annotation.DateTimeFormat;
import persistence.cet.common.ICetMapper;
import service.cet.CetProjectObjService;
import sys.tags.CmTag;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CetProjectPlan implements Serializable {

    public BigDecimal getFinishPeriod(){

        HttpServletRequest request = ContextHelper.getRequest();
        if (request == null) return null;

        Integer objId = (Integer) request.getAttribute("objId");
        if(objId==null) return null;

        CetProjectObjService cetProjectObjService = CmTag.getBean(CetProjectObjService.class);
        return cetProjectObjService.getPlanFinishPeriod(id, objId);
    }


    public List<Integer> getObjTrainIds(){

        HttpServletRequest request = ContextHelper.getRequest();
        if (request == null) return null;

        Integer objId = (Integer) request.getAttribute("objId");
        if(objId==null) return null;

        ICetMapper iCetMapper = CmTag.getBean(ICetMapper.class);
        return iCetMapper.selectObjTrainIds(objId, id);
    }

    private Integer id;

    private Integer projectId;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date startDate;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date endDate;

    private Byte type;

    private Boolean hasSummary;

    private String summary;

    private BigDecimal period;

    private Integer sortOrder;

    private String remark;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Boolean getHasSummary() {
        return hasSummary;
    }

    public void setHasSummary(Boolean hasSummary) {
        this.hasSummary = hasSummary;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public BigDecimal getPeriod() {
        return period;
    }

    public void setPeriod(BigDecimal period) {
        this.period = period;
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