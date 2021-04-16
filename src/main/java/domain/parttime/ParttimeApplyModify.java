package domain.parttime;

import sys.jackson.SignRes;

import java.io.Serializable;
import java.util.Date;

public class ParttimeApplyModify implements Serializable {
    private Integer id;

    private Byte modifyType;

    private Integer applyId;

    private Integer cadreId;

    private Date applyDate;

    private Byte type;

    private String title;

    private Boolean isFirst;

    private Boolean background;

    private Boolean hasPay;

    private Integer balance;

    private String reason;

    private Date startTime;

    private Date endTime;

    @SignRes
    private String modifyProof;

    private String modifyProofFileName;

    private String remark;

    private Integer modifyUserId;

    private Date createTime;

    private String ip;

    private String stauts;

    private Boolean isFinish;

    private Boolean flowNode;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Boolean getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Boolean isFirst) {
        this.isFirst = isFirst;
    }

    public Boolean getBackground() {
        return background;
    }

    public void setBackground(Boolean background) {
        this.background = background;
    }

    public Boolean getHasPay() {
        return hasPay;
    }

    public void setHasPay(Boolean hasPay) {
        this.hasPay = hasPay;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
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

    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts == null ? null : stauts.trim();
    }

    public Boolean getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Boolean isFinish) {
        this.isFinish = isFinish;
    }

    public Boolean getFlowNode() {
        return flowNode;
    }

    public void setFlowNode(Boolean flowNode) {
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