package domain.cadre;

import java.io.Serializable;
import java.util.Date;

public class CadreCompany implements Serializable {
    private Integer id;

    private Integer cadreId;

    private Integer type;

    private String typeOther;

    private Boolean hasPay;

    private Date startTime;

    private String unit;

    private String reportUnit;

    private String paper;

    private String paperFilename;

    private String remark;

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

    public Boolean getHasPay() {
        return hasPay;
    }

    public void setHasPay(Boolean hasPay) {
        this.hasPay = hasPay;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getReportUnit() {
        return reportUnit;
    }

    public void setReportUnit(String reportUnit) {
        this.reportUnit = reportUnit == null ? null : reportUnit.trim();
    }

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper == null ? null : paper.trim();
    }

    public String getPaperFilename() {
        return paperFilename;
    }

    public void setPaperFilename(String paperFilename) {
        this.paperFilename = paperFilename == null ? null : paperFilename.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}