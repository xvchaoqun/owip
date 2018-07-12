package domain.sc.scPassport;

import java.io.Serializable;
import java.util.Date;

public class ScPassportMsg implements Serializable {
    private Integer id;

    private Integer handId;

    private Integer userId;

    private String tplKey;

    private Integer contentTplId;

    private String msg;

    private Date sendTime;

    private Boolean success;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHandId() {
        return handId;
    }

    public void setHandId(Integer handId) {
        this.handId = handId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTplKey() {
        return tplKey;
    }

    public void setTplKey(String tplKey) {
        this.tplKey = tplKey == null ? null : tplKey.trim();
    }

    public Integer getContentTplId() {
        return contentTplId;
    }

    public void setContentTplId(Integer contentTplId) {
        this.contentTplId = contentTplId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}