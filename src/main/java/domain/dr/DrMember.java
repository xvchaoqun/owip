package domain.dr;

import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;

public class DrMember implements Serializable {
    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    private Integer id;

    private Integer userId;

    private Integer sortOrder;

    private Byte status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}