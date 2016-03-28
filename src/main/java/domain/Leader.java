package domain;

import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Map;

public class Leader implements Serializable {
    public SysUser getUser(){
        Cadre cadre = getCadre();
        return CmTag.getUserById(cadre.getUserId());
    }
    public Cadre getCadre(){

        return CmTag.getCadreById(cadreId);
    }
    public MetaType getLeaderType(){

        Map<Integer, MetaType> leaderTypeMap = CmTag.getMetaTypes("mc_leader_type");
        return leaderTypeMap.get(typeId);
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