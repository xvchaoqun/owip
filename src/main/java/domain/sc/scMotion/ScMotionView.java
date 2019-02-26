package domain.sc.scMotion;

import java.io.Serializable;
import java.util.Date;

public class ScMotionView implements Serializable {

    public String getCode(){
        return "动议〔"+seq+"〕号";
    }

    private Integer id;

    private Short year;

    private Date holdDate;

    private String seq;

    private Integer unitPostId;

    private Integer scType;

    private Byte way;

    private String wayOther;

    private String topics;

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

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public Date getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(Date holdDate) {
        this.holdDate = holdDate;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq == null ? null : seq.trim();
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

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics == null ? null : topics.trim();
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