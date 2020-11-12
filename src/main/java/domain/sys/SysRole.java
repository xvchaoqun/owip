package domain.sys;

import java.io.Serializable;

public class SysRole implements Serializable {
    private Integer id;

    private String code;

    private String name;

    private Byte type;

    private String resourceIds;

    private String mResourceIds;

    private String resourceIdsMinus;

    private String mResourceIdsMinus;

    private Integer userCount;

    private Byte available;

    private Boolean isSysHold;

    private Integer sortOrder;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds == null ? null : resourceIds.trim();
    }

    public String getmResourceIds() {
        return mResourceIds;
    }

    public void setmResourceIds(String mResourceIds) {
        this.mResourceIds = mResourceIds == null ? null : mResourceIds.trim();
    }

    public String getResourceIdsMinus() {
        return resourceIdsMinus;
    }

    public void setResourceIdsMinus(String resourceIdsMinus) {
        this.resourceIdsMinus = resourceIdsMinus == null ? null : resourceIdsMinus.trim();
    }

    public String getmResourceIdsMinus() {
        return mResourceIdsMinus;
    }

    public void setmResourceIdsMinus(String mResourceIdsMinus) {
        this.mResourceIdsMinus = mResourceIdsMinus == null ? null : mResourceIdsMinus.trim();
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Byte getAvailable() {
        return available;
    }

    public void setAvailable(Byte available) {
        this.available = available;
    }

    public Boolean getIsSysHold() {
        return isSysHold;
    }

    public void setIsSysHold(Boolean isSysHold) {
        this.isSysHold = isSysHold;
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