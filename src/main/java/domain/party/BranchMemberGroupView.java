package domain.party;

import domain.dispatch.Dispatch;
import domain.dispatch.DispatchUnit;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class BranchMemberGroupView implements Serializable {
    public String getDispatchCode(){
        if(dispatchUnitId==null) return null;
        DispatchUnit dispatchUnit = CmTag.getDispatchUnit(dispatchUnitId);
        if(dispatchUnit==null) return null;
        Dispatch dispatch = dispatchUnit.getDispatch();
        if(dispatch==null) return null;
        return CmTag.getDispatchCode(dispatch.getCode(), dispatch.getDispatchTypeId(), dispatch.getYear());
    }
    private Integer id;

    private Integer fid;

    private Integer branchId;

    private String name;

    private Boolean isPresent;

    private Date tranTime;

    private Date actualTranTime;

    private Date appointTime;

    private Integer dispatchUnitId;

    private Integer sortOrder;

    private Boolean isDeleted;

    private Integer partyId;

    private Integer partySortOrder;

    private Integer branchSortOrder;

    private Long memberCount;

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

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(Boolean isPresent) {
        this.isPresent = isPresent;
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

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getPartySortOrder() {
        return partySortOrder;
    }

    public void setPartySortOrder(Integer partySortOrder) {
        this.partySortOrder = partySortOrder;
    }

    public Integer getBranchSortOrder() {
        return branchSortOrder;
    }

    public void setBranchSortOrder(Integer branchSortOrder) {
        this.branchSortOrder = branchSortOrder;
    }

    public Long getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Long memberCount) {
        this.memberCount = memberCount;
    }
}