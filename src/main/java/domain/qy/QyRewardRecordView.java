package domain.qy;

import java.io.Serializable;

public class QyRewardRecordView implements Serializable {
    private Integer id;

    private Integer yearRewardId;

    private String remark;

    private Integer rewardId;

    private Integer rewardSortOrder;

    private Integer year;

    private String rewardName;

    private Byte rewardType;

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

    public Integer getRewardId() {
        return rewardId;
    }

    public void setRewardId(Integer rewardId) {
        this.rewardId = rewardId;
    }

    public Integer getRewardSortOrder() {
        return rewardSortOrder;
    }

    public void setRewardSortOrder(Integer rewardSortOrder) {
        this.rewardSortOrder = rewardSortOrder;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName == null ? null : rewardName.trim();
    }

    public Byte getRewardType() {
        return rewardType;
    }

    public void setRewardType(Byte rewardType) {
        this.rewardType = rewardType;
    }
}