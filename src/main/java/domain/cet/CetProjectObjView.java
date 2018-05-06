package domain.cet;

import service.cet.CetProjectObjService;
import sys.tags.CmTag;

import java.io.Serializable;
import java.math.BigDecimal;

public class CetProjectObjView implements Serializable {

    public BigDecimal getFinishPeriod(){

        CetProjectObjService cetProjectObjService = CmTag.getBean(CetProjectObjService.class);
        return cetProjectObjService.getFinishPeriod(projectId, id);
    }

    private Integer id;

    private Integer projectId;

    private Integer userId;

    private Integer traineeTypeId;

    private Boolean isQuit;

    private BigDecimal shouldFinishPeriod;

    private Boolean isGraduate;

    private String wordWrite;

    private String pdfWrite;

    private String remark;

    private String username;

    private String code;

    private String realname;

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

    public Boolean getIsQuit() {
        return isQuit;
    }

    public void setIsQuit(Boolean isQuit) {
        this.isQuit = isQuit;
    }

    public BigDecimal getShouldFinishPeriod() {
        return shouldFinishPeriod;
    }

    public void setShouldFinishPeriod(BigDecimal shouldFinishPeriod) {
        this.shouldFinishPeriod = shouldFinishPeriod;
    }

    public Boolean getIsGraduate() {
        return isGraduate;
    }

    public void setIsGraduate(Boolean isGraduate) {
        this.isGraduate = isGraduate;
    }

    public String getWordWrite() {
        return wordWrite;
    }

    public void setWordWrite(String wordWrite) {
        this.wordWrite = wordWrite == null ? null : wordWrite.trim();
    }

    public String getPdfWrite() {
        return pdfWrite;
    }

    public void setPdfWrite(String pdfWrite) {
        this.pdfWrite = pdfWrite == null ? null : pdfWrite.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
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
}