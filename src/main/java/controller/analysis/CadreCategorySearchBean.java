package controller.analysis;

public class CadreCategorySearchBean {

    private Integer cadreId;
    private Integer type;
    private Byte cadreStatus;
    private Boolean hasTalentTitle;

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