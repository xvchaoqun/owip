package bean;

import domain.Cadre;

import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/3/25.
 */
public class ApproverTypeBean {

    public Cadre cadre;

    public boolean isMainPost; // 是否是正职
    public Integer mainPostUnitId;

    public boolean isManagerLeader; // 是否是分管校领导
    List<Integer> leaderUnitIds; // 分管单位

    public boolean isApprover; // 是否是拥有其他审批身份
    Map<Integer, List<Integer>> approverTypePostIdListMap; //  其他审批身份及对应的审批的职务属性

    public Cadre getCadre() {
        return cadre;
    }

    public void setCadre(Cadre cadre) {
        this.cadre = cadre;
    }

    public boolean isMainPost() {
        return isMainPost;
    }

    public void setMainPost(boolean isMainPost) {
        this.isMainPost = isMainPost;
    }

    public Integer getMainPostUnitId() {
        return mainPostUnitId;
    }

    public void setMainPostUnitId(Integer mainPostUnitId) {
        this.mainPostUnitId = mainPostUnitId;
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
}
