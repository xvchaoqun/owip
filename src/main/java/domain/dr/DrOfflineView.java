package domain.dr;

import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import persistence.dr.DrMemberMapper;
import sys.jackson.SignRes;
import sys.tags.CmTag;
import sys.tags.UserTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DrOfflineView implements Serializable {

    public String getSrCode(){
        return "纪实〔"+srSeq+"〕号";
    }

    public String getCode(){
        return String.format("民主推荐〔%s%s〕号",
                DateUtils.formatDate(recommendDate, "yyyyMMdd"),
                String.format("%02d", seq));
    }
    public DrMember getChiefMember(){

        DrMemberMapper drMemberMapper = CmTag.getBean(DrMemberMapper.class);
        return drMemberMapper.selectByPrimaryKey(chiefMemberId);
    }

    public SysUserView getSuperviceUser(){
        return CmTag.getUserById(superviceUserId);
    }

    public List<DrMember> getDrMembers(){
        List<DrMember> memberList = new ArrayList<>();
        if(StringUtils.isNotBlank(members)) {
            DrMemberMapper drMemberMapper = CmTag.getBean(DrMemberMapper.class);
            for (String memberIdStr : members.split(",")) {
                memberList.add(drMemberMapper.selectByPrimaryKey(Integer.valueOf(memberIdStr)));
            }
        }
        return memberList;
    }

    private Integer id;

    private Short year;

    private Date recommendDate;

    private Integer seq;

    private Integer type;

    private Integer recordId;

    private Integer chiefMemberId;

    private Integer superviceUserId;

    private String members;

    @SignRes
    private String ballotSample;

    private Integer headcount;

    private String scope;

    private Integer expectVoterNum;

    private Integer actualVoterNum;

    private Integer ballot;

    private Integer abstain;

    private Integer invalid;

    private String title;

    private Boolean needVoterType;

    private Integer voterTypeTplId;

    private String voters;

    private String remark;

    private String srSeq;

    private Date holdDate;

    private Integer scType;

    private Integer unitPostId;

    private String postName;

    private String job;

    private Integer adminLevel;

    private Integer postType;

    private Integer unitId;

    private Integer unitType;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public Date getRecommendDate() {
        return recommendDate;
    }

    public void setRecommendDate(Date recommendDate) {
        this.recommendDate = recommendDate;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getChiefMemberId() {
        return chiefMemberId;
    }

    public void setChiefMemberId(Integer chiefMemberId) {
        this.chiefMemberId = chiefMemberId;
    }

    public Integer getSuperviceUserId() {
        return superviceUserId;
    }

    public void setSuperviceUserId(Integer superviceUserId) {
        this.superviceUserId = superviceUserId;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members == null ? null : members.trim();
    }

    public String getBallotSample() {
        return ballotSample;
    }

    public void setBallotSample(String ballotSample) {
        this.ballotSample = ballotSample == null ? null : ballotSample.trim();
    }

    public Integer getHeadcount() {
        return headcount;
    }

    public void setHeadcount(Integer headcount) {
        this.headcount = headcount;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope == null ? null : scope.trim();
    }

    public Integer getExpectVoterNum() {
        return expectVoterNum;
    }

    public void setExpectVoterNum(Integer expectVoterNum) {
        this.expectVoterNum = expectVoterNum;
    }

    public Integer getActualVoterNum() {
        return actualVoterNum;
    }

    public void setActualVoterNum(Integer actualVoterNum) {
        this.actualVoterNum = actualVoterNum;
    }

    public Integer getBallot() {
        return ballot;
    }

    public void setBallot(Integer ballot) {
        this.ballot = ballot;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Boolean getNeedVoterType() {
        return needVoterType;
    }

    public void setNeedVoterType(Boolean needVoterType) {
        this.needVoterType = needVoterType;
    }

    public Integer getVoterTypeTplId() {
        return voterTypeTplId;
    }

    public void setVoterTypeTplId(Integer voterTypeTplId) {
        this.voterTypeTplId = voterTypeTplId;
    }

    public String getVoters() {
        return voters;
    }

    public void setVoters(String voters) {
        this.voters = voters == null ? null : voters.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getSrSeq() {
        return srSeq;
    }

    public void setSrSeq(String srSeq) {
        this.srSeq = srSeq == null ? null : srSeq.trim();
    }

    public Date getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(Date holdDate) {
        this.holdDate = holdDate;
    }

    public Integer getScType() {
        return scType;
    }

    public void setScType(Integer scType) {
        this.scType = scType;
    }

    public Integer getUnitPostId() {
        return unitPostId;
    }

    public void setUnitPostId(Integer unitPostId) {
        this.unitPostId = unitPostId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName == null ? null : postName.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }

    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getUnitType() {
        return unitType;
    }

    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }
}