package controller.pcs;

/**
 * Created by lm on 2017/8/30.
 */
public class PcsPrCandidateFormBean {

    private Byte type;
    private Integer userId;
    private Integer vote;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }
}
