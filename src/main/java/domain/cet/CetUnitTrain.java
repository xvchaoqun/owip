package domain.cet;

import domain.sys.SysUserView;
import persistence.cet.CetUnitProjectMapper;
import service.cet.CetRecordService;
import sys.constants.CetConstants;
import sys.jackson.SignRes;
import sys.tags.CmTag;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CetUnitTrain implements Serializable {

    public Short getCertNo(){

        if(id==null) return null;

        CetRecordService cetRecordService = CmTag.getBean(CetRecordService.class);
        CetRecord cetRecord = cetRecordService.get(CetConstants.CET_SOURCE_TYPE_UNIT, id);
        if(cetRecord==null) return null;

        return cetRecord.getCertNo();
    }

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }

    public CetUnitProject getProject(){
        return CmTag.getBean(CetUnitProjectMapper.class).selectByPrimaryKey(projectId);
    }

    private Integer id;

    private Integer projectId;

    private Integer userId;

    private Integer traineeTypeId;

    private String otherTraineeType;

    private String identity;

    private String title;

    private Integer postType;

    private BigDecimal period;

    @SignRes
    private String wordNote;

    @SignRes
    private String pdfNote;

    private String score;

    private Boolean isGraduate;

    private Integer addUserId;

    private Date addTime;

    private Byte status;

    private String reason;

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

    public String getOtherTraineeType() {
        return otherTraineeType;
    }

    public void setOtherTraineeType(String otherTraineeType) {
        this.otherTraineeType = otherTraineeType == null ? null : otherTraineeType.trim();
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
    }

    public Boolean getIsGraduate() {
        return isGraduate;
    }

    public void setIsGraduate(Boolean isGraduate) {
        this.isGraduate = isGraduate;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}