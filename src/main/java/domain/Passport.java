package domain;

import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Passport implements Serializable {
    public SysUser getUser(){

        Cadre cadre = getCadre();
        return CmTag.getUserById(cadre.getUserId());
    }
    public Cadre getCadre(){

        return CmTag.getCadreById(cadreId);
    }
    public String getPassportType(){

        return SystemConstants.PASSPORT_TYPE_MAP.get(type);
    }
    public MetaType getPassportClass(){

        Map<Integer, MetaType> passportClassMap = CmTag.getMetaTypes("mc_passport_type");
        return passportClassMap.get(classId);
    }
    public SafeBox getSafeBox(){

        return CmTag.getSafeBoxMap().get(safeBoxId);
    }


    private Integer id;

    private Integer applyId;

    private Integer cadreId;

    private Integer classId;

    private String code;

    private String authority;

    private Date issueDate;

    private Date expiryDate;

    private Date keepDate;

    private Integer safeBoxId;

    private Boolean isLent;

    private Byte type;

    private Byte cancelType;

    private Boolean cancelConfirm;

    private String cancelPic;

    private Date cancelTime;

    private Date lostTime;

    private String lostProof;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority == null ? null : authority.trim();
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getKeepDate() {
        return keepDate;
    }

    public void setKeepDate(Date keepDate) {
        this.keepDate = keepDate;
    }

    public Integer getSafeBoxId() {
        return safeBoxId;
    }

    public void setSafeBoxId(Integer safeBoxId) {
        this.safeBoxId = safeBoxId;
    }

    public Boolean getIsLent() {
        return isLent;
    }

    public void setIsLent(Boolean isLent) {
        this.isLent = isLent;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getCancelType() {
        return cancelType;
    }

    public void setCancelType(Byte cancelType) {
        this.cancelType = cancelType;
    }

    public Boolean getCancelConfirm() {
        return cancelConfirm;
    }

    public void setCancelConfirm(Boolean cancelConfirm) {
        this.cancelConfirm = cancelConfirm;
    }

    public String getCancelPic() {
        return cancelPic;
    }

    public void setCancelPic(String cancelPic) {
        this.cancelPic = cancelPic == null ? null : cancelPic.trim();
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getLostTime() {
        return lostTime;
    }

    public void setLostTime(Date lostTime) {
        this.lostTime = lostTime;
    }

    public String getLostProof() {
        return lostProof;
    }

    public void setLostProof(String lostProof) {
        this.lostProof = lostProof == null ? null : lostProof.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}