package domain.modify;

import domain.cadre.CadreView;
import domain.sys.SysUserView;
import persistence.cadre.common.ICadreMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class ModifyCadreAuth implements Serializable {
    public CadreView getCadre(){

        ICadreMapper iCadreMapper = CmTag.getBean(ICadreMapper.class);
        return iCadreMapper.getCadre(cadreId);

        //return CmTag.getCadreById(cadreId); 此处不能使用缓存里的干部，因为可能不在缓存中（普通教师）
    }
    public SysUserView getAddUser(){

        return CmTag.getUserById(addUserId);
    }
    private Integer id;

    private Integer cadreId;

    private Date startTime;

    private Date endTime;

    private Boolean isUnlimited;

    private Byte type;

    private Byte status;

    private Date addTime;

    private Integer addUserId;

    private String addIp;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getIsUnlimited() {
        return isUnlimited;
    }

    public void setIsUnlimited(Boolean isUnlimited) {
        this.isUnlimited = isUnlimited;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Integer addUserId) {
        this.addUserId = addUserId;
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
    }
}