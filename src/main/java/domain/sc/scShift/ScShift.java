package domain.sc.scShift;

import domain.sys.SysUserView;
import domain.unit.UnitPost;
import persistence.unit.UnitPostMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class ScShift implements Serializable {

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }

    public UnitPost getUnitPost(){
        return CmTag.getBean(UnitPostMapper.class).selectByPrimaryKey(postId);
    }
    public UnitPost getAssignPost(){
        return CmTag.getBean(UnitPostMapper.class).selectByPrimaryKey(assignPostId);
    }

    private Integer id;

    private Integer userId;

    private Integer postId;

    private Integer assignPostId;

    private String unitName;

    private Integer unitTypeId;

    private Boolean isPrincipal;

    private Byte leaderType;

    private Integer adminLevel;

    private Integer postType;

    private Integer type;

    private Integer recordId;

    private Integer recordUserId;

    private Date createTime;

    private String remark;

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

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getAssignPostId() {
        return assignPostId;
    }

    public void setAssignPostId(Integer assignPostId) {
        this.assignPostId = assignPostId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public Integer getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Integer unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public Boolean getIsPrincipal() {
        return isPrincipal;
    }

    public void setIsPrincipal(Boolean isPrincipal) {
        this.isPrincipal = isPrincipal;
    }

    public Byte getLeaderType() {
        return leaderType;
    }

    public void setLeaderType(Byte leaderType) {
        this.leaderType = leaderType;
    }

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }

    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getRecordUserId() {
        return recordUserId;
    }

    public void setRecordUserId(Integer recordUserId) {
        this.recordUserId = recordUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}