package domain.member;

import java.io.Serializable;

public class ApplySnRange implements Serializable {
    private Integer id;

    private Integer year;

    private String prefix;

    private Long startSn;

    private Long endSn;

    private Integer len;

    private Integer useCount;

    private Integer abolishCount;

    private Integer sortOrder;

    private String remark;

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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix == null ? null : prefix.trim();
    }

    public Long getStartSn() {
        return startSn;
    }

    public void setStartSn(Long startSn) {
        this.startSn = startSn;
    }

    public Long getEndSn() {
        return endSn;
    }

    public void setEndSn(Long endSn) {
        this.endSn = endSn;
    }

    public Integer getLen() {
        return len;
    }

    public void setLen(Integer len) {
        this.len = len;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    public Integer getAbolishCount() {
        return abolishCount;
    }

    public void setAbolishCount(Integer abolishCount) {
        this.abolishCount = abolishCount;
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