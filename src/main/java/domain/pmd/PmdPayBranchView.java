package domain.pmd;

import java.io.Serializable;

public class PmdPayBranchView implements Serializable {
    private Integer branchId;

    private Integer partyId;

    private Integer monthId;

    private String name;

    private Boolean isDeleted;

    private Integer adminCount;

    private String partyName;

    private Boolean partyIsDelete;

    private Integer partySortOrder;

    private static final long serialVersionUID = 1L;

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getMonthId() {
        return monthId;
    }

    public void setMonthId(Integer monthId) {
        this.monthId = monthId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getAdminCount() {
        return adminCount;
    }

    public void setAdminCount(Integer adminCount) {
        this.adminCount = adminCount;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName == null ? null : partyName.trim();
    }

    public Boolean getPartyIsDelete() {
        return partyIsDelete;
    }

    public void setPartyIsDelete(Boolean partyIsDelete) {
        this.partyIsDelete = partyIsDelete;
    }

    public Integer getPartySortOrder() {
        return partySortOrder;
    }

    public void setPartySortOrder(Integer partySortOrder) {
        this.partySortOrder = partySortOrder;
    }
}