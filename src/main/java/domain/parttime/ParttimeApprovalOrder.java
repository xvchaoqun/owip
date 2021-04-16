package domain.parttime;

import java.io.Serializable;

public class ParttimeApprovalOrder implements Serializable {
    private Integer id;

    private Integer applicateTypeId;

    private Integer approverTypeId;

    private Integer sortOrder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApplicateTypeId() {
        return applicateTypeId;
    }

    public void setApplicateTypeId(Integer applicateTypeId) {
        this.applicateTypeId = applicateTypeId;
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