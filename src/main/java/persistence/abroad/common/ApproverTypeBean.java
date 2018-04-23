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

    public List<Integer> mainPostUnitIds; // 主职单位ID列表，包括兼任职务所在单位

    public boolean isManagerLeader; // 是否是分管校领导
    List<Integer> leaderUnitIds; // 分管单位

    public boolean isApprover; // 是否是拥有其他审批身份
    Map<Integer, List<Integer>> approverTypePostIdListMap; //  其他审批身份及对应的审批的职务属性

    public Set<Integer> approvalCadreIdSet; // 需要审批的干部

    public CadreView getCadre() {
        return cadre;
    }

    public void setCadre(CadreView cadre) {
        this.cadre = cadre;
    }

   /* public boolean isMainPost() {
        return isMainPost;
    }

    public void setMainPost(boolean isMainPost) {
        this.isMainPost = isMainPost;
    }*/

    public List<Integer> getMainPostUnitIds() {
        return mainPostUnitIds;
    }

    public void setMainPostUnitIds(List<Integer> mainPostUnitIds) {
        this.mainPostUnitIds = mainPostUnitIds;
    }

    public boolean isManagerLeader() {
        return isManagerLeader;
    }

    public void setManagerLeader(boolean isManagerLeader) {
        this.isManagerLeader = isManagerLeader;
    }

    public List<Integer> getLeaderUnitIds() {
        return leaderUnitIds;
    }

    public void setLeaderUnitIds(List<Integer> leaderUnitIds) {
        this.leaderUnitIds = leaderUnitIds;
    }

    public boolean isApprover() {
        return isApprover;
    }

    public void setApprover(boolean isApprover) {
        this.isApprover = isApprover;
    }

    public Map<Integer, List<Integer>> getApproverTypePostIdListMap() {
        return approverTypePostIdListMap;
    }

    public void setApproverTypePostIdListMap(Map<Integer, List<Integer>> approverTypePostIdListMap) {
        this.approverTypePostIdListMap = approverTypePostIdListMap;
    }

    public Set<Integer> getApprovalCadreIdSet() {
        return approvalCadreIdSet;
    }

    public void setApprovalCadreIdSet(Set<Integer> approvalCadreIdSet) {
        this.approvalCadreIdSet = approvalCadreIdSet;
    }
}
