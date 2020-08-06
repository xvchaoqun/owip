package domain.modify;

import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import sys.constants.ModifyConstants;
import sys.tags.CmTag;
import sys.tags.UserTag;

import java.io.Serializable;
import java.util.Date;

public class ModifyBaseItem implements Serializable {
    
    public SysUserView getCheckUser(){
        return CmTag.getUserById(checkUserId);
    }

    public String getSignOrginalValue(){
        if(type== ModifyConstants.MODIFY_BASE_ITEM_TYPE_IMAGE && orginalValue!=null){
            return UserTag.sign(orginalValue);
        }
        return null;
    }

    public String getSignModifyValue(){
        if((StringUtils.equals(code, "avatar")
                || type== ModifyConstants.MODIFY_BASE_ITEM_TYPE_IMAGE)
                && modifyValue!=null){

            return UserTag.sign(modifyValue);
        }
        return null;
    }

    private Integer id;

    private Integer applyId;

    private String code;

    private String tableName;

    private String tableIdName;

    private String name;

    private String orginalValue;

    private String modifyValue;

    private Byte type;

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

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getTableIdName() {
        return tableIdName;
    }

    public void setTableIdName(String tableIdName) {
        this.tableIdName = tableIdName == null ? null : tableIdName.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOrginalValue() {
        return orginalValue;
    }

    public void setOrginalValue(String orginalValue) {
        this.orginalValue = orginalValue == null ? null : orginalValue.trim();
    }

    public String getModifyValue() {
        return modifyValue;
    }

    public void setModifyValue(String modifyValue) {
        this.modifyValue = modifyValue == null ? null : modifyValue.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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