package controller.pcs.cm;

import java.util.Set;

/**
 * Created by lm on 2017/9/8.
 */
public class PcsOwBranchBean {

    public String partyName;
    public Integer partyId;
    public Boolean isRecommend; // 分党委是否推荐，只要有一个支部推荐了，就算已推荐
    public Set<Integer> branchIds;  // 推荐的支部
    public Set<Integer> notbranchIds; // 为推荐的支部

    public Boolean getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

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

    public Set<Integer> getBranchIds() {
        return branchIds;
    }

    public void setBranchIds(Set<Integer> branchIds) {
        this.branchIds = branchIds;
    }

    public Set<Integer> getNotbranchIds() {
        return notbranchIds;
    }

    public void setNotbranchIds(Set<Integer> notbranchIds) {
        this.notbranchIds = notbranchIds;
    }
}
