package domain.oa;

import java.io.Serializable;
import java.util.Date;

public class OaTaskUserView implements Serializable {
    private Integer id;

    private Integer taskId;

    private Date browseTime;

    private Integer userId;

    private String mobile;

    private String title;

    private Integer assignUserId;

    private String assignUserMobile;

    private String content;

    private String remark;

    private Boolean hasReport;

    private Integer reportUserId;

    private Date reportTime;

    private Boolean isBack;

    private Boolean isDelete;

    private String checkRemark;

    private Integer sortOrder;

    private Byte status;

    private String code;

    private String realname;

    private String taskName;

    private String taskContent;

    private Integer taskUserId;

    private String taskUserIds;

    private Date taskDeadline;

    private Integer userFileCount;

    private String taskContact;

    private Boolean taskIsDelete;

    private Boolean taskIsPublish;

    private Byte taskStatus;

    private Date taskPubDate;

    private Integer taskType;

    private String assignCode;

    private String assignRealname;

    private String reportCode;

    private String reportRealname;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Date getBrowseTime() {
        return browseTime;
    }

    public void setBrowseTime(Date browseTime) {
        this.browseTime = browseTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getAssignUserId() {
        return assignUserId;
    }

    public void setAssignUserId(Integer assignUserId) {
        this.assignUserId = assignUserId;
    }

    public String getAssignUserMobile() {
        return assignUserMobile;
    }

    public void setAssignUserMobile(String assignUserMobile) {
        this.assignUserMobile = assignUserMobile == null ? null : assignUserMobile.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Boolean getHasReport() {
        return hasReport;
    }

    public void setHasReport(Boolean hasReport) {
        this.hasReport = hasReport;
    }

    public Integer getReportUserId() {
        return reportUserId;
    }

    public void setReportUserId(Integer reportUserId) {
        this.reportUserId = reportUserId;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Boolean getIsBack() {
        return isBack;
    }

    public void setIsBack(Boolean isBack) {
        this.isBack = isBack;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getCheckRemark() {
        return checkRemark;
    }

    public void setCheckRemark(String checkRemark) {
        this.checkRemark = checkRemark == null ? null : checkRemark.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent == null ? null : taskContent.trim();
    }

    public Integer getTaskUserId() {
        return taskUserId;
    }

    public void setTaskUserId(Integer taskUserId) {
        this.taskUserId = taskUserId;
    }

    public String getTaskUserIds() {
        return taskUserIds;
    }

    public void setTaskUserIds(String taskUserIds) {
        this.taskUserIds = taskUserIds == null ? null : taskUserIds.trim();
    }

    public Date getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(Date taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    public Integer getUserFileCount() {
        return userFileCount;
    }

    public void setUserFileCount(Integer userFileCount) {
        this.userFileCount = userFileCount;
    }

    public String getTaskContact() {
        return taskContact;
    }

    public void setTaskContact(String taskContact) {
        this.taskContact = taskContact == null ? null : taskContact.trim();
    }

    public Boolean getTaskIsDelete() {
        return taskIsDelete;
    }

    public void setTaskIsDelete(Boolean taskIsDelete) {
        this.taskIsDelete = taskIsDelete;
    }

    public Boolean getTaskIsPublish() {
        return taskIsPublish;
    }

    public void setTaskIsPublish(Boolean taskIsPublish) {
        this.taskIsPublish = taskIsPublish;
    }

    public Byte getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Byte taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getTaskPubDate() {
        return taskPubDate;
    }

    public void setTaskPubDate(Date taskPubDate) {
        this.taskPubDate = taskPubDate;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getAssignCode() {
        return assignCode;
    }

    public void setAssignCode(String assignCode) {
        this.assignCode = assignCode == null ? null : assignCode.trim();
    }

    public String getAssignRealname() {
        return assignRealname;
    }

    public void setAssignRealname(String assignRealname) {
        this.assignRealname = assignRealname == null ? null : assignRealname.trim();
    }

    public String getReportCode() {
        return reportCode;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode == null ? null : reportCode.trim();
    }

    public String getReportRealname() {
        return reportRealname;
    }

    public void setReportRealname(String reportRealname) {
        this.reportRealname = reportRealname == null ? null : reportRealname.trim();
    }
}