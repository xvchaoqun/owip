package domain.sc.scMotion;

import org.springframework.format.annotation.DateTimeFormat;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class ScMotion implements Serializable {

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

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date holdDate;

    private Integer unitPostId;

    private Integer scType;

    private String content;

    private Integer recordUserId;

    private String remark;

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
}