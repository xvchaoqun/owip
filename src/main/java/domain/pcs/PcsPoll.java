package domain.pcs;

import controller.global.OpException;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import service.pcs.PcsPollService;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class PcsPoll implements Serializable {

    public String getReportMsg(){

        if(BooleanUtils.isTrue(hasReport)) return "reported";
        if(id==null) return "null";

        PcsPollService pcsPollService = CmTag.getBean(PcsPollService.class);
        try {
            pcsPollService.checkReportData(this);
        }catch (OpException opException){
            return opException.getMessage();
        }

        return "";
    }

    private Integer id;

    private Integer partyId;

    private Integer branchId;

    private String partyName;

    private String branchName;

    private String name;

    private Integer configId;

    private Byte stage;

    private Boolean hasReport;

    private Integer prNum;

    private Integer dwNum;

    private Integer jwNum;

    private Integer expectMemberCount;

    private Integer actualMemberCount;

    private Date reportDate;

    private Integer inspectorNum;

    private Integer inspectorFinishNum;

    private Integer positiveFinishNum;

    private String notice;

    private String mobileNotice;

    private String inspectorNotice;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    private Boolean isDeleted;

    private Integer userId;

    private Date createTime;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName == null ? null : partyName.trim();
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public Byte getStage() {
        return stage;
    }

    public void setStage(Byte stage) {
        this.stage = stage;
    }

    public Boolean getHasReport() {
        return hasReport;
    }

    public void setHasReport(Boolean hasReport) {
        this.hasReport = hasReport;
    }

    public Integer getPrNum() {
        return prNum;
    }

    public void setPrNum(Integer prNum) {
        this.prNum = prNum;
    }

    public Integer getDwNum() {
        return dwNum;
    }

    public void setDwNum(Integer dwNum) {
        this.dwNum = dwNum;
    }

    public Integer getJwNum() {
        return jwNum;
    }

    public void setJwNum(Integer jwNum) {
        this.jwNum = jwNum;
    }

    public Integer getExpectMemberCount() {
        return expectMemberCount;
    }

    public void setExpectMemberCount(Integer expectMemberCount) {
        this.expectMemberCount = expectMemberCount;
    }

    public Integer getActualMemberCount() {
        return actualMemberCount;
    }

    public void setActualMemberCount(Integer actualMemberCount) {
        this.actualMemberCount = actualMemberCount;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Integer getInspectorNum() {
        return inspectorNum;
    }

    public void setInspectorNum(Integer inspectorNum) {
        this.inspectorNum = inspectorNum;
    }

    public Integer getInspectorFinishNum() {
        return inspectorFinishNum;
    }

    public void setInspectorFinishNum(Integer inspectorFinishNum) {
        this.inspectorFinishNum = inspectorFinishNum;
    }

    public Integer getPositiveFinishNum() {
        return positiveFinishNum;
    }

    public void setPositiveFinishNum(Integer positiveFinishNum) {
        this.positiveFinishNum = positiveFinishNum;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice == null ? null : notice.trim();
    }

    public String getMobileNotice() {
        return mobileNotice;
    }

    public void setMobileNotice(String mobileNotice) {
        this.mobileNotice = mobileNotice == null ? null : mobileNotice.trim();
    }

    public String getInspectorNotice() {
        return inspectorNotice;
    }

    public void setInspectorNotice(String inspectorNotice) {
        this.inspectorNotice = inspectorNotice == null ? null : inspectorNotice.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}