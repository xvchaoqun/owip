package domain.crs;

import java.io.Serializable;
import java.util.Date;

public class CrsApplicantCheck implements Serializable {
    private Integer id;

    private Integer applicantId;

    private Integer requireRuleId;

    private Boolean pass;

    private Date checkTime;

    private Integer checkUserId;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Integer applicantId) {
        this.applicantId = applicantId;
    }

    public Integer getRequireRuleId() {
        return requireRuleId;
    }

    public void setRequireRuleId(Integer requireRuleId) {
        this.requireRuleId = requireRuleId;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Integer getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}