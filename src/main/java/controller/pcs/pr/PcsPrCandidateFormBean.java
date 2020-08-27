package controller.pcs.pr;

/**
 * Created by lm on 2017/8/30.
 */
public class PcsPrCandidateFormBean {

    private Byte type;
    private Integer userId;
    private Integer branchVote;
    private Integer vote;
    private Integer positiveVote;
    private Byte gender;
    private String birth;
    private String nation;
    private String mobile; // 三下三上
    private String email; // 三下三上

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

    public Integer getBranchVote() {
        return branchVote;
    }

    public PcsPrCandidateFormBean setBranchVote(Integer branchVote) {
        this.branchVote = branchVote;
        return this;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

    public Integer getPositiveVote() {
        return positiveVote;
    }

    public void setPositiveVote(Integer positiveVote) {
        this.positiveVote = positiveVote;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
