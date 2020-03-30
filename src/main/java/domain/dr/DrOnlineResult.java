package domain.dr;

import java.io.Serializable;

public class DrOnlineResult implements Serializable {
    private Integer id;

    private Integer onlineId;

    private Integer postId;

    private Integer candidateId;

    private Integer inspectorId;

    private Integer inspectorTypeId;

    private Boolean insOption;

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

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
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

    public Boolean getInsOption() {
        return insOption;
    }

    public void setInsOption(Boolean insOption) {
        this.insOption = insOption;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}