package domain.pcs;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class PcsCommitteeMember implements Serializable {
    private Integer id;

    private Boolean type;

    private Integer configId;

    private Integer userId;

    private Integer post;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date postDate;

    private String postFilePath;

    private Boolean isQuit;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date quitDate;

    private String quitFilePath;

    private String quitReason;

    private Integer sortOrder;

    private String remark;

    private Byte status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPost() {
        return post;
    }

    public void setPost(Integer post) {
        this.post = post;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getPostFilePath() {
        return postFilePath;
    }

    public void setPostFilePath(String postFilePath) {
        this.postFilePath = postFilePath == null ? null : postFilePath.trim();
    }

    public Boolean getIsQuit() {
        return isQuit;
    }

    public void setIsQuit(Boolean isQuit) {
        this.isQuit = isQuit;
    }

    public Date getQuitDate() {
        return quitDate;
    }

    public void setQuitDate(Date quitDate) {
        this.quitDate = quitDate;
    }

    public String getQuitFilePath() {
        return quitFilePath;
    }

    public void setQuitFilePath(String quitFilePath) {
        this.quitFilePath = quitFilePath == null ? null : quitFilePath.trim();
    }

    public String getQuitReason() {
        return quitReason;
    }

    public void setQuitReason(String quitReason) {
        this.quitReason = quitReason == null ? null : quitReason.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}