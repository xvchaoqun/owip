package domain.abroad;

import domain.cadre.Cadre;
import domain.sys.SysUser;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class ApplySelfModify implements Serializable {

    public SysUser getModifyUser() {
        return CmTag.getUserById(modifyUserId);
    }
    public SysUser getUser(){
        Cadre cadre = getCadre();
        return CmTag.getUserById(cadre.getUserId());
    }
    public Cadre getCadre() {
        return CmTag.getCadreById(cadreId);
    }

    private Integer id;

    private Byte modifyType;

    private Integer applyId;

    private Integer cadreId;

    private Date applyDate;

    private Byte type;

    private Date startDate;

    private Date endDate;

    private String toCountry;

    private String reason;

    private String peerStaff;

    private String costSource;

    private String needPassports;

    private String modifyProof;

    private String modifyProofFileName;

    private String remark;

    private Integer modifyUserId;

    private Date createTime;

    private String ip;

    private Boolean status;

    private Boolean isFinish;

    private Integer flowNode;

    private String flowNodes;

    private String flowUsers;

    private Boolean isAgreed;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getModifyType() {
        return modifyType;
    }

    public void setModifyType(Byte modifyType) {
        this.modifyType = modifyType;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getToCountry() {
        return toCountry;
    }

    public void setToCountry(String toCountry) {
        this.toCountry = toCountry == null ? null : toCountry.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getPeerStaff() {
        return peerStaff;
    }

    public void setPeerStaff(String peerStaff) {
        this.peerStaff = peerStaff == null ? null : peerStaff.trim();
    }

    public String getCostSource() {
        return costSource;
    }

    public void setCostSource(String costSource) {
        this.costSource = costSource == null ? null : costSource.trim();
    }

    public String getNeedPassports() {
        return needPassports;
    }

    public void setNeedPassports(String needPassports) {
        this.needPassports = needPassports == null ? null : needPassports.trim();
    }

    public String getModifyProof() {
        return modifyProof;
    }

    public void setModifyProof(String modifyProof) {
        this.modifyProof = modifyProof == null ? null : modifyProof.trim();
    }

    public String getModifyProofFileName() {
        return modifyProofFileName;
    }

    public void setModifyProofFileName(String modifyProofFileName) {
        this.modifyProofFileName = modifyProofFileName == null ? null : modifyProofFileName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(Integer modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Boolean isFinish) {
        this.isFinish = isFinish;
    }

    public Integer getFlowNode() {
        return flowNode;
    }

    public void setFlowNode(Integer flowNode) {
        this.flowNode = flowNode;
    }

    public String getFlowNodes() {
        return flowNodes;
    }

    public void setFlowNodes(String flowNodes) {
        this.flowNodes = flowNodes == null ? null : flowNodes.trim();
    }

    public String getFlowUsers() {
        return flowUsers;
    }

    public void setFlowUsers(String flowUsers) {
        this.flowUsers = flowUsers == null ? null : flowUsers.trim();
    }

    public Boolean getIsAgreed() {
        return isAgreed;
    }

    public void setIsAgreed(Boolean isAgreed) {
        this.isAgreed = isAgreed;
    }
}