package domain.dp;

import domain.cadre.Cadre;
import domain.sys.SysUserView;
import service.dp.dpCommon.DpCommonService;
import sys.helper.DpPartyHelper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class DpPrCmView implements Serializable {

    public Cadre getCadre(){
        DpCommonService dpCommonService = CmTag.getBean(DpCommonService.class);
        return dpCommonService.findOrCreateCadre(userId);
    }

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    public DpParty getDpParty(){
        return DpPartyHelper.getDpPartyByPartyId(partyId);
    }
    private Integer id;

    private Integer userId;

    private String electSession;

    private Date electTime;

    private Date endTime;

    private Boolean status;

    private Integer type;

    private Integer sortOrder;

    private String remark;

    private Integer partyId;

    private Date dpGrowTime;

    private Byte gender;

    private Date birth;

    private String nation;

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

    public String getElectSession() {
        return electSession;
    }

    public void setElectSession(String electSession) {
        this.electSession = electSession == null ? null : electSession.trim();
    }

    public Date getElectTime() {
        return electTime;
    }

    public void setElectTime(Date electTime) {
        this.electTime = electTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Date getDpGrowTime() {
        return dpGrowTime;
    }

    public void setDpGrowTime(Date dpGrowTime) {
        this.dpGrowTime = dpGrowTime;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }
}