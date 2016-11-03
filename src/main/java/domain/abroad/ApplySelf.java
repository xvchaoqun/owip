package domain.abroad;

import domain.cadre.Cadre;
import domain.sys.SysUserView;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class ApplySelf implements Serializable {

    public String getTypeName(){
        return SystemConstants.APPLY_SELF_DATE_TYPE_MAP.get(type);
    }

    public SysUserView getUser(){
        Cadre cadre = getCadre();
        return CmTag.getUserById(cadre.getUserId());
    }
    public Cadre getCadre(){
        return CmTag.getCadreById(cadreId);
    }

    public Map getApprovalTdBeanMap(){

        return CmTag.getApprovalTdBeanMap(id);
    }
    private Integer id;

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

    private Date createTime;

    private String ip;

    private Boolean status;

    private Boolean isFinish;

    private Integer flowNode;

    private String flowNodes;

    private String flowUsers;

    private Boolean isAgreed;

    private Boolean isModify;

    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getIsModify() {
        return isModify;
    }

    public void setIsModify(Boolean isModify) {
        this.isModify = isModify;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}