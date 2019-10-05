package domain.party;

import java.io.Serializable;
import java.util.Date;

public class PartyView implements Serializable {
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

    private String mailbox;

    private Date foundTime;

    private Integer sortOrder;

    private Date createTime;

    private Boolean isPycj;

    private Date pycjDate;

    private Boolean isBg;

    private Date bgDate;

    private Date updateTime;

    private Boolean isDeleted;

    private Integer branchCount;

    private Integer memberCount;

    private Integer studentMemberCount;

    private Integer positiveCount;

    private Integer teacherMemberCount;

    private Integer retireMemberCount;

    private Integer groupCount;

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

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox == null ? null : mailbox.trim();
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

    public Boolean getIsPycj() {
        return isPycj;
    }

    public void setIsPycj(Boolean isPycj) {
        this.isPycj = isPycj;
    }

    public Date getPycjDate() {
        return pycjDate;
    }

    public void setPycjDate(Date pycjDate) {
        this.pycjDate = pycjDate;
    }

    public Boolean getIsBg() {
        return isBg;
    }

    public void setIsBg(Boolean isBg) {
        this.isBg = isBg;
    }

    public Date getBgDate() {
        return bgDate;
    }

    public void setBgDate(Date bgDate) {
        this.bgDate = bgDate;
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

    public Integer getBranchCount() {
        return branchCount;
    }

    public void setBranchCount(Integer branchCount) {
        this.branchCount = branchCount;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Integer getStudentMemberCount() {
        return studentMemberCount;
    }

    public void setStudentMemberCount(Integer studentMemberCount) {
        this.studentMemberCount = studentMemberCount;
    }

    public Integer getPositiveCount() {
        return positiveCount;
    }

    public void setPositiveCount(Integer positiveCount) {
        this.positiveCount = positiveCount;
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

    public Integer getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Integer groupCount) {
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