package domain.cet;

import persistence.cet.CetPartyMapper;
import sys.tags.CmTag;

import java.io.Serializable;

public class CetPartyAdmin implements Serializable {
    private CetPartyMapper cetPartyMapper = CmTag.getBean(CetPartyMapper.class);
    public  CetParty getCetParty(){ return cetPartyMapper.selectByPrimaryKey(cetPartyId); }
    private Integer id;

    private Integer cetPartyId;

    private Integer userId;

    private Byte type;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCetPartyId() {
        return cetPartyId;
    }

    public void setCetPartyId(Integer cetPartyId) {
        this.cetPartyId = cetPartyId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}