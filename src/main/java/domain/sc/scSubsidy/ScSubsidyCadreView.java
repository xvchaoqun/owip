package domain.sc.scSubsidy;

import domain.cadre.CadreView;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class ScSubsidyCadreView implements Serializable {

    public CadreView getCadre(){
        return CmTag.getCadreById(cadreId);
    }

    public String getHrCode(){

        return ScSubsidy.getHrCode(hrType, year, hrNum);
    }

    public String getFeCode(){

        return ScSubsidy.getFeCode(feType, year, feNum);
    }

    private Integer id;

    private Integer subsidyId;

    private Integer cadreId;

    private Integer unitId;

    private String post;

    private Integer adminLevel;

    private String remark;

    private Date infoDate;

    private Short year;

    private Integer hrType;

    private Integer hrNum;

    private String hrFilePath;

    private Integer feType;

    private Integer feNum;

    private String feFilePath;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubsidyId() {
        return subsidyId;
    }

    public void setSubsidyId(Integer subsidyId) {
        this.subsidyId = subsidyId;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getInfoDate() {
        return infoDate;
    }

    public void setInfoDate(Date infoDate) {
        this.infoDate = infoDate;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public Integer getHrType() {
        return hrType;
    }

    public void setHrType(Integer hrType) {
        this.hrType = hrType;
    }

    public Integer getHrNum() {
        return hrNum;
    }

    public void setHrNum(Integer hrNum) {
        this.hrNum = hrNum;
    }

    public String getHrFilePath() {
        return hrFilePath;
    }

    public void setHrFilePath(String hrFilePath) {
        this.hrFilePath = hrFilePath == null ? null : hrFilePath.trim();
    }

    public Integer getFeType() {
        return feType;
    }

    public void setFeType(Integer feType) {
        this.feType = feType;
    }

    public Integer getFeNum() {
        return feNum;
    }

    public void setFeNum(Integer feNum) {
        this.feNum = feNum;
    }

    public String getFeFilePath() {
        return feFilePath;
    }

    public void setFeFilePath(String feFilePath) {
        this.feFilePath = feFilePath == null ? null : feFilePath.trim();
    }
}