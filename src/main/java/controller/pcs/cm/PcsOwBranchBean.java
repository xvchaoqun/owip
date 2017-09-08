package controller.pcs.cm;

import java.util.List;

/**
 * Created by lm on 2017/9/8.
 */
public class PcsOwBranchBean {

    public String partyName;
    public Integer partyId;
    public List<Integer> branchIds;  // 推荐的支部
    public List<Integer> notbranchIds; // 为推荐的支部

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public List<Integer> getBranchIds() {
        return branchIds;
    }

    public void setBranchIds(List<Integer> branchIds) {
        this.branchIds = branchIds;
    }

    public List<Integer> getNotbranchIds() {
        return notbranchIds;
    }

    public void setNotbranchIds(List<Integer> notbranchIds) {
        this.notbranchIds = notbranchIds;
    }
}
