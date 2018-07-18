package domain.cadre;

import sys.tags.CmTag;

import java.io.Serializable;

public class CadreLeaderUnitView implements Serializable {

    public CadreView getCadre(){
       return CmTag.getCadreById(cadreId);
    }

    private Integer id;

    private Integer leaderId;

    private Integer unitId;

    private Integer typeId;

    private Integer sortOrder;

    private Integer cadreId;

    private Integer leaderSortOrder;

    private Integer unitSortOrder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Integer getLeaderSortOrder() {
        return leaderSortOrder;
    }

    public void setLeaderSortOrder(Integer leaderSortOrder) {
        this.leaderSortOrder = leaderSortOrder;
    }

    public Integer getUnitSortOrder() {
        return unitSortOrder;
    }

    public void setUnitSortOrder(Integer unitSortOrder) {
        this.unitSortOrder = unitSortOrder;
    }
}