package domain.dr;

import java.io.Serializable;

public class DrOnlineResult implements Serializable {
    private Integer id;

    private Integer onlineId;

    private Integer postId;

    private Integer userId;

    private String candidate;

    private Integer inspectorId;

    private Integer inspectorTypeId;

    private Boolean isAgree;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Integer onlineId) {
        this.onlineId = onlineId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate == null ? null : candidate.trim();
    }

    public Integer getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(Integer inspectorId) {
        this.inspectorId = inspectorId;
    }

    public Integer getInspectorTypeId() {
        return inspectorTypeId;
    }

    public void setInspectorTypeId(Integer inspectorTypeId) {
        this.inspectorTypeId = inspectorTypeId;
    }

    public Boolean getIsAgree() {
        return isAgree;
    }

    public void setIsAgree(Boolean isAgree) {
        this.isAgree = isAgree;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}