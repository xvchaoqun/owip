package domain.pcs;

import java.io.Serializable;
import java.util.Date;

public class PcsAdminReport implements Serializable {
    private Integer id;

    private Integer partyId;

    private Integer userId;

    private Integer configId;

    private Byte stage;

    private Date createTime;

    private Integer branchCount;

    private Integer memberCount;

    private Integer positiveCount;

    private Integer studentMemberCount;

    private Integer teacherMemberCount;

    private Integer retireMemberCount;

    private String ip;

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

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public Byte getStage() {
        return stage;
    }

    public void setStage(Byte stage) {
        this.stage = stage;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }
}