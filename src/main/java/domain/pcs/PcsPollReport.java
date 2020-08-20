package domain.pcs;

import java.io.Serializable;

public class PcsPollReport implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer configId;

    private Integer partyId;

    private Integer branchId;

    private Byte stage;

    private Byte type;

    private String code;

    private String realname;

    private String unit;

    private Integer ballot;

    private Integer positiveBallot;

    private Integer growBallot;

    private Integer disagreeBallot;

    private Integer abstainBallot;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Byte getStage() {
        return stage;
    }

    public void setStage(Byte stage) {
        this.stage = stage;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Integer getBallot() {
        return ballot;
    }

    public void setBallot(Integer ballot) {
        this.ballot = ballot;
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

    public Integer getDisagreeBallot() {
        return disagreeBallot;
    }

    public void setDisagreeBallot(Integer disagreeBallot) {
        this.disagreeBallot = disagreeBallot;
    }

    public Integer getAbstainBallot() {
        return abstainBallot;
    }

    public void setAbstainBallot(Integer abstainBallot) {
        this.abstainBallot = abstainBallot;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}