package domain.sc.scMotion;

import domain.sc.scCommittee.ScCommitteeTopicView;
import domain.sc.scGroup.ScGroupTopicView;
import domain.sys.SysUserView;
import persistence.sc.IScMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class ScMotionView implements Serializable {

    public SysUserView getRecordUser(){ return CmTag.getUserById(recordUserId);}

    public ScGroupTopicView getGroupTopic(){

        if(groupTopicId==null) return null;
        IScMapper iScMapper = CmTag.getBean(IScMapper.class);
        return iScMapper.getScGroupTopicView(groupTopicId);
    }
    public ScCommitteeTopicView getCommitteeTopic(){

        if(committeeTopicId==null) return null;
        IScMapper iScMapper = CmTag.getBean(IScMapper.class);
        return iScMapper.getScCommitteeTopicView(committeeTopicId);
    }

    public String getCode(){
        return "动议〔"+seq+"〕号";
    }

    private Integer id;

    private String seq;

    private Short year;

    private Byte way;

    private String wayOther;

    private Integer committeeTopicId;

    private Integer groupTopicId;

    private Date holdDate;

    private Integer unitPostId;

    private Integer scType;

    private String content;

    private Integer recordUserId;

    private String remark;

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

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq == null ? null : seq.trim();
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public Byte getWay() {
        return way;
    }

    public void setWay(Byte way) {
        this.way = way;
    }

    public String getWayOther() {
        return wayOther;
    }

    public void setWayOther(String wayOther) {
        this.wayOther = wayOther == null ? null : wayOther.trim();
    }

    public Integer getCommitteeTopicId() {
        return committeeTopicId;
    }

    public void setCommitteeTopicId(Integer committeeTopicId) {
        this.committeeTopicId = committeeTopicId;
    }

    public Integer getGroupTopicId() {
        return groupTopicId;
    }

    public void setGroupTopicId(Integer groupTopicId) {
        this.groupTopicId = groupTopicId;
    }

    public Date getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(Date holdDate) {
        this.holdDate = holdDate;
    }

    public Integer getUnitPostId() {
        return unitPostId;
    }

    public void setUnitPostId(Integer unitPostId) {
        this.unitPostId = unitPostId;
    }

    public Integer getScType() {
        return scType;
    }

    public void setScType(Integer scType) {
        this.scType = scType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getRecordUserId() {
        return recordUserId;
    }

    public void setRecordUserId(Integer recordUserId) {
        this.recordUserId = recordUserId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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