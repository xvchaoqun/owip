package domain.cr;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class CrMeeting implements Serializable {
    private Integer id;

    private Integer infoId;

    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date meetingDate;

    private String postIds;

    private Integer requireNum;

    private Integer applyNum;

    private String remark;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getPostIds() {
        return postIds;
    }

    public void setPostIds(String postIds) {
        this.postIds = postIds == null ? null : postIds.trim();
    }

    public Integer getRequireNum() {
        return requireNum;
    }

    public void setRequireNum(Integer requireNum) {
        this.requireNum = requireNum;
    }

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}