package domain.sc.scGroup;

import domain.sys.SysUserView;
import service.sc.scGroup.ScGroupService;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ScGroupTopicView implements Serializable {

    public List<SysUserView> getUsers(){
        ScGroupService scGroupService = CmTag.getBean(ScGroupService.class);
        return scGroupService.getMemberUserList(groupId);
    }
    private Integer id;

    private String filePath;

    private Integer groupId;

    private String name;

    private String content;

    private String memo;

    private String remark;

    private Boolean isDeleted;

    private Integer year;

    private Date holdDate;

    private String groupFilePath;

    private String logFile;

    private String attendUsers;

    private String unitIds;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public String getGroupFilePath() {
        return groupFilePath;
    }

    public void setGroupFilePath(String groupFilePath) {
        this.groupFilePath = groupFilePath == null ? null : groupFilePath.trim();
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile == null ? null : logFile.trim();
    }

    public String getAttendUsers() {
        return attendUsers;
    }

    public void setAttendUsers(String attendUsers) {
        this.attendUsers = attendUsers == null ? null : attendUsers.trim();
    }

    public String getUnitIds() {
        return unitIds;
    }

    public void setUnitIds(String unitIds) {
        this.unitIds = unitIds == null ? null : unitIds.trim();
    }
}