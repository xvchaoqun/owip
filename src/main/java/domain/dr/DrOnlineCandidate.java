package domain.dr;

import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;

public class DrOnlineCandidate implements Serializable {

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }

    private Integer id;

    private Integer postId;

    private Integer userId;

    private String candidate;

    private Integer sortOrder;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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