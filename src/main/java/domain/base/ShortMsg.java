package domain.base;

import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class ShortMsg implements Serializable {
    public SysUserView getUser(){
        return CmTag.getUserById(receiverId);
    }
    public SysUserView getSender(){
        return CmTag.getUserById(senderId);
    }
    private Integer id;

    private Byte type;

    private Byte wxMsgType;

    private String wxTitle;

    private String wxUrl;

    private String wxPic;

    private Integer senderId;

    private Integer receiverId;

    private Integer relateId;

    private String relateSn;

    private Byte relateType;

    private String typeStr;

    private String mobile;

    private String content;

    private Date createTime;

    private String repeatTimes;

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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getWxMsgType() {
        return wxMsgType;
    }

    public void setWxMsgType(Byte wxMsgType) {
        this.wxMsgType = wxMsgType;
    }

    public String getWxTitle() {
        return wxTitle;
    }

    public void setWxTitle(String wxTitle) {
        this.wxTitle = wxTitle == null ? null : wxTitle.trim();
    }

    public String getWxUrl() {
        return wxUrl;
    }

    public void setWxUrl(String wxUrl) {
        this.wxUrl = wxUrl == null ? null : wxUrl.trim();
    }

    public String getWxPic() {
        return wxPic;
    }

    public void setWxPic(String wxPic) {
        this.wxPic = wxPic == null ? null : wxPic.trim();
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

    public Integer getRelateId() {
        return relateId;
    }

    public void setRelateId(Integer relateId) {
        this.relateId = relateId;
    }

    public String getRelateSn() {
        return relateSn;
    }

    public void setRelateSn(String relateSn) {
        this.relateSn = relateSn == null ? null : relateSn.trim();
    }

    public Byte getRelateType() {
        return relateType;
    }

    public void setRelateType(Byte relateType) {
        this.relateType = relateType;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr == null ? null : typeStr.trim();
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

    public String getRepeatTimes() {
        return repeatTimes;
    }

    public void setRepeatTimes(String repeatTimes) {
        this.repeatTimes = repeatTimes == null ? null : repeatTimes.trim();
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