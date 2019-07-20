package domain.cadre;

import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;

public class Cadre implements Serializable {
    public SysUserView getUser(){

        return CmTag.getUserById(userId);
    }
    private Integer id;

    private Integer userId;

    private Byte type;

    private Integer state;

    private String title;

    private Integer dispatchCadreId;

    private String remark;

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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getDispatchCadreId() {
        return dispatchCadreId;
    }

    public void setDispatchCadreId(Integer dispatchCadreId) {
        this.dispatchCadreId = dispatchCadreId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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