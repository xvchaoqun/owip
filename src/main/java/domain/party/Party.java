package domain.party;

import org.springframework.format.annotation.DateTimeFormat;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Party implements Serializable {

    public Party getParty(){
        if (fid != null) {
            return CmTag.getParty(fid);
        }
        return null;
    }

    private Integer id;

    private Integer fid;

    private String code;

    private String name;

    private String shortName;

    private String url;

    private Integer unitId;

    private String unitIds;

    private Integer classId;

    private Integer typeId;

    private Integer unitTypeId;

    private Integer branchType;

    private Boolean isEnterpriseBig;

    private Boolean isEnterpriseNationalized;

    private Boolean isSeparate;

    private String phone;

    private String address;

    private String fax;

    private String email;

    private String mailbox;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date foundTime;

    private Integer sortOrder;

    private Date createTime;

    private Boolean isPycj;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date pycjDate;

    private Boolean isBg;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date bgDate;

    private Boolean isDeleted;

    private BigDecimal integrity;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getUnitIds() {
        return unitIds;
    }

    public void setUnitIds(String unitIds) {
        this.unitIds = unitIds == null ? null : unitIds.trim();
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Integer unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public Integer getBranchType() {
        return branchType;
    }

    public void setBranchType(Integer branchType) {
        this.branchType = branchType;
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

    public Boolean getIsSeparate() {
        return isSeparate;
    }

    public void setIsSeparate(Boolean isSeparate) {
        this.isSeparate = isSeparate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox == null ? null : mailbox.trim();
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

    public Boolean getIsPycj() {
        return isPycj;
    }

    public void setIsPycj(Boolean isPycj) {
        this.isPycj = isPycj;
    }

    public Date getPycjDate() {
        return pycjDate;
    }

    public void setPycjDate(Date pycjDate) {
        this.pycjDate = pycjDate;
    }

    public Boolean getIsBg() {
        return isBg;
    }

    public void setIsBg(Boolean isBg) {
        this.isBg = isBg;
    }

    public Date getBgDate() {
        return bgDate;
    }

    public void setBgDate(Date bgDate) {
        this.bgDate = bgDate;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public BigDecimal getIntegrity() {
        return integrity;
    }

    public void setIntegrity(BigDecimal integrity) {
        this.integrity = integrity;
    }
}