package domain.pcs;

import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;

public class PcsAdmin implements Serializable {

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    public String getUnit(){
        return CmTag.getUserUnit(userId);
    }

    private Integer id;

    private Integer partyId;

    private Integer userId;

    private Byte type;

    private Integer configId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
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

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }
}