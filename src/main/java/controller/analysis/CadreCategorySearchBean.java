package controller.analysis;

public class CadreCategorySearchBean {

    private Integer cadreId;
    private Byte cadreStatus;
    private Integer unitTypeId;
    private Integer notUnitTypeId;
    private Boolean hasTalentTitle;

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Byte getCadreStatus() {
        return cadreStatus;
    }

    public void setCadreStatus(Byte cadreStatus) {
        this.cadreStatus = cadreStatus;
    }

    public Integer getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Integer unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public Integer getNotUnitTypeId() {
        return notUnitTypeId;
    }

    public void setNotUnitTypeId(Integer notUnitTypeId) {
        this.notUnitTypeId = notUnitTypeId;
    }

    public Boolean getHasTalentTitle() {
        return hasTalentTitle;
    }

    public void setHasTalentTitle(Boolean hasTalentTitle) {
        this.hasTalentTitle = hasTalentTitle;
    }
}