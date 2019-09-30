package domain.cet;

import domain.sys.SysUserView;
import persistence.cet.CetUnitProjectMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CetUnitTrain implements Serializable {

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    public SysUserView getAddUser(){
        return CmTag.getUserById(addUserId);
    }
    public CetUnitProject getProject(){
        return CmTag.getBean(CetUnitProjectMapper.class).selectByPrimaryKey(projectId);
    }

    private Integer id;

    private Integer projectId;

    private Integer userId;

    private Integer traineeTypeId;

    private String title;

    private Integer postType;

    private BigDecimal period;

    private String wordNote;

    private String pdfNote;

    private Integer addUserId;

    private Date addTime;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTraineeTypeId() {
        return traineeTypeId;
    }

    public void setTraineeTypeId(Integer traineeTypeId) {
        this.traineeTypeId = traineeTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    public BigDecimal getPeriod() {
        return period;
    }

    public void setPeriod(BigDecimal period) {
        this.period = period;
    }

    public String getWordNote() {
        return wordNote;
    }

    public void setWordNote(String wordNote) {
        this.wordNote = wordNote == null ? null : wordNote.trim();
    }

    public String getPdfNote() {
        return pdfNote;
    }

    public void setPdfNote(String pdfNote) {
        this.pdfNote = pdfNote == null ? null : pdfNote.trim();
    }

    public Integer getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Integer addUserId) {
        this.addUserId = addUserId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}