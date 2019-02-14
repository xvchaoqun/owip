package domain.dr;

import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import sys.tags.CmTag;
import sys.utils.XmlSerializeUtils;

import java.io.Serializable;
import java.util.Map;

public class DrOfflineCandidate implements Serializable {

    public Map<Integer, Integer> getVoterMap(){
        if(StringUtils.isNotBlank(voters)) {
            return XmlSerializeUtils.unserialize(voters, Map.class);
        }
        return null;
    }

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }

    private Integer id;

    private Integer offlineId;

    private Integer userId;

    private Integer vote;

    private String voters;

    private String weight;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOfflineId() {
        return offlineId;
    }

    public void setOfflineId(Integer offlineId) {
        this.offlineId = offlineId;
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

    public String getVoters() {
        return voters;
    }

    public void setVoters(String voters) {
        this.voters = voters == null ? null : voters.trim();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}