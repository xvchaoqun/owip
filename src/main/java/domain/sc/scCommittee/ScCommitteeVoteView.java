package domain.sc.scCommittee;

import domain.cadre.CadreView;
import domain.sys.SysUserView;
import domain.unit.Unit;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class ScCommitteeVoteView implements Serializable {
    public Unit getUnit(){
        return CmTag.getUnit(unitId);
    }

    public SysUserView getUser(){
        CadreView cadre = getCadre();
        return CmTag.getUserById(cadre.getUserId());
    }
    public CadreView getCadre(){
        return CmTag.getCadreById(cadreId);
    }
    private Integer id;

    private Integer topicId;

    private Integer cadreId;

    private Byte type;

    private Integer unitPostId;

    private Integer cadreTypeId;

    private Integer wayId;

    private Integer procedureId;

    private String post;

    private Integer postType;

    private Integer adminLevel;

    private Integer unitId;

    private Integer agreeCount;

    private Integer abstainCount;

    private Integer disagreeCount;

    private String remark;

    private Integer sortOrder;

    private String name;

    private Integer seq;

    private String content;

    private Integer committeeId;

    private String voteFilePath;

    private String originalPost;

    private Date originalPostTime;

    private Integer year;

    private Date holdDate;

    private Integer committeeMemberCount;

    private Integer count;

    private Integer absentCount;

    private String attendUsers;

    private String filePath;

    private String logFile;

    private Integer dispatchUserId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getUnitPostId() {
        return unitPostId;
    }

    public void setUnitPostId(Integer unitPostId) {
        this.unitPostId = unitPostId;
    }

    public Integer getCadreTypeId() {
        return cadreTypeId;
    }

    public void setCadreTypeId(Integer cadreTypeId) {
        this.cadreTypeId = cadreTypeId;
    }

    public Integer getWayId() {
        return wayId;
    }

    public void setWayId(Integer wayId) {
        this.wayId = wayId;
    }

    public Integer getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(Integer procedureId) {
        this.procedureId = procedureId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getAgreeCount() {
        return agreeCount;
    }

    public void setAgreeCount(Integer agreeCount) {
        this.agreeCount = agreeCount;
    }

    public Integer getAbstainCount() {
        return abstainCount;
    }

    public void setAbstainCount(Integer abstainCount) {
        this.abstainCount = abstainCount;
    }

    public Integer getDisagreeCount() {
        return disagreeCount;
    }

    public void setDisagreeCount(Integer disagreeCount) {
        this.disagreeCount = disagreeCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(Integer committeeId) {
        this.committeeId = committeeId;
    }

    public String getVoteFilePath() {
        return voteFilePath;
    }

    public void setVoteFilePath(String voteFilePath) {
        this.voteFilePath = voteFilePath == null ? null : voteFilePath.trim();
    }

    public String getOriginalPost() {
        return originalPost;
    }

    public void setOriginalPost(String originalPost) {
        this.originalPost = originalPost == null ? null : originalPost.trim();
    }

    public Date getOriginalPostTime() {
        return originalPostTime;
    }

    public void setOriginalPostTime(Date originalPostTime) {
        this.originalPostTime = originalPostTime;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(Date holdDate) {
        this.holdDate = holdDate;
    }

    public Integer getCommitteeMemberCount() {
        return committeeMemberCount;
    }

    public void setCommitteeMemberCount(Integer committeeMemberCount) {
        this.committeeMemberCount = committeeMemberCount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(Integer absentCount) {
        this.absentCount = absentCount;
    }

    public String getAttendUsers() {
        return attendUsers;
    }

    public void setAttendUsers(String attendUsers) {
        this.attendUsers = attendUsers == null ? null : attendUsers.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile == null ? null : logFile.trim();
    }

    public Integer getDispatchUserId() {
        return dispatchUserId;
    }

    public void setDispatchUserId(Integer dispatchUserId) {
        this.dispatchUserId = dispatchUserId;
    }
}