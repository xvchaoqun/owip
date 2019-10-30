package domain.dp;

import domain.sys.SysUserView;
import org.springframework.format.annotation.DateTimeFormat;
import sys.helper.DpPartyHelper;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class DpOrgAdmin implements Serializable {
    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    public DpParty getDpParty(){
        return DpPartyHelper.getDpPartyByPartyId(partyId);
    }
    private Integer id;

    private Integer userId;

    private Integer partyId;

    private Byte type;

    private Byte status;

    private String remark;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date createTime;

    private String code;

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

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
}