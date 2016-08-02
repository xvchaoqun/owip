package domain.sys;

import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class ShortMsg implements Serializable {

    public SysUser getUser(){
        return CmTag.getUserById(receiverId);
    }
    public SysUser getSender(){
        return CmTag.getUserById(senderId);
    }
    private Integer id;

    private Integer senderId;

    private Integer receiverId;

    private String type;

    private String mobile;

    private String content;

    private Date createTime;

    private String ip;

    private String remark;

    private Boolean status;

    private String ret;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret == null ? null : ret.trim();
    }
}