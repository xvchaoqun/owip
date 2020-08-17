package controller.pcs.cm;

public class PcsCandidateFormBean {

    private Byte type;
    private Integer userId;
    private Integer vote;

    public Byte getType() {
        return type;
    }

    public PcsCandidateFormBean setType(Byte type) {
        this.type = type;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public PcsCandidateFormBean setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getVote() {
        return vote;
    }

    public PcsCandidateFormBean setVote(Integer vote) {
        this.vote = vote;
        return this;
    }
}
