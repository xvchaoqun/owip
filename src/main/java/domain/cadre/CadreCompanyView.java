package domain.cadre;

import java.io.Serializable;
import java.util.Date;

public class CadreCompanyView implements Serializable {
    private Integer id;

    private Integer cadreId;

    private Integer type;

    private String typeOther;

    private String unit;

    private String post;

    private Date startTime;

    private Boolean isFinished;

    private Date finishTime;

    private String approvalUnit;

    private Date approvalDate;

    private String approvalFile;

    private String approvalFilename;

    private Boolean hasPay;

    private Boolean hasHand;

    private String remark;

    private Byte status;

    private String code;

    private String realname;

    private String title;

    private Byte cadreStatus;

    private Integer adminLevel;

    private String adminLevelCode;

    private Boolean isDouble;

    private Integer unitTypeId;

    private String unitTypeGroup;

    private Integer cadreSortOrder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeOther() {
        return typeOther;
    }

    public void setTypeOther(String typeOther) {
        this.typeOther = typeOther == null ? null : typeOther.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getApprovalUnit() {
        return approvalUnit;
    }

    public void setApprovalUnit(String approvalUnit) {
        this.approvalUnit = approvalUnit == null ? null : approvalUnit.trim();
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getApprovalFile() {
        return approvalFile;
    }

    public void setApprovalFile(String approvalFile) {
        this.approvalFile = approvalFile == null ? null : approvalFile.trim();
    }

    public String getApprovalFilename() {
        return approvalFilename;
    }

    public void setApprovalFilename(String approvalFilename) {
        this.approvalFilename = approvalFilename == null ? null : approvalFilename.trim();
    }

    public Boolean getHasPay() {
        return hasPay;
    }

    public void setHasPay(Boolean hasPay) {
        this.hasPay = hasPay;
    }

    public Boolean getHasHand() {
        return hasHand;
    }

    public void setHasHand(Boolean hasHand) {
        this.hasHand = hasHand;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Byte getCadreStatus() {
        return cadreStatus;
    }

    public void setCadreStatus(Byte cadreStatus) {
        this.cadreStatus = cadreStatus;
    }

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }

    public String getAdminLevelCode() {
        return adminLevelCode;
    }

    public void setAdminLevelCode(String adminLevelCode) {
        this.adminLevelCode = adminLevelCode == null ? null : adminLevelCode.trim();
    }

    public Boolean getIsDouble() {
        return isDouble;
    }

    public void setIsDouble(Boolean isDouble) {
        this.isDouble = isDouble;
    }

    public Integer getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Integer unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public String getUnitTypeGroup() {
        return unitTypeGroup;
    }

    public void setUnitTypeGroup(String unitTypeGroup) {
        this.unitTypeGroup = unitTypeGroup == null ? null : unitTypeGroup.trim();
    }

    public Integer getCadreSortOrder() {
        return cadreSortOrder;
    }

    public void setCadreSortOrder(Integer cadreSortOrder) {
        this.cadreSortOrder = cadreSortOrder;
    }
}