package controller.analysis;

public class CadreCategorySearchBean {

    private Integer cadreId;
    private Boolean isDep;
    private Byte cadreStatus;
    private Boolean hasTalentTitle;

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Boolean getDep() {
        return isDep;
    }

    public void setDep(Boolean dep) {
        isDep = dep;
    }

    public Byte getCadreStatus() {
        return cadreStatus;
    }

    public void setCadreStatus(Byte cadreStatus) {
        this.cadreStatus = cadreStatus;
    }

    public Boolean getHasTalentTitle() {
        return hasTalentTitle;
    }

    public void setHasTalentTitle(Boolean hasTalentTitle) {
        this.hasTalentTitle = hasTalentTitle;
    }
}