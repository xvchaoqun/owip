package domain.base;

import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class OneSend implements Serializable {

    public SysUserView getSender(){
        return CmTag.getUserById(sendUserId);
    }
    private Integer id;

    private Integer sendUserId;

    private String title;

    private String content;

    private String type;

    private String recivers;

    private String codes;

    private Date sendTime;

    private Boolean isSuccess;

    private String ret;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getRecivers() {
        return recivers;
    }

    public void setRecivers(String recivers) {
        this.recivers = recivers == null ? null : recivers.trim();
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes == null ? null : codes.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret == null ? null : ret.trim();
    }
}