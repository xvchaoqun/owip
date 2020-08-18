package domain.pcs;

import java.io.Serializable;

public class PcsBranch implements Serializable {
    private Integer id;

    private Integer configId;

    private Integer partyId;

    private Integer branchId;

    private Boolean isDirectBranch;

    private String name;

    private Integer sortOrder;

    private Integer memberCount;

    private Integer positiveCount;

    private Integer studentMemberCount;

    private Integer teacherMemberCount;

    private Integer retireMemberCount;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Boolean getIsDirectBranch() {
        return isDirectBranch;
    }

    public void setIsDirectBranch(Boolean isDirectBranch) {
        this.isDirectBranch = isDirectBranch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Integer getPositiveCount() {
        return positiveCount;
    }

    public void setPositiveCount(Integer positiveCount) {
        this.positiveCount = positiveCount;
    }

    public Integer getStudentMemberCount() {
        return studentMemberCount;
    }

    public void setStudentMemberCount(Integer studentMemberCount) {
        this.studentMemberCount = studentMemberCount;
    }

    public Integer getTeacherMemberCount() {
        return teacherMemberCount;
    }

    public void setTeacherMemberCount(Integer teacherMemberCount) {
        this.teacherMemberCount = teacherMemberCount;
    }

    public Integer getRetireMemberCount() {
        return retireMemberCount;
    }

    public void setRetireMemberCount(Integer retireMemberCount) {
        this.retireMemberCount = retireMemberCount;
    }
}