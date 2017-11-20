package controller.pcs.vote;

/**
 * Created by lm on 2017/8/30.
 */
public class PcsVoteCandidateFormBean {

    private Integer userId;
    private String realname;
    private Integer agree;
    private Integer degree;
    private Integer abstain;
    private Integer invalid;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getAgree() {
        return agree;
    }

    public void setAgree(Integer agree) {
        this.agree = agree;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public Integer getAbstain() {
        return abstain;
    }

    public void setAbstain(Integer abstain) {
        this.abstain = abstain;
    }

    public Integer getInvalid() {
        return invalid;
    }

    public void setInvalid(Integer invalid) {
        this.invalid = invalid;
    }
}
