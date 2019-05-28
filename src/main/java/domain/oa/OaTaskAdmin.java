package domain.oa;

import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class OaTaskAdmin implements Serializable {

    public SysUserView getUser(){

        return CmTag.getUserById(userId);
    }

    private Integer userId;

    private String types;

    private Boolean showAll;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types == null ? null : types.trim();
    }

    public Boolean getShowAll() {
        return showAll;
    }

    public void setShowAll(Boolean showAll) {
        this.showAll = showAll;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}