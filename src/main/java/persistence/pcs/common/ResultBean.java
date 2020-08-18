package persistence.pcs.common;

public class ResultBean {

    public Integer userId;//推荐人id

    public Integer branchNum;//提名支部数量

    public Integer ballot;//正式党员投的票

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public Integer getBallot() {
        return ballot;
    }

    public void setBallot(Integer ballot) {
        this.ballot = ballot;
    }
}
