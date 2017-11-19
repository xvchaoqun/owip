package domain.pcs;

import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;

public class PcsVoteGroup implements Serializable {

    public SysUserView getRecordUser(){
        return CmTag.getUserById(recordUserId);
    }
    private Integer id;

    private Integer configId;

    private Byte type;

    private String name;

    private String leader;

    private String member;

    private Integer recordUserId;

    private Boolean hasReport;

    private Integer vote;

    private Integer valid;

    private Integer invalid;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader == null ? null : leader.trim();
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member == null ? null : member.trim();
    }

    public Integer getRecordUserId() {
        return recordUserId;
    }

    public void setRecordUserId(Integer recordUserId) {
        this.recordUserId = recordUserId;
    }

    public Boolean getHasReport() {
        return hasReport;
    }

    public void setHasReport(Boolean hasReport) {
        this.hasReport = hasReport;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getInvalid() {
        return invalid;
    }

    public void setInvalid(Integer invalid) {
        this.invalid = invalid;
    }
}