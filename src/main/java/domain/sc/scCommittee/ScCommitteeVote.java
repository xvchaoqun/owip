package domain.sc.scCommittee;

import domain.cadre.CadreView;
import domain.sys.SysUserView;
import domain.unit.Unit;
import org.springframework.format.annotation.DateTimeFormat;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class ScCommitteeVote implements Serializable {

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

    private String originalPost;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date originalPostTime;

    private Integer cadreTypeId;

    private Integer wayId;

    private Integer procedureId;

    private String post;

    private Integer postId;

    private Integer adminLevelId;

    private Integer unitId;

    private Integer agreeCount;

    private Integer abstainCount;

    private Integer disagreeCount;

    private String remark;

    private Integer sortOrder;

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

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getAdminLevelId() {
        return adminLevelId;
    }

    public void setAdminLevelId(Integer adminLevelId) {
        this.adminLevelId = adminLevelId;
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
}