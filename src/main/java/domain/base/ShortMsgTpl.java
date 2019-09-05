package domain.base;

import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class ShortMsgTpl implements Serializable {

    public SysUserView getUser(){

        return (addUserId==null)?null:CmTag.getUserById(addUserId);
    }
    private Integer id;

    private Integer roleId;

    private Byte type;

    private Byte wxMsgType;

    private String wxTitle;

    private String wxUrl;

    private String wxPic;

    private String name;

    private String content;

    private String remark;

    private Integer sortOrder;

    private Integer sendCount;

    private Integer sendUserCount;

    private Date createTime;

    private String ip;

    private Integer addUserId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    public Integer getSendUserCount() {
        return sendUserCount;
    }

    public void setSendUserCount(Integer sendUserCount) {
        this.sendUserCount = sendUserCount;
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

    public Integer getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Integer addUserId) {
        this.addUserId = addUserId;
    }
}