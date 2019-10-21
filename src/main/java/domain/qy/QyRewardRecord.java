package domain.qy;

import java.io.Serializable;

public class QyRewardRecord implements Serializable {
    private Integer id;

    private Integer yearRewardId;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYearRewardId() {
        return yearRewardId;
    }

    public void setYearRewardId(Integer yearRewardId) {
        this.yearRewardId = yearRewardId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}