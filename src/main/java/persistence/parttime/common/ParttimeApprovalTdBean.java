package persistence.parttime.common;

import domain.sys.SysUserView;

import java.util.List;

public class ParttimeApprovalTdBean {

    private Integer applyId;
    private Integer approvalTypeId;
    /**
     * 以下2个参数是当前审批人的权限
     */
    private int tdType;
    private boolean canApproval;
    private List<SysUserView> approverList;

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
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

    public List<SysUserView> getApproverList() {
        return approverList;
    }

    public void setApproverList(List<SysUserView> approverList) {
        this.approverList = approverList;
    }
}
