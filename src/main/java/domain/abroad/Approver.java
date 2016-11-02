package domain.abroad;

import domain.cadre.Cadre;
import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;

public class Approver implements Serializable {

    public SysUserView getUser(){
        Cadre cadre = getCadre();
        return CmTag.getUserById(cadre.getUserId());
    }
    public Cadre getCadre(){

        return CmTag.getCadreById(cadreId);
    }

    private Integer id;

    private Integer cadreId;

    private Integer typeId;

    private Integer sortOrder;

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
}