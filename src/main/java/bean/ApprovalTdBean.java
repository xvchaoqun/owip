package bean;

/**
 * Created by fafa on 2016/3/26.
 */
public class ApprovalTdBean {

    private Integer applySelfId;
    private Integer approvalTypeId;
    /**
     * 以下2个参数是当前审批人的权限
     */
    private int tdType;
    private boolean canApproval;

    public Integer getApplySelfId() {
        return applySelfId;
    }

    public void setApplySelfId(Integer applySelfId) {
        this.applySelfId = applySelfId;
    }

    public Integer getApprovalTypeId() {
        return approvalTypeId;
    }

    public void setApprovalTypeId(Integer approvalTypeId) {
        this.approvalTypeId = approvalTypeId;
    }

    public int getTdType() {
        return tdType;
    }

    public void setTdType(int tdType) {
        this.tdType = tdType;
    }

    public boolean isCanApproval() {
        return canApproval;
    }

    public void setCanApproval(boolean canApproval) {
        this.canApproval = canApproval;
    }
}
