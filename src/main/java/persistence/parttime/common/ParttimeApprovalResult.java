package persistence.parttime.common;

import domain.cla.ClaApprovalLog;
import domain.parttime.ParttimeApprovalLog;

public class ParttimeApprovalResult {

    private Integer value;
    private ParttimeApprovalLog approvalLog;

    public ParttimeApprovalResult(Integer value, ParttimeApprovalLog approvalLog) {
        this.value = value;
        this.approvalLog = approvalLog;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public ParttimeApprovalLog getApprovalLog() {
        return approvalLog;
    }

    public void setApprovalLog(ParttimeApprovalLog approvalLog) {
        this.approvalLog = approvalLog;
    }
}
