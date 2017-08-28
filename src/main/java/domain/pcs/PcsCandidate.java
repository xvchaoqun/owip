package domain.pcs;

import java.io.Serializable;
import java.util.Date;

public class PcsCandidate implements Serializable {
    private Integer id;

    private Integer recommendId;

    private Integer userId;

    private Byte type;

    private Integer sortOrder;

    private Boolean isFromStage;

    private Date addTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(Integer recommendId) {
        this.recommendId = recommendId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getIsFromStage() {
        return isFromStage;
    }

    public void setIsFromStage(Boolean isFromStage) {
        this.isFromStage = isFromStage;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}