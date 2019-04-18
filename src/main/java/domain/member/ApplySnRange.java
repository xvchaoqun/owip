package domain.member;

import java.io.Serializable;

public class ApplySnRange implements Serializable {
    private Integer id;

    private Integer year;

    private Long startSn;

    private Long endSn;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}