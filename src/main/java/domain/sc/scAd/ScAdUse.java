package domain.sc.scAd;

import domain.cadre.CadreView;
import domain.unit.Unit;
import org.springframework.format.annotation.DateTimeFormat;
import sys.jackson.SignRes;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class ScAdUse implements Serializable {

    public Unit getUnit(){
        if(unitId==null) return null;
        return CmTag.getUnit(unitId);
    }

    public CadreView getCadre(){
        return CmTag.getCadreById(cadreId);
    }

    private Integer id;

    private Integer year;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date useDate;

    private Boolean isOnCampus;

    private Integer unitId;

    private String outUnit;

    private Integer cadreId;

    private Boolean isAdformSaved;

    private String filePath;

    @SignRes
    private String signFilePath;

    private String useage;

    private String remark;

    private String adform;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    public Boolean getIsOnCampus() {
        return isOnCampus;
    }

    public void setIsOnCampus(Boolean isOnCampus) {
        this.isOnCampus = isOnCampus;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getOutUnit() {
        return outUnit;
    }

    public void setOutUnit(String outUnit) {
        this.outUnit = outUnit == null ? null : outUnit.trim();
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

    public String getUseage() {
        return useage;
    }

    public void setUseage(String useage) {
        this.useage = useage == null ? null : useage.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAdform() {
        return adform;
    }

    public void setAdform(String adform) {
        this.adform = adform == null ? null : adform.trim();
    }
}