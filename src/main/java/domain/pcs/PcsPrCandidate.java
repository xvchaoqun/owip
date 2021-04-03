package domain.pcs;

import java.io.Serializable;
import java.util.Date;

public class PcsPrCandidate implements Serializable {
    private Integer id;

    private Integer recommendId;

    private Integer userId;

    private String code;

    private String realname;

    private Integer leaderSortOrder;

    private Byte userType;

    private Integer eduId;

    private String post;

    private Date growTime;

    private Date workTime;

    private String proPost;

    private String education;

    private String degree;

    private Boolean isRetire;

    private String eduLevel;

    private Integer partyId;

    private Integer configId;

    private Byte stage;

    private Integer partySortOrder;

    private String unitName;

    private Integer branchVote;

    private Integer type;

    private Byte gender;

    private Date birth;

    private String nation;

    private String mobile;

    private String email;

    private Integer vote;

    private Integer positiveVote;

    private Integer vote3;

    private Boolean isChosen;

    private Boolean isProposal;

    private Integer proposalSortOrder;

    private Boolean isFromStage;

    private Integer sortOrder;

    private Integer realnameSortOrder;

    private Date addTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(Integer recommendId) {
        this.recommendId = recommendId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getLeaderSortOrder() {
        return leaderSortOrder;
    }

    public void setLeaderSortOrder(Integer leaderSortOrder) {
        this.leaderSortOrder = leaderSortOrder;
    }

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    public Integer getEduId() {
        return eduId;
    }

    public void setEduId(Integer eduId) {
        this.eduId = eduId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public Date getGrowTime() {
        return growTime;
    }

    public void setGrowTime(Date growTime) {
        this.growTime = growTime;
    }

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public String getProPost() {
        return proPost;
    }

    public void setProPost(String proPost) {
        this.proPost = proPost == null ? null : proPost.trim();
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
    }

    public Boolean getIsRetire() {
        return isRetire;
    }

    public void setIsRetire(Boolean isRetire) {
        this.isRetire = isRetire;
    }

    public String getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel == null ? null : eduLevel.trim();
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public Byte getStage() {
        return stage;
    }

    public void setStage(Byte stage) {
        this.stage = stage;
    }

    public Integer getPartySortOrder() {
        return partySortOrder;
    }

    public void setPartySortOrder(Integer partySortOrder) {
        this.partySortOrder = partySortOrder;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public Integer getBranchVote() {
        return branchVote;
    }

    public void setBranchVote(Integer branchVote) {
        this.branchVote = branchVote;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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

    public Integer getVote3() {
        return vote3;
    }

    public void setVote3(Integer vote3) {
        this.vote3 = vote3;
    }

    public Boolean getIsChosen() {
        return isChosen;
    }

    public void setIsChosen(Boolean isChosen) {
        this.isChosen = isChosen;
    }

    public Boolean getIsProposal() {
        return isProposal;
    }

    public void setIsProposal(Boolean isProposal) {
        this.isProposal = isProposal;
    }

    public Integer getProposalSortOrder() {
        return proposalSortOrder;
    }

    public void setProposalSortOrder(Integer proposalSortOrder) {
        this.proposalSortOrder = proposalSortOrder;
    }

    public Boolean getIsFromStage() {
        return isFromStage;
    }

    public void setIsFromStage(Boolean isFromStage) {
        this.isFromStage = isFromStage;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getRealnameSortOrder() {
        return realnameSortOrder;
    }

    public void setRealnameSortOrder(Integer realnameSortOrder) {
        this.realnameSortOrder = realnameSortOrder;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}