package domain.qy;

import persistence.qy.QyRewardMapper;
import persistence.qy.QyYearMapper;
import sys.tags.CmTag;

import java.io.Serializable;

public class QyYearReward implements Serializable {

    public QyYear getQyYear(){
        if(yearId==null) return null;
        QyYearMapper qyYearMapper = CmTag.getBean(QyYearMapper.class);
        return qyYearMapper.selectByPrimaryKey(yearId);
    }
    public QyReward getQyReward(){
        if(rewardId==null) return null;
        QyRewardMapper qyRewardMapper = CmTag.getBean(QyRewardMapper.class);
        return qyRewardMapper.selectByPrimaryKey(rewardId);
    }

    private Integer id;

    private Integer yearId;

    private Integer rewardId;

    private Integer sortOrder;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYearId() {
        return yearId;
    }

    public void setYearId(Integer yearId) {
        this.yearId = yearId;
    }

    public Integer getRewardId() {
        return rewardId;
    }

    public void setRewardId(Integer rewardId) {
        this.rewardId = rewardId;
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