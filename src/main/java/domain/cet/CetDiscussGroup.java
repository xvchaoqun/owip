package domain.cet;

import java.io.Serializable;
import java.util.Date;

public class CetDiscussGroup implements Serializable {
    private Integer id;

    private Integer discussId;

    private Integer holdUserId;

    private String subject;

    private Boolean subjectCanModify;

    private Date discussTime;

    private String discussAddress;

    private Integer untiId;

    private Integer adminUserId;

    private Integer sortOrder;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDiscussId() {
        return discussId;
    }

    public void setDiscussId(Integer discussId) {
        this.discussId = discussId;
    }

    public Integer getHoldUserId() {
        return holdUserId;
    }

    public void setHoldUserId(Integer holdUserId) {
        this.holdUserId = holdUserId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public Boolean getSubjectCanModify() {
        return subjectCanModify;
    }

    public void setSubjectCanModify(Boolean subjectCanModify) {
        this.subjectCanModify = subjectCanModify;
    }

    public Date getDiscussTime() {
        return discussTime;
    }

    public void setDiscussTime(Date discussTime) {
        this.discussTime = discussTime;
    }

    public String getDiscussAddress() {
        return discussAddress;
    }

    public void setDiscussAddress(String discussAddress) {
        this.discussAddress = discussAddress == null ? null : discussAddress.trim();
    }

    public Integer getUntiId() {
        return untiId;
    }

    public void setUntiId(Integer untiId) {
        this.untiId = untiId;
    }

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}