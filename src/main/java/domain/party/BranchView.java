package domain.party;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BranchView implements Serializable {
    private Integer id;

    private String code;

    private String name;

    private String shortName;

    private Integer partyId;

    private Integer typeId;

    private Boolean isStaff;

    private Boolean isPrefessional;

    private Boolean isBaseTeam;

    private Integer unitTypeId;

    private Boolean isEnterpriseBig;

    private Boolean isEnterpriseNationalized;

    private Boolean isUnion;

    private String phone;

    private String fax;

    private String email;

    private Date foundTime;

    private Integer transferCount;

    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;

    private Boolean isDeleted;

    private Integer partySortOrder;

    private Long memberCount;

    private BigDecimal positiveCount;

    private BigDecimal studentMemberCount;

    private BigDecimal teacherMemberCount;

    private BigDecimal retireMemberCount;

    private Long groupCount;

    private Integer presentGroupId;

    private Date appointTime;

    private Date tranTime;

    private Date actualTranTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Boolean getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(Boolean isStaff) {
        this.isStaff = isStaff;
    }

    public Boolean getIsPrefessional() {
        return isPrefessional;
    }

    public void setIsPrefessional(Boolean isPrefessional) {
        this.isPrefessional = isPrefessional;
    }

    public Boolean getIsBaseTeam() {
        return isBaseTeam;
    }

    public void setIsBaseTeam(Boolean isBaseTeam) {
        this.isBaseTeam = isBaseTeam;
    }

    public Integer getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Integer unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public Boolean getIsEnterpriseBig() {
        return isEnterpriseBig;
    }

    public void setIsEnterpriseBig(Boolean isEnterpriseBig) {
        this.isEnterpriseBig = isEnterpriseBig;
    }

    public Boolean getIsEnterpriseNationalized() {
        return isEnterpriseNationalized;
    }

    public void setIsEnterpriseNationalized(Boolean isEnterpriseNationalized) {
        this.isEnterpriseNationalized = isEnterpriseNationalized;
    }

    public Boolean getIsUnion() {
        return isUnion;
    }

    public void setIsUnion(Boolean isUnion) {
        this.isUnion = isUnion;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getFoundTime() {
        return foundTime;
    }

    public void setFoundTime(Date foundTime) {
        this.foundTime = foundTime;
    }

    public Integer getTransferCount() {
        return transferCount;
    }

    public void setTransferCount(Integer transferCount) {
        this.transferCount = transferCount;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getPartySortOrder() {
        return partySortOrder;
    }

    public void setPartySortOrder(Integer partySortOrder) {
        this.partySortOrder = partySortOrder;
    }

    public Long getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Long memberCount) {
        this.memberCount = memberCount;
    }

    public BigDecimal getPositiveCount() {
        return positiveCount;
    }

    public void setPositiveCount(BigDecimal positiveCount) {
        this.positiveCount = positiveCount;
    }

    public BigDecimal getStudentMemberCount() {
        return studentMemberCount;
    }

    public void setStudentMemberCount(BigDecimal studentMemberCount) {
        this.studentMemberCount = studentMemberCount;
    }

    public BigDecimal getTeacherMemberCount() {
        return teacherMemberCount;
    }

    public void setTeacherMemberCount(BigDecimal teacherMemberCount) {
        this.teacherMemberCount = teacherMemberCount;
    }

    public BigDecimal getRetireMemberCount() {
        return retireMemberCount;
    }

    public void setRetireMemberCount(BigDecimal retireMemberCount) {
        this.retireMemberCount = retireMemberCount;
    }

    public Long getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Long groupCount) {
        this.groupCount = groupCount;
    }

    public Integer getPresentGroupId() {
        return presentGroupId;
    }

    public void setPresentGroupId(Integer presentGroupId) {
        this.presentGroupId = presentGroupId;
    }

    public Date getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(Date appointTime) {
        this.appointTime = appointTime;
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
}