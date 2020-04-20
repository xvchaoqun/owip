package domain.cg;

import domain.sys.SysUserView;
import domain.unit.Unit;
import persistence.cg.common.ICgMapper;
import sys.tags.CmTag;

import java.io.Serializable;

public class CgTeamView implements Serializable {

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }

    public Unit getUnit(){
        return CmTag.getUnitById(unitId);
    }

    public Integer getCountNeedAdjust(){return CmTag.getBean(ICgMapper.class).getCountNeedAdjust(id);}

    private Integer id;

    private Integer fid;

    private String name;

    private Byte type;

    private Integer category;

    private Boolean isCurrent;

    private Boolean needAdjust;

    private Integer sortOrder;

    private String remark;

    private Integer unitId;

    private Integer userId;

    private String phone;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
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

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public Boolean getNeedAdjust() {
        return needAdjust;
    }

    public void setNeedAdjust(Boolean needAdjust) {
        this.needAdjust = needAdjust;
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

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
}