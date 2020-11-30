package domain.sys;

import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class SysMsg implements Serializable {

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    };

    public SysUserView getSendUser(){
        return CmTag.getUserById(sendUserId);
    }

    private Integer id;

    private Integer userId;

    private Integer sendUserId;

    private String title;

    private String content;

    private Date sendTime;

    private Date confirmTime;

    private String ip;

    private Byte status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Integer sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}