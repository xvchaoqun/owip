package domain.dr;

import domain.sys.SysUserView;
import persistence.dr.DrOnlineCandidateMapper;
import service.dr.DrOnlinePostService;
import sys.tags.CmTag;

import java.io.Serializable;

public class DrOnlineResultView implements Serializable {

    public DrOnlineCandidate getCandidate(){
        DrOnlineCandidateMapper drOnlineCandidateMapper = CmTag.getBean(DrOnlineCandidateMapper.class);
        return drOnlineCandidateMapper.selectByPrimaryKey(candidateId);
    }

    public DrOnlinePostView getPost(){
        DrOnlinePostService drOnlinePostService = CmTag.getBean(DrOnlinePostService.class);
        return drOnlinePostService.getPost(postId);
    }

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }

    private Integer onlineId;

    private Integer postId;

    private Integer candidateId;

    private Integer optionSum;

    private Integer userId;

    private Integer pubCounts;

    private Integer finishCounts;

    private static final long serialVersionUID = 1L;

    public Integer getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Integer onlineId) {
        this.onlineId = onlineId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Integer getOptionSum() {
        return optionSum;
    }

    public void setOptionSum(Integer optionSum) {
        this.optionSum = optionSum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPubCounts() {
        return pubCounts;
    }

    public void setPubCounts(Integer pubCounts) {
        this.pubCounts = pubCounts;
    }

    public Integer getFinishCounts() {
        return finishCounts;
    }

    public void setFinishCounts(Integer finishCounts) {
        this.finishCounts = finishCounts;
    }

    public String toString() {
        return "DrOnlineResultView{" +
                "onlineId=" + onlineId +
                ", postId=" + postId +
                ", candidateId=" + candidateId +
                ", optionSum=" + optionSum +
                ", userId=" + userId +
                ", pubCounts=" + pubCounts +
                ", finishCounts=" + finishCounts +
                '}';
    }
}