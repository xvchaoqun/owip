package domain.sc.scAd;

import java.io.Serializable;
import java.util.Date;

public class ScAdArchiveView implements Serializable {
    private Integer id;

    private Integer committeeId;

    private Integer cadreId;

    private Boolean isAdformSaved;

    private Boolean hasAppoint;

    private Integer objId;

    private String filePath;

    private String signFilePath;

    private String cisFilePath;

    private String cisSignFilePath;

    private String remark;

    private Integer year;

    private String committeeFilePath;

    private Date holdDate;

    private String code;

    private String realname;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(Integer committeeId) {
        this.committeeId = committeeId;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Boolean getIsAdformSaved() {
        return isAdformSaved;
    }

    public void setIsAdformSaved(Boolean isAdformSaved) {
        this.isAdformSaved = isAdformSaved;
    }

    public Boolean getHasAppoint() {
        return hasAppoint;
    }

    public void setHasAppoint(Boolean hasAppoint) {
        this.hasAppoint = hasAppoint;
    }

    public Integer getObjId() {
        return objId;
    }

    public void setObjId(Integer objId) {
        this.objId = objId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getSignFilePath() {
        return signFilePath;
    }

    public void setSignFilePath(String signFilePath) {
        this.signFilePath = signFilePath == null ? null : signFilePath.trim();
    }

    public String getCisFilePath() {
        return cisFilePath;
    }

    public void setCisFilePath(String cisFilePath) {
        this.cisFilePath = cisFilePath == null ? null : cisFilePath.trim();
    }

    public String getCisSignFilePath() {
        return cisSignFilePath;
    }

    public void setCisSignFilePath(String cisSignFilePath) {
        this.cisSignFilePath = cisSignFilePath == null ? null : cisSignFilePath.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCommitteeFilePath() {
        return committeeFilePath;
    }

    public void setCommitteeFilePath(String committeeFilePath) {
        this.committeeFilePath = committeeFilePath == null ? null : committeeFilePath.trim();
    }

    public Date getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(Date holdDate) {
        this.holdDate = holdDate;
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
}