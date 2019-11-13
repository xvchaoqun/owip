package domain.op;

import domain.sys.SysUserView;
import org.springframework.format.annotation.DateTimeFormat;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class OpRecord implements Serializable {
    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    public SysUserView getTalkUser(){
        return CmTag.getUserById(talkUserId);
    }
    private Integer id;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date startDate;

    private Integer userId;

    private Integer adminLevel;

    private String post;

    private Integer type;

    private Integer way;

    private Integer talkType;

    private Integer talkUserId;

    private Integer issue;

    private String issueOther;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    public Integer getTalkType() {
        return talkType;
    }

    public void setTalkType(Integer talkType) {
        this.talkType = talkType;
    }

    public Integer getTalkUserId() {
        return talkUserId;
    }

    public void setTalkUserId(Integer talkUserId) {
        this.talkUserId = talkUserId;
    }

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    public String getIssueOther() {
        return issueOther;
    }

    public void setIssueOther(String issueOther) {
        this.issueOther = issueOther == null ? null : issueOther.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}