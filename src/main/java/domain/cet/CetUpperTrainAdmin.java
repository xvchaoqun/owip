package domain.cet;

import domain.sys.SysUserView;
import domain.unit.Unit;
import sys.tags.CmTag;

import java.io.Serializable;

public class CetUpperTrainAdmin implements Serializable {
    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    public Unit getUnit(){
        return CmTag.getUnit(unitId);
    }
    public SysUserView getLeaderUser(){
        return CmTag.getUserById(leaderUserId);
    }

    private Integer id;

    private Byte upperType;

    private Boolean type;

    private Integer unitId;

    private Integer leaderUserId;

    private Integer userId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getUpperType() {
        return upperType;
    }

    public void setUpperType(Byte upperType) {
        this.upperType = upperType;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getLeaderUserId() {
        return leaderUserId;
    }

    public void setLeaderUserId(Integer leaderUserId) {
        this.leaderUserId = leaderUserId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}