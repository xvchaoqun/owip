package domain.dr;

import persistence.dr.DrOnlineInspectorTypeMapper;
import persistence.dr.DrOnlineMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class DrOnlineInspector implements Serializable {

    public DrOnline getDrOnline(){
        DrOnlineMapper drOnlineMapper = CmTag.getBean(DrOnlineMapper.class);
        return drOnlineMapper.selectByPrimaryKey(onlineId);
    }

    public DrOnlineInspectorType getInspectorType(){
        DrOnlineInspectorTypeMapper inspectorTypeMapper = CmTag.getBean(DrOnlineInspectorTypeMapper.class);
        return inspectorTypeMapper.selectByPrimaryKey(typeId);
    }
    private Integer id;

    private Integer logId;

    private Integer onlineId;

    private String username;

    private String passwd;

    private Byte passwdChangeType;

    private Integer typeId;

    private Integer unitId;

    private Integer type;

    private String tempdata;

    private Byte status;

    private Boolean isMobile;

    private Byte pubStatus;

    private String remark;

    private Date submitTime;

    private String submitIp;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Integer onlineId) {
        this.onlineId = onlineId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public Byte getPasswdChangeType() {
        return passwdChangeType;
    }

    public void setPasswdChangeType(Byte passwdChangeType) {
        this.passwdChangeType = passwdChangeType;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTempdata() {
        return tempdata;
    }

    public void setTempdata(String tempdata) {
        this.tempdata = tempdata == null ? null : tempdata.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Boolean getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(Boolean isMobile) {
        this.isMobile = isMobile;
    }

    public Byte getPubStatus() {
        return pubStatus;
    }

    public void setPubStatus(Byte pubStatus) {
        this.pubStatus = pubStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getSubmitIp() {
        return submitIp;
    }

    public void setSubmitIp(String submitIp) {
        this.submitIp = submitIp == null ? null : submitIp.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}