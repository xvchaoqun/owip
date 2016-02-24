package domain;

import java.io.Serializable;
import java.util.Date;

public class Passport implements Serializable {
    private Integer id;

    private Integer cadreId;

    private Integer classId;

    private String code;

    private String authority;

    private Date issueDate;

    private Date expiryDate;

    private Date keepDate;

    private String safeCode;

    private Boolean isLent;

    private Byte type;

    private Byte cancelType;

    private Boolean cancelConfirm;

    private String cancelPic;

    private Date cancelTime;

    private String lostProof;

    private Date createTime;

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

    public String getSafeCode() {
        return safeCode;
    }

    public void setSafeCode(String safeCode) {
        this.safeCode = safeCode == null ? null : safeCode.trim();
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