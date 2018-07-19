package persistence.abroad.common;

import domain.cadre.CadreView;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by fafa on 2016/3/25.
 */
public class ApproverTypeBean {

    public CadreView cadre;

    public boolean isApprover; // 是否是拥有其他审批身份
    Map<Integer, List<Integer>> approverTypeCadreIdListMap; //  其他审批身份及对应的审批的申请人

    public Set<Integer> approvalCadreIdSet; // 需要审批的干部

    public CadreView getCadre() {
        return cadre;
    }

    public void setCadre(CadreView cadre) {
        this.cadre = cadre;
    }

    public boolean isApprover() {
        return isApprover;
    }

    public void setApprover(boolean isApprover) {
        this.isApprover = isApprover;
    }

    public Map<Integer, List<Integer>> getApproverTypeCadreIdListMap() {
        return approverTypeCadreIdListMap;
    }

    public void setApproverTypeCadreIdListMap(Map<Integer, List<Integer>> approverTypeCadreIdListMap) {
        this.approverTypeCadreIdListMap = approverTypeCadreIdListMap;
    }

    public Set<Integer> getApprovalCadreIdSet() {
        return approvalCadreIdSet;
    }

    public void setApprovalCadreIdSet(Set<Integer> approvalCadreIdSet) {
        this.approvalCadreIdSet = approvalCadreIdSet;
    }
}
