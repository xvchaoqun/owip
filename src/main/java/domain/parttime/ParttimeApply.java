package domain.parttime;

import domain.cadre.CadreView;
import domain.sys.SysUserView;
import org.springframework.format.annotation.DateTimeFormat;
import sys.helper.ParttimeHelper;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class ParttimeApply implements Serializable {
    public CadreView getCadre(){
        return CmTag.getCadreById(cadreId);
    }
    private SysUserView sysUserView;
    public SysUserView getUser(){

        if(sysUserView==null) {
            CadreView cadre = getCadre();
            sysUserView = CmTag.getUserById(cadre.getUserId());
        }

        return sysUserView;
    }

    public Map getApprovalTdBeanMap(){

        return ParttimeHelper.getParttimeApprovalTdBeanMap(id);
    }

    private Integer id;

    private Integer cadreId;

    private Byte type;

    private String title;

    private Boolean isFirst;

    private Boolean background;

    private Boolean hasPay;

    private Integer balance;

    private String reason;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date startTime;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date endTime;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date applyTime;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private Date createTime;

    private String approvalRemark;

    private Boolean isFinish;

    private Integer flowNode;

    private String flowNodes;

    private String flowUsers;

    private Boolean status;

    private Boolean isAgreed;

    private Boolean isModify;

    private Boolean isDeleted;

    private String remark;

    private String ip;

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

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getApprovalRemark() {
        return approvalRemark;
    }

    public void setApprovalRemark(String approvalRemark) {
        this.approvalRemark = approvalRemark == null ? null : approvalRemark.trim();
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }
}