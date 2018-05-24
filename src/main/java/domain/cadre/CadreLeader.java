package domain.cadre;

import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;

public class CadreLeader implements Serializable {
    public SysUserView getUser(){
        CadreView cadre = getCadre();
        return CmTag.getUserById(cadre.getUserId());
    }
    public CadreView getCadre(){

        return CmTag.getCadreById(cadreId);
    }

    private Integer id;

    private Integer cadreId;

    private Integer typeId;

    private String job;

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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}