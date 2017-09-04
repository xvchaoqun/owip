package domain.pcs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PcsPartyView implements Serializable {
    private Integer id;

    private String code;

    private String name;

    private String shortName;

    private String url;

    private Integer unitId;

    private Integer classId;

    private Integer typeId;

    private Integer unitTypeId;

    private Boolean isEnterpriseBig;

    private Boolean isEnterpriseNationalized;

    private Boolean isSeparate;

    private String phone;

    private String fax;

    private String email;

    private Date foundTime;

    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;

    private Boolean isDeleted;

    private Long branchCount;

    private Long memberCount;

    private BigDecimal studentMemberCount;

    private BigDecimal positiveCount;

    private BigDecimal teacherMemberCount;

    private BigDecimal retireMemberCount;

    private Long groupCount;

    private Long presentGroupCount;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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

    public Boolean getIsSeparate() {
        return isSeparate;
    }

    public void setIsSeparate(Boolean isSeparate) {
        this.isSeparate = isSeparate;
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

    public Long getBranchCount() {
        return branchCount;
    }

    public void setBranchCount(Long branchCount) {
        this.branchCount = branchCount;
    }

    public Long getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Long memberCount) {
        this.memberCount = memberCount;
    }

    public BigDecimal getStudentMemberCount() {
        return studentMemberCount;
    }

    public void setStudentMemberCount(BigDecimal studentMemberCount) {
        this.studentMemberCount = studentMemberCount;
    }

    public BigDecimal getPositiveCount() {
        return positiveCount;
    }

    public void setPositiveCount(BigDecimal positiveCount) {
        this.positiveCount = positiveCount;
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

    public Long getPresentGroupCount() {
        return presentGroupCount;
    }

    public void setPresentGroupCount(Long presentGroupCount) {
        this.presentGroupCount = presentGroupCount;
    }
}