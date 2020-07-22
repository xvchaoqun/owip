package persistence.dr.common;

public class DrFinalResult {

    public Integer onlineId;//批次
    public Integer postId;//推荐职务
    public String postName;//推荐职务
    public String realname;//推荐人姓名
    public Integer ballot;//票数

    public Integer getOnlineId() {
        return onlineId;
    }

    public DrFinalResult setOnlineId(Integer onlineId) {
        this.onlineId = onlineId;
        return this;
    }

    public Integer getPostId() {
        return postId;
    }

    public DrFinalResult setPostId(Integer postId) {
        this.postId = postId;
        return this;
    }

    public String getPostName() {
        return postName;
    }

    public DrFinalResult setPostName(String postName) {
        this.postName = postName;
        return this;
    }

    public String getRealname() {
        return realname;
    }

    public DrFinalResult setRealname(String realname) {
        this.realname = realname;
        return this;
    }

    public Integer getBallot() {
        return ballot;
    }

    public DrFinalResult setBallot(Integer ballot) {
        this.ballot = ballot;
        return this;
    }
}
