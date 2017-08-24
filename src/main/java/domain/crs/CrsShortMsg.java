package domain.crs;

import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class CrsShortMsg implements Serializable {

    public String getPostName(){

        CrsPost crsPost = CmTag.getCrsPost(postId);
        return (crsPost!=null)?crsPost.getName():null;
    }

    private Integer id;

    private Integer postId;

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

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
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