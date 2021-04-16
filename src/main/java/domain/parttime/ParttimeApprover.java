package domain.parttime;

import domain.cadre.CadreView;
import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;

public class ParttimeApprover implements Serializable {
    private CadreView cadreView;
    private SysUserView sysUserView;
    public SysUserView getUser(){

        if(sysUserView==null) {
            CadreView cadre = getCadre();
            sysUserView = CmTag.getUserById(cadre.getUserId());
        }

        return sysUserView;
    }
    public CadreView getCadre(){

        if(cadreView==null){
            cadreView = CmTag.getCadreById(cadreId);
        }

        return cadreView;
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