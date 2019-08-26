package domain.pm;
import persistence.pm.IPmMapper;
import sys.tags.CmTag;

import java.io.Serializable;

public class PmQuarterBranch implements Serializable {

    public Integer getMeetingCount(){
        IPmMapper iPmMapper = CmTag.getBean(IPmMapper.class);
        return iPmMapper.getMeetingCount(branchId);
    }
    private Integer id;

    private Integer quarterId;

    private Integer partyId;

    private Integer branchId;

    private String branchName;

    private Boolean isExclude;

    private Integer meetingNum;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuarterId() {
        return quarterId;
    }

    public void setQuarterId(Integer quarterId) {
        this.quarterId = quarterId;
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

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
    }

    public Boolean getIsExclude() {
        return isExclude;
    }

    public void setIsExclude(Boolean isExclude) {
        this.isExclude = isExclude;
    }

    public Integer getMeetingNum() {
        return meetingNum;
    }

    public void setMeetingNum(Integer meetingNum) {
        this.meetingNum = meetingNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}