package domain.cet;

import java.io.Serializable;

public class CetSpecialObj implements Serializable {
    private Integer id;

    private Integer specialId;

    private Integer userId;

    private Byte type;

    private Boolean isQuit;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpecialId() {
        return specialId;
    }

    public void setSpecialId(Integer specialId) {
        this.specialId = specialId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Boolean getIsQuit() {
        return isQuit;
    }

    public void setIsQuit(Boolean isQuit) {
        this.isQuit = isQuit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}