package domain.dp;

import org.springframework.format.annotation.DateTimeFormat;
import sys.helper.DpPartyHelper;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class DpPartyMemberGroup implements Serializable {
    public DpParty getDpParty(){
        return DpPartyHelper.getDpPartyByPartyId(partyId);
    }

    private Integer id;

    private Integer fid;

    private Integer partyId;

    private String name;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date tranTime;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date actualTranTime;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date appointTime;

    private Integer dispatchUnitId;

    private Integer sortOrder;

    private Boolean isDeleted;

    private String groupSession;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public Date getActualTranTime() {
        return actualTranTime;
    }

    public void setActualTranTime(Date actualTranTime) {
        this.actualTranTime = actualTranTime;
    }

    public Date getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(Date appointTime) {
        this.appointTime = appointTime;
    }

    public Integer getDispatchUnitId() {
        return dispatchUnitId;
    }

    public void setDispatchUnitId(Integer dispatchUnitId) {
        this.dispatchUnitId = dispatchUnitId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getGroupSession() {
        return groupSession;
    }

    public void setGroupSession(String groupSession) {
        this.groupSession = groupSession == null ? null : groupSession.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}