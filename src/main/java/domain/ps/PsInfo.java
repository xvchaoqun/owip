package domain.ps;

import org.springframework.format.annotation.DateTimeFormat;
import service.ps.PsInfoService;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class PsInfo implements Serializable {

    public Long getCountNumber(){
        PsInfoService psInfoService = CmTag.getBean(PsInfoService.class);
        return psInfoService.getAllCountNumberById(id);
    }

    private Integer id;

    private String seq;

    private String name;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date foundDate;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date abolishDate;

    private Integer sortOrder;

    private Boolean isHistory;

    private Boolean isDeleted;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq == null ? null : seq.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
    }

    public Date getAbolishDate() {
        return abolishDate;
    }

    public void setAbolishDate(Date abolishDate) {
        this.abolishDate = abolishDate;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(Boolean isHistory) {
        this.isHistory = isHistory;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}