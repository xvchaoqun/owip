package domain.party;

import domain.sys.MetaType;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Branch implements Serializable {

    public Party getParty() {

        return CmTag.getParty(partyId);
    }
    public MetaType getBranchType(){
        Map<Integer, MetaType> branchTypeMap = CmTag.getMetaTypes("mc_branch_type");
        return branchTypeMap.get(typeId);
    }
    public MetaType getUnitType(){
        Map<Integer, MetaType> unitTypeMap = CmTag.getMetaTypes("mc_branch_unit_type");
        return unitTypeMap.get(unitTypeId);
    }

    private Integer id;

    private String code;

    private String name;

    private String shortName;

    private Integer partyId;

    private Integer typeId;

    private Boolean isStaff;

    private Boolean isPrefessional;

    private Boolean isBaseTeam;

    private Integer unitTypeId;

    private Boolean isEnterpriseBig;

    private Boolean isEnterpriseNationalized;

    private Boolean isUnion;

    private String phone;

    private String fax;

    private String email;

    private Date foundTime;

    private Integer sortOrder;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Boolean getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(Boolean isStaff) {
        this.isStaff = isStaff;
    }

    public Boolean getIsPrefessional() {
        return isPrefessional;
    }

    public void setIsPrefessional(Boolean isPrefessional) {
        this.isPrefessional = isPrefessional;
    }

    public Boolean getIsBaseTeam() {
        return isBaseTeam;
    }

    public void setIsBaseTeam(Boolean isBaseTeam) {
        this.isBaseTeam = isBaseTeam;
    }

    public Integer getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Integer unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public Boolean getIsEnterpriseBig() {
        return isEnterpriseBig;
    }

    public void setIsEnterpriseBig(Boolean isEnterpriseBig) {
        this.isEnterpriseBig = isEnterpriseBig;
    }

    public Boolean getIsEnterpriseNationalized() {
        return isEnterpriseNationalized;
    }

    public void setIsEnterpriseNationalized(Boolean isEnterpriseNationalized) {
        this.isEnterpriseNationalized = isEnterpriseNationalized;
    }

    public Boolean getIsUnion() {
        return isUnion;
    }

    public void setIsUnion(Boolean isUnion) {
        this.isUnion = isUnion;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getFoundTime() {
        return foundTime;
    }

    public void setFoundTime(Date foundTime) {
        this.foundTime = foundTime;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}