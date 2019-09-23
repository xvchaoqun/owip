package domain.dp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DpPartyView implements Serializable {
    private Integer id;

    private String code;

    private String name;

    private String shortName;

    private Integer unitId;

    private Integer classId;

    private String phone;

    private String fax;

    private String email;

    private String mailbox;

    private Date foundTime;

    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;

    private Boolean isDeleted;

    private Date deleteTime;

    private Long memberCount;

    private BigDecimal studentMemberCount;

    private BigDecimal positiveCount;

    private BigDecimal teacherMemberCount;

    private BigDecimal retireMemberCount;

    private BigDecimal honorRetireCount;

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

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
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

    public BigDecimal getHonorRetireCount() {
        return honorRetireCount;
    }

    public void setHonorRetireCount(BigDecimal honorRetireCount) {
        this.honorRetireCount = honorRetireCount;
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