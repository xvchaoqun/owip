package bean;

/**
 * Created by fafa on 2016/3/18.
 */
public class ShortMsgBean {

    private byte type; // 类型，1 短信 2 微信 3 短信+微信
    private Byte wxMsgType;
    private String wxTitle;
    private String wxUrl;
    private String wxPic;

    private Integer relateId;
    private Byte relateType;
    private Integer sender;
    private Integer receiver;
    private String mobile;
    private String content;
    private String typeStr;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
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
        this.wxTitle = wxTitle;
    }

    public String getWxUrl() {
        return wxUrl;
    }

    public void setWxUrl(String wxUrl) {
        this.wxUrl = wxUrl;
    }

    public String getWxPic() {
        return wxPic;
    }

    public void setWxPic(String wxPic) {
        this.wxPic = wxPic;
    }

    public Integer getRelateId() {
        return relateId;
    }

    public void setRelateId(Integer relateId) {
        this.relateId = relateId;
    }

    public Byte getRelateType() {
        return relateType;
    }

    public void setRelateType(Byte relateType) {
        this.relateType = relateType;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }
}
