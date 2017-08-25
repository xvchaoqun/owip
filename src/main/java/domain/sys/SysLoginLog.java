package domain.sys;

import org.apache.commons.lang.StringUtils;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class SysLoginLog implements Serializable {

    public SysUserView getUser(){

        if(type==null || type== SystemConstants.LOGIN_TYPE_TRAIN_INSPECTOR
        || StringUtils.isBlank(username)) return null;

        return CmTag.getUserByUsername(username);
    }

    private Integer id;

    private Integer userId;

    private String username;

    private Date loginTime;

    private String loginIp;

    private String country;

    private String area;

    private Date lastLoginTime;

    private String lastLoginIp;

    private String lastCountry;

    private String lastArea;

    private String agent;

    private Byte type;

    private Boolean success;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public String getLastCountry() {
        return lastCountry;
    }

    public void setLastCountry(String lastCountry) {
        this.lastCountry = lastCountry == null ? null : lastCountry.trim();
    }

    public String getLastArea() {
        return lastArea;
    }

    public void setLastArea(String lastArea) {
        this.lastArea = lastArea == null ? null : lastArea.trim();
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent == null ? null : agent.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}