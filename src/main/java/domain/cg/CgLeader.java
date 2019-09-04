package domain.cg;

import domain.sys.SysUserView;
import domain.unit.UnitPost;
import org.springframework.format.annotation.DateTimeFormat;
import persistence.unit.UnitPostMapper;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class CgLeader implements Serializable {

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }

    public UnitPost getUnitPost(){
        return CmTag.getBean(UnitPostMapper.class).selectByPrimaryKey(unitPostId);
    }
    private Integer id;

    private Integer teamId;

    private Boolean isPost;

    private Integer unitPostId;

    private Integer userId;

    private String phone;

    private Boolean isCurrent;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date confirmDate;

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

    public Boolean getIsPost() {
        return isPost;
    }

    public void setIsPost(Boolean isPost) {
        this.isPost = isPost;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}