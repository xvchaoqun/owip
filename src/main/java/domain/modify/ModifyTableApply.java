package domain.modify;

import domain.cadre.CadreView;
import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class ModifyTableApply implements Serializable {
    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    public CadreView getCadre(){
        return CmTag.getCadreByUserId(userId);
    }
    public SysUserView getCheckUser(){
        return CmTag.getUserById(checkUserId);
    }
    private Integer id;

    private Byte module;

    private String tableName;

    private Integer originalId;

    private Integer modifyId;

    private Integer applyUserId;

    private Integer userId;

    private Byte type;

    private String originalJson;

    private Date createTime;

    private String ip;

    private Byte status;

    private String checkRemark;

    private String checkReason;

    private Integer checkUserId;

    private Date checkTime;

    private String checkIp;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getModule() {
        return module;
    }

    public void setModule(Byte module) {
        this.module = module;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public Integer getOriginalId() {
        return originalId;
    }

    public void setOriginalId(Integer originalId) {
        this.originalId = originalId;
    }

    public Integer getModifyId() {
        return modifyId;
    }

    public void setModifyId(Integer modifyId) {
        this.modifyId = modifyId;
    }

    public Integer getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(Integer applyUserId) {
        this.applyUserId = applyUserId;
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

    public String getOriginalJson() {
        return originalJson;
    }

    public void setOriginalJson(String originalJson) {
        this.originalJson = originalJson == null ? null : originalJson.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCheckRemark() {
        return checkRemark;
    }

    public void setCheckRemark(String checkRemark) {
        this.checkRemark = checkRemark == null ? null : checkRemark.trim();
    }

    public String getCheckReason() {
        return checkReason;
    }

    public void setCheckReason(String checkReason) {
        this.checkReason = checkReason == null ? null : checkReason.trim();
    }

    public Integer getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckIp() {
        return checkIp;
    }

    public void setCheckIp(String checkIp) {
        this.checkIp = checkIp == null ? null : checkIp.trim();
    }
}