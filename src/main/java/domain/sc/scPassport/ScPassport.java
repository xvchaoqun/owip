package domain.sc.scPassport;

import domain.abroad.SafeBox;
import org.springframework.format.annotation.DateTimeFormat;
import sys.helper.AbroadHelper;
import sys.jackson.SignRes;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class ScPassport implements Serializable {

    public SafeBox getSafeBox(){

        if(safeBoxId==null) return null;
        return AbroadHelper.getSafeBoxMap().get(safeBoxId);
    }

    private Integer id;

    private Integer handId;

    private Integer classId;

    private Boolean isExist;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date keepDate;

    private String code;

    @SignRes
    private String pic;

    private String authority;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date issueDate;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date expiryDate;

    private Integer safeBoxId;

    private Date handTime;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHandId() {
        return handId;
    }

    public void setHandId(Integer handId) {
        this.handId = handId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Boolean getIsExist() {
        return isExist;
    }

    public void setIsExist(Boolean isExist) {
        this.isExist = isExist;
    }

    public Date getKeepDate() {
        return keepDate;
    }

    public void setKeepDate(Date keepDate) {
        this.keepDate = keepDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
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

    public Integer getSafeBoxId() {
        return safeBoxId;
    }

    public void setSafeBoxId(Integer safeBoxId) {
        this.safeBoxId = safeBoxId;
    }

    public Date getHandTime() {
        return handTime;
    }

    public void setHandTime(Date handTime) {
        this.handTime = handTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}