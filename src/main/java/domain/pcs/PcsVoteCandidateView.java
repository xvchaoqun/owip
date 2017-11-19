package domain.pcs;

import java.io.Serializable;
import java.math.BigDecimal;

public class PcsVoteCandidateView implements Serializable {
    private Integer userId;

    private String realname;

    private Boolean isFromStage;

    private BigDecimal agree;

    private BigDecimal degree;

    private BigDecimal abstain;

    private static final long serialVersionUID = 1L;

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
        this.realname = realname == null ? null : realname.trim();
    }

    public Boolean getIsFromStage() {
        return isFromStage;
    }

    public void setIsFromStage(Boolean isFromStage) {
        this.isFromStage = isFromStage;
    }

    public BigDecimal getAgree() {
        return agree;
    }

    public void setAgree(BigDecimal agree) {
        this.agree = agree;
    }

    public BigDecimal getDegree() {
        return degree;
    }

    public void setDegree(BigDecimal degree) {
        this.degree = degree;
    }

    public BigDecimal getAbstain() {
        return abstain;
    }

    public void setAbstain(BigDecimal abstain) {
        this.abstain = abstain;
    }
}