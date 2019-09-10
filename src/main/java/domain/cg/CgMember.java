package domain.cg;

import domain.sys.SysUserView;
import domain.unit.UnitPost;
import persistence.cg.CgTeamMapper;
import persistence.unit.UnitPostMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class CgMember implements Serializable {

    public SysUserView getUser(){ return CmTag.getUserById(userId); }

    public UnitPost getUnitPost(){ return CmTag.getBean(UnitPostMapper.class).selectByPrimaryKey(unitPostId); }

    public CgTeam getCgTeam(){return CmTag.getBean(CgTeamMapper.class).selectByPrimaryKey(teamId);}

    private Integer id;

    private Integer teamId;

    private Integer post;

    private Byte type;

    private Integer unitPostId;

    private Integer userId;

    private String tag;

    private Date startDate;

    private Date endDate;

    private Boolean isCurrent;

    private Boolean needAdjust;

    private Integer sortOrder;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getPost() {
        return post;
    }

    public void setPost(Integer post) {
        this.post = post;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getUnitPostId() {
        return unitPostId;
    }

    public void setUnitPostId(Integer unitPostId) {
        this.unitPostId = unitPostId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
}