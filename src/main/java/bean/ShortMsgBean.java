package bean;

/**
 * Created by fafa on 2016/3/18.
 */
public class ShortMsgBean {

    private Integer relateId;
    private Byte relateType;
    private Integer sender;
    private Integer receiver;
    private String mobile;
    private String content;
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
