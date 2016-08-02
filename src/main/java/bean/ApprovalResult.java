package bean;

import domain.abroad.ApprovalLog;

/**
 * Created by fafa on 2016/3/11.
 */
public class ApprovalResult {

    private Integer value;
    private ApprovalLog approvalLog;

    public ApprovalResult(Integer value, ApprovalLog approvalLog) {
        this.value = value;
        this.approvalLog = approvalLog;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public ApprovalLog getApprovalLog() {
        return approvalLog;
    }

    public void setApprovalLog(ApprovalLog approvalLog) {
        this.approvalLog = approvalLog;
    }
}
