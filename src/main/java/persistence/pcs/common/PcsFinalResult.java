package persistence.pcs.common;

import domain.sys.SysUserView;
import sys.tags.CmTag;

public class PcsFinalResult {

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }

    public Integer pollId;//党代会投票id
    public Integer userId;//推荐人
    public Byte type;//推荐人类型
    public Integer branchNum;//投票党支部数
    public Integer positiveBallot;//党员票数
    public Integer growBallot;//预备党员票数
    public Integer supportNum;//支持人数
    public Integer notSupportNum;//不支持人数
    public Integer notVoteNum;//弃权票

    public Integer getPollId() {
        return pollId;
    }

    public void setPollId(Integer pollId) {
        this.pollId = pollId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public Integer getPositiveBallot() {
        return positiveBallot;
    }

    public void setPositiveBallot(Integer positiveBallot) {
        this.positiveBallot = positiveBallot;
    }

    public Integer getGrowBallot() {
        return growBallot;
    }

    public void setGrowBallot(Integer growBallot) {
        this.growBallot = growBallot;
    }

    public Integer getSupportNum() {
        return supportNum;
    }

    public void setSupportNum(Integer supportNum) {
        this.supportNum = supportNum;
    }

    public Integer getNotSupportNum() {
        return notSupportNum;
    }

    public void setNotSupportNum(Integer notSupportNum) {
        this.notSupportNum = notSupportNum;
    }

    public Integer getNotVoteNum() {
        return notVoteNum;
    }

    public void setNotVoteNum(Integer notVoteNum) {
        this.notVoteNum = notVoteNum;
    }
}
