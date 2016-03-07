package domain;

import java.io.Serializable;

public class ApprovalOrder implements Serializable {
    private Integer id;

    private Integer applicatTypeId;

    private Integer approverTypeId;

    private Integer sortOrder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApplicatTypeId() {
        return applicatTypeId;
    }

    public void setApplicatTypeId(Integer applicatTypeId) {
        this.applicatTypeId = applicatTypeId;
    }

    public Integer getApproverTypeId() {
        return approverTypeId;
    }

    public void setApproverTypeId(Integer approverTypeId) {
        this.approverTypeId = approverTypeId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}