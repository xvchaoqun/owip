package persistence.cla.common;

import domain.cla.ClaApprovalLog;

public class ClaApprovalResult {

    private Integer value;
    private ClaApprovalLog approvalLog;

    public ClaApprovalResult(Integer value, ClaApprovalLog approvalLog) {
        this.value = value;
        this.approvalLog = approvalLog;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public ClaApprovalLog getApprovalLog() {
        return approvalLog;
    }

    public void setApprovalLog(ClaApprovalLog approvalLog) {
        this.approvalLog = approvalLog;
    }
}
